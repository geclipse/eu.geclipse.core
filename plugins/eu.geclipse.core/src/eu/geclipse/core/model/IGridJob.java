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
 *    Pawel Wolniewicz
 *****************************************************************************/

package eu.geclipse.core.model;

import java.util.Date;


/**
 * Base interface for all middleware specific implementations of
 * jobs for the Grid.
 */
public interface IGridJob
    extends IGridContainer, IGridResource, IManageable {
  
  /**
   * Cancel this job if it is already running.
   */
  public void cancel();
  
  /**
   * Get the current status of this job.
   * 
   * @return This job's current status.
   */
  public IGridJobStatus getJobStatus();
  

  /**
   * Update job status. implementation of this method should
   * ask middleware for the fresh job status
   * 
   * @return This job's current status.
   */
  public IGridJobStatus updateJobStatus();
  
  /**
   * Get the unique id of this job. This id may be used to query
   * job information.
   * 
   * @return The unique id of this job.
   */
  public IGridJobID getID();

  /**
   * Get the job description used to create the job.
   * 
   * @return The unique id of this job.
   */
  public IGridJobDescription getJobDescription();

  /**
   * Returns the date of submission. 
   * 
   * @return time of last update
   */
  public Date getSubmissionTime();
  
}
