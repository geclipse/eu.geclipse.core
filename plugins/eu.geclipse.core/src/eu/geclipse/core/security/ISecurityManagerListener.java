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

package eu.geclipse.core.security;


/**
 * Interface definition for listeners to security managers.
 */
public interface ISecurityManagerListener {

  /**
   * If registered as listener to a security manager this method
   * is called by the manager whenever its content changes.
   * 
   * @param manager The manager that issued the change.
   */
  public void contentChanged( final ISecurityManager manager );
  
}
