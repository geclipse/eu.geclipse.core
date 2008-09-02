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

package eu.geclipse.core.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class TokenValidator {
  
  public static boolean validateFQHN( final String fqhn ) {
    boolean valid = true;
    try {
      new URI( null, null, fqhn, -1, null, null, null );
    } catch ( URISyntaxException uriExc ) {
      valid = false;
    }
    return valid;
  }
  
  public static boolean validatePort( final String port ) {
    boolean valid = true;
    try {
      int iport = Integer.parseInt( port );
      valid = validatePort( iport );
    } catch ( NumberFormatException nfExc ) {
      valid = false;
    }
    return valid;
  }
  
  public static boolean validatePort( final int port ) {
    return ( port >= 0 ) && ( port <= 65535 );
  }
  
  public static boolean validateURL( final String url ) {
    boolean valid = true;
    try {
      new URL( url );
    } catch ( MalformedURLException murlExc ) {
      valid = false;
    }
    return valid;
  }
  
  public static boolean validateURI( final String uri ) {
    boolean valid = true;
    try {
      new URI( uri );
    } catch ( URISyntaxException uriExc ) {
      valid = false;
    }
    return valid;
  }

}
