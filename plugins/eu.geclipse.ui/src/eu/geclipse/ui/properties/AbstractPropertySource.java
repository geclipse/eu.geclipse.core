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
import java.util.Arrays;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import eu.geclipse.core.model.IGridElement;


/**
 * This class creates array of properties for given Grid object.
 * Every inherited class creates properties for one type of {@link IGridElement}
 */
abstract class AbstractPropertySource implements IPropertySource {

  private IPropertyDescriptor[] propertyDescriptors;    // descriptors used for IPropertySource
  private IProperty[] properties;                       // g-eclipse properties

  /**
   * 
   */
  public AbstractPropertySource() {
    super();
  }

  /**
   * Create {@link AbstractProperty} objects
   * @return table of {@link IProperty}
   */
  abstract protected IProperty[] createProperties();

  public IPropertyDescriptor[] getPropertyDescriptors() {
    if( this.propertyDescriptors == null ) {      
      this.propertyDescriptors = createDescriptors( getProperties() );
    }
    return this.propertyDescriptors;
  }
  
  protected IProperty[] getProperties() {
    if( this.properties == null ) {
      this.properties = createProperties();
    }
    
    return this.properties;
  }
   
  public Object getEditableValue() {
    // Edit not supported
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
   */
  public Object getPropertyValue( final Object id ) {
    Object value = null;
    if( id instanceof AbstractProperty ) {
      AbstractProperty property = ( AbstractProperty )id;
      if( property.isComplex() ) {
        value = property.getComplexPropertySource();
      } else {
        value = property.getValue();
      }
    }
    return value;
  }

  public boolean isPropertySet( final Object id ) {
    //  Edit not supported
    return false;
  }

  public void resetPropertyValue( final Object id ) {
    //  Edit not supported
  }

  public void setPropertyValue( final Object id, final Object value ) {
    //  Edit not supported
  }

  private IPropertyDescriptor[] createDescriptors( final IProperty[] prop ) {
    IPropertyDescriptor [] descriptors = new IPropertyDescriptor[ prop.length ];
    
    for( int index = 0; index < prop.length; index++ ) {
      descriptors[ index ] = prop[ index ].getDescriptor();
    }
    
    return descriptors;
  }  
  
  /**
   * Usefull method when one PropertySource uses other PropertySource
   * e.g. {@link JobPropertySource} uses properties from {@link JobDescPropertySource}
   * @param firstProperties
   * @param secondProperties
   * @return joined properties
   */
  protected IProperty[] joinProperties( final IProperty[] firstProperties, final IProperty[] secondProperties )
  {
    ArrayList<IProperty> result = new ArrayList<IProperty>( firstProperties.length + secondProperties.length );
    result.addAll( Arrays.asList( firstProperties ) );
    result.addAll( Arrays.asList( secondProperties ) );
    return result.toArray( new IProperty[0] );
  }
  
  
}
