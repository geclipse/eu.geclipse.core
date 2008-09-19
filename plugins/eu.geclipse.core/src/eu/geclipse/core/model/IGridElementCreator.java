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
 * Base interface for all classes that implement functionality for
 * creating specific implementations of the {@link IGridElement}
 * interface. Element creators and their capabilities to create elements from
 * a source object are specified with the eu.geclipse.core.gridElementCreator
 * extension point. Normally an element creator needs a source object from
 * which it creates a corresponding grid model element. This source object has
 * to be set with {@link #setSource(Object)}.
 */
public interface IGridElementCreator {
  
  /**
   * Create an element and set it to be a child of the specified
   * {@link IGridContainer}. The element is created from the
   * object that was specified in a former call to
   * {@link #setSource(Object)}. If the creation fails a
   * {@link ProblemException} will be thrown.
   * 
   * @param parent The parent of the newly created element.
   * @return The newly created element.
   * @throws ProblemException If any problem occurs while the new element is
   * created. For instance if a source object is needed to create the element
   * but was not defined before.
   */
  public IGridElement create( final IGridContainer parent ) throws ProblemException;
  
  /**
   * Shortcut method for creating an element from the specified source object.
   * 
   * @param parent The parent of the newly created element.
   * @param source The object from which to create the element.
   * @return The newly created element.
   * @throws ProblemException If any problem occurs while the new element is
   * created.
   */
  public IGridElement create( final IGridContainer parent, final Object source ) throws ProblemException;
  
  /**
   * Get the source object that was formerly specified with
   * {@link #setSource(Object)}.
   * 
   * @return Get the object from which this creator will try to create a grid
   * model element if {@link #create(IGridContainer)} is called.
   */
  public Object getSource();
  
  /**
   * Set the source object to be used by this creator to create a grid model
   * element from.
   *  
   * @param source The object from which this creator will try to create a grid
   * model element if {@link #create(IGridContainer)} is called.
   */
  public void setSource( final Object source );
  
}
