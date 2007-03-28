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

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Externalized messages
 *
 */
public class Messages {

  private static final String BUNDLE_NAME = "eu.geclipse.ui.properties.messages"; //$NON-NLS-1$
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle( BUNDLE_NAME );

  private Messages() {
    // empty implementation
  }

  /**
   * @param key
   * @return externalized string
   */
  public static String getString( final String key ) {
    String string = null;
    
    try {
      string = RESOURCE_BUNDLE.getString( key );
    } catch( final MissingResourceException e ) {
      string = '!' + key + '!';
    }
    
    return string;
  }
}
