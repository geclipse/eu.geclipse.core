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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import eu.geclipse.core.ExtensionManager;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.properties.IPropertiesFactory;
import eu.geclipse.ui.wizards.jobs.NewJobWizard;

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
  
  private static final String PROPERTIES_FACTORY_POINT = "eu.geclipse.ui.propertiesFactory";  //$NON-NLS-1$
  private static final String PROPERTIES_FACTORY_ELEMENT = "PropertiesFactory";
  private static final String PROPERTIES_FACTORY_SOURCECLASS_ATTR = "sourceObjectClass";
  private static final String PROPERTIES_FACTORY_CLASS_ATTR = "class";
  
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
      result.add( element.getAttribute( JSDL_PARAMETERS_SCHEMA_ELEMENT_XML_PATH_ATTRIBUTE ) );
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
   * Returns path (in file system, not in Eclipse resources way) to xml file
   * describing contents of additional {@link NewJobWizard} pages, for given
   * bundle id
   * 
   * @param bundleId id of bundle within which xml file is defined
   * @return path to xml file with description of additional pages for
   *         {@link NewJobWizard}
   */
  static public Path getXMLPath( final String bundleId ) {
    Path result = null;
    ExtensionManager eManager = new ExtensionManager();
    for( IConfigurationElement element : eManager.getConfigurationElements( JSDL_APPLICATION_PARAMETERS_POINT,
                                                                            JSDL_PARAMETERS_SCHEMA_ELEMENT ) )
    {
      if( element.getDeclaringExtension()
        .getContributor()
        .getName()
        .equals( bundleId ) )
      {
        try {
          result = new Path( element.getAttribute( JSDL_PARAMETERS_SCHEMA_ELEMENT_XML_PATH_ATTRIBUTE ) );
          URL fileURL = FileLocator.find( Platform.getBundle( bundleId ),
                                          result,
                                          null );
          fileURL = FileLocator.toFileURL( fileURL );
          String temp = fileURL.toString();
          temp = temp.substring( temp.indexOf( fileURL.getProtocol() )
                                 + fileURL.getProtocol().length()
                                 + 1, temp.length() );
          result = new Path( temp );
        } catch( IOException ioe ) {
          // TODO katis log
        }
      }
    }
    return result;
  }
  
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
  
  static public List<IPropertiesFactory> getPropertiesFactories( final Class<?> sourceObjectClass )
  {
    List<IPropertiesFactory> propertiesFactoryList = new ArrayList<IPropertiesFactory>();
    ExtensionManager extManager = new ExtensionManager();
    List<IConfigurationElement> confElementsList = extManager.getConfigurationElements( PROPERTIES_FACTORY_POINT,
                                                                                        PROPERTIES_FACTORY_ELEMENT );
    for( IConfigurationElement element : confElementsList ) {
      String currentSourceObjectString = element.getAttribute( PROPERTIES_FACTORY_SOURCECLASS_ATTR );
      if( currentSourceObjectString != null ) {        
        try {          
          Class<?> sourceClass = Class.forName( currentSourceObjectString );
          
          if( sourceClass != null
              && sourceClass.isAssignableFrom( sourceObjectClass ) )
          {
            IPropertiesFactory factory = ( IPropertiesFactory )element.createExecutableExtension( PROPERTIES_FACTORY_CLASS_ATTR );
            propertiesFactoryList.add( factory );
          }          
        } catch( ClassNotFoundException exception ) {
          // don't log, because extension can support properties for objects from plugins, which were not loaded yet
        } catch( CoreException exception ) {
          Activator.logException( exception );
        }
      } else {
        Activator.logStatus( new Status( IStatus.ERROR,
                                         Activator.PLUGIN_ID,
                                         IStatus.ERROR,
                                         "Attribute " //$NON-NLS-1$
                                             + PROPERTIES_FACTORY_SOURCECLASS_ATTR
                                             + " not found", //$NON-NLS-1$
                                         null ) );
      }
    }
    return propertiesFactoryList;
  }
}
