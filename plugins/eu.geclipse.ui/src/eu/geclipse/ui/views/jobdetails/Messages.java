/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/

package eu.geclipse.ui.views.jobdetails;

import org.eclipse.osgi.util.NLS;

/**
 *
 */
public class Messages extends NLS {
  /**
   * 
   */
  public static String GridJobDetailsFactory_arguments;
  public static String GridJobDetailsFactory_description;
  /**
   * 
   */
  public static String GridJobDetailsFactory_executable;
  /**
   * 
   */
  public static String GridJobDetailsFactory_id;
  /**
   * 
   */
  public static String GridJobDetailsFactory_input;
  /**
   * 
   */
  public static String GridJobDetailsFactory_lastUpdateTime;
  /**
   * 
   */
  public static String GridJobDetailsFactory_name;
  /**
   * 
   */
  public static String GridJobDetailsFactory_output;
  /**
   * 
   */
  public static String GridJobDetailsFactory_reason;
  /**
   * 
   */
  public static String GridJobDetailsFactory_status;
  /**
   * 
   */
  public static String JobDetailSectionsManager_application;
  /**
   * 
   */
  public static String JobDetailSectionsManager_general;
  public static String JobDetailSectionsManager_taskNameUpdateJobStatus;
  /**
   * 
   */
  public static String JobDetailsView_emptyJobDescription;
  /**
   * 
   */
  public static String JobEditedDetail_editorNameFormat;
  
  private static final String BUNDLE_NAME = "eu.geclipse.ui.views.jobdetails.messages"; //$NON-NLS-1$
  
  static {
    // initialize resource bundle
    NLS.initializeMessages( BUNDLE_NAME, Messages.class );
  }

  private Messages() {
    // empty implementation
  }
}
