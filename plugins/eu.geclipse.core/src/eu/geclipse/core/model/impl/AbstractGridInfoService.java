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
    
    IProgressMonitor lMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
      
    if ( category.equals( GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_APPLICATIONS ) ) ) {
      if ( parent instanceof IGridComputing ) {
        result = fetchApplications( parent, vo, ( IGridComputing ) parent, lMonitor );
      } else {
        result = fetchApplications( parent, vo, null, lMonitor );
      }
    }

    else if ( category.equals( GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_COMPUTING ) ) ) {
      result = fetchComputing( parent, vo, lMonitor );
    }

    else if ( category.equals( GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_JOB_SERVICES ) ) ) {
      IGridResource[] services = fetchServices( parent, vo, lMonitor );
      services = checkThisInfoService( services );
      result = filterResources( services, IGridJobService.class, false );
    }

    else if ( category.equals( GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_INFO_SERVICES ) ) ) {
      IGridResource[] services = fetchServices( parent, vo, lMonitor );
      services = checkThisInfoService( services );
      result = filterResources( services, IGridInfoService.class, false );
    }

    else if ( category.equals( GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_OTHER_SERVICES ) ) ) {
      IGridResource[] services = fetchServices( parent, vo, lMonitor );
      services = checkThisInfoService( services );
      services = filterResources( services, IGridJobService.class, true );
      result = filterResources( services, IGridInfoService.class, true );
    }

    else if ( category.equals( GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_STORAGE ) ) ) {
      result = fetchStorage( parent, vo, monitor );
    }
    
    if ( ( result != null ) && ( typeFilter != null ) ) {
      result = filterResources( result, typeFilter, false );
    }
    
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
/*
  public String getHostName() {
    // TODO Auto-generated method stub
    return null;
  }

  public URI getURI() {
    // TODO Auto-generated method stub
    return null;
  }

  public IFileStore getFileStore() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getName() {
    // TODO Auto-generated method stub
    return null;
  }

  public IGridContainer getParent() {
    // TODO Auto-generated method stub
    return null;
  }

  public IPath getPath() {
    // TODO Auto-generated method stub
    return null;
  }

  public IResource getResource() {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean isLocal() {
    // TODO Auto-generated method stub
    return false;
  }
*/
}
