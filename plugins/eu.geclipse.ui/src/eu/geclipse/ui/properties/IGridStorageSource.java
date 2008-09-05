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
 *    Nikolaos Tsioutsias - University of Cyprus
 *****************************************************************************/
package eu.geclipse.ui.properties;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.core.model.IGridStorage;

/**
 * @author tnikos
 *
 */
public class IGridStorageSource
extends AbstractPropertySource<IGridStorage>{
  
  static private List<IProperty<IGridStorage>> staticDescriptors;
  
  /**
   * @param sourceObject a valid IGridStorage Object
   */
  public IGridStorageSource( final IGridStorage sourceObject ) {
    super( sourceObject );
  }

  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return IGridStorageSource.class;
  }

  @Override
  protected List<IProperty<IGridStorage>> getStaticProperties() {
    if( staticDescriptors == null ) {
      staticDescriptors = createProperties();
    }
    
    return staticDescriptors;
  }
  
  static private List<IProperty<IGridStorage>> createProperties() {
    List<IProperty<IGridStorage>> propertiesList = 
      new ArrayList<IProperty<IGridStorage>>( 1 );
    propertiesList.add( createURI() );
    
    return propertiesList;
  }
  
  static private IProperty<IGridStorage> createURI() {
    return new AbstractProperty<IGridStorage>( "URI", null) { //$NON-NLS-1$
      @Override
      public Object getValue( final IGridStorage gridStorage )
      {
        return gridStorage.getURI();
      } 
    };
  }
}
