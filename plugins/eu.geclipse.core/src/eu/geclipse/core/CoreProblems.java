package eu.geclipse.core;

import eu.geclipse.core.internal.Activator;

public class CoreProblems implements IProblemProvider {
  
  public static final int CONNECTION_FAILED
    = ProblemRegistry.uniqueID();
  
  public static final int CONNECTION_TIMEOUT
    = ProblemRegistry.uniqueID();
  
  public static final int MALFORMED_URL
  = ProblemRegistry.uniqueID();

  public static final int JOB_SUBMISSION_FAILED
  = ProblemRegistry.uniqueID();

  public static final int UNSPECIFIED_IO_PROBLEM
    = ProblemRegistry.uniqueID();

  public IProblem getProblem( final int problemID,
                              final Throwable exc ) {
    
    IProblem problem = null;
    
    if ( problemID == CONNECTION_TIMEOUT ) {
      problem = createProblem( problemID,
                               "A timeout has occurred on a socket read or accept",
                               exc,
                               new int[] {
                                 SolutionRegistry.SERVER_DOWN,
                                 SolutionRegistry.CHECK_TIMEOUT_SETTINGS
                               } );
    }
    
    else if ( problemID == CONNECTION_FAILED ) {
      problem = createProblem( problemID,
                               "Unable to establish connection",
                               exc,
                               new int[] {
                                 SolutionRegistry.CHECK_INTERNET_CONNECTION,
                                 SolutionRegistry.CHECK_SERVER_URL,
                                 SolutionRegistry.CHECK_PROXY_SETTINGS
                               } );
    }
    
    else if ( problemID == UNSPECIFIED_IO_PROBLEM ) {
      problem = createProblem( problemID,
                               "Unspecified IO problem",
                               exc,
                               null );
    }

    else if ( problemID == JOB_SUBMISSION_FAILED ) {
      problem = createProblem( problemID,
                               "Job Submission failed",
                               exc,
                               null );
    }
    
    else if ( problemID == MALFORMED_URL ) {
      problem = createProblem( problemID,
                               "URL is not correct",
                               exc,
                               new int[] {
                               SolutionRegistry.CHECK_SERVER_URL,
      } );
    }
    
    return problem;
    
  }
  
  private IProblem createProblem( final int id,
                                  final String text,
                                  final Throwable exc,
                                  final int[] solutionIDs ) {
    return ProblemRegistry.createProblem( id, text, exc, solutionIDs, Activator.PLUGIN_ID );
  }

}
