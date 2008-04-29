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

import java.util.List;

import eu.geclipse.batch.IQueueInfo.QueueType;
import eu.geclipse.batch.internal.WorkerNodeInfo;
import eu.geclipse.batch.model.qdl.DocumentRoot;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Wrapper for executing commands on a batch service.
 * IMPORTANT: Make all the public methods of an implementation of this interface synchronized.
 */
public interface IBatchService {
  
  /**
   * Get the batch service description that was used to create this service.
   * 
   * @return The description from which this service was created.
   */
  public IBatchServiceDescription getDescription();

  /**
   * Get the batch service name, i.e. the configuration file that were used to instantiate this service.
   * 
   * @return The batch service name.
   */
  public String getName();

  /**
   * Establishes a ssh-connection to the server running the batch service.
   *
   * @param sshConnectionInfo Holding the information needed to establish a ssh
   * connection with the PBS server.
   * @return Returns <code>true</code> if the connection is established,
   *  <code>false</code> otherwise.
   * @throws ProblemException In case of connection problem to service
   */
  public abstract boolean connectToServer( final ISSHConnectionInfo sshConnectionInfo ) throws ProblemException;

  /**
   * Tears down an already established ssh-connection to the server.
   */
  public abstract void disconnectFromServer( );

  /**
   * Returns a List of the jobs as {@link IBatchJobInfo}. If no jobs are running or
   * error then this method will return <code>null</code>.
   * 
   * @param manager The manager where the jobs will be merged into. 
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void getJobs( final BatchJobManager manager ) throws ProblemException;

  /**
   * Deletes a job in the queues of the batch service.
   *
   * @param jobId The identifier of the job to be deleted.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void delJob( final String jobId ) throws ProblemException;

  /**
   * Moves a job in the batch system to another batch system and/or queue.
   *
   * @param jobId The identifier of the job to be moved.
   * @param destQueue The destination queue, <code>null</code> if no destination queue.
   * @param destServer The destination server, <code>null</code> if no destination server.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void moveJob( final String jobId, 
                                   final String destQueue, 
                                   final String destServer ) throws ProblemException;

  /**
   * Move jobs in the batch system to another batch system and/or queue.
   *
   * @param jobIds The identifier of the jobs to be moved.
   * @param destQueue The destination queue, <code>null</code> if no destination queue.
   * @param destServer The destination server, <code>null</code> if no destination server.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void moveJobs( final String[] jobIds, 
                                   final String destQueue, 
                                   final String destServer ) throws ProblemException;

  /**
   * Puts a hold on a job in the queue of the batch service.
   *
   * @param jobId The identifier of the job to be held.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void holdJob( final String jobId ) throws ProblemException;

  /**
   * Puts a hold on one or more jobs in the queue of the batch service.
   *
   * @param jobIds The identifiers of the jobs to be held.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void holdJobs( final String[] jobIds ) throws ProblemException;

  /**
   * Release a job with a previous hold in queue of the batch system.
   *
   * @param jobId The identifier of the job to be released.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void releaseJob( final String jobId ) throws ProblemException;

  /**
   * Release one or more jobs with a previous hold in queue of the batch system.
   *
   * @param jobIds The identifiers of the jobs to be released.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void releaseJobs( final String[] jobIds ) throws ProblemException;

  /**
   * Rerun a currently running job.
   *
   * @param jobId The identifier of the job to be rerun.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void reRunJob( final String jobId ) throws ProblemException;

  /**
   * Rerun one or more currently running jobs.
   *
   * @param jobIds The identifiers of the jobs to be rerun.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void reRunJobs( final String[] jobIds ) throws ProblemException;

  /**
   * Executes pbsnodes on the PBS server and returns a list of the workernodes as
   * {@link WorkerNodeInfo}. If no workernodes or error parsing the output then
   * this method will return <code>null</code>. The output of pbsnodes are as
   * follows:
   *
   * @return A {@link List} of {@link IWorkerNodeInfo} or <code>null</code>.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract List<IWorkerNodeInfo> getWorkerNodes() throws ProblemException;

  /**
   * Changes the state of a workernode to offline, no jobs will be placed on
   * this workernode.
   *
   * @param nodeId The identifier of the node to be disabled.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void disableWN( final String nodeId ) throws ProblemException;

  /**
   * Changes the state of a workernode to enable, jobs will be placed on
   * this workernode.
   *
   * @param nodeId The identifier of the node to be disabled.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void enableWN( final String nodeId ) throws ProblemException;

  /**
   * Returns a list of the queues as {@link IQueueInfo}. If no queues or error
   * then this method will return <code>null</code>.
   *
   * @return A {@link List} of {@link IQueueInfo} or <code>null</code>.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract List<IQueueInfo> getQueues() throws ProblemException;

  /**
   * Sets the specified queue to be disabled, i.e. closes it for new jobs.
   *
   * @param queueId The identifier of the queue to be disabled.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void disableQueue( final String queueId ) throws ProblemException;

  /**
   * Sets the specified queues to be disabled, i.e. closes them for new jobs.
   *
   * @param queueIds The identifiers of the queues to be drained.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void disableQueues( final String[] queueIds ) throws ProblemException;

  /**
   * Enable the specified queue, i.e. new jobs can be inserted into the
   * queue.
   *
   * @param queueId The identifier of the queue to be enabled.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void enableQueue( final String queueId ) throws ProblemException;

  /**
   * Enables the specified queues, i.e. new jobs can be inserted into the
   * queues.
   *
   * @param queueIds The identifiers of the queues to be enabled.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void enableQueues( final String[] queueIds ) throws ProblemException;

  /**
   * Executes qstart to start a specific queue.
   *
   * @param queueId The identifier of the queue to be started.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void startQueue( final String queueId ) throws ProblemException;
  
  /**
   * Executes qstart to start all specified queues.
   *
   * @param queueIds The identifiers of the queues to be started.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void startQueues( final String[] queueIds ) throws ProblemException;
  
  /**
   * Executes qstop to stop a specific queue.
   *
   * @param queueId The identifier of the queue to be stopped.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void stopQueue( final String queueId ) throws ProblemException;
  
  /**
   * Executes qstop to stop all specified queues.
   *
   * @param queueIds The identifiers of the queues to be stopped.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void stopQueues( final String[] queueIds ) throws ProblemException;
  
  /**
   * Executes command that will create a new queue with minimum arguments.
   *
   * @param queueName The name of the new queue.
   * @param type The type of the new queue.
   * @param enabled The state of the new queue.
   * @param timeCPU Maximum allowed CPU time for any job. 
   * @param timeWall Maximum allowed wall time for any job.
   * @param vos Only allow access to the specified vos, <code>null</code> no restriction is applied.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void createQueue( final String queueName, final QueueType type, 
                                       final boolean enabled, final double timeCPU, 
                                       final double timeWall, final List<String> vos ) throws ProblemException; 
  
  /**
   * Executes command that will create a new queue with a "default" number of argument.
   *
   * @param queueName The name of the new queue.
   * @param priority The priority of the new queue.
   * @param type The type of the new queue.
   * @param enabled The state of the new queue.
   * @param started If the new queue will be started when created.
   * @param runMax Maximum running jobs at any given time from the queue.
   * @param timeCPU Maximum allowed CPU time for any job. 
   * @param timeWall Maximum allowed wall time for any job.
   * @param queMax Maximum allowed jobs in the queue
   * @param assignedResources 
   * @param vos Only allow access to the specified vos, <code>null</code> no restriction is applied.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void createQueue( final String queueName, final int priority, 
                                       final QueueType type, final boolean enabled, final boolean started, 
                                       final int runMax, final double timeCPU, 
                                       final double timeWall, final int queMax, 
                                       final int assignedResources, final List<String> vos ) throws ProblemException;
  
  
  /**
   * Executes command that will create a new queue with the root element of a Queue Description
   * Language (QDL) document
   * and argument.
   * 
   * @param documentRoot The root element of a QDL document.
   * @throws ProblemException
   */
  public abstract void createQueue( final DocumentRoot documentRoot ) throws ProblemException;
  
  /**
   * Deletes the specified queue.
   *
   * @param queueId The identifier of the queue to be deleted.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void delQueue( final String queueId ) throws ProblemException;

  /**
   * Executes command that will change the maximum allowed wall time of a specific queue.
   *
   * @param queueName The name of the queue to be modified.
   * @param timeWall The new maximum allowed wall time for any job.
   * @throws ProblemException If command is not executed successfully
   */
  public abstract void setMaxWallTime( final String queueName, final String timeWall ) throws ProblemException;
}
