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

import org.eclipse.core.filesystem.IFileStore;

/**
 * Element creator that is able to create elements from an
 * {@link IFileStore}.
 */
public interface IStorableElementCreator
    extends IGridElementCreator, IExtensible {
  
  /**
   * Determine if this creator is able to create an element from
   * the specified file store.
   * 
   * @param fromFileStore The file store from which to create
   * the element.
   * @return True if this creator is potentially able to create
   * an element from the specified file store.
   */
  public boolean canCreate( final IFileStore fromFileStore );
  
}
