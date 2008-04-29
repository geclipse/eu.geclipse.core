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
 * Base interface for all classes that implement functionality for
 * creating specific implementations of the {@link IGridElement}
 * interface. Element creates contain mainly two types of methods, the
 * <code>canCreate(...)</code> methods and the <code>create(...)</code>
 * methods. Therefore an element creator can be asked if it is
 * potentially able to create an element and if so it can be asked to
 * really try to create the element. It is not necessarily true that the
 * creator can create an element if it returns true when asked if it can.
 * It may also the case that there occurs an error during the creation
 * of the element even if it should in principal be possible to create
 * an element.
 * 
 * If any of the <code>canCreate(...)</code> methods is called with an
 * argument this argument is stored if the method returns true until
 * another <code>canCreate(...)</code> method is called with an argument
 * and returns true. This stored argument can be retrieved with the
 * {@link #getObject()} method.
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
   */
  public boolean canCreate( final Class< ? extends IGridElement > elementType );
  
  /**
   * Ask this creator if it is able to create elements from the
   * specified object. If this methods returns true the object
   * will be remembered until another call of any of the
   * <code>canCreate(...)</code> methods. This object can 
   * afterwards retrieved with the {@link #getObject()} method.
   * 
   * @param fromObject The object from which to create an element.
   * @return True if this creator is potentially able to
   * create elements from the specified object. 
   */
  public boolean canCreate( final Object fromObject );
  
  /**
   * Create an element and set it to be a child of the specified
   * {@link IGridContainer}. The element is created from the
   * object that is returned by the {@link #getObject()} method.
   * If the creation fails a {@link GridModelException} will be
   * thrown.
   * 
   * @param parent The parent of the newly created element.
   * @return The newly created element.
   * @throws GridModelException If any problem occurs during the
   * new element is created.
   */
  public IGridElement create( final IGridContainer parent ) throws GridModelException;
  
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
   */
  public Object getObject();
  
}
