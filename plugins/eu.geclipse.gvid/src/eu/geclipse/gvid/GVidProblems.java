package eu.geclipse.gvid;

import eu.geclipse.core.IProblem;
import eu.geclipse.core.IProblemProvider;
import eu.geclipse.core.ProblemRegistry;

public class GVidProblems implements IProblemProvider {
  
  /**
   * Unique id for the codec instantiation failed problem.
   */
  public static final int CODEC_INSTATIATION_FAILED
    = ProblemRegistry.uniqueID();

  public IProblem getProblem( final int problemID,
                              final Throwable exc ) {
    
    IProblem problem = null;
    
    if ( problemID == CODEC_INSTATIATION_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("GVidProblems.codec_instantiation_failed"), //$NON-NLS-1$
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
