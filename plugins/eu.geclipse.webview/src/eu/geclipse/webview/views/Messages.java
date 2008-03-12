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
 *    Harald Kornmayer - initial API and implementation
 *****************************************************************************/

package eu.geclipse.webview.views;

import org.eclipse.osgi.util.NLS;

/**
 * This class contains the public accessors to the externalised strings.
 */
public class Messages extends NLS {

  public static String WebView_action_2_tooltip;

  public static String WebView_back;
  public static String WebView_forward;

  public static String WebView_initializeError;

  public static String WebView_project_home;
  public static String WebView_g_eclipse;
  public static String WebView_GGUS;
  public static String WebView_GGUSshow;
  public static String WebView_VOMS;
  public static String WebView_VOMSshow;
  
  private static final String BUNDLE_NAME = "eu.geclipse.webview.views.messages"; //$NON-NLS-1$
  
  static {
    // initialize resource bundle
    NLS.initializeMessages( BUNDLE_NAME, Messages.class );
  }

  private Messages() {
    // empty constructor
  }
}
