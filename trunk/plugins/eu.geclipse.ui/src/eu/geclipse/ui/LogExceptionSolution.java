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

import eu.geclipse.ui.internal.Activator;

/**
 * Solution implementation for logging an exception to the
 * log and to bring the log view to front. 
 */
public class LogExceptionSolution
    extends ShowViewSolution {
  
  /**
   * The ID of the PDE log view.
   */
  private static final String LOG_VIEW_ID
    = "org.eclipse.pde.runtime.LogView"; //$NON-NLS-1$
  
  /**
   * The exception to be logged.
   */
  private Throwable exc;

  /**
   * Create a new <code>LogExceptionSolution</code> for the specified
   * exception.
   * 
   * @param exc The exception to be logged.
   */
  public LogExceptionSolution( final Throwable exc ) {
    super( UISolutionRegistry.LOG_EXCEPTION,
           Messages.getString("LogExceptionSolution.log_exception"), //$NON-NLS-1$
           LOG_VIEW_ID );
    this.exc = exc;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.UISolution#solve()
   */
  @Override
  public void solve() {
    Activator.logException( this.exc );
    super.solve();
  }
  
}
