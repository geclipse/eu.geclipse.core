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
 * First version of a connection manager. This is a very preliminary defintion
 * that will for sure change in the future.
 */
public interface IConnectionManager
    extends IGridElementManager {
  
  /**
   * Get all global connections that are currently defined.
   * 
   * @return An array containing all currently defined global
   * connections.
   * @see IGridConnection#isGlobal()
   */
  public IGridConnection[] getGlobalConnections();
  
}
