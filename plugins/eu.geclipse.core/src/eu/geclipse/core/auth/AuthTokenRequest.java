/*****************************************************************************
 * Copyright (c) 2007-2008 g-Eclipse Consortium 
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
 * This class is used by token requesters in order to specify the type
 * of the token, a name for the requester and the purpose of the token.
 * The requester name and the purpose may be used to present the user
 * some additional information if user interaction is needed in order
 * to create a new token.
 */
public class AuthTokenRequest {
  
  /**
   * The token description.
   */
  private IAuthenticationTokenDescription description;
  
  /**
   * A short descriptive text representing the requester or consumer of the token.
   */
  private String requester;
  
  /**
   * A descriptive text denoting the purpose of the token. 
   */
  private String purpose;

  /**
   * Create a new token request from the specified parameters. Any of the
   * parameters may be <code>null</code>. If the token description is
   * <code>null</code> any type of token will be returned. 
   * 
   * @param description The token description that determines which type
   * of token is requested. One or more parameters of the description are
   * not <code>null</code> only tokens will be returned that fulfill these
   * parameters.
   * @param requester A short descriptive text representing the requester
   * or consumer of the token.
   * @param purpose A descriptive text denoting the purpose of the token.
   */
  public AuthTokenRequest( final IAuthenticationTokenDescription description,
                           final String requester,
                           final String purpose ) {
    this.description = description;
    this.requester = requester;
    this.purpose = purpose;
  }
  
  /**
   * Get the token description.
   * 
   * @return The token description used to query the token.
   */
  public IAuthenticationTokenDescription getDescription() {
    return this.description;
  }
  
  /**
   * Get the token's purpose.
   * 
   * @return A descriptive text denoting the purpose of the token.
   */
  public String getPurpose() {
    return this.purpose;
  }
  
  /**
   * Get the token requester.
   * 
   * @return A short descriptive text representing the requester
   * or consumer of the token.
   */
  public String getRequester() {
    return this.requester;
  }

}
