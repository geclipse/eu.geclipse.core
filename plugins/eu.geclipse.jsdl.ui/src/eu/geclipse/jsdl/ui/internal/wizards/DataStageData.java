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
 *     PSNC - Katarzyna Bylec
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal.wizards;

import java.util.Properties;

/**
 * Class to keep information of files to stage in/out to/from execution host
 */
public class DataStageData {

  private Properties valueVsURI;
  private String argName;

  /**
   * Creates new instance of class
   * 
   * @param argName name of the argument
   * @param value name of the file
   * @param URI URI of the file
   */
  public DataStageData( final String argName,
                        final String value,
                        final String URI )
  {
    this.argName = argName;
    this.valueVsURI = new Properties();
    this.valueVsURI.setProperty( value, URI );
  }

  /**
   * Method to access name of the argument
   * 
   * @return name of the argument
   */
  public String getArgName() {
    return this.argName;
  }

  /**
   * Method to access properties with file name as a key and this file's URI as
   * a value
   * 
   * @return properties with file name as a key and this file's URI as a value
   */
  public Properties getValuesAndURIs() {
    return this.valueVsURI;
  }
}
