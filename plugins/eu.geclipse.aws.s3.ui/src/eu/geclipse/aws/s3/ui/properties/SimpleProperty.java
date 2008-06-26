/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.s3.ui.properties;

import eu.geclipse.ui.properties.AbstractProperty;

/**
 * This implementation of the {@link AbstractProperty} class uses a simple
 * key/value pair mechanism to provide a property.
 * 
 * @author Moritz Post
 */
public class SimpleProperty extends AbstractProperty<Object> {

  /** The default category to use. */
  private static final String CATEGORY_GENERAL = Messages.getString("SimpleProperty.properties_category_general"); //$NON-NLS-1$

  /** The value to display as the property. */
  private String value;

  /**
   * Create a new {@link SimpleProperty} object with the given name and value to
   * display.
   * 
   * @param key the name to use as the key
   * @param value the value to display as the value
   */
  public SimpleProperty( final String key, final String value ) {
    this( SimpleProperty.CATEGORY_GENERAL, key, value );
  }

  /**
   * Create a new {@link SimpleProperty} with the given key/value pair and place
   * it into the category given.
   * 
   * @param category the category to place the property in
   * @param key the name to use as the key
   * @param value the value to display as the value
   */
  public SimpleProperty( final String category,
                         final String key,
                         final String value )
  {
    super( key, category );
    this.value = value;
  }

  @Override
  public Object getValue( final Object sourceObject ) {
    return this.value;
  }
}