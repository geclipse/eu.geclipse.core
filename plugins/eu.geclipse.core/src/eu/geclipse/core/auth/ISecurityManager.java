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

package eu.geclipse.core.auth;

/**
 * A security manager is a container for security tokens.
 */
public interface ISecurityManager {

  /**
   * Add a listener to this manager that is informed whenever the
   * content of the manager changes.
   * 
   * @param l The listener to be added.
   */
  public void addListener( final ISecurityManagerListener l );
  
  /**
   * Remove a listener from the manager's internal list of listeners.
   * 
   * @param l The listener to be removed.
   */
  public void removeListener( final ISecurityManagerListener l );
  
}
