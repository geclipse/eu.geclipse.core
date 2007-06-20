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

/**
 * This is a helper class that holds static fields and methods to easily access
 * extension of the g-Eclipse ui extension points.
 * 
 * @author stuempert-m
 */
public class Extensions {
  
  public static final String EFS_POINT
    = "eu.geclipse.ui.efs"; //$NON-NLS-1$

  public static final String EFS_FILESYSTEM_ELEMENT
    = "filesystem"; //$NON-NLS-1$
  
  public static final String EFS_SCHEME_ATT
    = "scheme"; //$NON-NLS-1$
  
  public static final String EFS_VALIDATOR_ATT
    = "validator"; //$NON-NLS-1$
  
  public static final String EFS_URI_ATT
    = "uri"; //$NON-NLS-1$
  
  public static final String EFS_SCHEME_SPEC_PART_ATT
    = "scheme-specific-part"; //$NON-NLS-1$
  
  public static final String EFS_AUTHORITY_ATT
    = "authority"; //$NON-NLS-1$
  
  public static final String EFS_USER_INFO_ATT
    = "user-info"; //$NON-NLS-1$
  
  public static final String EFS_HOST_ATT
    = "host"; //$NON-NLS-1$
  
  public static final String EFS_PORT_ATT
    = "port"; //$NON-NLS-1$
  
  public static final String EFS_PATH_ATT
    = "path"; //$NON-NLS-1$
  
  public static final String EFS_QUERY_ATT
    = "query"; //$NON-NLS-1$
  
  public static final String EFS_FRAGMENT_ATT
    = "fragment"; //$NON-NLS-1$
  
  public static final String EFS_URI_RAW
    = "raw"; //$NON-NLS-1$
  
  public static final String EFS_URI_OPAQUE
    = "opaque"; //$NON-NLS-1$
  
  public static final String EFS_URI_HIERARCHICAL
    = "hierarchical"; //$NON-NLS-1$
  
  public static final String EFS_URI_SERVER
    = "server"; //$NON-NLS-1$

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
  
  private static final String PROPERTIES_FACTORY_POINT = "eu.geclipse.ui.propertiesFactory";  //$NON-NLS-1$
  private static final String PROPERTIES_FACTORY_ELEMENT = "PropertiesFactory"; //$NON-NLS-1$
  private static final String PROPERTIES_FACTORY_SOURCECLASS_ATTR = "sourceObjectClass"; //$NON-NLS-1$
  private static final String PROPERTIES_FACTORY_CLASS_ATTR = "class"; //$NON-NLS-1$
  
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
  
  static public IConfigurationElement getRegisteredEFSExtension( final String scheme ) {
    IConfigurationElement result = null;
    List< IConfigurationElement > registeredEFSExtensions
      = getRegisteredEFSExtensions();
    for ( IConfigurationElement element : registeredEFSExtensions ) {
      if ( element.getAttribute( EFS_SCHEME_ATT ).equals( scheme ) ) {
        /*IConfigurationElement[] children = element.getChildren();
        if ( ( children != null ) && ( children.length > 0 ) ) {
          result = children[0];
          break;
        }*/
        result = element;
        break;
      }
    }
    return result;
  }
  
  static public List< IConfigurationElement > getRegisteredEFSExtensions() {
    ExtensionManager manager = new ExtensionManager();
    List< IConfigurationElement > filesystems
      = manager.getConfigurationElements( EFS_POINT, EFS_FILESYSTEM_ELEMENT );
    return filesystems;
  }

  /**
   * Scan registered plugins and return all {@link IPropertiesFactory}, which support properties for 
   * class <code>sourceObjectClass</code>, or for <code>sourceObjectClass</code> base classes, or for
   *  <code>sourceObjectClass</code> interfaces.   
   * @param sourceObjectClass class of object, for which we need properties 
   * @return all factories, which can produces properties for sourceObjectClass 
   */
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
          if( isInstanceOf( sourceObjectClass, currentSourceObjectString ) ) {
            IPropertiesFactory factory = ( IPropertiesFactory )element.createExecutableExtension( PROPERTIES_FACTORY_CLASS_ATTR );
            propertiesFactoryList.add( factory );
          }
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
  
  /**
   * Make the same what {@link Class#isAssignableFrom(Class)}
   * <p>
   * Why we cannot use {@link Class#isAssignableFrom(Class)}?<br>
   * {@link Class#isAssignableFrom(Class)} needs instance of Class as parameter.
   * Unfortunatelly we have only full class name, and we don't want to load this
   * class if wasn't loaded yet. So, instead to compare {@link Class} objects,
   * we just compare class name. Similar solution was used in Eclipse. See:
   * <code>TabbedPropertyRegistryClassSectionFilter.appliesToEffectiveType()</code>
   * 
   * @param checkedObjectClass
   * @param fullyClassNameString
   * @return true if: <br>
   *         checkedObjectClass is instance of fullyClassNameString <br>
   *         or checkedObjectClass inherit fullyClassNameString <br>
   *         or checkedObjectClass implement interface fullyClassNameString <br>
   */
  static private boolean isInstanceOf( final Class<?> checkedObjectClass,
                                       final String fullyClassNameString )
  {
    boolean isInstance = false;
    if( checkedObjectClass.getName().equals( fullyClassNameString ) ) {
      isInstance = true;
    } else {
      // check interfaces
      Class<?>[] interfaces = checkedObjectClass.getInterfaces();
      for( int index = 0; index < interfaces.length && isInstance == false; index++ )
      {
        if( interfaces[ index ].getName().equals( fullyClassNameString ) ) {
          isInstance = true;
        }
      }
    }
    // check base class
    if( isInstance == false && checkedObjectClass.getSuperclass() != null ) {
      isInstance = isInstanceOf( checkedObjectClass.getSuperclass(),
                                 fullyClassNameString );
    }
    return isInstance;
  }
}
