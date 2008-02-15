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

package eu.geclipse.core;

import java.util.List;
import eu.geclipse.core.internal.Activator;

/**
 * This is the base problem registry that is always used if no other
 * problem registry is specified.
 * 
 * A problem registry is used to map problem IDs to problem implementations.
 * Therefore if asked for a problem with a specific ID it asks all registered
 * problem providers if they are able to create such a problem. In that sense
 * it just delegates the central problem request to the decentralised problem
 * providers. If no problem could be found for the specified ID a default problem
 * is generated that denotes an unknown problem.
 */
@Deprecated
public class ProblemRegistry {

  /**
   * ID for an unknown problem, i.e. a problem ID for which no problem could
   * be found.
   */
  public static final int UNKNOWN_PROBLEM = -1;

  /**
   * Internal field to track the last used uniqueID.
   */
  private static int lastUniqueID = 0;
  
  /**
   * The singleton instance.
   */
  private static ProblemRegistry singleton;
  
  /**
   * Private constructor.
   */
  private ProblemRegistry() {
    // empty implementation
  }
  
  /**
   * Static access method to the singleton instance of the problem registry.
   * 
   * @return The singleton instance of the problem registry.
   */
  public static ProblemRegistry getRegistry() {
    if ( singleton == null ) {
      singleton = new ProblemRegistry();
    }
    return singleton;
  }
  
  /**
   * Create a new problem with the specified parameters. Only problem
   * providers should use this method to create their problems.
   *  
   * @param id The unique ID of the problem. In order to be sure that
   * the ID is really unique one should make use of {@link #uniqueID()}.
   * @param text A descriptive text that explains the problem.
   * @param exc An exception that may have caused this problem. May be
   * <code>null</code>.
   * @param solutionIDs Unique IDs of the solutions of the new problem.
   * @param pluginID The plugin ID of the plugin where this problem
   * is defined. 
   * @return The newly created problem.
   */
  public static IProblem createProblem( final int id,
                                        final String text,
                                        final Throwable exc,
                                        final int[] solutionIDs,
                                        final String pluginID ) {
    IProblem problem = new AbstractProblem( id, text, exc ) {
      @Override
      protected String getPluginID() {
        return pluginID;
      }
    };
    if ( solutionIDs != null ) {
      for ( int solutionID : solutionIDs ) {
        problem.addSolution( solutionID );
      }
    }
    return problem;
  }
  
  /**
   * Get the problem with the specified ID and apply the specified
   * exception and description to it.
   * 
   * @param problemID The unique ID of the problem.
   * @param exc The exception that caused the problem or <code>null</code>.
   * @param description A descriptive text that gives a more detailed
   * description of the problem or <code>null</code>.
   * @return The problem or a standard problem if the specified ID could
   * not be mapped to an existing problem.
   */
  public IProblem getProblem( final int problemID,
                              final Throwable exc, 
                              final String description ) {
    IProblem problem = getProblem(problemID, exc);
    if ( description != null ) {
      problem.addReason( description );
    }
    return problem;
    
  }
  
  /**
   * Get the problem with the specified ID and apply the specified
   * exception to it.
   * 
   * @param problemID The unique ID of the problem.
   * @param exc The exception that caused the problem or <code>null</code>.
   * @return The problem or a standard problem if the specified ID could
   * not be mapped to an existing problem.
   */
  public IProblem getProblem( final int problemID,
                              final Throwable exc ) {
    IProblem problem = findProblem( problemID, exc );
    if ( problem == null ) {
      problem = createProblem( UNKNOWN_PROBLEM,
                               String.format( Messages.getString("ProblemRegistry.unknown_problem"), //$NON-NLS-1$
                                              Integer.valueOf( problemID ) ),
                               exc,
                               null,
                               Activator.PLUGIN_ID );
    }
    return problem;
  }
  
  /**
   * Ask the registered {@link IProblemProvider}s if they are able to map the
   * specified problem ID to a concrete problem and return this problem. If
   * no associated problem could be found <code>null</code> is returned.
   * 
   * @param problemID The unique ID of the problem.
   * @param exc The exception that has caused the problem or <code>null</code>.
   * @return A concrete problem implementation or <code>null</code> if the
   * problem ID could not be mapped.
   */
  protected IProblem findProblem( final int problemID,
                                  final Throwable exc ) {
    IProblem problem = null;
    List<IProblemProvider> providers
      = Extensions.getRegisteredProblemProviders();
    for ( IProblemProvider provider : providers ) {
      problem = provider.getProblem( problemID, exc );
      if ( problem != null ) {
        break;
      }
    }
    return problem;
  }
  
  /**
   * Get a unique ID for a problem. The returned ID is unique during runtime but
   * may change between different sessions.
   * 
   * @return A runtime unique ID.
   */
  public static synchronized int uniqueID() {
    return ++lastUniqueID;
  }
  
}
