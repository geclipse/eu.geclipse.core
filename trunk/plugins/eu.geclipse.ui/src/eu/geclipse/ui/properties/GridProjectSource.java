/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     PSNC - Mariusz Wojtysiak
 *           
 *****************************************************************************/
package eu.geclipse.ui.properties;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import eu.geclipse.core.model.IGridProject;


/**
 * Properties for {@link IGridProject}
 *
 */
public class GridProjectSource extends AbstractPropertySource<IGridProject>{
  static private List<IPropertyDescriptor> staticDescriptors;
  
  /**
   * @param gridProject The project, for which properties will be displayed 
   */
  public GridProjectSource( final IGridProject gridProject ) {
    super( gridProject );
  }  
  
  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return GridProjectSource.class;
  }

  @Override
  public List<IPropertyDescriptor> getStaticDescriptors()
  {
    if( staticDescriptors == null ) {
      staticDescriptors = AbstractPropertySource.createDescriptors( createProperties(),
                                                                    getPropertySourceClass() );
    }
    return staticDescriptors;
  }
  
  static private List<IProperty<IGridProject>> createProperties() {
    List<IProperty<IGridProject>> propertiesList = new ArrayList<IProperty<IGridProject>>( 5 );
    propertiesList.add( createName() );
    propertiesList.add( createVO() );
    return propertiesList;
  }
  
  static IProperty<IGridProject> createName() {
    return new AbstractProperty<IGridProject>( Messages.getString("GridProjectSource.propertyName"), null ) { //$NON-NLS-1$

      @Override
      public Object getValue( final IGridProject gridProject )
      {
        return gridProject.getName();
      }      
    };
  }
  
  static IProperty<IGridProject> createVO() {
    AbstractProperty<IGridProject> property = new AbstractProperty<IGridProject>( Messages.getString("GridProjectSource.propertyVO"), null ) { //$NON-NLS-1$

      @Override
      public Object getValue( final IGridProject gridProject )
      {        
        Object value = null;
        
        if( gridProject.getVO() != null ) {
          value =  gridProject.getVO().getName();
        }
        
        return value;
      }      
    };
    
    return property;
  }

}
