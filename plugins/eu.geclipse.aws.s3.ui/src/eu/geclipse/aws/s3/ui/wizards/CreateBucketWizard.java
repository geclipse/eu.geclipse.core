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

package eu.geclipse.aws.s3.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;

import eu.geclipse.aws.s3.IS3Problems;
import eu.geclipse.aws.s3.S3ProblemException;
import eu.geclipse.aws.s3.internal.S3ServiceRegistry;
import eu.geclipse.aws.s3.ui.internal.Activator;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * This wizard displays a form to collect details for a new bucket. The new
 * bucket is created when the user finished the wizard.
 * 
 * @author Moritz Post
 */
public class CreateBucketWizard extends Wizard {

  /**
   * The {@link AWSVirtualOrganization} holding the aws access id to identify
   * the current user.
   */
  private AWSVirtualOrganization awsVo;

  /** The wizard page providing the form elements. */
  private CreateBucketWizardPage wizardPage;

  /**
   * Creates a new {@link WizardPage} with the aws access id in the aws vo.
   * 
   * @param awsVo the container of the aws access id
   * @param gridProject the project initiating the action
   */
  public CreateBucketWizard( final AWSVirtualOrganization awsVo ) {
    this.awsVo = awsVo;
    setNeedsProgressMonitor( true );
    setHelpAvailable( false );
  }

  @Override
  public void addPages() {
    this.wizardPage = new CreateBucketWizardPage();
    addPage( this.wizardPage );
  }

  @Override
  public String getWindowTitle() {
    return Messages.getString( "CreateBucketWizard.window_title" ); //$NON-NLS-1$
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
              final String bucketName = CreateBucketWizard.this.wizardPage.getBucketName();
              final String location = CreateBucketWizard.this.wizardPage.getLocation();

              monitor.beginTask( Messages.getString( "CreateBucketWizard.taskCreateBucket_title" ), //$NON-NLS-1$
                                 IProgressMonitor.UNKNOWN );

              S3ServiceRegistry s3ServiceRegistry = S3ServiceRegistry.getRegistry();

              S3Service service;
              service = s3ServiceRegistry.getService( CreateBucketWizard.this.awsVo.getProperties()
                .getAwsAccessId() );

              if( service != null ) {
                service.createBucket( bucketName, location );
              }

              // refresh category in VO tree
              // TODO correct error with ui threading for the update

              // CreateBucketWizard.this.gridProject.getVO()
              // .refreshResources( GridResourceCategoryFactory.getCategory(
              // IS3Categories.CATEGORY_S3_STORAGE ),
              // monitor );

            } catch( Exception ex ) {
              throw new InvocationTargetException( ex,
                                                   "Could not create bucket" ); //$NON-NLS-1$
            } finally {
              monitor.done();
            }

          }

        } );
      } catch( Exception ex ) {
        showErrorDialog( ex );
      }
      return true;
    }
    return false;
  }

  /**
   * Shows an error dialog with.
   * 
   * @param ex the reason for the error
   */
  private void showErrorDialog( final Exception ex ) {
    Activator.log( "A problem occured while creating a s3 bucket", ex ); //$NON-NLS-1$

    // process any errors
    final S3ProblemException exception = new S3ProblemException( IS3Problems.S3_BUCKET_CREATION_FAILED,
                                                                 ex.getCause()
                                                                   .getLocalizedMessage(),
                                                                 ex,
                                                                 Activator.PLUGIN_ID );

    String message = null;
    Exception displayException = null;

    if( ex.getCause() instanceof S3ServiceException ) {
      S3ServiceException s3ServiceEx = ( S3ServiceException )ex.getCause();
      message = s3ServiceEx.getS3ErrorMessage();
    } else {
      message = MessageFormat.format( Messages.getString( "CreateBucketWizard.errorCreatingBucket_message" ), //$NON-NLS-1$
                                      CreateBucketWizard.this.wizardPage.getBucketName() );
      displayException = exception;
    }
    IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
      .getActiveWorkbenchWindow();
    ProblemDialog.openProblem( workbenchWindow.getShell(),
                               Messages.getString( "CreateBucketWizard.errorCreatingBucket_title" ), //$NON-NLS-1$
                               message,
                               displayException );
  }
}
