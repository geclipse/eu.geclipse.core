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

package eu.geclipse.core;

/**
 * Concrete implementation of the {@link ISolution} interface. Implementations
 * of {@link ISolution} should always extend this class rather than implementing
 * the interface by themselves. This standard implementation is a passive
 * solution so {@link #solve()} does nothing. 
 */
public class Solution implements ISolution {
  
  /**
   * The solutions unique ID.
   */
  private int id;
  
  /**
   * A text describing the solution.
   */
  private String text;
  
  /**
   * Construct a new solution with the specified ID and text.
   * 
   * @param id The unique ID of this solution.
   * @param text A descriptive text that describes the solution.
   */
  protected Solution( final int id, final String text ) {
    this.id = id;
    this.text = text;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.ISolution#getID()
   */
  public int getID() {
    return this.id;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.ISolution#getText()
   */
  public String getText() {
    return this.text;
  }

  /**
   * Standard implementation that returns false.
   */
  public boolean isActive() {
    return false;
  }

  /**
   * Standard implementation that does nothing.
   */
  public void solve() {
    // empty implementation
  }
  
}
