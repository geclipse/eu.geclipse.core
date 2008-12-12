/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.s3;

import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ProblemException;

/**
 * This {@link ProblemException} deals with problems, occurring while
 * interfacing with the AWS S3 Service.
 * 
 * @author Moritz Post
 */
public class S3ProblemException extends ProblemException {

  /**
   * Create a new {@link S3ProblemException} for the problem with the specified
   * ID.
   * 
   * @param problemID The ID of the problem that should be reported.
   * @param description An optional description that may replace the problems
   *          standard description.
   * @param exception An optional {@link Throwable} that may have caused the
   *          problem.
   * @param pluginID The ID of the plug-in where the problem happened.
   * @see #ProblemException(IProblem)
   */
  public S3ProblemException( final String problemID,
                             final String description,
                             final Throwable exception,
                             final String pluginID )
  {
    super( problemID, description, exception, pluginID );
  }

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

}
