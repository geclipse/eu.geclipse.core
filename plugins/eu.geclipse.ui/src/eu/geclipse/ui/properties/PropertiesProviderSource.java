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
 *****************************************************************************/
package eu.geclipse.ui.properties;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.core.model.IPropertiesProvider;



/**
 * Properties source for object implementing IPropertiesProvider
 */
public class PropertiesProviderSource
extends AbstractPropertySource<IPropertiesProvider> 
{
  IPropertiesProvider propertyProvider;

  /**
   * @param sourceObject
   */
  public PropertiesProviderSource( final IPropertiesProvider sourceObject ) {
    super( sourceObject );
    this.propertyProvider = sourceObject;
  }

  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass() {
    return PropertiesProviderSource.class;
  }

  @Override
  protected List<IProperty<IPropertiesProvider>> getStaticProperties() {
    List<IProperty<IPropertiesProvider>> list = new ArrayList<IProperty<IPropertiesProvider>>();

    for( String key : this.propertyProvider.getPropertyKeys() ) {
      list.add( createProperty( key ) );
    }
    
    return list;
  }
  
  private IProperty<IPropertiesProvider> createProperty( final String key ) {
    return new AbstractProperty<IPropertiesProvider>( key, PropertiesProviderSource.this.propertyProvider.getCategory(), false ) {

      @Override
      public Object getValue( final IPropertiesProvider sourceObject ) {
        return PropertiesProviderSource.this.propertyProvider.getPropertyValue( key );
      }
    };
  }
}
