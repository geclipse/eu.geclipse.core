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
import eu.geclipse.glite.info.BDIIService;


/**
 * Properties for BDIIService
 */
public class BDIIServicePropertySource
  extends AbstractPropertySource<BDIIService>
{

  static private IPropertyDescriptor[] staticDescriptors;

  /**
   * @param bdiiService - service, for which properties will be shown
   */
  public BDIIServicePropertySource( final BDIIService bdiiService ) {
    super( bdiiService );
    if( bdiiService.getURI() != null ) {
      addChildSource( new URIPropertySource( bdiiService.getURI() ) );
    }
  }

  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return BDIIServicePropertySource.class;
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

  static private List<IProperty<BDIIService>> createProperties() {
    List<IProperty<BDIIService>> propertiesList = new ArrayList<IProperty<BDIIService>>( 5 );
    propertiesList.add( createName() );
    return propertiesList;
  }

  static private IProperty<BDIIService> createName() {
    return new AbstractProperty<BDIIService>( Messages.getString( "BDIIServicePropertySource.propertyName" ), null ) { //$NON-NLS-1$

      @Override
      public Object getValue( final BDIIService bdiiService )
      {
        return bdiiService.getName();
      }
    };
  }
}
