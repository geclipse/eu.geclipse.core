package eu.geclipse.core.filesystem.internal.filesystem;

import java.net.URI;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import eu.geclipse.core.filesystem.FileSystem;
import eu.geclipse.core.filesystem.internal.Activator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.AbstractGridContainer;

public class ConnectionElement
    extends AbstractGridContainer
    implements IGridConnectionElement {
  
  private IResource resource;
  
  private Throwable fetchError;
  
  public ConnectionElement( final IResource resource ) {
    Assert.isNotNull( resource );
    this.resource = resource;
  }
  
  @Override
  public boolean canContain( final IGridElement element ) {
    return isFolder() && ( element instanceof IGridConnectionElement );
  }

  public IResource createLocalCopy( final IProgressMonitor monitor )
      throws CoreException {
    // TODO Auto-generated method stub
    return null;
  }
  
  public IFileInfo getConnectionFileInfo() throws CoreException {
    return getConnectionFileStore().fetchInfo();
  }

  public IFileStore getConnectionFileStore() throws CoreException {
    IResource res= getResource();
    URI uri = res.getLocationURI();
    FileSystem fileSystem = new FileSystem();
    return fileSystem.getStore( uri );
  }
  
  public String getError() {
    String error = null;
    if ( this.fetchError != null ) {
      error = this.fetchError.getLocalizedMessage();
    }
    return error;
  }
  
  @Override
  public boolean hasChildren() {
    return isFolder() ? super.hasChildren() : false;
  }

  public boolean isFolder() {
    return getResource().getType() == IResource.FOLDER;
  }
  
  @Override
  public boolean isHidden() {
    return false;
  }
  
  public boolean isLazy() {
    return true;
  }
  
  public boolean isLocal() {
    return false;
  }

  public boolean isValid() {
    return getError() == null;
  }
  
  @Override
  protected synchronized boolean fetchChildren( final IProgressMonitor monitor ) {
    
    ConnectionElementFetcher job = new ConnectionElementFetcher( this );
      
    job.schedule();
    
    try {
      job.join();
    } catch ( InterruptedException intExc ) {
      Activator.logException( intExc );
    }
  
    IStatus result = job.getResult();
    if ( ! result.isOK() ) {
      Activator.logStatus( result );
    }
    
    return result.isOK();
    
  }

  public IFileStore getFileStore() {
    URI uri = getResource().getLocationURI();
    IFileSystem fileSystem = EFS.getLocalFileSystem();
    IFileStore fileStore = fileSystem.getStore( uri );
    return fileStore;
  }

  public String getName() {
    return getResource().getName();
  }

  public IGridContainer getParent() {
    IGridContainer parent = null;
    IPath parentPath = getPath().removeLastSegments( 1 );
    IGridElement parentElement = GridModel.getRoot().findElement( parentPath );
    if ( parentElement instanceof IGridContainer ) {
      parent = ( IGridContainer ) parentElement;
    }
    return parent;
  }

  public IPath getPath() {
    return getResource().getFullPath();
  }

  public IResource getResource() {
    return this.resource;
  }

  /*
  private IGridContainer parent;
  
  private IResource resource;
  
  private CoreException fetchError;
  
  private IFileStore fileStore;
  
  private IFileInfo fileInfo;
  
  public ConnectionElement( final IGridContainer parent,
                            final IResource resource ) {
    Assert.isNotNull( parent );
    Assert.isNotNull( resource );
    this.parent = parent;
    this.resource = resource;
  }
  
  @Override
  public boolean canContain( final IGridElement element ) {
    return
      isFolder()
      && !element.isVirtual()
      && !( element instanceof IGridConnection );
  }
  
  public IResource createLocalCopy( final IProgressMonitor monitor )
      throws CoreException {
    // TODO Auto-generated method stub
    return null;
  }
  
  public IFileInfo getConnectionFileInfo() throws CoreException {
    if ( this.fileInfo == null ) {
      IFileStore store = getConnectionFileStore();
      this.fileInfo = store.fetchInfo();
    }
    return this.fileInfo;
  }

  public IFileStore getConnectionFileStore() throws CoreException {
    if ( this.fileStore == null ) {
      IResource res = getResource();
      URI uri = res.getLocationURI();
      String scheme = uri.getScheme();
      IFileSystem fileSystem = EFS.getFileSystem( scheme );
      this.fileStore = fileSystem.getStore( uri );
    }
    return this.fileStore;
  }
  
  public String getError() {
    String error = null;
    if ( this.fetchError != null ) {
      error = this.fetchError.getLocalizedMessage();
    }
    return error;
  }

  public IFileStore getFileStore() {
    return getParent().getFileStore().getChild( getName() );
  }

  public String getName() {
    return getResource().getName();
  }

  public IGridContainer getParent() {
    return this.parent;
  }

  public IPath getPath() {
    return getResource().getFullPath();
  }

  public IResource getResource() {
    return this.resource;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.VirtualGridContainer#hasChildren()
   */
/*  @Override
  public boolean hasChildren() {
    return isFolder() ? super.hasChildren() : false;
  }
  
  public boolean isFolder() {
    return getResource().getType() == IResource.FOLDER;
  }
  
  public boolean isLazy() {
    return true;
  }

  public boolean isLocal() {
    return false;
  }

  public boolean isValid() {
    return getError() == null;
  }
  
  @Override
  protected boolean fetchChildren( final IProgressMonitor monitor )
      throws GridModelException {

    this.fetchError = null;
    IFileStore store = null;
    FileSystemManager manager = null;

    setProcessEvents( false );
    
    try {
      
      store = getConnectionFileStore();
      manager = FileSystemManager.getInstance();
      manager.setActive( store, true );
    
      IResource res = getResource();
      
      if ( res instanceof IContainer ) {
        res.refreshLocal( IResource.DEPTH_ONE, monitor );
        /*IContainer container = ( IContainer ) res;
        IResource[] members = container.members();
        if ( members != null ) {
          for ( IResource member : members ) {
            ConnectionElement child = new ConnectionElement( this, member );
            addElement( child );
          }
        }*/
/*      }
      
    } catch ( GridModelException gmExc ) {
      throw gmExc;
    } catch ( CoreException cExc ) {
      throw new GridModelException( GridModelProblems.FETCH_CHILDREN_FAILED, cExc );
    } finally {
      setProcessEvents( true );
      if ( ( store != null ) && ( manager != null ) ) {
        manager.setActive( store, false );
      }
    }

    return this.fetchError == null;

  }
*/
}
