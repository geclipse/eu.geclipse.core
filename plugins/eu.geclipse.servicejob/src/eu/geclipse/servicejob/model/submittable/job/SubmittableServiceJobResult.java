/*****************************************************************************
 * Copyright (c) 2008, g-Eclipse Consortium 
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
 *    Szymon Mueller - PSNC - Initial API and implementation
 *****************************************************************************/
package eu.geclipse.servicejob.model.submittable.job;

import java.util.Date;

import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.servicejob.model.impl.ServiceJobResult;

/**
 * Abstract class for submittable service result. Contains important jobID field, 
 * which corresponds to the jobID of the submitted job.
 *
 */
public class SubmittableServiceJobResult extends ServiceJobResult {

  private IGridJobID jobID;
  
  /**
   * Simple constructor.
   * 
   * @param runDate When this result was created.
   * @param resource Name of resource where service job should be run.
   * @param subJob Name of performed sub-job.
   * @param resultRawData Content of Output > Result > ResultData GTDL element.
   * @param resultSummary Textual interpretation of service job's result.
   * @param resultType Type of the result.
   * @param resultEnum BES status result.
   */
  public SubmittableServiceJobResult( final Date runDate,
                                      final String resource,
                                      final String subJob,
                                      final String resultRawData,
                                      final String resultSummary,
                                      final String resultType,
                                      final String resultEnum )
  {
    super( runDate,resource,subJob,resultRawData,resultSummary,resultType,resultEnum );
    this.jobID = null;
  }
  
  /**
   * Setter of jobID for this service job result. If possible, this should be 
   * middleware specific jobID.
   * @param jobID {@link IGridJobID} which should be set.
   */
  public void setJobID( final IGridJobID jobID ) {
    this.jobID = jobID;
  }
   
  /**
   * Updates job service job results. This method should be invoked after each 
   * fetching of the status from the job service.
   * 
   * @param date 
   *            date of the last status refresh
   * @param result
   *            status of the job
   * @param besStatus
   *            BES status
   */
  public void updateStatus( final Date date,
                            final String result,
                            final String besStatus ) {
    setRunDate( date );
    setResultSummary( result );
    setResultEnum( besStatus );
  }
  
  /**
   * Getter of jobID.
   * @return ID of the job associated with this service job result.
   */
  public IGridJobID getJobID() {
    return this.jobID;
  }

  /**
   * Gets the jobID String representation of job associated with this service
   * job result.
   * 
   * @return jobID string representation of this job's ID.
   */
  public String getJobIDString() {
    return this.jobID.getJobID();
  }
  
}
