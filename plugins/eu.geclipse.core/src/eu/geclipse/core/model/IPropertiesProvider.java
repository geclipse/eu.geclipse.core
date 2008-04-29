/*****************************************************************************
 * Copyright (c) 2007, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Mateusz Pabis (PSNC) - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.model;

import java.util.Vector;


/**
 * This interface provides dynamic set of property names for various elements.
 *
 */
public interface IPropertiesProvider {
  /**
   * Provides set of property names, use one name to obtain single value. 
   * @return Vector of property names 
   */
  public Vector<String> getPropertyKeys();
  
  /**
   * This method always returns non null value.
   * Even if there is no such key supported result shouldn't be null.
   * 
   * @param value name of the property which value is requested 
   * @return requested value of the property, should not be null!
   */
  public String getPropertyValue( final String value );
  
  /**
   * @return category, in which all properties will be shown. May be <code>null</code>
   */
  public String getCategory();
  
}
