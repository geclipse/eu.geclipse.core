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


public class GridModelPropertyTester extends PropertyTester {

  public boolean test( final Object receiver,
                       final String property,
                       final Object[] args,
                       final Object expectedValue ) {
    
    boolean result = false;
    
    IResource resource = ( IResource ) receiver;
    if ( "isGridConnection".equals( property ) ) { //$NON-NLS-1$
      IGridElement element = GridModel.getRoot().findElement( resource );
      if ( ( element != null ) && ( element instanceof IGridConnection ) ) {
        result = true;
      }
    }
    
    return result;
    
  }

}
