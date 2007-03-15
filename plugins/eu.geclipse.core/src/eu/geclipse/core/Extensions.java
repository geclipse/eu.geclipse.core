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
   * The ID of the authentication token management extension point.
   */
  public static final String AUTH_TOKEN_MANAGEMENT_POINT
    = "eu.geclipse.core.authenticationTokenManagement"; //$NON-NLS-1$
  
  /**
   * The ID of the connection management extension point.
   */
  public static final String CONNECTION_MANAGEMENT_POINT
    = "eu.geclipse.core.connectionManagement"; //$NON-NLS-1$
  
  public static final String GRID_ELEMENT_CREATOR_POINT
    = "eu.geclipse.core.gridElementCreator"; //$NON-NLS-1$
  
  public static final String PROBLEM_PROVIDER_POINT
    = "eu.geclipse.core.problemProvider"; //$NON-NLS-1$
  
  /**
   * The ID of the authentication token configuration element contained
   * in the authentication token management extension point. 
   */
  public static final String AUTH_TOKEN_ELEMENT
    = "token"; //$NON-NLS-1$

  /**
   * The ID of the executable extension of the authentication token description
   * configuration element.
   */
  public static final String AUTH_TOKEN_DESCRIPTION_EXECUTABLE
    = "description"; //$NON-NLS-1$

  /**
   * The ID of the authentication token provider configuration element
   * contained in the authentication token management extension point. 
   */
  public static final String AUTH_TOKEN_PROVIDER_ELEMENT
    = "provider"; //$NON-NLS-1$
  
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
  
  public static final String GRID_ELEMENT_CREATOR_ELEMENT
    = "creator"; //$NON-NLS-1$
  
  public static final String GRID_ELEMENT_CREATOR_EXECUTABLE
    = "class"; //$NON-NLS-1$
  
  public static final String PROBLEM_PROVIDER_ELEMENT
    = "provider"; //$NON-NLS-1$

  public static final String PROBLEM_PROVIDER_EXECUTABLE
    = "class"; //$NON-NLS-1$
  
  /**
   * Get a list with the names of all registered authentication tokens.
   * The list will be sorted alphabetically.
   * 
   * @return A list containing the names of the types of all
   * currently available tokens.
   */
  public static List< String > getRegisteredAuthTokenNames() {
    List< String > resultList = new ArrayList< String >();
    ExtensionManager manager = new ExtensionManager();
    List< IConfigurationElement > cElements
      = manager.getConfigurationElements( AUTH_TOKEN_MANAGEMENT_POINT,
                                          AUTH_TOKEN_ELEMENT );
    for ( IConfigurationElement element : cElements ) {
      String name = element.getAttribute( "name" ); //$NON-NLS-1$
      if ( name != null ) {
        resultList.add( name );
      }
    }
    Collections.sort( resultList );
    return resultList;
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
    List< IAuthenticationTokenDescription > resultList
      = new ArrayList< IAuthenticationTokenDescription >();
    ExtensionManager manager = new ExtensionManager();
    List< Object > objectList
      = manager.getExecutableExtensions( AUTH_TOKEN_MANAGEMENT_POINT,
                                         AUTH_TOKEN_ELEMENT,
                                         AUTH_TOKEN_DESCRIPTION_EXECUTABLE );
    for ( Object o : objectList ) {
      if ( o instanceof IAuthenticationTokenDescription ) {
        resultList.add( ( IAuthenticationTokenDescription ) o );
      }
    }
    return resultList;
  }
  
  public static List< IGridElementCreator > getRegisteredElementCreators() {
    List< IGridElementCreator > resultList
      = new ArrayList< IGridElementCreator >();
    ExtensionManager manager = new ExtensionManager();
    List< Object > objectList
      = manager.getExecutableExtensions( GRID_ELEMENT_CREATOR_POINT,
                                         GRID_ELEMENT_CREATOR_ELEMENT,
                                         GRID_ELEMENT_CREATOR_EXECUTABLE );
    for ( Object o : objectList ) {
      if ( o instanceof IGridElementCreator ) {
        resultList.add( ( IGridElementCreator ) o );
      }
    }
    return resultList;
  }
  
  public static List< IProblemProvider > getRegisteredProblemProviders() {
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
    return resultList;
  }
  
}
