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

import eu.geclipse.core.internal.model.notify.GridModelEvent;

/**
 * Definition of a notification service for {@link GridModelEvent}s.
 */
public interface IGridModelNotifier {
  
  /**
   * Add the specified {@link IGridModelListener} to the list
   * of listeners.
   * 
   * @param listener The listener to be added.
   */
  public void addGridModelListener( final IGridModelListener listener );
  
  /**
   * Remove the specified {@link IGridModelListener} from the list
   * of listeners.
   * 
   * @param listener The listener to be removed. 
   */
  public void removeGridModelListener( final IGridModelListener listener );
  
}
