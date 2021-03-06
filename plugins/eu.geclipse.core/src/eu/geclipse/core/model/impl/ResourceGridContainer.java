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

package eu.geclipse.core.model.impl;

import java.net.URI;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.internal.model.notify.GridElementLifecycleManager;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;

/**
 * {@link IResource} based implementation of an {@link IGridContainer}. 
 */
public class ResourceGridContainer
    extends AbstractGridContainer {
  
  /**
   * The associated IResource.
   */
  private IResource resource;
  
  /**
   * Construct a new <code>ResourceGridContainer</code> from the specified
   * {@link IResource}. The constructed container is per definitionem local
   * and not virtual.
   * 
   * @param resource The associated resource of this element. This may not
   * be <code>null</code>.
   */
  protected ResourceGridContainer( final IResource resource ) {
    Assert.isNotNull( resource );
    this.resource = resource;
    fetchChildren( null );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#isLazy()
   */
  public boolean isLazy() {
    return false;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getFileStore()
   */
  public IFileStore getFileStore() {
    URI uri = this.resource.getLocationURI();
    IFileSystem fileSystem = EFS.getLocalFileSystem();
    IFileStore fileStore = fileSystem.getStore( uri );
    return fileStore;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getName()
   */
  final public String getName() {
    return this.resource.getName();
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
    return this.resource.getFullPath();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getResource()
   */
  public IResource getResource() {
    return this.resource;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isLocal()
   */
  public boolean isLocal() {
    return true;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#fetchChildren(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected IStatus fetchChildren( final IProgressMonitor monitor ) {
    
    IProgressMonitor localMonitor
      = monitor != null
      ? monitor
      : new NullProgressMonitor();

    IStatus result = Status.CANCEL_STATUS;
    
    deleteAll();
    if ( ( this.resource != null ) && ( this.resource instanceof IContainer ) ) {
      lock();
      try {
        IResource[] members = ( ( IContainer ) this.resource ).members();
        localMonitor.beginTask( Messages.getString( "AbstractGridContainer.load_progress" ), members.length ); //$NON-NLS-1$
        for ( IResource member : members ) {
          try {
            IGridElementCreator creator = findCreator( member );
            if ( creator != null ) {
              create( creator );
            }
  
            localMonitor.worked( 1 );
            if ( localMonitor.isCanceled() ) {
              break;
            }
          } catch ( Throwable throwable ) {
            Activator.logException( throwable );
          }
        }
        result = Status.OK_STATUS;
      } catch ( CoreException cExc ) {
        Activator.logException( cExc );
        result = new Status( IStatus.ERROR, Activator.PLUGIN_ID, "Fetch Error", cExc );
      } finally {
        unlock();
      }
    }

    localMonitor.done();

    return result;

  }
  
  /**
   * Find an element creator for the specified {@link IResource}.
   * Searches first all externally registered creators and afterwards
   * the internal creators.
   * 
   * @param resource The resource for which to find a creator.
   * @return The creator or <code>null</code> if no such
   * creator could be found.
   * @see GridModel#getElementCreators()
   * @see GridModel#getStandardCreators()
   */
  protected IGridElementCreator findCreator( final IResource res ) {
    return GridElementLifecycleManager.findCreator( res );
  }
  
}
