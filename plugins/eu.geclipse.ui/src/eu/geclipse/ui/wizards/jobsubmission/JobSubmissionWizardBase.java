/******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     Pawel Wolniewicz - PSNC
 *     Harald Kornmayer - NEC
 *     David Johnson - ACET
 *****************************************************************************/
package eu.geclipse.ui.wizards.jobsubmission;

import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobCreator;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridJobSubmissionService;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.wizards.wizardselection.IInitalizableWizard;

/**
 * Base class for submission wizard
 */
public abstract class JobSubmissionWizardBase extends Wizard
  implements IInitalizableWizard, IExecutableExtension
{

  protected IGridJobCreator creator;
  protected List<IGridJobDescription> jobDescriptions;
  boolean finishResult;

  protected JobSubmissionWizardBase() {
    setNeedsProgressMonitor( true );
  }

  /*
   * This it the routine were the sub-submission is started. (non-Javadoc)
   * 
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    {
      // final boolean result = true;
      final JobSubmissionWizardBase base = this;
      finishResult = true;
      Job job = new Job( "Grid Job Submission" ) {

        // create the service for the job submission
        final IGridJobSubmissionService service = getSubmissionService();

        /**
         * the run method for the Background job create the JobSubmissionWizard
         * and call the service
         */
        protected IStatus run( final IProgressMonitor monitor ) {
          /*
           * we loop over all selected jobs in the workspace yes, we can submit
           * more than one job at a time
           */
          IStatus result = Status.OK_STATUS;
          for( IGridJobDescription description : JobSubmissionWizardBase.this.jobDescriptions )
          {
            if( JobSubmissionWizardBase.this.creator.canCreate( description ) )
            {
              try {
                IGridContainer parent = buildPath( description );
                IGridJobID jobId = null;
                if( this.service != null ) {
                  monitor.setTaskName( "service there" );
                  /*
                   * here we submit the job
                   */
                  jobId = service.submitJob( description, monitor );
                  IWorkbench workbench = PlatformUI.getWorkbench();
                  Display display = workbench.getDisplay();
                  display.asyncExec( new Runnable() {

                    public void run() {
                      base.dispose();
                    }
                  } );
                  monitor.setTaskName( "job submitted" );
                } else {
//                  ProblemDialog.openProblem( getShell(),
//                                             Messages.getString( "JobSubmissionWizardBase.errSubmissionFailed" ), //$NON-NLS-1$
//                                             Messages.getString( "JobSubmissionWizardBase.errUnknownSubmissionService" ), //$NON-NLS-1$
//                                             null );
                  result = new Status( Status.ERROR,
                                       Activator.getDefault().PLUGIN_ID,
                                       Status.OK,
                                       "Job not submitted",
                                       null );
//                  IWorkbench workbench = PlatformUI.getWorkbench();
//                  Display display = workbench.getDisplay();
//                  display.asyncExec( new Runnable() {
//
//                    public void run() {
//                      base.getShell().setVisible( true );
//                    }
//                  } );
                }
                // create job
                JobSubmissionWizardBase.this.creator.create( parent, jobId );
              } catch( ProblemException pExc ) {
//                ProblemDialog.openProblem( getShell(),
//                                           Messages.getString( "JobSubmissionWizardBase.errSubmissionFailed" ), //$NON-NLS-1$
//                                           null,
//                                           gmExc );
                String message=pExc.getMessage().trim()+System.getProperty( "line.separator" );;
                for(String r:pExc.getProblem().getReasons()){
                  message+=r+System.getProperty( "line.separator" ).trim();
                }
//                result = pExc.getStatus();
                result = new Status( Status.ERROR,
                                     Activator.getDefault().PLUGIN_ID,
                                     Status.OK,
                                     message,
                                     pExc.getCause() );
//                IWorkbench workbench = PlatformUI.getWorkbench();
//                Display display = workbench.getDisplay();
//                display.asyncExec( new Runnable() {
//
//                  public void run() {
//                    base.getShell().setVisible( true );
//                  }
//                } );
              } catch( CoreException cExc ) {
//                ProblemDialog.openProblem( getShell(),
//                                           Messages.getString( "JobSubmissionWizardBase.errSubmissionFailed" ), //$NON-NLS-1$
//                                           null,
//                                           cExc );
                result = new Status( Status.ERROR,
                                     Activator.getDefault().PLUGIN_ID,
                                     Status.OK,
                                     "Job not submitted",
                                     cExc );
//                IWorkbench workbench = PlatformUI.getWorkbench();
//                Display display = workbench.getDisplay();
//                display.asyncExec( new Runnable() {
//
//                  public void run() {
//                    base.getShell().setVisible( true );
//                  }
//                } );
                // /
              }
            }
            monitor.worked( 1 );
          }
          return result;
        }
      };
      job.addJobChangeListener( new JobChangeAdapter() {

        @Override
        public void done( final IJobChangeEvent event ) {
          if( event.getResult().isOK() ) {
          } else {
            JobSubmissionWizardBase.this.finishResult = false;
          }
        }
      } );
      job.setUser( true );
      job.schedule(); // start as soon as possible
      // return this.finishResult;
//      base.getShell().setVisible( false );
      return true;
    }
  }

  /**
   * Method called when wizard is finished, and job should be submitted
   * Submission service is then asked to submit job using submitJob method
   * 
   * @return
   */
  protected abstract IGridJobSubmissionService getSubmissionService();

  IGridContainer buildPath( final IGridJobDescription description )
    throws CoreException
  {
    IGridContainer result = null;
    IGridProject project = description.getProject();
    IPath projectPath = project.getPath();
    IGridContainer jobFolder = project.getProjectFolder( IGridJob.class );
    IPath jobFolderPath = jobFolder.getPath();
    if( jobFolderPath.equals( projectPath ) ) {
      result = project;
    } else {
      IPath descriptionPath = description.getPath().removeLastSegments( 1 );
      IGridContainer descriptionFolder = project.getProjectFolder( IGridJobDescription.class );
      IPath descriptionFolderPath = descriptionFolder.getPath();
      if( descriptionFolderPath.isPrefixOf( descriptionPath ) ) {
        int matchingFirstSegments = descriptionPath.matchingFirstSegments( descriptionFolderPath );
        IPath appendedPath = descriptionPath.removeFirstSegments( matchingFirstSegments );
        jobFolderPath = jobFolderPath.append( appendedPath );
        IWorkspaceRoot workspaceRoot = ( IWorkspaceRoot )GridModel.getRoot()
          .getResource();
        IFolder folder = workspaceRoot.getFolder( jobFolderPath );
        createFolder( folder );
        result = ( IGridContainer )GridModel.getRoot().findElement( folder );
      } else {
        result = jobFolder;
      }
    }
    /*
     * IPath descPath = description.getPath().removeLastSegments( 1 ); IPath
     * projPath = description.getProject().getPath(); descPath =
     * descPath.removeFirstSegments( projPath.segmentCount() ); IPath jobPath =
     * projPath.append( IGridProject.DIR_JOBS ); if (
     * IGridProject.DIR_JOBDESCRIPTIONS.equals( descPath.segment( 0 ) ) ) {
     * jobPath = jobPath.append( descPath.removeFirstSegments( 1 ) ); }
     * IWorkspaceRoot workspaceRoot = ( IWorkspaceRoot )GridModel.getRoot()
     * .getResource(); IFolder folder = workspaceRoot.getFolder( jobPath );
     * createFolder( folder ); result = ( IGridContainer
     * )GridModel.getRoot().findElement( folder );
     */
    return result;
  }

  private void createFolder( final IFolder folder ) throws CoreException {
    IContainer parent = folder.getParent();
    if( ( parent != null ) && ( parent instanceof IFolder ) ) {
      createFolder( ( IFolder )parent );
    }
    if( !folder.exists() ) {
      folder.create( true, true, null );
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.wizards.wizardselection.IInitalizableWizard#init(java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  public boolean init( final Object data ) {
    boolean result = false;
    if( data instanceof List ) {
      this.jobDescriptions = ( List<IGridJobDescription> )data;
      result = true;
    }
    return result;
  }

  public void setInitializationData( final IConfigurationElement config,
                                     final String propertyName,
                                     final Object data ) throws CoreException
  {
    IConfigurationElement[] elements = config.getDeclaringExtension()
      .getConfigurationElements();
    for( IConfigurationElement element : elements ) {
      if( "job_creator".equals( element.getName() ) ) { //$NON-NLS-1$
        Object obj = element.createExecutableExtension( "class" ); //$NON-NLS-1$
        if( !( obj instanceof IGridJobCreator ) ) {
          String errorMessage = "Job Creator configured in class atribute for job_creator "
                                + "element in eu.geclipse.ou.jobSubmissionWizzard "
                                + "is not implementing IGridJobCreator interface";
          Status status = new Status( IStatus.ERROR,
                                      Activator.PLUGIN_ID,
                                      IStatus.OK,
                                      errorMessage,
                                      null );
          throw new CoreException( status );
        }
        this.creator = ( IGridJobCreator )obj;
      }
    }
  }


}
