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
import java.util.LinkedList;
import java.util.List;
import org.eclipse.ui.views.properties.IPropertyDescriptor;


/**
 * This class creates array of properties for any object of type ESourceType
 * Here list of {@link IProperty} objects are connected with instance of ESourceType
 * 
 * If {@link AbstractPropertySource#sourceObject} contains also properties defined in another class inherited from {@link AbstractPropertySource},
 * then just add these properties using {@link AbstractPropertySource#addChildSource(AbstractPropertySource)} 
 * @param <ESourceType> type of object, for which properties will be shown
 */
abstract public class AbstractPropertySource<ESourceType> {
  private ESourceType sourceObject;
  private List<AbstractPropertySource<?>> childPropSourceList = new LinkedList<AbstractPropertySource<?>>();

  /**
   * All descriptors, also from child property sources
   */
  private List<IPropertyDescriptor> descriptors = null; 

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
  abstract protected List<IPropertyDescriptor> getStaticDescriptors();

  /** 
   * @return descriptors for all properties valid for current {@link AbstractPropertySource#sourceObject}
   * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
   */
  public List<IPropertyDescriptor> getPropertyDescriptors() {
    if( this.descriptors == null ) {      

      if( this.childPropSourceList == null
        || this.childPropSourceList.isEmpty() )   
      {
        this.descriptors = getStaticDescriptors();
      }
      else {
        int descriptorsSize = getStaticDescriptors().size();
        
        for( AbstractPropertySource<?> source : this.childPropSourceList ) {
          descriptorsSize += source.getPropertyDescriptors().size();
        }
                
        ArrayList<IPropertyDescriptor> descList = new ArrayList<IPropertyDescriptor>( descriptorsSize );
        
        descList.addAll( getStaticDescriptors() );
        
        for( AbstractPropertySource<?> source : this.childPropSourceList ) {
          descList.addAll( source.getPropertyDescriptors() );
        }
        
        this.descriptors = descList;
      }
    }
    
    return this.descriptors;
  }

  @SuppressWarnings("unchecked")
  Object getPropertyValue( final IProperty property )
  {
    return property.getValue( this.sourceObject );
  }
  
  AbstractPropertySource<?> getPropertySourceInstanceFor( final Class<? extends AbstractPropertySource<?>> propertySourceClass )
  {
    AbstractPropertySource<?> propertySource = null;
    if( this.getPropertySourceClass() == propertySourceClass ) {
      propertySource = this;
    } else {
      Iterator<AbstractPropertySource<?>> iterator = this.childPropSourceList.iterator();
      while( iterator.hasNext() && propertySource == null ) {
        AbstractPropertySource<?> childSource = iterator.next();
        propertySource = childSource.getPropertySourceInstanceFor( propertySourceClass );
      }
    }
    return propertySource;
  }

  protected static <ESourceType> List<IPropertyDescriptor> createDescriptors( final List<IProperty<ESourceType>> properties,
                                                                              final Class<? extends AbstractPropertySource<?>> propSourceClass )
  {
    ArrayList<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>( properties.size() );
    for( IProperty<ESourceType> property : properties ) {
      PropertyId<ESourceType> id = new PropertyId<ESourceType>( propSourceClass,
                                                                property );
      descriptors.add( property.getDescriptor( id ) );
    }
    return descriptors;
  }
  
  protected void addChildSource( final AbstractPropertySource<?> childPropertySource ) {
    this.childPropSourceList.add( childPropertySource );
  }
}
