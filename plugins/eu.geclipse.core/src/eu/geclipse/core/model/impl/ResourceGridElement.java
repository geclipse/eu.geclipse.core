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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;

/**
 * {@link IResource} based implementation of an {@link IGridElement}. 
 */
public class ResourceGridElement extends AbstractGridElement {
  
  /**
   * The associated IResource.
   */
  private IResource resource;
  
  /**
   * Construct a new <code>ResourceGridElement</code> from the specified
   * {@link IResource}. The constructed element is per definitionem local
   * and not virtual.
   * 
   * @param resource The associated resource of this element. This may not
   * be <code>null</code>.
   */
  protected ResourceGridElement( final IResource resource ) {
    Assert.isNotNull( resource );
    this.resource = resource;
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
  public String getName() {
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
  
}
