package eu.geclipse.core;

import java.util.List;
import eu.geclipse.core.internal.Activator;

public class ProblemRegistry {

  public static final int UNKNOWN_PROBLEM = -1;

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
  
  public static IProblem createProblem( final int id,
                                        final String text,
                                        final Throwable exc,
                                        int[] solutionIDs,
                                        final String pluginID ) {
    IProblem problem = new AbstractProblem( id, text, exc ) {
      @Override
      protected String getPluginID() {
        return pluginID;
      }
    };
    if ( solutionIDs != null ) {
      for ( int solutionID : solutionIDs ) {
        problem.addSolution( solutionID );
      }
    }
    return problem;
  }
  
  public IProblem getProblem( final int problemID,
                              final Throwable exc ) {
    IProblem problem = findProblem( problemID, exc );
    if ( problem == null ) {
      problem = createProblem( UNKNOWN_PROBLEM,
                               "Unknown problem occured (id = " + problemID + ")",
                               exc,
                               null,
                               Activator.PLUGIN_ID );
    }
    return problem;
  }
  
  protected IProblem findProblem( final int problemID,
                                  final Throwable exc ) {
    IProblem problem = null;
    List<IProblemProvider> providers
      = Extensions.getRegisteredProblemProviders();
    for ( IProblemProvider provider : providers ) {
      problem = provider.getProblem( problemID, exc );
      if ( problem != null ) {
        break;
      }
    }
    return problem;
  }
  
  public static synchronized int uniqueID() {
    return ++lastUniqueID;
  }
  
}
