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

package eu.geclipse.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * This class provides some helper methods to easily access extension
 * points, related configuration elements and executable extensions.
 *  
 * @author stuempert-m
 */
public class ExtensionManager {
  
  /**
   * The <code>IExtensionRegistry</code> used to look up the extensions.
   */
  private IExtensionRegistry registry;
  
  /**
   * Construct a new <code>ExtensionManager</code> that uses the default
   * extension registry.
   */
  public ExtensionManager() {
    this( Platform.getExtensionRegistry() );
  }
  
  /**
   * Construct a new <code>ExtensionManager</code> that uses the specified
   * extension registry to look up the extensions.
   * 
   * @param registry The <code>IExtensionRegistry</code> used to look up
   * extensions.
   */
  public ExtensionManager( final IExtensionRegistry registry ) {
    this.registry = registry;
  }
  
  /**
   * Get the extension registry that is used by this extension manager
   * to look up extensions.
   * 
   * @return The <code>IExtensionRegistry</code> used to look up
   * extensions.
   */
  public IExtensionRegistry getRegistry() {
    return this.registry;
  }
  
  /**
   * Get the extension point with the specified id.
   * 
   * @param extensionPointID The id of the extension point.
   * @return The extension point with the specified id or null.
   */
  public IExtensionPoint getExtensionPoint( final String extensionPointID ) {
    return this.registry.getExtensionPoint( extensionPointID );
  }
  
  /**
   * Get a list of all currently available extensions for the specified
   * extension point.
   * 
   * @param extensionPointID The ID of the extension point.
   * @return A list of all currently registered extensions of the specified
   * extension point.
   */
  public List< IExtension > getExtensions( final String extensionPointID ) {
    IExtensionPoint extensionPoint = getExtensionPoint( extensionPointID );
    List< IExtension > resultList = new ArrayList< IExtension >();
    if( extensionPoint != null ) {
      IExtension[] extensions = extensionPoint.getExtensions();
      resultList = Arrays.asList( extensions );
    }
    return resultList;
  }
  
  /**
   * Get a list of all configuration elements with the specified name that
   * are available for the specified extension point.
   * 
   * @param extensionPointID The ID of the extension point.
   * @param configurationElementName The name of the configuration element.
   * @return A list of all <code>IConfigurationElement</code>s with the
   * specified name that could be found for the specified extension point.
   */
  public List< IConfigurationElement > getConfigurationElements( final String extensionPointID,
                                                                 final String configurationElementName ) {
    List< IConfigurationElement > resultList = new ArrayList< IConfigurationElement >();
    List< IExtension > extensions = getExtensions( extensionPointID );
    for ( IExtension extension : extensions ) {
      IConfigurationElement[] configurationElements = extension.getConfigurationElements();
      for ( int cel = 0 ; cel < configurationElements.length ; cel++ ) {
        String name = configurationElements[ cel ].getName();
        if ( name.equals( configurationElementName ) ) {
          resultList.add( configurationElements[ cel ] );
        }
      }
    }
    return resultList;
  }
  
  /**
   * Get a list of all executable extension with the specified property name
   * for the specified configuration element and the specified extension point.
   * 
   * @param extensionPointID The ID of the extension point.
   * @param configurationElementName The name of the configuration element.
   * @param propertyName The property name that denotes the executable extension.
   * @return A list of instances of the executable extension that could be found
   * for the specified parameters.
   */
  public List< Object > getExecutableExtensions( final String extensionPointID,
                                                 final String configurationElementName,
                                                 final String propertyName ) {
    List< Object > resultList = new ArrayList< Object >();
    List< IConfigurationElement > configurationElements
      = getConfigurationElements( extensionPointID, configurationElementName );
    for ( IConfigurationElement element : configurationElements ) {
      try {
        Object obj = element.createExecutableExtension( propertyName );
        resultList.add( obj );
      } catch ( CoreException cExc ) {
        eu.geclipse.core.internal.Activator.logException( cExc );
      }
    }
    return resultList;
  }
  
}
