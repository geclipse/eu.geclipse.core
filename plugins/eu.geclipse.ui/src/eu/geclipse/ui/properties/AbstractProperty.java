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

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * Abstract class for read-only property
 * 
 * @param <ESourceType> type of object, for which property will be shown
 */
abstract public class AbstractProperty<ESourceType>
  implements IProperty<ESourceType>
{
  private String nameString;
  private String categoryString;
  private ILabelProvider labelProvider;

  /**
   * @param name Property name
   * @param category Property category. May be null
   */
  public AbstractProperty( final String name, final String category ) {
    super();
    this.nameString = name;
    this.categoryString = category;
  }

  /**
   * Have to be implemented in every property. In most cases return property
   * value as String, but it may also return {@link IPropertySource} for complex
   * property
   * 
   * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(Object)
   * @param sourceObject Object, for which property will be displayed
   * @return Property value
   */
  abstract public Object getValue( final ESourceType sourceObject );

  protected String getName() {
    return this.nameString;
  }
  
  void setLabelProvider( final ILabelProvider provider ) {
    this.labelProvider = provider;
  }
  
  public IPropertyDescriptor getDescriptor( final PropertyId<ESourceType> propertyId )
  {
    PropertyDescriptor descriptor = createDescriptor( propertyId,
                                                      this.nameString );
    descriptor.setCategory( this.categoryString );
    descriptor.setLabelProvider( this.labelProvider );
    return descriptor;
  }

  protected PropertyDescriptor createDescriptor( final PropertyId<ESourceType> propertyId,
                                                 final String name )
  {
    return new PropertyDescriptor( propertyId, name );
  }
}
