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

import org.eclipse.core.runtime.IStatus;

/**
 * This exception is thrown if information from authentication tokens
 * are requested while the token is not active.
 *  
 * @author stuempert-m
 */
public class TokenNotActiveException extends AuthenticationException {

  private static final long serialVersionUID = 2795313500643203965L;
  
  /**
   * Create a new <code>TokenNotActiveException</code> with the specified
   * {@link IStatus}.
   * 
   * @param status The {@link IStatus} that describes this exception. 
   */
  public TokenNotActiveException( final IStatus status ) {
    super( status );
  }

}
