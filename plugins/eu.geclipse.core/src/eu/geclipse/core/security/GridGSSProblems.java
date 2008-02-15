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

package eu.geclipse.core.security;

import org.ietf.jgss.GSSException;
import eu.geclipse.core.IProblem;
import eu.geclipse.core.IProblemProvider;
import eu.geclipse.core.ProblemRegistry;
import eu.geclipse.core.internal.Activator;

/**
 * Problem provider that maps GSS error codes to g-Eclipse problems.
 */
@Deprecated
public class GridGSSProblems implements IProblemProvider {
  
  /**
   * Unique ID for the problem according to {@link GSSException#BAD_BINDINGS}.  
   */
  public static final int BAD_BINDINGS_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#BAD_MECH}.  
   */
  public static final int BAD_MECH_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#BAD_MIC}.  
   */
  public static final int BAD_MIC_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#BAD_NAME}.  
   */
  public static final int BAD_NAME_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#BAD_NAMETYPE}.  
   */
  public static final int BAD_NAMETYPE_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#BAD_QOP}.  
   */
  public static final int BAD_QOP_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#BAD_STATUS}.  
   */
  public static final int BAD_STATUS_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#CONTEXT_EXPIRED}.  
   */
  public static final int CONTEXT_EXPIRED_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#CREDENTIALS_EXPIRED}.  
   */
  public static final int CREDENTIALS_EXPIRED_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#DEFECTIVE_CREDENTIAL}.  
   */
  public static final int DEFECTIVE_CREDENTIAL_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#DEFECTIVE_TOKEN}.  
   */
  public static final int DEFECTIVE_TOKEN_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#DUPLICATE_ELEMENT}.  
   */
  public static final int DUPLICATE_ELEMENT_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#DUPLICATE_TOKEN}.  
   */
  public static final int DUPLICATE_TOKEN_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#FAILURE}.  
   */
  public static final int FAILURE_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#GAP_TOKEN}.  
   */
  public static final int GAP_TOKEN_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#NAME_NOT_MN}.  
   */
  public static final int NAME_NOT_MN_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#NO_CONTEXT}.  
   */
  public static final int NO_CONTEXT_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#NO_CRED}.  
   */
  public static final int NO_CRED_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#OLD_TOKEN}.  
   */
  public static final int OLD_TOKEN_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#UNAUTHORIZED}.  
   */
  public static final int UNAUTHORIZED_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#UNAVAILABLE}.  
   */
  public static final int UNAVAILABLE_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the problem according to {@link GSSException#UNSEQ_TOKEN}.  
   */
  public static final int UNSEQ_TOKEN_GSS_PROBLEM
    = ProblemRegistry.uniqueID();
   
  /* (non-Javadoc)
   * @see eu.geclipse.core.IProblemProvider#getProblem(int, java.lang.Throwable)
   */
  public IProblem getProblem( final int problemID,
                              final Throwable exc ) {
    IProblem problem = null;
    if ( exc instanceof GSSException ) {
      problem = getProblem( problemID, ( GSSException ) exc );
    }
    return problem;
  }
  
  /**
   * Get the problem ID for the specified exception.
   * 
   * @param exc The {@link GSSException} for which to get a problem ID.
   * @return The corresponding problem ID or
   * {@link ProblemRegistry#UNKNOWN_PROBLEM} if no associated problem
   * could be found.
   */
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
  
  /**
   * Create a problem for the specified ID.
   * 
   * @param problemID The problem's unique ID.
   * @param exc The exception that caused this problem.
   * @return The new problem.
   */
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
  
  /**
   * Get solutions for the specified problem ID.
   * 
   * @param problemID The unique problem ID for which to get solutions.
   * @return A list if solutions or <code>null</code>.
   */
  private int[] getSolutions( @SuppressWarnings("unused")
                              final int problemID ) {
    int[] solutions = null;
    return solutions;
  }
  
}
