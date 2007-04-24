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
import eu.geclipse.core.SolutionRegistry;

/**
 * Solution registry for all general solutions related to the UI.
 */
public class UISolutionRegistry extends SolutionRegistry {
  
  /**
   * ID of the log exception solution.
   */
  public static final int LOG_EXCEPTION
    = uniqueID();
  
  /**
   * The singleton instance.
   */
  private static UISolutionRegistry singleton;
  
  /**
   * The {@link Shell} used to create the UI solutions.
   */
  private Shell shell;
  
  /**
   * Private constructor.
   *  
   * @param shell The {@link Shell} used to create the UI solutions.
   */
  private UISolutionRegistry( final Shell shell ) {
    this.shell = shell;
  }
  
  /**
   * Static access method to the singleton instance.
   * 
   * @param shell The {@link Shell} used to create the UI solutions.
   * @return The singleton instance of the <code>UISolutionRegistry</code>.
   */
  public static UISolutionRegistry getRegistry( final Shell shell ) {
    if ( singleton == null ) {
      singleton = new UISolutionRegistry( shell );
    }
    return singleton;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.SolutionRegistry#findSolution(int)
   */
  @Override
  protected ISolution findSolution( final int solutionID ) {

    ISolution solution = super.findSolution( solutionID );
    
    if ( solutionID == SolutionRegistry.CHECK_PROXY_SETTINGS ) {
      solution = new PreferenceSolution( solution,
                                         this.shell,
                                         PreferenceSolution.NETWORK_PREFERENCE_PAGE );
    }
    
    else if ( solutionID == SolutionRegistry.CHECK_TIMEOUT_SETTINGS ) {
      solution = new PreferenceSolution( solution,
                                         this.shell,
                                         PreferenceSolution.NETWORK_PREFERENCE_PAGE );
    }

    return solution;

  }
  
}
