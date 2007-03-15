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
 * This interface has to be implemented by each grid element that
 * is entended to be extended. It provides a single method that
 * returns the extension ID of the creator that was responsible for
 * the creation of that element.
 */
public interface IExtensible {
  
  /**
   * Get the ID of the extension of the grid element creator extension
   * point that was responsible for the creation of this element.
   * 
   * @return The creators extension ID.
   */
  public String getExtensionID();
  
}
