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

package eu.geclipse.core.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridJobService;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IGridResourceCategory;
import eu.geclipse.core.model.IVirtualOrganization;

/**
 * Abstract core implementation of {@link IGridInfoService}.
 */
public abstract class AbstractGridInfoService
    extends AbstractGridElement
    implements IGridInfoService {
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridInfoService#fetchResources(eu.geclipse.core.model.IGridContainer, eu.geclipse.core.model.IVirtualOrganization, eu.geclipse.core.model.IGridResourceCategory, org.eclipse.core.runtime.IProgressMonitor)
   */
  public IGridResource[] fetchResources( final IGridContainer parent,
                                         final IVirtualOrganization vo,
                                         final IGridResourceCategory category,
                                         final IProgressMonitor monitor ) {
    return fetchResources( parent, vo, category, false, null, monitor );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridInfoService#fetchResources(eu.geclipse.core.model.IGridContainer, eu.geclipse.core.model.IVirtualOrganization, eu.geclipse.core.model.IGridResourceCategory, boolean, java.lang.Class, org.eclipse.core.runtime.IProgressMonitor)
   */
  public IGridResource[] fetchResources( final IGridContainer parent,
                                         final IVirtualOrganization vo,
                                         final IGridResourceCategory category,
                                         final boolean exclusive,
                                         final Class< ? extends IGridResource > typeFilter,
                                         final IProgressMonitor monitor ) {
    
    IGridResource[] result = null;
    
    
    
    return result;
  }
  
  protected IGridResource[] filterResources( final IGridResource[] resources,
                                             final Class< ? extends IGridResource > type,
                                             final boolean remove ) {

    List< IGridResource > resultList = new ArrayList< IGridResource >();

    for ( IGridResource resource : resources ) {
      boolean isType = type.isAssignableFrom( resource.getClass() );
      if ( ( remove && ! isType ) || ( ! remove && isType ) ) {
        resultList.add( resource );
      }
    }

    return resultList.toArray( new IGridResource[ resultList.size() ] );
  }
  
  private IGridResource[] checkThisInfoService( final IGridResource[] resources ) {
    
    IGridResource[] result = resources;
    
    boolean found = false;
    if ( resources != null ) {
      for ( IGridResource resource : resources ) {
        if ( resource.equals( this ) ) {
          found = true;
          break;
        }
      }
    }
    
    if ( ! found ) {
      if ( resources != null ) {
        result = new IGridResource[ resources.length + 1 ];
        System.arraycopy( resources, 0, result, 0, resources.length );
        result[ resources.length ] = this;
      } else {
        result = new IGridResource[] { this };
      }
    }
    
    return result;
    
  }

}
