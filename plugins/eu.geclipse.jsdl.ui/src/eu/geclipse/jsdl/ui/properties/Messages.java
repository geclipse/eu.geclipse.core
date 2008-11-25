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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.properties;

import org.eclipse.osgi.util.NLS;

/**
 *
 */
public class Messages extends NLS {

  /**
   * 
   */
  public static String JsdlJobDescSource_CpuArchitecture;
  /**
   * 
   */
  public static String JsdlJobDescSource_OS;
  /**
   * 
   */
  public static String JsdlJobDescSource_OSVersion;
  /**
   * 
   */
  public static String JsdlJobDescSource_Requirements;
  /**
   * 
   */
  public static String JsdlJobDescSource_RequirementsOS;
  /**
   * 
   */
  public static String JsdlJobDescSource_CandidateHosts;
  /**
   * 
   */
  public static String JsdlJobDescSource_NetworkBandwidth;
  
  /**
   * 
   */
  public static String JsdlJobDescSource_JsdlJobDescSource_Min;
  
  /**
   * 
   */
  public static String JsdlJobDescSource_JsdlJobDescSource_Max;
  /**
   * 
   */
  public static String JsdlJobDescSource_propAppName;
  /**
   * 
   */
  public static String JsdlJobDescSource_propInputDataStagers;
  /**
   * 
   */
  public static String JsdlJobDescSource_propOutputDataStagers;
  
  private static final String BUNDLE_NAME = "eu.geclipse.jsdl.ui.properties.messages"; //$NON-NLS-1$
  

  static {
    // initialize resource bundle
    NLS.initializeMessages( BUNDLE_NAME, Messages.class );
  }

  private Messages() {
    // empty constructor
  }

 
}
