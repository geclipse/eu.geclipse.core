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

package eu.geclipse.core.filesystem;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.runtime.CoreException;

/**
 * The <code>GEclipseURI</code> takes care about the conversion from
 * master and slave URIs. A master URI therefore is a URI that belongs
 * to the gecl-filesystem, i.e. the g-Eclipse EFS implementation that
 * is used as a wrapper for other EFS implementations. The slave URI
 * may be any URI that can be resolved to another EFS implementation.
 */
public class GEclipseURI {
  
  private static final String SCHEME = "gecl"; //$NON-NLS-1$
  
  private static final String QUERY_TOKEN_SEPARATOR = "&"; //$NON-NLS-1$
  
  private static final String QUERY_TOKEN_ASSIGN = "="; //$NON-NLS-1$
  
  private static final String QUERY_SLAVE_SCHEME_TOKEN = "geclslave"; //$NON-NLS-1$
  
  private static final String QUERY_UID_TOKEN = "gecluid"; //$NON-NLS-1$
  
  private URI masterURI;

  /**
   * Create a new <code>GEclipseURI</code> from the specified <code>URI</code>.
   *  
   * @param uri The <code>URI</code> for which to create a new
   * <code>GEclipseURI</code>. This may be either a master or a slave
   * URI. This constructor handles both cases correctly.
   */
  public GEclipseURI( final URI uri ) {
    
    URI slaveURI = getSlaveURI( uri );
    
    // Get the unique slave URI by using the file stores toURI()-method
    try {
      IFileSystem fileSystem = EFS.getFileSystem( slaveURI.getScheme() );
      IFileStore fileStore = fileSystem.getStore( slaveURI );
      slaveURI = fileStore.toURI();
    } catch ( CoreException cExc ) {
      // Do nothing in order to make the constructor fail-safe.
      // If an exception occurs just use the original slave URI.
    }
    
    this.masterURI = getMasterURI( slaveURI );
    
  }
  
  /**
   * Get the gecl-scheme.
   * 
   * @return The gecl-scheme.
   */
  public static String getScheme() {
    return SCHEME;
  }
  
  /**
   * Get the scheme of the slave system.
   * 
   * @return The slave system's scheme.
   */
  public String getSlaveScheme() {
    return toSlaveURI().getScheme();
  }
  
  /**
   * Get the master URI corresponding to this <code>GEclipseURI</code>.
   * 
   * @return The associated master URI.
   */
  public URI toMasterURI() {
    return this.masterURI;
  }
  
  /**
   * Get the slave URI corresponding to this <code>GEclipseURI</code>.
   * 
   * @return The associated slave URI.
   */
  public URI toSlaveURI() {
    return getSlaveURI( this.masterURI );
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return toMasterURI().toString();
  }
  
  private static URI getMasterURI( final URI uri ) {
    
    URI result = uri;
    
    if ( ! isMasterURI ( uri ) ) {
      
      String scheme = SCHEME;
      String userInfo = uri.getUserInfo();
      String host = uri.getHost();
      int port = uri.getPort();
      String path = uri.getPath();
      String query = uri.getQuery();
      String fragment = uri.getFragment();
      
      if ( ! isEmptyString( query ) ) {
        query += QUERY_TOKEN_SEPARATOR;
      } else {
        query = "";
      }
      
      query += QUERY_SLAVE_SCHEME_TOKEN + QUERY_TOKEN_ASSIGN + uri.getScheme();
      
      String uid = "";
      if ( ( host != null ) && ( host.length() > 0 ) ) {
        uid += host;
      }
      if ( port != -1 ) {
        uid += String.valueOf( port );
      }
      if ( uid.length() > 0 ) {
        query += QUERY_TOKEN_SEPARATOR + QUERY_UID_TOKEN + QUERY_TOKEN_ASSIGN + uid;
      }
         
      
      try {
        result = new URI( scheme, userInfo, host, port, path, query, fragment );
      } catch ( URISyntaxException uriExc ) {
        throw new IllegalArgumentException( Messages.getString("GEclipseURI.master_creation_failed"), uriExc ); //$NON-NLS-1$
      }
      
    }
    
    return result;
    
  }
  
  private static URI getSlaveURI( final URI uri ) {
    
    URI result = uri;
    
    if ( uri != null && isMasterURI( uri ) ) {
      
      String scheme = null;
      String userInfo = uri.getUserInfo();
      String host = uri.getHost();
      int port = uri.getPort();
      String path = uri.getPath();
      String query = uri.getQuery();
      String fragment = uri.getFragment();
      
      String[] tokens = query.split( QUERY_TOKEN_SEPARATOR );
      query = "";
      
      for ( String token : tokens ) {
        
        String[] tokenParts = token.split( QUERY_TOKEN_ASSIGN );
        boolean append = true;
        
        if ( tokenParts.length == 2 ) {
          if ( tokenParts[0].equals( QUERY_SLAVE_SCHEME_TOKEN ) ) {
            scheme = tokenParts[1];
            append = false;
          } else if ( tokenParts[0].equals( QUERY_UID_TOKEN ) ) {
            append = false;
          }
        }
        
        if ( append ) {
          if ( query.length() > 0 ) {
            query += QUERY_TOKEN_SEPARATOR;
          }
          query += token;
        }
        
      }
      
      if ( isEmptyString( query ) ) {
        query = null;
      }
      
      try {
        result = new URI( scheme, userInfo, host, port, path, query, fragment );
      } catch ( URISyntaxException uriExc ) {
        throw new IllegalArgumentException( Messages.getString("GEclipseURI.slave_creation_failed"), uriExc ); //$NON-NLS-1$
      }
      
    }
    
    return result;
    
  }
  
  private static boolean isEmptyString( final String s ) {
    return ( s == null ) || ( s.length() == 0 );
  }
  
  private static boolean isMasterURI( final URI uri ) {
    return SCHEME.equals( uri.getScheme() );
  }
  
}
