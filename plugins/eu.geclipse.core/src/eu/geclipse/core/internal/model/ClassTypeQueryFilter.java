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
package eu.geclipse.core.internal.model;

import java.util.ArrayList;

import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IWrappedElement;

public class ClassTypeQueryFilter implements IQueryFilter {
  
  private Class< ? > classType;
  
  private boolean allow;
  
  public ClassTypeQueryFilter( final Class< ? > classType,
                               final boolean allow ) {
    this.classType = classType;
    this.allow = allow;
  }

  public IGridElement[] filter( final IGridElement[] input ) {
    
    java.util.List< IGridElement > result = new ArrayList< IGridElement >();
    
    for ( IGridElement element : input ) {
      if ( element instanceof IWrappedElement ) {
        element = ( ( IWrappedElement ) element ).getWrappedElement();
      }
      if ( this.allow
          && this.classType.isAssignableFrom( element.getClass() ) ) {
        result.add( element );
      } else if ( ! this.allow
          && ! this.classType.isAssignableFrom( element.getClass() ) ) {
        result.add( element );
      }
    }
    
    return result.toArray( new IGridElement[ result.size() ] );
    
  }

}
