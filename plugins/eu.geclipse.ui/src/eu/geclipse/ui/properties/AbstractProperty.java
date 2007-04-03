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

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;


/**
 * Abstract class for read-only property
 *
 */
abstract public class AbstractProperty implements IProperty {

  private PropertyDescriptor descriptor;
  private IPropertySource complexPropertySource;

  /**
   * @param nameString - property name
   * @param categoryString - name of category in which property is. null if property has no category
   */
  public AbstractProperty( final String nameString, final String categoryString )
  {
    this( nameString, categoryString, null );
  }
  
  /**
   * @param nameString - property name
   * @param categoryString - name of category in which property is. null if property has no category
   * @param complexPropertySource - defines structure of complex property  
   */
  public AbstractProperty( final String nameString, final String categoryString, final IPropertySource complexPropertySource ) {
    super();
    this.descriptor = new PropertyDescriptor( this, nameString );
    this.descriptor.setCategory( categoryString );
    this.complexPropertySource = complexPropertySource;
  }

  public IPropertyDescriptor getDescriptor() {
    return this.descriptor;
  }
  
  boolean isComplex() {
    return this.complexPropertySource != null;
  }
  
  IPropertySource getComplexPropertySource() {
    return this.complexPropertySource;
  }
  
}
