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

/**
 * Central repository for all {@link GEclipseFileStore}s. The file stores
 * are cached in order to workaround some issues that occure when new
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
  private Hashtable< URI, GEclipseFileStore > registeredStores
    = new Hashtable< URI, GEclipseFileStore >();
  
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
    URI uri = store.toURI();
    URI key = getKey( uri );
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
  GEclipseFileStore getStore( final URI uri ) {
    URI key = getKey( uri );
    return this.registeredStores.get( key );
  }
  
  /**
   * Get a master store for the specified slave store.
   * 
   * @param fileStore The slave store for which to get a master store.
   * @return The corresponding master store or <code>null</code> if no
   * such store was yet registered.
   */
  GEclipseFileStore getStore( final IFileStore fileStore ) {
    return getStore( fileStore.toURI() );
  }
  
  /**
   * Get a unique URI as key for internal usage.
   * 
   * @param uri The URI for which to create a key.
   * @return The URI that is used to reference file stores.
   */
  private URI getKey( final URI uri ) {
    URI key = uri;
    key = FileSystemManager.createSlaveURI( uri );
    return key;
  }
  
}
