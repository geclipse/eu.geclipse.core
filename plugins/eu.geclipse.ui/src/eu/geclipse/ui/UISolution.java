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

package eu.geclipse.ui;

import org.eclipse.swt.widgets.Shell;
import eu.geclipse.core.ISolution;
import eu.geclipse.core.Solution;

/**
 * Abstract implementation of the {@link ISolution} interface that is
 * intended to trigger UI actions. UI solutions may either be stand-alone
 * or wrappers for non-UI solutions.
 */
public abstract class UISolution extends Solution {
  
  /**
   * The {@link Shell} that may be used for UI actions.
   */
  private Shell shell;
  
  /**
   * Construct a new UI solutions that maps the specified slave to
   * the UI.
   *  
   * @param slave A non-UI solution that is mapped to the UI.
   * @param shell A {@link Shell} that may be used to trigger the
   * UI action.
   */
  protected UISolution( final ISolution slave,
                        final Shell shell ) {
    this( slave.getID(), slave.getText(), shell );
  }
  
  /**
   * Construct a new UI solution from the specified parameters.
   * 
   * @param id The unique ID of the solution.
   * @param text The text describing this solution.
   * @param shell A {@link Shell} that may be used to trigger the
   * UI action.
   */
  protected UISolution( final int id,
                        final String text,
                        final Shell shell ) {
    super( id, text );
    this.shell = shell;
  }
  
  /**
   * Get the {@link Shell} that may be used to trigger the UI action.
   * 
   * @return The {@link Shell} or <code>null</code> if a specific
   * implementation does not need a {@link Shell} in order to trigger
   * its action.
   */
  public Shell getShell() {
    return this.shell;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.Solution#isActive()
   */
  @Override
  public boolean isActive() {
    return true;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.Solution#solve()
   */
  @Override
  public abstract void solve();
  
}
