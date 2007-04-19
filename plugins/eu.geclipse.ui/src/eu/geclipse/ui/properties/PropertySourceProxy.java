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
import java.util.Iterator;
import java.util.List;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;


/**
 *
 */
class PropertySourceProxy implements IPropertySource {
  private final List<AbstractPropertySource<?>> propertySourcesList = new ArrayList<AbstractPropertySource<?>>();
  private final Object sourceObject;

  PropertySourceProxy( final List<IPropertiesFactory> factoriesList, final Object sourceObject ) {
    super();
    this.sourceObject = sourceObject;
    
    for( IPropertiesFactory factory : factoriesList ) {
      List<AbstractPropertySource<?>> sourcesList = factory.getPropertySources( sourceObject );
      
      if( sourcesList != null
          && !sourcesList.isEmpty() ) {
        this.propertySourcesList.addAll( sourcesList );
      }
    }
  }

  public Object getEditableValue() {
    return this.sourceObject;
  }

  public IPropertyDescriptor[] getPropertyDescriptors() {
    int descriptorsSize = 0;
    for( AbstractPropertySource<?> propertySource : this.propertySourcesList ) {
      descriptorsSize = propertySource.getPropertyDescriptors().size();
    }
    
    List<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>( descriptorsSize );
    
    for( AbstractPropertySource<?> propertySource : this.propertySourcesList ) {
      descriptors.addAll( propertySource.getPropertyDescriptors() );      
    }

    return descriptors.toArray( new IPropertyDescriptor[descriptors.size()] );
  }

  public Object getPropertyValue( final Object finalId ) {
    Object value = null;
    
    if( finalId instanceof PropertyId ) {
      PropertyId<?> propertyId = ( PropertyId<?> )finalId;
      AbstractPropertySource<?> propertySource = findPropertySource( propertyId.getSourceClass() );
      
      if( propertySource != null ) {
        value = propertySource.getPropertyValue( propertyId.getProperty() );
      }      
    }
    
    return value;
  }

  public boolean isPropertySet( final Object finalId ) {
    // Editable properties not supported
    return false;
  }

  public void resetPropertyValue( final Object finalId ) {
    //  Editable properties not supported    
  }

  public void setPropertyValue( final Object finalId, final Object finalValue ) {
    //  Editable properties not supported    
  }
  
  AbstractPropertySource<?> findPropertySource( final Class<? extends AbstractPropertySource<?>> propertySourceClass ) {
    AbstractPropertySource<?> propertySource = null;    
    Iterator<AbstractPropertySource<?>> iterator = this.propertySourcesList.iterator();    
    
    while( iterator.hasNext() && propertySource == null ) {
      AbstractPropertySource<?> currentPropSource = iterator.next();
      
      propertySource = currentPropSource.getPropertySourceInstanceFor( propertySourceClass );
    }   
    
    return propertySource;
  }
}
