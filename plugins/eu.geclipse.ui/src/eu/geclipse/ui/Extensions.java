/******************************************************************************
  * Copyright (c) 2006 g-Eclipse consortium
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License v1.0
  * which accompanies this distribution, and is available at
  * http://www.eclipse.org/legal/epl-v10.html
  *
  * Initialial development of the original code was made for
  * project g-Eclipse founded by European Union
  * project number: FP6-IST-034327  http://www.geclipse.eu/
  *
  * Contributor(s):
  *     FZK (http://www.fzk.de)
  *      - Mathias Stuempert (mathias.stuempert@iwr.fzk.de)
  *     PSNC
  *      - Katarzyna Bylec (katis@man.poznan.pl)
  *****************************************************************************/

package eu.geclipse.ui;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IConfigurationElement;
import eu.geclipse.core.ExtensionManager;

/**
 * This is a helper class that holds static fields and methods to easily access
 * extension of the g-Eclipse ui extension points.
 * 
 * @author stuempert-m
 */
public class Extensions {

  /**
   * The ID of the authentication token ui extension point.
   */
  public static final String AUTH_TOKEN_UI_POINT = "eu.geclipse.ui.authTokenUI"; //$NON-NLS-1$
  /**
   * The ID of the authentication token ui factory element contained in the
   * authentication token ui extension point.
   */
  public static final String AUTH_TOKEN_FACTORY_ELEMENT = "factory"; //$NON-NLS-1$
  /**
   * The ID of the executable extension of the authentication token ui factory
   * configuration element.
   */
  public static final String AUTH_TOKEN_FACTORY_EXECUTABLE = "class"; //$NON-NLS-1$
  /**
   * The ID of the connection wizard extension point.
   */
  public static final String CONNECTION_WIZARD_POINT = "eu.geclipse.ui.connectionWizard"; //$NON-NLS-1$
  /**
   * The ID of the jsdl application's additional parameters extension point.
   */
  public static final String JSDL_APPLICATION_PARAMETERS_POINT = "eu.geclipse.ui.jsdlApplicationParameters"; //$NON-NLS-1$
  /**
   * The id of parameters' schema element contained in the authentication token
   * ui extension point.
   */
  public static final String JSDL_PARAMETERS_SCHEMA_ELEMENT = "parametersSchema"; //$NON-NLS-1$
  /**
   * Name of attribute
   */
  public static final String JSDL_PARAMETERS_SCHEMA_ELEMENT_PATH_ATTRIBUTE = "path"; //$NON-NLS-1$

  /**
   * Get a list of all currently registered authentication token ui factories.
   * 
   * @return A list containing instances of all currently registered extensions
   *         of the authentication token ui configuration elements.
   */
  static public List<IAuthTokenUIFactory> getRegisteredAuthTokenUIFactories() {
    List<IAuthTokenUIFactory> resultList = new ArrayList<IAuthTokenUIFactory>();
    ExtensionManager browser = new ExtensionManager();
    List<Object> objectList = browser.getExecutableExtensions( AUTH_TOKEN_UI_POINT,
                                                               AUTH_TOKEN_FACTORY_ELEMENT,
                                                               AUTH_TOKEN_FACTORY_EXECUTABLE );
    for( Object o : objectList ) {
      if( o instanceof IAuthTokenUIFactory ) {
        resultList.add( ( IAuthTokenUIFactory )o );
      }
    }
    return resultList;
  }

  /**
   * Method to access list of paths to xml files that describe additional
   * application's parameters for NewJobWizard
   * 
   * @return List of paths to xml files containing description of additional
   *         parameters for applications used in JSDL
   */
  static public List<String> getApplicationParametersXML() {
    List<String> result = new ArrayList<String>();
    ExtensionManager eManager = new ExtensionManager();
    for( IConfigurationElement element : eManager.getConfigurationElements( JSDL_APPLICATION_PARAMETERS_POINT,
                                                                            JSDL_PARAMETERS_SCHEMA_ELEMENT ) )
    {
      result.add( element.getAttribute( JSDL_PARAMETERS_SCHEMA_ELEMENT_PATH_ATTRIBUTE ));
    }
    return result;
  }
}
