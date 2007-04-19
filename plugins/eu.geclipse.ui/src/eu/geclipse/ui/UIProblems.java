package eu.geclipse.ui;

import eu.geclipse.core.IProblem;
import eu.geclipse.core.IProblemProvider;
import eu.geclipse.core.ProblemRegistry;
import eu.geclipse.ui.internal.Activator;


public class UIProblems implements IProblemProvider {
  
  public static final int ELEMENT_TRANSFER_FAILED
    = ProblemRegistry.uniqueID();

  public IProblem getProblem( final int problemID,
                              final Throwable exc ) {

    IProblem problem = null;
    
    if ( problemID == ELEMENT_TRANSFER_FAILED ) {
      problem = createProblem( problemID,
                               "The transfer of at least one element failed",
                               exc,
                               null );
    }
    
    else if ( problemID == ProblemRegistry.UNKNOWN_PROBLEM ) {
      problem = createProblem( ProblemRegistry.UNKNOWN_PROBLEM,
                               "Unknown problem occured (id = " + problemID + ")",
                               exc,
                               new int[] {
                                 UISolutionRegistry.LOG_EXCEPTION        
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
