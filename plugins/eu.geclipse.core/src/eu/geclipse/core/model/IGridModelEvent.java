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

package eu.geclipse.core.model;

/**
 * Definition of Grid model events that are used to track
 * changes on the model, i.e. addition of new elements and
 * deletion of existing elements. Move operations are represented
 * by to events, a deletion followed by an addition.
 */
public interface IGridModelEvent {
  
  /**
   * ID for an element add operation.
   */
  public static final int ELEMENTS_ADDED = 1;
  
  /**
   * ID for an element remove operation.
   */
  public static final int ELEMENTS_REMOVED = 2;
  
  /**
   * Get the affected elements.
   * 
   * @return The elements that were added/removed. 
   */
  public IGridElement[] getElements();
  
  /**
   * Get the source of the event.
   * 
   * @return The parent where the elements were added/removed.
   */
  public IGridElement getSource();
  
  /**
   * Get the type of this event.
   * 
   * @return Either {@link #ELEMENTS_ADDED} or {@link #ELEMENTS_REMOVED}.
   */
  public int getType();
  
}
