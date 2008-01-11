/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Ariel Garcia - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core;

public interface ICoreSolutions {
  
  /*
   * Please keep the list sorted by extensionID!
   */
  
  // Net
  public static final String CHECK_FIREWALL
    = "eu.geclipse.solution.net.checkInternetConnection"; //$NON-NLS-1$
  
  public static final String CHECK_INTERNET_CONNECTION
    = "eu.geclipse.solution.net.checkInternetConnection"; //$NON-NLS-1$
  
  public static final String CHECK_PORT_ALREADY_IN_USE
    = "eu.geclipse.solution.net.checkPortAlreadyInUse"; //$NON-NLS-1$
  
  public static final String CHECK_PROXY_SETTINGS
    = "eu.geclipse.solution.net.checkProxySettings"; //$NON-NLS-1$
  
  public static final String CHECK_SERVER_URL
    = "eu.geclipse.solution.net.checkServerURL"; //$NON-NLS-1$
  
  public static final String CHECK_TIMEOUT_SETTINGS
    = "eu.geclipse.solution.net.checkTimeoutSettings"; //$NON-NLS-1$
  
  public static final String CONTACT_SERVER_ADMIN
    = "eu.geclipse.solution.net.contactServerAdmin"; //$NON-NLS-1$
  
  public static final String USE_ANOTHER_PORT
    = "eu.geclipse.solution.net.useAnotherPort"; //$NON-NLS-1$

}
