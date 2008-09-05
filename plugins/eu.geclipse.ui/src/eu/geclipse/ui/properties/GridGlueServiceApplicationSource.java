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

import eu.geclipse.info.model.GridGlueServiceApplication;


/**
 * @author tnikos
 *
 */
public class GridGlueServiceApplicationSource
extends AbstractPropertySource<GridGlueServiceApplication>{
  
  static private List<IProperty<GridGlueServiceApplication>> staticDescriptors;
  
  /**
   * @param gridGlueServiceApplication a valid GridGlueServiceApplication object
   */
  public GridGlueServiceApplicationSource(final GridGlueServiceApplication gridGlueServiceApplication)
  {
    super(gridGlueServiceApplication);
  }

  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return GridGlueServiceApplicationSource.class;
  }

  @Override
  protected List<IProperty<GridGlueServiceApplication>> getStaticProperties() {
    if( staticDescriptors == null ) {
      staticDescriptors = createProperties();
    }
    
    return staticDescriptors;
  }
  
  static private List<IProperty<GridGlueServiceApplication>> createProperties() {
    List<IProperty<GridGlueServiceApplication>> propertiesList = 
      new ArrayList<IProperty<GridGlueServiceApplication>>( 1 );
    propertiesList.add( createEndPoint() );
    propertiesList.add( createStatus() );
    
    return propertiesList;
  }
  
  static private IProperty<GridGlueServiceApplication> createEndPoint() {
    return new AbstractProperty<GridGlueServiceApplication>( "Endpoint", null) { //$NON-NLS-1$
      @Override
      public Object getValue( final GridGlueServiceApplication gridGlueServiceApplication )
      {
        return gridGlueServiceApplication.getGlueApplication().endpoint;
      } 
    };
  }
  
  static private IProperty<GridGlueServiceApplication> createStatus() {
    return new AbstractProperty<GridGlueServiceApplication>( "URI", null) { //$NON-NLS-1$
      @Override
      public Object getValue( final GridGlueServiceApplication gridGlueServiceApplication )
      {
        return gridGlueServiceApplication.getGlueApplication().uri;
      } 
    };
  }
}
