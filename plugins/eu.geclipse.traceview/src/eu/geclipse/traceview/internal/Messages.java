/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
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
 *    Christof Klausecker GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.traceview.internal;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Messages for the Event Graph Plugin
 */
public class Messages {

  private static final String BUNDLE_NAME = "eu.geclipse.traceview.internal.messages"; //$NON-NLS-1$
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle( BUNDLE_NAME );

  private Messages() {
    // not instantiable
  }

  /**
   * Returns the message for the specified key.
   * @param key
   * @return message
   */
  public static String getString( final String key ) {
    String result = null;
    try {
      result = RESOURCE_BUNDLE.getString( key );
    } catch( MissingResourceException e ) {
      result = '!' + key + '!';
    }
    return result;
  }
}
