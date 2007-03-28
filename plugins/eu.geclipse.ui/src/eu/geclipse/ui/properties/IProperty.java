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


/**
 * Property shown in Properties view 
 *
 */
interface IProperty {

  /**
   * 
   * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(Object)
   * @return property value
   */
  String getValue();

  /**
   * Return descriptor
   * @return property descriptor
   */
  IPropertyDescriptor getDescriptor();
}
