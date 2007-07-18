/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
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
package eu.geclipse.ui.views.filters;

import eu.geclipse.ui.views.GridJobView;



/**
 * Filters configuration for {@link GridJobView}
 */
public class JobViewFilterConfiguration extends AbstractGridFilterConfiguration
{
  /**
   * @param name configuration name
   */
  public JobViewFilterConfiguration( final String name ) {
    super( name );
    addFilter( new JobStatusFilter() );
    addFilter( new JobSubmissionTimeFilter() );
  }
  
  /**
   * @return filter using job-status to filter
   */
  public JobStatusFilter getJobStatusFilter() {
    return (JobStatusFilter)findFilter( JobStatusFilter.getId() );
  }
  
  /**
   * @return filter using job submission time to filter
   */
  public JobSubmissionTimeFilter getSubmissionTimeFilter() {
    return (JobSubmissionTimeFilter)findFilter( JobSubmissionTimeFilter.getId() );
  }
}
