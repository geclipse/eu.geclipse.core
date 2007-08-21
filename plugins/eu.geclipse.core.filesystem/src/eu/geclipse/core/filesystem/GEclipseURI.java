package eu.geclipse.core.filesystem;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.runtime.CoreException;

public class GEclipseURI {
  
  private static final String SCHEME = "gecl"; //$NON-NLS-1$
  
  private static final String QUERY_TOKEN_SEPARATOR = "&"; //$NON-NLS-1$
  
  private static final String QUERY_TOKEN_ASSIGN = "="; //$NON-NLS-1$
  
  private static final String QUERY_SLAVE_SCHEME_TOKEN = "geclslave"; //$NON-NLS-1$
  
  private static final String QUERY_UID_TOKEN = "gecluid"; //$NON-NLS-1$
  
  private URI masterURI;

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
  
  public static String getScheme() {
    return SCHEME;
  }
  
  public String getSlaveScheme() {
    return toSlaveURI().getScheme();
  }
  
  public URI toMasterURI() {
    return this.masterURI;
  }
  
  public URI toSlaveURI() {
    return getSlaveURI( this.masterURI );
  }
  
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
        query = new String();
      }
      
      String uid = host + String.valueOf( port );
      
      query
        += QUERY_SLAVE_SCHEME_TOKEN + QUERY_TOKEN_ASSIGN + uri.getScheme()
         + QUERY_TOKEN_SEPARATOR
         + QUERY_UID_TOKEN + QUERY_TOKEN_ASSIGN + uid;
      
      try {
        result = new URI( scheme, userInfo, host, port, path, query, fragment );
      } catch ( URISyntaxException uriExc ) {
        throw new IllegalArgumentException( "Failed to create master URI", uriExc );
      }
      
    }
    
    return result;
    
  }
  
  private static URI getSlaveURI( final URI uri ) {
    
    URI result = uri;
    
    if ( isMasterURI( uri ) ) {
      
      String scheme = null;
      String userInfo = uri.getUserInfo();
      String host = uri.getHost();
      int port = uri.getPort();
      String path = uri.getPath();
      String query = uri.getQuery();
      String fragment = uri.getFragment();
      
      String[] tokens = query.split( QUERY_TOKEN_SEPARATOR );
      query = new String();
      
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
        throw new IllegalArgumentException( "Failed to create slave URI", uriExc );
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
