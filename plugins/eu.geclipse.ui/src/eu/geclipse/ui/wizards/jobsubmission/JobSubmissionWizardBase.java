package eu.geclipse.ui.wizards.jobsubmission;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridJobCreator;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.ui.UISolutionRegistry;
import eu.geclipse.ui.dialogs.NewProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.wizards.wizardselection.IInitalizableWizard;

public abstract class JobSubmissionWizardBase extends Wizard implements IInitalizableWizard {
  protected IGridJobCreator creator;
  protected List< IGridJobDescription > jobDescriptions;

  protected IGridJobID submitJob( final IGridJobDescription description ) throws GridModelException {
    return this.creator.submitJob( description );
  }
  
  @Override
  public boolean performFinish() {
    boolean result = true;
    if ( this.creator != null ) {
      final IWizardContainer container = getContainer();
      try {
        container.run( false, false, new IRunnableWithProgress() {
          public void run( final IProgressMonitor monitor ) throws InvocationTargetException, InterruptedException {
            monitor.beginTask( "Submitting jobs...", JobSubmissionWizardBase.this.jobDescriptions.size() );
            for ( IGridJobDescription description : JobSubmissionWizardBase.this.jobDescriptions ) {
              monitor.subTask( description.getName() );
              if ( JobSubmissionWizardBase.this.creator.canCreate( description ) ) {
                try {
                  IGridContainer parent = buildPath( description );
                  IGridJobID jobId = submitJob( description );
                  JobSubmissionWizardBase.this.creator.create( parent, jobId );
                } catch( GridModelException gmExc ) {
                  NewProblemDialog.openProblem( getShell(),
                                                "Job submission failed",
                                                "Job submission failed",
                                                gmExc,
                                                UISolutionRegistry.getRegistry() );
                } catch( CoreException cExc ) {
                  NewProblemDialog.openProblem( getShell(),
                                                "Job submission failed",
                                                "Job submission failed",
                                                cExc );
                }
              }
              monitor.worked( 1 );
            }
            monitor.done();
          }
        } );
      } catch( InvocationTargetException itExc ) {
        Throwable cause = itExc.getCause();
        String message = cause.getMessage();
        ( ( WizardPage ) container.getCurrentPage() ).setErrorMessage( message );
        result = false;
        Activator.logException( itExc );
      } catch( InterruptedException intExc ) {
        String message = intExc.getMessage();
        ( ( WizardPage ) container.getCurrentPage() ).setErrorMessage( message );
        result = false;
        Activator.logException( intExc );
      }
    }
    return result;
  }

  private IGridContainer buildPath( final IGridJobDescription description ) throws CoreException {
    IGridContainer result = null;
    IPath descPath = description.getPath().removeLastSegments( 1 );
    IPath projPath = description.getProject().getPath();
    descPath = descPath.removeFirstSegments( projPath.segmentCount() );

    IPath jobPath = projPath.append( IGridProject.DIR_JOBS );
    if ( descPath.segment( 0 ).equals( IGridProject.DIR_JOBDESCRIPTIONS ) ) {
      jobPath = jobPath.append( descPath.removeFirstSegments( 1 ) );
    }

    IWorkspaceRoot workspaceRoot
      = ( IWorkspaceRoot ) GridModel.getRoot().getResource();
    IFolder folder = workspaceRoot.getFolder( jobPath );
    createFolder( folder );
    result = ( IGridContainer ) GridModel.getRoot().findElement( folder );
    return result;
  }

  private void createFolder( final IFolder folder ) throws CoreException {
    IContainer parent = folder.getParent();
    if ( ( parent != null ) && ( parent instanceof IFolder ) ) {
      createFolder( ( IFolder ) parent );
    }
    if ( !folder.exists() ) {
      folder.create( true, true, null );
    }
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.wizards.wizardselection.IInitalizableWizard#init(java.lang.Object)
   */
  public boolean init( final Object data ) {
    boolean result = false;
    if ( data instanceof List ) {
      this.jobDescriptions = ( List<IGridJobDescription> ) data;
      result = true;
    }
    return result;
  }
}
