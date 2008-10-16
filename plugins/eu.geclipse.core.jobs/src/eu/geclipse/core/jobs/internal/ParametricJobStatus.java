/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium 
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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.core.jobs.internal;

import java.util.List;

import eu.geclipse.core.jobs.GridJob;
import eu.geclipse.core.jobs.GridJobStatus;


/**
 * Job status representing cumulative status for all jobs generated from parametric JSDL
 */
public class ParametricJobStatus extends GridJobStatus {
  private List<GridJob> childrenJobs;
  
  /**
   * 
   */
  public ParametricJobStatus() {
    // empty implementation for extension point
  }

  /**
   * @param statusName
   * @param statusType
   * @param childrenJobs
   */
  public ParametricJobStatus( final String statusName,
                              final int statusType,
                              final List<GridJob> childrenJobs )
  {
    super( statusName, statusType );
    this.childrenJobs = childrenJobs;
  }
  
  boolean isValid() {
    return this.childrenJobs != null;
  }  
}
