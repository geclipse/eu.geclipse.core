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

import eu.geclipse.core.model.IGridElement;

/**
 * Interface definition for a Grid element filter that provides
 * a specific filtering mechanism for an array of input elements.
 */
public interface IQueryFilter {
  
  /**
   * Filter the specified input elements.
   * 
   * @param input The Grid elements to be filtered.
   * @return An array that may contain a subset of the input array.
   */
  public IGridElement[] filter( final IGridElement[] input );

}
