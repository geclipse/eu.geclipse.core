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
 *****************************************************************************/

package eu.geclipse.core.auth;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.core.security.BaseSecurityManager;

/**
 * The <code>AuthenticationTokenManager</code> manages all types of authentication tokens.
 * It holds an internal list of all currently available authentication tokens. Although the
 * authentication token manager is the base class of the token management system, non-core
 * classes should use an {@link eu.geclipse.core.auth.IAuthTokenProvider} to request a token.
 * 
 * @author stuempert-m
 */
public class AuthenticationTokenManager
    extends BaseSecurityManager {
  
  /**
   * The singleton that holds the instance of this
   * <code>AuthenticationTokenManager</code>.
   */
  static private AuthenticationTokenManager singleton = null;
  
  /**
   * The internal list of managed tokens.
   */
  private List<IAuthenticationToken> tokens = new ArrayList<IAuthenticationToken>();
  
  /**
   * The token used as default.
   */
  private IAuthenticationToken defaultToken;
  
  /**
   * Private constructor. Use the {@link getManager} method to get the singleton.
   */
  private AuthenticationTokenManager() {
    // empty implementation
  }
  
  /**
   * Get the singleton. There is only one instance of the <code>AuthenticationTokenManager</code>
   * that is created and returned by this method.
   * 
   * @return The singleton.
   */
  public static AuthenticationTokenManager getManager() {
    if ( singleton == null ) {
      singleton = new AuthenticationTokenManager();
    }
    return singleton;
  }
  
  /**
   * Create a new authentication token from the specified token description and adds it
   * to the list of managed tokens.
   * 
   * @param description The description from which to create the new token.
   * @return A new authentication token constructed from the specified description.
   * @throws AuthenticationException If an error occurs while creating the token
   * @see IAuthenticationTokenDescription#createToken()
   */
  public IAuthenticationToken createToken( final IAuthenticationTokenDescription description )
    throws AuthenticationException
  {
    IAuthenticationToken token = description.createToken();
    addToken( token );
    return token;
  }
  
  /**
   * Searches for an authentication token that matches the specified description. If no token
   * could be found <code>null</code> is returned.
   * 
   * @param description The {@link IAuthenticationTokenDescription} that describes the token.
   * @return A token that matches the specified description or null if no such token could be found.
   */
  public IAuthenticationToken findToken( final IAuthenticationTokenDescription description ) {
    IAuthenticationToken resultToken = null;
    for ( IAuthenticationToken token : this.tokens ) {
      if ( token.getDescription().equals( description ) ) {
        resultToken = token;
        break;
      }
    }
    return resultToken;
  }
  
  /**
   * Get all tokens that are currently available.
   * 
   * @return A copy of the internal list of managed tokens.
   */
  public List< IAuthenticationToken > getTokens() {
    return new ArrayList< IAuthenticationToken >( this.tokens );
  }

  /**
   * Destroy the specified {@link IAuthenticationToken}. This means especially that the
   * specified token is removed from the list of currently managed tokens. Therefore this
   * token is not longer accessible from this manager and should not longer be used.
   *  
   * @param token The {@link IAuthenticationToken} to be destroyed.
   * @throws AuthenticationException
   */
  public void destroyToken( final IAuthenticationToken token ) throws AuthenticationException {
    removeToken( token );
    token.setActive( false );
  }
  
  /**
   * Get the number of currently available tokens.
   * 
   * @return The number of tokens that are currently contained in the internal list of
   * managed tokens.
   */
  public int getTokenCount() {
    return this.tokens.size();
  }
  
  /**
   * Determine if this token manager's list of managed tokens is currently empty.
   * 
   * @return True if there are no tokens available, false otherwise.
   */
  public boolean isEmpty() {
    return getTokenCount() == 0;
  }
  
  /**
   * Set the specified token to be the default token. The default token is used whenever
   * no specific token is used.
   * 
   * @param token The token to be the default token. The default token is only changed if
   * the specified token is in the list of managed tokens and is not already set as the
   * default token.
   * @see #getDefaultToken()
   */
  public void setDefaultToken( final IAuthenticationToken token ) {
    if ( ( token == null ) && ( this.defaultToken != null ) ) {
      this.defaultToken = null;
      manageDefaultToken();
      fireContentChanged();
    }
    if ( this.tokens.contains( token ) && ( this.defaultToken != token ) ) {
      this.defaultToken = token;
      fireContentChanged();
    }
  }
  
  /**
   * Get the currently defined default token.
   * 
   * @return The token that is currently defined to be the default token.
   * @see #setDefaultToken(IAuthenticationToken)
   */
  public IAuthenticationToken getDefaultToken() {
    return this.defaultToken;
  }
  
  /**
   * Add the specified IAuthenticationToken to the list of managed tokens.
   * 
   * @param token The token to be added.
   */
  protected void addToken( final IAuthenticationToken token ) {
    this.tokens.add( token );
    manageDefaultToken();
    fireContentChanged();
  }
  
  /**
   * Remove the specified token from the list of managed tokens.
   * 
   * @param token The token to be removed.
   * @return True if the token was contained in the list of managed tokens.
   */
  protected boolean removeToken( final IAuthenticationToken token ) {
    boolean result = this.tokens.remove( token );
    if ( result ) {
      manageDefaultToken();
      fireContentChanged();
    }
    return result;
  }
  
  /**
   * Takes care that a default token is always defined if the list of managed tokens is not empty.
   * 
   * @return True if the default token has changed.
   */
  private boolean manageDefaultToken() {
    boolean changed = false;
    if ( ( this.defaultToken != null ) && !this.tokens.contains( this.defaultToken ) ) {
      this.defaultToken = null;
      changed = true;
    }
    if ( ( this.defaultToken == null ) && !this.tokens.isEmpty() ) {
      this.defaultToken = this.tokens.get( 0 );
      changed = true;
    }
    return changed;
  }
  
}
