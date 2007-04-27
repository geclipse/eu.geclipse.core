package eu.geclipse.ui.wizards.jobsubmission;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
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
import eu.geclipse.core.model.IGridJobSubmissionService;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.ui.UISolutionRegistry;
import eu.geclipse.ui.dialogs.NewProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.wizards.wizardselection.IInitalizableWizard;

public abstract class JobSubmissionWizardBase extends Wizard implements IInitalizableWizard, IExecutableExtension {
  protected IGridJobCreator creator;
  protected List< IGridJobDescription > jobDescriptions;

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
                  IGridJobSubmissionService service=getSubmissionService();
                  IGridJobID jobId=null;
                  if(service!=null){
                    jobId = service.submitJob( description );
                  }
                  else{
                    NewProblemDialog.openProblem( getShell(),
                                                  "Job submission failed",
                                                  "Cannot find a Job Submission Service to submit job to.",
                                                  null,
                                                  UISolutionRegistry.getRegistry() );
                  }
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
        NewProblemDialog.openProblem( getShell(),
                                      "Job submission failed",
                                      "Job submission failed",
                                      itExc.getCause() );
        result = false;
      } catch( InterruptedException intExc ) {
        NewProblemDialog.openProblem( getShell(),
                                      "Job submission interrupted",
                                      "Job submission interrupted",
                                      intExc );
        result = false;
      }
    }
    return result;
  }

  /**
   * Method called when wizzard is finished, and job should be submitted
   * Submission service is then asked to submit job using submitJob method
   * @return
   */
  protected abstract IGridJobSubmissionService getSubmissionService();  
  
  private IGridContainer buildPath( final IGridJobDescription description ) throws CoreException {
    IGridContainer result = null;
    IPath descPath = description.getPath().removeLastSegments( 1 );
    IPath projPath = description.getProject().getPath();
    descPath = descPath.removeFirstSegments( projPath.segmentCount() );

    IPath jobPath = projPath.append( IGridProject.DIR_JOBS );
    if ( IGridProject.DIR_JOBDESCRIPTIONS.equals( descPath.segment( 0 ) ) ) {
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

  public void setInitializationData(final IConfigurationElement config, final String propertyName, final Object data) throws CoreException{
    IConfigurationElement[] elements = config.getDeclaringExtension().getConfigurationElements();
    
    for(IConfigurationElement element:elements){
      if("job_creator".equals( element.getName())){
        Object obj=element.createExecutableExtension( "class" );
        if(!(obj instanceof IGridJobCreator)){
          Status status=new Status(Status.ERROR, Activator.PLUGIN_ID, Status.OK, "Job Creator configured in class atribute for job_creator element in eu.geclipse.ou.jobSubmissionWizzard is not implementing IGridJobCreator interface",null);
          throw new CoreException(status);
        }
        creator=( IGridJobCreator )obj;
      }
//      if("job_submitter".equals( element.getName())){
//        Object obj=element.createExecutableExtension( "class" );
//        if(!(obj instanceof IGridJobSubmissionService)){
//          Status status=new Status(Status.ERROR, Activator.PLUGIN_ID, Status.OK, "Job Creator configured in class atribute for job_submitter element in eu.geclipse.ou.jobSubmissionWizzard is not implementing IGridJobSubmitter interface",null);
//          throw new CoreException(status);
//        }
//        submitter=( IGridJobSubmissionService )obj;
//      }
    }
  }
}
