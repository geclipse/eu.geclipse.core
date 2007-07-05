package eu.geclipse.core.filesystem.internal.filesystem;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.runtime.CoreException;

import eu.geclipse.core.filesystem.FileSystem;
import eu.geclipse.core.filesystem.internal.Activator;

public class FileSystemManager
    implements IFileSystemProperties {
  
  private static Hashtable< String, FileSystemManager > instances
    = new Hashtable< String, FileSystemManager >();
  
  private Hashtable< URI, FileStore > registeredStores
    = new Hashtable< URI, FileStore >();
  
  private List< URI > activeStores
    = new ArrayList< URI >();
  
  private FileSystemManager() {
    // Empty implementation
  }
  
  public static FileSystemManager getInstance( final FileStore masterStore ) {
    return getInstance( masterStore.toURI() );
  }
  
  public static FileSystemManager getInstance( final URI masterURI ) {
    String slaveScheme = getSlaveScheme( masterURI );
    FileSystemManager instance = instances.get( slaveScheme );
    if ( instance == null ) {
      instance = new FileSystemManager();
      instances.put( slaveScheme, instance );
    }
    return instance;
  }
  
  public static URI createMasterURI( final URI slaveURI ) {
    
    String slaveScheme = slaveURI.getScheme();
    String query = slaveURI.getQuery();
    
    if ( query == null ) {
      query = ""; //$NON-NLS-1$
    }
    
    if ( query.length() > 0 ) {
      query += QUERY_SEPARATOR;
    }
    
    query += QUERY_ID + QUERY_ASSIGN + slaveScheme;
    
    URI masterURI;
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
    
    return masterURI;
    
  }
  
  public static void registerStore( final FileStore store ) {
    FileSystemManager instance = getInstance( store );
    instance.registeredStores.put( store.toURI(), store );
  }
  
  public FileStore getStore( final FileSystem fileSystem,
                             final URI uri ) {
    
    FileStore result = this.registeredStores.get( uri );
    
    if ( result == null ) {
      try {
        IFileStore slaveStore = getSlaveStore( uri );
        result = new FileStore( fileSystem, slaveStore );
      } catch ( CoreException cExc ) {
        Activator.logException( cExc );
      } catch ( URISyntaxException uriExc ) {
        Activator.logException( uriExc );
      }
    }
    
    return result;
    
  }
  
  public boolean isActive( final IFileStore fileStore ) {
    return isActive( fileStore.toURI() );
  }
  
  public void setActive( final IFileStore fileStore,
                         final boolean active ) {
    setActive( fileStore.toURI(), active );
  }
  
  private static IFileStore getSlaveStore( final URI uri )
      throws CoreException, URISyntaxException {
    IFileSystem slaveSystem = getSlaveSystem( uri );
    URI slaveURI = getSlaveURI( uri );
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

  private static URI getSlaveURI( final URI uri )
      throws URISyntaxException {

    String slaveQuery = ""; //$NON-NLS-1$
    String slaveScheme = ""; //$NON-NLS-1$

    String query = uri.getQuery();
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

    return new URI(
        slaveScheme,
        uri.getUserInfo(),
        uri.getHost(),
        uri.getPort(),
        uri.getPath(),
        slaveQuery.length() == 0 ? null : slaveQuery,
            uri.getFragment() );

  }

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

}
