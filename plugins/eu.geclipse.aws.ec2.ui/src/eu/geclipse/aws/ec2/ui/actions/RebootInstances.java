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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;

import eu.geclipse.aws.ec2.EC2Instance;
import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.op.EC2OpRebootInstances;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.ui.Messages;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.core.reporting.ProblemException;

/**
 * This {@link IObjectActionDelegate} is used to start a {@link Job} which is
 * rebooting an instance.
 * 
 * @author Moritz Post
 */
public class RebootInstances extends AbstractAWSProjectAction {

  /** The list of AMIs which are selected. */
  private ArrayList<EC2Instance> instanceList;

  /**
   * Instantiate and initialize the reboot object.
   */
  public RebootInstances() {
    this.instanceList = new ArrayList<EC2Instance>();
  }

  @Override
  public void run( final IAction action ) {
    if( action.isEnabled() ) {

      Job job = new Job( Messages.getString( "TerminateInstance.job_title" ) ) { //$NON-NLS-1$

        @Override
        protected IStatus run( final IProgressMonitor monitor ) {
          try {
            if( RebootInstances.this.instanceList.size() == 0 ) {
              return Status.CANCEL_STATUS;
            }

            monitor.beginTask( Messages.getString("RebootInstances.monitor_title"), 2 ); //$NON-NLS-1$

            extractAWSVoFromGridElement( RebootInstances.this.instanceList.get( 0 ) );

            IEC2 ec2;
            try {
              ec2 = EC2Registry.getRegistry().getEC2( getAwsVo() );
            } catch( ProblemException problemEx ) {
              Activator.log( "Can not obtain IEC2 instance", problemEx ); //$NON-NLS-1$
              return Status.CANCEL_STATUS;
            }
            monitor.worked( 1 );

            // extract instance ids
            ArrayList<String> instanceIds = new ArrayList<String>( RebootInstances.this.instanceList.size() );

            for( EC2Instance instance : RebootInstances.this.instanceList ) {
              instanceIds.add( instance.getInstanceId() );
            }

            EC2OpRebootInstances opRebootInstances = new EC2OpRebootInstances( ec2,
                                                                               instanceIds );
            new OperationExecuter().execOp( opRebootInstances );

            // process any errors
            if( opRebootInstances.getException() != null ) {
              Throwable cause = opRebootInstances.getException();
              processException( cause, Messages.getString("RebootInstances.problem_description") ); //$NON-NLS-1$
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
