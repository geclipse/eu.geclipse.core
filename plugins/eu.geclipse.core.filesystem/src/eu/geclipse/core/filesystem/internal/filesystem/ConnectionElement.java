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
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

import eu.geclipse.core.filesystem.GEclipseFileSystem;
import eu.geclipse.core.filesystem.internal.Activator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.AbstractGridContainer;

/**
 * Internal implementation of the {@link IGridConnectionElement}.
 */
public class ConnectionElement
    extends AbstractGridContainer
    implements IGridConnectionElement {
  
  /**
   * The corresponding resource.
   */
  private IResource resource;
  
  /**
   * An error that may have occurred during the last fetch operation.
   */
  private Throwable fetchError;
  
  /**
   * Create a new connection element from the specified resource.
   * 
   * @param resource The resource from which to create a new element.
   * This resource has either to be a folder linked to a g-Eclipse URI
   * or has to be a child of such a folder.
   */
  public ConnectionElement( final IResource resource ) {
    Assert.isNotNull( resource );
    this.resource = resource;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#canContain(eu.geclipse.core.model.IGridElement)
   */
  @Override
  public boolean canContain( final IGridElement element ) {
    return isFolder();// && ( element instanceof IGridConnectionElement );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnectionElement#getConnectionFileInfo()
   */
  public IFileInfo getConnectionFileInfo() throws CoreException {
    return getConnectionFileStore().fetchInfo();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnectionElement#getConnectionFileStore()
   */
  public IFileStore getConnectionFileStore() throws CoreException {
    IFileStore result = null;
    IResource res = getResource();
    if ( res != null ) {
      URI uri = res.getLocationURI();
      GEclipseFileSystem fileSystem = new GEclipseFileSystem();
      result = fileSystem.getStore( uri ); 
    }
    return result;
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

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.getURI#getURI()
   */  
  public URI getURI() {
    URI result = null;
    try {
      GEclipseFileStore fileStore = ( GEclipseFileStore ) getConnectionFileStore();
      result = fileStore.getSlave().toURI();
    } catch ( CoreException cExc ) {
      Activator.logException( cExc );
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridResource#getHostName()
   */
  public String getHostName() {
    String str = null;
    URI uri = getURI();
    
    if ( null != uri ) {
      str = uri.getHost();

      if ( null == str )
        str = uri.getScheme();
    }
    
    return str;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#hasChildren()
   */
  @Override
  public boolean hasChildren() {
    return isFolder() ? super.hasChildren() : false;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnectionElement#isFolder()
   */
  public boolean isFolder() {
    return getResource().getType() == IResource.FOLDER;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#isHidden()
   */
  @Override
  public boolean isHidden() {
    return false;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#isLazy()
   */
  public boolean isLazy() {
    return true;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isLocal()
   */
  public boolean isLocal() {
    
    boolean result = false;
    
    try {
      GEclipseFileStore fileStore = ( GEclipseFileStore ) getConnectionFileStore();
      result = fileStore.isLocal();
    } catch ( CoreException cExc ) {
      Activator.logException( cExc );
    }
    
    return result;
    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnectionElement#isValid()
   */
  public boolean isValid() {
    return getError() == null;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#fetchChildren(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected IStatus fetchChildren( final IProgressMonitor monitor ) {
    
    SubMonitor sMonitor = SubMonitor.convert(
        monitor,
        String.format( Messages.getString("ConnectionElement.fetching_children_progress"), getName() ), //$NON-NLS-1$
        100
    );
    
    IStatus result = Status.CANCEL_STATUS;
    this.fetchError = null;
    
    try {
    
      IResource res = getResource();
      GEclipseFileStore fileStore = ( GEclipseFileStore ) getConnectionFileStore();
      fileStore.reset();
      fileStore.setExternalMonitor( sMonitor.newChild( 10 ) );
      res.refreshLocal( IResource.DEPTH_INFINITE, null );
      
      if ( sMonitor.isCanceled() ) {
        return Status.CANCEL_STATUS;
      }
      
      fileStore.activate();
      fileStore.setExternalMonitor( sMonitor.newChild( 90 ) );
      res.refreshLocal( IResource.DEPTH_ONE, null );
      
      result = Status.OK_STATUS;
      
    } catch ( CoreException cExc ) {
      this.fetchError = cExc;
      result = new Status( IStatus.ERROR, Activator.PLUGIN_ID, "Fetch Error", cExc );
    } finally {
      sMonitor.done();
    }
    
    return result;
    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getFileStore()
   */
  public IFileStore getFileStore() {
    IResource res = getResource();
    IPath path = res.getWorkspace().getRoot().getLocation().append( res.getFullPath() );
    IFileSystem fileSystem = EFS.getLocalFileSystem();
    IFileStore fileStore = fileSystem.getStore( path );
    return fileStore;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getName()
   */
  public String getName() {
    return getResource().getName();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getParent()
   */
  public IGridContainer getParent() {
    IGridContainer parent = null;
    IPath parentPath = getPath().removeLastSegments( 1 );
    IGridElement parentElement = GridModel.getRoot().findElement( parentPath );
    if ( parentElement instanceof IGridContainer ) {
      parent = ( IGridContainer ) parentElement;
    }
    return parent;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getPath()
   */
  public IPath getPath() {
    return getResource().getFullPath();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getResource()
   */
  public IResource getResource() {
    return this.resource;
  }

}
