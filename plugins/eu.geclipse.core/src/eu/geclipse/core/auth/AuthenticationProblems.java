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

package eu.geclipse.core.auth;

import eu.geclipse.core.IProblem;
import eu.geclipse.core.IProblemProvider;
import eu.geclipse.core.ProblemRegistry;
import eu.geclipse.core.internal.Activator;

/**
 * Problem provider implementation for all authentication related
 * problem.
 */
public class AuthenticationProblems implements IProblemProvider {
  
  /**
   * Unique ID for the certificate load failed problem.
   */
  public static final int CERTIFICATE_LOAD_FAILED
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the credential creation failed problem.
   */
  public static final int CREDENTIAL_CREATE_FAILED
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the credential save failed problem.
   */
  public static final int CREDENTIAL_SAVE_FAILED
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the ivalid token description problem.
   */
  public static final int INVALID_TOKEN_DESCRIPTION
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the private key load failed problem.
   */
  public static final int KEY_LOAD_FAILED
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the token activation failed problem.
   */
  public static final int TOKEN_ACTIVATE_FAILED
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the token not yet validated problem.
   */
  public static final int TOKEN_NOT_YET_VALID
    = ProblemRegistry.uniqueID();

  /* (non-Javadoc)
   * @see eu.geclipse.core.IProblemProvider#getProblem(int, java.lang.Throwable)
   */
  public IProblem getProblem( final int problemID,
                              final Throwable exc ) {

    IProblem problem = null;
    
    if ( problemID == CERTIFICATE_LOAD_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("AuthenticationProblems.certificate_load_failed"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    else if ( problemID == CREDENTIAL_CREATE_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("AuthenticationProblems.credential_create_failed"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    else if ( problemID == CREDENTIAL_SAVE_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("AuthenticationProblems.credential_save_failed"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    else if ( problemID == INVALID_TOKEN_DESCRIPTION ) {
      problem = createProblem( problemID,
                               Messages.getString("AuthenticationProblems.invalid_token_description"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    else if ( problemID == KEY_LOAD_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("AuthenticationProblems.key_load_failed"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    else if ( problemID == TOKEN_ACTIVATE_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("AuthenticationProblems.token_activate_failed"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    else if ( problemID == TOKEN_NOT_YET_VALID ) {
      problem = createProblem( problemID,
                               Messages.getString("AuthenticationProblems.token_not_valid"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    return problem;
    
  }
  
  /**
   * Create a problem with the specified parameters.
   * 
   * @param id The unique ID of the problem.
   * @param text The problems descriptive text.
   * @param exc The exception that caused the problem or <code>null</code>.
   * @param solutionIDs Solution IDs for the problem or <code>null</code>.
   * @return The newly created problem.
   */
  public IProblem createProblem( final int id,
                                 final String text,
                                 final Throwable exc,
                                 final int[] solutionIDs ) {
    return ProblemRegistry.createProblem( id, text, exc, solutionIDs, Activator.PLUGIN_ID );
  }
  
}
