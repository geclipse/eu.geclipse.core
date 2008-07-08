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

import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.reporting.ProblemException;


/**
 * A job service is an {@link IGridService} that provides job
 * submission and status querying functionality. It is the interface
 * to the grid computing resources.
 */
public interface IGridJobService extends IGridService {

  /**
   * Tests if a given job description can be handled (i.e., submitted) by
   * this job service.
   * 
   * @param desc the job description to be tested.
   * @return true if the given job description can be submitted to the grid
   * using <code>this</code> service, false otherwise.
   */
  public boolean canSubmit( final IGridJobDescription desc );
  
  /**
   * Submits the given job description to the grid.
   * 
   * @param description the job description to be submitted.
   * @param monitor Use to monitor progress. May be <code>null</code>.
   * @return The id of the submitted job.
   * @throws ProblemException if submitting the job failed.
   * @throws GridModelException 
   */
  public IGridJobID submitJob( final IGridJobDescription description,
                               final IProgressMonitor monitor )
    throws ProblemException, GridModelException;
  
  /**
   * Queries the service about the job's status.
   * 
   * @param id The id of the job whose status has to be queried.
   * @param monitor Use to monitor progress. May be <code>null</code>.
   * @return IGridJobStatus the current status of the job.
   * @throws ProblemException if the status query failed.
   */
  public IGridJobStatus getJobStatus( final IGridJobID id, final IProgressMonitor monitor )
    throws ProblemException;
  
  /**
   * Deletes the given job from the server and releases resources on it.
   * 
   * @param job The job which has to be deleted.
   * @param monitor Use to monitor progress. May be <code>null</code>.
   * @throws ProblemException if deleting the job failed.
   */
  public void deleteJob( final IGridJobID id, IProgressMonitor monitor )
    throws ProblemException;

}
