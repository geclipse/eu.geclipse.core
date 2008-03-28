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
import eu.geclipse.info.model.GridGlueComputing;


public class GridGlueComputingSource extends AbstractPropertySource<GridGlueComputing>{

  static private List<IProperty<GridGlueComputing>> staticDescriptors;
  
  public GridGlueComputingSource(final GridGlueComputing gridGlueComputing)
  {
    super(gridGlueComputing);
  }
  
  public Object getEditableValue() {
    return this;
  }
  
  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return GridGlueComputingSource.class;
  }

  @Override
  protected List<IProperty<GridGlueComputing>> getStaticProperties() {
    
    if( staticDescriptors == null ) {
      staticDescriptors = createProperties();
    }
    
    return staticDescriptors;
  }
  
  static private List<IProperty<GridGlueComputing>> createProperties() {
    List<IProperty<GridGlueComputing>> propertiesList = new ArrayList<IProperty<GridGlueComputing>>( 1 );
    propertiesList.add( createHostName() );
    propertiesList.add( createInformationServiceURL() );
    propertiesList.add( Status() );
    propertiesList.add( LRMSType() );
    propertiesList.add( LRMSVersion() );
    
    return propertiesList;
  }
  
  static private IProperty<GridGlueComputing> createHostName() {
    return new AbstractProperty<GridGlueComputing>( Messages.getString("InfoComputingElement.hostname"), null) { //$NON-NLS-1$
      @Override
      public Object getValue( final GridGlueComputing gridGlueComputing )
      {
        return gridGlueComputing.getGlueCe().HostName;
      } 
    };
  }
  
  static private IProperty<GridGlueComputing> createInformationServiceURL() {
    return new AbstractProperty<GridGlueComputing>( Messages.getString("InfoComputingElement.informationServiceURL"), null) { //$NON-NLS-1$
      @Override
      public Object getValue( final GridGlueComputing gridGlueComputing )
      {
        return gridGlueComputing.getGlueCe().InformationServiceURL;
      } 
    };
  }
  
  static private IProperty<GridGlueComputing> Status() {
    return new AbstractProperty<GridGlueComputing>( Messages.getString("InfoComputingElement.status"), null) { //$NON-NLS-1$
      @Override
      public Object getValue( final GridGlueComputing gridGlueComputing )
      {
        return gridGlueComputing.getGlueCe().Status;
      } 
    };
  }
  
  static private IProperty<GridGlueComputing> LRMSType() {
    return new AbstractProperty<GridGlueComputing>( Messages.getString("InfoComputingElement.LRMSType"), null) { //$NON-NLS-1$
      @Override
      public Object getValue( final GridGlueComputing gridGlueComputing )
      {
        return gridGlueComputing.getGlueCe().LRMSType;
      } 
    };
  }
    
    static private IProperty<GridGlueComputing> LRMSVersion() {
      return new AbstractProperty<GridGlueComputing>( Messages.getString("InfoComputingElement.LRMSVersion"), null) { //$NON-NLS-1$
        @Override
        public Object getValue( final GridGlueComputing gridGlueComputing )
        {
          return gridGlueComputing.getGlueCe().LRMSVersion;
        } 
      };
  }
}
