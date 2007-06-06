package eu.geclipse.core.internal.model;

import java.net.URI;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.GridModelProblems;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.core.model.impl.AbstractGridContainer;


public class TemporaryGridConnection
    extends AbstractGridContainer
    implements IGridConnection {
  
  private IFileStore fileStore;
  
  private CoreException fetchError;
  
  public TemporaryGridConnection( final IFileStore fileStore ) {
    this.fileStore = fileStore;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.VirtualGridContainer#canContain(eu.geclipse.core.model.IGridElement)
   */
  @Override
  public boolean canContain( final IGridElement element ) {
    return
      isFolder()
      && !element.isVirtual()
      && !( element instanceof IGridConnection );
  }

  public IResource createLocalCopy( final IProgressMonitor monitor )
      throws CoreException {
    // Not implemented for this class
    return null;
  }

  public IFileInfo getConnectionFileInfo() {
    return null;
  }

  public IFileStore getConnectionFileStore()
      throws CoreException {
    return this.fileStore;
  }

  public String getError() {
    String error = null;
    if ( this.fetchError != null ) {
      error = this.fetchError.getLocalizedMessage();
    }
    return error;
  }
  
  public URI getURI() {
    return this.fileStore.toURI();
  }

  public boolean isFolder() {
    return true;
  }

  public boolean isValid() {
    return this.fetchError == null;
  }

  public IFileStore getFileStore() {
    return this.fileStore;
  }

  public String getName() {
    return this.fileStore.getName();
  }

  public IGridContainer getParent() {
    return null;
  }

  public IPath getPath() {
    return null;
  }

  public IResource getResource() {
    return null;
  }

  public boolean isLocal() {
    return false;
  }

  public boolean isLazy() {
    return true;
  }

  public IGridElementManager getManager() {
    return null;
  }

  public void load()
      throws GridModelException {
    // Not implemented for this class
  }

  public void save()
      throws GridModelException {
    // Not implemented for this class
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.VirtualGridContainer#fetchChildren(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected boolean fetchChildren( final IProgressMonitor monitor )
      throws GridModelException {
    
    this.fetchError = null;
    
    try {
      setProcessEvents( false );
      IFileStore fs = getConnectionFileStore();
      if ( fs != null ) {
        IFileStore[] childStores
          = fs.childStores( EFS.NONE, monitor );
        for ( IFileStore childStore : childStores ) {
          IFileInfo fi = childStore.fetchInfo();
          IGridConnectionElement element
            = new GridConnectionElement( this, childStore, fi );
          addElement( element );
        }
      } else {
        // TODO mathias
      }
    } catch ( GridModelException gmExc ) {
      throw gmExc;
    } catch ( CoreException cExc ) {
      throw new GridModelException( GridModelProblems.FETCH_CHILDREN_FAILED, cExc, getName() );
    } finally {
      setProcessEvents( true );
    }
    
    return this.fetchError == null;
    
  }
  
}
