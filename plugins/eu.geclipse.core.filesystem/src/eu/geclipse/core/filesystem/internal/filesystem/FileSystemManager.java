package eu.geclipse.core.filesystem.internal.filesystem;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.runtime.CoreException;

import eu.geclipse.core.filesystem.FileSystem;
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
    
      String slaveScheme = slaveURI.getScheme();
      String query = slaveURI.getQuery();
      
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
            slaveURI.getUserInfo(),
            slaveURI.getHost(),
            slaveURI.getPort(),
            slaveURI.getPath(),
            query,
            slaveURI.getFragment()
        );
      } catch ( URISyntaxException uriExc ) {
        throw new IllegalArgumentException( uriExc );
      }
      
    }
    
    return masterURI;
    
  }
  
  public static URI createSlaveURI( final URI masterURI )
      throws URISyntaxException {

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

      slaveURI = new URI(
          slaveScheme,
          masterURI.getUserInfo(),
          masterURI.getHost(),
          masterURI.getPort(),
          masterURI.getPath(),
          slaveQuery.length() == 0 ? null : slaveQuery,
              masterURI.getFragment() );

    }

    return slaveURI;

  }

  public FileStore getStore( final FileSystem fileSystem, final URI uri ) {

    FileStoreRegistry registry = FileStoreRegistry.getInstance();
    FileStore result = registry.getStore( uri );
    
    if ( result == null ) {
      try {
        IFileStore slaveStore = getSlaveStore( uri );
        result = new FileStore( fileSystem, slaveStore );
        registry.putStore( result );
      } catch ( CoreException cExc ) {
        Activator.logException( cExc );
      } catch ( URISyntaxException uriExc ) {
        Activator.logException( uriExc );
      }
    }
    
    return result;
    
    /*
    FileStore result = null;
    
    try {
      
      IFileStore slaveStore = getSlaveStore( uri );
      URI key = createMasterURI( slaveStore.toURI() );
      result = this.registeredStores.get( key );
    
      if ( result == null ) {
        result = new FileStore( fileSystem, slaveStore );
      }
      
    } catch ( CoreException cExc ) {
      Activator.logException( cExc );
    } catch ( URISyntaxException uriExc ) {
      Activator.logException( uriExc );
    }
        
    return result;
    */
  }
/*
  public boolean isActive( final IFileStore fileStore ) {
    return isActive( fileStore.toURI() );
  }
  
  public void setActive( final IFileStore fileStore,
                         final boolean active ) {
    setActive( fileStore.toURI(), active );
  }
*/
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

  private static String getSlaveScheme( final URI uri ) {

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
