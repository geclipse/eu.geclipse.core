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
package eu.geclipse.batch.pbs;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import eu.geclipse.batch.AbstractBatchService;
import eu.geclipse.batch.BatchJobManager;
import eu.geclipse.batch.IBatchJobInfo;
import eu.geclipse.batch.IBatchServiceDescription;
import eu.geclipse.batch.IQueueInfo;
import eu.geclipse.batch.ISSHConnectionInfo;
import eu.geclipse.batch.IWorkerNodeInfo;
import eu.geclipse.batch.IBatchJobInfo.JobState;
import eu.geclipse.batch.IQueueInfo.QueueRunState;
import eu.geclipse.batch.IQueueInfo.QueueState;
import eu.geclipse.batch.IQueueInfo.QueueType;
import eu.geclipse.batch.IWorkerNodeInfo.WorkerNodeState;
import eu.geclipse.batch.internal.BatchJobInfo;
import eu.geclipse.batch.internal.QueueInfo;
import eu.geclipse.batch.internal.WorkerNodeInfo;
import eu.geclipse.batch.model.qdl.DocumentRoot;
import eu.geclipse.batch.model.qdl.QueueStatusEnumeration;
import eu.geclipse.batch.model.qdl.QueueTypeEnumeration;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Class for executing PBS commands on a Computing Element.
 */
public final class PBSBatchService extends AbstractBatchService {

  private String pbsPath;

  private List< IBatchJobInfo > tmpJobs = new ArrayList< IBatchJobInfo >();
  
  /**
   * Create a new PBSWrapper.
   * @param description The {@link IBatchServiceDescription} from which
   *                    this service should be created.
   * @param name batch service name, i.e. the configuration file that were 
   *             used to instantiate this service
   */
  public PBSBatchService( final IBatchServiceDescription description, final String name ) {
    super( description, name );
    this.pbsPath = null;
  }

  /**
   * Parses a string containing information about a job and
   * returns it as a {@link BatchJobInfo}. If the string is malformed
   * this method will return <code>null</code>.
   *
   * @param jobLine The string containing the information about the specific job.
   * @return A job {@link BatchJobInfo} or <code>null</code>.
   */
  private IBatchJobInfo parseJobLine( final String jobLine, final BatchJobManager manager ) {
    IBatchJobInfo jobInfo = null;
    String jobId, jobName, queueName;
    String userAccount, timeUse;
    JobState status = null;
    String[] lineSplit;

    // Split the line on 1 or more spaces
    lineSplit = jobLine.split( " +" ); //$NON-NLS-1$

    // TODO verify the structure of the following variables
    if ( 6 == lineSplit.length ) {
      jobId = lineSplit[0];
      jobName = lineSplit[1];
      userAccount = lineSplit[2];
      timeUse = lineSplit[3];
      try {
        status = JobState.valueOf( lineSplit[4] );
      } catch ( IllegalArgumentException iaexc ) {
        // Do nothing
      }
      queueName = lineSplit[5];

      // Only if all the fields have qualified values.
      if ( null != status )
        jobInfo = manager.addMerge( jobId, jobName, userAccount, timeUse, status, queueName, this );
        //jobInfo = new BatchJobInfo( jobId, jobName, userAccount, timeUse, status, queueName, this );
    }

    return jobInfo;
  }

  /**
   * Parses a string containing information about a queue and
   * returns it as a {@link QueueInfo}. If the string is malformed
   * this method will return <code>null</code>.
   *
   * @param queueLine The string containing the information about the specific queue.
   * @return A queue {@link QueueInfo} or <code>null</code>.
   */
  private QueueInfo parseQueueLine( final String queueLine ) {
    QueueInfo queueInfo = null;
    QueueState state = null;
    QueueRunState runState = null;
    String queueName, timeCPU, timeWall, node, lm;
    int memory = -1, run = -1, que = -1;
    String[] lineSplit;

    // Split the line on 1 or more spaces
    lineSplit = queueLine.split( " +" ); //$NON-NLS-1$

    // TODO verify the structure of the following variables
    if ( 10 == lineSplit.length ) {
      queueName = lineSplit[0];
      try {
        memory = Integer.parseInt( lineSplit[1] );
      } catch ( NumberFormatException NumExp) {
        // Nothing to do here
      }
      timeCPU = lineSplit[2];
      timeWall = lineSplit[3];
      node =  lineSplit[4];
      try {
        run = Integer.parseInt( lineSplit[5] );
        que = Integer.parseInt( lineSplit[6] );
      } catch ( NumberFormatException NumExp) {
        // Nothing to do here
      }
      lm = lineSplit[7];

      try {
        state = QueueState.valueOf( lineSplit[8] );
      } catch ( IllegalArgumentException iaexc ) {
        // Do nothing
      }

      try {
        runState = QueueRunState.valueOf( lineSplit[9] );
      } catch ( IllegalArgumentException iaexc ) {
        // Do nothing
      }

      // Only if all the fields have qualified values.
      if ( null != state )
        queueInfo = new QueueInfo( queueName, memory, timeCPU, timeWall, node, run, que, lm, state, runState );
    }

    return queueInfo;
  }

  /**
   * Returns whatever follows from the identifier in the string. If the identifier
   * doesn't exists <code>null</code> is returned.
   *
   * @param line The string containing the identifier and other info.
   * @param identifier The term that is to be removed.
   * @return The remainder of the string, staring at the end of the identifier or <code>null</code>.
   */
  private String getRightHandSide( final String line, final String identifier ) {
    String str = null;
    int idx;

    idx = line.indexOf( identifier );
    if ( -1 != idx ) {
      str = line.substring( idx + identifier.length() );
    }

    return str;
  }

  private String findJobId( final String jobIdLine ) {
    int beginIndex, endIndex;
    String ret;
    // NOTE if not found -1 is returned, but with the +1 our index is 
    // still at 0 so no need to test for no existance 
    beginIndex = jobIdLine.indexOf( '/' ) + 1;
    
    // If there are any text after the second '.', then we want to cut it
    endIndex = jobIdLine.indexOf( '.') + 1;
    
    
    if ( -1 == jobIdLine.indexOf( '.', endIndex ) )
      ret = jobIdLine.substring( beginIndex ); // only one '.' so no cutting at the end
    else
      ret = jobIdLine.substring( beginIndex, jobIdLine.indexOf( '.', endIndex ) );
    
    return ret;
  }
  /**
   * Parses a string containing information about all the workernodes and
   * returns it as a List of {@link WorkerNodeInfo}. If the string is malformed
   * for one of the workernodes it is excluded. If the information for all the
   * workernodes are malformed then this method will return <code>null</code>.
   *
   * @param nodeLines The string containing the information about all the workernodes.
   * @return A List of {@link WorkerNodeInfo} or <code>null</code>.
   */
  private List< IWorkerNodeInfo > parseNodes ( final String nodeLines ) {
    String wnFQN = null, properties = null, type = null, status = null, job = null;
    WorkerNodeState state = null;
    int tmpIdx;
    int np = -1;
    int endIndex, beginIndex = 0;
    int beginJobIdx, endJobIdx;
    String tmpJob;
    String line = null;
    WorkerNodeInfo wnInfo;
    List<IWorkerNodeInfo> wns = new ArrayList< IWorkerNodeInfo >();
    List< String> jobs = null;

    while ( -1 != ( endIndex = nodeLines.indexOf( '\n', beginIndex ) ) ) {
      // clear any empty lines
      while ( beginIndex == endIndex && beginIndex < nodeLines.length() ) {
        ++beginIndex;
        endIndex = nodeLines.indexOf( '\n', beginIndex );
      }

      // Reached the end of the string
      if ( -1 == endIndex )
        break;

      // Get the name of the worker node
      wnFQN = nodeLines.substring( beginIndex, endIndex );

      // Get the state
      beginIndex = endIndex+1;
      endIndex = nodeLines.indexOf( '\n', beginIndex );
      if ( -1 != endIndex ) {
        line = nodeLines.substring( beginIndex, endIndex );
        line = getRightHandSide( line, "state = " ); //$NON-NLS-1$

        // Sometimes there may be multiple states listed in a comma separated list, we will only get the first
        tmpIdx = line.indexOf( ',' );

        if ( -1 != tmpIdx )
          line = line.substring( 0, tmpIdx );

        try {
          state = WorkerNodeState.valueOfEnhanced( line );
        } catch ( IllegalArgumentException iaexc ) {
          // Do nothing
        }
      }

      // Get the num. of processors
      beginIndex = endIndex+1;
      endIndex = nodeLines.indexOf( '\n', beginIndex );
      if ( -1 != endIndex ) {
        line = nodeLines.substring( beginIndex, endIndex );
        line = getRightHandSide( line, "np = " ); //$NON-NLS-1$
        if (null != line ) {
          try {
            np = Integer.parseInt( line );
          } catch ( NumberFormatException NumExp) { np = -1; }
        }
        else
          np = -1;
      }

      // Get the properties
      beginIndex = endIndex+1;
      endIndex = nodeLines.indexOf( '\n', beginIndex );
      if ( -1 != endIndex ) {
        line = nodeLines.substring( beginIndex, endIndex );
        properties = getRightHandSide( line, "properties = " ); //$NON-NLS-1$
      }

      // Get the type
      if ( null == properties && null != line )
        type = getRightHandSide( line, "ntype = " ); //$NON-NLS-1$
      else {
        beginIndex = endIndex+1;
        endIndex = nodeLines.indexOf( '\n', beginIndex );
        if ( -1 != endIndex ) {
          line = nodeLines.substring( beginIndex, endIndex );
          type = getRightHandSide( line, "ntype = " ); //$NON-NLS-1$
        }
      }
      // There may be multiple jobs on this wn
      do {
        beginIndex = endIndex+1;
        endIndex = nodeLines.indexOf( '\n', beginIndex );
        if ( -1 != endIndex ) {
          line = nodeLines.substring( beginIndex, endIndex );
          job = getRightHandSide( line, "jobs = " ); //$NON-NLS-1$

          // Add the job to the jobs
          if ( null != job ) {
            if ( null == jobs )
              jobs = new ArrayList< String >();
            
            // Multiple jobs may be separated with a ',' on the same line
            beginJobIdx = 0;
            endJobIdx = job.indexOf( ',' );
            
            while ( -1 != endJobIdx ) {
              tmpJob = job.substring( beginJobIdx, endJobIdx );
              tmpJob = tmpJob.trim();
              jobs.add( findJobId( tmpJob ) );
              
              beginJobIdx = endJobIdx+1;
              endJobIdx = job.indexOf( ',', beginJobIdx );
            }

            // Do the last one
            tmpJob = job.substring( beginJobIdx );
            tmpJob = tmpJob.trim();
            jobs.add( findJobId( tmpJob ) );
          }
          else // We have past the last job, we have moved to the properties
            status = getRightHandSide( line, "status = " ); //$NON-NLS-1$
        }
      } while ( null != job );

      if ( null != wnFQN && null != state && -1 != np && null != type ) {
        wnInfo = new WorkerNodeInfo( wnFQN, state, np, properties, type, status, jobs );
        wns.add( wnInfo );
      }

      // Clear the fields
      wnFQN = null;
      state = null;
      properties = null;
      type = null;
      status = null;
      if ( null != jobs )
        jobs = null;

      beginIndex = endIndex+1;
    }

    if ( wns.isEmpty() )
      wns = null;

    return wns;
  }

  /**
   * Establishes a ssh-connection to the server running the PBS service.
   *
   * @param sshConnectionInfo Holding the information needed to establish a ssh
   * connection with the PBS server.
   * @return Returns <code>true</code> if the connection is established,
   *  <code>false</code> otherwise.
   * @throws ProblemException If the ssh connection cannot be established
   */
  @Override
  public synchronized boolean connectToServer( final ISSHConnectionInfo sshConnectionInfo )  throws ProblemException {
    boolean ret;

    ret = super.connectToServer( sshConnectionInfo ); 

    if ( ret ) {
      try {
        // Lets first check if we need to explicitly set the path for the PBS commands
        this.pbsPath = this.connection.execCommand( "qstat -q" ); //$NON-NLS-1$

        // No path needed
        this.pbsPath = ""; //$NON-NLS-1$
      } catch ( ProblemException bexp ) {
        // TODO when in the model fix this
        //if ( bexp.getProblem().getID() == BatchProblems.COMMAND_FAILED  ){
          String cmd = "find / -xdev -maxdepth 10 -type f -name qstat 2> /dev/null"; //$NON-NLS-1$
          this.pbsPath = this.connection.execCommand( cmd ); 

          if ( null != this.pbsPath ) {
            int idx = this.pbsPath.lastIndexOf( "qstat" ); //$NON-NLS-1$

            // The path was found
            if ( -1 != idx )
              this.pbsPath = this.pbsPath.substring( 0, idx );
          }
//        } else // Something else went wrong 
//          throw bexp;
      }
    }

    return ret;
  }

  /**
   * Executes qstat on the PBS server and returns a list of the jobs as
   * {@link BatchJobInfo}. If no jobs are running or error parsing the output then
   * this method will return <code>null</code>. The output of qstat are as
   * follows:
   * Job id              Name             User            Time Use S Queue
   * ------------------- ---------------- --------------- -------- - -----
   * 968.ce201           blahjob_KJ8465   see001                 0 R see
   * 969.ce201           blahjob_RT8482   see001                 0 R see
   * 
   * @param manager The manager where the jobs will be merged into. 
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void getJobs( final BatchJobManager manager ) throws ProblemException {
    String outPut;
    IBatchJobInfo jobInfo;
    String line;

    outPut = this.connection.execCommand( this.pbsPath + "qstat" ); //$NON-NLS-1$
    this.tmpJobs.clear(); // clear the old holder

    if ( null != outPut ) {
      int endIndex, beginIndex = 0;
      int skip = 0;
      while ( -1 != ( endIndex = outPut.indexOf( '\n', beginIndex ) ) ) {
        line = outPut.substring( beginIndex, endIndex );

        // Skip first two lines
        if ( 2 < ++skip ) {
          jobInfo = parseJobLine( line, manager );
          if ( null != jobInfo )
            this.tmpJobs.add( jobInfo );
        }

        beginIndex = endIndex+1;
      }
    }

    // Remove the deleted jobs from the manager
    manager.removeOld( this.tmpJobs );
  }

  /**
   * Executes qdel on the PBS server and returns if the command was executed of not.
   *
   * @param jobId The identifier of the job to be deleted.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void delJob( final String jobId ) throws ProblemException {
    if ( null != jobId )
      this.connection.execCommand( this.pbsPath + "qdel "+ jobId ); //$NON-NLS-1$
  }

  /**
   * Executes qmove on the PBS server and returns if the command was executed of not.
   *
   * @param jobId The identifier of the job to be moved.
   * @param destQueue The destination queue, <code>null</code> if no destination queue.
   * @param destServer The destination server, <code>null</code> if no destination server.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void moveJob( final String jobId, 
                                       final String destQueue, 
                                       final String destServer ) throws ProblemException {
    String cmd = "qmove "; //$NON-NLS-1$

    if ( null != jobId ) {
      if ( null != destServer && null != destQueue )
        cmd += destQueue + "@" + destServer; //$NON-NLS-1$
      else if ( null == destServer )
        cmd += destQueue;
      else if ( null == destQueue )
        cmd += destServer;
      else
        assert false; // Not enough arguments 

      this.connection.execCommand( this.pbsPath + cmd + " " + jobId ); //$NON-NLS-1$
    }
  }

  /**
   * Executes qmove on the PBS server and returns if the command was executed of not.
   *
   * @param jobIds The identifier of the jobs to be moved.
   * @param destQueue The destination queue, <code>null</code> if no destination queue.
   * @param destServer The destination server, <code>null</code> if no destination server.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void moveJobs( final String[] jobIds, 
                                       final String destQueue, 
                                       final String destServer ) throws ProblemException {
    String strJobs = null;
    String cmd = "qmove "; //$NON-NLS-1$

    if ( null != jobIds ) {
      if ( null != destServer && null != destQueue )
        cmd += destQueue + "@" + destServer; //$NON-NLS-1$
      else if ( null == destServer )
        cmd += destQueue;
      else if ( null == destQueue )
        cmd += destServer;
      else
        assert false; // Not enough arguments 

      // Add the jobIds 
      for ( String str : jobIds )
        strJobs = str + " "; //$NON-NLS-1$
    
      if ( null != strJobs ) {
        strJobs = strJobs.trim();
    
        this.connection.execCommand( this.pbsPath + cmd + " " + strJobs ); //$NON-NLS-1$
      }
    }
  }

  /**
   * Puts a hold on a job in the queue of the batch service.
   *
   * @param jobId The identifier of the job to be held.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void holdJob( final String jobId ) throws ProblemException {
    if ( null != jobId ) {
      String cmd = "qhold " + jobId; //$NON-NLS-1$

      this.connection.execCommand( this.pbsPath + cmd );
    }
  }
  
  /**
   * Puts a hold on one or more jobs in the queue of the batch service.
   *
   * @param jobIds The identifiers of the jobs to be held.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void holdJobs( final String[] jobIds ) throws ProblemException {
    String strJobs = null;
    String cmd = "qhold "; //$NON-NLS-1$

    // Add the jobIds 
    for ( String str : jobIds )
      strJobs = str + " "; //$NON-NLS-1$
  
    if ( null != strJobs ) {
      strJobs = strJobs.trim();
  
      this.connection.execCommand( this.pbsPath + cmd + " " + strJobs ); //$NON-NLS-1$
    }
  }
  
  /**
   * Release a job with a previous hold in queue of the batch system.
   *
   * @param jobId The identifier of the job to be released.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void releaseJob( final String jobId ) throws ProblemException {
    if ( null != jobId ) {
      String cmd = "qrls " + jobId; //$NON-NLS-1$

      this.connection.execCommand( this.pbsPath + cmd );
    }
  }

  /**
   * Release one or more jobs with a previous hold in queue of the batch system.
   *
   * @param jobIds The identifiers of the jobs to be released.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void releaseJobs( final String[] jobIds ) throws ProblemException {
    String strJobs = null;
    String cmd = "qrls "; //$NON-NLS-1$

    // Add the jobIds 
    for ( String str : jobIds )
      strJobs = str + " "; //$NON-NLS-1$
  
    if ( null != strJobs ) {
      strJobs = strJobs.trim();
  
      this.connection.execCommand( this.pbsPath + cmd + " " + strJobs ); //$NON-NLS-1$
    }
  }
  
  /**
   * Executes pbsnodes on the PBS server and returns a list of the workernodes as
   * {@link WorkerNodeInfo}. If no workernodes or error parsing the output then
   * this method will return <code>null</code>. The output of pbsnodes are as
   * follows:
   * wn201.grid.ucy.ac.cy
   * state = free
   * np = 1
   * properties = lcgpro
   * ntype = cluster
   * status = opsys=linux,uname=Linux wn201.grid.ucy.ac.cy 2.4.21-47.0.1.EL #1 Thu Oct 19 11:02:51 CDT 2006 i686,
   *          sessions=? 0,nsessions=? 0,nusers=0,idletime=1268300,totmem=1821164kb,availmem=1716700kb,
   *          physmem=768916kb,ncpus=1,loadave=0.00,netload=637790691,state=free,jobs=? 0,rectime=1180422371
   *
   * @return A {@link List} of {@link WorkerNodeInfo} or <code>null</code>.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized List<IWorkerNodeInfo> getWorkerNodes() throws ProblemException {
    List<IWorkerNodeInfo> wns = null;
    String outPut;

    outPut = this.connection.execCommand( this.pbsPath + "pbsnodes" ); //$NON-NLS-1$

    if ( null != outPut )
      wns = parseNodes ( outPut );

    return wns;
  }

  /**
   * Changes the state of a workernode to offline, no jobs will be placed on
   * this workernode.
   *
   * @param nodeId The identifier of the node to be disabled.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void disableWN( final String nodeId ) throws ProblemException {
    if ( null != nodeId ) {
      String cmd = "qmgr -c \"set node "; //$NON-NLS-1$
      cmd += nodeId;
      cmd += " state = offline\""; //$NON-NLS-1$

      this.connection.execCommand( this.pbsPath + cmd );
    }
  }

  /**
   * Changes the state of a workernode to free, jobs will be placed on
   * this workernode.
   *
   * @param nodeId The identifier of the node to be disabled.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void enableWN( final String nodeId ) throws ProblemException {
    if ( null != nodeId ) {
      String cmd = "qmgr -c \"set node "; //$NON-NLS-1$
      cmd += nodeId;
      cmd += " state = free\""; //$NON-NLS-1$

      this.connection.execCommand( this.pbsPath + cmd );
    }
  }

  /**
   * Executes qstat -q on the PBS server and returns a list of the queues as
   * {@link QueueInfo}. If no queues or error parsing the output then
   * this method will return <code>null</code>. The output of qstat -q are as
   * follows:
   * server: ce201.grid.ucy.ac.cy
   * Queue            Memory CPU Time Walltime Node  Run Que Lm  State
   * ---------------- ------ -------- -------- ----  --- --- --  -----
   * dteam              --   48:00:00 72:00:00   --    0   0 --   E R
   * see                --   48:00:00 72:00:00   --    0   0 --   E R
   * geclipse           --   48:00:00 72:00:00   --    0   0 --   E R
   * ops                --   48:00:00 72:00:00   --    0   0 --   E R
   *                                                ----- -----
   *                                                    0     0
   *
   * @return A {@link List} of {@link QueueInfo} or <code>null</code>.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized List<IQueueInfo> getQueues() throws ProblemException {
    String outPut;
    QueueInfo queueInfo;
    String line;
    List<IQueueInfo> queues = null;

    outPut = this.connection.execCommand( this.pbsPath + "qstat -q" ); //$NON-NLS-1$

    if ( null != outPut ) {
      queues = new ArrayList< IQueueInfo >();

      int endIndex, beginIndex = 0;
      int skip = 0;
      while ( -1 != ( endIndex = outPut.indexOf( '\n', beginIndex ) ) ) {
        line = outPut.substring( beginIndex, endIndex );

        // Skip first five lines
        if ( 5 < ++skip ) {
          queueInfo = parseQueueLine( line );
          if ( null != queueInfo )
            queues.add( queueInfo );
        }

        beginIndex = endIndex+1;
      }

      if ( queues.isEmpty() )
        queues = null;
    }

    return queues;
  }

  /**
   * Executes qdisable to disable a specific queue.
   *
   * @param queueId The identifier of the queue to be disable.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void disableQueue( final String queueId ) throws ProblemException {
    if ( null != queueId )
      this.connection.execCommand( this.pbsPath + "qdisable "+ queueId ); //$NON-NLS-1$
  }

  /**
   * Executes qdisable to disable all specified queues.
   *
   * @param queueIds The identifiers of the queues to be disable.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void disableQueues( final String[] queueIds ) throws ProblemException {
    if ( null != queueIds ) {
      if ( 0 < queueIds.length ) { 
        String queues = ""; //$NON-NLS-1$

        for ( String str : queueIds )
          queues += str + " "; //$NON-NLS-1$

        this.connection.execCommand( this.pbsPath + "qdisable "+ queues ); //$NON-NLS-1$
      }
    }
  }

  /**
   * Executes qenable to enable a specific queue.
   *
   * @param queueId The identifier of the queue to be enabled.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void enableQueue( final String queueId ) throws ProblemException {
    if ( null != queueId )
      this.connection.execCommand( this.pbsPath + "qenable "+ queueId ); //$NON-NLS-1$
  }

  /**
   * Executes qenable to enable all specified queues.
   *
   * @param queueIds The identifiers of the queues to be enabled.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void enableQueues( final String[] queueIds ) throws ProblemException {
    if ( null != queueIds ) {
      if ( 0 < queueIds.length ) { 
        String queues = ""; //$NON-NLS-1$

        for ( String str : queueIds )
          queues += str + " "; //$NON-NLS-1$

        this.connection.execCommand( this.pbsPath + "qenable "+ queues ); //$NON-NLS-1$
      }
    }
  }

  /**
   * Executes qstart to start a specific queue.
   *
   * @param queueId The identifier of the queue to be started.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void startQueue( final String queueId ) throws ProblemException {
    if ( null != queueId )
      this.connection.execCommand( this.pbsPath + "qstart "+ queueId ); //$NON-NLS-1$
  }

  /**
   * Executes qstart to start all specified queues.
   *
   * @param queueIds The identifiers of the queues to be started.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void startQueues( final String[] queueIds ) throws ProblemException {
    if ( null != queueIds ) {
      if ( 0 < queueIds.length ) { 
        String queues = ""; //$NON-NLS-1$

        for ( String str : queueIds )
          queues += str + " "; //$NON-NLS-1$

        this.connection.execCommand( this.pbsPath + "qstart "+ queues ); //$NON-NLS-1$
      }
    }
  }
  
  /**
   * Executes qstop to stop a specific queue.
   *
   * @param queueId The identifier of the queue to be stopped.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void stopQueue( final String queueId ) throws ProblemException {
    if ( null != queueId )
      this.connection.execCommand( this.pbsPath + "qstop "+ queueId ); //$NON-NLS-1$
  }

  /**
   * Executes qstop to stop all specified queues.
   *
   * @param queueIds The identifiers of the queues to be stopped.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void stopQueues( final String[] queueIds ) throws ProblemException {
    if ( null != queueIds ) {
      if ( 0 < queueIds.length ) { 
        String queues = ""; //$NON-NLS-1$

        for ( String str : queueIds )
          queues += str + " "; //$NON-NLS-1$

        this.connection.execCommand( this.pbsPath + "qstop "+ queues ); //$NON-NLS-1$
      }
    }
  }

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
  public synchronized void createQueue( final String queueName, final QueueType type, 
                                           final boolean enabled, final double timeCPU, 
                                           final double timeWall, final List<String> vos ) throws ProblemException {

    if ( null != queueName && null != type && -1 != timeCPU && -1 != timeWall ) { 
     
      String adaptedTimeWall, adaptedTimeCPU  = null;
      double newTimeWallHours = (timeWall / 3600);
      double newTimeCPUHours = (timeCPU / 3600);
      
     
      
      adaptedTimeWall = String.format( "%02d:%02d:00", //$NON-NLS-1$
                                            Integer.valueOf( getHoursFromDouble( newTimeWallHours ) ),
                                            Integer.valueOf( getMinutesFromDouble( newTimeWallHours) ) );
      
   
      adaptedTimeCPU = String.format( "%02d:%02d:00", //$NON-NLS-1$
                                            Integer.valueOf( getHoursFromDouble( newTimeCPUHours ) ),
                                            Integer.valueOf( getMinutesFromDouble( newTimeCPUHours ) ) ); 
      
      
      String cmd = this.pbsPath + "qmgr -c \"create queue "; //$NON-NLS-1$
      String setAttr = " ; qmgr -c \"set queue " + queueName; //$NON-NLS-1$
    
      // Build up the command
      cmd += queueName;
      cmd += " queue_type=" + type.name() + "\""; //$NON-NLS-1$ //$NON-NLS-2$

      if ( !enabled )
        cmd += setAttr + " enabled=false\""; //$NON-NLS-1$

      cmd += setAttr + " resources_max.walltime=" + adaptedTimeWall + "\""; //$NON-NLS-1$ //$NON-NLS-2$
    
      cmd += setAttr + " resources_max.cput=" + adaptedTimeCPU + "\""; //$NON-NLS-1$ //$NON-NLS-2$

      // If access control on vos, add the allowed vos
      if ( null != vos ) {
        cmd += setAttr + " acl_group_enable=true\""; //$NON-NLS-1$

        for ( String str : vos ) {
          cmd += setAttr + " acl_groups= +" + str + "\"" ; //$NON-NLS-1$ //$NON-NLS-2$
        }
      }
    
      // Try to create the queue on the batch service
      this.connection.execCommand( this.pbsPath + cmd );
    }  
  }
    
  /**
   * Executes command that will create a new queue with a "default" number of argument.
   *
   * @param queueName The name of the new queue.
   * @param priority The priority of the new queue.
   * @param type The type of the new queue.
   * @param enabled The state of the new queue.
   * @param started If the new queue will be started when created.
   * @param maxRunningJobs Maximum running jobs at any given time from the queue.
   * @param timeCPU Maximum allowed CPU time for any job. 
   * @param timeWall Maximum allowed wall time for any job.
   * @param maxJobsInQueue Maximum allowed jobs in the queue
   * @param assignedResources 
   * @param vos Only allow access to the specified vos, <code>null</code> no restriction is applied.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void createQueue( final String queueName, final int priority, final QueueType type, 
                                           final boolean enabled, final boolean started, final int maxRunningJobs,
                                           final double timeCPU, final double timeWall, final int maxJobsInQueue,
                                           final int assignedResources, final List<String> vos )
                                      throws ProblemException {

    if ( null != queueName && null != type && -1 != timeCPU && -1 != timeWall ) { 
      
      String adaptedTimeWall, adaptedTimeCPU  = null;
//      double newTimeWallHours = (timeWall / 3600);
//      double newTimeCPUHours = (timeCPU / 3600);
      
      adaptedTimeWall = String.format( "%02d:%02d:00", //$NON-NLS-1$
                                       Integer.valueOf( getHoursFromDouble( timeWall ) ),
                                       Integer.valueOf( getMinutesFromDouble( timeWall) ) );
 

      adaptedTimeCPU = String.format( "%02d:%02d:00", //$NON-NLS-1$
                                       Integer.valueOf( getHoursFromDouble( timeCPU ) ),
                                       Integer.valueOf( getMinutesFromDouble( timeCPU ) ) );
      
      
      String cmd = this.pbsPath + "qmgr -c \"create queue "; //$NON-NLS-1$
      String setAttr = " ; qmgr -c \"set queue " + queueName; //$NON-NLS-1$

      
      // Build up the command
      cmd += queueName;
      cmd += " queue_type=" + type.name() + "\""; //$NON-NLS-1$ //$NON-NLS-2$

      
      if ( !enabled ){
        cmd += setAttr + " enabled = False\""; //$NON-NLS-1$
      }else {
        cmd += setAttr + " enabled = True\""; //$NON-NLS-1$
      }
      
      if (!started ){
        cmd += setAttr + " started = False\""; //$NON-NLS-1$
      }else {
        cmd += setAttr + " started = True\""; //$NON-NLS-1$
      }

      if ( -1 != priority ) { 
        cmd += setAttr + " priority=" + priority + "\""; //$NON-NLS-1$ //$NON-NLS-2$
      }
      
      cmd += setAttr + " resources_max.walltime=" + adaptedTimeWall + "\""; //$NON-NLS-1$ //$NON-NLS-2$
    
      cmd += setAttr + " resources_max.cput=" + adaptedTimeCPU + "\""; //$NON-NLS-1$ //$NON-NLS-2$

      if ( -1 != maxRunningJobs ){
        cmd += setAttr + " max_running=" + maxRunningJobs + "\""; //$NON-NLS-1$ //$NON-NLS-2$
      }
      
      if ( -1 != maxJobsInQueue ){
        cmd += setAttr + " max_queuable=" + maxJobsInQueue + "\""; //$NON-NLS-1$ //$NON-NLS-2$
      }

      if ( -1 !=assignedResources ){
        cmd += setAttr + " resources_assigned.nodect=" + assignedResources+"\""; //$NON-NLS-1$ //$NON-NLS-2$
      }
      
      // If access control on vos, add the allowed vos
      if ( null != vos ) {
        cmd += setAttr + " acl_group_enable=true\""; //$NON-NLS-1$

        for ( String str : vos ) {
          cmd += setAttr + " acl_groups= +" + str + "\"" ; //$NON-NLS-1$ //$NON-NLS-2$
        }
      }
    
      // Try to create the queue on the batch service
      this.connection.execCommand( this.pbsPath + cmd );
    }
  }
  
 
  /**
   * Executes command that will change the maximum allowed wall time of a specific queue.
   *
   * @param queueName The name of the queue to be modified.
   * @param timeWall The new maximum allowed wall time for any job.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void setMaxWallTime( final String queueName, final String timeWall ) throws ProblemException {
    if ( null != queueName && null != timeWall ) {
      String cmd = this.pbsPath + "qmgr -c \"set queue " + queueName; //$NON-NLS-1$

      cmd += " resources_max.walltime=" + timeWall; //$NON-NLS-1$

      // Terminate the command
      cmd += "\""; //$NON-NLS-1$
    
      this.connection.execCommand( this.pbsPath + cmd );
    }
  }
  
  /**
   * Deletes the specified queue.
   *
   * @param queueId The identifier of the queue to be deleted.
   * @throws ProblemException If command is not executed successfully
   */
  public synchronized void delQueue( final String queueId ) throws ProblemException {
    if ( null != queueId ) {
      this.connection.execCommand( this.pbsPath 
                                              + "qmgr -c \"delete queue "  //$NON-NLS-1$
                                              + queueId + "\"" ); //$NON-NLS-1$
    }
  }


  public void createQueue( final DocumentRoot documentRoot ) throws ProblemException {
    
    eu.geclipse.batch.model.qdl.QueueType queue;
    QueueType type;
    String queueName = null;
    boolean queueStatus = false;
    boolean queueStarted = false;
    double timeCPU;
    double timeWall;
    int priority = -1;
    int maxRunningJobs = -1;
    int maxJobsInQueue = -1;
    int assignedResources = -1;
    List<String> vos = null;
    
    
    queue = documentRoot.getQueue();
    
    /* Get Queue Type ( EXECUTION || ROUTE ) */
    if(queue.getQueueType().equals( QueueTypeEnumeration.EXECUTION )){
      type = IQueueInfo.QueueType.execution;
    }
    else {
      type = IQueueInfo.QueueType.route;
    }
    
    /* Get Queue Name */
    queueName = queue.getQueueName();
    
    /* Get Queue Status ( ENABLED || DISABLED )*/
    if(queue.getQueueStatus().equals( QueueStatusEnumeration.ENABLED )){
      queueStatus = true;
    }   
    
    /* Get Queue Started ( TRUE || FALSE )*/
    if( queue.isQueueStarted() == true ){
      queueStarted = true;
    }
    
    
    timeCPU = queue.getCPUTimeLimit().getUpperBoundedRange().getValue();
        
    /* Get the list of VOs that are allowed to use the queue */ 
    if ( queue.getAllowedVirtualOrganizations() != null ) {
      vos = queue.getAllowedVirtualOrganizations().getVOName();
    }
    
    timeWall = queue.getWallTimeLimit().getUpperBoundedRange().getValue();
   
    /* Get QUEUE__TYPE_PRIORITY */
    if ( null != queue.getPriority() ) {
      if (queue.getPriority().getUpperBoundedRange() != null) {
        priority = queue.getPriority().getUpperBoundedRange().getValue();
      }
      else {
        priority = queue.getPriority().getLowerBoundedRange().getValue();
      }
    }
    
    /* Get QUEUE__TYPE_RUNNING_JOBS */    
    if ( null != queue.getRunningJobs() ) {
      if (queue.getRunningJobs().getUpperBoundedRange() != null) {
        maxRunningJobs = queue.getRunningJobs().getUpperBoundedRange().getValue();
      }
      else {
        maxRunningJobs = queue.getRunningJobs().getLowerBoundedRange().getValue();
      }
    }
    
    /* Get QUEUE__TYPE_JOBS_IN_QUEUE */
    if ( null != queue.getJobsInQueue() ) {
      if (queue.getJobsInQueue().getUpperBoundedRange() != null) {
        maxJobsInQueue = queue.getJobsInQueue().getUpperBoundedRange().getValue();
      }
      else {
        maxJobsInQueue = queue.getJobsInQueue().getLowerBoundedRange().getValue();
      }
    }
    
    /* Get QUEUE__TYPE_JOBS_ASSIGNED_RESOURCES */
    if ( null != queue.getAssignedResources() ) {
      if (queue.getAssignedResources().getExact() != null) {
        maxJobsInQueue = queue.getJobsInQueue().getUpperBoundedRange().getValue();
      }
    }

//    createQueue( queueName, type, queueStatus, timeCPU, timeWall, vos );
    createQueue( queueName, priority,
                 type, queueStatus, queueStarted,
                 maxRunningJobs, timeCPU,
                 timeWall, maxJobsInQueue,
                 assignedResources, vos);
            
  }
  
  
  static int getHoursFromDouble( final double value ) {
    
    BigDecimal b = new BigDecimal( value/3600 );
    double c = b.longValue();    
    return (int) c;
    
  }
  
  
  static int getMinutesFromDouble( final double value ) {
    
    int result;
    
    result = (int)((value %3600)/60);
     
   return result;
  }
  
}


