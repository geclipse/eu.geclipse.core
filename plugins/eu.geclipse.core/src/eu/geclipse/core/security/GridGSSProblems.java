package eu.geclipse.core.security;

import org.ietf.jgss.GSSException;
import eu.geclipse.core.IProblem;
import eu.geclipse.core.IProblemProvider;
import eu.geclipse.core.ProblemRegistry;
import eu.geclipse.core.internal.Activator;

public class GridGSSProblems implements IProblemProvider {
  
  public static final int BAD_BINDINGS_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int BAD_MECH_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int BAD_MIC_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int BAD_NAME_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int BAD_NAMETYPE_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int BAD_QOP_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int BAD_STATUS_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int CONTEXT_EXPIRED_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int CREDENTIALS_EXPIRED_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int DEFECTIVE_CREDENTIAL_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int DEFECTIVE_TOKEN_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int DUPLICATE_ELEMENT_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int DUPLICATE_TOKEN_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int FAILURE_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int GAP_TOKEN_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int NAME_NOT_MN_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int NO_CONTEXT_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int NO_CRED_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int OLD_TOKEN_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int UNAUTHORIZED_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int UNAVAILABLE_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  public static final int UNSEQ_TOKEN_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
   
  public IProblem getProblem( final int problemID,
                              final Throwable exc ) {
    IProblem problem = null;
    if ( exc instanceof GSSException ) {
      problem = getProblem( problemID, ( GSSException ) exc );
    }
    return null;
  }
  
  public static int getProblemID( final GSSException exc ) {
    
    int id = ProblemRegistry.UNKNOWN_PROBLEM;
    int code = exc.getMajor();
    
    switch ( code ) {
      case GSSException.BAD_BINDINGS:
        id = BAD_BINDINGS_GSS_PROBLEM;
        break;
      case GSSException.BAD_MECH:
        id = BAD_MECH_GSS_PROBLEM;
        break;
      case GSSException.BAD_MIC:
        id = BAD_MIC_GSS_PROBLEM;
        break;
      case GSSException.BAD_NAME:
        id = BAD_NAME_GSS_PROBLEM;
        break;
      case GSSException.BAD_NAMETYPE:
        id = BAD_NAMETYPE_GSS_PROBLEM;
        break;
      case GSSException.BAD_QOP:
        id = BAD_QOP_GSS_PROBLEM;
        break;
      case GSSException.BAD_STATUS:
        id = BAD_STATUS_GSS_PROBLEM;
        break;
      case GSSException.CONTEXT_EXPIRED:
        id = CONTEXT_EXPIRED_GSS_PROBLEM;
        break;
      case GSSException.CREDENTIALS_EXPIRED:
        id = CREDENTIALS_EXPIRED_GSS_PROBLEM;
        break;
      case GSSException.DEFECTIVE_CREDENTIAL:
        id = DEFECTIVE_CREDENTIAL_GSS_PROBLEM;
        break;
      case GSSException.DEFECTIVE_TOKEN:
        id = DEFECTIVE_TOKEN_GSS_PROBLEM;
        break;
      case GSSException.DUPLICATE_ELEMENT:
        id = DUPLICATE_ELEMENT_GSS_PROBLEM;
        break;
      case GSSException.DUPLICATE_TOKEN:
        id = DUPLICATE_TOKEN_GSS_PROBLEM;
        break;
      case GSSException.FAILURE:
        id = FAILURE_GSS_PROBLEM;
        break;
      case GSSException.GAP_TOKEN:
        id = GAP_TOKEN_GSS_PROBLEM;
        break;
      case GSSException.NAME_NOT_MN:
        id = NAME_NOT_MN_GSS_PROBLEM;
        break;
      case GSSException.NO_CONTEXT:
        id = NO_CONTEXT_GSS_PROBLEM;
        break;
      case GSSException.NO_CRED:
        id = NO_CRED_GSS_PROBLEM;
        break;
      case GSSException.OLD_TOKEN:
        id = OLD_TOKEN_GSS_PROBLEM;
        break;
      case GSSException.UNAUTHORIZED:
        id = UNAUTHORIZED_GSS_PROBLEM;
        break;
      case GSSException.UNAVAILABLE:
        id = UNAVAILABLE_GSS_PROBLEM;
        break;
      case GSSException.UNSEQ_TOKEN:
        id = UNSEQ_TOKEN_GSS_PROBLEM;
        break;
    }
    
    return id;
    
  }
  
  private IProblem getProblem( final int problemID,
                               final GSSException exc ) {
    String majorString = exc.getMajorString();
    String minorString = exc.getMinorString();
    String msg = majorString + " (" + minorString + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    int[] solutionIDs = getSolutions( problemID );
    IProblem problem = ProblemRegistry.createProblem( problemID,
                                                      msg,
                                                      exc,
                                                      solutionIDs,
                                                      Activator.PLUGIN_ID );
    return problem;
  }
  
  private int[] getSolutions( final int problemID ) {
    int[] solutions = null;
    return solutions;
  }
  
}
