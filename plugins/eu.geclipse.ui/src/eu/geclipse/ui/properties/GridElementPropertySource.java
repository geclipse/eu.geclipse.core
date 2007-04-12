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
import eu.geclipse.core.model.IGridElement;

/**
 * Simple property source for GridElement.
 * Used when specialized property source is not defined for objects inherited from {@link IGridElement} 
 */
public class GridElementPropertySource extends AbstractPropertySource<IGridElement> {
  static private IPropertyDescriptor[] staticDescriptors;

  /**
   * @param gridElement - object from which properties will be displayed
   */
  public GridElementPropertySource( final IGridElement gridElement ) {
    super( gridElement );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.properties.AbstractPropertySource#getPropertySourceClass()
   */
  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return GridElementPropertySource.class;
  }

  @Override
  protected IPropertyDescriptor[] getStaticDescriptors()
  {
    if( staticDescriptors == null ) {
      staticDescriptors = AbstractPropertySource.createDescriptors( createProperties(), getPropertySourceClass() );    
    }
    
    return staticDescriptors;
  }
  
  static private List<IProperty<IGridElement>> createProperties() {
    List<IProperty<IGridElement>> propertiesList = new ArrayList<IProperty<IGridElement>>( 1 );
    propertiesList.add( createPropName() );
    return propertiesList;
  }
  
  static private IProperty<IGridElement> createPropName() {
    return new AbstractProperty<IGridElement>( Messages.getString( "GridElementPropertySource.propertyName" ), //$NON-NLS-1$
                                 null )
    {
      @Override
      public Object getValue( final IGridElement gridElement )
      {
        return gridElement.getName();
      }
    };
  }
}
