/*****************************************************************************
 * Copyright (c) 2008, g-Eclipse Consortium 
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
 *    Szymon Mueller - PSNC - Initial API and implementation
 *****************************************************************************/
package eu.geclipse.servicejob.model.tests.job;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Class for getting Messages.
 *
 */
public class Messages {

  private static final String BUNDLE_NAME = "eu.geclipse.servicejob.model.tests.job.messages"; //$NON-NLS-1$
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle( BUNDLE_NAME );

  private Messages() {
    //Empty implementation
  }

  /**
   * Gets the message for the given string.
   * @param key String key of the message.
   * @return Message
   */
  public static String getString( final String key ) {
    String returnString = ""; //$NON-NLS-1$
    try {
      returnString = RESOURCE_BUNDLE.getString( key );
    } catch( MissingResourceException e ) {
      returnString = '!' + key + '!';
    }
    return returnString;
  }
}
