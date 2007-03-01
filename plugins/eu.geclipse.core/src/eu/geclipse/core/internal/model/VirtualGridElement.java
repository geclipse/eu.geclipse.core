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

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;

/**
 * This class implements the {@link IGridElement} interface to
 * represent virtual resources.
 */
public class VirtualGridElement implements IGridElement {
  
  /**
   * The parent of this virtual element.
   */
  private IGridContainer parent;
  
  /**
   * The name of this element.
   */
  private String name;
  
  /**
   * Construct a new <code>VirtualGridElement</code>.
   * 
   * @param parent The parent of this element.
   * @param name The name of this element.
   */
  VirtualGridElement( final IGridContainer parent,
                      final String name ) {
    this.parent = parent;
    this.name = name;
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
    return null;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getName()
   */
  public String getName() {
    return this.name;
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
    return getParent().getPath().append( getName() );
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
    return null;
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
    return true;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
   */
  @SuppressWarnings("unchecked")
  public Object getAdapter( final Class adapter ) {
    Object result = null;
    if ( IResource.class.isAssignableFrom( adapter ) ) {
      result = getResource();
    }
    return result;
  }
  
}
