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

import java.util.List;
import org.eclipse.core.runtime.IStatus;

/**
 * Base interface of g-Eclipse's problem reporting mechanism. An <code>IProblem</code>
 * holds all necessary information to describe a problem that arised during runtime in
 * the framework. It may additionally give hints how to avoid this problem with the
 * help of a list of {@link ISolution}s.
 */
public interface IProblem {
  
  /**
   * Add a solution with the specified ID to this problem.
   * 
   * @param solutionID The ID of the solution that should be added. This ID has
   * to be defined at any registered {@link IProblemProvider}.
   */
  public void addSolution( final int solutionID );
  
  /**
   * Add a user-defined solution to this problem. This solution has not to be
   * defined by any registered {@link IProblemProvider}.
   * 
   * @param solution
   */
  public void addSolution( final ISolution solution );
  
  /**
   * Get the exception that caused this problem.
   *  
   * @return The associated exception.
   */
  public Throwable getException();
  
  /**
   * Get the unique ID of this problem.
   * 
   * @return The ID of this problem.
   */
  public int getID();
  
  /**
   * Get the solutions contained in this problem using the specified
   * {@link SolutionRegistry}.
   * 
   * @param registry The {@link SolutionRegistry} that is used to map solution
   * IDs to solutions.
   * @return A list of all solutions contained in this problem and mapped with
   * the specified solution registry.
   */
  public List< ISolution > getSolutions( final SolutionRegistry registry );

  /**
   * Get the status that maps this problem to the Eclipse error reporting
   * mechanism.
   * 
   * @return An {@link IStatus}-object describing this problem.
   */
  public IStatus getStatus();
  
  /**
   * Get a descriptive text that gives information about this problem.
   * 
   * @return A description of this problem.
   */
  public String getText();
  
  /**
   * This method allow to set specific text for already created problem.
   * Typical usage is to get predefined problem from ProblemRegistry and 
   * than set text explaining specific reason of the problem.
   * 
   * @param reason A text specific for a problem that is not associated
   * with a unique problem ID.
   */
  public void setReason( final String reason );
  
}
