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

package eu.geclipse.core.auth;

import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.Extensions;

/**
 * Abstract implementation of the {@link eu.geclipse.core.auth.IAuthTokenProvider}
 * interface that adds some static methods to deal with the extensions of that
 * interface. The most important methods are therefore the static access methods
 * for retrieving authentication tokens.
 * 
 * @author stuempert-m
 */
public abstract class AbstractAuthTokenProvider implements IAuthTokenProvider {

  /**
   * Static access method that calls the corresponding method of the
   * token provider with the highest priority of all currently
   * registered providers.
   * 
   * @return An authentication token if at least one provider is 
   * registered and a token could be found.
   * @see eu.geclipse.core.auth.IAuthTokenProvider#requestToken()
   */
  public static synchronized IAuthenticationToken staticRequestToken() {
    IAuthTokenProvider provider = getHighestPriorityProvider();
    return provider == null ? null : provider.requestToken();
  }
  
  /**
   * Static access method that calls the corresponding method of the
   * token provider with the highest priority of all currently
   * registered providers.
   * 
   * @param description type of token to return.
   * @return An authentication token if at least one provider is 
   * registered and a token could be found.
   * @see eu.geclipse.core.auth.IAuthTokenProvider#requestToken(IAuthenticationTokenDescription)
   */
  public static synchronized IAuthenticationToken staticRequestToken( final IAuthenticationTokenDescription description ) {
    IAuthTokenProvider provider = getHighestPriorityProvider();
    return provider == null ? null : provider.requestToken( description );
  }
  
  /**
   * Get the token provider with the highest priority.
   * 
   * @return The token provider that has the highest priority of all
   * currently registered token providers. 
   */
  static private IAuthTokenProvider getHighestPriorityProvider() {
    
    IConfigurationElement element = null;
    int priority = Integer.MIN_VALUE;
    ExtensionManager extensionBrowser = new ExtensionManager();
    List< IConfigurationElement > providers
      = extensionBrowser.getConfigurationElements( Extensions.AUTH_TOKEN_MANAGEMENT_POINT,
                                                   Extensions.AUTH_TOKEN_PROVIDER_ELEMENT );
    
    for ( IConfigurationElement e : providers ) {
      String prioString = e.getAttribute( "priority" ); //$NON-NLS-1$
      try {
        int p = Integer.parseInt( prioString );
        if ( p > priority ) {
          element = e;
          priority = p;
        }
      } catch ( NumberFormatException nfExc ) {
        eu.geclipse.core.internal.Activator.logException( nfExc );
      }
    }
    
    IAuthTokenProvider provider = null;
    if ( element != null ) {
      try {
        provider = ( IAuthTokenProvider ) element.createExecutableExtension( "class" ); //$NON-NLS-1$
      } catch( CoreException cExc ) {
        eu.geclipse.core.internal.Activator.logException( cExc );
      }
    }
    
    return provider;
    
  }
  
}
