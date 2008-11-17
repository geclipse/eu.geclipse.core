/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *     IT Research Division, NEC Laboratories Europe, NEC Europe Ltd. (http://www.it.neclab.eu)
 *     - Harald Kornmayer (harald.kornmayer@it.neclab.eu)
 *
 *****************************************************************************/
package eu.geclipse.core.sla.ui.preferences;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author korn
 *
 */
public class Messages {

  private static final String BUNDLE_NAME = "eu.geclipse.core.sla.ui.preferences.messages"; //$NON-NLS-1$
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle( BUNDLE_NAME );

  private Messages() {
    //    
    }


  /**
   * @param key
   * @return message
   */
  public static String getString( String key ) {
    try {
      return RESOURCE_BUNDLE.getString( key );
    } catch( MissingResourceException e ) {
      return '!' + key + '!';
    }
  }
}
