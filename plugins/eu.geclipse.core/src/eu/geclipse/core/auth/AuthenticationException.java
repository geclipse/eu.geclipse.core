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

import eu.geclipse.core.GridException;

/**
 * This exception is thrown by methods dealing with security tokens.
 * 
 * @author stuempert-m
 */
public class AuthenticationException
    extends GridException {
  
  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2736623942948428527L;

  /**
   * Create a new Authentication Exception with the specified problem ID.
   * 
   * @param problemID The unique ID of the corresponding problem.
   */
  public AuthenticationException( final int problemID ) {
    super( problemID );
  }
  
  /**
   * Create a new Authentication Exception with the specified problem ID and
   * a string that contains the description.
   * 
   * @param problemID The unique ID of the corresponding problem.
   * @param description Description string of the problem
   */
  public AuthenticationException( final int problemID,
                                  final String description ) {
    super( problemID, description );
  }

  /**
   * Create a new Authentication Exception with the specified problem ID.
   * 
   * @param problemID The unique ID of the corresponding problem.
   * @param exc The exception that caused the problem.
   */
  public AuthenticationException( final int problemID,
                                  final Throwable exc ) {
    super( problemID, exc );
  }

}
