package eu.geclipse.core.project;

import java.util.Hashtable;

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
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;

public class GridProjectCreationOperation {
  
  private GridProjectProperties properties;
  
  private IProject gridProject;
  
  public GridProjectCreationOperation( final GridProjectProperties properties ) {
    this.properties = properties;
    this.gridProject = null;
  }
  
  public void run( final IProgressMonitor monitor )
      throws ProblemException {

    monitor.beginTask( "Creating Grid project", 300 );

    try {
      IProject proj = createProject( this.properties, monitor );
      addProjectNature( proj, monitor );
      this.gridProject = proj;
    } catch ( CoreException cExc ) {
      throw new ProblemException( cExc.getStatus() );
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

    monitor.subTask( "Initializing project..." );

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
    desc.setLocation( projectPath );
    if ( referencesProjects != null ) {
      desc.setReferencedProjects( referencesProjects );
    }
    project.create( desc, new SubProgressMonitor( monitor, 50 ) );
    project.open( new SubProgressMonitor( monitor, 50 ) );

    createProjectStructure( project, props );
    setProjectProperties( project, props );

    if ( monitor.isCanceled() ) {
      throw new OperationCanceledException();
    }

    return project;

  }

  private void addProjectNature( final IProject proj,
                                 final IProgressMonitor monitor )
      throws CoreException {

    monitor.subTask( "Adding project nature..." );

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

  private void createProjectFolder( final IProject project,
                                    final String name )
      throws CoreException {
    if ( ( name != null ) && ( name.length() != 0 ) ) {
      IFolder folder = project.getFolder( new Path( name ) );
      if ( !folder.exists() ) {
        folder.create( IResource.FORCE, true, null );
      }
    }
  }

  private void createProjectStructure( final IProject project,
                                       final GridProjectProperties props )
      throws CoreException {
    Hashtable< String, String > projectFolders = props.getProjectFolders();
    for ( String label : projectFolders.values() ) {
      createProjectFolder( project, label );
    }
  }

  private void setProjectProperties( final IProject project,
                                     final GridProjectProperties props )
  throws CoreException {

    IVirtualOrganization projectVo = props.getProjectVo();
    String voName = projectVo.getName();
    Hashtable< String, String > folders = props.getProjectFolders();

    IScopeContext projectScope = new ProjectScope( project );

    try {

      Preferences projectNode = projectScope.getNode( "eu.geclipse.core" ); //$NON-NLS-1$
      projectNode.put( "vo", voName ); //$NON-NLS-1$
      projectNode.flush();

      Preferences folderNode = projectScope.getNode( "eu.geclipse.core.folders" ); //$NON-NLS-1$
      for ( String id : folders.keySet() ) {
        String label = folders.get( id );
        folderNode.put( id, label );
      }
      folderNode.flush();

    } catch( BackingStoreException bsExc ) {
      IStatus status = new Status( IStatus.ERROR,
                                   Activator.PLUGIN_ID,
                                   IStatus.CANCEL,
                                   "Unable to set project preferences",
                                   bsExc );
      throw new CoreException( status );
    }

  }

}
