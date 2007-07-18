package eu.geclipse.core.filesystem.internal.filesystem;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;

import org.eclipse.core.filesystem.IFileStore;

import eu.geclipse.core.filesystem.internal.Activator;

public class FileStoreRegistry {
  
  private static FileStoreRegistry instance;
  
  private Hashtable< URI, FileStore > registeredStores
    = new Hashtable< URI, FileStore >();
  
  private FileStoreRegistry() {
    // empty implementation
  }
  
  public static FileStoreRegistry getInstance() {
    if ( instance == null ) {
      instance = new FileStoreRegistry();
    }
    return instance;
  }
  
  void putStore( final FileStore store ) {
    URI uri = store.toURI();
    URI key = getKey( uri );
    this.registeredStores.put( key, store );
  }
  
  FileStore getStore( final URI uri ) {
    URI key = getKey( uri );
    return this.registeredStores.get( key );
  }
  
  FileStore getStore( final IFileStore fileStore ) {
    return getStore( fileStore.toURI() );
  }
  
  private URI getKey( final URI uri ) {
    URI key = uri;
    key = FileSystemManager.createSlaveURI( uri );
    return key;
  }
  
}
