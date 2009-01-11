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
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.aws.ec2.EC2Instance;
import eu.geclipse.aws.ec2.EC2ProblemException;
import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.IEC2Problems;
import eu.geclipse.aws.ec2.op.EC2OpTerminateInstances;
import eu.geclipse.aws.ec2.op.IOperation;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.service.EC2Service;
import eu.geclipse.aws.ec2.ui.Messages;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * This {@link IActionDelegate} is registered on the list of running EC2
 * instances. It is registered on {@link IGridComputing} objects. On invocation,
 * the selected instances are terminated using the
 * {@link EC2OpTerminateInstances} {@link IOperation}. The action is executed in
 * its own {@link Job}.
 * 
 * @author Moritz Post
 */
public class TerminateInstance implements IActionDelegate {

  /** The list of AMIs which are selected. */
  private ArrayList<EC2Instance> instanceList;

  /**
   * Default constructor.
   */
  public TerminateInstance() {
    this.instanceList = new ArrayList<EC2Instance>();
  }

  public void run( final IAction action ) {
    if( action.isEnabled() ) {

      Job job = new Job( Messages.getString( "TerminateInstance.job_title" ) ) { //$NON-NLS-1$

        @Override
        protected IStatus run( final IProgressMonitor monitor ) {
          monitor.beginTask( Messages.getString( "TerminateInstance.monitor_title" ), 2 ); //$NON-NLS-1$
          ArrayList<String> instanceIds = new ArrayList<String>();
          for( EC2Instance ec2Instance : TerminateInstance.this.instanceList ) {
            instanceIds.add( ec2Instance.getInstanceId() );
          }
          monitor.worked( 1 );

          EC2Instance firstInstance = null;
          if( TerminateInstance.this.instanceList.size() > 0 ) {
            firstInstance = TerminateInstance.this.instanceList.get( 0 );
          } else {
            return Status.CANCEL_STATUS;
          }

          EC2Service ec2Service = firstInstance.getEC2Service();
          AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )ec2Service.getParent();
          String awsAccessId = null;
          try {
            awsAccessId = awsVo.getProperties().getAwsAccessId();
          } catch( ProblemException problemEx ) {
            Activator.log( "Could not read aws vo properties", problemEx ); //$NON-NLS-1$
          }
          IEC2 ec2 = EC2Registry.getRegistry().getEC2( awsAccessId );
          EC2OpTerminateInstances opTerminateInstances = new EC2OpTerminateInstances( ec2,
                                                                                      instanceIds );
          OperationExecuter operationExecuter = new OperationExecuter();
          operationExecuter.execOp( opTerminateInstances );
          monitor.done();

          if( opTerminateInstances.getException() != null ) {
            Throwable cause = opTerminateInstances.getException();
            final EC2ProblemException exception = new EC2ProblemException( IEC2Problems.EC2_INTERACTION,
                                                                           cause.getCause()
                                                                             .getLocalizedMessage(),
                                                                           cause,
                                                                           Activator.PLUGIN_ID );
            Display display = PlatformUI.getWorkbench().getDisplay();
            display.asyncExec( new Runnable() {

              public void run() {
                IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
                  .getActiveWorkbenchWindow();
                ProblemDialog.openProblem( workbenchWindow.getShell(),
                                           Messages.getString( "TerminateInstance.error_problem_with_ec2_title" ), //$NON-NLS-1$
                                           Messages.getString( "TerminateInstance.error_problem_with_ec2_description" ), //$NON-NLS-1$
                                           exception );
              }
            } );
          }
          return Status.OK_STATUS;
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
