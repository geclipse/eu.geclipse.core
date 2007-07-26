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
package eu.geclipse.jsdl.ui;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.jsdl.ui.wizards.NewJobWizard;

public class Extensions {

  /**
   * The ID of the jsdl application's additional parameters extension point.
   */
  public static final String JSDL_APPLICATION_PARAMETERS_POINT = "eu.geclipse.jsdl.ui.applicationParameters"; //$NON-NLS-1$

  /**
   * The name element contained in the eu.geclipse.ui.jsdlApplicationParameters
   * ui extension point.
   */
  public static final String JSDL_PARAMETERS_APPLICATION_NAME_ELEMENT = "applicationName"; //$NON-NLS-1$

  /**
   * Name of attribute that holds path to xml document within
   * {@link Extensions#JSDL_PARAMETERS_SCHEMA_ELEMENT} element in{@link Extensions#JSDL_APPLICATION_PARAMETERS_POINT}
   * plug-in
   */
  public static final String JSDL_PARAMETERS_SCHEMA_ELEMENT_XML_PATH_ATTRIBUTE = "path"; //$NON-NLS-1$

  /**
   * Name of the attribute of
   * {@link Extensions#JSDL_PARAMETERS_APPLICATION_NAME_ELEMENT} that holds the
   * name (in {@link Extensions#JSDL_APPLICATION_PARAMETERS_POINT} extension
   * point)
   */
  public static final String JSDL_PARAMETERS_APPLICATION_NAME_ELEMENT_NAME_ATTRIBUTE = "name"; //$NON-NLS-1$

  /**
   * Name of the element in extension of
   * {@link Extensions#JSDL_APPLICATION_PARAMETERS_POINT} extension point that
   * keeps information of xml
   */
  public static final String JSDL_PARAMETERS_SCHEMA_ELEMENT = "parametersSchema"; //$NON-NLS-1$

  /**
   * The executable element contained in the eu.geclipse.ui.jsdlApplicationParameters
   * ui extension point.
   */
  public static final String JSDL_PARAMETERS_EXECUTABLE_ELEMENT = "executablePath"; //$NON-NLS-1$

  /**
   * Name of the attribute of
   * {@link Extensions#JSDL_PARAMETERS_EXECUTABLE_ELEMENT} that holds the
   * path (in {@link Extensions#JSDL_APPLICATION_PARAMETERS_POINT} extension
   * point)
   */
  public static final String JSDL_PARAMETERS_EXECUTABLE_ELEMENT_PATH_ATTRIBUTE = "path"; //$NON-NLS-1$

  /**
   * Returns path (in file system, not in Eclipse resources way) to xml file
   * describing contents of additional {@link NewJobWizard} pages, for given
   * bundle id
   * 
   * @param bundleId id of bundle within which xml file is defined
   * @return path to xml file with description of additional pages for
   *         {@link NewJobWizard}
   */
  static public Path getXMLPath(final String bundleId) {
    Path result = null;
    ExtensionManager eManager = new ExtensionManager();
    for (IConfigurationElement element : eManager.getConfigurationElements(
        JSDL_APPLICATION_PARAMETERS_POINT, JSDL_PARAMETERS_SCHEMA_ELEMENT)) {
      if (element.getDeclaringExtension().getContributor().getName().equals(
          bundleId)) {
        try {
          result = new Path(element
              .getAttribute(JSDL_PARAMETERS_SCHEMA_ELEMENT_XML_PATH_ATTRIBUTE));
          URL fileURL = FileLocator.find(Platform.getBundle(bundleId), result,
              null);
          fileURL = FileLocator.toFileURL(fileURL);
          String temp = fileURL.toString();
          temp = temp.substring(temp.indexOf(fileURL.getProtocol())
              + fileURL.getProtocol().length() + 1, temp.length());
          result = new Path(temp);
        } catch (IOException ioe) {
          // TODO katis log
        }
      }
    }
    return result;
  }
  
  /**
   * Method to access list of names of application for which additional
   * parameters are required. Each element of this list is connected with bundle
   * (by this bundle's id)
   * 
   * @return Map with bundles' ids as key and names of applications that require
   *         additional parameters as values
   */
  static public Map<String, String> getApplicationParametersXMLMap() {
    Map<String, String> result = new HashMap<String, String>();
    ExtensionManager eManager = new ExtensionManager();
    for( IConfigurationElement element : eManager.getConfigurationElements( JSDL_APPLICATION_PARAMETERS_POINT,
                                                                            JSDL_PARAMETERS_APPLICATION_NAME_ELEMENT ) )
    {
      String bundleId = element.getDeclaringExtension()
        .getContributor()
        .getName();
      String name = element.getAttribute( JSDL_PARAMETERS_APPLICATION_NAME_ELEMENT_NAME_ATTRIBUTE );
      result.put( bundleId, name );
    }
    return result;
  }
  
  /**
   * Get all currently registered extension executable for the JSDL
   * application parameter extension point.
   *   
   * @param bundleId The ID of the bundle for which to get the executables.
   * @return The JSDL extension executables.
   */
  static public String getJSDLExtensionExecutable( final String bundleId ){
    String result = null;
    ExtensionManager eManager = new ExtensionManager();
    for( IConfigurationElement element : eManager.getConfigurationElements( JSDL_APPLICATION_PARAMETERS_POINT,
                                                                            JSDL_PARAMETERS_EXECUTABLE_ELEMENT ) )
    {
      if( element.getDeclaringExtension()
          .getContributor()
          .getName()
          .equals( bundleId ) ){
        result = element.getAttribute( JSDL_PARAMETERS_EXECUTABLE_ELEMENT_PATH_ATTRIBUTE );
      }
      
    }
    return result;
  }

}
