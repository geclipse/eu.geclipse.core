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
 * Preliminary definition of grid transfers. This is not yet intended
 * for public use and may change in the near future.  
 */
public interface IGridTransfer
    extends IGridContainer {
  
  /**
   * Get the element to be transfered.
   * 
   * @return The source of the transfer. 
   */
  public IGridElement getSource();
  
  /**
   * Get the destination to where to transfer the source.
   * 
   * @return The target of the transfer.
   */
  public IGridContainer getTarget();
  
  /**
   * Determines if this is an atomic transfer, i.e. the source is a single file
   * and not a collection of files like a Grid container.
   * 
   * @return True if the source is a single file.
   */
  public boolean isAtomic();
  
}
