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

import java.io.Serializable;
import java.util.Comparator;

import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IVirtualOrganization;

/**
 * Comparator implementation for sorting elements within
 * the Grid model.
 */
public class GridModelComparator
    implements Comparator< Object >, Serializable {
  
  private static final long serialVersionUID = -8103675385948860987L;

  /* (non-Javadoc)
   * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
   */
  public int compare( final Object o1,
                      final Object o2 ) {
    int result = 0;
    if ( ( o1 instanceof IGridElement ) && ( o2 instanceof IGridElement ) ) {
      result = compare( ( IGridElement ) o1, ( IGridElement ) o2 );
    } else {
      result = o1.toString().compareTo( o2.toString() );
    }
    return result;
  }

  /**
   * Compare the specified elements.
   * 
   * @param e1 The first element.
   * @param e2 The second element.
   * @return The return value is calculated in order to prefer folders
   * before files. If both elements are either folders or files the names
   * of the elements are compared.
   */
  public int compare( final IGridElement e1,
                      final IGridElement e2 ) {
    int value = getValue( e1 ) - getValue( e2 );
    if ( value == 0 ) {
      value = e1.getName().compareTo( e2.getName() );
    }
    return value;
  }
  
  private int getValue( final IGridElement e ) {
    int result = 0;
    if ( e instanceof IVirtualOrganization ) {
      result = 10;
    } else if ( e instanceof IGridConnectionElement ) {
      result
        = ( ( IGridConnectionElement ) e ).isFolder()
        ? 1 : 2;
    } else if ( e instanceof IGridContainer ) {
      result = 1;
    } else {
      result = 2;
    }
    return result;
  }
  
}
