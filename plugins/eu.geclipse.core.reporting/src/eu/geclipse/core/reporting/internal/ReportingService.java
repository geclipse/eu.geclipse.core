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

import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.IReportingService;
import eu.geclipse.core.reporting.ISolution;
import eu.geclipse.core.reporting.ISolver;

/**
 * Plugin-internal implementation of the {@link IReportingService}.
 */
public class ReportingService
    implements IReportingService {
  
  /**
   * The singleton instance.
   */
  private static ReportingService singleton;
  
  /**
   * Private constructor
   */
  private ReportingService() {
    // empty implementation
  }
  
  /**
   * Get the singleton instance.
   * 
   * @return The service's singleton instance.
   */
  public static ReportingService getService() {
    if ( singleton == null ) {
      singleton = new ReportingService();
    }
    return singleton;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.IReportingService#getProblem(java.lang.String, java.lang.String, java.lang.Throwable, java.lang.String)
   */
  public IProblem getProblem( final String problemID,
                              final String description,
                              final Throwable exception,
                              final String pluginID ) {
    return ProblemFactory.getFactory().getProblem( problemID, description, exception, pluginID );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.IReportingService#getProblem(java.lang.String, java.lang.Throwable, java.lang.String, java.lang.String)
   */
  public IProblem getProblem( final String description,
                              final Throwable exception,
                              final String mailto,
                              final String pluginID ) {
    return new Problem( null, description, exception, mailto, pluginID );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.IReportingService#getSolution(java.lang.String, java.lang.String)
   */
  public ISolution getSolution( final String solutionID,
                                final String description ) {
    return SolutionFactory.getFactory().getSolution( solutionID, description );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.IReportingService#getSolution(java.lang.String, eu.geclipse.core.reporting.ISolver)
   */
  public ISolution getSolution( final String description,
                                final ISolver solver ) {
    return new Solution( null, description, solver );
  }

}
