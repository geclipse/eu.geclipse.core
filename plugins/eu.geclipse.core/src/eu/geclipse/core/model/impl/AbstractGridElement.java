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

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.PlatformObject;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;

/**
 * Abstract implementation of the {@link IGridElement} interface.
 */
public abstract class AbstractGridElement
    extends PlatformObject
    implements IGridElement {
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#dispose()
   */
  public void dispose() {
    // empty implementation
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
   */
  @Override
  @SuppressWarnings("unchecked")
  public Object getAdapter( final Class adapter ) {
    Object result = null;
    if ( adapter.isAssignableFrom( IResource.class ) ) {
      result = getResource();
    }
    if ( result == null ) {
      result = super.getAdapter( adapter );
    }
    return result;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getProject()
   */
  public IGridProject getProject() {
    IGridContainer parent = getParent();
    return parent == null ? null : parent.getProject();
    /*IGridProject project = null;
    IGridContainer parent = getParent();
    while ( ( parent != null ) && !( parent instanceof IGridProject ) ) {
      parent = parent.getParent();
    }
    if ( parent instanceof IGridProject ) {
      project = ( IGridProject ) parent; 
    }
    return project;*/
  }
  
  public boolean isHidden() {
    return getName().startsWith( "." );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isVirtual()
   */
  public boolean isVirtual() {
    return getResource() == null;
  }
  
}
