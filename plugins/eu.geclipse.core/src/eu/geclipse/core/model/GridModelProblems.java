package eu.geclipse.core.model;

import eu.geclipse.core.IProblem;
import eu.geclipse.core.IProblemProvider;
import eu.geclipse.core.ProblemRegistry;
import eu.geclipse.core.internal.Activator;


public class GridModelProblems implements IProblemProvider {
  
  public static final int CONTAINER_CAN_NOT_CONTAIN
    = ProblemRegistry.uniqueID();
  
  public static final int ELEMENT_CREATE_FAILED
    = ProblemRegistry.uniqueID();
  
  public static final int ELEMENT_LOAD_FAILED
    = ProblemRegistry.uniqueID();
  
  public static final int ELEMENT_NOT_MANAGEABLE
    = ProblemRegistry.uniqueID();
  
  public static final int ELEMENT_SAVE_FAILED
    = ProblemRegistry.uniqueID();
  
  public static final int JOB_SUBMIT_FAILED
    = ProblemRegistry.uniqueID();
  
  public static final int REFRESH_FAILED
    = ProblemRegistry.uniqueID();

  public IProblem getProblem( final int problemID,
                              final Throwable exc ) {
    
    IProblem problem = null;
    
    if ( problemID == CONTAINER_CAN_NOT_CONTAIN ) {
      problem = createProblem( problemID,
                               "Container can not contain elements of the specified type",
                               exc,
                               null );
    }
    
    else if ( problemID == ELEMENT_CREATE_FAILED ) {
      problem = createProblem( problemID,
                               "Failed to create grid element",
                               exc,
                               null );
    }
    
    else if ( problemID == ELEMENT_LOAD_FAILED ) {
      problem = createProblem( problemID,
                               "Failed to load grid element",
                               exc,
                               null );
    }
    
    else if ( problemID == ELEMENT_SAVE_FAILED ) {
      problem = createProblem( problemID,
                               "Failed to save grid element",
                               exc,
                               null );
    }
    
    else if ( problemID == JOB_SUBMIT_FAILED ) {
      problem = createProblem( problemID,
                               "Failed to submit job",
                               exc,
                               null );
    }
    
    else if ( problemID == REFRESH_FAILED ) {
      problem = createProblem( problemID,
                               "Failed to refresh container",
                               exc,
                               null );
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
