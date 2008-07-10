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
 * An {@link IGridElementManager} that is able to save its
 * managed elements and to load them back. That makes the state
 * of that manager persistent. 
 */
public interface IStorableElementManager
    extends IGridElementManager {

  /**
   * Save all currently managed elements. It is the managers
   * responsibility to save the elements to an appropriate
   * location. It is also the managers responsibility how the
   * elements should be saved (one per file or all in one big
   * file...).
   * 
   * @throws ProblemException If an error occurs during the
   * save operation. This may for example be due to an
   * <code>IOException</code>.
   */
  public void saveElements() throws ProblemException;
  
  /**
   * Load the state of this manager from a predefined location.
   * It is the managers responsibility to determine the location
   * from which to load the state. It is also the managers
   * responsibility how the elements are loaded (one per file or
   * all elements in one big file...). Be aware of the fact that all
   * formerly contained elements are deleted before the managers loads
   * the new elements.
   * 
   * @throws ProblemException If an error occurs during the
   * load operation. This may for example be due to an
   * <code>IOException</code>.
   */
  public void loadElements() throws ProblemException;
  
}
