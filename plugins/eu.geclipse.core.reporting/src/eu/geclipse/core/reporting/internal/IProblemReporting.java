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
 *    Ariel Garcia      - added the cheatsheet and help tags
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
  
  
  // All possible tags come here
  
  /**
   * The ID of the cheatsheet-tag element within the problem reporting
   * extension point.
   */
  public static final String CHEATSHEET_TAG_ELEMENT
    = "cheatsheetTag"; //$NON-NLS-1$
  
  /**
   * The ID of the cheatsheet page ID attribute of the cheatsheet-tag element within
   * the problem reporting extension point.
   */
  public static final String CHEATSHEET_ID_ATTRIBUTE
    = "cheatsheetID"; //$NON-NLS-1$
  
  /**
   * The ID of the help-tag element within the problem reporting
   * extension point.
   */
  public static final String HELP_TAG_ELEMENT
    = "helpTag"; //$NON-NLS-1$
  
  /**
   * The ID of the help page path attribute of the help-tag element within
   * the problem reporting extension point.
   */
  public static final String HELP_ID_ATTRIBUTE
    = "pagePath"; //$NON-NLS-1$
  
  /**
   * The ID of the preference-tag element within the problem reporting
   * extension point.
   */
  public static final String PREFERENCE_TAG_ELEMENT
    = "preferenceTag"; //$NON-NLS-1$
  
  /**
   * The ID of the preference page ID attribute of the preference-tag element within
   * the problem reporting extension point.
   */
  public static final String PREFERENCE_ID_ATTRIBUTE
    = "pageID"; //$NON-NLS-1$
  
  /**
   * The ID of the URL-tag element within the problem reporting
   * extension point.
   */
  public static final String URL_TAG_ELEMENT
    = "urlTag"; //$NON-NLS-1$
  
  /**
   * The ID of the URL attribute of the URL-tag element within the problem reporting
   * extension point.
   */
  public static final String URL_ATTRIBUTE
    = "url"; //$NON-NLS-1$
  
  /**
   * The ID of the view-tag element within the problem reporting
   * extension point.
   */
  public static final String VIEW_TAG_ELEMENT
    = "viewTag"; //$NON-NLS-1$
  
  /**
   * The ID of the view ID attribute of the view-tag element within
   * the problem reporting extension point.
   */
  public static final String VIEW_ID_ATTRIBUTE
    = "viewID"; //$NON-NLS-1$
  
  /**
   * The ID of the wizard-tag element within the problem reporting
   * extension point.
   */
  public static final String WIZARD_TAG_ELEMENT
    = "wizardTag"; //$NON-NLS-1$
  
  /**
   * The ID of the page ID attribute of the wizard-tag element within
   * the problem reporting extension point.
   */
  public static final String WIZARD_ID_ATTRIBUTE
    = "pageID"; //$NON-NLS-1$
  
  /**
   * The ID of the unknown problem.
   */
  public static final String UNKNOWN_PROBLEM_ID
    = "eu.geclipse.core.reporting.problem.unknown"; //$NON-NLS-1$

}
