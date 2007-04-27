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

/**
 * Core implementation of the {@link eu.geclipse.core.auth.IAuthTokenProvider}
 * interface to give a core extension for the authentication management
 * extension point.
 * 
 * @author stuempert-m
 */
public class CoreAuthTokenProvider extends AbstractAuthTokenProvider {

  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.IAuthTokenProvider#requestToken()
   */
  public IAuthenticationToken requestToken() {
    return requestToken( null );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.IAuthTokenProvider#requestToken(eu.geclipse.core.auth.IAuthenticationTokenDescription)
   */
  public IAuthenticationToken requestToken( final IAuthenticationTokenDescription description ) {
    
    // Try to find an appropriate token
    AuthenticationTokenManager manager = AuthenticationTokenManager.getManager();
    IAuthenticationToken token = manager.getDefaultToken();
    if ( ( token != null ) && ( description != null ) ) {
      IAuthenticationTokenDescription tDesc = token.getDescription();
      if ( !description.getClass().isAssignableFrom( tDesc.getClass() ) ) {
        token = null;
        for( IAuthenticationToken t : manager.getTokens() ) {
          tDesc = t.getDescription();
          if( description.getClass().isAssignableFrom( tDesc.getClass() ) ) {
            token = t;
            manager.setDefaultToken( token );
            break;
          }
        }
      }
    }
    
    // Validate and activate the token if necessary
    if ( token != null ) {
      try {
        if ( !token.isValid() ) {
          token.validate();
        }
        if ( !token.isActive() ) {
          token.setActive( true );
        }
      } catch ( AuthenticationException authExc ) {
        eu.geclipse.core.internal.Activator.logException( authExc );
      }
    }
    
    return token;
    
  }
  
}
