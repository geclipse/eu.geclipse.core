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

import eu.geclipse.core.model.impl.AbstractGridInfoService;



/**
 * @author tnikos
 *
 */
public class AbstractGridInfoServiceSource 
extends AbstractPropertySource<AbstractGridInfoService>
{

  static private List<IProperty<AbstractGridInfoService>> staticDescriptors;
  AbstractGridInfoService abstractGridInfoService;
  
  /**
   * @param abstractGridInfoService an AbstractGridInfoService
   */
  public AbstractGridInfoServiceSource(final AbstractGridInfoService abstractGridInfoService)
  {
    super(abstractGridInfoService);
    this.abstractGridInfoService = abstractGridInfoService;
  }
  
  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return AbstractGridInfoServiceSource.class;
  }

  @Override
  protected List<IProperty<AbstractGridInfoService>> getStaticProperties()
  {
    if( staticDescriptors == null ) {
      staticDescriptors = createProperties();
    }
    
    return staticDescriptors;
  }
  
  static private List<IProperty<AbstractGridInfoService>> createProperties() {
    List<IProperty<AbstractGridInfoService>> propertiesList = new ArrayList<IProperty<AbstractGridInfoService>>( 1 );
    propertiesList.add( createURI() );
    propertiesList.add( createPath() );
    
    return propertiesList;
  }
  
  static private IProperty<AbstractGridInfoService> createURI() {
    return new AbstractProperty<AbstractGridInfoService>( "URI", null) { //$NON-NLS-1$
      @Override
      public Object getValue( final AbstractGridInfoService localAbstractGridInfoService )
      {
        return localAbstractGridInfoService.getURI().toString();
      } 
    };
  }
  
  static private IProperty<AbstractGridInfoService> createPath() {
    return new AbstractProperty<AbstractGridInfoService>( "Path", null) { //$NON-NLS-1$
      @Override
      public Object getValue( final AbstractGridInfoService localAbstractGridInfoService )
      {
        return localAbstractGridInfoService.getFileStore();
      } 
    };
  }
}
