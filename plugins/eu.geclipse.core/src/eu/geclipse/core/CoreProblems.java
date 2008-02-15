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

package eu.geclipse.core;

import eu.geclipse.core.internal.Activator;

/**
 * Problem provider implementation for core related
 * problems. 
 */
@Deprecated
public class CoreProblems implements IProblemProvider {
  
  /**
   * Problem ID for a failed connection.
   */
  public static final int CONNECTION_FAILED
    = ProblemRegistry.uniqueID();
  
  /**
   * Problem ID for a timed out connection.
   */
  public static final int CONNECTION_TIMEOUT
    = ProblemRegistry.uniqueID();
  
  /**
   * Problem ID for an unknown host.
   */
  public static final int UNKNOWN_HOST
    = ProblemRegistry.uniqueID();
  
  /**
   * Problem ID for malformed URLs.
   */
  public static final int MALFORMED_URL
    = ProblemRegistry.uniqueID();

  /**
   * Problem ID for failed binding of ports.
   */
  public static final int BIND_FAILED
    = ProblemRegistry.uniqueID();

  /**
   * Problem ID for job submission failures.
   */
  public static final int JOB_SUBMISSION_FAILED
    = ProblemRegistry.uniqueID();

  /**
   * Problem ID for unspecified IO problems.
   */
  public static final int UNSPECIFIED_IO_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Problem ID for file access problems.
   */
  public static final int FILE_ACCESS_PROBLEM
    = ProblemRegistry.uniqueID();
 
  /**
   * Problem ID for problem getting the SSH service from OSGI.
   */
  public static final int GET_SSH_SERVICE_FAILED
    = ProblemRegistry.uniqueID();

  /**
   * Problem ID for login failed.
   */
  public static final int LOGIN_FAILED
    = ProblemRegistry.uniqueID();

  /**
   * Problem ID for credential creation errors.
   */
  public static final int ERROR_CREATING_CREDENTIAL
    = ProblemRegistry.uniqueID();

  /**
   * Problem ID for system time check failures.
   */
  public static final int SYSTEM_TIME_CHECK_FAILED
    = ProblemRegistry.uniqueID();

  /* (non-Javadoc)
   * @see eu.geclipse.core.IProblemProvider#getProblem(int, java.lang.Throwable)
   */
  public IProblem getProblem( final int problemID,
                              final Throwable exc ) {
    
    IProblem problem = null;
    
    if ( problemID == CONNECTION_TIMEOUT ) {
      problem = createProblem( problemID,
                               Messages.getString("CoreProblems.connection_timeout"), //$NON-NLS-1$
                               exc,
                               new int[] {
                                 SolutionRegistry.SERVER_DOWN,
                                 SolutionRegistry.CHECK_TIMEOUT_SETTINGS
                               } );
    }
    
    else if ( problemID == CONNECTION_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("CoreProblems.connection_failed"), //$NON-NLS-1$
                               exc,
                               new int[] {
                                 SolutionRegistry.CHECK_INTERNET_CONNECTION,
                                 SolutionRegistry.CHECK_SERVER_URL,
                                 SolutionRegistry.CHECK_PROXY_SETTINGS
                               } );
    }
    
    else if ( problemID == UNKNOWN_HOST ) {
      problem = createProblem( problemID,
                               Messages.getString("CoreProblems.unknown_host"), //$NON-NLS-1$
                               exc,
                               new int[] {
                                 SolutionRegistry.CHECK_INTERNET_CONNECTION,
                                 SolutionRegistry.CHECK_SERVER_URL
                               } );
    }
    
    else if ( problemID == UNSPECIFIED_IO_PROBLEM ) {
      problem = createProblem( problemID,
                               Messages.getString("CoreProblems.unspecified_io"), //$NON-NLS-1$
                               exc,
                               null );
    }
    else if ( problemID == FILE_ACCESS_PROBLEM ) {
      problem = createProblem( problemID,
                               Messages.getString("CoreProblems.file_access"), //$NON-NLS-1$
                               exc,
                               null );
    }
    else if ( problemID == BIND_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("CoreProblems.bindFailed"), //$NON-NLS-1$
                               exc,
                               new int[] {
                                 SolutionRegistry.CHECK_PORT_ALREADY_IN_USE,
                                 SolutionRegistry.USE_ANOTHER_PORT
                               } );
    }

    else if ( problemID == JOB_SUBMISSION_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("CoreProblems.job_submission_failed"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    else if ( problemID == MALFORMED_URL ) {
      problem = createProblem( problemID,
                               Messages.getString("CoreProblems.malformed_url"), //$NON-NLS-1$
                               exc,
                               new int[] {
                                 SolutionRegistry.CHECK_SERVER_URL
                               } );
    }

    else if ( problemID == GET_SSH_SERVICE_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("CoreProblems.get_ssh_service_failed"), //$NON-NLS-1$
                               exc,
                               new int[] {
                                 SolutionRegistry.CHECK_INSTALLATION
                               } );
    }

    else if ( problemID == LOGIN_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("CoreProblems.login_failed"), //$NON-NLS-1$
                               exc,
                               null );
    }

    else if ( problemID == ERROR_CREATING_CREDENTIAL ) {
      problem = createProblem( problemID,
                               Messages.getString("CoreProblems.errorCreatingCredential"), //$NON-NLS-1$
                               exc,
                               null );
    }

    else if ( problemID == SYSTEM_TIME_CHECK_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("CoreProblems.system_time_check_failed"), //$NON-NLS-1$
                               exc,
                               null );
    }

    return problem;
    
  }
  
  /**
   * Internal method for creating problems from the specified parameters.
   * 
   * @param id The ID of the problem.
   * @param text The text of the problem.
   * @param exc The exception that caused the problem.
   * @param solutionIDs Solution IDs that are associated with this problem.
   * @return The newly created problem. This problem will have the core
   * plugin's ID.
   */
  private IProblem createProblem( final int id,
                                  final String text,
                                  final Throwable exc,
                                  final int[] solutionIDs ) {
    return ProblemRegistry.createProblem( id, text, exc, solutionIDs, Activator.PLUGIN_ID );
  }

}
