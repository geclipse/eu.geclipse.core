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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.ui.properties;

import java.util.List;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.views.properties.IPropertySource;

import eu.geclipse.ui.Extensions;


/**
 * Adapter factory for properties Register here new class inheriting
 * {@link AbstractPropertySource}
 */
public class PropertiesAdapterFactory implements IAdapterFactory {

  static private PropertiesAdapterFactory singleton;

  @SuppressWarnings("unchecked")
  public Class[] getAdapterList()
  {
    return new Class[]{
      IPropertySource.class
    };
  }

  @SuppressWarnings("unchecked")
  public Object getAdapter( final Object adaptableObject,
                            final Class adapterType )
  {
    IPropertySource propertySource = null;
    if( adapterType == IPropertySource.class ) {
      List<IPropertiesFactory> factoriesList = Extensions.getPropertiesFactories( adaptableObject.getClass() );
      
      if( !factoriesList.isEmpty() ) {
        propertySource = new PropertySourceProxy( factoriesList, adaptableObject );
      }      
    }
    return propertySource;
  }

  /**
   * Register adapter in eclipse
   */
  static public void register() {
    if( singleton == null ) {
      singleton = new PropertiesAdapterFactory();
      Platform.getAdapterManager().registerAdapters( singleton,
                                                     Object.class );
    }
  }

  /**
   * Unregister adapter
   */
  static public void unregister() {
    if( singleton != null ) {
      Platform.getAdapterManager().unregisterAdapters( singleton );
      singleton = null;
    }
  }
  
  
}
