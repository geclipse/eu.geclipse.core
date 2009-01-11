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

import eu.geclipse.aws.ec2.EC2ProblemException;
import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.IEC2Problems;
import eu.geclipse.aws.ec2.op.EC2OpCreateSecurityGroup;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.ui.Messages;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * This {@link Wizard} allows to create security group within the account
 * denoted by the provided aws access id contained in the
 * {@link AWSVirtualOrganization}.
 * 
 * @author Moritz Post
 */
public class CreateSecurityGroupWizard extends Wizard {

  /**
   * The {@link AWSVirtualOrganization} holding the aws access id to identify
   * the current user.
   */
  private AWSVirtualOrganization awsVo;

  /** The wizard page providing the form elements. */
  private CreateSecurityGroupWizardPage wizardPage;

  /**
   * Creates the new wizard with the passed {@link AWSVirtualOrganization}
   * acting as the source for the aws access id.
   * 
   * @param awsVo the provider of the aws access id
   */
  public CreateSecurityGroupWizard( final AWSVirtualOrganization awsVo ) {
    this.awsVo = awsVo;
    setNeedsProgressMonitor( true );
    setHelpAvailable( false );
  }

  @Override
  public void addPages() {
    this.wizardPage = new CreateSecurityGroupWizardPage( this.awsVo );
    addPage( this.wizardPage );
  }

  @Override
  public String getWindowTitle() {
    return Messages.getString( "CreateSecurityGroupWizard.wizard_title" ); //$NON-NLS-1$
  }

  /**
   * Creates the new security group.
   */
  @Override
  public boolean performFinish() {
    if( this.wizardPage.isPageComplete() ) {
      try {

        getContainer().run( false, false, new IRunnableWithProgress() {

          public void run( final IProgressMonitor monitor )
            throws InvocationTargetException, InterruptedException
          {
            final String securityGroupName = CreateSecurityGroupWizard.this.wizardPage.getSecurityGroupName();
            final String securityGroupDescription = CreateSecurityGroupWizard.this.wizardPage.getSecurityGroupDescription();
            monitor.beginTask( Messages.getString( "CreateSecurityGroupWizard.task_title" ) //$NON-NLS-1$
                                   + securityGroupName,
                               2 );

            EC2Registry registry = EC2Registry.getRegistry();
            IEC2 ec2 = null;
            try {
              ec2 = registry.getEC2( CreateSecurityGroupWizard.this.awsVo );
            } catch( ProblemException problemEx ) {
              Activator.log( "Could not obtain the aws vo properties", problemEx ); //$NON-NLS-1$
            }
            EC2OpCreateSecurityGroup op = new EC2OpCreateSecurityGroup( ec2,
                                                                        securityGroupName,
                                                                        securityGroupDescription );

            new OperationExecuter().execOp( op );
            monitor.worked( 1 );
            if( op.getException() != null ) {
              throw new InvocationTargetException( op.getException() );
            }
            monitor.done();
          }
        } );

      } catch( Exception ex ) {
        Activator.log( "An problem occured while creating a security group", ex ); //$NON-NLS-1$
        // process any errors
        final EC2ProblemException exception = new EC2ProblemException( IEC2Problems.EC2_INTERACTION,
                                                                       ex.getCause()
                                                                         .getLocalizedMessage(),
                                                                       ex,
                                                                       Activator.PLUGIN_ID );

        Display display = PlatformUI.getWorkbench().getDisplay();
        display.asyncExec( new Runnable() {

          public void run() {
            IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
              .getActiveWorkbenchWindow();
            ProblemDialog.openProblem( workbenchWindow.getShell(),
                                       Messages.getString( "CreateSecurityGroupWizard.problem_title_ec2" ), //$NON-NLS-1$
                                       Messages.getString( "CreateSecurityGroupWizard.problem_description_ec2" ) //$NON-NLS-1$
                                           + CreateSecurityGroupWizard.this.wizardPage.getSecurityGroupName(),
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
