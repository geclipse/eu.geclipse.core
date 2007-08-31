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
  private boolean showEmptyValue;
  private PropertyDescriptor descriptor;

  /**
   * @param name Property name
   * @param category Property category. May be null
   * @param showEmptyValue if false, then property will be hidden when its value is empty 
   */
  public AbstractProperty( final String name, final String category, final boolean showEmptyValue ) {
    super();
    this.nameString = name;
    this.categoryString = category;
    this.showEmptyValue = showEmptyValue;
  }
  
  /**
   * @param name Property name
   * @param category Property category. May be null
   */
  public AbstractProperty( final String name, final String category ) {
    this( name, category, true );
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
  
  public IPropertyDescriptor getDescriptor( final Class<? extends AbstractPropertySource<?>> propSourceClass )
  {
    if( this.descriptor == null ) {
      this.descriptor = createDescriptor( new PropertyId<ESourceType>( propSourceClass, this ), this.nameString );
      this.descriptor.setCategory( this.categoryString );
      this.descriptor.setLabelProvider( this.labelProvider );
    }
    
    return this.descriptor;
  }

  protected PropertyDescriptor createDescriptor( final PropertyId<ESourceType> propertyId,
                                                 final String name )
  {
    return new PropertyDescriptor( propertyId, name );
  }
  
  /**
   * Formats number of bytes (e.g. file length ) as pretty looking String
   * @param value number of bytes
   * @return formatted String
   */
  protected String getBytesFormattedString( final double value ) {
    String formattedString;
    double smallerValue = value;
    ArrayList<String> suffixList = new ArrayList<String>( 5 );
    suffixList.add( "B" );  //$NON-NLS-1$
    suffixList.add( "kB" );  //$NON-NLS-1$
    suffixList.add( "MB" );  //$NON-NLS-1$
    suffixList.add( "GB" );  //$NON-NLS-1$
    suffixList.add( "TB" );  //$NON-NLS-1$
    Iterator<String> iterator = suffixList.iterator();
    String suffixString = iterator.next();
    while( iterator.hasNext() && smallerValue > 1024 ) {
      suffixString = iterator.next();
      smallerValue /= 1024;
    }
    if( ( ( int )smallerValue ) == smallerValue ) {
      formattedString = String.format( "%d", Integer.valueOf( ( int )smallerValue ) );  //$NON-NLS-1$
    } else {
      formattedString = String.format( "%.2f", Double.valueOf( smallerValue ) );  //$NON-NLS-1$
    }
    return formattedString + ' ' + suffixString;
  }
  
  public boolean isShowEmptyValue() {
    return this.showEmptyValue;
  }
}
