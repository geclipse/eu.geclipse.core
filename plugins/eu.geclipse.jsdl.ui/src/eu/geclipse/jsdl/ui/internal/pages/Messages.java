/******************************************************************************
  * Copyright (c) 2007 g-Eclipse consortium
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License v1.0
  * which accompanies this distribution, and is available at
  * http://www.eclipse.org/legal/epl-v10.html
  *
  * Initialial development of the original code was made for
  * project g-Eclipse founded by European Union
  * project number: FP6-IST-034327  http://www.geclipse.eu/
  *
  * Contributor(s):
  *     UCY (http://www.ucy.cs.ac.cy)
  *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy) 
  *****************************************************************************/

package eu.geclipse.jsdl.ui.internal.pages;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Returns the localised messages for this package.
 */
public class Messages {

  private static final String BUNDLE_NAME =
                    "eu.geclipse.jsdl.ui.internal.pages.messages"; //$NON-NLS-1$
  private static final ResourceBundle RESOURCE_BUNDLE = 
                                      ResourceBundle.getBundle( BUNDLE_NAME );

  private Messages() {
    // Empty constructor
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
