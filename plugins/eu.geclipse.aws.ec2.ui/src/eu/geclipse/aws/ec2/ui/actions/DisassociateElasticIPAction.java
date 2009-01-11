/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.ec2.ui.actions;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;

import com.xerox.amazonws.ec2.AddressInfo;

import eu.geclipse.aws.ec2.EC2Instance;
import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.op.EC2OpDescribeAddresses;
import eu.geclipse.aws.ec2.op.EC2OpDisassociateAddress;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.ui.Messages;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.core.reporting.ProblemException;

/**
 * This {@link IObjectActionDelegate} provides the {@link Job} to disassociate
 * an elastic IP from an ec2 instance.
 * 
 * @author Moritz Post
 */
public class DisassociateElasticIPAction extends AbstractAWSProjectAction {

  /** The list of selected {@link EC2Instance}s. */
  private List<EC2Instance> instanceList;

  /**
   * Create the a new instance of this {@link IObjectActionDelegate}.
   */
  public DisassociateElasticIPAction() {
    this.instanceList = new ArrayList<EC2Instance>();
  }

  @Override
  public void run( final IAction action ) {
    final EC2Instance ec2Instance = this.instanceList.get( 0 );

    Job job = new Job( MessageFormat.format( Messages.getString( "DisassociateElasticIPAction.job_title" ), //$NON-NLS-1$
                                             ec2Instance.getInstanceId() ) )
    {

      @Override
      protected IStatus run( final IProgressMonitor monitor ) {

        monitor.beginTask( MessageFormat.format( Messages.getString( "DisassociateElasticIPAction.monitor_title" ), //$NON-NLS-1$
                                                 ec2Instance.getInstanceId() ),
                           3 );

        IEC2 ec2 = null;
        try {
          extractAWSVoFromGridElement( ec2Instance );
          ec2 = EC2Registry.getRegistry().getEC2( getAwsVo() );
        } catch( ProblemException problemEx ) {
          Activator.log( "Can not obtain IEC2 instance", problemEx ); //$NON-NLS-1$
          return Status.CANCEL_STATUS;
        }

        monitor.worked( 1 );

        OperationExecuter operationExecuter = new OperationExecuter();

        monitor.subTask( Messages.getString( "DisassociateElasticIPAction.monitor_subtask_fetch_elastic_ip_to_instance_id_mapping" ) ); //$NON-NLS-1$

        String elasticIP = fetchMappedElasticIP( ec2Instance,
                                                 ec2,
                                                 operationExecuter );
        if( elasticIP != null ) {

          monitor.subTask( MessageFormat.format( Messages.getString( "DisassociateElasticIPAction.monitor_subtask_disassociating_elastic_ip" ), //$NON-NLS-1$
                                                 elasticIP,
                                                 ec2Instance.getInstanceId() ) );

          EC2OpDisassociateAddress opDisassociateAddress = new EC2OpDisassociateAddress( ec2,
                                                                                         elasticIP );
          operationExecuter.execOp( opDisassociateAddress );

          // process any errors
          if( opDisassociateAddress.getException() != null ) {
            Throwable cause = opDisassociateAddress.getException();
            processException( cause,
                              Messages.getString( "DisassociateElasticIPAction.problem_description_could_not_disassociate_elastic_ip" ) ); //$NON-NLS-1$
            return Status.CANCEL_STATUS;
          }
        } else {
          Exception exception = new Exception( MessageFormat.format( Messages.getString( "DisassociateElasticIPAction.exception_description_no_elastic_ip_bound_to_instance" ), //$NON-NLS-1$
                                                                     ec2Instance.getInstanceId() ) );
          processException( exception,
                            Messages.getString( "DisassociateElasticIPAction.problem_description_could_not_disassociate_elastic_ip" ) ); //$NON-NLS-1$
          return Status.CANCEL_STATUS;
        }
        return Status.OK_STATUS;
      }

      /**
       * Fetches the Elastic IP associated with the passed instance or
       * <code>null</code> if there is no mapping currently assigned.
       * 
       * @param ec2Instance the instance to check
       * @param ec2 the IEC2 to do the interfacing with
       * @param operationExecuter an operation executor to start query
       * @return the assigned Elastic IP or <code>null</code> if none
       */
      private String fetchMappedElasticIP( final EC2Instance ec2Instance,
                                           final IEC2 ec2,
                                           final OperationExecuter operationExecuter )
      {
        EC2OpDescribeAddresses opDescribeAddresses = new EC2OpDescribeAddresses( ec2 );

        operationExecuter.execOp( opDescribeAddresses );

        // process any errors
        if( opDescribeAddresses.getException() != null ) {
          Throwable cause = opDescribeAddresses.getException();
          processException( cause,
                            Messages.getString( "DisassociateElasticIPAction.problem_description_could_not_describe_elastic_ips" ) ); //$NON-NLS-1$
        }

        String elasticIP = null;
        for( AddressInfo addressInfo : opDescribeAddresses.getResult() ) {
          if( addressInfo.getInstanceId().equals( ec2Instance.getInstanceId() ) )
          {
            elasticIP = addressInfo.getPublicIp();
          }
        }
        return elasticIP;
      }
    };
    job.setPriority( Job.SHORT );
    job.setUser( true );
    job.schedule();

  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    boolean enable = false;
    this.instanceList.clear();

    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection structuredSelection = ( IStructuredSelection )selection;
      for( Object element : structuredSelection.toList() ) {
        if( element instanceof EC2Instance ) {
          EC2Instance ec2Instance = ( EC2Instance )element;
          this.instanceList.add( ec2Instance );
        }
      }
    }
    if( this.instanceList.size() > 0 ) {
      enable = true;
    }
    action.setEnabled( enable );
  }

}
