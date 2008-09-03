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

package eu.geclipse.core.model.impl;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IResource;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridResourceCategory;
import eu.geclipse.core.model.IGridResourceContainer;


public class GridModelPropertyTester extends PropertyTester {
  
  private static final String PROPERTY_IS_GRID_CONNECTION
    = "isGridConnection"; //$NON-NLS-1$
  
  private static final String PROPERTY_RESOURCE_CATEGORY
    = "resourceCategory"; //$NON-NLS-1$

  public boolean test( final Object receiver,
                       final String property,
                       final Object[] args,
                       final Object expectedValue ) {
    
    boolean result = false;
    
    if ( receiver instanceof IResource ) {
      result = testResource( ( IResource ) receiver, property, args, expectedValue );
    } else if ( receiver instanceof IGridElement ) {
      result = testGridElement( ( IGridElement ) receiver, property, args, expectedValue );
    }
    
    return result;
    
  }
  
  private boolean testResource( final IResource resource,
                               final String property,
                               final Object[] args,
                               final Object expectedValue ) {
    
    boolean result = false;
    
    if ( PROPERTY_IS_GRID_CONNECTION.equals( property ) ) {
      IGridElement element = GridModel.getRoot().findElement( resource );
      if ( ( element != null ) && ( element instanceof IGridConnection ) ) {
        result = true;
      }
    }
    
    return result;
    
  }
  
  private boolean testGridElement( final IGridElement element,
                                  final String property,
                                  final Object[] args,
                                  final Object expectedValue ) {
    
    boolean result = false;
    
    if ( PROPERTY_RESOURCE_CATEGORY.equals( property ) ) {
      if ( element instanceof IGridResourceContainer ) {
        String categoryID = expectedValue.toString();
        IGridResourceCategory referenceCategory = GridResourceCategoryFactory.getCategory( categoryID );
        IGridResourceCategory elementCategory = ( ( IGridResourceContainer ) element ).getCategory();
        result = ( referenceCategory != null ) && elementCategory.equals( referenceCategory ); 
      }
    }
    
    return result;
    
  }

}
