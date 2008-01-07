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

package eu.geclipse.core.reporting.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import eu.geclipse.core.reporting.IConfigurableSolver;
import eu.geclipse.core.reporting.ISolver;

/**
 * The {@link SolutionFactory} is responsible for loading
 * solutions from the specified extensions of the
 * eu.geclipse.reporting.problemReporting extension point.
 */
public class SolutionFactory {
  
  /**
   * The singleton instance.
   */
  private static SolutionFactory singleton;
  
  /**
   * The problem reporting extension point. 
   */
  private IExtensionPoint problemReporting;
  
  /**
   * Private constructor.
   */
  private SolutionFactory() {
    IExtensionRegistry registry = Platform.getExtensionRegistry();
    this.problemReporting = registry.getExtensionPoint( IProblemReporting.EXTENSION_POINT );
  }
  
  /**
   * Get the singleton instance.
   * 
   * @return The factory's singleton instance.
   */
  public static SolutionFactory getFactory() {
    if ( singleton == null ) {
      singleton = new SolutionFactory();
    }
    return singleton;
  }
  
  /**
   * Get the solution with the specified ID and set the
   * specified description. If no solution with the 
   * specified ID could be found <code>null</code>
   * will be returned.
   *   
   * @param solutionID The ID of the solution.
   * @param description The solution's description or
   * <code>null</code> if the solution's default description
   * should be taken.
   * @return The solution with the specified ID or
   * <code>null</code> if no solution with this ID could
   * be found.
   */
  public Solution getSolution( final String solutionID,
                               final String description ) {
    
    Solution result = null;
    
    IConfigurationElement element = getSolutionElement( solutionID );
    
    if ( element != null ) {
      result = createSolution( element, description );
    }
    
    return result;
    
  }
  
  /**
   * Create a new solution from the specified configuration
   * element and set the description if not <code>null</code>.
   * 
   * @param element The configuration element from which to
   * create a solution.
   * @param description The description that should be set or
   * <code>null</code> if the default description should be
   * taken.
   * @return The newly created solution.
   */
  private Solution createSolution( final IConfigurationElement element,
                                   final String description ) {
    
    String id = element.getAttribute( IProblemReporting.SOLUTION_ID_ATTRIBUTE );
    
    String desc = description;
    if ( desc == null ) {
      desc = element.getAttribute( IProblemReporting.SOLUTION_DESCRIPTION_ATTRIBUTE ); 
    }
    
    ISolver solver = getSolver( element );
    
    return new Solution( id, desc, solver );
    
  }
  
  /**
   * Get the configuration element of the solution with the
   * specified ID.
   * 
   * @param solutionID The solution's ID.
   * @return The configuration element for the solution with
   * the specified ID or <code>null</code> if no such element
   * could be found. 
   */
  private IConfigurationElement getSolutionElement( final String solutionID ) {
    
    IExtension[] extensions = this.problemReporting.getExtensions();
    for ( IExtension extension : extensions ) {
      IConfigurationElement[] elements = extension.getConfigurationElements();
      for ( IConfigurationElement element : elements ) {
        String name = element.getName();
        if ( IProblemReporting.SOLUTION_ELEMENT.equals( name ) ) {
          String id = element.getAttribute( IProblemReporting.SOLUTION_ID_ATTRIBUTE );
          if ( solutionID.equals( id ) ) {
            return element;
          }
        }
      }
    }
    
    return null;
    
  }
  
  /**
   * Extract and create the solver from the specified configuration
   * element. This takes both into account dedicated solvers and
   * predefined solvers.
   * 
   * @param element The solution element from which to extract
   * the solver.
   * @return The newly created solver or <code>null</code> if no
   * solver is specified or could be created.
   */
  private ISolver getSolver( final IConfigurationElement element ) {
    
    ISolver result = null;
    
    try {
      result = ( ISolver ) element.createExecutableExtension( IProblemReporting.SOLUTION_SOLVER_ATTRIBUTE );
    } catch ( CoreException cExc ) {
      IConfigurationElement[] children = element.getChildren();
      for ( IConfigurationElement child : children ) {
        result = getPredefinedSolver( child );
        if ( result != null ) {
          break;
        }
      }
    }
    
    return result;
    
  }
  
  private ISolver getPredefinedSolver( final IConfigurationElement element ) {
    
    ISolver result = null;
    
    String name = element.getName();
    
    if ( IProblemReporting.PREFERENCE_TAG_ELEMENT.equals( name ) ) {
      result = SolutionFactory.getFactory().getSolution( name, null );
    } else if ( IProblemReporting.VIEW_TAG_ELEMENT.equals( name ) ) {
      result = SolutionFactory.getFactory().getSolution( name, null );
    }
    
    if ( result instanceof IConfigurableSolver ) {
      try {
        ( ( IConfigurableSolver ) result ).setInitializationData( element, null, null );
      } catch ( CoreException cExc ) {
        // Silently ignored
      }
    }
    
    return result;
    
  }

}
