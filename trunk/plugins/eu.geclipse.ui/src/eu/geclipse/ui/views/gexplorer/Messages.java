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
 *    Katarzyna Bylec (PSNC) - initial implementation 
 *    Mateusz Pabis (PSNC)   - initial implementation
 *****************************************************************************/
package eu.geclipse.ui.views.gexplorer;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
/**
 * Returns the localised messages for this package.
 */
public class Messages {

  private static final String BUNDLE_NAME = "eu.geclipse.ui.views.gexplorer.messages"; //$NON-NLS-1$
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle( BUNDLE_NAME );

  private Messages() {
    // empty implementation
  }
  
  /**
   * Standard method to get the specified message string.
   * 
   * @param key The key to the requested string.
   * @return The requested string.
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
