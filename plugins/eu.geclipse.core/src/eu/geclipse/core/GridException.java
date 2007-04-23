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
 *    Pawel Wolniewicz, PSNC 
 *****************************************************************************/

package eu.geclipse.core;

import org.eclipse.core.runtime.CoreException;

/**
 * Base class of all Grid exceptions. This exception is also the base
 * of g-Eclipse's problem reporting mechanism. Plugins that want to make
 * use of this exception type have to either use the predefined problems
 * from existing {@link IProblemProvider}s or have to implement their own
 * problem provider in order to define new problems and related solutions. 
 */
public class GridException extends CoreException {
  
  /**
   * Serialization ID.
   */
  private static final long serialVersionUID = -6816743743846963886L;
  
  /**
   * The problem that describes this exception.
   */
  private IProblem problem;
  
  /**
   * Construct a new <code>GridException</code> with the specified problem.
   * 
   * @param problemID The ID of the problem that describes the cause of
   * this problems and may give solutions to the problem.
   */
  public GridException( final int problemID ) {
    this( problemID, (Throwable)null );
  }
  
  /**
   * Construct a new <code>GridException</code> with the specified problem.
   * 
   * @param problemID The ID of the problem that describes the cause of
   * this problems and may give solutions to the problem.
   * @param description A descriptive string that gives a more detailed
   * description of the problem.
   */
  public GridException( final int problemID, final String description ) {
    this( problemID, (Throwable)null, description );
  }
  
  /**
   * Construct a new <code>GridException</code> with the specified problem.
   * 
   * @param problemID The ID of the problem that describes the cause of
   * this problems and may give solutions to the problem.
   * @param exc The exception that caused this problem.
   */
  public GridException( final int problemID,
                        final Throwable exc ) {
    this( ProblemRegistry.getRegistry().getProblem( problemID, exc ) );
  }
  
  /**
   * Construct a new <code>GridException</code> with the specified problem.
   * 
   * @param problemID The ID of the problem that describes the cause of
   * this problems and may give solutions to the problem.
   * @param exc The exception that caused this problem.
   * @param description A descriptive string that gives a more detailed
   * description of the problem.
   */
  public GridException( final int problemID,
                        final Throwable exc,
                        final String description ) {
    this(ProblemRegistry.getRegistry().getProblem( problemID, exc, description ));
  }

  /**
   * Construct a new <code>GridException</code> with the specified problem.
   *
   * @param problem The problem that describes the cause of
   * this problems and may give solutions to the problem.
   */
  public GridException( final IProblem problem ) {
    super( problem.getStatus() );
    this.problem = problem;
  }
  
  /**
   * Get the problem associated with this exception.
   * 
   * @return An {@link IProblem} that describes the cause of this exception.
   */
  public IProblem getProblem() {
    return this.problem;
  }
  
}
