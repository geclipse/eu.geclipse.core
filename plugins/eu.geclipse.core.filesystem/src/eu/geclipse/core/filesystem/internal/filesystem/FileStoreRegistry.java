package eu.geclipse.core.filesystem.internal.filesystem;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;

import org.eclipse.core.filesystem.IFileStore;

import eu.geclipse.core.filesystem.internal.Activator;

public class FileStoreRegistry {
  
  private static FileStoreRegistry instance;
  
  private Hashtable< URI, GEclipseFileStore > registeredStores
    = new Hashtable< URI, GEclipseFileStore >();
  
  private FileStoreRegistry() {
    // empty implementation
  }
  
  public static FileStoreRegistry getInstance() {
    if ( instance == null ) {
      instance = new FileStoreRegistry();
    }
    return instance;
  }
  
  void putStore( final GEclipseFileStore store ) {
    URI uri = store.toURI();
    URI key = getKey( uri );
    this.registeredStores.put( key, store );
  }
  
  GEclipseFileStore getStore( final URI uri ) {
    URI key = getKey( uri );
    return this.registeredStores.get( key );
  }
  
  GEclipseFileStore getStore( final IFileStore fileStore ) {
    return getStore( fileStore.toURI() );
  }
  
  private URI getKey( final URI uri ) {
    URI key = uri;
    key = FileSystemManager.createSlaveURI( uri );
    return key;
  }
  
}
