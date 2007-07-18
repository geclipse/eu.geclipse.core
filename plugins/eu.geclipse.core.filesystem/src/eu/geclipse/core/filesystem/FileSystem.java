package eu.geclipse.core.filesystem;

import java.net.URI;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.filesystem.IFileTree;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.filesystem.internal.Activator;
import eu.geclipse.core.filesystem.internal.filesystem.FileStore;
import eu.geclipse.core.filesystem.internal.filesystem.FileSystemManager;

public class FileSystem
    extends org.eclipse.core.filesystem.provider.FileSystem
    implements IFileSystem {
  
  private IFileSystem slave;
  
  public static URI createMasterURI( final URI slaveURI ) {
    return FileSystemManager.createMasterURI( slaveURI );
  }
  
  public static URI createSlaveURI( final URI masterURI ) {
    return FileSystemManager.createSlaveURI( masterURI );
  }
    
  @Override
  public int attributes() {
    return this.slave != null ? this.slave.attributes() : super.attributes();
  }
  
  @Override
  public boolean canDelete() {
    return this.slave != null ? this.slave.canDelete() : super.canDelete();
  }
  
  @Override
  public boolean canWrite() {
    return this.slave != null ? this.slave.canWrite() : super.canWrite();
  }
  
  @Override
  public IFileTree fetchFileTree( final IFileStore root,
                                  final IProgressMonitor monitor ) {
    
    IFileTree fileTree = null;
    
    if ( ( this.slave != null ) && ( root instanceof FileStore ) ) {
      IFileStore slaveStore = ( ( FileStore ) root ).getSlave();
      try {
        fileTree = this.slave.fetchFileTree( slaveStore, monitor );
      } catch ( CoreException cExc ) {
        Activator.logException( cExc );
      }
    }
      
    if ( fileTree == null ) {
      fileTree = super.fetchFileTree( root, monitor );
    }
    
    return fileTree;
    
  }

  @Override
  public IFileStore getStore( final URI uri ) {
    FileStore result = null;
    FileSystemManager manager = FileSystemManager.getInstance();
    result = manager.getStore( this, uri );
    this.slave = result.getSlave().getFileSystem();
    return result;
  }
  
  @Override
  public boolean isCaseSensitive() {
    return this.slave != null ? this.slave.isCaseSensitive() : super.isCaseSensitive();
  }
  
}
