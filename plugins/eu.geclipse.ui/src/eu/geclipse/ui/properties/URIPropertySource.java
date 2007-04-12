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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.ui.views.properties.IPropertyDescriptor;


/**
 * Properties for {@link URI}
 */
public class URIPropertySource extends AbstractPropertySource<URI> {

  static private IPropertyDescriptor[] staticDescriptors;

  /**
   * @param uri - URI for which properties will be displayed
   */
  public URIPropertySource( final URI uri ) {
    super( uri );
  }

  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return URIPropertySource.class;
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

  static private List<IProperty<URI>> createProperties() {
    List<IProperty<URI>> propertiesList = new ArrayList<IProperty<URI>>( 4 );
    propertiesList.add( createHost() );
    propertiesList.add( createPath() );
    propertiesList.add( createPort() );
    propertiesList.add( createScheme() );
    return propertiesList;
  }

  static private IProperty<URI> createHost() {
    return new AbstractProperty<URI>( Messages.getString( "URIPropertySource.propertyHost" ), //$NON-NLS-1$ 
                                      Messages.getString( "URIPropertySource.categoryUri" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final URI uri )
      {
        return uri.getHost();
      }
    };
  }

  static private IProperty<URI> createPath() {
    return new AbstractProperty<URI>( Messages.getString( "URIPropertySource.propertyPath" ), //$NON-NLS-1$
                                      Messages.getString( "URIPropertySource.categoryUri" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final URI uri )
      {
        return uri.getPath();
      }
    };
  }

  static private IProperty<URI> createPort() {
    return new AbstractProperty<URI>( Messages.getString( "URIPropertySource.propertyPort" ), //$NON-NLS-1$
                                      Messages.getString( "URIPropertySource.categoryUri" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final URI uri )
      {
        String portString = null;
        if( uri.getPort() != -1 ) {
          portString = String.valueOf( uri.getPort() );
        }
        return portString;
      }
    };
  }

  static private IProperty<URI> createScheme() {
    return new AbstractProperty<URI>( Messages.getString( "URIPropertySource.propertyScheme" ), //$NON-NLS-1$
                                      Messages.getString( "URIPropertySource.categoryUri" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final URI uri )
      {
        String portString = null;
        if( uri.getPort() != -1 ) {
          portString = String.valueOf( uri.getScheme() );
        }
        return portString;
      }
    };
  }
}
