/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     PSNC - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.ui.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import eu.geclipse.core.model.IGridConnectionElement;

/**
 * Implementation of a {@link ViewerFilter} that may be used in conjunction with
 * viewers used to display Grid model elements. It filters out connection
 * elements which slave protocol was given by user.
 */
public class GridConnectionProtocolFilter extends ViewerFilter {

  private List<String> protocolsToFilter = new ArrayList<String>();

  /**
   * Adds name of a protocol. Connections with this protocol will be filtered
   * out.
   * 
   * @param protocol
   */
  public void addFilterProtocol( final String protocol ) {
    if( !this.protocolsToFilter.contains( protocol ) ) {
      this.protocolsToFilter.add( protocol );
    }
  }

  @Override
  public boolean select( final Viewer viewer,
                         final Object parentElement,
                         final Object element )
  {
    boolean result = false;
    if( element instanceof IGridConnectionElement ) {
      result = select( ( IGridConnectionElement )element );
    }
    return result;
  }

  private boolean select( final IGridConnectionElement element ) {
    boolean result = true;
    try {
      String query = element.getConnectionFileStore().toURI().getQuery();
      if( query != null ) {
        int start = query.indexOf( "geclslave=" ); //$NON-NLS-1$
        if( start != -1 ) {
          String protocol = query.substring( start + "geclslave=".length(), //$NON-NLS-1$
                                             query.indexOf( "&", start ) ); //$NON-NLS-1$
          if( this.protocolsToFilter.contains( protocol ) ) {
            result = false;
          }
        }
      }
    } catch( CoreException e ) {
      Activator.logException( e );
    }
    return result;
  }
}
