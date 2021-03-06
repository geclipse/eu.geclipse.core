/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
 *    Ariel Garcia      - updated to new problem reporting
 *****************************************************************************/
package eu.geclipse.core.internal.model;

import java.net.URI;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridPreferences;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridRoot;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.impl.ResourceGridContainer;
import eu.geclipse.core.reporting.ProblemException;


/**
 * The hidden project is a project that is not visible in the Grid model views.
 * It implements {@link IGridPreferences} and therefore stores all global
 * preferences in a well defined structure within a project.
 */
public class HiddenProject extends ResourceGridContainer
  implements IGridProject, IGridPreferences
{

  /**
   * The name of the project.
   */
  public static final String NAME = ".geclipse"; //$NON-NLS-1$
  /**
   * Folder name of the global connections folder.
   */
  private static final String DIR_GLOBAL_CONNECTIONS = ".connections"; //$NON-NLS-1$
  /**
   * Folder name of the temporary folder.
   */
  private static final String DIR_TEMP = ".temp"; //$NON-NLS-1$
  /**
   * Name of the temporary connection.
   */
  private static final String TEMP_CONNECTION_NAME = ".tmp_connection"; //$NON-NLS-1$

  /**
   * Private constructor.
   * 
   * @param project The {@link IProject} from which to create the project.
   */
  private HiddenProject( final IProject project ) {
    super( project );
  }

  /**
   * Get the singleton instance of the hidden project.
   * 
   * @return The singleton. If not yet happened the singleton will be created.
   * @throws ProblemException If the creation of the singleton fails.
   */
  public static HiddenProject getInstance() throws ProblemException {
    IGridRoot gridRoot = GridModel.getRoot();
    HiddenProject result = ( HiddenProject )gridRoot.findChild( NAME );
    if( result == null ) {
      IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
      String projectName = NAME;
      IProject project = workspaceRoot.getProject( projectName );
      result = getInstance( project );
    }
    return result;
  }

  /**
   * Get the singleton instance of the hidden project.
   * 
   * @param project The {@link IProject} from which to create the singleton.
   * @return The hidden project.
   * @throws ProblemException If the creation of the project fails.
   */
  static HiddenProject getInstance( final IProject project )
    throws ProblemException
  {
    if ( !project.exists() ) {
      String projectName = project.getName();
      IPath projectPath = null;
      IStatus status = ResourcesPlugin.getWorkspace()
        .validateProjectLocation( project, projectPath );
      if ( status.getSeverity() != IStatus.OK ) {
        throw new ProblemException( ICoreProblems.MODEL_PREFERENCE_CREATION_FAILED,
                                    status.getMessage(),
                                    status.getException(),
                                    Activator.PLUGIN_ID );
      }
      IProjectDescription desc = project.getWorkspace()
        .newProjectDescription( projectName );
      desc.setLocation( projectPath );
      try {
        project.create( desc, null );
      } catch ( CoreException cExc ) {
        throw new ProblemException( ICoreProblems.MODEL_PREFERENCE_CREATION_FAILED,
                                    cExc,
                                    Activator.PLUGIN_ID );
      }
    }
    return new HiddenProject( project );
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#canContain(eu.geclipse.core.model.IGridElement)
   */
  @Override
  public boolean canContain( final IGridElement element ) {
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridPreferences#createGlobalConnection(java.lang.String,
   *      java.net.URI)
   */
  public void createGlobalConnection( final String name, final URI masterURI, final IProgressMonitor monitor )
    throws ProblemException
  {
    
    try {
      
      IFolder folder = getGlobalConnectionsFolder();
      
      IFileStore fileStore = EFS.getStore( masterURI );
      IFileInfo fileInfo = fileStore.fetchInfo();
      
      if ( fileInfo.isDirectory() ) {
        IFolder connection = folder.getFolder( name );
        connection.createLink( masterURI, IResource.ALLOW_MISSING_LOCAL, monitor );
      } else {
        IFile connection = folder.getFile( name );
        connection.createLink( masterURI, IResource.ALLOW_MISSING_LOCAL, monitor );
      }
      
    } catch( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                  cExc,
                                  Activator.PLUGIN_ID );
    }
    
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridPreferences#createTemporaryConnection(java.net.URI)
   */
  public IGridConnection createTemporaryConnection( final URI masterURI )
    throws ProblemException
  {
    IGridConnection result = null;
    try {
      IFolder folder = getTemporaryFolder();
      IFolder connection = folder.getFolder( TEMP_CONNECTION_NAME );
      if ( connection.exists() ) {
        connection.delete( true, null );
      }
      connection.createLink( masterURI, IResource.ALLOW_MISSING_LOCAL
                                        | IResource.REPLACE, null );
      result = ( IGridConnection )GridModel.getConnectionManager()
        .findChild( TEMP_CONNECTION_NAME );
    } catch ( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                  cExc,
                                  Activator.PLUGIN_ID );
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.impl.AbstractGridElement#getProject()
   */
  @Override
  public IGridProject getProject() {
    return this;
  }

  public IGridContainer getProjectFolder( final Class<? extends IGridElement> elementType )
  {
    return null;
  }

  public IGridContainer getProjectFolder( final IGridElement element ) {
    return null;
  }

  public String getProjectFolderID( final IGridContainer folder ) {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridProject#getVO()
   */
  public IVirtualOrganization getVO() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridProject#hasGridNature()
   */
  public boolean hasGridNature() {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridProject#isOpen()
   */
  public boolean isOpen() {
    return ( ( IProject )getResource() ).isOpen();
  }

  @Override
  protected IStatus fetchChildren( final IProgressMonitor monitor ) {
    IStatus result = Status.CANCEL_STATUS;
    if( isOpen() ) {
      result = super.fetchChildren( monitor );
    }
    return result;
  }

  protected IProject getAccessibleProject() throws ProblemException {
    IProject project = ( IProject )getResource();
    if( !project.isOpen() ) {
      try {
        project.open( null );
      } catch( CoreException exception ) {
        throw new ProblemException( "eu.geclipse.core.problem.io.openProjectFailed", exception, Activator.PLUGIN_ID );
      }
    }
    return project;
  }

  protected IFolder getGlobalConnectionsFolder() throws CoreException {
    IFolder folder = getProjectFolder( DIR_GLOBAL_CONNECTIONS );
    return folder;
  }

  public IFolder getTemporaryFolder() throws ProblemException {
    IFolder folder = getProjectFolder( DIR_TEMP );
    return folder;
  }

  /**
   * Create and/or retrieve a directory within the project.
   * 
   * @param name The name of the new directory.
   * @throws CoreException If the creation of the directory failed.
   */
  private IFolder getProjectFolder( final String name ) throws ProblemException {
    IProject project = getAccessibleProject();
    IFolder folder = project.getFolder( new Path( name ) );
    if ( !folder.exists() ) {
      try {
        folder.create( IResource.FORCE, true, null );
      } catch( CoreException exception ) {
        String msg = String.format( "Couldn't create folder %s in project %s", name, project.getName() );
        throw new ProblemException( "eu.geclipse.core.problem.io.crateFolderFailed", msg, exception, Activator.PLUGIN_ID ); //$NON-NLS-1$
      }
    }
    return folder;
  }
}
