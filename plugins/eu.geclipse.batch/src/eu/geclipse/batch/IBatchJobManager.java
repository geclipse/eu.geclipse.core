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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch;

import java.util.List;

import eu.geclipse.batch.IBatchJobInfo.JobState;


/**
 * Interface for a manager that manages Batch Jobs
 * 
 * @author hgjermund
 */
public interface IBatchJobManager {
  /**
   * Either add the following job if it doesn't exist or merge the state 
   * if it already exists.
   *   
   * @param jobId The unique id of the job.
   * @param jobName The name of the job.
   * @param userAccount The name of the pool account that are used to execute 
   * this job.
   * @param timeUse How much time this job have used.
   * @param status The current status of this job.
   * @param queueName The name of the queue where this job is placed.
   * @param batchWrapper The wrapper to access the batch service where this 
   * job is queued.
   * @return Returns the created job.
   */
  public IBatchJobInfo addMerge( final String jobId, final String jobName, final String userAccount,
                        final String timeUse, final JobState status, final String queueName,
                        final IBatchService batchWrapper );
  
  /**
   * Update our local state with the current state, by only keeping the 
   * active jobs. I.e. remove all the jobs that are not active anymore. 
   * 
   * @param currentActiveJobs These are the currently active jobs.
   */
  public void removeOld( final List< IBatchJobInfo > currentActiveJobs );
  
}
