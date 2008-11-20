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
 *    Szymon Mueller
 *****************************************************************************/

package eu.geclipse.core.internal;

/**
 * Helper class that holds the constants used for refering preferences.
 *  
 * @author stuempert-m
 */
public class PreferenceConstants {

  /**
   * The id for the defined VOs.
   */
  public static final String DEFINED_VOS_ID
    = "vos"; //$NON-NLS-1$
  
  /**
   * The id for the name of the default VO.
   */
  public static final String DEFAULT_VO_ID
    = "def_vo"; //$NON-NLS-1$
  
  /**
   * States if background updaters for job status should be running
   */
  public static final String JOBS_UPDATE_JOBS_STATUS = "jobs.update.jobs.status"; //$NON-NLS-1$
  
  /**
   * Behaviour of the global job status updater when user cancels creating
   * proxy needed by job status updater
   */
  public static final String JOBS_UPDATE_JOBS_CANCEL_BAHAVIOUR = "jobs.update.jobs.cancelBehaviour";
  
  /**
   * Time between background updates for each job
   */
  public static final String JOBS_UPDATE_JOBS_PERIOD = "jobs.update.jobs.period"; //$NON-NLS-1$
  
  /**
   * Maximum number of simultaneous running job status updaters
   */
  public static final String JOBS_UPDATE_UPDATERS_LIMTI = "jobs.update.updaters.limit"; //$NON-NLS-1$
 
}
