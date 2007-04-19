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
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import eu.geclipse.core.model.IVirtualOrganization;


/**
 * Properties for {@link IVirtualOrganization}
 *
 */
class VOPropertySource extends AbstractPropertySource<IVirtualOrganization> {
   static private List<IPropertyDescriptor> staticDescriptors;
  
  /**
   * @param virtualOrganization - vo for which properties be displayed
   */
  public VOPropertySource( final IVirtualOrganization virtualOrganization ) {
    super( virtualOrganization );
  }
  
  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return VOPropertySource.class;
  }

  @Override
  protected List<IPropertyDescriptor> getStaticDescriptors()
  {
    if( staticDescriptors == null ) {
      staticDescriptors = AbstractPropertySource.createDescriptors( createProperties(), getPropertySourceClass() );    
    }
    
    return staticDescriptors;
  }
  
  static private List<IProperty<IVirtualOrganization>> createProperties() {
    List<IProperty<IVirtualOrganization>> propertiesList = new ArrayList<IProperty<IVirtualOrganization>>( 1 );
    propertiesList.add( createName() );
    propertiesList.add( createType() );
    return propertiesList;
  }
  
  static private IProperty<IVirtualOrganization> createName() {
    return new AbstractProperty<IVirtualOrganization>( Messages.getString("VOPropertySource.propertyName"),   //$NON-NLS-1$
                                 Messages.getString("VOPropertySource.categoryGeneral") ) { //$NON-NLS-1$
      @Override
      public Object getValue( final IVirtualOrganization vo )
      {
        return vo.getName();
      } 
    };
  }
  
  static private IProperty<IVirtualOrganization> createType() {
    return new AbstractProperty<IVirtualOrganization>( Messages.getString("VOPropertySource.propertyType"),   //$NON-NLS-1$
                                 Messages.getString("VOPropertySource.categoryGeneral") ) { //$NON-NLS-1$
      @Override
      public Object getValue( final IVirtualOrganization vo )
      {
        return vo.getTypeName();
      } 
    };
  }
  
  static ILabelProvider getLabelProvider() {
    return new LabelProvider() {

      /* (non-Javadoc)
       * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
       */
      @Override
      public String getText( final Object element )
      {
        String string = null;
        
        if( element instanceof IVirtualOrganization ) {
          string = ( ( IVirtualOrganization ) element ).getName();
        }
 
        return string;
      }      
    };
  }
}
