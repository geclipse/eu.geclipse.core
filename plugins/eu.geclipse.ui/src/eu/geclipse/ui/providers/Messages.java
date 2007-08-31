/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.providers;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Returns the localised messages for this package.
 */
public class Messages {

  private static final String BUNDLE_NAME = "eu.geclipse.ui.providers.messages"; //$NON-NLS-1$
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle( BUNDLE_NAME );

  private Messages() {
    // empty implementation
  }

  /**
   * Returns a localised version of a message.
   * 
   * @param key key for the message.
   * @return the localised string.
   */
  public static String getString( final String key ) {
    String resultString = '!' + key + '!'; 
    try {
      resultString = RESOURCE_BUNDLE.getString( key );
    } catch( MissingResourceException mrEx ) {
      // Nothing to do here
    }
    return resultString;
  }
  
}
