/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium 
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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *      - Pawel Wolniewicz
 *           
 *****************************************************************************/
package eu.geclipse.ui.wizards.jobsubmission;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.jobs.GridJobCreator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobCreator;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridJobService;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ISolution;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;

public class JobCreatorSelectionWizard extends Wizard {

  protected List<IGridJobDescription> jobDescriptions;
  private List<IGridJobService> jobServices;
  private JobServiceSelectionWizardPage selectionPage;
  private FolderSelectionWizardPage folderSelection;

  public JobCreatorSelectionWizard( final List<IGridJobDescription> jobDescriptions )
  {
    this.jobDescriptions = new ArrayList<IGridJobDescription>(jobDescriptions);
    setNeedsProgressMonitor( true );
    setForcePreviousAndNextButtons( true );
    setWindowTitle( Messages.getString( "JobCreatorSelectionWizard.title" ) ); //$NON-NLS-1$
    URL imgUrl = Activator.getDefault()
      .getBundle()
      .getEntry( "icons/wizban/jobsubmit_wiz.gif" ); //$NON-NLS-1$
    setDefaultPageImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }

  public IResource getDestinationFolder() {
    return this.folderSelection.getDestinationFolder();
  }

  public IGridJobService getSubmissionService() {
    return this.selectionPage.getSubmissionService();
  }

  @Override
  public void addPages() {
    this.folderSelection = new FolderSelectionWizardPage( "name",
                                                          this.jobDescriptions.get( 0 )
                                                            .getProject(),
                                                          this.jobDescriptions );
    addPage( this.folderSelection );
    this.selectionPage = new JobServiceSelectionWizardPage( "Job Service Selection",
                                                            this.jobDescriptions );
    // start job for retrieving list of services
    jobServices = new ArrayList<IGridJobService>();
    Job job = new Job( "Retrieving list of job services" ) {

      @Override
      protected IStatus run( IProgressMonitor monitor ) {
        assert jobDescriptions != null;
        assert jobDescriptions.get( 0 ) != null;
        IGridJobService[] allServices = null;
        IGridProject project = jobDescriptions.get( 0 ).getProject();
        assert project != null;
        assert project.getVO() != null;
        try {
          allServices = project.getVO().getJobSubmissionServices( null );
          boolean valid;
          for( IGridJobService service : allServices ) {
            Iterator<IGridJobDescription> iter = jobDescriptions.iterator();
            valid = true;
            while( iter.hasNext() ) {
              IGridJobDescription jobDescription = iter.next();
              if( !service.canSubmit( jobDescription ) )
                valid = false;
            }
            if(valid==true){
              jobServices.add( service );
            }
          }
          IWorkbench workbench = PlatformUI.getWorkbench();
          Display display = workbench.getDisplay();
          display.syncExec( new Runnable() {

            public void run() {
//              List<IGridJobService> synchronizedList = Collections.synchronizedList( jobServices );
              JobCreatorSelectionWizard.this.selectionPage.setServices( jobServices ) ;
            }
          } );
        } catch( GridModelException e ) {
          // TODO pawelw - handle error
          return Status.CANCEL_STATUS;
        }
        

        return Status.OK_STATUS;
      }
    };
    job.setUser( true );
    job.schedule();
    addPage( this.selectionPage );
  }

  @Override
  public boolean canFinish() {
    return super.canFinish() & this.getSubmissionService() != null;
  }

  // @Override
  // public boolean performFinish() {
  // return false;
  // }
  //
  /*
   * This it the routine were the sub-submission is started. (non-Javadoc)
   * 
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    {
      JobSubmissionJob job = new JobSubmissionJob();
      // needs to be stored, because JobSubmissionJob cannot access GUI fields
      // from folderSelectionWizardPage
      job.setJobNames( this.folderSelection.getJobNames() );
      job.setDestinationFolder( this.folderSelection.getDestinationFolder() );
      job.setUser( true );
      Shell shell = getWizardShell();
      if( shell != null ) {
        shell.setVisible( false );
      }
      job.schedule(); // start as soon as possible
      return false;
    }
  }

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
      createFolder( parent );
    }
    if( !( folder.exists() ) && ( folder instanceof IFolder ) ) {
      ( ( IFolder )folder ).create( true, true, null );
    }
  }

  private Shell getWizardShell() {
    Shell shell = null;
    if( getContainer() != null ) {
      shell = getContainer().getShell();
    }
    return shell;
  }

  // public void changeInitData() {
  // WrapperInitObject initObject = new WrapperInitObject(this.jobDescriptions,
  // this.folderSelection.getJobNames(),
  // this.folderSelection.getDestinationFolder());
  // this.selectionPage.setInitData( initObject );
  // }
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
          JobCreatorSelectionWizard.this.getContainer()
            .getShell()
            .setVisible( true );
        }
      } );
    }
  }
  private class JobSubmissionJob extends Job {

    // create the service for the job submission
    final IGridJobService service = JobCreatorSelectionWizard.this.getSubmissionService();
    private List<String> jobNames;
    private IResource destinationFolder;

    JobSubmissionJob() {
      super( Messages.getString( "JobSubmissionWizardBase.jobName" ) ); //$NON-NLS-1$
    }

    void setJobNames( final List<String> _jobNames ) {
      this.jobNames = _jobNames;
    }

    void setDestinationFolder( final IResource folder ) {
      this.destinationFolder = folder;
    }

    /**
     * the run method for the Background job create the JobSubmissionWizard and
     * call the service
     */
    @Override
    protected IStatus run( final IProgressMonitor monitor ) {
      boolean closeWizard = true;
      GridJobCreator creator = new GridJobCreator();
      SubMonitor betterMonitor = SubMonitor.convert( monitor,
                                                     JobCreatorSelectionWizard.this.jobDescriptions.size() );
      try {
        /*
         * we loop over all selected jobs in the workspace yes, we can submit
         * more than one job at a time
         */
        Iterator<IGridJobDescription> iterator = jobDescriptions.iterator();
        Iterator<String> namesIterator = jobNames.iterator();
        while( iterator.hasNext() ) {
          testCancelled( betterMonitor );
          IGridJobDescription description = iterator.next();
          betterMonitor.setTaskName( String.format( Messages.getString( "JobSubmissionWizardBase.taskNameSubmitting" ), description.getName() ) ); //$NON-NLS-1$
          IGridContainer parent = buildTargetFolder( description,
                                                     destinationFolder );
          IGridJobID jobId = null;
          if( this.service != null ) {
            jobId = this.service.submitJob( description,
                                            betterMonitor.newChild( 1 ) );
          }
          testCancelled( betterMonitor );
          // needed to pass jobDescription to creator
          // maybe will change some day...
          creator.canCreate( description );
          creator.create( parent, jobId, service, namesIterator.next() );
          // don't submit this job again during again submission after error
          iterator.remove();
          namesIterator.remove();
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
          if( !JobCreatorSelectionWizard.this.getContainer()
            .getShell()
            .isVisible() )
          {
            JobCreatorSelectionWizard.this.getContainer().getShell().close();
          }
        }
      } );
    }
  }
}
