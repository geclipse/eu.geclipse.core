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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch;

import eu.geclipse.batch.internal.Messages;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Interface for holding information about a specific Grid job.
 */
public interface IBatchJobInfo {
  /**
   * This field determines the type of the state of this Queue.
   */
  public static enum JobState {
    /**
     * Job is completed after having run
     */
    C, 

    /**
     * Job is exiting after having run.
     */
    E,

    /**
     * Job is held.
     */
    H,

    /**
     * Job is queued, eligible to run or routed.
     */
    Q,

    /**
     * Job is running.
     */
    R,

    /**
     * job is being moved to new location.
     */
    T,

    /**
     * job is waiting for its execution time.
     */
    W,

    /**
     * (Unicos only) job is suspend.
     */
    S;

    @Override
    public String toString() {
      String str = null;
      
      switch ( this ) {
        case C: 
          str = Messages.getString( "IBatchJobInfo.JobStateC" ); //$NON-NLS-1$
          break;
        case E:
          str = Messages.getString( "IBatchJobInfo.JobStateE" ); //$NON-NLS-1$
          break;
        case H:
          str = Messages.getString( "IBatchJobInfo.JobStateH" ); //$NON-NLS-1$
          break;
        case Q:
          str = Messages.getString( "IBatchJobInfo.JobStateQ" ); //$NON-NLS-1$
          break;
        case R:
          str = Messages.getString( "IBatchJobInfo.JobStateR" ); //$NON-NLS-1$
          break;
        case T:
          str = Messages.getString( "IBatchJobInfo.JobStateT" ); //$NON-NLS-1$
          break;
        case W:
          str = Messages.getString( "IBatchJobInfo.JobStateW" ); //$NON-NLS-1$
          break;
        case S:
          str = Messages.getString( "IBatchJobInfo.JobStateS" ); //$NON-NLS-1$
          break;
        default:
          break;
      }

      return str;
    }
  }

  /**
   * @return the queueName
   */
  public String getQueueName();

  /**
   * @param queueName the queueName to set
   */
  public void setQueueName( final String queueName );

  /**
   * @return the status
   */
  public JobState getStatus();

  /**
   * @param status the status to set
   */
  public void setStatus( final JobState status );

  /**
   * @return the timeUse
   */
  public String getTimeUse();

  /**
   * @param timeUse the timeUse to set
   */
  public void setTimeUse( final String timeUse );

  /**
   * @return the userAccount
   */
  public String getUserAccount();

  /**
   * @param userAccount the userAccount to set
   */
  public void setUserAccount( final String userAccount );

  /**
   * @return the jobId
   */
  public String getJobId();

  /**
   * @return the jobName
   */
  public String getJobName();

  /**
   * Can this job be deleted.
   * @return Returns <code>true<\code> if this job can be deleted, <code>false</code> otherwise.
   */
  public boolean isDeletable();

  /**
   * Can this job be moved.
   * @return Returns <code>true</code> if this job can be moved, <code>false</code> otherwise.
   */
  public boolean isMovable();

  /**
   * Can this job be held.
   * @return Returns <code>true<\code> if this job can be held, <code>false</code> otherwise.
   */
  public boolean isHoldable();

  /**
   * Can this job be released.
   * @return Returns <code>true<\code> if this job can be released, <code>false</code> otherwise.
   */
  public boolean isReleasable();

  /**
   * Deletes this job from the batch service.
   * @throws ProblemException If command is not executed successfully
   */
  public void deleteJob() throws ProblemException;
  
  /**
   * Move this job to another worker node or batch service.
   * @param destQueue The destination queue, <code>null</code> if no destination queue.
   * @param destServer The destination server, <code>null</code> if no destination server.
   * @throws ProblemException If command is not executed successfully
   */
  public void moveJob( final String destQueue, final String destServer ) throws ProblemException;

  /**
   * Puts a hold on the job in the queue of the batch service.
   *
   * @throws ProblemException If command is not executed successfully
   */
  public void holdJob() throws ProblemException;

  /**
   * Release the job with a previous hold in queue of the batch system.
   *
   * @throws ProblemException If command is not executed successfully
   */
  public void releaseJob() throws ProblemException;
  
  /**
   * Return the type of batch service name that handles this batch job.
   *
   * @return Returns the batch service name, <code>null</code> if unknown.
   */
  public abstract String getServiceTypeName ();

}
