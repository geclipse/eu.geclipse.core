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

package eu.geclipse.core.reporting.internal;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import eu.geclipse.core.reporting.IConfigurableSolver;
import eu.geclipse.core.reporting.ISolution;
import eu.geclipse.core.reporting.ISolver;

/**
 * Plug-in internal implementation of the {@link ISolution}-interface.
 */
public class Solution
    implements ISolution, IConfigurableSolver {
  
  /**
   * The solution's ID in case this is a descriptively defined
   * solution.
   */
  private String id;
  
  /**
   * The solution's description.
   */
  private String description;
  
  /**
   * The solution's optional solver.
   */
  private ISolver solver;
  
  /**
   * Create a new solution with the specified id, description
   * and solver.
   * 
   * @param id The unique ID of the solution if this is
   * a descriptively defined solution. <code>null</code> id this
   * is a programmatically defined solution.
   * @param description The description of the solution.
   * @param solver An optional solver for this solution.
   */
  public Solution( final String id,
                   final String description,
                   final ISolver solver ) {
    this.id = id;
    this.description = description;
    this.solver = solver;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.ISolution#getDescription()
   */
  public String getDescription() {
    return this.description;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.ISolution#getID()
   */
  public String getID() {
    return this.id;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.ISolution#isActive()
   */
  public boolean isActive() {
    return this.solver != null;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
   */
  public void setInitializationData( final IConfigurationElement config,
                                     final String propertyName,
                                     final Object data )
      throws CoreException {
    if ( this.solver instanceof IConfigurableSolver ) {
      ( ( IConfigurableSolver ) this.solver ).setInitializationData( config, propertyName, data );
    }
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.ISolver#solve()
   */
  public void solve() throws InvocationTargetException {
    if ( this.solver != null ) {
      this.solver.solve();
    }
  }

}
