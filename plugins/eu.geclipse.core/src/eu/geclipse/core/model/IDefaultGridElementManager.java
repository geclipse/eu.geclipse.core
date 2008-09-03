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

package eu.geclipse.core.model;

import eu.geclipse.core.reporting.ProblemException;


/**
 * An {@link IGridElementManager} that supports the management of
 * a default element. A default element is an element contained
 * in the manager that represents a default choice whenever the
 * user is asked to make a choice. 
 */
public interface IDefaultGridElementManager
    extends IGridElementManager {
  
  /**
   * Sets this manager default element.
   * 
   * @param defaultElement The element that should be set as default.
   * If the element is not yet contained in the manager it will be
   * added to the managers children.
   * 
   * @throws ProblemException If this manager can not handle the
   * specified element.
   * @see IGridElementManager#canManage(IGridElement)
   */
  public void setDefault( final IGridElement defaultElement ) throws ProblemException;
  
  /**
   * The current default element of this manager.
   * 
   * @return The default element.
   */
  public IGridElement getDefault();
  
}
