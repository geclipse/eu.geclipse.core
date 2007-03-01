package eu.geclipse.core.internal.model;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridMount;

public class GridMount
    extends VirtualGridContainer
    implements IGridMount {
  
  private IFileInfo fileInfo;
  
  private IFileStore fileStore;
  
  private IFile fsFile;
  
  private IResource resource;
  
  private CoreException fetchError;
  
  protected GridMount( final IGridMount parent,
                       final IFileStore fileStore ) {
    this( parent, fileStore, null );
  }
  
  protected GridMount( final IGridContainer parent,
                       final IFileStore fileStore,
                       final IFile fsFile ) {
    super( parent, constructName( fileStore, fsFile ) );
    this.fileStore = fileStore;
    this.fsFile = fsFile;
    if ( fsFile == null ) {
      this.fileInfo = fileStore.fetchInfo();
      if ( this.fileInfo.isDirectory() ) {
        this.resource = new GridMountFolderAdapter( this );
      } else {
        this.resource = new GridMountFileAdapter( this );
      }
    } else {
      this.resource = new GridMountRootAdapter( this );
    }
  }
  
  public String getError() {
    String error = null;
    if ( this.fetchError != null ) {
      error = this.fetchError.getLocalizedMessage();
    }
    return error;
  }
  
  public IFileInfo getFileInfo() {
    return this.fileInfo;
  }
  
  @Override
  public IFileStore getFileStore() {
    return this.fileStore;
  }
  
  public IFile getFsFile() {
    return this.fsFile;
  }
  
  @Override
  public IResource getResource() {
    return this.resource;
  }
  
  @Override
  public boolean hasChildren() {
    return isFolder() ? super.hasChildren() : false;
  }
  
  public boolean isFolder() {
    return this.fileInfo != null
      ? this.fileInfo.isDirectory()
      : true;
  }
  
  @Override
  public boolean isLocal() {
    boolean result = true;
    if ( isVirtual() && ( this.fileStore != null ) ) {
      String scheme = this.fileStore.getFileSystem().getScheme();
      result = scheme.equals( "file" ); //$NON-NLS-1$
    }
    return result;
  }
  
  public boolean isRoot() {
    return getResource() != null;
  }

  public boolean isValid() {
    return this.fetchError == null;
  }
  
  @Override
  public boolean isVirtual() {
    return false;
  }
  
  @Override
  protected void fetchChildren( final IProgressMonitor monitor ) {
    this.fetchError = null;
    if ( this.fileStore != null ) {
      try {
        IFileStore[] childStores
          = this.fileStore.childStores( EFS.NONE, monitor );
        for ( IFileStore childStore : childStores ) {
          IGridMount childMount = new GridMount( this, childStore );
          addElement( childMount );
        }
      } catch( CoreException cExc ) {
        this.fetchError = cExc;
        Activator.logException( cExc );
      }
    } else {
      // TODO mathias
    }
  }
  
  private static String constructName( final IFileStore fileStore,
                                       final IFile fsFile ) {
    String name = "N/A";
    if ( fsFile != null ) {
      name = fsFile.getName();
      try {
        name = name.substring( 1, name.length() - 3 );
      } catch ( StringIndexOutOfBoundsException oobExc ) {
        // Should never happen. If it happens anyhow the name
        // will be the pure name of the fsFile
      }
    } else if ( fileStore != null ) {
      name = fileStore.getName();
    }
    return name;
  }  

}
