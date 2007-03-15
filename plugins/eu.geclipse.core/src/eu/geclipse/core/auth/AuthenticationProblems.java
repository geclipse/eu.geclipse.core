package eu.geclipse.core.auth;

import eu.geclipse.core.IProblem;
import eu.geclipse.core.IProblemProvider;
import eu.geclipse.core.ProblemRegistry;
import eu.geclipse.core.internal.Activator;

public class AuthenticationProblems implements IProblemProvider {
  
  public static final int CERTIFICATE_LOAD_FAILED
    = ProblemRegistry.uniqueID();
  
  public static final int CREDENTIAL_CREATE_FAILED
    = ProblemRegistry.uniqueID();
  
  public static final int CREDENTIAL_SAVE_FAILED
    = ProblemRegistry.uniqueID();
  
  public static final int INVALID_TOKEN_DESCRIPTION
    = ProblemRegistry.uniqueID();
  
  public static final int KEY_LOAD_FAILED
    = ProblemRegistry.uniqueID();
  
  public static final int TOKEN_ACTIVATE_FAILED
    = ProblemRegistry.uniqueID();
  
  public static final int TOKEN_NOT_YET_VALID
    = ProblemRegistry.uniqueID();

  public IProblem getProblem( final int problemID,
                              final Throwable exc ) {

    IProblem problem = null;
    
    if ( problemID == CERTIFICATE_LOAD_FAILED ) {
      problem = createProblem( problemID,
                               "Unable to load certificate",
                               exc,
                               null );
    }
    
    else if ( problemID == CREDENTIAL_CREATE_FAILED ) {
      problem = createProblem( problemID,
                               "Unable to create credential",
                               exc,
                               null );
    }
    
    else if ( problemID == CREDENTIAL_SAVE_FAILED ) {
      problem = createProblem( problemID,
                               "Unable to save credential",
                               exc,
                               null );
    }
    
    else if ( problemID == INVALID_TOKEN_DESCRIPTION ) {
      problem = createProblem( problemID,
                               "Invalid token description",
                               exc,
                               null );
    }
    
    else if ( problemID == KEY_LOAD_FAILED ) {
      problem = createProblem( problemID,
                               "Unable to load key file",
                               exc,
                               null );
    }
    
    else if ( problemID == TOKEN_ACTIVATE_FAILED ) {
      problem = createProblem( problemID,
                               "Unable to activate token",
                               exc,
                               null );
    }
    
    else if ( problemID == TOKEN_NOT_YET_VALID ) {
      problem = createProblem( problemID,
                               "Authentication token was not yet validated",
                               exc,
                               null );
    }
    
    return problem;
    
  }
  
  public IProblem createProblem( final int id,
                                 final String text,
                                 final Throwable exc,
                                 final int[] solutionIDs ) {
    return ProblemRegistry.createProblem( id, text, exc, solutionIDs, Activator.PLUGIN_ID );
  }
  
}
