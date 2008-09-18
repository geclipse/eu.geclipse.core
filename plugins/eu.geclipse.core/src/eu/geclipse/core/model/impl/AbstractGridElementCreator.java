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

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Abstract implementation of the {@link IGridElementCreator}
 * interface.
 */
public abstract class AbstractGridElementCreator
    implements IGridElementCreator {
  
  /**
   * The agument of the last successful call to any of the
   * <code>canCreate(...)</code> methods.
   */
  private Object internalSource;
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#canCreate(java.lang.Object)
   */
  public boolean canCreate( final Object fromObject ) {
    boolean result = internalCanCreate( fromObject );
    if ( result ) {
      setSource( fromObject );
    }
    return result;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#create(eu.geclipse.core.model.IGridContainer, java.lang.Object)
   */
  public IGridElement create( final IGridContainer parent, final Object source ) throws ProblemException {
    setSource( source );
    return create( parent );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#getSource()
   */
  public Object getSource() {
    return this.internalSource;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#setSource(java.lang.Object)
   */
  public void setSource( final Object source ) {
    this.internalSource = source;
  }
  
  /**
   * Internal method to determine if this creator is potentially 
   * able to create elements from the specified object. This method
   * is called from {@link #canCreate(Object)} and has not to care
   * about storing the passed object since this is handled by the
   * {@link #canCreate(Object)} method. Therefore this method
   * may never be called directly.
   * 
   * @param fromObject The object from which the element should be
   * created.
   * @return True if this creator is potentially able to create an
   * element from the specified object.
   * @Deprecated This method is deprecated in favour of the extended definition
   * of the eu.geclipse.core.gridElementCreator extension point.
   */
  protected abstract boolean internalCanCreate( final Object fromObject );
  
}
