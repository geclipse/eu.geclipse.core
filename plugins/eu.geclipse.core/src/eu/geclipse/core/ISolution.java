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

/**
 * Solutions are thought to give the user a hint how to solve a problem in the
 * context of the g-Eclipse problem reporting mechanism. A solution may either
 * be active or passive. Passive solutions are only descriptive texts that may
 * present the user ways how to deal with the problem. Active solutions allow
 * to trigger an action that may solve the problem.
 * 
 * Solutions may be shared among problems. Different problem may have the same
 * solutions. An example would be a solution to check the internet connection
 * that could be a solution for all problems that are related to networking.
 * Therefore solutions are reusable by their unique ID.
 */
public interface ISolution {
  
  /**
   * Get the ID of the solution. Each solution has a unique ID.
   * 
   * @return The solution's unique ID.
   */
  public int getID();
  
  /**
   * Get a descriptive text that explains the user how to deal with a problem.
   * 
   * @return A text explaining this solution. 
   */
  public String getText();
  
  /**
   * Determines if this solution is active or passive.
   * 
   * @return True if this is an active solution.
   */
  public boolean isActive();
  
  /**
   * If this solution is active this method triggers the action that may resolve
   * the corresponding problem. If this solution is passive this has to be an
   * empty implementation.
   */
  public void solve();
  
}
