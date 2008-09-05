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
 *    Nikolaos Tsioutsias - University of Cyprus
 *****************************************************************************/

package eu.geclipse.ui.properties;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.core.model.IGridJobService;


/**
 * @author tnikos
 *
 */
public class IGridJobServiceSource 
extends AbstractPropertySource<IGridJobService>
{

  static private List<IProperty<IGridJobService>> staticDescriptors;
  
  /**
   * @param sourceObject a valid IGridJobService object
   */
  public IGridJobServiceSource( final IGridJobService sourceObject ) {
    super( sourceObject );
  }
  
  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return IGridJobServiceSource.class;
  }

  @Override
  protected List<IProperty<IGridJobService>> getStaticProperties() {
    if( staticDescriptors == null ) {
      staticDescriptors = createProperties();
    }
    
    return staticDescriptors;
  }
  
  static private List<IProperty<IGridJobService>> createProperties() {
    List<IProperty<IGridJobService>> propertiesList = new ArrayList<IProperty<IGridJobService>>( 1 );
    propertiesList.add( createURI() );
    
    return propertiesList;
  }
  
  static private IProperty<IGridJobService> createURI() {
    return new AbstractProperty<IGridJobService>( "URI", null) { //$NON-NLS-1$
      @Override
      public Object getValue( final IGridJobService gridJobService )
      {
        return gridJobService.getURI();
      } 
    };
  }
}
