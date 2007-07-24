package eu.geclipse.core.filesystem.internal.filesystem;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.runtime.CoreException;

import eu.geclipse.core.filesystem.GEclipseFileSystem;
import eu.geclipse.core.filesystem.internal.Activator;

public class FileSystemManager
    implements IFileSystemProperties {
  
  private static FileSystemManager instance;
  
  /*private List< URI > activeStores
    = new ArrayList< URI >();*/
  
  private FileSystemManager() {
    // Empty implementation
  }
  
  public static FileSystemManager getInstance() {
    if ( instance == null ) {
      instance = new FileSystemManager();
    }
    return instance;
  }
  
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
      } catch ( URISyntaxException uriExc ) {
        Activator.logException( uriExc );
      }
    }
    
    return result;
    
  }

  private static IFileStore getSlaveStore( final URI uri )
      throws CoreException, URISyntaxException {
    IFileSystem slaveSystem = getSlaveSystem( uri );
    URI slaveURI = createSlaveURI( uri );
    return slaveSystem.getStore( slaveURI );
  }

  private static IFileSystem getSlaveSystem( final URI uri )
      throws CoreException {
    String slaveScheme = getSlaveScheme( uri );
    return EFS.getFileSystem( slaveScheme );
  }

/*
  private boolean isActive( final URI key ) {
    return this.activeStores.contains( key );
  }

  private void setActive( final URI key, final boolean active ) {
    boolean isActive = isActive( key );
    if ( active && ! isActive ) {
      this.activeStores.add( key );
    } else if ( ! active && isActive ) {
      this.activeStores.remove( key );
    }
  }
*/
}
