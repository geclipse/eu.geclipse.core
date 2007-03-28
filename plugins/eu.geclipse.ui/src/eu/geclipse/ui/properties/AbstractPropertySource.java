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
import eu.geclipse.core.model.IGridElement;


/**
 * This class creates array of properties for given Grid object.
 * Every inherited class creates properties for one type of {@link IGridElement}
 */
abstract class AbstractPropertySource implements IPropertySource {

  private IPropertyDescriptor[] propertyDescriptors;

  /**
   * 
   */
  public AbstractPropertySource() {
    super();
  }

  /**
   * Create {@link AbstractProperty} objects
   * @return table of {@link IPropertyDescriptor} obtained from {@link AbstractProperty#getDescriptor()}
   */
  abstract protected IPropertyDescriptor[] createPropertyDescriptors();

  public IPropertyDescriptor[] getPropertyDescriptors() {
    if( this.propertyDescriptors == null ) {
      this.propertyDescriptors = createPropertyDescriptors();
    }
    return this.propertyDescriptors;
  }

  public Object getEditableValue() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
   */
  public Object getPropertyValue( final Object id ) {
    String valueString = null;
    if( id instanceof AbstractProperty ) {
      valueString = ( ( AbstractProperty )id ).getValue();
    }
    return valueString;
  }

  public boolean isPropertySet( final Object id ) {
    // TODO Auto-generated method stub
    return false;
  }

  public void resetPropertyValue( final Object id ) {
    // TODO Auto-generated method stub
  }

  public void setPropertyValue( final Object id, final Object value ) {
    // TODO Auto-generated method stub
  }
}
