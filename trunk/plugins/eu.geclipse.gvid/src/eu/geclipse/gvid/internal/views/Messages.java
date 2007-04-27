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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.gvid.internal.views;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Returns the localised messages for this package.
 */
public class Messages {
  private static final String BUNDLE_NAME = "eu.geclipse.gvid.internal.views.messages"; //$NON-NLS-1$
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle( BUNDLE_NAME );

  private Messages() {
    // not instanceable
  }

  /**
   * Returns a localised version of a message.
   * 
   * @param key key for the message.
   * @return the localised string.
   */
  public static String getString( final String key ) {
    String message;
    try {
      message = RESOURCE_BUNDLE.getString( key );
    } catch( final MissingResourceException exception ) {
      message = '!' + key + '!';
    }
    return message;
  }

  /**
   * Returns a localised version of a message (Containing three arguments).
   * 
   * @param key key for the message.
   * @param arg0 1st argument to insert.
   * @param arg1 2nd argument to insert.
   * @param arg2 3rd argument to insert.
   * @return the localised string.
   */
  public static String formatMessage( final String key, final Object arg0, final Object arg1, final Object arg2 ) {
    String messageString = getString( key );
    MessageFormat mf = new MessageFormat(messageString);
    Object[] args = new Object[3];
    args[0] = arg0;
    args[1] = arg1;
    args[2] = arg2;
    return mf.format( args );
  }
}
