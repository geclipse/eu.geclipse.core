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
 *    Ariel Garcia      - reused file from core for the util.tar package
 *****************************************************************************/

package eu.geclipse.core.util.tar;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.IProblem;
import eu.geclipse.core.IProblemProvider;
import eu.geclipse.core.ProblemRegistry;
import eu.geclipse.core.SolutionRegistry;

/**
 * Problem provider implementation for core related
 * problems. 
 */
public class TarProblems implements IProblemProvider {
  
  /**
   * Problem ID for a wrong header size.
   */
  public static final int WRONG_HEADER_SIZE
    = ProblemRegistry.uniqueID();
  
  /**
   * Problem ID for a corrupted header.
   */
  public static final int BAD_HEADER_CHECKSUM
    = ProblemRegistry.uniqueID();
  
  /**
   * Problem ID for a corrupted entry size.
   */
  public static final int INVALID_ENTRY_SIZE
    = ProblemRegistry.uniqueID();
  
  /**
   * Problem ID for an invalid entry type.
   */
  public static final int UNSUPPORTED_ENTRY_TYPE
    = ProblemRegistry.uniqueID();
  
  /**
   * Problem ID for an invalid entry type.
   */
  public static final int INVALID_ENTRY_TYPE
    = ProblemRegistry.uniqueID();
  
  /**
   * Problem ID for unspecified IO problems.
   */
  public static final int UNSPECIFIED_IO_PROBLEM
    = ProblemRegistry.uniqueID();

  /* (non-Javadoc)
   * @see eu.geclipse.core.IProblemProvider#getProblem(int, java.lang.Throwable)
   */
  public IProblem getProblem( final int problemID,
                              final Throwable exc ) {
    
    IProblem problem = null;
    
    if ( problemID == WRONG_HEADER_SIZE ) {
      problem = createProblem( problemID,
                               Messages.getString("TarProblems.wrong_header_size"), //$NON-NLS-1$
                               exc,
                               new int[] {
                                 SolutionRegistry.REPORT_AS_BUG
                               } );
    }
    
    else if ( problemID == BAD_HEADER_CHECKSUM ) {
      problem = createProblem( problemID,
                               Messages.getString("TarProblems.bad_header_checksum"), //$NON-NLS-1$
                               exc,
                               new int[] {
                                 SolutionRegistry.DOWNLOAD_FILE_AGAIN,
                                 SolutionRegistry.DOWNLOAD_FROM_ANOTHER_SOURCE
                               } );
    }
    
    else if ( problemID == INVALID_ENTRY_SIZE ) {
      problem = createProblem( problemID,
                               Messages.getString("TarProblems.invalid_entry_size"), //$NON-NLS-1$
                               exc,
                               new int[] {
                                 SolutionRegistry.DOWNLOAD_FILE_AGAIN,
                                 SolutionRegistry.DOWNLOAD_FROM_ANOTHER_SOURCE
                               } );
    }
    
    else if ( problemID == UNSUPPORTED_ENTRY_TYPE ) {
      problem = createProblem( problemID,
                               Messages.getString("TarProblems.unsupported_entry_type"), //$NON-NLS-1$
                               exc,
                               new int[] {
                                 SolutionRegistry.DONT_USE_THIS_IMPLEMENTATION,
                                 SolutionRegistry.REPORT_AS_BUG
                               } );
    }
    
    else if ( problemID == INVALID_ENTRY_TYPE ) {
      problem = createProblem( problemID,
                               Messages.getString("TarProblems.invalid_entry_type"), //$NON-NLS-1$
                               exc,
                               new int[] {
                                 SolutionRegistry.DOWNLOAD_FILE_AGAIN,
                                 SolutionRegistry.DOWNLOAD_FROM_ANOTHER_SOURCE
                               } );
    }
    
    else if ( problemID == UNSPECIFIED_IO_PROBLEM ) {
      problem = createProblem( problemID,
                               Messages.getString("TarProblems.unspecified_io_problem"), //$NON-NLS-1$
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
   *         plugin's ID.
   */
  private IProblem createProblem( final int id,
                                  final String text,
                                  final Throwable exc,
                                  final int[] solutionIDs ) {
    return ProblemRegistry.createProblem( id, text, exc, solutionIDs, Activator.PLUGIN_ID );
  }

}
