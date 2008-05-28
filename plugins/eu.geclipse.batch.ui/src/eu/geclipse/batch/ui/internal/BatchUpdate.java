/*******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium All rights reserved. This program and
 * the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Initial development of the original
 * code was made for project g-Eclipse founded by European Union project number:
 * FP6-IST-034327 http://www.geclipse.eu/ Contributor(s): UCY
 * (http://www.cs.ucy.ac.cy) - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 ******************************************************************************/
package eu.geclipse.batch.ui.internal;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
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
import eu.geclipse.batch.IBatchService;
import eu.geclipse.batch.IBatchJobInfo;
import eu.geclipse.batch.IQueueInfo;
import eu.geclipse.batch.IWorkerNodeInfo;
import eu.geclipse.batch.ui.internal.model.BatchDiagram;
import eu.geclipse.batch.ui.internal.model.BatchResource;
import eu.geclipse.batch.ui.internal.model.ComputingElement;
import eu.geclipse.batch.ui.internal.model.Box;
import java.util.Collections;
import eu.geclipse.batch.ui.internal.model.Connection;
import eu.geclipse.batch.ui.internal.model.Queue;
import eu.geclipse.batch.ui.internal.model.WorkerNode;
import eu.geclipse.batch.ui.dialogs.*;
import eu.geclipse.batch.ui.editors.BatchEditor;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Class that runs a job to synchronize the current view of the batch service
 * with the actual state of the batch service.
 */
public class BatchUpdate {

  protected Job updateJob;
  private final int nMaxElements = 6; // how many queues or nodes to draw at each line
  
  private List<BatchResource> Qlist = new ArrayList<BatchResource>();
  private List<BatchResource> Nlist = new ArrayList<BatchResource>();
  private ScheduledExecutorService executor;
  private Runnable updateRunnable;
  private int updateInterval;
  private IBatchService batchWrapper;
  private BatchDiagram diagram;
  private String batchName;
  private String batchType;
  private BatchJobManager jobManager;
  private ComputingElement computingElement;
  private Box box_queue;
  private Box box_nodes;
  private LinkedHashMap<String, WorkerNode> workerNodes = new LinkedHashMap<String, WorkerNode>();
  private LinkedHashMap<String, Queue> queues = new LinkedHashMap<String, Queue>();
  private List<BatchResource> removedResources = new ArrayList<BatchResource>();
  private boolean firstTime;
  private ProgressDialog initProgress;
  private int maxX = 0;
  private int maxY = 0;
  private int count = 0;
  private int EnableQ = 0;
  private int DisabledQ = 0;
  private int freeN = 0;
  private int job_exclusiveN = 0;
  private int busyN = 0;
  private int downN = 0;
  private BatchEditor editor;
  private int[] queue_dim = { // queue_dim[0] keeps the first x coordinate of
                              // the fist child
    -1, -1, -1
  // queue_dim[1] keeps the max x coordinate
  }; // queue_dim[2] keeps the max y coordinate
  private int[] nodes_dim = {
    -1, -1, -1
  // nodes_dim[0] keeps the first x coordinate of the fist child
  }; // nodes_dim[1] keeps the max x coordinate

  // nodes_dim[2] keeps the max y coordinate
  /**
   * Default constructor.
   * 
   * @param shell The parent shell
   * @param diagram Reference to the diagram that holds the figures.
   * @param batchWrapper Interface to the batch service.
   * @param batchName The name FQDN of the machine hosting the batch service
   * @param batchType The type of the batch service.
   * @param updateInterval The interval between each update.
   * @param editor The editor that connects the sorting order
   */
  public BatchUpdate( final Shell shell,
                      final BatchDiagram diagram,
                      final IBatchService batchWrapper,
                      final String batchName,
                      final String batchType,
                      final int updateInterval,
                      final BatchEditor editor )
  {
    this.diagram = diagram;
    this.batchWrapper = batchWrapper;
    this.batchName = batchName;
    this.batchType = batchType;
    this.updateInterval = updateInterval;
    this.updateRunnable = null;
    this.updateJob = null;
    this.jobManager = new BatchJobManager();
    this.executor = Executors.newSingleThreadScheduledExecutor();
    this.firstTime = true;
    this.editor = editor;
    this.initProgress = new ProgressDialog( shell );
    String[] description = new String[]{
      "Worker Nodes", //$NON-NLS-1$
      "Queues", //$NON-NLS-1$
      "Sort Queue", //$NON-NLS-1$
      "Computing Element", //$NON-NLS-1$
      "Sort Worker Nodes", //$NON-NLS-1$
      "Connections"}; //$NON-NLS-1$
    this.initProgress.initInformation( 6,
                                       description,
                                       Messages.getString( "BatchUpdate.ProgressDialogTitle" ) ); //$NON-NLS-1$
  }

  /**
   * Updates (synchronizes the Batch service with the viewed Figure) the
   * attributes of the Figure. IMPORTANT: Pay attention to the semantic of the
   * return part
   * 
   * @param wni The current state of the worker node at the Batch service.
   * @return Returns <code>null</code> if the worker node is merged. A
   *         <code>WorkerNode</code> if the worker node was unknown, in this
   *         case it is added to the view.
   */
  private WorkerNode updateWN( final IWorkerNodeInfo wni ) {
    WorkerNode wn;
    String name;
    name = wni.getWnFQN();
    wn = this.workerNodes.get( name );
    // Check if any of the fields have been modified
    if( null != wn ) {
      wn.updateState( wni );
      wn = null;
    } else {
      final Dimension dimWN = new Dimension( 100, 40 );
      wn = new WorkerNode( this.jobManager, this.editor );
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
   * Updates (synchronizes the Batch service with the viewed Figure) the
   * attributes of the Figure. IMPORTANT: Pay attention to the semantic of the
   * return part
   * 
   * @param queuei The current state of the queue at the Batch service.
   * @param jobis All the job in the batch service.
   * @return Returns <code>null</code> if the queue is merged. A
   *         <code>Queue</code> if the queue was unknown, in this case it is
   *         added to the view.
   */
  private Queue updateQueue( final IQueueInfo queuei,
                             final List<IBatchJobInfo> jobis )
  {
    Queue queue;
    String name;
    name = queuei.getQueueName();
    queue = this.queues.get( name );
    // Check if any of the fields have been modified
    if( null != queue ) {
      queue.updateState( queuei );
      queue = null;
    } else {
      final Dimension dimQ = new Dimension( 100, 55 );//
      queue = new Queue( this.jobManager, this.editor );
      queue.setQueueName( queuei.getQueueName() );
      queue.setState( queuei.getState() );
      queue.setRunState( queuei.getRunState() );
      queue.setMemory( queuei.getMemory() );
      queue.setTimeCPU( queuei.getTimeCPU() );
      queue.setTimeWall( queuei.getTimeWall() );
      queue.setRun( queuei.getRun() );
      queue.setLm( queuei.getLm() );
      queue.setQue( queuei.getQue() );
      queue.setNode( queuei.getNode() );
      queue.setSize( dimQ );
      this.queues.put( queuei.getQueueName(), queue );
    }
    return queue;
  }

  /**
   * Stops this execution service (thread) from periodically updating the state
   * of the Batch service.
   */
  public synchronized void stopUpdate() {
    if( null != this.updateJob ) {
      this.executor.shutdownNow();
      this.updateJob.cancel();
    }
    if( null != this.jobManager ) {
      this.jobManager.removeAll();
    }
  }

  /**
   * Starts the execution of the update "thread" that will run at the specified
   * interval.
   */
  public synchronized void startUpdate() {
    if( null == this.updateJob ) {
      // Draw the update dialog
      this.initProgress.open();
      this.updateJob = new Job( this.batchName ) {

        @Override
        protected IStatus run( final IProgressMonitor monitor ) {
          Status status = null;
          try {
            drawBatchInfo();
            status = new Status( IStatus.OK,
                                 Activator.PLUGIN_ID,
                                 Messages.getString( "BatchUpdate.Ok.StatusUpdate" ) ); //$NON-NLS-1$
          } catch( Exception exc ) {
            Activator.logException( exc );
            status = new Status( IStatus.ERROR,
                                 Activator.PLUGIN_ID,
                                 Messages.getString( "BatchUpdate.Error.StatusUpdate" ) ); //$NON-NLS-1$
          }
          return status;
        }
      };
      this.updateRunnable = new Runnable() {

        public void run() {
          if( Job.NONE == BatchUpdate.this.updateJob.getState() ) {
            BatchUpdate.this.updateJob.schedule();
          }
        }
      };
      // The execution starts
      this.executor.scheduleWithFixedDelay( this.updateRunnable,
                                            3,
                                            this.updateInterval,
                                            TimeUnit.SECONDS );
    }
  }

  /**
   * Merges what is currently displayed with the current state of the batch
   * service. If a component doesn't exist, them it is created.
   */
  public void drawBatchInfo() {
    Queue queue;
    IQueueInfo queuei;
    List<IQueueInfo> queueis = null;
    WorkerNode wn;
    IWorkerNodeInfo wni;
    List<IWorkerNodeInfo> wnis = null;
    List<BatchResource> newReses = null;
    List<IBatchJobInfo> jobis = null;
    Dimension dimCE;
    Point pointCE, pointWN, pointQ;
    Dimension dimBox_queue = null;
    Dimension dimBox_nodes = null;
    Point pointBox_queue, pointBox_nodes;
    dimCE = new Dimension( 140, 110 );
    pointBox_queue = new Point( 100, 55 );
    pointBox_nodes = new Point( 100, 400 );
    pointCE = new Point( 200, 100 );
    pointWN = new Point( 400, 10 );
    pointQ = new Point( 100, 1000 );
    // Do the worker nodes
    try {
      wnis = this.batchWrapper.getWorkerNodes();
    } catch( ProblemException exc ) {
      Activator.logException( exc );
    }
    if( null != wnis ) {
      if( this.firstTime )
        this.initProgress.moveNextMajorTask( wnis.size() );
      int loc_y = 0;
      int newfreeN = 0;
      int newjob_exclusiveN = 0;
      int newbusyN = 0;
      int newdownN = 0;
      boolean changeN = false;
      boolean flag_notes = true;
      int j = 0;
      for( int i = 0; i < wnis.size(); ++i ) {
        wni = wnis.get( i );
        wn = this.updateWN( wni );
        // checking if some of the nodes have changed their state
        if( wni.getState().toString().equals( "free" ) ) //$NON-NLS-1$
        {
          newfreeN++;
        } else if( wni.getState().toString().equals( "job_exclusive" ) ) //$NON-NLS-1$
        {
          newjob_exclusiveN++;
        } else if( wni.getState().toString().equals( "busy" ) ) //$NON-NLS-1$
        {
          newbusyN++;
        } else {
          newdownN++;
        }
        if( this.firstTime ) {
          this.freeN = newfreeN;
          this.job_exclusiveN = newjob_exclusiveN;
          this.busyN = newbusyN;
          this.downN = newdownN;
        }
        if( ( this.freeN != newfreeN
              || this.job_exclusiveN != newjob_exclusiveN
              || this.busyN != newbusyN || this.downN != newdownN )
            && i == wnis.size() - 1 )
        {
          changeN = true;
          this.freeN = newfreeN;
          this.job_exclusiveN = newjob_exclusiveN;
          this.busyN = newbusyN;
          this.downN = newdownN;
        }
        // drawing algorithm
        if( null != wn ) {
          int loc = 10 + ( 120 * ( j ) );
          j++;
          if( i == 0 )
            this.nodes_dim[ 0 ] = loc;
          if( i % this.nMaxElements == 0 && i > 0 ) {
            loc_y = loc_y + 45;
            loc = this.nodes_dim[ 0 ];
            this.nodes_dim[ 2 ] = loc_y;
            j = 0;
          }
          if( i == this.nMaxElements - 1 ) {
            this.nodes_dim[ 1 ] = loc + 40;
            flag_notes = false;
          }
          if( i == wnis.size() - 1 && flag_notes ) {
            this.nodes_dim[ 1 ] = loc + 100;
          }
          pointWN = pointWN.setLocation( loc, loc_y );
          wn.setLocation( pointWN );
          this.Nlist.add( wn );
          if( this.firstTime )
            this.initProgress.moveNextMinor();
        }
      }
      // Sorting because at least one node changed its state
      if( changeN && this.editor.workerNodeByName != 1 ) {
        this.editor.sortedN = 2;
        this.editor.workerNodeByState = 2;
        changeN = false;
      }
    }
    try {
      // Do the jobs
      this.batchWrapper.getJobs( this.jobManager );
      // Do the queues
      queueis = this.batchWrapper.getQueues();
    } catch( ProblemException exc ) {
      Activator.logException( exc );
    }
    int loc_y = 0;
    int newenabledQ = 0;
    int newdisabledQ = 0;
    boolean changeQ = false;
    if( null != queueis ) {
      if( this.firstTime )
        this.initProgress.moveNextMajorTask( queueis.size() );
      boolean flag_queue = true;
      int j = 0;
      boolean add = false;
      for( int i = 0; i < queueis.size(); ++i ) {
        queuei = queueis.get( i );
        queue = this.updateQueue( queuei, jobis );
        // checking if some of the queues have changed their state
        if( queuei.getState().toString().equals( "enabled" ) ) //$NON-NLS-1$ 
        {
          newenabledQ++;
        } else {
          newdisabledQ++;
        }
        if( this.firstTime ) {
          this.EnableQ = newenabledQ;
          this.DisabledQ = newdisabledQ;
        }
        if( ( this.EnableQ != newenabledQ || this.DisabledQ != newdisabledQ )
            && i == queueis.size() - 1 )
        {
          changeQ = true;
          this.EnableQ = newenabledQ;
          this.DisabledQ = newdisabledQ;
        }
        // drawing algorithm
        if( null != queue ) {
          this.count = queueis.size();
          if( !this.firstTime ) {
            add = true;
          }
        
          int loc_x = 50 + ( 120 * ( j ) );
          j++;
          if( i == 0 )
            this.queue_dim[ 0 ] = loc_x;
          if( i % this.nMaxElements == 0 && i > 0 ) {
            loc_y = loc_y + 70;
            loc_x = this.queue_dim[ 0 ];
            this.queue_dim[ 2 ] = loc_y;
            j = 0;
          }
          if( this.maxX < loc_x )
            this.maxX = loc_x;
          if( this.maxY <= loc_y )
            this.maxY = loc_y;
          if( i == this.nMaxElements - 1 ) {
            this.queue_dim[ 1 ] = loc_x + 50;
            flag_queue = false;
          }
          if( i == queueis.size() - 1 && flag_queue ) {
              this.queue_dim[ 1 ] = loc_x + 50;
          }
          pointQ = pointQ.setLocation( loc_x, loc_y );
          queue.setLocation( pointQ );
          this.Qlist.add( queue );
          if( this.firstTime )
            this.initProgress.moveNextMinor();
        }
      }
      boolean values = true;
      // Sorting because at least one node changed its state
      if( changeQ && this.editor.queueByName != 1 ) {
        this.editor.queueByState = 2;
        this.editor.sortedQ = 2;
        // this.Sort( this.editor.sortedQ, this.Qlist, this.box_queue, true );
        changeQ = false;
        values = false;
      }
      // case of add a new queue
      if( !this.firstTime ) {
        try {
          if( add ) {
            
           addQueue(values,dimBox_queue );
          }
        } catch( Exception Z ) {
          // No code needed 
        }
      }
      // Check if any of the queues were removed
      Iterator<String> iter = this.queues.keySet().iterator();
      while( iter.hasNext() ) {
        String queueName = iter.next();
        boolean found = false;
        for( int i = 0; i < queueis.size(); ++i ) {
          queuei = queueis.get( i );
          if( queueName.equals( queuei.getQueueName() ) )
            found = true;
        }
        // Queue is removed
        if( !found ) {
          this.removedResources.add( this.queues.get( queueName ) );
        }
      }
    }
    // sorting methods
    Sort( this.editor.sortedQ, this.Qlist, this.box_queue, true );
    Sort( this.editor.sortedN, this.Nlist, this.box_nodes, false );
    // create at first time a queue box
    if( null == this.box_queue ) {
      int startx = this.queue_dim[ 1 ] - this.queue_dim[ 0 ];
      int starty = this.queue_dim[ 2 ];
      if( this.firstTime )
        this.initProgress.moveNextMajorTask( 1 );
      dimBox_queue = new Dimension( startx, starty + 95 );
      this.box_queue = new Box( this.jobManager ); // new object
      this.box_queue.setSize( dimBox_queue );
      this.box_queue.setName( Messages.getString( "BoxElementQueues" ) ); //$NON-NLS-1$
      pointBox_queue = pointBox_queue.setLocation( 25, 25 );
      this.box_queue.setLocation( pointBox_queue );
      this.box_queue.addChildren( this.Qlist );
      newReses = new ArrayList<BatchResource>();
      newReses.add( this.box_queue );
      if( this.firstTime )
        this.initProgress.moveNextMinor();
    }
    int X = this.queue_dim[ 0 ];
    int Y = this.queue_dim[ 2 ] + 200;//
    if( null == this.computingElement ) // Create the ce
    {
      if( this.firstTime )
        this.initProgress.moveNextMajorTask( 1 );
      this.computingElement = new ComputingElement( this.jobManager );
      this.computingElement.setSize( dimCE );
      this.computingElement.setFQDN( this.batchName );
      this.computingElement.setType( this.batchType );
      pointCE = pointCE.setLocation( X + 205, Y );
      this.computingElement.setLocation( pointCE );
      if( null == newReses )
        newReses = new ArrayList<BatchResource>();
      newReses.add( this.computingElement );
      if( this.firstTime )
        this.initProgress.moveNextMinor();
    }
    // case of nodes box
    if( null == this.box_nodes ) {
      if( this.firstTime )
        this.initProgress.moveNextMajorTask( 1 );
      int startx = this.nodes_dim[ 1 ] - this.nodes_dim[ 0 ];
      int starty = this.nodes_dim[ 2 ];

      dimBox_nodes = new Dimension( startx, starty + 70 );
      this.box_nodes = new Box( this.jobManager );
      this.box_nodes.setSize( dimBox_nodes );
      this.box_nodes.setName( Messages.getString( "BoxElementNodes" ) );//$NON-NLS-1$
      this.box_nodes.setIsNodes( true );
      pointBox_nodes = pointBox_nodes.setLocation( X, Y + 200 );
      this.box_nodes.setLocation( pointBox_nodes );
      if( null == newReses )
        newReses = new ArrayList<BatchResource>();
      newReses.add( this.box_nodes );
      this.box_nodes.addChildren( this.Nlist );
      if( this.firstTime )
        this.initProgress.moveNextMinor();
      if( this.firstTime )
        this.initProgress.moveNextMajorTask( 2 );
      Connection conn = new Connection( this.computingElement,
                                        this.box_nodes,
                                        Connection.SOLID_CONNECTION );
      conn.reconnect();
      Connection conn1 = new Connection( this.box_queue,
                                         this.computingElement,
                                         Connection.DASHED_CONNECTION );
      conn1.reconnect();
    }
    // Draw all the new elements at the same time
    if( null != newReses ) {
      this.diagram.addChildren( newReses );
    }
    // See if the dynamic information about the ce have changed
    if( null != queueis
        && queueis.size() != this.computingElement.getNumQueues() )
      this.computingElement.setNumQueues( queueis.size() );
    else if( null == queueis && 0 < this.computingElement.getNumQueues() )
      this.computingElement.setNumQueues( 0 );
    if( null != wnis && wnis.size() != this.computingElement.getNumWNs() )
      this.computingElement.setNumWNs( wnis.size() );
    else if( null == wnis && 0 < this.computingElement.getNumWNs() )
      this.computingElement.setNumWNs( 0 );
    if( this.jobManager.getJobCount() != this.computingElement.getNumJobs() )
      this.computingElement.setNumJobs( this.jobManager.getJobCount() );
    if( !this.removedResources.isEmpty() ) {
      for( BatchResource resource : this.removedResources ) {
        this.box_queue.removeChildren( this.Qlist );
        this.Qlist.remove( resource );
        if( resource instanceof WorkerNode ) {
          // No nothing yet
        } else if( resource instanceof Queue ) {
          Queue q = ( Queue )resource;
          this.queues.remove( q.getQueneName() );
        } else
          assert false;
      }
      this.diagram.removeChild( this.box_queue );
      this.box_queue.addChildren( this.Qlist );
      this.diagram.addChild( this.box_queue );
      this.removedResources.clear();
      this.editor.sortedQ = 0;
    }
    // Only show the progress bar the first time
    if( this.firstTime ) {
      this.initProgress.close();
      this.firstTime = false;
    }
  }
  
  private void addQueue(final boolean values,final Dimension dimBox_queue)
  {
      this.box_queue.removeChildren( this.Qlist );
      this.diagram.removeChild( this.box_queue );
      this.box_queue.addChildren( this.Qlist );
    //  this.box_queue.setIsDimentionChanged( true );
      boolean newsize = false;
    
      if( ( this.count ) % ( this.nMaxElements ) == 1 ) {
      this.maxY = this.maxY + 160;
      this.count = 0;
      Dimension dimBox_new = new Dimension( this.maxX, this.maxY );
      this.box_queue.setSize( dimBox_new );
      this.diagram.addChild( this.box_queue );
      newsize = true;
      }
      if( !newsize ) {
      this.box_queue.setSize( dimBox_queue );
      this.diagram.addChild( this.box_queue );
      }
   
      if( this.editor.queueByName == 1 && values ) {
      this.editor.sortedQ = 1;
      this.editor.queueByName = 0;
      } else if( this.editor.queueByState == 2 && values ) {
      this.editor.sortedQ = 2;
      this.editor.queueByName = 0;
      } else {
      this.editor.sortedQ = 0;
    }
  }
  

  /*
   * Making the sorting in queues or nodes. if cont is 1 it sorts by name else
   * by state, if x is true it sorts the Queues box else the Nodes box
   */
  @SuppressWarnings("unchecked")
  private void Sort( final int cont,
                     final List list,
                     final Box box,
                     final boolean x )
  {
    
    if( cont == 1 ) {
      Collections.sort( list );
      if( x ) {
        box.setName( Messages.getString( "BoxQueue.SortedByName" ) );//$NON-NLS-1$
      } else {
        box.setName( Messages.getString( "BoxNode.SortedByName" ) );//$NON-NLS-1$
      }
      Refresh( box, list );
      ResetValues();
    } else if( cont == 2 ) {
      Collections.sort( list );
      if( x ) {
        box.setName( Messages.getString( "BoxQueue.SortedByState" ) );//$NON-NLS-1$
      } else {
        box.setName( Messages.getString( "BoxNode.SortedByState" ) );//$NON-NLS-1$
      }
      Refresh( box, list );
      ResetValues();
    }
  }

  // refreshing the display
  @SuppressWarnings("unchecked")
  private void Refresh( final Box box, final List list ) {
    box.removeChildren( list );
    this.diagram.removeChild( box );
    box.addChildren( list );
    this.diagram.addChild( box );
  }

  // reseting values
  private void ResetValues() {
    this.editor.sortedQ = 0;
    this.editor.sortedN = 0;
  }
}
