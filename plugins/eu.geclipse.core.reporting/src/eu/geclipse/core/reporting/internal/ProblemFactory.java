/*****************************************************************************
 * Copyright (c) 2007, 2008 g-Eclipse Consortium 
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

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * The {@link ProblemFactory} is responsible for loading
 * problems from the specified extensions of the
 * eu.geclipse.reporting.problemReporting extension point.
 */
public class ProblemFactory {
  
  private static ProblemFactory singleton;
  
  /**
   * The problem reporting extension point. 
   */
  private IExtensionPoint problemReporting;
  
  /**
   * Private constructor.
   */
  private ProblemFactory() {
    IExtensionRegistry registry = Platform.getExtensionRegistry();
    this.problemReporting = registry.getExtensionPoint( IProblemReporting.EXTENSION_POINT );
  }
  
  /**
   * Get the singleton instance.
   * 
   * @return The factory's singleton instance.
   */
  public static ProblemFactory getFactory() {
    if ( singleton == null ) {
      singleton = new ProblemFactory();
    }
    return singleton;
  }
  
  /**
   * Get the problem with the specified ID and set the specified
   * parameters. If no problem with the specified ID could be found
   * a *unknown problem*-problem will be returned.
   * 
   * @param problemID The unique ID of the problem as declared
   * in the corresponding extension.
   * @param description A descriptive text that overwrites the
   * default text provided by the extension. If this is
   * <code>null</code> the default text will be taken.
   * @param exception A {@link Throwable} that may have caused
   * this problem. May be <code>null</code>.
   * @param pluginID The ID of the plug-in where the problem
   * happened.
   * @return The problem with the specified ID or a
   * *unknown problem*-problem if no such problem could be found.
   */
  public Problem getProblem( final String problemID,
                             final String description,
                             final Throwable exception,
                             final String pluginID ) {

    Problem result = null;
    
    IConfigurationElement element = getProblemElement( problemID );
    
    if ( element != null ) {
      result = createProblem( element, description, exception, pluginID );
    } else {
      result = new Problem( "unknownProblem", //$NON-NLS-1$
                            description,
                            exception,
                            null,
                            pluginID );
    }
    
    return result;
    
  }
  
  /**
   * Create a new problem from the specified configuration
   * element and set the parameters accordingly.
   * 
   * @param element The configuration element from which to
   * create a problem.
   * @param description A descriptive text that overwrites the
   * default text provided by the extension. If this is
   * <code>null</code> the default text will be taken.
   * @param exception A {@link Throwable} that may have caused
   * this problem. May be <code>null</code>.
   * @param pluginID The ID of the plug-in where the problem
   * happened.
   * @return The newly created problem.
   */
  private Problem createProblem( final IConfigurationElement element,
                                 final String description,
                                 final Throwable exception,
                                 final String pluginID ) {
    
    String id = element.getAttribute( IProblemReporting.PROBLEM_ID_ATTRIBUTE );
    String mailto = element.getAttribute( IProblemReporting.PROBLEM_MAILTO_ATTRIBUTE );
    
    String desc = description;
    if ( desc == null ) {
      desc = element.getAttribute( IProblemReporting.PROBLEM_DESCRIPTION_ATTRIBUTE );
    }
    
    Problem problem = new Problem( id, desc, exception, mailto, pluginID );
    
    addInternalSolutions( problem, element );
    addExternalSolutions( problem );
    
    return problem;
    
  }

  /**
   * Get the configuration element of the problem with the
   * specified ID.
   * 
   * @param problemID The solution's ID.
   * @return The configuration element for the problem with
   * the specified ID or <code>null</code> if no such element
   * could be found. 
   */
  private IConfigurationElement getProblemElement( final String problemID ) {
    
    IExtension[] extensions = this.problemReporting.getExtensions();
    for ( IExtension extension : extensions ) {
      IConfigurationElement[] elements = extension.getConfigurationElements();
      for ( IConfigurationElement element : elements ) {
        String name = element.getName();
        if ( IProblemReporting.PROBLEM_ELEMENT.equals( name ) ) {
          String id = element.getAttribute( IProblemReporting.PROBLEM_ID_ATTRIBUTE );
          if ( problemID.equals( id ) ) {
            return element;
          }
        }
      }
    }
    
    return null;
    
  }
  
  private void addExternalSolutions( final Problem problem ) {
    
    IExtension[] extensions = this.problemReporting.getExtensions();
    String id = problem.getID();
    
    for ( IExtension extension : extensions ) {
      IConfigurationElement[] elements = extension.getConfigurationElements();
      for ( IConfigurationElement element : elements ) {
        if ( IProblemReporting.SOLUTION_ELEMENT.equals( element.getName() ) ) {
          IConfigurationElement[] children = element.getChildren();
          for ( IConfigurationElement child : children ) {
            if ( IProblemReporting.REFERENCE_ELEMENT.equals( child.getName() ) ) {
              String problemReference
                = child.getAttribute( IProblemReporting.REFERENCE_ID_ATTRIBUTE );
              if ( id.equals( problemReference ) ) {
                String solutionID
                  = element.getAttribute( IProblemReporting.SOLUTION_ID_ATTRIBUTE );
                problem.addSolution( solutionID, null );
              }
            }
          }
        }
      }
    }
    
  }
  
  private void addInternalSolutions( final Problem problem,
                                     final IConfigurationElement element ) {
    IConfigurationElement[] children = element.getChildren();
    for ( IConfigurationElement child : children ) {
      if ( IProblemReporting.REFERENCE_ELEMENT.equals( child.getName() ) ) {
        String solutionID = child.getAttribute( IProblemReporting.REFERENCE_ID_ATTRIBUTE );
        problem.addSolution( solutionID, null );
      }
    }
  }

}
