package eu.geclipse.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
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
import eu.geclipse.ui.internal.Activator;

public class JobSubmissionWizard
    extends Wizard {
  
  protected List< IGridJobDescription > jobDescriptions;
  
  private final List< IGridJobCreator > jobCreators;
  
  private WizardJobCreatorSelectionPage selectionPage;
  
  public JobSubmissionWizard( final List< IGridJobDescription > jobDescriptions,
                              final List< IGridJobCreator > jobCreators ) {
    this.jobDescriptions = jobDescriptions;
    this.jobCreators = jobCreators;
    setNeedsProgressMonitor( true );
    setWindowTitle( "Submit Job" );
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/jobsubmissionwizard.gif" ); //$NON-NLS-1$
    setDefaultPageImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }
  
  public List< IGridJobCreator > getJobCreators() {
    return this.jobCreators;
  }
    
  @Override
  public void addPages() {
    this.selectionPage = new WizardJobCreatorSelectionPage( this );
    addPage( this.selectionPage );
  }

  @Override
  public boolean performFinish() {
    boolean result = true;
    final IGridJobCreator creator = this.selectionPage.getSelectedCreator();
    if ( creator != null ) {
      final IWizardContainer container = getContainer();
      try {
        container.run( false, false, new IRunnableWithProgress() {
          public void run( final IProgressMonitor monitor ) throws InvocationTargetException, InterruptedException {
            monitor.beginTask( "Submitting jobs...",
                               JobSubmissionWizard.this.jobDescriptions.size() );
            for ( IGridJobDescription description : JobSubmissionWizard.this.jobDescriptions ) {
              monitor.subTask( description.getName() );
              if ( creator.canCreate( description ) ) {
                try {
                  IGridContainer parent = buildPath( description );
                  IGridJobID jobId = creator.submitJob( description );
//                   jobId = creator.submitJob( parent , url);
//                  IGridJob job = ( IGridJob ) creator.create( parent, jobId);
                  creator.create( parent, jobId);
//                  if ( job != null ) {
//                    job.submit();
//                  }
                } catch( GridModelException gmExc ) {
                  throw new InvocationTargetException( gmExc );
                } catch( CoreException cExc ) {
                  throw new InvocationTargetException( cExc );
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
  
  protected IGridContainer buildPath( final IGridJobDescription description ) throws CoreException {
    
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
  
}
