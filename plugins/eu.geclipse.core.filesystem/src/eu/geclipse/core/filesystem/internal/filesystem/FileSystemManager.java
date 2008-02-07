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

package eu.geclipse.core.filesystem.internal.filesystem;

import java.net.URI;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.runtime.CoreException;

import eu.geclipse.core.filesystem.GEclipseFileSystem;
import eu.geclipse.core.filesystem.GEclipseURI;
import eu.geclipse.core.filesystem.internal.Activator;

/**
 * Internal class that manages file stores within the g-Eclipse
 * file system.
 */
public class FileSystemManager {
  
  /**
   * The singleton instance.
   */
  private static FileSystemManager instance;
  
  /**
   * Private constructor.
   */
  private FileSystemManager() {
    // Empty implementation
  }
  
  /**
   * Get the singleton instance of the file system manager.
   * If not yet happened this instance is created.
   * 
   * @return The singleton instance.
   */
  public static FileSystemManager getInstance() {
    if ( instance == null ) {
      instance = new FileSystemManager();
    }
    return instance;
  }

  /**
   * Get a file store for the specified URI. The file store is created
   * for the specified file system.
   * 
   * @param fileSystem The {@link GEclipseFileSystem} for which to create
   * the file store.
   * @param uri The URI from which to create a file store.
   * @return The {@link GEclipseFileStore} for the specified URI.
   */
  public GEclipseFileStore getStore( final GEclipseFileSystem fileSystem, final URI uri ) {

    GEclipseURI geclURI = new GEclipseURI( uri );
    
    FileStoreRegistry registry = FileStoreRegistry.getInstance();
    GEclipseFileStore result = registry.getStore( geclURI );
    
    if ( result == null ) {
      try {
        IFileStore slaveStore = getSlaveStore( geclURI );
        result = new GEclipseFileStore( fileSystem, slaveStore );
        registry.putStore( result );
      } catch ( CoreException cExc ) {
        Activator.logException( cExc );
      }
    }
    
    return result;
    
  }

  /**
   * Get a slave store for the specified URI.
   * 
   * @param uri The URI from which to create a slave store.
   * @return The created slave store.
   * @throws CoreException If the store could not be created.
   */
  private static IFileStore getSlaveStore( final GEclipseURI uri )
      throws CoreException {
    IFileSystem slaveSystem = getSlaveSystem( uri );
    return slaveSystem.getStore( uri.toSlaveURI() );
  }

  /**
   * Get a slave system for the specified URI.
   * 
   * @param uri The URI from which to create a slave system.
   * @return The created slave system.
   * @throws CoreException If the system could not be created.
   */
  private static IFileSystem getSlaveSystem( final GEclipseURI uri )
      throws CoreException {
    return EFS.getFileSystem( uri.getSlaveScheme() );
  }

  /**
   * Called when filestore for given uri was changed (e.g. user fetchChildren()
   * was called to force refreshing)
   * 
   * @param uri of file, which was changed
   * @param fileStore filestore for changed file
   */
  public void onFileChanged( final URI uri, final IFileStore fileStore ) {
    GEclipseURI geclURI = new GEclipseURI( uri );
    FileStoreRegistry registry = FileStoreRegistry.getInstance();
    GEclipseFileStore cachedStore = registry.getStore( geclURI );
    
    if( cachedStore != null ) {      
      registry.putStore( new GEclipseFileStore( (GEclipseFileSystem)cachedStore.getFileSystem(), fileStore ) );
    }
  }
}
