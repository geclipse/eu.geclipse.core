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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

/**
 * Exception implementation that may carry an associated
 * {@link IProblem}. 
 */
public class ProblemException extends CoreException {
  
  /**
   * The serial version UID.
   */
  private static final long serialVersionUID = 3570521051854249775L;
  
  /**
   * The associated problem if one was specified.
   */
  private IProblem problem;
  
  /**
   * Create a new {@link ProblemException} for the problem with
   * the specified ID.
   * 
   * @param problemID The ID of the problem that should be reported.
   * @param pluginID The ID of the plug-in where the problem happened.
   * @see #ProblemException(IProblem)
   */
  public ProblemException( final String problemID,
                           final String pluginID ) {
    this( problemID, null, null, pluginID );
  }
  
  /**
   * Create a new {@link ProblemException} for the problem with
   * the specified ID.
   * 
   * @param problemID The ID of the problem that should be reported.
   * @param exception An optional {@link Throwable} that may have caused
   * the problem.
   * @param pluginID The ID of the plug-in where the problem happened.
   * @see #ProblemException(IProblem)
   */
  public ProblemException( final String problemID,
                           final Throwable exception,
                           final String pluginID ) {
    this( problemID, null, exception, pluginID );
  }
  
  /**
   * Create a new {@link ProblemException} for the problem with
   * the specified ID.
   * 
   * @param problemID The ID of the problem that should be reported.
   * @param description An optional description that may replace the
   * problems standard description.
   * @param pluginID The ID of the plug-in where the problem happened.
   * @see #ProblemException(IProblem)
   */
  public ProblemException( final String problemID,
                           final String description,
                           final String pluginID ) {
    this( problemID, description, null, pluginID );
  }
  
  /**
   * Create a new {@link ProblemException} for the problem with
   * the specified ID.
   * 
   * @param problemID The ID of the problem that should be reported.
   * @param description An optional description that may replace the
   * problems standard description.
   * @param exception An optional {@link Throwable} that may have caused
   * the problem.
   * @param pluginID The ID of the plug-in where the problem happened.
   * @see #ProblemException(IProblem)
   */
  public ProblemException( final String problemID,
                           final String description,
                           final Throwable exception,
                           final String pluginID ) {
    this( ReportingPlugin.getReportingService().getProblem( problemID, description, exception, pluginID ) );
  }
  
  /**
   * Create a new {@link ProblemException} with the specified
   * associated problem. The {@link IProblem#getStatus()}-method
   * is used to create a status for the super constructor. Notice
   * that this status object represents the state of the problem at
   * the creation time of the exception. See {@link IProblem#getStatus()}
   * for more details.
   * 
   * @param problem The problem associated to this exception.
   * @see #ProblemException(IStatus)
   */
  public ProblemException( final IProblem problem ) {
    this( problem.getStatus() );
    this.problem = problem;
  }

  /**
   * Create a new {@link ProblemException} for the specified
   * {@link IStatus}-object. This constructor creates a
   * {@link ProblemException} without an associated {@link IProblem}.
   * 
   * @param status The {@link IStatus}-object that is directly
   * passed to {@link CoreException#CoreException(IStatus)}.
   */
  public ProblemException( final IStatus status ) {
    super(status);
  }
  
  /**
   * Get this exception's associated problem if one was specified
   * during creation time or <code>null</code>.
   *  
   * @return The associated problem or <code>null</code>.
   */
  public IProblem getProblem() {
    return this.problem;
  }

}
