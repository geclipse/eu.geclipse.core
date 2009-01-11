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

package eu.geclipse.aws.ec2.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.xerox.amazonws.ec2.GroupDescription;

import eu.geclipse.aws.ec2.EC2ProblemException;
import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.IEC2Problems;
import eu.geclipse.aws.ec2.op.EC2OpAuthorizeSecurityGroup;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * This Wizard defines input methods to specify a valid CIDR based access
 * permission. In the {@link #performFinish()} method the actual CIDR entry is
 * added in EC2.
 * 
 * @author Moritz Post
 */
public class CIDRPermissionWizard extends Wizard {

  /** The security group name denoting the currently edited security group. */
  private GroupDescription securityGroupName;

  /** The wizard page providing the form elements. */
  private CIDRPermissionWizardPage wizardPage;

  /** The aws access id to identify the current user. */
  private String awsAccessId;

  /**
   * This constructor creates a new WIzard and stores the
   * {@link #securityGroupName} group name.
   * 
   * @param securityGroupName the security group which permissions to edit
   * @param awsAccessId the access id to identify the user
   */
  public CIDRPermissionWizard( final GroupDescription securityGroupName,
                               final String awsAccessId )
  {
    this.securityGroupName = securityGroupName;
    this.awsAccessId = awsAccessId;
  }

  @Override
  public void addPages() {
    this.wizardPage = new CIDRPermissionWizardPage( this.securityGroupName );
    addPage( this.wizardPage );
  }

  @Override
  public String getWindowTitle() {
    return Messages.getString( "CIDRPermissionWizard.wizard_title" ); //$NON-NLS-1$
  }

  @Override
  public boolean performFinish() {
    if( this.wizardPage.isPageComplete() ) {
      try {
        getContainer().run( false, false, new IRunnableWithProgress() {

          public void run( final IProgressMonitor monitor )
            throws InvocationTargetException, InterruptedException
          {
            IEC2 ec2 = null;
            ec2 = EC2Registry.getRegistry()
              .getEC2( CIDRPermissionWizard.this.awsAccessId );
            EC2OpAuthorizeSecurityGroup opAuthorizeSecurityGroup = new EC2OpAuthorizeSecurityGroup( ec2,
                                                                                                    CIDRPermissionWizard.this.securityGroupName.getName(),
                                                                                                    CIDRPermissionWizard.this.wizardPage.getCidr(),
                                                                                                    CIDRPermissionWizard.this.wizardPage.getProtocol(),
                                                                                                    CIDRPermissionWizard.this.wizardPage.getFromPort(),
                                                                                                    CIDRPermissionWizard.this.wizardPage.getToPort() );
            OperationExecuter operationExecuter = new OperationExecuter();
            operationExecuter.execOp( opAuthorizeSecurityGroup );

            if( opAuthorizeSecurityGroup.getException() != null ) {
              throw new InvocationTargetException( opAuthorizeSecurityGroup.getException() );
            }
          }
        } );
      } catch( final Exception ex ) {
        Activator.log( "A problem occured while authorizing security group access", ex ); //$NON-NLS-1$

        // process any errors
        final EC2ProblemException exception = new EC2ProblemException( IEC2Problems.EC2_INTERACTION,
                                                                       ex.getCause()
                                                                         .getCause()
                                                                         .getLocalizedMessage(),
                                                                       ex,
                                                                       Activator.PLUGIN_ID );

        Display display = PlatformUI.getWorkbench().getDisplay();
        display.asyncExec( new Runnable() {

          public void run() {
            IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
              .getActiveWorkbenchWindow();
            ProblemDialog.openProblem( workbenchWindow.getShell(),
                                       Messages.getString( "CIDRPermissionWizard.problem_title" ), //$NON-NLS-1$
                                       Messages.getString( "CIDRPermissionWizard.problem_description" ), //$NON-NLS-1$
                                       exception );
          }
        } );
        return false;
      }
      return true;
    }
    return false;

  }
}
