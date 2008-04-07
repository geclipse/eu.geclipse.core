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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobCreator;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridJobSubmissionService;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ISolution;
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
  ArrayList<String> jobNames;
  WrapperInitObject initObject;

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
      Job job = new JobSubmissionJob();
      job.setUser( true );
      Shell shell = getWizardShell();
      if( shell != null ) {
        shell.setVisible( false );
      }
      job.schedule(); // start as soon as possible
      return false;
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

  void createFolder( final IContainer folder ) throws CoreException {
    IContainer parent = folder.getParent();
    if( ( parent != null ) && ( parent instanceof IFolder ) ) {
      createFolder( ( IFolder )parent );
    }
    if( !( folder.exists() ) && ( folder instanceof IFolder ) ) {
      ( ( IFolder )folder ).create( true, true, null );
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
    if( data instanceof WrapperInitObject ) {
      this.initObject = ( WrapperInitObject )data;
      this.jobDescriptions = new ArrayList<IGridJobDescription>( this.initObject.getJobDescriptions() );
      this.jobNames = new ArrayList<String>( this.initObject.getJobNames() );
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
          String errorMessage = "Job Creator configured in class atribute for job_creator " //$NON-NLS-1$
                                + "element in eu.geclipse.ou.jobSubmissionWizzard " //$NON-NLS-1$
                                + "is not implementing IGridJobCreator interface"; //$NON-NLS-1$
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
  private class JobSubmissionJob extends Job {

    // create the service for the job submission
    final IGridJobSubmissionService service = getSubmissionService();

    JobSubmissionJob() {
      super( Messages.getString( "JobSubmissionWizardBase.jobName" ) ); //$NON-NLS-1$
    }

    /**
     * the run method for the Background job create the JobSubmissionWizard and
     * call the service
     */
    @Override
    protected IStatus run( final IProgressMonitor monitor ) {
      boolean closeWizard = true;
      SubMonitor betterMonitor = SubMonitor.convert( monitor,
                                                     JobSubmissionWizardBase.this.jobDescriptions.size() );
      try {
        /*
         * we loop over all selected jobs in the workspace yes, we can submit
         * more than one job at a time
         */
        Iterator<IGridJobDescription> iterator = JobSubmissionWizardBase.this.jobDescriptions.iterator();
        Iterator<String> namesIterator = JobSubmissionWizardBase.this.jobNames.iterator();
        while( iterator.hasNext() ) {
          testCancelled( betterMonitor );
          IGridJobDescription description = iterator.next();
          betterMonitor.setTaskName( String.format( Messages.getString( "JobSubmissionWizardBase.taskNameSubmitting" ), description.getName() ) ); //$NON-NLS-1$
          if( JobSubmissionWizardBase.this.creator.canCreate( description ) ) {
            IGridContainer parent = buildTargetFolder( description,
                                                       JobSubmissionWizardBase.this.initObject.getDestinationFolder() );
            IGridJobID jobId = null;
            if( this.service != null ) {
              jobId = this.service.submitJob( description,
                                              betterMonitor.newChild( 1 ) );
            }
            testCancelled( betterMonitor );
            JobSubmissionWizardBase.this.creator.create( parent,
                                                         jobId,
                                                         namesIterator.next() );
            // don't submit this job again during again submission after error
            iterator.remove();
            namesIterator.remove();
          }
        }
      } catch( ProblemException pExc ) {
        showProblem( pExc );
        closeWizard = false;
      } catch( CoreException cExc ) {
        showProblem( cExc );
        closeWizard = false;
      } finally {
        betterMonitor.done();
      }
      if( closeWizard ) {
        closeWizard();
      }
      return Status.OK_STATUS;
    }

    private IGridContainer buildTargetFolder( final IGridJobDescription description,
                                              final IResource folder )
      throws CoreException
    {
      IResource targetFolder = folder;
      IContainer targetBuildFolder = null;
      IGridProject project = description.getProject();
      IPath descriptionPath = description.getPath().removeLastSegments( 1 );
      IPath descriptionFolderPath = project.getProjectFolder( IGridJobDescription.class )
        .getPath();
      // if jsdl is in subfolder of JsdlContainer, then build that subfolder
      // structure also in JobContainer
      if( descriptionFolderPath.isPrefixOf( descriptionPath )
          && folder.getFullPath()
            .equals( project.getProjectFolder( IGridJob.class ).getPath() ) )
      {
        int matchingFirstSegments = descriptionPath.matchingFirstSegments( descriptionFolderPath );
        IPath appendedPath = descriptionPath.removeFirstSegments( matchingFirstSegments );
        IWorkspaceRoot workspaceRoot = ( IWorkspaceRoot )GridModel.getRoot()
          .getResource();
        // if (workspaceRoot.findContainersForLocation(
        // targetFolder.getFullPath().append( appendedPath ) ));
        if( targetFolder instanceof IProject
            && appendedPath.toString().equals( "" ) )
        {
          targetBuildFolder = ( IContainer )targetFolder;
        } else {
          targetBuildFolder = workspaceRoot.getFolder( targetFolder.getFullPath()
            .append( appendedPath ) );
        }
        createFolder( targetBuildFolder );
      }
      if( targetBuildFolder == null ) {
        targetBuildFolder = ( IContainer )targetFolder;
      }
      return ( IGridContainer )GridModel.getRoot()
        .findElement( targetBuildFolder );
    }

    private void testCancelled( final IProgressMonitor monitor ) {
      if( monitor.isCanceled() ) {
        throw new OperationCanceledException();
      }
    }

    void closeWizard() {
      IWorkbench workbench = PlatformUI.getWorkbench();
      Display display = workbench.getDisplay();
      display.asyncExec( new Runnable() {

        public void run() {
          WizardDialog dialog = ( WizardDialog )getContainer();
          dialog.close();
        }
      } );
    }

    private void showProblem( final Throwable exc ) {
      IWorkbench workbench = PlatformUI.getWorkbench();
      Display display = workbench.getDisplay();
      display.asyncExec( new Runnable() {

        public void run() {
          addShowWizardSolution( exc );
          ProblemDialog.openProblem( getShell(),
                                     Messages.getString( "JobSubmissionWizardBase.errSubmissionFailed" ), //$NON-NLS-1$
                                     null,
                                     exc );
          // if "show wizard" action wasn't called, we can definitely close the
          // wizard
          if( !JobSubmissionWizardBase.this.getContainer()
            .getShell()
            .isVisible() )
          {
            JobSubmissionWizardBase.this.getContainer().getShell().close();
          }
        }
      } );
    }
  }

  private Shell getWizardShell() {
    Shell shell = null;
    if( getContainer() != null ) {
      shell = getContainer().getShell();
    }
    return shell;
  }

  protected void addShowWizardSolution( final Throwable exc ) {
    if( exc instanceof ProblemException ) {
      ProblemException problemExc = ( ProblemException )exc;
      IProblem problem = problemExc.getProblem();
      problem.addSolution( new ISolution() {

        public String getDescription() {
          return Messages.getString( "JobSubmissionWizardBase.solutionOpenWizard" ); //$NON-NLS-1$
        }

        public String getID() {
          return null;
        }

        public boolean isActive() {
          return true;
        }

        public void solve() throws InvocationTargetException {
          JobSubmissionWizardBase.this.getContainer()
            .getShell()
            .setVisible( true );
        }
      } );
    }
  }
}
