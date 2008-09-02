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
 *    Nikolaos Tsioutsias - initial API and implementation
 *    
 *****************************************************************************/

package eu.geclipse.ui.properties;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.info.model.GridGlueService;


public class GridGlueServiceSource extends AbstractPropertySource<GridGlueService> {
  
  static private List<IProperty<GridGlueService>> staticDescriptors;
  
  public GridGlueServiceSource(final GridGlueService gridGlueService)
  {
    super(gridGlueService);
  }
  
  public Object getEditableValue() {
    return this;
  }
  
  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return GridGlueServiceSource.class;
  }

  @Override
  protected List<IProperty<GridGlueService>> getStaticProperties() {
    
    if( staticDescriptors == null ) {
      staticDescriptors = createProperties();
    }
    
    return staticDescriptors;
  }
  
  static private List<IProperty<GridGlueService>> createProperties() {
    List<IProperty<GridGlueService>> propertiesList = new ArrayList<IProperty<GridGlueService>>( 1 );
    propertiesList.add( createEndPoint() );
    propertiesList.add( createStatus() );
    propertiesList.add( createType() );
    propertiesList.add( createVersion() );
    propertiesList.add( createSupported() );
    
    return propertiesList;
  }
  
  static private IProperty<GridGlueService> createEndPoint() {
    return new AbstractProperty<GridGlueService>( Messages.getString("InfoServiceElement.endPoint"), null) { //$NON-NLS-1$
      @Override
      public Object getValue( final GridGlueService gridGlueService )
      {
        return gridGlueService.getGlueService().endpoint;
      } 
    };
  }
  
  static private IProperty<GridGlueService> createStatus() {
    return new AbstractProperty<GridGlueService>( Messages.getString("InfoServiceElement.status"), null) { //$NON-NLS-1$
      @Override
      public Object getValue( final GridGlueService gridGlueService )
      {
        return gridGlueService.getGlueService().status;
      } 
    };
  }
  
  static private IProperty<GridGlueService> createType() {
    return new AbstractProperty<GridGlueService>( Messages.getString("InfoServiceElement.type"), null) { //$NON-NLS-1$
      @Override
      public Object getValue( final GridGlueService gridGlueService )
      {
        return gridGlueService.getGlueService().type;
      } 
    };
  }
  
  static private IProperty<GridGlueService> createVersion() {
    return new AbstractProperty<GridGlueService>( Messages.getString("InfoServiceElement.version"), null) { //$NON-NLS-1$
      @Override
      public Object getValue( final GridGlueService gridGlueService )
      {
        return gridGlueService.getGlueService().version;
      } 
    };
  }
  
  static private IProperty<GridGlueService> createSupported() {
    return new AbstractProperty<GridGlueService>( Messages.getString("InfoServiceElement.supported"), null) { //$NON-NLS-1$
      @Override
      public Object getValue( final GridGlueService gridGlueService )
      {
        return gridGlueService.getGlueService().isSupported();
      } 
    };
  }
}
