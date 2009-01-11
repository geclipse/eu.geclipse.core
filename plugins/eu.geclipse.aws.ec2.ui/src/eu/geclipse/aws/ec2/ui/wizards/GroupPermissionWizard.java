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
import org.eclipse.jface.wizard.WizardPage;

import com.xerox.amazonws.ec2.GroupDescription;

import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.op.EC2OpAuthorizeSecurityGroup;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * The Wizard to add and remove permissions to a security group.
 * 
 * @author Moritz Post
 */
public class GroupPermissionWizard extends Wizard {

  /** The security group to add and remove permission from. */
  private GroupDescription securityGroupName;

  /** The {@link WizardPage} providing the form elements. */
  private GroupPermissionWizardPage wizardPage;

  /** The aws access id to identify the current user. */
  private String awsAccessId;

  /**
   * Creates a new Wizard with the passed security group name as the group to
   * modify.
   * 
   * @param securityGroupName the group to edit
   * @param awsAccessId The aws access id to identify the current account
   */
  public GroupPermissionWizard( final GroupDescription securityGroupName,
                                final String awsAccessId )
  {
    this.securityGroupName = securityGroupName;
    this.awsAccessId = awsAccessId;
  }

  @Override
  public void addPages() {
    this.wizardPage = new GroupPermissionWizardPage( this.securityGroupName );
    addPage( this.wizardPage );
  }

  @Override
  public String getWindowTitle() {
    return Messages.getString( "GroupPermissionWizard.wizard_title" ); //$NON-NLS-1$
  }

  @Override
  public boolean performFinish() {
    if( this.wizardPage.isPageComplete() ) {
      try {
        getContainer().run( false, false, new IRunnableWithProgress() {

          public void run( final IProgressMonitor monitor )
            throws InvocationTargetException, InterruptedException
          {
            IEC2 ec2 = EC2Registry.getRegistry()
              .getEC2( GroupPermissionWizard.this.awsAccessId );
            EC2OpAuthorizeSecurityGroup opAuthorizeSecurityGroup = new EC2OpAuthorizeSecurityGroup( ec2,
                                                                                                    GroupPermissionWizard.this.securityGroupName.getName(),
                                                                                                    GroupPermissionWizard.this.wizardPage.getGroup(),
                                                                                                    GroupPermissionWizard.this.wizardPage.getAccountId() );
            OperationExecuter operationExecuter = new OperationExecuter();
            operationExecuter.execOp( opAuthorizeSecurityGroup );

            if( opAuthorizeSecurityGroup.getException() != null ) {
              ProblemDialog.openProblem( getContainer().getShell(),
                                         Messages.getString("GroupPermissionWizard.problem_title"), //$NON-NLS-1$
                                         Messages.getString("GroupPermissionWizard.problem_description"), //$NON-NLS-1$
                                         opAuthorizeSecurityGroup.getException()
                                           .getCause() );
              throw new InvocationTargetException( opAuthorizeSecurityGroup.getException() );
            }
          }
        } );
      } catch( InvocationTargetException invTargetEx ) {
        Activator.log( "An problem occured while authorizing security group access", //$NON-NLS-1$
                       invTargetEx );
      } catch( InterruptedException interruptedEx ) {
        Activator.log( "The thread was interrupted while authorizing security group access", //$NON-NLS-1$
                       interruptedEx );
      }
      return true;
    }
    return false;

  }
}
