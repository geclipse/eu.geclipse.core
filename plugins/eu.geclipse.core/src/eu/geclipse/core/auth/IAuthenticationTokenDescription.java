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
 * This interface is the base for all classes that describe specialised
 * authentication tokens like grid proxies. 
 * 
 * @author stuempert-m
 */
public interface IAuthenticationTokenDescription {

  /**
   * Create a token from this <code>IAuthenticationTokenDescription</code>.
   * This method should never be called directly. Use instead
   * {@link AuthenticationTokenManager#createToken(IAuthenticationTokenDescription)}
   * within the core or the {@link eu.geclipse.core.auth.IAuthTokenProvider}
   * from outside the core to get a valid authentication token.
   * 
   * @return A new authentication token that is created from the settings
   *         of this <code>IAuthenticationTokenDescription</code>.
   * @throws AuthenticationException If the token could not be created due
   *                                 to some error.
   */
  public IAuthenticationToken createToken() throws AuthenticationException;
  
  /**
   * Get the name of the token type that is described by this description. This
   * is a short name like "Grid Proxy" or "VOMS Proxy" that is used in UI
   * components to reference this description.
   * 
   * @return The name of the type of tokens that is described by this description.
   */
  public String getTokenTypeName();
  
  /**
   * Returns the id of the wizard to be shown in the WizardSelectionPage of
   * the authorization token wizard.
   * @return the wizard for this token type.
   */
  public String getWizardId();
  
  /**
   * Determine if this token matches the specified one. Implementations have to
   * test all available parameters of a token. Parameters that are <code>null</code>
   * within this token are not checked against the other token. All other
   * parameters have to be not zero for the other token and have to be the
   * same for both tokens.
   * 
   * @param otherToken The token to be checked against this token.
   * @return <code>true</code> if all non-null parameters of this token
   * match the parameters of the specified token. 
   */
  public boolean matches( final IAuthenticationTokenDescription otherToken );
  
}
