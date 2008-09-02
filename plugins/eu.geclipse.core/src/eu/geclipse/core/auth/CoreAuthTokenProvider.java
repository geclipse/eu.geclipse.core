/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
 *    Jie Tao -- extensions
 *****************************************************************************/

package eu.geclipse.core.auth;

import java.util.List;

import eu.geclipse.core.internal.Activator;


/**
 * Core implementation of the {@link eu.geclipse.core.auth.IAuthTokenProvider}
 * interface to give a core extension for the authentication management
 * extension point.
 */
public class CoreAuthTokenProvider extends AbstractAuthTokenProvider {

  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.IAuthTokenProvider#requestToken()
   */
  public IAuthenticationToken requestToken() {
    //return requestToken( new AuthTokenRequest( null, null, null ) );
    return requestToken( null);
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.IAuthTokenProvider#requestToken(eu.geclipse.core.auth.IAuthenticationTokenDescription)
   */
  public IAuthenticationToken requestToken( final AuthTokenRequest request ) {
    
    /*
     * Note: we are now returning the default token if request = null
     */
    IAuthenticationToken token = null;
    if ( request == null ) {
      token = AuthenticationTokenManager.getManager().getDefaultToken();
    } else {
      IAuthenticationTokenDescription description = request.getDescription();
      token = checkDefaultToken( description );

      if ( token == null ) {
        token = checkAnyToken( description );
      }
    }
    
    if ( token != null ) {
      AuthenticationTokenManager.getManager().setDefaultToken( token );
      try {
        if ( !token.isValid() ) {
          token.validate();
        }
        if ( !token.isActive() ) {
          token.setActive( true );
        }
      } catch ( AuthenticationException authExc ) {
        Activator.logException( authExc );
      }
    }
    
    return token;
    
  }
  
  private IAuthenticationToken checkDefaultToken( final IAuthenticationTokenDescription description ) {
    
    IAuthenticationToken result = null;
    
    AuthenticationTokenManager manager = AuthenticationTokenManager.getManager();
    IAuthenticationToken token = manager.getDefaultToken();
    
    if ( checkToken( token, description ) ) {
      result = token;
    }
    
    return result;
    
  }
  
  private IAuthenticationToken checkAnyToken( final IAuthenticationTokenDescription description ) {
    
    IAuthenticationToken result = null;
    
    AuthenticationTokenManager manager = AuthenticationTokenManager.getManager();
    List< IAuthenticationToken > tokens = manager.getTokens();
    
    for ( IAuthenticationToken token : tokens ) {
      if ( checkToken( token, description ) ) {
        result = token;
        break;
      }
    }
    
    return result;
    
  }
  
  private boolean checkToken( final IAuthenticationToken token,
                              final IAuthenticationTokenDescription description ) {
    
    boolean result = false;
    
    if ( token != null ) {
      IAuthenticationTokenDescription tokenDescription = token.getDescription();
      result = description.matches( tokenDescription );
    }
    
    return result;
    
  }
  
}
