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

/**
 * Internally used interface that contains all definitions
 * of the corresponding extension point.
 */
public interface IProblemReporting {
  

  /**
   * The plug-in's ID. 
   */
  public static final String PLUGIN_ID
    = "eu.geclipse.core.reporting"; //$NON-NLS-1$
  
  /**
   * The ID of the problem reporting extension point.
   */
  public static final String EXTENSION_POINT
    = "eu.geclipse.core.reporting.problemReporting"; //$NON-NLS-1$
  
  /**
   * The ID of the problem element within the problem reporting
   * extension point.
   */
  public static final String PROBLEM_ELEMENT
    = "problem"; //$NON-NLS-1$
  
  /**
   * The ID of the ID attribute of the problem element within
   * the problem reporting extension point.
   */
  public static final String PROBLEM_ID_ATTRIBUTE
    = "id"; //$NON-NLS-1$
  
  /**
   * The ID of the description attribute of the problem element within
   * the problem reporting extension point.
   */
  public static final String PROBLEM_DESCRIPTION_ATTRIBUTE
    = "description"; //$NON-NLS-1$
  
  /**
   * The ID of the mailto attribute of the problem element within
   * the problem reporting extension point.
   */
  public static final String PROBLEM_MAILTO_ATTRIBUTE
    = "mailto"; //$NON-NLS-1$
  
  /**
   * The ID of the solution element within the problem reporting
   * extension point.
   */
  public static final String SOLUTION_ELEMENT
    = "solution"; //$NON-NLS-1$
  
  /**
   * The ID of the ID attribute of the solution element within
   * the problem reporting extension point.
   */
  public static final String SOLUTION_ID_ATTRIBUTE
    = "id"; //$NON-NLS-1$
  
  /**
   * The ID of the description attribute of the solution element within
   * the problem reporting extension point.
   */
  public static final String SOLUTION_DESCRIPTION_ATTRIBUTE
    = "description"; //$NON-NLS-1$
  
  /**
   * The ID of the solver attribute of the solution element within
   * the problem reporting extension point.
   */
  public static final String SOLUTION_SOLVER_ATTRIBUTE
    = "solver"; //$NON-NLS-1$
  
  /**
   * The ID of the reference element within the problem reporting
   * extension point.
   */
  public static final String REFERENCE_ELEMENT
    = "reference"; //$NON-NLS-1$
  
  /**
   * The ID of the reference ID attribute of the reference element within
   * the problem reporting extension point.
   */
  public static final String REFERENCE_ID_ATTRIBUTE
    = "referenceID"; //$NON-NLS-1$
  
  /**
   * The ID of the properties tag element within the problem reporting
   * extension point.
   */
  public static final String PREFERENCE_TAG_ELEMENT
    = "preferenceTag"; //$NON-NLS-1$
  
  /**
   * The ID of the page ID attribute of the properties element within
   * the problem reporting extension point.
   */
  public static final String PROPERTIES_ID_ATTRIBUTE
    = "pageID"; //$NON-NLS-1$
  
  /**
   * The ID of the view tag element within the problem reporting
   * extension point.
   */
  public static final String VIEW_TAG_ELEMENT
    = "viewTag"; //$NON-NLS-1$
  
  /**
   * The ID of the view ID attribute of the properties element within
   * the problem reporting extension point.
   */
  public static final String VIEW_ID_ATTRIBUTE
    = "viewID"; //$NON-NLS-1$

}
