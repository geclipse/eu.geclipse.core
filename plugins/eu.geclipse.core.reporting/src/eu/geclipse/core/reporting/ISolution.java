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

package eu.geclipse.core.reporting;

/**
 * Definition of a solution that may be associated to one
 * or more problems. A solution may either be active or passive.
 * If a solutions is active its {@link ISolver#solve()}-method
 * is assumed to provide an action that solves the associated
 * problem(s). In general a solution has to contain a text that
 * gives a brief description of the solution that is presented
 * to the user.
 */
public interface ISolution
    extends ISolver {
  
  /**
   * Get this solution's ID.
   * 
   * @return The solution's unique identifier or <code>null</code>
   * if this solution was created programmatically.
   */
  public String getID();
  
  /**
   * Determine if this solution is active. An active solution
   * has to provide a reasonable implementation of the
   * {@link ISolver#solve()}-method in order to solve the
   * associated problem(s). Non-active solutions are called
   * passive solutions and only contain a descriptive text that
   * may give the user hints how to solve the associated
   * problem.
   * 
   * @return True if this solution is active, false for
   * passive solutions.
   */
  public boolean isActive();

  /**
   * Get a brief text that gives a description of this solution.
   * 
   * @return This solutions description.
   */
  public String getDescription();
  
}
