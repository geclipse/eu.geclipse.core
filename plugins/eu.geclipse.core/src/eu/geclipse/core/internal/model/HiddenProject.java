package eu.geclipse.core.internal.model;

import java.net.URI;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.GridModelProblems;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridPreferences;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridRoot;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.impl.ResourceGridContainer;

public class HiddenProject
    extends ResourceGridContainer
    implements IGridProject, IGridPreferences {
  
  public static final String NAME = ".geclipse"; //$NON-NLS-1$
  
  private static final String DIR_GLOBAL_CONNECTIONS = ".connections"; //$NON-NLS-1$
  
  private HiddenProject( final IResource resource ) {
    super(resource);
  }
  
  public static HiddenProject getInstance() throws GridModelException {
    
    IGridRoot gridRoot = GridModel.getRoot();
    HiddenProject result = ( HiddenProject ) gridRoot.findChild( NAME );
    
    if ( result == null ) {
      
      IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
      
      String projectName = NAME;
      
      IProject project = workspaceRoot.getProject( projectName );
      
      result = getInstance( project );
      
    }
    
    return result;
    
  }
  
  public static HiddenProject getInstance( final IProject project )
      throws GridModelException {
    
    if ( ! project.exists() ) {
      
      String projectName = project.getName();      
      IPath projectPath = null;
      
      IStatus status = 
        ResourcesPlugin.getWorkspace().validateProjectLocation( project, projectPath );
      if ( status.getSeverity() != IStatus.OK ) {
        throw new GridModelException( GridModelProblems.PREFERENCE_CREATION_FAILED,
                                      status.getException(),
                                      status.getMessage() );
      }
      
      IProjectDescription desc = project.getWorkspace().newProjectDescription( projectName );
      desc.setLocation(projectPath);
      
      try {
        
        project.create( desc, null );
        project.open( null );
        
        createProjectStructure( project );
        
      } catch ( CoreException cExc ) {
        throw new GridModelException( GridModelProblems.PREFERENCE_CREATION_FAILED,
                                      cExc );
      }
      
    }
    
    return new HiddenProject( project );
    
  }
  
  @Override
  public boolean canContain( final IGridElement element ) {
    return true;
  }
  
  public void createGlobalConnection( final String name, final URI masterURI )
      throws GridModelException {
    try {
      IProject project = ( IProject ) getResource();
      IFolder folder = project.getFolder( DIR_GLOBAL_CONNECTIONS );
      IFolder connection = folder.getFolder( name );
      connection.createLink( masterURI, IResource.ALLOW_MISSING_LOCAL, null );
    } catch ( CoreException cExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                    cExc ); 
    }
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#getProject()
   */
  @Override
  public IGridProject getProject() {
    return this;
  }
  
  public IVirtualOrganization getVO() {
    return null;
  }

  public boolean isGridProject() {
    return false;
  }

  public boolean isOpen() {
    return false;
  }
  
  private static void createProjectStructure( final IProject project )
      throws CoreException {
    createProjectDirectory( project, DIR_GLOBAL_CONNECTIONS );
  }
  
  private static void createProjectDirectory( final IProject project,
                                       final String name )
      throws CoreException {
    
    IFolder folder = project.getFolder( new Path( name ) );
    
    if ( !folder.exists() ) {
      folder.create( IResource.FORCE, true, null );
    }
    
  }

}
