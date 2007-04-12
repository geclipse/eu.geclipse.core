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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;


/**
 * This class creates array of properties for any object of type ESourceType
 * Here list of {@link IProperty} objects are connected with instance of ESourceType
 * 
 * If {@link AbstractPropertySource#sourceObject} contains also properties defined in another class inherited from {@link AbstractPropertySource},
 * then just add these properties using {@link AbstractPropertySource#addChildSource(AbstractPropertySource)} 
 */
abstract class AbstractPropertySource<ESourceType> implements IPropertySource {
  private ESourceType sourceObject;
  private List<AbstractPropertySource<?>> childPropSourceList = new LinkedList<AbstractPropertySource<?>>();

  /**
   * All descriptors, also from child property sources
   */
  private IPropertyDescriptor[] descriptors = null; 

  /**
   * @param sourceObject Object, for which properties will be displayed
   */
  public AbstractPropertySource( final ESourceType sourceObject ) {
    super();
    this.sourceObject = sourceObject;
  }
  
  /**
   * @return Just {@link Class} object represents class inherited from {@link AbstractPropertySource}
   * Used to recognize, which of child {@link AbstractPropertySource} should return property value
   */
  abstract protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass();
  
  /**
   * @return Property descriptors defined as static member, without child properties
   */
  abstract protected IPropertyDescriptor[] getStaticDescriptors();

  /* (non-Javadoc)
   * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
   */
  public Object getEditableValue() {    
    return null;    // Editable properties not supported   
  }

  /** 
   * Returns descriptors for all properties valid for current {@link AbstractPropertySource#sourceObject} 
   * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
   */
  public IPropertyDescriptor[] getPropertyDescriptors() {
    
    if( this.descriptors == null ) {      

      if( this.childPropSourceList == null
        || this.childPropSourceList.isEmpty() )   
      {
        this.descriptors = getStaticDescriptors();
      }
      else {
        int descriptorsSize = getStaticDescriptors().length;
        
        for( AbstractPropertySource<?> source : this.childPropSourceList ) {
          descriptorsSize += source.getPropertyDescriptors().length;
        }
                
        ArrayList<IPropertyDescriptor> descList = new ArrayList<IPropertyDescriptor>( descriptorsSize );
        
        descList.addAll( Arrays.asList( getStaticDescriptors() ) );
        
        for( AbstractPropertySource<?> source : this.childPropSourceList ) {
          descList.addAll( Arrays.asList( source.getPropertyDescriptors() ) );
        }
        
        this.descriptors = descList.toArray( new IPropertyDescriptor[ descriptorsSize ] );
      }
    }
    
    return this.descriptors;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  public Object getPropertyValue( final Object id )
  {
    Object result = null;
    if( id instanceof PropertyId ) {
      PropertyId<?> propertyId = ( PropertyId )id;
      if( propertyId.getSourceClass() == getPropertySourceClass() ) {   // get value from this source
        PropertyId<ESourceType> thisPropertyId = ( PropertyId<ESourceType> )propertyId;
        result = thisPropertyId.getProperty().getValue( this.sourceObject );
      } else {  // get value from child sources
        Iterator<AbstractPropertySource<?>> iterator = this.childPropSourceList.iterator();
        while( iterator.hasNext() && result == null ) {
          AbstractPropertySource<?> childSource = iterator.next();
          if( propertyId.getSourceClass() == childSource.getPropertySourceClass() )
          {
            result = childSource.getPropertyValue( propertyId );
          }
        }
      }
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
   */
  public boolean isPropertySet( final Object id ) {
    return false;   // Editable properties not supported
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.views.properties.IPropertySource#resetPropertyValue(java.lang.Object)
   * Editable properties not supported
   */
  public void resetPropertyValue( final Object id ) {
    //  Editable properties not supported
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object, java.lang.Object)
   */
  public void setPropertyValue( final Object id, final Object value ) {
    // Editable properties not supported    
  } 

  protected static <ESourceType> IPropertyDescriptor[] createDescriptors( final List<IProperty<ESourceType>> properties, 
                                                                              final Class<? extends AbstractPropertySource<?>> propSourceClass ) {
    ArrayList<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>( properties.size() );
    
    for( IProperty<ESourceType> property : properties ) {      
      PropertyId<ESourceType> id = new PropertyId<ESourceType>( propSourceClass, property );
      PropertyDescriptor descriptor = new PropertyDescriptor( id, property.getName() );
      descriptor.setCategory( property.getCategory() );
      descriptors.add( descriptor );
    }
    
    return descriptors.toArray( new IPropertyDescriptor[ descriptors.size() ] );
  }  
  
  protected void addChildSource( final AbstractPropertySource<?> childPropertySource ) {
    this.childPropSourceList.add( childPropertySource );
  }
}
