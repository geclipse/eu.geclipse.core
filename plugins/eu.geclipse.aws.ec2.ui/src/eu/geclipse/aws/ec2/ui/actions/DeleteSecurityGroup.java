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

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.aws.ec2.EC2ProblemException;
import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.EC2SecurityGroup;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.IEC2Problems;
import eu.geclipse.aws.ec2.op.EC2OpDeleteSecurityGroup;
import eu.geclipse.aws.ec2.op.IOperation;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.op.OperationSet;
import eu.geclipse.aws.ec2.service.EC2Service;
import eu.geclipse.aws.ec2.ui.Messages;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * Deletes the currently selected security group from EC2.
 * 
 * @author Moritz Post
 */
public class DeleteSecurityGroup extends AbstractSecurityGroupAction {

  public void run( final IAction action ) {
    if( action.isEnabled() ) {

      List<EC2SecurityGroup> securityGroupList = getSecurityGroupList();
      StringBuffer strBuf = new StringBuffer();

      String dash = "- "; //$NON-NLS-1$
      String newLine = "\n"; //$NON-NLS-1$

      for( EC2SecurityGroup securityGroup : securityGroupList ) {
        strBuf.append( dash + securityGroup.getName() + newLine );
      }

      Shell shell = getWorkbenchPart().getSite().getShell();
      boolean confirmation = MessageDialog.openConfirm( shell,
                                                        Messages.getString( "DeleteSecurityGroup.dialog_title_delete_security_group" ), //$NON-NLS-1$
                                                        Messages.getString( "DeleteSecurityGroup.dialog_description_delete_security_group" ) //$NON-NLS-1$
                                                            + newLine
                                                            + newLine
                                                            + strBuf.toString() );

      if( confirmation ) {
        deleteSecurityGroup();
      }
    }
  }

  /**
   * 
   */
  private void deleteSecurityGroup() {
    Job job = new Job( Messages.getString( "DeleteSecurityGroup.job_title" ) ) { //$NON-NLS-1$

      @Override
      protected IStatus run( final IProgressMonitor monitor ) {

        List<EC2SecurityGroup> securityGroupList = getSecurityGroupList();

        monitor.beginTask( Messages.getString( "DeleteSecurityGroup.task_title_delete_security_group" ), securityGroupList.size() ); //$NON-NLS-1$

        // extract aws access id from first security group (at least one is
        // present)
        EC2Service ec2Service = securityGroupList.get( 0 ).getEC2Service();
        AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )ec2Service.getParent();
        String awsAccessId = null;
        try {
          awsAccessId = awsVo.getProperties().getAwsAccessId();
        } catch( ProblemException problemEx ) {
          Activator.log( "Could not read aws vo properties", problemEx ); //$NON-NLS-1$
        }

        // create operations for each security group
        OperationSet operationSet = new OperationSet();
        IEC2 ec2 = EC2Registry.getRegistry().getEC2( awsAccessId );

        for( EC2SecurityGroup securityGroup : securityGroupList ) {
          EC2OpDeleteSecurityGroup opDeleteSecurityGroup = new EC2OpDeleteSecurityGroup( ec2,
                                                                                         securityGroup.getName() );
          operationSet.addOp( opDeleteSecurityGroup );

        }

        // execute operations
        new OperationExecuter().execOpGroup( operationSet );

        // process any errors
        for( IOperation op : operationSet.getOps() ) {

          if( op.getException() != null ) {
            Throwable cause = op.getException();
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
                                           Messages.getString( "DeleteSecurityGroup.problem_title" ), //$NON-NLS-1$
                                           Messages.getString( "DeleteSecurityGroup.problem_description" ), //$NON-NLS-1$
                                           exception );
              }
            } );
          }
        }
        return Status.OK_STATUS;
      }
    };
    job.setPriority( Job.SHORT );
    job.setUser( true );
    job.schedule();
  }

  @Override
  protected int getEnablementCount() {
    return AbstractSecurityGroupAction.ENABLE_FOR_MANY;
  }
}
