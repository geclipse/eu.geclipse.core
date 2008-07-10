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
 * A <code>IStorableElement</code> defines methods to save
 * an element to an <code>OutputStream</code> and to load it
 * from an <code>InputStream</code>. That makes that element
 * persistent.
 */

public interface IStorableElement extends IGridElement {
  
  /**
   * Load the properties of that element. That may cause that
   * some predefined properties may be overwritten with the
   * loaded properties. The element itself has to know from
   * where to load itself.
   * 
   * @throws ProblemException If an error occurs while loading
   * this element. This is mainly due to <code>IOException</code>s.
   */
  public void load() throws ProblemException;
  
  /**
   * Write the properties of this element. The element itself has
   * to know where to save itself. 
   * 
   * @throws ProblemException If an error occurs while saving
   * this element. This is mainly due to <code>IOException</code>s.
   */
  public void save() throws ProblemException;
  
}
