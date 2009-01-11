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

package eu.geclipse.aws.ec2.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This class uses the bundle mechanism with the message.properties file located
 * in the root of the project for i18n.
 * 
 * @author Moritz Post
 */
public class Messages {

  /** Name of the bundle file in the project root */
  private static final String BUNDLE_NAME = "eu.geclipse.aws.ec2.ui.messages"; //$NON-NLS-1$

  /** Bundle to work with. */
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle( Messages.BUNDLE_NAME );

  /**
   * Hidden constructor
   */
  private Messages() {
    // nothing to do here
  }

  /**
   * Get the value for the string key.
   * 
   * @param key the key for the value
   * @return the value
   */
  public static String getString( final String key ) {
    try {
      return Messages.RESOURCE_BUNDLE.getString( key );
    } catch( MissingResourceException e ) {
      return '!' + key + '!';
    }
  }
}
