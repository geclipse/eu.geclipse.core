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
 * A reporting service provides both methods for querying
 * the reporting mechanism for declarative problems and
 * solutions and to for programmatically creating programs
 * and solutions.
 * 
 * @see ReportingPlugin#getReportingService()
 */
public interface IReportingService {
  
  /**
   * Get a problem that was declaratively defined via the
   * eu.geclipse.core.reporting.problemReporting extension
   * point.
   * 
   * @param problemID The unique ID of the problem as declared
   * in the corresponding extension.
   * @param description A descriptive text that overwrites the
   * default text provided by the extension. If this is
   * <code>null</code> the default text will be taken.
   * @param exception A {@link Throwable} that may have caused
   * this problem. May be <code>null</code>.
   * @param pluginID The ID of the plug-in where the problem
   * happened.
   * @return The problem corresponding to the provided problem
   * ID and modified with the specified description and exception.
   * If no problem with the specified ID could be found a 
   * *unknown problem*-problem will be returned.
   */
  public IProblem getProblem( final String problemID,
                              final String description,
                              final Throwable exception,
                              final String pluginID );
  
  /**
   * Programmatically create a problem from the specified
   * description and exception. The {@link IProblem#addReason(String)}
   * and {@link IProblem#addSolution(ISolution)}/{@link IProblem#addSolution(String,String)}
   * may be used afterwards to further customize the problem.
   * 
   * @param description A descriptive text that gives a short
   * explanation of the problem.
   * @param exception A {@link Throwable} that may have caused
   * this problem. May be <code>null</code>.
   * @param mailto An email-address that may be used to send
   * an automated error report.
   * @param pluginID The ID of the plug-in where the problem
   * happened.
   * @return The newly created problem. This problem will not
   * be stored in the internal registry.
   */
  public IProblem getProblem( final String description,
                              final Throwable exception,
                              final String mailto,
                              final String pluginID );
  
  /**
   * Get a solution that was declaratively defined via the
   * eu.geclipse.core.reporting.problemReporting extension
   * point.
   * 
   * @param solutionID The unique ID of the solution as declared
   * in the corresponding extension.
   * @param description A descriptive text that overwrites the
   * default text provided by the extension. If this is
   * <code>null</code> the default text will be taken.
   * @return The solution corresponding to the provided solution
   * ID and modified with the specified description.
   * If no problem with the specified ID could be found
   * <code>null</code> will be returned.
   */
  public ISolution getSolution( final String solutionID,
                                final String description );
  
  /**
   * Programmatically create a solution from the specified
   * description and solver.
   * 
   * @param description A descriptive text that gives a short
   * explanation of the solution.
   * @param solver An instance of the {@link ISolver}-interface
   * that provides dedicated problem solving strategies. If this
   * is <code>null</code> the solution will be passive.
   * @return The newly created solution. This solution will not
   * be stored in the internal registry.
   */
  public ISolution getSolution( final String description,
                                final ISolver solver );

}
