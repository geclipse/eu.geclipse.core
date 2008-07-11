/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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

import eu.geclipse.core.model.IDefaultGridElementManager;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Internal abstract implementation of an {@link IDefaultGridElementManager}.  
 */
public abstract class AbstractDefaultGridElementManager
    extends AbstractGridElementManager
    implements IDefaultGridElementManager {
  
  /**
   * The current default element.
   */
  private IGridElement defaultElement;

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IDefaultGridElementManager#getDefault()
   */
  public IGridElement getDefault() {
    return this.defaultElement;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IDefaultGridElementManager#setDefault(eu.geclipse.core.model.IGridElement)
   */
  public void setDefault( final IGridElement element ) throws ProblemException {
    if ( ( element != null ) && ( element != this.defaultElement ) ) {
      addElement( element );
      this.defaultElement = element;
      updateDefault();
    }
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.AbstractGridElementManager#addElement(eu.geclipse.core.model.IGridElement)
   */
  @Override
  public boolean addElement( final IGridElement element ) throws ProblemException {
    boolean result = super.addElement( element );
    if ( result ) {
      updateDefault();
    }
    return result;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.AbstractGridElementManager#removeElement(eu.geclipse.core.model.IGridElement)
   */
  @Override
  public boolean removeElement( final IGridElement element ) {
    boolean result = super.removeElement( element );
    if ( result ) {
      updateDefault();
    }
    return result;
  }
  
  /**
   * Update the default element.
   * 
   * @return True if the default element has changed during this
   * operation.
   */
  protected boolean updateDefault() {
    boolean changed = false;
    if ( ( this.defaultElement != null )
        && !contains( this.defaultElement ) ) {
      this.defaultElement = null;
      changed = true;
    }
    if ( ( this.defaultElement == null ) && hasChildren() ) {
      this.defaultElement = getChildren( null )[0];
      changed = true;
    }
    return changed;
  }

}
