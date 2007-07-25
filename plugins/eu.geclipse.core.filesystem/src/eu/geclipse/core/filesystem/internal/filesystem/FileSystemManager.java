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
import java.net.URISyntaxException;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.runtime.CoreException;

import eu.geclipse.core.filesystem.GEclipseFileSystem;
import eu.geclipse.core.filesystem.internal.Activator;

/**
 * Internal class that manages file stores within the g-Eclipse
 * file system.
 */
public class FileSystemManager
    implements IFileSystemProperties {
  
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
   * Create a g-Eclipse URI from the specified URI. If the specified URI
   * is already a g-Eclipse URI it is just returned.
   * 
   * @param slaveURI The slave URI from which to create a master URI. 
   * @return The g-Eclipse URI that corresponds to the specified
   * slave URI. 
   */
  public static URI createMasterURI( final URI slaveURI ) {
    
    URI masterURI = slaveURI;
    
    if ( ! masterURI.getScheme().equals( SCHEME ) ) {
      
      try {
    
        String slaveScheme = slaveURI.getScheme();
        IFileSystem slaveSystem = EFS.getFileSystem( slaveScheme );
        IFileStore slaveStore = slaveSystem.getStore( slaveURI );
        URI remappedURI = slaveStore.toURI();
        
        String query = remappedURI.getQuery();
        
        if ( query == null ) {
          query = ""; //$NON-NLS-1$
        }
        
        if ( query.length() > 0 ) {
          query += QUERY_SEPARATOR;
        }
        
        query += QUERY_ID + QUERY_ASSIGN + slaveScheme;
        
        try {
          masterURI = new URI(
              SCHEME,
              remappedURI.getUserInfo(),
              remappedURI.getHost(),
              remappedURI.getPort(),
              remappedURI.getPath(),
              query,
              remappedURI.getFragment()
          );
        } catch ( URISyntaxException uriExc ) {
          throw new IllegalArgumentException( uriExc );
        }
        
      } catch ( CoreException cExc ) {
        throw new IllegalArgumentException( cExc );
      }
      
    }
    
    return masterURI;
    
  }
  
  /**
   * Create a slave URI from the specified g-Eclipse URI. If the specified
   * URI is not a g-Eclipse URI it is just returned.
   * 
   * @param masterURI The g-Eclipse URI from which to create the slave URI.
   * @return The slave URI that corresponds to the specified g-Eclipse URI.
   */
  public static URI createSlaveURI( final URI masterURI ) {

    URI slaveURI = masterURI;
    if ( masterURI.getScheme().equals( SCHEME ) ) {

      String slaveQuery = ""; //$NON-NLS-1$
      String slaveScheme = ""; //$NON-NLS-1$

      String query = masterURI.getQuery();
      String[] parts = query.split( QUERY_SEPARATOR );

      for ( String part : parts ) {
        if ( ! part.startsWith( QUERY_ID + QUERY_ASSIGN ) ) {
          if ( slaveQuery.length() > 0 ) {
            slaveQuery += QUERY_SEPARATOR;
          }
          slaveQuery += part;
        } else {
          slaveScheme = part.substring( QUERY_ID.length() + QUERY_ASSIGN.length() );
        }
      }

      try {
        slaveURI = new URI(
          slaveScheme,
          masterURI.getUserInfo(),
          masterURI.getHost(),
          masterURI.getPort(),
          masterURI.getPath(),
          slaveQuery.length() == 0 ? null : slaveQuery,
              masterURI.getFragment() );
      } catch ( URISyntaxException uriExc ) {
        throw new IllegalArgumentException( uriExc );
      }
      
    }

    return slaveURI;

  }
  
  /**
   * Get the scheme of the slave file system from the specified URI.
   * 
   * @param uri The URI from which to retrieve the scheme. This may be
   * either a slave or a master URI. 
   * @return The scheme of the slave file system.
   */
  public static String getSlaveScheme( final URI uri ) {

    String scheme = ""; //$NON-NLS-1$

    String query = uri.getQuery();

    if ( query != null ) {

      String[] parts = query.split( QUERY_SEPARATOR );

      for ( String part : parts ) {
        if ( part.startsWith( QUERY_ID + QUERY_ASSIGN ) ) {
          scheme = part.substring( QUERY_ID.length() + QUERY_ASSIGN.length() );
          break;
        }
      }

    }

    return scheme;

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

    FileStoreRegistry registry = FileStoreRegistry.getInstance();
    GEclipseFileStore result = registry.getStore( uri );
    
    if ( result == null ) {
      try {
        IFileStore slaveStore = getSlaveStore( uri );
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
  private static IFileStore getSlaveStore( final URI uri )
      throws CoreException {
    IFileSystem slaveSystem = getSlaveSystem( uri );
    URI slaveURI = createSlaveURI( uri );
    return slaveSystem.getStore( slaveURI );
  }

  /**
   * Get a slave system for the specified URI.
   * 
   * @param uri The URI from which to create a slave system.
   * @return The created slave system.
   * @throws CoreException If the system could not be created.
   */
  private static IFileSystem getSlaveSystem( final URI uri )
      throws CoreException {
    String slaveScheme = getSlaveScheme( uri );
    return EFS.getFileSystem( slaveScheme );
  }

}
