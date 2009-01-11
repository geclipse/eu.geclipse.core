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
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.xerox.amazonws.ec2.LaunchPermissionAttribute;
import com.xerox.amazonws.ec2.ImageListAttribute.ImageListAttributeItemType;
import com.xerox.amazonws.ec2.Jec2.ImageListAttributeOperationType;

import eu.geclipse.aws.ec2.EC2AMIImage;
import eu.geclipse.aws.ec2.EC2ProblemException;
import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.IEC2Problems;
import eu.geclipse.aws.ec2.op.EC2OpModifyImageAttribute;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * This {@link Wizard} lets the user provide an AWS account ID.
 * 
 * @author Moritz Post
 */
public class AddAttributeWizard extends Wizard {

  /** The {@link WizardPage} providing the form elements. */
  private AddAttributeWizardPage wizardPage;

  /** The parent aws vo used to map to an aws account for interaction. */
  private AWSVirtualOrganization awsVo;

  /** The AMI to modify. */
  private EC2AMIImage ec2AmiImage;

  /**
   * Creates a new {@link AddAttributeWizardPage}, storing the passed aws vo and
   * image.
   * 
   * @param awsVo the parent aws vo
   * @param image the ami to modify
   */
  public AddAttributeWizard( final AWSVirtualOrganization awsVo,
                             final EC2AMIImage image )
  {
    this.awsVo = awsVo;
    this.ec2AmiImage = image;
  }

  @Override
  public void addPages() {
    this.wizardPage = new AddAttributeWizardPage();
    addPage( this.wizardPage );
    setNeedsProgressMonitor( true );
  }

  @Override
  public String getWindowTitle() {
    return Messages.getString("AddAttributeWizard.wizard_title"); //$NON-NLS-1$
  }

  @Override
  public boolean performFinish() {
    if( this.wizardPage.isPageComplete() ) {
      try {

        getContainer().run( false, false, new IRunnableWithProgress() {

          public void run( final IProgressMonitor monitor )
            throws InvocationTargetException, InterruptedException
          {
            try {
              monitor.beginTask( Messages.getString("AddAttributeWizard.task_adding_permission"), 2 ); //$NON-NLS-1$
              EC2Registry registry = EC2Registry.getRegistry();
              IEC2 ec2;
              try {
                ec2 = registry.getEC2( AddAttributeWizard.this.awsVo );
              } catch( ProblemException problemEx ) {
                throw new InvocationTargetException( problemEx );
              }
              monitor.worked( 1 );

              LaunchPermissionAttribute attribute = new LaunchPermissionAttribute();
              attribute.addImageListAttributeItem( ImageListAttributeItemType.userId,
                                                   AddAttributeWizard.this.wizardPage.getAWSAccountId() );

              EC2OpModifyImageAttribute op = new EC2OpModifyImageAttribute( ec2,
                                                                            AddAttributeWizard.this.ec2AmiImage.getImageId(),
                                                                            attribute,
                                                                            ImageListAttributeOperationType.add );

              new OperationExecuter().execOp( op );

              if( op.getException() != null ) {
                throw new InvocationTargetException( op.getException() );
              }
            } finally {
              monitor.done();
            }
          }
        } );
      } catch( Exception ex ) {
        Activator.log( "An problem occured while adding permissions", ex ); //$NON-NLS-1$
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
                                       Messages.getString("AddAttributeWizard.problem_granting_access_permission_title"), //$NON-NLS-1$
                                       Messages.getString("AddAttributeWizard.problem_granting_access_permission_description"), //$NON-NLS-1$
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
