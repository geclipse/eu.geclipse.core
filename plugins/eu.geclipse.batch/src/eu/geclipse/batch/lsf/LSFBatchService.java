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
package eu.geclipse.batch.lsf;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.batch.AbstractBatchService;
import eu.geclipse.batch.BatchJobManager;
import eu.geclipse.batch.IBatchJobInfo;
import eu.geclipse.batch.IBatchServiceDescription;
import eu.geclipse.batch.IQueueInfo;
import eu.geclipse.batch.IWorkerNodeInfo;
import eu.geclipse.batch.IQueueInfo.QueueType;
import eu.geclipse.batch.internal.QueueInfo;
import eu.geclipse.batch.internal.WorkerNodeInfo;
import eu.geclipse.batch.model.qdl.DocumentRoot;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Class for executing LSF commands on a Computing Element.
 */
public class LSFBatchService extends AbstractBatchService {
  private String pbsCmdPath;
  
  private String qCmdPath;

  private String qmgrCmdPath;
  
  private List< IBatchJobInfo > tmpJobs = new ArrayList< IBatchJobInfo >();
  
  /**
   * Create a new LSFWrapper.
   * @param description The {@link IBatchServiceDescription} from which
   *                    this service should be created.
   * @param name batch service name, i.e. the configuration file that were 
   *             used to instantiate this service
   */
  public LSFBatchService( final IBatchServiceDescription description, final String name ) {
    super( description, name );
    this.pbsCmdPath = null;
    this.qCmdPath = null;
    this.qmgrCmdPath = null;
  }

  public void createQueue( final String queueName,
                           final QueueType type,
                           final boolean enabled,
                           final double timeCPU,
                           final double timeWall,
                           final List< String > vos ) throws ProblemException
  {
    // TODO Auto-generated method stub
    
  }

  public void createQueue( final String queueName,
                           final int priority,
                           final QueueType type,
                           final boolean enabled,
                           final boolean started,
                           final int runMax,
                           final double timeCPU,
                           final double timeWall,
                           final int queMax,
                           final int assignedResources,
                           final List< String > vos ) throws ProblemException
  {
    // TODO Auto-generated method stub
    
  }

  public void createQueue( final DocumentRoot documentRoot ) throws ProblemException {
    // TODO Auto-generated method stub
    
  }
  
  /**
   * Executes bkill on the LSF server and returns if the command was executed of not.
   *
   * @param jobId The identifier of the job to be deleted.
   * @throws ProblemException If command is not executed successfully
   */
  public void delJob( final String jobId ) throws ProblemException {
    if ( null != jobId )
      this.connection.execCommand( this.qCmdPath + "bkill "+ jobId ); //$NON-NLS-1$
  }

  public void delQueue( final String queueId ) throws ProblemException {
    // TODO Auto-generated method stub
    
  }

  /**
   * Executes 'badmin qclose' to disable a specific queue.
   *
   * @param queueId The identifier of the queue to be disable.
   * @throws ProblemException If command is not executed successfully
   */
  public void disableQueue( final String queueId ) throws ProblemException {
    if ( null != queueId )
      this.connection.execCommand( this.qCmdPath + "badmin qclose "+ queueId ); //$NON-NLS-1$
  }

  public void disableQueues( final String[] queueIds ) throws ProblemException {
    // TODO Auto-generated method stub
    
  }
  
  /**
   * Shuts down the workernode, no jobs will be placed on this workernode.
   *
   * @param nodeId The identifier of the node to be disabled.
   * @throws ProblemException If command is not executed successfully
   */
  public void disableWN( final String nodeId ) throws ProblemException {
    if ( null != nodeId ) {
      this.connection.execCommand( this.qmgrCmdPath + "lsadmin limshutdown " + nodeId ); //$NON-NLS-1$
      this.connection.execCommand( this.qmgrCmdPath + "lsadmin resshutdown " + nodeId ); //$NON-NLS-1$
    }
  }
  
  /**
   * Executes 'badmin qopen' to enable a specific queue.
   *
   * @param queueId The identifier of the queue to be enabled.
   * @throws ProblemException If command is not executed successfully
   */
  public void enableQueue( final String queueId ) throws ProblemException {
    if ( null != queueId )
      this.connection.execCommand( this.qCmdPath + "badmin qopen "+ queueId ); //$NON-NLS-1$
  }

  public void enableQueues( final String[] queueIds ) throws ProblemException {
    // TODO Auto-generated method stub
    
  }

  
  /**
   * Start up the workernode, jobs will be placed on this workernode.
   *
   * @param nodeId The identifier of the node to be enabled.
   * @throws ProblemException If command is not executed successfully
   */

  public void enableWN( final String nodeId ) throws ProblemException {
    if ( null != nodeId ) {
      this.connection.execCommand( this.qmgrCmdPath + "lsadmin limstartup " + nodeId ); //$NON-NLS-1$
      this.connection.execCommand( this.qmgrCmdPath + "lsadmin resstartup " + nodeId ); //$NON-NLS-1$
    }
  }

  public void getJobs( final BatchJobManager manager ) throws ProblemException {
    // TODO Auto-generated method stub
    
  }

  /**
   * Executes bqueues on the LSF server and returns a list of the queues as
   * {@link QueueInfo}. If no queues or error parsing the output then
   * this method will return <code>null</code>. The output of bqueues are as
   * follows:
   * QUEUE_NAME     PRIO      STATUS     MAX  JL/U JL/P JL/H NJOBS  PEND  RUN  SUSP
   * night           30    Open:Inactive  -     -    -    -    4     4     0    0
   * short           10    Open:Active    50    5    -    -    1     0     1    0
   * simulation      10    Open:Active    -     2    -    -    0     0     0    0
   * default          1    Open:Active    -     -    -    -    6     4     2    0
   *
   * @return A {@link List} of {@link QueueInfo} or <code>null</code>.
   * @throws ProblemException If command is not executed successfully
   */  
  public List< IQueueInfo > getQueues() throws ProblemException {
    // TODO Auto-generated method stub
    return null;
  }
  
//  lshosts
//  HOST_NAME type model  cpuf ncpus maxmem maxswp server RESOURCES
//  hostC     SUN4 SunIPC  2.7     1   24M    48M    Yes  (sparc !f77)
//  hostE     SUN4 SunIPC  2.7     2   96M   170M    Yes  (sparc f77)
  /**
   * Executes bhosts on the LSF server and returns a list of the workernodes as
   * {@link WorkerNodeInfo}. If no workernodes or error parsing the output then
   * this method will return <code>null</code>. The output of bhosts are as
   * follows:
   * HOST_NAME          STATUS    JL/U  MAX  NJOBS  RUN  SSUSP USUSP  RSV
   * hostA                ok        2     1     0     0     0     0     0
   * hostB              closed      2     2     2     2     0     0     0
   * hostD                ok        -     8     1     1     0     0     0
   *
   * @return A {@link List} of {@link WorkerNodeInfo} or <code>null</code>.
   * @throws ProblemException If command is not executed successfully
   */  
  public List< IWorkerNodeInfo > getWorkerNodes() throws ProblemException {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Puts a hold on a job in the queue of the batch service.
   *
   * @param jobId The identifier of the job to be held.
   * @throws ProblemException If command is not executed successfully
   */
  public void holdJob( final String jobId ) throws ProblemException {
    if ( null != jobId ) {
      String cmd = "qstop " + jobId; //$NON-NLS-1$

      this.connection.execCommand( this.qCmdPath + cmd );
    }
  }

  public void holdJobs( final String[] jobIds ) throws ProblemException {
    // TODO Auto-generated method stub
    
  }

  /**
   * Executes bswitch on the LSF server and returns if the command was executed of not.
   *
   * @param jobId The identifier of the job to be moved.
   * @param destQueue The destination queue, <code>null</code> if no destination queue.
   * @param destServer The destination server, <code>null</code> if no destination server.
   * @throws ProblemException If command is not executed successfully
   */
  public void moveJob( final String jobId, final String destQueue, final String destServer )
    throws ProblemException {
    if ( null != jobId && null != destQueue )
      this.connection.execCommand( this.qCmdPath + "bswitch " + destQueue + " " + jobId ); //$NON-NLS-1$ //$NON-NLS-2$
  }

  public void moveJobs( final String[] jobIds, final String destQueue, final String destServer )
    throws ProblemException
  {
    // TODO Auto-generated method stub
    
  }

  /**
   * Release a job with a previous hold in queue of the batch system.
   *
   * @param jobId The identifier of the job to be released.
   * @throws ProblemException If command is not executed successfully
   */
  public void releaseJob( final String jobId ) throws ProblemException {
    if ( null != jobId ) {
      String cmd = "bresume " + jobId; //$NON-NLS-1$

      this.connection.execCommand( this.qCmdPath + cmd );
    }
  }

  public void releaseJobs( final String[] jobIds ) throws ProblemException {
    // TODO Auto-generated method stub
    
  }

  public void setMaxWallTime( final String queueName, final String timeWall )
    throws ProblemException
  {
    // TODO Auto-generated method stub
    
  }

  /**
   * Executes 'badmin qact' to start a specific queue.
   *
   * @param queueId The identifier of the queue to be started.
   * @throws ProblemException If command is not executed successfully
   */
  public void startQueue( final String queueId ) throws ProblemException {
    if ( null != queueId )
      this.connection.execCommand( this.qCmdPath + "badmin qact "+ queueId ); //$NON-NLS-1$
  }

  public void startQueues( final String[] queueIds ) throws ProblemException {
    // TODO Auto-generated method stub
    
  }

  /**
   * Executes 'badmin qinact' to stop a specific queue.
   *
   * @param queueId The identifier of the queue to be stopped.
   * @throws ProblemException If command is not executed successfully
   */  
  public void stopQueue( final String queueId ) throws ProblemException {
    if ( null != queueId )
      this.connection.execCommand( this.qCmdPath + "badmin qinact "+ queueId ); //$NON-NLS-1$
  }

  public void stopQueues( final String[] queueIds ) throws ProblemException {
    // TODO Auto-generated method stub
    
  }
}
