/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.internal.model;

import java.io.InputStream;
import java.net.URI;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.GridModelProblems;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;

/**
 * Implementation of the {@link IGridConnectionElement} interface.
 */
public class GridConnectionElement
    extends VirtualGridContainer
    implements IGridConnectionElement {
  
  /**
   * The URI of the connection element.
   */
  private URI uri;
  
  /**
   * The file store of the connection element.
   */
  private IFileStore fileStore;
  
  /**
   * The file info of the connection element. This is
   * <code>null</code> for {@link IGridConnection}s.
   */
  private IFileInfo fileInfo;
  
  /**
   * The associated resource.
   */
  private IResource resource;
  
  /**
   * Holds the error that occured last time when
   * {@link #fetchChildren(IProgressMonitor)} failed.
   */
  private CoreException fetchError;
  
  /**
   * Create a new connection element from the specified parameters.
   * 
   * @param parent The parent of the new element.
   * @param fileStore The associated file store.
   * @param fileInfo The file info that was fetched from the
   * file store. This may only be <code>null</code> for
   * {@link IGridConnection} implementations.
   */
  protected GridConnectionElement( final IGridContainer parent,
                                   final IFileStore fileStore,
                                   final IFileInfo fileInfo ) {
    super( parent, fileStore.getName() );
    this.fileStore = fileStore;
    this.fileInfo = fileInfo;
    if ( fileInfo != null ) {
      if ( fileInfo.isDirectory() ) {
        this.resource = new GridConnectionFolderAdapter( this );
      } else {
        this.resource = new GridConnectionFileAdapter( this );
      }
    } else {
      this.resource = new GridConnectionRootAdapter( ( GridConnection ) this );
    }
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
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnectionElement#createLocalCopy(org.eclipse.core.runtime.IProgressMonitor)
   */
  public IResource createLocalCopy( final IProgressMonitor monitor )
      throws CoreException {
    
    IFile result = null;
    
    IProgressMonitor localMonitor = monitor;
    if ( localMonitor == null ) {
      localMonitor = new NullProgressMonitor();
    }
    
    IGridContainer firstLocalContainer = getParent();
    while ( ( firstLocalContainer != null )
        && !firstLocalContainer.isLocal() ) {
      firstLocalContainer = firstLocalContainer.getParent();
    }
    
    if ( firstLocalContainer != null ) {
      
      IContainer container = ( IContainer ) firstLocalContainer.getResource();
      IPath sourcePath = firstLocalContainer.getPath();
      IPath targetPath = getPath();
      
      if ( sourcePath.isPrefixOf( targetPath ) ) {
        int matchingSegments = targetPath.matchingFirstSegments( sourcePath );
        IPath newPath = targetPath.removeFirstSegments( matchingSegments );
        for ( int i = 0 ; i < newPath.segmentCount() - 1 ; i++ ) {
          String segment = newPath.segment( i );
          if ( i == 0 ) {
            segment = "." + segment; //$NON-NLS-1$
          }
          container = container.getFolder( new Path( segment ) );
          if ( !container.exists() ) {
            ( ( IFolder ) container ).create( true, true, null );
          }
        }
        result = container.getFile( new Path( newPath.lastSegment() ) );
      }
      
    }
    
    if ( result != null ) {
      if ( result.exists() ) {
        result.delete( true, null );
      }
      IFile source = ( IFile ) getResource();
      InputStream contents = source.getContents();
      result.create( contents, true, localMonitor );
    }
    
    return result;
    
  }
  
  @Override
  public void delete( final IGridElement child )
      throws GridModelException {
    try {
      IFileStore fs = ( ( IGridConnectionElement ) child ).getConnectionFileStore();
      if ( fs != null ) {
        try {
          fs.delete( EFS.NONE, null );
        } catch( CoreException cExc ) {
          throw new GridModelException( GridModelProblems.ELEMENT_DELETE_FAILED, cExc );
        }
      }
      super.delete( child );
    } catch ( CoreException cExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_DELETE_FAILED, cExc );
    }
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnectionElement#getConnectionFileStore()
   */
  public IFileStore getConnectionFileStore()
      throws CoreException {
    if ( this.fileStore == null ) {
      String scheme = this.uri.getScheme();
      IFileSystem fileSystem = EFS.getFileSystem( scheme );
      this.fileStore = fileSystem.getStore( this.uri );
    }
    return this.fileStore;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnectionElement#getConnectionFileInfo()
   */
  public IFileInfo getConnectionFileInfo() {
    return this.fileInfo;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnectionElement#getError()
   */
  public String getError() {
    String error = null;
    if ( this.fetchError != null ) {
      error = this.fetchError.getLocalizedMessage();
    }
    return error;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.VirtualGridElement#getResource()
   */
  @Override
  public IResource getResource() {
    return this.resource;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.VirtualGridContainer#hasChildren()
   */
  @Override
  public boolean hasChildren() {
    return isFolder() ? super.hasChildren() : false;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnectionElement#isFolder()
   */
  public boolean isFolder() {
    return this.fileInfo != null
      ? this.fileInfo.isDirectory()
      : true;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.VirtualGridElement#isLocal()
   */
  public boolean isLocal() {
    return false;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnectionElement#isValid()
   */
  public boolean isValid() {
    return this.fetchError == null;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.VirtualGridElement#isVirtual()
   */
  @Override
  public boolean isVirtual() {
    return false;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.VirtualGridContainer#fetchChildren(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected boolean fetchChildren( final IProgressMonitor monitor ) {
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
    } catch( CoreException cExc ) {
      this.fetchError = cExc;
      Activator.logException( cExc );
    } finally {
      setProcessEvents( true );
    }
    return this.fetchError == null;
  }
  
  void addChild( final GridConnectionElement child )
      throws GridModelException {
    addElement( child );
  }
  
}
