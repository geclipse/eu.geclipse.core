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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

/**
 * This exception or one of its subclasses is thrown by methods
 * dealing with security tokens.
 * 
 * @author stuempert-m
 */
public class AuthenticationException extends CoreException {

  private static final long serialVersionUID = 870794359732379699L;

  /**
   * Create a new <code>AuthenticationTokenException</code> from the
   * specified status.
   * 
   * @param status The status that describes the exception.
   */
  public AuthenticationException( final IStatus status ) {
    super( status );
  }

}
