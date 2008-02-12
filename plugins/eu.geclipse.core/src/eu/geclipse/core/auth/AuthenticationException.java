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
 *    Ariel Garcia      - updated to new problem reporting
 *****************************************************************************/

package eu.geclipse.core.auth;

import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ProblemException;


/**
 * This exception is thrown by methods dealing with security tokens.
 * 
 * @author stuempert-m
 */
public class AuthenticationException
    extends ProblemException {
  
  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2736623942948428528L;
    
  /**
   * Create a new Authentication Exception with the specified problem ID.
   * 
   * @param problemID   The ID of the problem that should be reported.
   * @param pluginID    The ID of the plug-in where the problem happened.
   * 
   * @see #ProblemException(String,String)
   */
  public AuthenticationException( final String problemID,
                                  final String pluginID ) {
    super( problemID, pluginID );
  }
  
  /**
   * Create a new Authentication Exception with the specified problem ID
   * and the causing exception.
   * 
   * @param problemID   The ID of the problem that should be reported.
   * @param exception   A {@link Throwable} that may have caused the problem.
   * @param pluginID    The ID of the plug-in where the problem happened.
   * 
   * @see #ProblemException(String,Throwable,String)
   */
  public AuthenticationException( final String problemID,
                                  final Throwable exception,
                                  final String pluginID ) {
    super( problemID, exception, pluginID );
  }
  
  /**
   * Create a new Authentication Exception with the specified problem ID
   * and an alternate description.
   * 
   * @param problemID   The ID of the problem that should be reported.
   * @param description A description that will replace the problems standard description.
   * @param pluginID    The ID of the plug-in where the problem happened.
   * 
   * @see #ProblemException(String,String,String)
   */
  public AuthenticationException( final String problemID,
                                  final String description,
                                  final String pluginID ) {
    super( problemID, description, pluginID );
  }
  
  /**
   * Create a new Authentication Exception with the specified problem ID,
   * an alternate description, and the causing exception.
   * 
   * @param problemID   The ID of the problem that should be reported.
   * @param description An optional description that may replace the
   *                    problems standard description.
   * @param exception   An optional {@link Throwable} that may have caused
   *                    the problem.
   * @param pluginID    The ID of the plug-in where the problem happened.
   * 
   * @see #ProblemException(String,String,Throwable,String)
   */
  public AuthenticationException( final String problemID,
                                  final String description,
                                  final Throwable exception,
                                  final String pluginID ) {
    super( problemID, description, exception, pluginID );
  }
  
  /**
   * Create a new Authentication Exception with the specified associated problem.
   * 
   * @param problem The problem associated to this exception.
   * 
   * @see #ProblemException(IProblem)
   */
  public AuthenticationException( final IProblem problem ) {
    super( problem );
  }

}
