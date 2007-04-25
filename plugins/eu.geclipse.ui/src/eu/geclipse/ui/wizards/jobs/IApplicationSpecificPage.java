/******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.ui.wizards.jobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import eu.geclipse.ui.internal.wizards.jobs.DataStageData;
import eu.geclipse.ui.wizards.jobs.wizardnodes.SpecificWizardPart;

/**
 * Interface common for all pages contained in {@link SpecificWizardPart}
 */
public interface IApplicationSpecificPage {
  
  /**
   * Returns map of parameter's names and their values. For one parameter there
   * may be more than one value
   * 
   * @return map of parameter's names and their values
   */
  Map<String, ArrayList<String>> getParametersValues();
  
  /**
   * Returns multidimensional map of parameters whose values are files local to
   * execution host, but that need to be transferred from remote location
   * (staged in). In this map keys are parameters names and inner maps are
   * values. Those inner map structure is: key - file name (local for execution
   * host), value - URI path to remote location
   * 
   * @return multidemensional map - keys are parameters names and inner maps are
   *         values. Those inner maps structure is: key - file name (local for
   *         execution host), value - URI path to remote location
   */
  Map<String, Properties> getStageInFiles();

  
  Map<String, Properties> getStageOutFiles();
  
}
