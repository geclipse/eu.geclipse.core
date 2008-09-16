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
   * Ask this creator if it is able to create elements of the
   * specified type. If this methods returns true the element type
   * will be remembered until another call of any of the
   * <code>canCreate(...)</code> methods. This element type can 
   * afterwards retrieved with the {@link #getObject()} method.
   * 
   * @param elementType The type of the element that should be created.
   * @return True if this element creator is potentially able to
   * create an element of the specified type. 
   * @Deprecated This method is deprecated in favour of the extended definition
   * of the eu.geclipse.core.gridElementCreator extension point.
   */
  public boolean canCreate( final Class< ? extends IGridElement > elementType );
  
  /**
   * Ask this creator if it is able to create elements from the
   * specified object. If this methods returns true the object
   * will be remembered until another call of any of the
   * <code>canCreate(...)</code> methods. This object can 
   * afterwards retrieved with the {@link #getObject()} method.
   * 
   * @param source The object from which to create an element.
   * @return True if this creator is potentially able to
   * create elements from the specified object.
   * @Deprecated This method is deprecated in favour of the extended definition
   * of the eu.geclipse.core.gridElementCreator extension point.
   */
  public boolean canCreate( final Object source );
  
  /**
   * Create an element and set it to be a child of the specified
   * {@link IGridContainer}. The element is created from the
   * object that is was specified in a former call to
   * {@link #setSource(Object)}. If the creation fails a
   * {@link ProblemException} will be thrown.
   * 
   * @param parent The parent of the newly created element.
   * @return The newly created element.
   * @throws ProblemException If any problem occurs while the new element is
   * created. For instance of a source object is needed to create the element
   * but was not defined before an Exception is thrown.
   */
  public IGridElement create( final IGridContainer parent ) throws ProblemException;
  
  /**
   * Shortcut method for creating an element from the specified source object.
   * @param parent The parent of the newly created element.
   * @param source The object from which to create the element.
   * @return The newly created element.
   * @throws ProblemException If any problem occurs while the new element is
   * created.
   */
  public IGridElement create( final IGridContainer parent, final Object source ) throws ProblemException;
  
  /**
   * Get the argument of the last successful call of any of the
   * <code>canCreate(...)</code> methods. A successful call is a
   * call that returned <code>true</code>.
   * 
   * @return The argument of the last successful call of a
   * <code>canCreate(...)</code> method. This may be
   * <code>null</code> if no such method was called before or if
   * the argument was <code>null</code> itself. This method is
   * used by {@link #create(IGridContainer)} to get the object from
   * which to create the new element.
   * @Deprecated This method is deprecated in favour of the extended definition
   * of the eu.geclipse.core.gridElementCreator extension point.
   */
  public Object getObject();
  
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
