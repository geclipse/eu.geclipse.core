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

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.filesystem.IFileStore;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.internal.model.GridMountCreator;
import eu.geclipse.core.internal.model.GridProjectCreator;
import eu.geclipse.core.internal.model.GridRoot;
import eu.geclipse.core.internal.model.LocalResourceCreator;

public class GridModel {
  
  private static GridRoot gridRoot;
  
  private static List< IGridElementCreator > standardCreators;

  public static IGridRoot getRoot() {
    if ( gridRoot == null ) {
      gridRoot = new GridRoot();
    }
    return gridRoot;
  }
  
  public static List< IGridElementCreator > getStandardCreators() {
    if ( standardCreators == null ) {
      standardCreators = new ArrayList< IGridElementCreator >();
      standardCreators.add( new GridProjectCreator() );
      standardCreators.add( new LocalResourceCreator() );
      standardCreators.add( new GridMountCreator() );
    }
    return standardCreators;
  }
  
  public static List< IGridElementCreator > getElementCreators() {
    return Extensions.getRegisteredElementCreators();
  }
  
  public static List< IGridElementCreator > getElementCreators( final Class< ? extends IGridElement > elementType ) {
    List< IGridElementCreator > resultList
      = new ArrayList< IGridElementCreator >();
    List<IGridElementCreator> creators
      = getElementCreators();
    for ( IGridElementCreator creator : creators ) {
      if ( creator.canCreate( elementType ) ) {
        resultList.add( creator );
      }
    }
    return resultList;
  }
  
  public static List< IStorableElementCreator > getStorableElementCreators() {
    List< IStorableElementCreator > jobCreators
      = new ArrayList< IStorableElementCreator >();
    List< IGridElementCreator > elementCreators
      = getElementCreators();
    for ( IGridElementCreator creator : elementCreators ) {
      if ( creator instanceof IStorableElementCreator ) {
        jobCreators.add( ( IStorableElementCreator ) creator ); 
      }
    }
    return jobCreators;
  }
  
  public static IStorableElementCreator getStorableElementCreator( final IFileStore fileStore ) {
    IStorableElementCreator result = null;
    List<IStorableElementCreator> creators
      = getStorableElementCreators();
    for ( IStorableElementCreator creator : creators ) {
      if ( creator.canCreate( fileStore ) ) {
        result = creator;
        break;
      }
    }
    return result;
  }
  
  public static List< IGridJobCreator > getJobCreators() {
    List< IGridJobCreator > jobCreators
      = new ArrayList< IGridJobCreator >();
    List< IGridElementCreator > elementCreators
      = getElementCreators();
    for ( IGridElementCreator creator : elementCreators ) {
      if ( creator instanceof IGridJobCreator ) {
        jobCreators.add( ( IGridJobCreator ) creator ); 
      }
    }
    return jobCreators;
  }
  
  public static List< IGridJobCreator > getJobCreators( final IGridJobDescription description ) {
    List< IGridJobCreator > resultList
      = new ArrayList< IGridJobCreator >();
    List<IGridJobCreator> creators = getJobCreators();
    for ( IGridJobCreator creator : creators ) {
      if ( creator.canCreate( description ) ) {
        resultList.add( creator );
      }
    }
    return resultList;
  }
  
  public static List< IGridElementCreator > getVoCreators() {
    return getElementCreators( IVirtualOrganization.class );
  }
  
}
