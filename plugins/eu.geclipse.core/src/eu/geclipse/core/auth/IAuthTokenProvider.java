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
 * This interface is the base for all authentication token providers.
 * It is included in the authentication management extension point but
 * it is not recommended to extend this mechanism since there are
 * two existing extensions (core and ui) that should fit all needs.
 * <p>
 * Authentication token providers are the central point for all non-core
 * plugin to request authentication tokens. These non-core classes should
 * not make use of the {@link eu.geclipse.core.auth.AuthenticationTokenManager}.
 *  
 * @author stuempert-m
 */
public interface IAuthTokenProvider {
  
  /**
   * Request any authentication token. The requested token can have
   * any type. This is a shortcut for <code>requestToken( null )</code>.
   * 
   * @return Any token that could be found. In fact the currently defined
   * default token is returned.
   */
  public IAuthenticationToken requestToken();
  
  /**
   * Request an authentication token of the specified type. If the default
   * token is not of the specified type but an appropriate token could be
   * found this token will be set to be the new default token.
   * 
   * @param description A token description that is used to determine the
   * type of the token that is requested.
   * @return A token that matches the specified token description.
   */
  public IAuthenticationToken requestToken( final IAuthenticationTokenDescription description );
  
}
