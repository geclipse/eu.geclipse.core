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

package eu.geclipse.ui.providers;

import java.util.Collection;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * This implementation of the {@link ITableLabelProvider} handles the case where
 * the row input of a {@link org.eclipse.jface.viewers.TableViewer} is either an
 * array or a {@link java.util.Collection}. It may be used  for table viewers in
 * conjunction with Eclipse's {@link org.eclipse.jface.viewers.ArrayContentProvider}
 * in order to display 2-dimensional arrays. 
 */
public class ArrayTableLabelProvider
    extends LabelProvider
    implements ITableLabelProvider {
  
  /**
   * Empty string constant.
   */
  private static final String EMPTY_STRING = ""; //$NON-NLS-1$

  /**
   * This implementation tries to cast the specified element either to an array
   * or to a {@link java.util.Collection} and uses afterwards the
   * {@link #getImage(Object)} method to retrieve an image for the specified
   * index. Subclasses may therefore overwrite {@link #getImage(Object)}.
   * 
   * @see #getImage(Object)
   */
  public Image getColumnImage( final Object element, final int columnIndex ) {
    
    Image result = null;
    Object[] array = null;
    
    // Test if the element is either an array or a collection
    if ( element instanceof Object[] ) {
      array = ( Object[] ) element;
    } else if ( element instanceof Collection ) {
      // Collections are cast to an array
      Collection< ? > collection = ( Collection< ? > ) element;
      array = collection.toArray( new Object[ collection.size() ] );
    }
    
    // If an array was found and the columnIndex fits into the size of this
    // array use getImage to retrieve an image for the specified object.
    if ( ( array != null ) && ( columnIndex < array.length ) ) {
      result = getImage( array[ columnIndex ] );
    }
    
    return result;
    
  }

  /**
   * This implementation tries to cast the specified element either to an array
   * or to a {@link java.util.Collection} and uses afterwards the
   * {@link #getText(Object)} method to retrieve a text for the specified
   * index. Subclasses may therefore overwrite {@link #getText(Object)}.
   * 
   * @see #getText(Object)
   */
  public String getColumnText( final Object element, final int columnIndex ) {
    
    String result = EMPTY_STRING;
    
    Object[] array = null;
    
    // Test if the element is either an array or a collection
    if ( element instanceof Object[] ) {
      array = ( Object[] ) element;
    } else if ( element instanceof Collection ) {
      // Collections are cast to an array
      Collection< ? > collection = ( Collection< ? > ) element;
      array = collection.toArray( new Object[ collection.size() ] );
    }
    
    // If an array was found and the columnIndex fits into the size of this
    // array use getImage to retrieve an image for the specified object.
    if ( ( array != null ) && ( columnIndex < array.length ) ) {
      result = getText( array[ columnIndex ] );
    }
    
    return result;
    
  }

}
