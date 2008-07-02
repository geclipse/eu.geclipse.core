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

  abstract protected List<IProperty<ESourceType>> getStaticProperties();

  /** 
   * @return descriptors for all properties valid for current {@link AbstractPropertySource#sourceObject}
   * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
   */
  public List<IPropertyDescriptor> getPropertyDescriptors() {
    List<IPropertyDescriptor> descriptorsList = new ArrayList<IPropertyDescriptor>();    
    appendDescriptors( descriptorsList );    
    return descriptorsList;
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

  protected void appendDescriptors( final List<IPropertyDescriptor> descriptorsList )
  {
    for( IProperty<ESourceType> property : getStaticProperties() ) {
      if( property.isShowEmptyValue() 
          || property.getValue( this.sourceObject ) != null ) {
        descriptorsList.add( property.getDescriptor( getPropertySourceClass() ) );
      }      
    }
    
    if( this.childPropSourceList != null ) {
     for( AbstractPropertySource<?> childPropertySource : this.childPropSourceList ) {
       childPropertySource.appendDescriptors( descriptorsList );
     }
    }   
  }
  
  protected void addChildSource( final AbstractPropertySource<?> childPropertySource ) {
    this.childPropSourceList.add( childPropertySource );
  }
}
