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
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.voms.vo.VomsVirtualOrganization;
import eu.geclipse.voms.vo.VomsVoProperties;


/**
 * Properties for {@link VomsVirtualOrganization}
 */
public class VomsPropertySource
  extends AbstractPropertySource<VomsVirtualOrganization>
{

  static private IPropertyDescriptor[] staticDescriptors;

  /**
   * @param voms - voms which properties will be displayed
   */
  VomsPropertySource( final VomsVirtualOrganization voms ) {
    super( voms );
  }

  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return VomsPropertySource.class;
  }

  @Override
  protected IPropertyDescriptor[] getStaticDescriptors()
  {
    if( staticDescriptors == null ) {
      staticDescriptors = AbstractPropertySource.createDescriptors( createProperties(),
                                                                    getPropertySourceClass() );
    }
    return staticDescriptors;
  }

  static private List<IProperty<VomsVirtualOrganization>> createProperties() {
    List<IProperty<VomsVirtualOrganization>> propertiesList = new ArrayList<IProperty<VomsVirtualOrganization>>( 1 );
    propertiesList.add( createHost() );
    propertiesList.add( createHostDN() );
    propertiesList.add( createPort() );
    return propertiesList;
  }

  static private IProperty<VomsVirtualOrganization> createHost() {
    return new AbstractProperty<VomsVirtualOrganization>( Messages.getString( "VomsPropertySource.propertyHost" ), //$NON-NLS-1$
                                                          Messages.getString( "VomsPropertySource.categoryHost" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final VomsVirtualOrganization sourceObject )
      {
        String valueString = null;
        try {
          VomsVoProperties vomsProperties = sourceObject.getProperties();
          if( vomsProperties != null ) {
            valueString = vomsProperties.getHost();
          }
        } catch( GridModelException e ) {
          valueString = e.getMessage();
        }
        return valueString;
      }
    };
  }

  static private IProperty<VomsVirtualOrganization> createHostDN() {
    return new AbstractProperty<VomsVirtualOrganization>( Messages.getString( "VomsPropertySource.propertyHostDN" ), //$NON-NLS-1$
                                                          Messages.getString( "VomsPropertySource.categoryHost" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final VomsVirtualOrganization sourceObject )
      {
        String valueString = null;
        try {
          VomsVoProperties vomsProperties = sourceObject.getProperties();
          if( vomsProperties != null ) {
            valueString = vomsProperties.getHostDN();
          }
        } catch( GridModelException e ) {
          valueString = e.getMessage();
        }
        return valueString;
      }
    };
  }

  static private IProperty<VomsVirtualOrganization> createPort() {
    return new AbstractProperty<VomsVirtualOrganization>( Messages.getString( "VomsPropertySource.propertyPort" ), //$NON-NLS-1$
                                                          Messages.getString( "VomsPropertySource.categoryHost" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final VomsVirtualOrganization sourceObject )
      {
        String valueString = null;
        try {
          VomsVoProperties vomsProperties = sourceObject.getProperties();
          if( vomsProperties != null ) {
            valueString = String.valueOf( vomsProperties.getPort() );
          }
        } catch( GridModelException e ) {
          valueString = e.getMessage();
        }
        return valueString;
      }
    };
  }
}
