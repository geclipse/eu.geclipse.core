package eu.geclipse.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.project.GridProjectNature;
import eu.geclipse.ui.internal.Activator;

public class GridProjectCreationOperation implements IRunnableWithProgress {
  
  private GridProjectProperties properties;
  
  private IProject gridProject;
  
  public GridProjectCreationOperation( final GridProjectProperties properties ) {
    this.properties = properties;
    this.gridProject = null;
  }

  public void run( final IProgressMonitor monitor )
      throws InvocationTargetException, InterruptedException {

    monitor.beginTask( Messages.getString("GridProjectCreationOperation.create_task"), 300 ); //$NON-NLS-1$
    
    try {
      IProject proj = createProject( this.properties, monitor );
      addProjectNature( proj, monitor );
      this.gridProject = proj;
    } catch ( CoreException cExc ) {
      eu.geclipse.ui.internal.Activator.logException( cExc );
    } finally {
      monitor.done();
    }
  
  }
  
  public IProject getProject() {
    return this.gridProject;
  }
  
  private IProject createProject( final GridProjectProperties props,
                                  final IProgressMonitor monitor )
      throws CoreException {

    monitor.subTask( Messages.getString("GridProjectCreationOperation.init_task") ); //$NON-NLS-1$
    
    String projectName = props.getProjectName();
    IPath projectPath = props.getProjectLocation();
    IProject[] referencesProjects = props.getReferencesProjects();
    
    IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
    IProject project = workspaceRoot.getProject( projectName );
    
    IStatus status = 
      ResourcesPlugin.getWorkspace().validateProjectLocation( project, projectPath );
    if ( status.getSeverity() != IStatus.OK ) {
      throw new CoreException( status );
    }
    
    IProjectDescription desc = project.getWorkspace().newProjectDescription( projectName );
    desc.setLocation(projectPath);
    if ( referencesProjects != null ) {
      desc.setReferencedProjects( referencesProjects );
    }
    project.create( desc, new SubProgressMonitor( monitor, 50 ) );
    project.open( new SubProgressMonitor( monitor, 50 ) );
    
    createProjectStructure( project );
    setProjectProperties( project, props );
    
    if ( monitor.isCanceled() ) {
      throw new OperationCanceledException();
    }
    
    return project;
    
  }
  
  private void addProjectNature( final IProject proj, final IProgressMonitor monitor ) throws CoreException {
    
    monitor.subTask( Messages.getString("GridProjectCreationOperation.nature_task") ); //$NON-NLS-1$
    
    IProjectDescription desc = proj.getDescription();
    String[] natureIDs = desc.getNatureIds();
    String gridNatureID = GridProjectNature.getID();
    
    boolean found = false;
    for ( int i = 0 ; ( i < natureIDs.length ) && ( !found ) ; i++ ) {
      if ( natureIDs[i].equals( gridNatureID ) ) {
        found = true;
      }
    }
    
    if ( !found ) {
      String[] newNatureIDs = new String[ natureIDs.length +1 ];
      System.arraycopy( natureIDs, 0, newNatureIDs, 1, natureIDs.length );
      newNatureIDs[0] = gridNatureID;
      desc.setNatureIds( newNatureIDs );
      proj.setDescription( desc, new SubProgressMonitor( monitor, 100 ) );
    }
    
    if ( monitor.isCanceled() ) {
      throw new OperationCanceledException();
    }
    
  }
  
  private void createProjectDirectory( final IProject project,
                                       final String name ) {
    IFolder folder = project.getFolder( new Path( name ) );
    if ( !folder.exists() ) {
      try {
        folder.create( IResource.FORCE, true, null );
      } catch( CoreException cExc ) {
        Activator.logException( cExc );
      }
    }
  }
  
  private void createProjectStructure( final IProject project ) {
    createProjectDirectory( project, IGridProject.DIR_MOUNTS );
    createProjectDirectory( project, IGridProject.DIR_JOBDESCRIPTIONS );
    createProjectDirectory( project, IGridProject.DIR_JOBS );
    createProjectDirectory( project, IGridProject.DIR_WORKFLOWS );
  }
  
  private void setProjectProperties( final IProject project,
                                     final GridProjectProperties props )
      throws CoreException {
    
    IVirtualOrganization projectVo = props.getProjectVo();
    String voName = projectVo.getName();
    
    IScopeContext projectScope = new ProjectScope( project );
    Preferences projectNode = projectScope.getNode( "eu.geclipse.core" ); //$NON-NLS-1$
    projectNode.put( "vo", voName ); //$NON-NLS-1$
    
    try {
      projectNode.flush();
    } catch( BackingStoreException bsExc ) {
      IStatus status = new Status( IStatus.ERROR,
                                 Activator.PLUGIN_ID,
                                 IStatus.CANCEL,
                                 Messages.getString("GridProjectCreationOperation.set_preferences_failed"), //$NON-NLS-1$
                                 bsExc );
      throw new CoreException( status );
    }
    
  }
  
}
