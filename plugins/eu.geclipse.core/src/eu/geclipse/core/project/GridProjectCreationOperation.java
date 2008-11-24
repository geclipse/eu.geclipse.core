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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

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

/**
 * An operation that is used to create a grid project in the workspace.
 */
public class GridProjectCreationOperation {
  
  /**
   * The properties from which to create the project.
   */
  private GridProjectProperties properties;
  
  /**
   * The resulting {@link IProject}. 
   */
  private IProject gridProject;
  
  /**
   * Create a new {@link GridProjectCreationOperation} that will create a
   * project from the specified properties.
   * 
   * @param properties The properties of the newly created grid project.
   */
  public GridProjectCreationOperation( final GridProjectProperties properties ) {
    this.properties = properties;
    this.gridProject = null;
  }
  
  /**
   * Create the grid project according to the {@link GridProjectProperties} of
   * this operation.
   * 
   * @param monitor A progress monitor used to monitor the progress of this
   * operation.
   * @throws ProblemException If the creation of the project fails.
   */
  public void run( final IProgressMonitor monitor )
      throws ProblemException {

    monitor.beginTask( Messages.getString("GridProjectCreationOperation.create_progress"), 300 ); //$NON-NLS-1$

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

  /**
   * Get the resulting {@link IProject}. In case the operation's
   * {@link #run(IProgressMonitor)}-metod was not yet called or failed this
   * will return <code>null</code>.
   * 
   * @return The {@link IProject} corresponding to the created grid project.
   */
  public IProject getProject() {
    return this.gridProject;
  }

  /**
   * Create the project in the workspace and apply the specified project
   * properties.
   * 
   * @param props The properties of the newly created project.
   * @param monitor A monitor used to monitor the progress of the creation
   * operation.
   * @return The resulting {@link IProject}.
   * @throws CoreException If the project creation fails.
   */
  private IProject createProject( final GridProjectProperties props,
                                  final IProgressMonitor monitor )
      throws CoreException {

    monitor.subTask( Messages.getString("GridProjectCreationOperation.init_progress") ); //$NON-NLS-1$

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

  /**
   * Add the grid project nature to the specified project.
   * 
   * @param proj The project to which the nature should be added.
   * @param monitor A monitor used to monitor the progress.
   * @throws CoreException If an error occurs.
   */
  private void addProjectNature( final IProject proj,
                                 final IProgressMonitor monitor )
      throws CoreException {

    monitor.subTask( Messages.getString("GridProjectCreationOperation.add_nature_progress") ); //$NON-NLS-1$

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

  /**
   * Create a project folder with the specified name in the specified project.
   * 
   * @param project The project for which to create a project folder.
   * @param name The name of the newly created project folder.
   * @throws CoreException If the creation of the project folder fails.
   */
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

  /**
   * Create the project structure, i.e. the project folders according to the
   * specified project properties.
   * 
   * @param project The project for which to create the structure.
   * @param props The project properties containing the folder specifications.
   * @throws CoreException If the creation of the project structure fails.
   */
  private void createProjectStructure( final IProject project,
                                       final GridProjectProperties props )
      throws CoreException {
    Hashtable< String, String > projectFolders = props.getProjectFolders();
    for ( String label : projectFolders.values() ) {
      createProjectFolder( project, label );
    }
  }

  /**
   * Sets the projects properties, i.e. the VO and the project folders.
   * 
   * @param project The project for which to set the properties.
   * @param props The project properties containing the VO name and the project
   * folder specifications.
   * @throws CoreException If the project properties setting fails.
   */
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
                                   Messages.getString("GridProjectCreationOperation.preference_failure"), //$NON-NLS-1$
                                   bsExc );
      throw new CoreException( status );
    }

  }

}
