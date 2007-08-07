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
 *    Pawel Wolniewicz, PSNC 
 *****************************************************************************/

package eu.geclipse.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;

import eu.geclipse.core.auth.IAuthenticationTokenDescription;
import eu.geclipse.core.model.IGridElementCreator;

/**
 * This is a helper class that holds static fields and methods to
 * easily access extension of the g-Eclipse core extension points.
 * 
 * @author stuempert-m
 */
public class Extensions {
  
  /**
   * The ID of the application deployment extension point.
   */
  public static final String APPLICATION_DEPLOYMENT_POINT
  = "eu.geclipse.core.applicationDeployment"; //$NON-NLS-1$
  
  /**
   * The ID of the application deployment element contained
   * in the application deployment extension point. 
   */
  public static final String APPLICATION_DEPLOYMENT_ELEMENT
    = "deployment"; //$NON-NLS-1$
  
  /**
   * The ID of the executable extension of the application deployment description
   * configuration element.
   */
  public static final String APPLICATION_DEPLOYMENT_EXECUTABLE
    = "class"; //$NON-NLS-1$
  
  
  /**
   * The ID of the authentication token extension point.
   */
  public static final String AUTH_TOKEN_POINT
    = "eu.geclipse.core.authTokens"; //$NON-NLS-1$
  
  /**
   * The ID of the authentication token configuration element contained
   * in the authentication token management extension point. 
   */
  public static final String AUTH_TOKEN_ELEMENT
    = "token"; //$NON-NLS-1$
  
  /**
   * The ID of the name attribute of the token element of the
   * authentication token extension point.
   */
  public static final String AUTH_TOKEN_NAME_ATTRIBUTE
    = "name"; //$NON-NLS-1$
  
  /**
   * The ID of the wizard attribute of the token element of the
   * authentication token extension point.
   */
  public static final String AUTH_TOKEN_WIZARD_ATTRIBUTE
    = "wizard"; //$NON-NLS-1$

  /**
   * The ID of the executable extension of the authentication token description
   * configuration element.
   */
  public static final String AUTH_TOKEN_DESCRIPTION_EXECUTABLE
    = "description"; //$NON-NLS-1$
  
  /**
   * The ID of the authentication token provider extension point.
   */
  public static final String AUTH_TOKEN_PROVIDER_POINT
    = "eu.geclipse.core.authTokenProvider"; //$NON-NLS-1$
  
  /**
   * The ID of the authentication token provider configuration element
   * contained in the authentication token management extension point. 
   */
  public static final String AUTH_TOKEN_PROVIDER_ELEMENT
    = "provider"; //$NON-NLS-1$
  
  /**
   * The ID of the executable extension of the authentication token
   * provider element.
   */
  public static final String AUTH_TOKEN_PROVIDER_EXECUTABLE
    = "class"; //$NON-NLS-1$
  
  /**
   * The ID of the priority attribute of the provider element of the
   * authentication token provider extension point.
   */
  public static final String AUTH_TOKEN_PROVIDER_PRIORITY_ATTRIBUTE
    = "priority"; //$NON-NLS-1$
  
  /**
   * The ID of the job status service manager point.
   */
  public static final String JOB_STATUS_SERVICE_MANAGER_POINT
    = "eu.geclipse.core.gridJobStatusServiceManager"; //$NON-NLS-1$
  
  /**
   * The ID of the filesystems extension point.
   */
  public static final String FILESYSTEMS_POINT
    = "org.eclipse.core.filesystem.filesystems"; //$NON-NLS-1$
  
  /**
   * The ID of the scheme configuration element contained
   * in the filesystems extension point.
   */
  public static final String FILESYSTEMS_ELEMENT
    = "filesystem"; //$NON-NLS-1$
  
  /**
   * The ID of the scheme attribute for the filesystems
   * extension point.
   */
  public static final String FILESYSTEMS_SCHEME_ATTRIBUTE
    = "scheme"; //$NON-NLS-1$

  /**
   * The ID of the connection management extension point.
   */
  public static final String CONNECTION_MANAGEMENT_POINT
    = "eu.geclipse.core.connectionManagement"; //$NON-NLS-1$
  
  /**
   * The ID of the grid element creator extension point.
   */
  public static final String GRID_ELEMENT_CREATOR_POINT
    = "eu.geclipse.core.gridElementCreator"; //$NON-NLS-1$
  
  /**
   * The ID of the problem provider extension point.
   */
  public static final String PROBLEM_PROVIDER_POINT
    = "eu.geclipse.core.problemProvider"; //$NON-NLS-1$
  
  /**
   * The ID of the connection protocol configuration element
   * contained in the connection management extension point. 
   */
  public static final String CONNECTION_PROTOCOL_ELEMENT
    = "connectionProtocol"; //$NON-NLS-1$
  
  /**
   * The ID of the executable extension of the connection protocol
   * configuration element.
   */
  public static final String CONNECTION_PROTOCOL_EXECUTABLE
    = "class"; //$NON-NLS-1$
  
  /**
   * The ID of the Grid element creator configuration element
   * contained in the Grid element creator extension point.
   */
  public static final String GRID_ELEMENT_CREATOR_ELEMENT
    = "creator"; //$NON-NLS-1$
  
  /**
   * The ID of the executable extension of the Grid element creator
   * configuration element.
   */
  public static final String GRID_ELEMENT_CREATOR_EXECUTABLE
    = "class"; //$NON-NLS-1$
  
  /**
   * The ID of the problem provider configuration element
   * contained in the problem provider extension point.
   */
  public static final String PROBLEM_PROVIDER_ELEMENT
    = "provider"; //$NON-NLS-1$

  /**
   * The ID of the executable extension of the problem provider
   * configuration element.
   */
  public static final String PROBLEM_PROVIDER_EXECUTABLE
    = "class"; //$NON-NLS-1$
  
  /**
   * List that holds all known element creators.
   */
  private static List< IGridElementCreator > elementCreators = null;

  /**
   * List that holds all known authentication token descriptions.
   */
  private static List<IAuthenticationTokenDescription> authTokenDescriptions;

  /**
   * List that holds all known problem providers.
   */
  private static List<IProblemProvider> problemProviders;

  /**
   * List containing the names of all known authentication token.
   */
  private static List<String> authTokenNames;
  
  /**
   * List that holds all known deployment service descriptions.
   */
  private static List< IApplicationDeployment > applicationDeployment;
  
  /**
   * Get a list with the names of all registered authentication tokens.
   * The list will be sorted alphabetically.
   * 
   * @return A list containing the names of the types of all
   * currently available tokens.
   */
  public static List< String > getRegisteredAuthTokenNames() {
    if( authTokenNames == null ) {
      List< String > resultList = new ArrayList< String >();
      ExtensionManager manager = new ExtensionManager();
      List< IConfigurationElement > cElements
        = manager.getConfigurationElements( AUTH_TOKEN_POINT,
                                            AUTH_TOKEN_ELEMENT );
      for ( IConfigurationElement element : cElements ) {
        String name = element.getAttribute( AUTH_TOKEN_NAME_ATTRIBUTE );
        if ( name != null ) {
          resultList.add( name );
        }
      }
      Collections.sort( resultList );
      authTokenNames = resultList;
    }
    return authTokenNames;
  }
  
  /**
   * Get a list of all currently registered authentication token
   * description.
   * 
   * @return A list containing instances of all currently registered
   * extensions of the authentication token description
   * configuration elements.
   */
  public static List< IAuthenticationTokenDescription > getRegisteredAuthTokenDescriptions() {
    if( authTokenDescriptions == null ) {
      List< IAuthenticationTokenDescription > resultList
      = new ArrayList< IAuthenticationTokenDescription >();
      ExtensionManager manager = new ExtensionManager();
      List< Object > objectList
        = manager.getExecutableExtensions( AUTH_TOKEN_POINT,
                                           AUTH_TOKEN_ELEMENT,
                                           AUTH_TOKEN_DESCRIPTION_EXECUTABLE );
      for ( Object o : objectList ) {
        if ( o instanceof IAuthenticationTokenDescription ) {
          resultList.add( ( IAuthenticationTokenDescription ) o );
        }
      }
      authTokenDescriptions = resultList;
    }
    return authTokenDescriptions;
  }
  
  /**
   * Get a list of all currently registered Grid element creators.
   * 
   * @return A list containing instances of all currently registered
   * extensions of the Grid element creator configuration elements.
   */
  public static List<IGridElementCreator> getRegisteredElementCreators() {
    if( elementCreators == null ) {
      List<IGridElementCreator> resultList = new ArrayList<IGridElementCreator>();
      ExtensionManager manager = new ExtensionManager();
      List<Object> objectList = manager.getExecutableExtensions( GRID_ELEMENT_CREATOR_POINT,
                                                                 GRID_ELEMENT_CREATOR_ELEMENT,
                                                                 GRID_ELEMENT_CREATOR_EXECUTABLE );
      for( Object o : objectList ) {
        if( o instanceof IGridElementCreator ) {
          resultList.add( ( IGridElementCreator )o );
        }
      }
      elementCreators = resultList;
    }
    return elementCreators;
  }
  
  /**
   * Get a list of all currently registered problem providers.
   * 
   * @return A list containing instances of all currently registered
   * extensions of the problem provider configuration elements.
   */
  public static List< IProblemProvider > getRegisteredProblemProviders() {
    if( problemProviders == null ) {
      List< IProblemProvider > resultList
      = new ArrayList< IProblemProvider >();
      ExtensionManager manager = new ExtensionManager();
      List< Object > objectList
      = manager.getExecutableExtensions( PROBLEM_PROVIDER_POINT,
                                         PROBLEM_PROVIDER_ELEMENT,
                                         PROBLEM_PROVIDER_EXECUTABLE );
      for ( Object o : objectList ) {
        if ( o instanceof IProblemProvider ) {
          resultList.add( ( IProblemProvider ) o );
        }
      }
      problemProviders = resultList;
    }
    return problemProviders;
  }
  
  /**
   * Get a list of all currently registered filesystem schemes.
   * 
   * @return A list of Strings containing all schemes that are
   * currently known by the EFS.
   */
  public static List< String > getRegisteredFilesystemSchemes() {
    List< String > resultList = new ArrayList< String >();
    ExtensionManager manager = new ExtensionManager();
    List< IConfigurationElement > configurationElements
      = manager.getConfigurationElements( FILESYSTEMS_POINT, FILESYSTEMS_ELEMENT );
    for ( IConfigurationElement element : configurationElements ) {
      String scheme = element.getAttribute( FILESYSTEMS_SCHEME_ATTRIBUTE );
      resultList.add( scheme );
    }
    return resultList;
  }
  
  /**
   * Get a list of all currently registered application deployment.
   * 
   * @return A list containing instances of all currently registered
   * extensions of the application deployment configuration elements.
   */
  public static List< IApplicationDeployment > getRegisteredParametersDescription() {
    if ( applicationDeployment == null ) {
      List< IApplicationDeployment > resultList
      = new ArrayList< IApplicationDeployment >();
      ExtensionManager manager = new ExtensionManager();
      List< Object > objectList
      = manager.getExecutableExtensions( APPLICATION_DEPLOYMENT_POINT,
                                         APPLICATION_DEPLOYMENT_ELEMENT,
                                         APPLICATION_DEPLOYMENT_EXECUTABLE );
      for ( Object o : objectList ) {
        if ( o instanceof IApplicationDeployment ) {
          resultList.add( ( IApplicationDeployment ) o );
        }
      }
      applicationDeployment = resultList;
    }
    return applicationDeployment;
  }
  
}
