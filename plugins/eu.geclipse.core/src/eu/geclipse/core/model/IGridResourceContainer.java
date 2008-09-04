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

package eu.geclipse.core.model;

/**
 * <code>IGridResourceContainer</code>s are {@link IGridContainer}s that contain
 * {@link IGridResource}s of a specific category. Within the grid project they
 * are used under the VO subtree in order to represent the user's personnalised
 * Grid.
 */
public interface IGridResourceContainer
    extends IGridContainer {
  
  /**
   * Get the {@link IGridResourceCategory} that corresponds to the resources
   * contained in this container.
   * 
   * @return This container's resource category.
   */
  public IGridResourceCategory getCategory();

}
