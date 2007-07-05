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
  
  private FileSystem fileSystem;
  
  private FileStore parent;
 
  private IFileStore slave;
  
  FileStore( final FileSystem fileSystem,
             final IFileStore slave ) {
    Assert.isNotNull( fileSystem );
    Assert.isNotNull( slave );
    this.fileSystem = fileSystem;
    this.parent = null;
    this.slave = slave;
    FileSystemManager.registerStore( this );
  }

  FileStore( final FileStore parent,
             final IFileStore slave ) {
    Assert.isNotNull( parent );
    Assert.isNotNull( slave );
    this.fileSystem = ( FileSystem ) parent.getFileSystem();
    this.parent = parent;
    this.slave = slave;
    FileSystemManager.registerStore( this );
  }

  @Override
  public String[] childNames( final int options,
                              final IProgressMonitor monitor )
      throws CoreException {
    FileSystemManager manager = FileSystemManager.getInstance( this );
    return manager.isActive( this ) ? getSlave().childNames( options, monitor ) : new String[ 0 ];
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
    //System.out.println( getName() + ".fetchInfo" );
    IFileInfo fileInfo = getSlave().fetchInfo( options, monitor );
    /*if ( fileInfo instanceof FileInfo ) {
      ( ( FileInfo ) fileInfo ).setExists( true );
    }*/
    return fileInfo; 
  }

  @Override
  public IFileStore getChild( final String name ) {
    //System.out.println( getName() + ".getChild(" + name + ")" );
    IFileStore child = getSlave().getChild( name );
    return child == null ? null : new FileStore( this, child );
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
    //System.out.println( getName() + ".getParent" );
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
      result = new FileStore( this, dir );
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

  @Override
  public URI toURI() {
    return FileSystemManager.createMasterURI( getSlave().toURI() );
  }

}
