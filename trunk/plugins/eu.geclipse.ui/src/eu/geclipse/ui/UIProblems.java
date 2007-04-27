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

package eu.geclipse.ui;

import eu.geclipse.core.IProblem;
import eu.geclipse.core.IProblemProvider;

/**
 * Problem provider of the UI plugin. Defines all general UI related
 * problems.
 */
public class UIProblems implements IProblemProvider {
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.IProblemProvider#getProblem(int, java.lang.Throwable)
   */
  public IProblem getProblem( final int problemID,
                              final Throwable exc ) {

    IProblem problem = null;
    
    // currently empty
    
    return problem;
    
  }
  
  /**
   * Method to internally create a problem for the UI plugin.
   * 
   * @param id The unique ID of the problem.
   * @param text The descriptive text of the problem.
   * @param exc The exception that caused the problem or <code>null</code>.
   * @param solutionIDs The IDs of possible solutions or <code>null</code>.
   * @return The newly constructed problem.
   */
  /*private IProblem createProblem( final int id,
                                  final String text,
                                  final Throwable exc,
                                  final int[] solutionIDs ) {
    return ProblemRegistry.createProblem( id, text, exc, solutionIDs, Activator.PLUGIN_ID );
  }*/
  
}
