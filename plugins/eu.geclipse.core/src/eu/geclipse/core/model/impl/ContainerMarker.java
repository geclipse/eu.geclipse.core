/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.model.IGridContainer;

/**
 * Child of container that may be used to mark the container
 * as invalid or empty.
 */
public class ContainerMarker extends AbstractGridElement {
    
  /**
   * The type of this marker. May have influence how the marker
   * will be presented in the UI,
   */
  public static enum MarkerType {
    
    /**
     * Tag for an info marker.
     */
    INFO,
    
    /**
     * Tag for an error marker. 
     */
    ERROR
    
  }
  
  private IGridContainer container;
  
  private MarkerType type;
  
  private String message;
  
  /**
   * Create a new container marker of the specified type.
   * 
   * @param container The container, i.e. the parent.
   * @param type The type of this marker, i.e. either
   * <code>INFO</code> or <code>error</code>.
   * @param message Message to be displayed.
   */
  public ContainerMarker( final IGridContainer container,
                          final MarkerType type,
                          final String message ) {
    this.container = container;
    this.type = type;
    this.message = message;
  }
  
  public static ContainerMarker getEmptyFolderMarker( final IGridContainer container ) {
    return new ContainerMarker( container, MarkerType.INFO, "Folder is empty" );
  }
  
  public static ContainerMarker getErrorMarker( final IGridContainer container, final Throwable t ) {
    return new ContainerMarker( container, MarkerType.ERROR, t.getLocalizedMessage() );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getFileStore()
   */
  public IFileStore getFileStore() {
    return null;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getName()
   */
  public String getName() {
    return this.message;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getParent()
   */
  public IGridContainer getParent() {
    return this.container;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getPath()
   */
  public IPath getPath() {
    return getParent().getPath().append( getName() );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getResource()
   */
  public IResource getResource() {
    return null;
  }
  
  /**
   * Get the type of this marker.
   * 
   * @return The marker's type.
   */
  public MarkerType getType() {
    return this.type;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isLocal()
   */
  public boolean isLocal() {
    return true;
  }

}
