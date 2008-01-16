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
package eu.geclipse.batch.ui.internal;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.widgets.Shell;

import eu.geclipse.batch.BatchException;
import eu.geclipse.batch.IBatchService;
import eu.geclipse.batch.IBatchJobInfo;
import eu.geclipse.batch.IQueueInfo;
import eu.geclipse.batch.IWorkerNodeInfo;
import eu.geclipse.batch.ui.internal.model.BatchDiagram;
import eu.geclipse.batch.ui.internal.model.BatchResource;
import eu.geclipse.batch.ui.internal.model.ComputingElement;
import eu.geclipse.batch.ui.internal.model.Connection;
import eu.geclipse.batch.ui.internal.model.Queue;
import eu.geclipse.batch.ui.internal.model.WorkerNode;
import eu.geclipse.batch.ui.dialogs.*;

/**
 * Class that runs a job to synchronize the current view of the batch service
 * with the actual state of the batch service.
 */
public class BatchUpdate {
  protected Job updateJob;

  private ScheduledExecutorService executor;
  private Runnable updateRunnable;
  private int updateInterval;
  private IBatchService batchWrapper;

  private BatchDiagram diagram;
  private String batchName;
  private String batchType;
  private ComputingElement computingElement;
  private LinkedHashMap<String, WorkerNode> workerNodes = new LinkedHashMap<String, WorkerNode>();
  private LinkedHashMap<String, Queue> queues = new LinkedHashMap<String, Queue>();
  private List<BatchResource> newResources = new Vector<BatchResource>();
  private List<BatchResource> removedResources = new Vector<BatchResource>();
  private boolean firstTime;
  private ProgressDialog initProgress; 
  
  /**
   * Default constructor.
   * @param shell The parent shell
   * @param diagram Reference to the diagram that holds the figures.
   * @param batchWrapper Interface to the batch service.
   * @param batchName The name FQDN of the machine hosting the batch service
   * @param batchType The type of the batch service.
   * @param updateInterval The interval between each update.
   */
  public BatchUpdate( final Shell shell, final BatchDiagram diagram, final IBatchService batchWrapper, 
                      final String batchName, final String batchType, final int updateInterval) {
    this.diagram = diagram;
    this.batchWrapper = batchWrapper;
    this.batchName = batchName;
    this.batchType = batchType;
    this.updateInterval = updateInterval;
    this.updateRunnable = null;
    this.updateJob = null;
    this.executor = Executors.newSingleThreadScheduledExecutor();
    
    this.firstTime = true;
    this.initProgress = new ProgressDialog( shell );
    
    String[] description = new String[] { "Worker Node",  //$NON-NLS-1$
                                          "Queues",  //$NON-NLS-1$
                                          "Computing Element",  //$NON-NLS-1$
                                          "Connections" }; //$NON-NLS-1$
    this.initProgress.initInformation( 4, description, Messages.getString( "BatchUpdate.ProgressDialogTitle" ) ); //$NON-NLS-1$
  }

  /**
   * Updates (synchronizes the Batch service with the viewed Figure) the attributes of the Figure.
   * IMPORTANT: Pay attention to the semantic of the return part
   * @param wni The current state of the worker node at the Batch service.
   * @return Returns <code>null</code> if the worker node is merged. A
   * <code>WorkerNode</code> if the worker node was unknown, in this case it is added to the view.
   */
  private WorkerNode updateWN( final IWorkerNodeInfo wni ) {
    WorkerNode wn;
    String name;

    name = wni.getWnFQN();
    wn = this.workerNodes.get( name );

    //  Check if any of the fields have been modified
    if ( null != wn ) {
      wn.updateState( wni );
      wn = null;
    }
    else {
      final Dimension dimWN = new Dimension( 100, 40 );

      wn = new WorkerNode();

      wn.setFQDN( wni.getWnFQN() );
      wn.setKernelVersion( wni.getKernelVersion() );
      wn.setNumProcessors( wni.getNp() );
      wn.setTotalMem( wni.getTotalMem() );
      wn.setState( wni.getState() );
      wn.setJobIds( wni.getJobs() );
      wn.setSize( dimWN );
      this.workerNodes.put( wni.getWnFQN(), wn );
    }

    return wn;
  }

  /**
   * Updates (synchronizes the Batch service with the viewed Figure) the attributes of the Figure.
   * IMPORTANT: Pay attention to the semantic of the return part
   * @param queuei The current state of the queue at the Batch service.
   * @param jobis All the job in the batch service.
   * @return Returns <code>null</code> if the queue is merged. A
   * <code>Queue</code> if the queue was unknown, in this case it is added to the view.
   */
  private Queue updateQueue( final IQueueInfo queuei, final List<IBatchJobInfo> jobis ) {
    Queue queue;
    String name;

    name = queuei.getQueueName();
    queue = this.queues.get( name );

    //  Check if any of the fields have been modified
    if ( null != queue ) {
      queue.updateState( queuei );

      // Give the queue a reference to all the jobs
      queue.setJobs( jobis );

      queue = null;
    }
    else {
      final Dimension dimQ = new Dimension( 100, 55 );

      queue = new Queue();

      queue.setQueueName( queuei.getQueueName() );
      queue.setState( queuei.getState() );
      queue.setRunState ( queuei.getRunState() );
      queue.setMemory( queuei.getMemory() );
      queue.setTimeCPU( queuei.getTimeCPU() );
      queue.setTimeWall( queuei.getTimeWall() );
      queue.setRun( queuei.getRun() );
      queue.setLm( queuei.getLm() );
      queue.setQue( queuei.getQue() );
      queue.setNode( queuei.getNode() );

      queue.setSize( dimQ );

      // Give the queue a reference to all the jobs
      queue.setJobs( jobis );

      this.queues.put( queuei.getQueueName(), queue );
    }

    return queue;
  }

  /**
   * Stops this execution service (thread) from periodically updating the
   * state of the Batch service.
   */
  public synchronized void stopUpdate() {
    if ( null != this.updateJob ) {
      this.executor.shutdownNow();
      this.updateJob.cancel();
    }
  }

  /**
   * Starts the execution of the update "thread" that will run at the specified interval.
   */
  public synchronized void startUpdate() {
    if ( null == this.updateJob ) {

      // Draw the update dialog
      this.initProgress.open();
      this.updateJob = new Job ( this.batchName ) {
        @Override
        protected IStatus run( final IProgressMonitor monitor )
        {
          Status status = null;

          try {
            drawBatchInfo();
            status = new Status( IStatus.OK, Activator.PLUGIN_ID, 
                                 Messages.getString( "BatchUpdate.Ok.StatusUpdate" ) );  //$NON-NLS-1$
          } catch ( Exception exc ) {
            Activator.logException( exc );
            status = new Status( IStatus.ERROR, Activator.PLUGIN_ID, 
                                 Messages.getString( "BatchUpdate.Error.StatusUpdate" ) );  //$NON-NLS-1$
          }

          return status;
        }
      };

      this.updateRunnable = new Runnable() {
        public void run() {
          if ( Job.NONE == BatchUpdate.this.updateJob.getState() ){
            BatchUpdate.this.updateJob.schedule();
          }
        }
      };

      // The execution starts
      this.executor.scheduleWithFixedDelay( this.updateRunnable, 3, this.updateInterval, TimeUnit.SECONDS );
    }
  }

  /**
   * Merges what is currently displayed with the current state of the batch
   * service. If a component doesn't exist, them it is created.
   */
  protected void drawBatchInfo() {
    Queue queue;
    IQueueInfo queuei;
    List<IQueueInfo> queueis = null;
    WorkerNode wn;
    IWorkerNodeInfo wni;
    List<IWorkerNodeInfo> wnis = null;
    List<IBatchJobInfo> jobis = null;
    Dimension dimCE;
    Point pointCE, pointWN, pointQ;

    // Get the computing element
    dimCE = new Dimension(140, 90);

    pointCE = new Point(200, 100);
    pointWN = new Point(400, 10);
    pointQ = new Point(10, 10);

    // Do the worker nodes
    try {
      wnis = this.batchWrapper.getWorkerNodes();
    } catch( BatchException exc ) {
      Activator.logException( exc );
    }

    if ( null != wnis ) {
      if ( this.firstTime )
        this.initProgress.moveNextMajorTask( wnis.size() );
     
      for ( int i = 0; i < wnis.size(); ++i ) {
        wni = wnis.get( i );

        wn = this.updateWN( wni );
        if ( null != wn ) {
          int loc = 10+(45*(i+1));
          pointWN = pointWN.setLocation( 400, loc );
          wn.setLocation( pointWN );

          this.diagram.addChild( wn );
          this.newResources.add( wn );

          if ( this.firstTime )
            this.initProgress.moveNextMinor();
        }
      }
    }

    try {
      // Do the jobs
      jobis = this.batchWrapper.getJobs();

      // Do the queues
      queueis = this.batchWrapper.getQueues();
    } catch( BatchException exc ) {
      Activator.logException( exc );
    }

    if ( null != queueis ) {
      if ( this.firstTime )
        this.initProgress.moveNextMajorTask( queueis.size() );

      for ( int i = 0; i < queueis.size(); ++i ) {
        queuei = queueis.get( i );

        queue = this.updateQueue( queuei, jobis );
        if ( null != queue ) {
          int loc = 10+(60*(i+1));
          pointQ = pointQ.setLocation( 10, loc );

          queue.setLocation( pointQ );

          this.diagram.addChild( queue );
          this.newResources.add( queue );

          if ( this.firstTime )
            this.initProgress.moveNextMinor();
        }
      }

      // Check if any of the queues were removed 
      Iterator< String > iter = this.queues.keySet().iterator();
      
      while ( iter.hasNext() ) {
        String queueName = iter.next();
        boolean found = false;
        for ( int i = 0; i < queueis.size(); ++i ) {
          queuei = queueis.get( i );
          if ( queueName.equals( queuei.getQueueName() ) )
            found = true;
        }
        
        // Queue is removed
        if ( !found ) {
          this.removedResources.add( this.queues.get( queueName ) );
        }
      }
    }
    
    // Create the ce
    if ( null == this.computingElement ) {
      if ( this.firstTime )
        this.initProgress.moveNextMajorTask( 1 );

      this.computingElement = new ComputingElement( );
      this.computingElement.setSize( dimCE );
      this.computingElement.setFQDN( this.batchName );
      this.computingElement.setType( this.batchType );

      // Set the location
      int loc = 300;
      if ( null != queueis && null != wnis ) {
        if ( queueis.size() > wnis.size() )
          loc = 10+(45*((queueis.size() / 2))) - 45/2 - 5;
        else
          loc = 10+(45*((wnis.size() / 2))) - 45/2 - 5;
      }
      pointCE = pointCE.setLocation( 200, loc );

      this.computingElement.setLocation( pointCE );
      this.diagram.addChild( this.computingElement );

      if ( this.firstTime )
        this.initProgress.moveNextMinor();
    }

    // See if the dynamic information about the ce have changed
    if ( null != queueis && queueis.size() != this.computingElement.getNumQueues() )
      this.computingElement.setNumQueues( queueis.size() );
    else if ( null == queueis && 0 < this.computingElement.getNumQueues() )
      this.computingElement.setNumQueues( 0 );

    if ( null != wnis && wnis.size() != this.computingElement.getNumWNs() )
      this.computingElement.setNumWNs( wnis.size() );
    else if ( null == wnis && 0 < this.computingElement.getNumWNs() )
      this.computingElement.setNumWNs( 0 );

    // Set the jobs
    this.computingElement.setJobs( jobis );

    // Only draw the line on the next iteration
    if ( ! this.newResources.isEmpty() ) {
      
      if ( this.firstTime )
        this.initProgress.moveNextMajorTask( this.newResources.size() );

      // Set the connections of the newly added BatchResources
      for ( BatchResource resource : this.newResources ) {
        Connection conn = null;
        if ( resource instanceof WorkerNode ) {
          conn = new Connection( this.computingElement, resource, Connection.SOLID_CONNECTION);
          conn.reconnect();
        } else if ( resource instanceof Queue ) {
          conn = new Connection( resource, this.computingElement, Connection.DASHED_CONNECTION);
          conn.reconnect();
        } else
          assert false;

        if ( this.firstTime )
          this.initProgress.moveNextMinor();
      }

      // Clear the added resources
      this.newResources.clear();
    }
    
    // Take care of the removed entities
    if ( ! this.removedResources.isEmpty() ) {
      for ( BatchResource resource : this.removedResources ) {
        // Remove the common elements
        List<Connection> conns = resource.getSourceConnections();
        for ( Connection conn : conns) {
          resource.removeConnection( conn );
          conn.disconnect();
        }
        conns = resource.getTargetConnections();
        
        for ( Connection conn : conns) {
          resource.removeConnection( conn );
          conn.disconnect();
        }
        
        // Remove the resource itself
        this.diagram.removeChild( resource );

        // Remove the specifics 
        if ( resource instanceof WorkerNode ) {
          // No nothing yet
        } else if ( resource instanceof Queue ) {
          Queue q = ( Queue )resource;
          
          this.queues.remove( q.getQueneName() );

        } else
          assert false;
      }
      
      this.removedResources.clear();
    }
    
    // Only show the progress bar the first time
    if ( this.firstTime ) {
      this.initProgress.close();
      this.firstTime = false;
    }
  }
  
}
