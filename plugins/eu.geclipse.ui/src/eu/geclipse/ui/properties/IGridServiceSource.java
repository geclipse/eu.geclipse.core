package eu.geclipse.ui.properties;


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

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.core.model.IGridService;


/**
 * @author tnikos
 *
 */
public class IGridServiceSource 
extends AbstractPropertySource<IGridService>
{

  static private List<IProperty<IGridService>> staticDescriptors;
  
  /**
   * @param sourceObject a valid IGridJobService object
   */
  public IGridServiceSource( final IGridService sourceObject ) {
    super( sourceObject );
  }
  
  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return IGridJobServiceSource.class;
  }

  @Override
  protected List<IProperty<IGridService>> getStaticProperties() {
    if( staticDescriptors == null ) {
      staticDescriptors = createProperties();
    }
    
    return staticDescriptors;
  }
  
  static private List<IProperty<IGridService>> createProperties() {
    List<IProperty<IGridService>> propertiesList = new ArrayList<IProperty<IGridService>>( 1 );
    propertiesList.add( createURI() );
    
    return propertiesList;
  }
  
  static private IProperty<IGridService> createURI() {
    return new AbstractProperty<IGridService>( "URI", null) { //$NON-NLS-1$
      @Override
      public Object getValue( final IGridService gridService )
      {
        return gridService.getURI();
      } 
    };
  }
}




