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

import java.net.URI;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;

/**
 * This is an internal implementation of the {@link IGridElement}
 * interface. Though it fully implements that interface it is not
 * intended to be used directly. Therefore it is marked as abstract.
 * Coders should rather use the subclasses of this class like the
 * {@link LocalFile} or {@link LocalFolder}.
 */
public abstract class LocalResource implements IGridElement {
  
  /**
   * The parent of this local resource.
   */
  private IGridContainer parent;
  
  /**
   * The corresponding <code>IResource</code>.
   */
  private IResource resource;
  
  /**
   * Construct a new new local resource with the specified parent.
   * The specified resource will be the related <code>IResource</code>
   * object of this local resource. Both the parent and the resource
   * may not be null!
   * 
   * @param parent The parent element of this local resource. 
   * @param resource The corresponding <code>IResource</code>
   */
  LocalResource( final IGridContainer parent,
                 final IResource resource ) {
    Assert.isNotNull( parent );
    Assert.isNotNull( resource );
    this.parent = parent;
    this.resource = resource;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#dispose()
   */
  public void dispose() {
    // empty implementation
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getFileStore()
   */
  public IFileStore getFileStore() {
    URI uri = getResource().getLocationURI();
    IFileStore fileStore = EFS.getLocalFileSystem().getStore( uri );
    return fileStore;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getName()
   */
  public String getName() {
    return getPath().lastSegment();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getParent()
   */
  public IGridContainer getParent() {
    return this.parent;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getPath()
   */
  public IPath getPath() {
    return this.resource.getFullPath();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getProject()
   */
  public IGridProject getProject() {
    IGridContainer par = getParent();
    while ( ( par != null ) && !( par instanceof IGridProject ) ) {
      par = par.getParent();
    }
    return ( IGridProject ) par;
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
   * @see eu.geclipse.core.model.IGridElement#isVirtual()
   */
  public boolean isVirtual() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
   */
  @SuppressWarnings("unchecked")
  public Object getAdapter( final Class adapter ) {
    Object result = null;
    if ( adapter.isAssignableFrom( IResource.class ) ) {
      result = getResource();
    }
    return result;
  }
  
}
