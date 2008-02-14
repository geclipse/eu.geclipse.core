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
  
  // Auth
  public static final String AUTH_CHECK_CA_CERTIFICATES
    = "eu.geclipse.core.solution.auth.checkCaCertificates"; //$NON-NLS-1$
  public static final String AUTH_CHECK_TOKENS
    = "eu.geclipse.core.solution.auth.checkTokens"; //$NON-NLS-1$
  public static final String AUTH_CHECK_VO_SETTINGS
    = "eu.geclipse.core.solution.auth.checkVoSettings"; //$NON-NLS-1$
  
  // IO
  public static final String IO_DOWNLOAD_FILE_AGAIN
    = "eu.geclipse.core.solution.io.downloadFileAgain"; //$NON-NLS-1$
  public static final String IO_DOWNLOAD_FROM_ANOTHER_SOURCE
    = "eu.geclipse.core.solution.io.downloadFromAnotherSource"; //$NON-NLS-1$
  
  // Net
  public static final String NET_CHECK_FIREWALL
    = "eu.geclipse.core.solution.net.checkFirewall"; //$NON-NLS-1$
  public static final String NET_CHECK_INTERNET_CONNECTION
    = "eu.geclipse.core.solution.net.checkInternetConnection"; //$NON-NLS-1$
  public static final String NET_CHECK_PORT_ALREADY_IN_USE
    = "eu.geclipse.core.solution.net.checkPortAlreadyInUse"; //$NON-NLS-1$
  public static final String NET_CHECK_PROXY_SETTINGS
    = "eu.geclipse.core.solution.net.checkProxySettings"; //$NON-NLS-1$
  public static final String NET_CHECK_SERVER_URL
    = "eu.geclipse.core.solution.net.checkServerURL"; //$NON-NLS-1$
  public static final String NET_CHECK_TIMEOUT_SETTINGS
    = "eu.geclipse.core.solution.net.checkTimeoutSettings"; //$NON-NLS-1$
  public static final String NET_CONTACT_SERVER_ADMIN
    = "eu.geclipse.core.solution.net.contactServerAdmin"; //$NON-NLS-1$
  public static final String NET_USE_ANOTHER_PORT
    = "eu.geclipse.core.solution.net.useAnotherPort"; //$NON-NLS-1$
  
  // Sys
  public static final String SYS_CHECK_SYSTEM_TIME
    = "eu.geclipse.core.solution.sys.checkSystemTime"; //$NON-NLS-1$

}
