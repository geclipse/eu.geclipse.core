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

public class AuthTokenRequest {
  
  private IAuthenticationTokenDescription description;
  
  private String requester;
  
  private String purpose;

  public AuthTokenRequest( final IAuthenticationTokenDescription description,
                           final String requester,
                           final String purpose ) {
    this.description = description;
    this.requester = requester;
    this.purpose = purpose;
  }
  
  public IAuthenticationTokenDescription getDescription() {
    return this.description;
  }
  
  public String getPurpose() {
    return this.purpose;
  }
  
  public String getRequester() {
    return this.requester;
  }

}
