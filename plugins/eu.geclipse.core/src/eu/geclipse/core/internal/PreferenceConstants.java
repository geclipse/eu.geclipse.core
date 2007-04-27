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

package eu.geclipse.core.internal;

/**
 * Helper class that holds the constants used for refering preferences.
 *  
 * @author stuempert-m
 */
public class PreferenceConstants {

  /**
   * The id for the connection timeout setting.
   */
  public static final String CONNECTION_TIMEOUT
    = "connectiontimeout"; //$NON-NLS-1$
  
  /**
   * The id for the defined VOs.
   */
  public static final String DEFINED_VOS_ID
    = "vos"; //$NON-NLS-1$
  
  /**
   * The id for the name of the default VO.
   */
  public static final String DEFAULT_VO_ID
    = "def_vo"; //$NON-NLS-1$
  
  /**
   * The id of the proxy enabled setting.
   */
  public static final String HTTP_PROXY_ENABLED
    = "http_proxy_enabled"; //$NON-NLS-1$
  
  /**
   * The id of the proxy host setting.
   */
  public static final String HTTP_PROXY_HOST
    = "http_proxy_host"; //$NON-NLS-1$
  
  /**
   * The id of the proxy port setting.
   */
  public static final String HTTP_PROXY_PORT
    = "http_proxy_port"; //$NON-NLS-1$
  
  /**
   * The id of the proxy authentication required setting.
   */
  public static final String HTTP_PROXY_AUTH_REQUIRED
    = "http_proxy_auth_required"; //$NON-NLS-1$
  
  /**
   * The id of the proxy authentication login setting.
   */
  public static final String HTTP_PROXY_AUTH_LOGIN
    = "http_proxy_auth_login"; //$NON-NLS-1$
  
  /**
   * The id of the proxy authentication password setting.
   */
  public static final String HTTP_PROXY_AUTH_PW
    = "http_proxy_auth_pw"; //$NON-NLS-1$
  
}
