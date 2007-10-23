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
package eu.geclipse.core.internal.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

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

import eu.geclipse.core.StructuralTestUpdater;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.GridModelProblems;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridPreferences;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridRoot;
import eu.geclipse.core.model.IGridTest;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.impl.ResourceGridContainer;

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
   * Folder name of the grid tests results.
   */
  private static final String DIR_GRID_TESTS = ".tests"; //$NON-NLS-1$

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
   * @throws GridModelException If the creation of the singleton fails.
   */
  public static HiddenProject getInstance() throws GridModelException {
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
   * @throws GridModelException If the creation of the project fails.
   */
  static HiddenProject getInstance( final IProject project )
    throws GridModelException
  {
    if( !project.exists() ) {
      String projectName = project.getName();
      IPath projectPath = null;
      IStatus status = ResourcesPlugin.getWorkspace()
        .validateProjectLocation( project, projectPath );
      if( status.getSeverity() != IStatus.OK ) {
        throw new GridModelException( GridModelProblems.PREFERENCE_CREATION_FAILED,
                                      status.getException(),
                                      status.getMessage() );
      }
      IProjectDescription desc = project.getWorkspace()
        .newProjectDescription( projectName );
      desc.setLocation( projectPath );
      try {
        project.create( desc, null );
      } catch( CoreException cExc ) {
        throw new GridModelException( GridModelProblems.PREFERENCE_CREATION_FAILED,
                                      cExc );
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
  public void createGlobalConnection( final String name, final URI masterURI )
    throws GridModelException
  {
    try {
      IFolder folder = getGlobalConnectionsFolder();
      IFolder connection = folder.getFolder( name );
      connection.createLink( masterURI, IResource.ALLOW_MISSING_LOCAL, null );
    } catch( CoreException cExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                    cExc );
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridPreferences#createTemporaryConnection(java.net.URI)
   */
  public IGridConnection createTemporaryConnection( final URI masterURI )
    throws GridModelException
  {
    IGridConnection result = null;
    try {
      IFolder folder = getTemporaryFolder();
      IFolder connection = folder.getFolder( TEMP_CONNECTION_NAME );
      if( connection.exists() ) {
        connection.delete( true, null );
      }
      connection.createLink( masterURI, IResource.ALLOW_MISSING_LOCAL
                                        | IResource.REPLACE, null );
      result = ( IGridConnection )GridModel.getConnectionManager()
        .findChild( TEMP_CONNECTION_NAME );
    } catch( CoreException cExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                    cExc );
    }
    return result;
  }

  public IGridTest createStructuralGridTest( final String name,
                                        final InputStream inputStream )
  {
    IGridTest result = null;
    // IGridTest result = null;
    try {
      IFolder folder = getTestFolder();
      IFile file = folder.getFile( name + ".gtdl"); //$NON-NLS-1$
      if( !file.exists() ) {
        file.create( inputStream, IResource.FORCE | IResource.REPLACE, null );
      }
      result = GridModel.getTestManager().getStructuralTest( name );
    } catch( CoreException coreExc ) {
      // TODO Auto-generated catch block
      coreExc.printStackTrace();
    }
    return result;
    // return result;
  }
  
  public IGridTest createSimpleTest( final String name, final String parentTestName, final InputStream inputStream ) {
    IGridTest result = null;
    try {
      IFolder folder = getTestFolder();
      IFile file = folder.getFile( name + ".gbdl" );
      if ( !file.exists() ) {
        file.create(  inputStream, IResource.FORCE | IResource.REPLACE, null );
      }
      result = GridModel.getTestManager().getSimpleTest( name, parentTestName );
    } catch( CoreException coreExc ) {
      coreExc.printStackTrace();
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
   * @see eu.geclipse.core.model.IGridProject#isGridProject()
   */
  public boolean isGridProject() {
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
  protected boolean fetchChildren( final IProgressMonitor monitor ) {
    boolean result = false;
    if( isOpen() ) {
      result = super.fetchChildren( monitor );
    }
    return result;
  }

  protected IProject getAccessibleProject() throws CoreException {
    IProject project = ( IProject )getResource();
    if( !project.isOpen() ) {
      project.open( null );
    }
    return project;
  }

  protected IFolder getGlobalConnectionsFolder() throws CoreException {
    IFolder folder = getProjectFolder( DIR_GLOBAL_CONNECTIONS );
    return folder;
  }

  protected IFolder getTemporaryFolder() throws CoreException {
    IFolder folder = getProjectFolder( DIR_TEMP );
    return folder;
  }

  protected IFolder getTestFolder() throws CoreException {
    IFolder result = getProjectFolder( DIR_GRID_TESTS );
    return result;
  }

  /**
   * Create and/or retrieve a directory within the project.
   * 
   * @param name The name of the new directory.
   * @throws CoreException If the creation of the directory failed.
   */
  private IFolder getProjectFolder( final String name ) throws CoreException {
    IProject project = getAccessibleProject();
    IFolder folder = project.getFolder( new Path( name ) );
    if( !folder.exists() ) {
      folder.create( IResource.FORCE, true, null );
    }
    return folder;
  }
}
