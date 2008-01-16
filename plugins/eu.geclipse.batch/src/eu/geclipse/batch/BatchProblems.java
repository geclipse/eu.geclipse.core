/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch;

import eu.geclipse.batch.internal.Messages;
import eu.geclipse.core.IProblem;
import eu.geclipse.core.IProblemProvider;
import eu.geclipse.core.ProblemRegistry;
//import eu.geclipse.core.internal.Activator;
import eu.geclipse.batch.internal.Activator;

/**
 * Problem provider implementation for all batch service related
 * problem.
 */
public class BatchProblems implements IProblemProvider {
  
  /**
   * Unique ID for remote host connection problem.
   */
  public static final int CONNECTION_FAILED
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for io problem with the connection to the remote host.
   */
  public static final int CONNECTION_IO_ERROR
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for a failed executed command.
   */
  public static final int COMMAND_FAILED
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for no authorization to execute the command.
   */
  public static final int NO_AUTHORIZATION
    = ProblemRegistry.uniqueID();
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.IProblemProvider#getProblem(int, java.lang.Throwable)
   */
  public IProblem getProblem( final int problemID,
                              final Throwable exc ) {

    IProblem problem = null;
    
    if ( problemID == CONNECTION_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("BatchProblem.connection_failed"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    else if ( problemID == CONNECTION_IO_ERROR ) {
      problem = createProblem( problemID,
                               Messages.getString("BatchProblem.connection_io_error"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    else if ( problemID == COMMAND_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("BatchProblem.command_failed"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    else if ( problemID == NO_AUTHORIZATION ) {
      problem = createProblem( problemID,
                               Messages.getString("BatchProblem.no_authorization"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    return problem;
    
  }
  
  /**
   * Create a problem with the specified parameters.
   * 
   * @param id The unique ID of the problem.
   * @param text The problems descriptive text.
   * @param exc The exception that caused the problem or <code>null</code>.
   * @param solutionIDs Solution IDs for the problem or <code>null</code>.
   * @return The newly created problem.
   */
  public IProblem createProblem( final int id,
                                 final String text,
                                 final Throwable exc,
                                 final int[] solutionIDs ) {
    return ProblemRegistry.createProblem( id, text, exc, solutionIDs, Activator.PLUGIN_ID );
  }
  
}
