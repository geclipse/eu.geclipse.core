/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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

import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.reporting.ProblemException;


/**
 * Base interface for all middleware specific implementations of
 * jobs for the Grid.
 */
public interface IGridJob
    extends IGridContainer, IManageable {
  
  /**
   * Cancel this job if it is already running.
   */
  public void cancel();
  
  /**
   * Deletes the job from the grid.
   * 
   * @param monitor Use to monitor the progress. May be <code>null</code>.
   * @throws ProblemException if an error occurs while deleting the job.
   */
  public void deleteJob( IProgressMonitor monitor ) throws ProblemException;

  /**
   * Gets the current status of this job, without contacting the services.
   * 
   * @return This job's latest known status.
   */
  public IGridJobStatus getJobStatus();

  /**
   * Updates the job status. The implementation of this method should
   * ask the middleware for the up-to-date job status.
   * 
   * @param monitor Use to monitor the progress. May be <code>null</code>.
   * @return This job's current status.
   */
  public IGridJobStatus updateJobStatus( IProgressMonitor monitor );
  
  /**
   * Gets the unique id of this job. This id may be used to query
   * for job information.
   * 
   * @return The unique id of this job.
   */
  public IGridJobID getID();

  /**
   * Gets the job name.
   * 
   * @return The name of this job without additional decorations,
   *         file extensions, etc.
   */
  public String getJobName();

  /**
   * Gets the job description used to create the job.
   * 
   * @return The job description used to create this job.
   */
  public IGridJobDescription getJobDescription();

  /**
   * Gets the date and time of submission. 
   * 
   * @return A <code>Date</code> object with the submission time.
   */
  public Date getSubmissionTime();

}
