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

import java.net.URI;
import java.util.Map;

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
   */
  public IGridJobID submitJob( final IGridJobDescription description,
                               final IProgressMonitor monitor )
    throws ProblemException;
  
  /**
   * Submits the given job description to the grid.
   * 
   * @param description the job description to be submitted.
   * @param vo Virtual Organization, to which job is submitted
   * @param monitor Use to monitor progress. May be <code>null</code>.
   * @return The id of the submitted job.
   * @throws ProblemException if submitting the job failed.
   */
  public IGridJobID submitJob( final IGridJobDescription description,
                               final IVirtualOrganization vo,
                               final IProgressMonitor monitor )
    throws ProblemException;

  /**
   * Queries the service about the job's status.
   * 
   * @param id The id of the job whose status has to be queried.
   * @param vo Virtual Organization, to which job with passed id belongs to
   * @param monitor Use to monitor progress. May be <code>null</code>.
   * @return IGridJobStatus the current status of the job.
   * @throws ProblemException if the status query failed.
   */
  public IGridJobStatus getJobStatus( final IGridJobID id, final IVirtualOrganization vo, final IProgressMonitor monitor )
    throws ProblemException;
  
  /**
   * Deletes the given job from the server and releases resources on it.
   * @param id The id of job, which will be deleted
   * @param vo Virtual Organization, to which job with passed id belongs to
   * 
   * @param monitor Use to monitor progress. May be <code>null</code>.
   * @throws ProblemException if deleting the job failed.
   */
  public void deleteJob( final IGridJobID id, final IVirtualOrganization vo, IProgressMonitor monitor )
    throws ProblemException;
  
  /**
   * Get pairs (filename, URI) of input files for job.
   * @param jobId id of job, for which input files will be returned
   * @param jobDescription job description, which was used to submit job
   * @param vo Virtual Organization, for which job belongs to
   * @return URI Map containing input files used by job. Map contains pairs: <b>filename</b> (used to create link), and <b>URI</b> of input file.<br/>
   * It's possible to return <code>null</code> if input files cannot be returned
   * @throws ProblemException thrown, when input files cannot be retrieved, because of any error
   */
  public Map<String, URI> getInputFiles( IGridJobID jobId, IGridJobDescription jobDescription, IVirtualOrganization vo )
    throws ProblemException;
  
  /**
   * Get pairs (filename, URI) of output files for job.
   * @param jobId id of job, for which input files will be returned
   * @param jobDescription job description, which was used to submit job
   * @param vo Virtual Organization, for which job belongs to
   * @return URI Map containing output files created by job. Map contains pairs: <b>filename</b> (used to create link), and <b>URI</b> of output file.<br/>
   * It's possible to return <code>null</code> if input files cannot be returned
   * @throws ProblemException
   */
  public Map<String, URI> getOutputFiles( IGridJobID jobId, IGridJobDescription jobDescription, IVirtualOrganization vo )
    throws ProblemException;
  
}
