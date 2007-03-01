package eu.geclipse.core;

import eu.geclipse.core.internal.CoreProblem;

public class ProblemRegistry {
  
  public static final int CONNECTION_FAILED = uniqueID();
  
  public static final int CONNECTION_TIMEOUT = uniqueID();

  private static int lastUniqueID = 0;
  
  private static ProblemRegistry singleton;
  
  private ProblemRegistry() {
    // empty implementation
  }
  
  public static ProblemRegistry getRegistry() {
    if ( singleton == null ) {
      singleton = new ProblemRegistry();
    }
    return singleton;
  }
  
  public IProblem getProblem( final int problemID,
                              final Throwable exc ) {
    IProblem problem = findProblem( problemID, exc );
    if ( problem == null ) {
      problem = new CoreProblem( -1, "Unknown problem occured (id = " + problemID + ")" );
    }
    return problem;
  }
  
  protected IProblem findProblem( final int problemID,
                                  final Throwable exc ) {
    
    IProblem problem = null;
    SolutionRegistry sReg = SolutionRegistry.getRegistry();
    
    if ( problemID == CONNECTION_TIMEOUT ) {
      problem = new CoreProblem( CONNECTION_TIMEOUT,
                                 "A timeout has occurred on a socket read or accept",
                                 exc );
      problem.addSolution( SolutionRegistry.SERVER_DOWN );
      problem.addSolution( SolutionRegistry.CHECK_TIMEOUT_SETTINGS );
    }
    
    else if ( problemID == CONNECTION_FAILED ) {
      problem = new CoreProblem( CONNECTION_FAILED,
                                 "Unable to establish connection",
                                 exc );
      problem.addSolution( SolutionRegistry.CHECK_INTERNET_CONNECTION );
      problem.addSolution( SolutionRegistry.CHECK_SERVER_URL );
      problem.addSolution( SolutionRegistry.CHECK_PROXY_SETTINGS );
    }
    
    return problem;
    
  }
  
  private static int uniqueID() {
    return ++lastUniqueID;
  }
  
}
