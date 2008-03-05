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

import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.views.GridConnectionView;

/**
 * Specialised {@link ConfigurableContentProvider} to be used with the
 * {@link GridConnectionView}. Basically this content provider assures
 * that only {@link IGridConnectionElement}s are visible.
 */
public class ConnectionViewContentProvider extends ConfigurableContentProvider {
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.providers.ConfigurableContentProvider#isVisible(eu.geclipse.core.model.IGridElement)
   */
  @Override
  protected boolean isVisible( final IGridElement element ) {
    boolean visible = element instanceof IGridConnectionElement;
    if ( ! visible ) {
      visible = super.isVisible( element );
    }
    return visible;
  }
  
  @Override
  protected Object remapElement( final IGridContainer container,
                                 final IGridElement element ) {
    
    Object result = null;
    
    if ( element instanceof IGridConnection ) {
      IGridConnection connection = ( IGridConnection ) element;
      if ( connection.isGlobal() ) {
        result = connection;
      }
    }
    
    if ( result == null ) {
      result = super.remapElement( container, element );
    }
    
    return result;
    
  }
  
}
