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

import eu.geclipse.core.model.IGridElementCreator;

/**
 * Abstract imlementation of the {@link IGridElementCreator}
 * interface. This implementation mainly delegates the
 * {@link #canCreate(Object)} method to an internal one and
 * sets the internal object to the argument if the internal
 * implementation returned true.
 */
public abstract class AbstractGridElementCreator
    implements IGridElementCreator {
  
  /**
   * The agument of the last successful call to any of the
   * <code>canCreate(...)</code> methods.
   */
  private Object internalObject;
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#canCreate(java.lang.Object)
   */
  public boolean canCreate( final Object fromObject ) {
    boolean result = internalCanCreate( fromObject );
    if ( result ) {
      setObject( fromObject );
    }
    return result;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#getObject()
   */
  public Object getObject() {
    return this.internalObject;
  }
  
  /**
   * Set the internal object that will afterwards be used to create
   * elements from.
   * 
   * @param object The new internal object.
   */
  protected void setObject( final Object object ) {
    this.internalObject = object;
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
   */
  protected abstract boolean internalCanCreate( final Object fromObject );
  
}
