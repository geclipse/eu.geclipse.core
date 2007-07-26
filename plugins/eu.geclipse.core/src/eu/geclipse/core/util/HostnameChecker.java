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

package eu.geclipse.core.util;

import java.util.StringTokenizer;

/**
 * Class for checking hostnames.
 */
public class HostnameChecker {
  /**
   * Checks if a hostname label is valid.
   * @param label label to check
   * @return true if the label is valid, false otherwise
   */
  public static boolean checkLabel( final String label ) {
    boolean valid = true;
    if ( label.length() == 0 || label.length() > 63 ) {
      valid = false;
    } else {
      for ( char ch : label.toCharArray() ) {
        if ( ! ( Character.isDigit( ch ) || Character.isLetter( ch ) || ch == '-' ) ) {
          valid = false;
        }
      }
      if ( label.charAt( 0 ) == '-' ) {
        valid = false;
      }
    }
    return valid;
  }

  /**
   * Checks if a hostname is valid. 
   * @param hostname hostname to check
   * @return true if the hostname is valid, false otherwise
   */
  public static boolean checkHostname( final String hostname ) {
    boolean valid = true;
    if ( hostname.length() == 0 || hostname.length() > 255 ) {
      valid = false;
    } else {
      StringTokenizer tokenizer = new StringTokenizer( hostname, "." ); //$NON-NLS-1$
      while ( tokenizer.hasMoreElements() ) {
        String label = tokenizer.nextToken();
        if ( !checkLabel( label ) ) {
          valid = false;
        }
      }
    }
    return valid;
  }
}
