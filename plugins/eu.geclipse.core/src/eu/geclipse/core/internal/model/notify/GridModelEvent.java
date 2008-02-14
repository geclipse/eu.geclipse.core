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

package eu.geclipse.core.internal.model.notify;

import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;

/**
 * Implementation of the {@link IGridModelEvent} interface. Grid model events
 * are used to track changes in the Grid model.
 */
public class GridModelEvent implements IGridModelEvent {
  
  /**
   * The type of the event.
   */
  private int type;
  
  /**
   * The source where the event occurred.
   */
  private IGridElement source;
  
  /**
   * The elements that are affected by this event.
   */
  private IGridElement[] elements;
  
  /**
   * Construct a new Grid model event.
   * 
   * @param type The type of this event. This may be one of
   * {@link IGridModelEvent#ELEMENTS_ADDED} and
   * {@link IGridModelEvent#ELEMENTS_REMOVED}.
   * @param source The element where the event occurred.
   * @param elements The elements that are affected by this event.
   */
  public GridModelEvent( final int type,
                         final IGridElement source,
                         final IGridElement[] elements ) {
    this.type = type;
    this.source = source;
    this.elements = elements;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridModelEvent#getElements()
   */
  public IGridElement[] getElements() {
    return this.elements;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridModelEvent#getSource()
   */
  public IGridElement getSource() {
    return this.source;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridModelEvent#getType()
   */
  public int getType() {
    return this.type;
  }

}
