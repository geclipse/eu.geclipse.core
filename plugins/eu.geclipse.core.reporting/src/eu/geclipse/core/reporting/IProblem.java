/*****************************************************************************
 * Copyright (c) 2007, 2008 g-Eclipse Consortium 
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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

/**
 * Definition of a problem that may be appended to a {@link ProblemException}.
 * Problems are defined by a description, zero or more reasons and zero or
 * more solutions. The description is just a text that gives a more or less
 * detailed description of the problem. The reasons are a list of strings that
 * may give hints about the reasons of the problem. The solutions are instances
 * of {@link ISolution} and may assist the user by solving this problem.
 * 
 * Problems may either be defined programmatically or via the
 * eu.geclipse.core.reporting.problemReporting extension point.
 */
public interface IProblem {
  
  /**
   * Add a new reason to this problem. Different implementation
   * layers may be aware of different reasons for a specific
   * problem. Therefore each layer may add additional reasons to
   * a given problem.
   * 
   * @param reason A short text giving a further reason for this
   * problem.
   */
  public void addReason( final String reason );
  
  /**
   * Add a new solution to this problem. Different implementation
   * layers may be aware of different solutions for a specific
   * problem. Therefore each layer may add additional solutions to
   * a given problem.
   * 
   * @param solution A solution that will be added to this problem.
   */
  public void addSolution( final ISolution solution );
  
  /**
   * Add a new solution to this problem. Different implementation
   * layers may be aware of different solutions for a specific
   * problem. Therefore each layer may add additional solutions to
   * a given problem.
   * 
   * @param solutionID The unique ID of the solution that will be
   * added to this problem.
   * @param description An optional alternative description for the
   * solution. If this is <code>null</code> the solution's default
   * description will be taken.
   */
  public void addSolution( final String solutionID, final String description );
  
  /**
   * Get the descriptive text that gives a more or less detailed
   * explanation about this problem.
   * 
   * @return The description of this problem.
   */
  public String getDescription();
  
  /**
   * Get an exception that may be the reason for this problem.
   * 
   * @return A {@link Throwable} or <code>null</code> if this
   * problem was not caused by one.
   */
  public Throwable getException();
  
  /**
   * Get the ID of this problem.
   * 
   * @return The problem's ID.
   */
  public String getID();
  
  /**
   * Get the mailing-address as specified in the mailto-field of
   * the problem extension point.
   *  
   * @return The mailing address where this problem should be reported
   * or <code>null</code> if no such mailing address is specified for
   * this problem. 
   */
  public String getMailTo();
  
  /**
   * Get the ID of the plug-in where the problem happened.
   * 
   * @return The plug-in's ID.
   */
  public String getPluginID();
  
  /**
   * Get a list of all reasons that may give further hints about the nature
   * of this problems.
   *  
   * @return A list of all currently available reasons for this
   * problem or an empty array if no reasons are available.
   */
  public String[] getReasons();
  
  /**
   * Get all solutions that are associated to this problem.
   * 
   * @return A list of all currently available solutions for
   * this problem or an empty array if no solutions are
   * available.
   */
  public ISolution[] getSolutions();
  
  /**
   * Get an {@link IStatus}-object created from this problem. This
   * object represents the connection to the
   * {@link CoreException}-mechanism.
   * 
   * @return An {@link IStatus}-object that is created on the fly
   * an represents the state of this problem at creation time. Notice
   * that the state may change due to calls to {@link #addReason(String)}
   * and {@link #addSolution(ISolution)}.
   */
  public IStatus getStatus();
  
  /**
   * Determines if this problem has associated reasons.
   * 
   * @return True if the problem contains at least one reason.
   */
  public boolean hasReasons();
  
  /**
   * Determines if this problem has associated solutions.
   * 
   * @return True if the problem contains at least one solution.
   */
  public boolean hasSolutions();
  
  /**
   * Merge the specified problem with this problem. Both the description and
   * the reasons of the specified problem are added as reasons to this problem
   * if they are not already existent. Additionally the solutions are added to
   * this problem.
   * 
   * @param problem The problem to incorporate into this problem.
   */
  public void merge( final IProblem problem );

}
