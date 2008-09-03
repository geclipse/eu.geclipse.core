/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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

package eu.geclipse.ui.wizards;

import java.lang.reflect.InvocationTargetException;
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
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

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
    
    createProjectStructure( project, props );
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
  
  private void createProjectFolder( final IProject project,
                                    final String name ) {
    if ( ( name != null ) && ( name.length() != 0 ) ) {
      IFolder folder = project.getFolder( new Path( name ) );
      if ( !folder.exists() ) {
        try {
          folder.create( IResource.FORCE, true, null );
        } catch( CoreException cExc ) {
          Activator.logException( cExc );
        }
      }
    }
  }
  
  private void createProjectStructure( final IProject project,
                                       final GridProjectProperties props ) {
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
                                 Messages.getString("GridProjectCreationOperation.set_preferences_failed"), //$NON-NLS-1$
                                 bsExc );
      throw new CoreException( status );
    }
    
  }
  
}
