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

package eu.geclipse.core.model;

import eu.geclipse.core.IProblem;
import eu.geclipse.core.IProblemProvider;
import eu.geclipse.core.ProblemRegistry;
import eu.geclipse.core.internal.Activator;

/**
 * Problem provider implementation for Grid model related problems.
 */
public class GridModelProblems implements IProblemProvider {
  
  /**
   * Unique ID for the container can not contain element of a specific type problem.
   */
  public static final int CONTAINER_CAN_NOT_CONTAIN
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the element creation failed problem.
   */
  public static final int ELEMENT_CREATE_FAILED
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the element deletion failed problem.
   */
  public static final int ELEMENT_DELETE_FAILED
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the element load failed problem.
   */
  public static final int ELEMENT_LOAD_FAILED
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the manager cannot manage a specified element problem.
   */
  public static final int ELEMENT_NOT_MANAGEABLE
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the element save failed problem.
   */
  public static final int ELEMENT_SAVE_FAILED
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the job submission failed problem.
   */
  public static final int JOB_SUBMIT_FAILED
    = ProblemRegistry.uniqueID();
  
  /**
   * Unique ID for the refresh failed problem.
   */
  public static final int REFRESH_FAILED
    = ProblemRegistry.uniqueID();

  /* (non-Javadoc)
   * @see eu.geclipse.core.IProblemProvider#getProblem(int, java.lang.Throwable)
   */
  public IProblem getProblem( final int problemID,
                              final Throwable exc ) {
    
    IProblem problem = null;
    
    if ( problemID == CONTAINER_CAN_NOT_CONTAIN ) {
      problem = createProblem( problemID,
                               Messages.getString("GridModelProblems.container_can_not_contain"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    else if ( problemID == ELEMENT_CREATE_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("GridModelProblems.element_create_failed"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    else if ( problemID == ELEMENT_DELETE_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("GridModelProblems.element_delete_failed"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    else if ( problemID == ELEMENT_LOAD_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("GridModelProblems.element_load_failed"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    else if ( problemID == ELEMENT_SAVE_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("GridModelProblems.element_save_failed"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    else if ( problemID == JOB_SUBMIT_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("GridModelProblems.submit_job_failed"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    else if ( problemID == REFRESH_FAILED ) {
      problem = createProblem( problemID,
                               Messages.getString("GridModelProblems.refresh_failed"), //$NON-NLS-1$
                               exc,
                               null );
    }
    
    return problem;
    
  }
  
  /**
   * Create a problem from the specified parameters.
   * 
   * @param id The unique ID of the problem.
   * @param text The problem's descriptive text.
   * @param exc The exception that has caused the problem or <code>null</code>.
   * @param solutionIDs Solution IDs for this problem or <code>null</code>.
   * @return The newly created problem.
   */
  private IProblem createProblem( final int id,
                                  final String text,
                                  final Throwable exc,
                                  final int[] solutionIDs ) {
    return ProblemRegistry.createProblem( id, text, exc, solutionIDs, Activator.PLUGIN_ID );
  }
  
}
