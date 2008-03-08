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
import java.util.Hashtable;

import org.eclipse.core.filesystem.IFileStore;

import eu.geclipse.core.filesystem.GEclipseURI;

/**
 * Central repository for all {@link GEclipseFileStore}s. The file stores
 * are cached in order to workaround some issues that occur when new
 * file stores are created whenever {@link IFileStore#getChild(String)}
 * is called. 
 */
public class FileStoreRegistry {
  
  /**
   * The singleton instance.
   */
  private static FileStoreRegistry instance;
  
  /**
   * List of all registered file stores.
   */
  private Hashtable< String, GEclipseFileStore > registeredStores
    = new Hashtable< String, GEclipseFileStore >();
  
  /**
   * Private constructor.
   */
  private FileStoreRegistry() {
    // empty implementation
  }
  
  /**
   * Get the singleton instance of the file store registry.
   * If not yet happened this instance is created.
   * 
   * @return The singleton instance.
   */
  public static FileStoreRegistry getInstance() {
    if ( instance == null ) {
      instance = new FileStoreRegistry();
    }
    return instance;
  }
  
  /**
   * Put a store in the registry.
   * 
   * @param store The store to be cached.
   */
  void putStore( final GEclipseFileStore store ) {
    GEclipseURI uri = new GEclipseURI( store.toURI() );
    String key = getKey( uri.toSlaveURI() );
    this.registeredStores.put( key, store );
  }
  
  /**
   * Get a master store from the registry.
   * 
   * @param uri The URI of the store. This may be a master or
   * a slave URI.
   * @return The corresponding store or <code>null</code> if no such
   * store is yet registered.
   */
  GEclipseFileStore getStore( final GEclipseURI uri ) {
    String key = getKey( uri.toSlaveURI() );
    GEclipseFileStore fileStore = this.registeredStores.get( key );
    return fileStore;
  }
  
  /**
   * Get a master store for the specified slave store.
   * 
   * @param fileStore The slave store for which to get a master store.
   * @return The corresponding master store or <code>null</code> if no
   * such store was yet registered.
   */
  GEclipseFileStore getStore( final IFileStore fileStore ) {
    GEclipseURI uri = new GEclipseURI( fileStore.toURI() );
    return getStore( uri );
  }
  
  GEclipseFileStore removeStore( final GEclipseURI uri ) {
    String key = getKey( uri.toSlaveURI() );
    return this.registeredStores.remove( key );
  }
  
  GEclipseFileStore removeStore( final IFileStore fileStore ) {
    GEclipseURI uri = new GEclipseURI( fileStore.toURI() );
    return removeStore( uri );
  }
  
  private String getKey( final URI uri ) {
    
    String scheme = uri.getScheme();
    String userInfo = uri.getUserInfo();
    String host = uri.getHost();
    int port = uri.getPort();
    String path = uri.getPath();
    String query = uri.getQuery();
    String fragment = uri.getFragment();
    
    StringBuffer buffer = new StringBuffer();
    
    if ( scheme != null ) {
      buffer.append( cleanupToken( scheme ) );
    }
    
    if ( userInfo != null ) {
      buffer.append( cleanupToken( userInfo ) );
    }
    
    if ( host != null ) {
      buffer.append( host );
    }
    
    if ( port >= 0 ) {
      buffer.append( cleanupToken( String.valueOf( port ) ) );
    }
    
    if ( path != null ) {
      path = cleanupToken( path );
      path = path.replaceAll( "^/+|/+$", "" ); //$NON-NLS-1$ //$NON-NLS-2$
      buffer.append( path );
    }
    
    if ( query != null ) {
      buffer.append( cleanupToken( query ) );
    }
    
    if ( fragment != null ) {
      buffer.append( cleanupToken( fragment ) );
    }
    
    return buffer.toString();
    
  }
  
  private String cleanupToken( final String token ) {
    return token.replaceAll( "^[ \t\0]+|[ \t\0]$", "" );
  }
  
}
