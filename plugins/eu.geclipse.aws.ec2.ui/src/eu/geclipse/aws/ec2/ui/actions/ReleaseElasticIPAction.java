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

import eu.geclipse.aws.ec2.EC2ElasticIPAddress;
import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.op.EC2OpReleaseAddress;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.ui.Messages;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.core.reporting.ProblemException;

/**
 * This {@link IObjectActionDelegate} runs the {@link Job} to release a
 * currently allocated elastic IP.
 * 
 * @author Moritz Post
 */
public class ReleaseElasticIPAction extends AbstractAWSProjectAction {

  /** The list selected Elastic IPs. */
  private List<EC2ElasticIPAddress> elasticIPList;

  /**
   * Creates a new instance of this {@link IObjectActionDelegate}.
   */
  public ReleaseElasticIPAction() {
    this.elasticIPList = new ArrayList<EC2ElasticIPAddress>();
  }

  @Override
  public void run( final IAction action ) {
    Job job = new Job( Messages.getString( "ReleaseElasticIPAction.job_title" ) ) { //$NON-NLS-1$

      @Override
      protected IStatus run( final IProgressMonitor monitor ) {

        try {
          if( ReleaseElasticIPAction.this.elasticIPList.size() == 0 ) {
            return Status.CANCEL_STATUS;
          }

          monitor.beginTask( Messages.getString( "ReleaseElasticIPAction.monitor_title" ), 2 ); //$NON-NLS-1$

          extractAWSVoFromGridElement( ReleaseElasticIPAction.this.elasticIPList.get( 0 ) );

          IEC2 ec2;
          try {
            ec2 = EC2Registry.getRegistry().getEC2( getAwsVo() );
          } catch( ProblemException problemEx ) {
            Activator.log( "Can not obtain IEC2 instance", problemEx ); //$NON-NLS-1$
            return Status.CANCEL_STATUS;
          }
          monitor.worked( 1 );

          AddressInfo addressInfo = ReleaseElasticIPAction.this.elasticIPList.get( 0 )
            .getAddressInfo();
          EC2OpReleaseAddress opReleaseAddress = new EC2OpReleaseAddress( ec2,
                                                                          addressInfo.getPublicIp() );
          new OperationExecuter().execOp( opReleaseAddress );

          // process any errors
          if( opReleaseAddress.getException() != null ) {
            Throwable cause = opReleaseAddress.getException();
            processException( cause,
                              Messages.getString( "ReleaseElasticIPAction.problem_description" ) ); //$NON-NLS-1$
            return Status.CANCEL_STATUS;
          }
          return Status.OK_STATUS;
        } finally {
          monitor.done();
        }
      }
    };

    job.setPriority( Job.SHORT );
    job.setUser( true );
    job.schedule();
  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    boolean enable = false;
    this.elasticIPList.clear();

    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection structuredSelection = ( IStructuredSelection )selection;
      for( Object element : structuredSelection.toList() ) {
        if( element instanceof EC2ElasticIPAddress ) {
          EC2ElasticIPAddress elasticIp = ( EC2ElasticIPAddress )element;
          this.elasticIPList.add( elasticIp );
        }
      }
    }
    if( this.elasticIPList.size() > 0 ) {
      enable = true;
    }
    action.setEnabled( enable );
  }

}
