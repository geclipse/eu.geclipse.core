package eu.geclipse.core.filesystem.internal.filesystem;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.filesystem.FileSystem;

public class FileStore
    extends org.eclipse.core.filesystem.provider.FileStore
    implements IFileStore {
  
  private static int counter = 0;
  
  private FileSystem fileSystem;
  
  private FileStore parent;
 
  private IFileStore slave;
  
  private boolean isActive;
  
  private String[] childNames;
  
  private int count;
  
  protected FileStore( final FileSystem fileSystem,
                       final IFileStore slave ) {
    Assert.isNotNull( fileSystem );
    Assert.isNotNull( slave );
    this.fileSystem = fileSystem;
    this.parent = null;
    this.slave = slave;
    this.isActive = false;
    count = counter++;
  }

  private FileStore( final FileStore parent,
                     final IFileStore slave ) {
    Assert.isNotNull( parent );
    Assert.isNotNull( slave );
    this.fileSystem = ( FileSystem ) parent.getFileSystem();
    this.parent = parent;
    this.slave = slave;
    this.isActive = false;
    count = counter++;
  }
  
  public int getID() {
    return count;
  }
  
  public void activate() {
    setActive( true );
  }

  @Override
  public String[] childNames( final int options,
                              final IProgressMonitor monitor )
      throws CoreException {
    
    String[] result = this.childNames;
    
    if ( isActive() ) {
      result = getSlave().childNames( options, monitor );
      this.childNames = result;
      setActive( false );
    }
    
    if ( result == null ) {
      result = new String[ 0 ];
    }
    
    return result;
    
  }
  
  @Override
  public void delete( final int options,
                      final IProgressMonitor monitor )
      throws CoreException {
    getSlave().delete( options, monitor );
  }

  @Override
  public IFileInfo fetchInfo( final int options,
                              final IProgressMonitor monitor )
      throws CoreException {
    IFileInfo fileInfo = getSlave().fetchInfo( options, monitor );
    return fileInfo; 
  }

  @Override
  public IFileStore getChild( final String name ) {
    
    FileStore result = null;
    IFileStore child = getSlave().getChild( name );
    
    if ( child != null ) {
      FileStoreRegistry registry = FileStoreRegistry.getInstance();
      result = registry.getStore( child );
      if ( result == null ) {
        result = new FileStore( this, child );
        registry.putStore( result );
      }
    }
    
    return result;
    
  }
  
  @Override
  public IFileSystem getFileSystem() {
    return this.fileSystem;
  }

  @Override
  public String getName() {
    return getSlave().getName();
  }

  @Override
  public IFileStore getParent() {
    return this.parent;
  }
  
  public IFileStore getSlave() {
    return this.slave;
  }
  
  @Override
  public IFileStore mkdir( final int options,
                           final IProgressMonitor monitor)
      throws CoreException {
    FileStore result = this;
    if ( ! fetchInfo().exists() ) {
      IFileStore dir = getSlave().mkdir( options, monitor );
      FileStoreRegistry registry = FileStoreRegistry.getInstance();
      result = registry.getStore( dir );
      if ( result == null ) {
        result = new FileStore( this, dir );
        registry.putStore( result );
      }
    }
    return result;
  }

  @Override
  public InputStream openInputStream( final int options,
                                      final IProgressMonitor monitor )
      throws CoreException {
    return getSlave().openInputStream( options, monitor );
  }
  
  @Override
  public OutputStream openOutputStream( final int options,
                                        final IProgressMonitor monitor )
      throws CoreException {
    return getSlave().openOutputStream( options, monitor );
  }
  
  @Override
  public void putInfo( final IFileInfo info,
                       final int options,
                       final IProgressMonitor monitor)
      throws CoreException {
    getSlave().putInfo( info, options, monitor );
  }
  
  public void reset() {
    this.childNames = null;
  }
  
  @Override
  public URI toURI() {
    return FileSystemManager.createMasterURI( getSlave().toURI() );
  }
  
  private boolean isActive() {
    return this.isActive;
  }
  
  private void setActive( final boolean active ) {
    this.isActive = active;
  }

}
