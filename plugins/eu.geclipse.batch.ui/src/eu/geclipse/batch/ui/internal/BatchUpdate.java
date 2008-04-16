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
import eu.geclipse.batch.BatchJobManager;
import eu.geclipse.batch.IBatchService;
import eu.geclipse.batch.IBatchJobInfo;
import eu.geclipse.batch.IQueueInfo;
import eu.geclipse.batch.IWorkerNodeInfo;
import eu.geclipse.batch.ui.internal.model.BatchDiagram;
import eu.geclipse.batch.ui.internal.model.BatchResource;
import eu.geclipse.batch.ui.internal.model.ComputingElement; //
import eu.geclipse.batch.ui.internal.model.Box;
import java.util.Collections; //
import eu.geclipse.batch.ui.internal.model.Connection;
import eu.geclipse.batch.ui.internal.model.Queue;
import eu.geclipse.batch.ui.internal.model.WorkerNode;
import eu.geclipse.batch.ui.dialogs.*;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Class that runs a job to synchronize the current view of the batch service
 * with the actual state of the batch service.
 */
public class BatchUpdate {

  public static int sortedQ = 0;
  public static int sortedN = 0;
  public static int Qbyname = 0;
  public static boolean Nbyname = false;
  protected Job updateJob;
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
  private Box box_notes;
  private int N = 5;
  private LinkedHashMap<String, WorkerNode> workerNodes = new LinkedHashMap<String, WorkerNode>();
  private LinkedHashMap<String, Queue> queues = new LinkedHashMap<String, Queue>();
  // private List<BatchResource> newResources = new ArrayList<BatchResource>();
  private List<BatchResource> removedResources = new ArrayList<BatchResource>();
  private boolean firstTime;
  private ProgressDialog initProgress;
  private int checkX = 0;
  private int checkY = 0;
  int count = 0;
  int startx;
  int starty;
  int tempx;
  int tempy;
  int[] queue_dim = {
    -1, -1, -1
  };
  int[] notes_dim = {
    -1, -1, -1
  };

  /**
   * Default constructor.
   * 
   * @param shell The parent shell
   * @param diagram Reference to the diagram that holds the figures.
   * @param batchWrapper Interface to the batch service.
   * @param batchName The name FQDN of the machine hosting the batch service
   * @param batchType The type of the batch service.
   * @param updateInterval The interval between each update.
   */
  public BatchUpdate( final Shell shell,
                      final BatchDiagram diagram,
                      final IBatchService batchWrapper,
                      final String batchName,
                      final String batchType,
                      final int updateInterval )
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
    this.initProgress = new ProgressDialog( shell );
    String[] description = new String[]{
      "Worker Node", //$NON-NLS-1$
      "Queues", //$NON-NLS-1$
      "Computing Element", //$NON-NLS-1$
      "Connections"}; //$NON-NLS-1$
    this.initProgress.initInformation( 4,
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
      final Dimension dimWN = new Dimension( 100, 40 );//
      wn = new WorkerNode( this.jobManager );
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
      queue = new Queue( this.jobManager );
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
    Dimension dimBox_notes = null;
    Point pointBox_queue, pointBox_notes;
    dimCE = new Dimension( 140, 90 );
    pointBox_queue = new Point( 100, 55 );
    pointBox_notes = new Point( 100, 400 );
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
      int loc_y = 15;
      boolean flag_notes = true;
      int j = 0;
      for( int i = 0; i < wnis.size(); ++i ) {
        wni = wnis.get( i );
        wn = this.updateWN( wni );
        if( null != wn ) {
          int loc = 30 + ( 120 * ( j ) );
          j++;
          if( i == 0 )
            notes_dim[ 0 ] = loc;
          if( i >= this.N && i % this.N == 1 ) {
            loc_y = loc_y + 50;
            loc = notes_dim[ 0 ];
            notes_dim[ 2 ] = loc_y;
            j = 1;
          }
          // pointWN = pointWN.setLocation( 100 , 100 );
          if( i == this.N - 1 ) {
            notes_dim[ 1 ] = loc + 150; // take the x;
            flag_notes = false;
          }
          if( i == wnis.size() - 1 )// teleftaio stoixeio
          {
            if( flag_notes ) {
              notes_dim[ 1 ] = loc + 150;
            }
            notes_dim[ 2 ] = loc_y + 30;// 150
            // notes_dim[2]+=100;//150
          }
          pointWN = pointWN.setLocation( loc, loc_y );// 100,loc
          wn.setLocation( pointWN );
          this.Nlist.add( wn );
          // Collections.sort(Nlist );
          // this.newResources.add( wn );
          if( this.firstTime )
            this.initProgress.moveNextMinor();
          if( this.Qbyname == 1 ) {
            BatchUpdate.sortedQ = 1;
            Qbyname = 0;
          } else if( this.Qbyname == 2 ) {
            BatchUpdate.sortedQ = 2;
            Qbyname = 0;
          }
          Sort( this.sortedN, 1, 2, this.Nlist, this.box_notes, false );
        }
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
    int loc_y = 15;
    if( null != queueis ) {
      if( this.firstTime )
        this.initProgress.moveNextMajorTask( queueis.size() );
      boolean flag_queue = true;
      int j = 0;
      boolean add = false;
      for( int i = 0; i < queueis.size(); ++i ) {
        queuei = queueis.get( i );
        queue = this.updateQueue( queuei, jobis );
        tempy = queue_dim[ 2 ];
        if( null != queue ) {
          count = queueis.size();
          if( !this.firstTime ) {
            add = true;
          }
          int loc_x = 50 + ( 120 * ( j ) );// 50 120
          j++;
          if( i == 0 )
            queue_dim[ 0 ] = loc_x;// krataei to x tou protou stoixeiou
          if( i >= this.N && i % ( this.N ) == 1 ) {
            loc_y = loc_y + 60;
            loc_x = queue_dim[ 0 ];
            queue_dim[ 2 ] = loc_y + 20;
            j = 1;
          }
          if( this.checkX < loc_x )
            this.checkX = loc_x;
          if( this.checkY <= loc_y )
            this.checkY = loc_y;
          if( i == this.N - 1 ) {
            queue_dim[ 1 ] = loc_x + 150;
            flag_queue = false;
          }

          if( i == queueis.size() - 1 )// teleftaio stoixeio
          {
            if( flag_queue ) {
              queue_dim[ 1 ] = loc_x + 150;
            }
            queue_dim[ 2 ] = queue_dim[ 2 ] + 70;// 150
          }
          pointQ = pointQ.setLocation( loc_x, loc_y );
          queue.setLocation( pointQ );
          this.Qlist.add( queue );
          if( this.firstTime )
            this.initProgress.moveNextMinor();
        }
      }
      // / tempx=checkX+50;
      tempy = queue_dim[ 2 ];
      if( !this.firstTime )// case of add a new queue
      {
        try {
          if( add ) {
            this.box_queue.removeChildren( this.Qlist );
            this.diagram.removeChild( this.box_queue );
            this.box_queue.addChildren( this.Qlist );
            int oldX = checkX;
            boolean newsize = false;

            if( ( count ) % ( N + 1 ) == 1 ) {
              checkY = checkY + 95;
              count = 0;
              if( tempy - checkY > 50 ) {
                checkY = checkY + 30;
              }
              Dimension dimBox_new = new Dimension( checkX + 20, checkY );
              this.box_queue.setSize( dimBox_new );
              this.diagram.addChild( this.box_queue );
              newsize = true;
            } else
              checkY = checkY;
            // Dimension dimBox_new = new Dimension(checkX+20 ,checkY );
            if( !newsize ) {
              this.box_queue.setSize( dimBox_queue );
              this.diagram.addChild( this.box_queue );
            }
            if( this.Qbyname == 1 ) {
              BatchUpdate.sortedQ = 1;
              Qbyname = 0;
            } else if( this.Qbyname == 2 ) {
              BatchUpdate.sortedQ = 2;
              Qbyname = 0;
            }
            Sort( this.sortedQ, 1, 2, this.Qlist, this.box_queue, true );
          }
        } catch( Exception Z ) {
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
    Sort( this.sortedQ, 1, 2, this.Qlist, this.box_queue, true );
    Sort( this.sortedN, 1, 2, this.Nlist, this.box_notes, false );
    if( null == this.box_queue ) // queue box
    {
      startx = queue_dim[ 1 ];
      starty = queue_dim[ 2 ];
      if( this.firstTime )
        this.initProgress.moveNextMajorTask( 1 );
      // / dimBox_queue = new Dimension(queue_dim[1]-85, queue_dim[2]-190);
      if( queueis.size() <= N )
        dimBox_queue = new Dimension( startx, starty + 50 );
      else
        dimBox_queue = new Dimension( startx, starty );
      // dimBox_queue = new Dimension(startx, checkY);
      this.box_queue = new Box( this.jobManager ); // new object
      this.box_queue.setSize( dimBox_queue );
      this.box_queue.setName( Messages.getString( "BoxElementQueues" ) ); //$NON-NLS-1$
      pointBox_queue = pointBox_queue.setLocation( 25, 25 );// 200 loc
      this.box_queue.setLocation( pointBox_queue );
      this.box_queue.addChildren( this.Qlist );
      if( null == newReses )
        newReses = new ArrayList<BatchResource>();
      newReses.add( this.box_queue );
      if( this.firstTime )
        this.initProgress.moveNextMinor();
    }
    int X = queue_dim[ 0 ];// proto stoixieo
    int Y = queue_dim[ 2 ] + 150;//
    if( null == this.computingElement ) // Create the ce
    {
      if( this.firstTime )
        this.initProgress.moveNextMajorTask( 1 );
      this.computingElement = new ComputingElement( this.jobManager );
      this.computingElement.setSize( dimCE );
      this.computingElement.setFQDN( this.batchName );
      this.computingElement.setType( this.batchType );
      pointCE = pointCE.setLocation( X + 205, Y );// 200 loc
      this.computingElement.setLocation( pointCE );
      if( null == newReses )
        newReses = new ArrayList<BatchResource>();
      newReses.add( this.computingElement );
      if( this.firstTime )
        this.initProgress.moveNextMinor();
    }
    if( null == this.box_notes ) // node box
    {
      if( this.firstTime )
        this.initProgress.moveNextMajorTask( 1 );
      if( wnis.size() <= N )
        dimBox_notes = new Dimension( notes_dim[ 1 ] - 4, notes_dim[ 2 ] + 50 );
      else
        dimBox_notes = new Dimension( notes_dim[ 1 ] - 4, notes_dim[ 2 ] - 15 );
      this.box_notes = new Box( this.jobManager );
      this.box_notes.setSize( dimBox_notes );
      this.box_notes.setName( Messages.getString( "BoxElementNodes" ) );//$NON-NLS-1$
      this.box_notes.setIsNodes( true );
      pointBox_notes = pointBox_notes.setLocation( X, Y + 200 );
      this.box_notes.setLocation( pointBox_notes );
      if( null == newReses )
        newReses = new ArrayList<BatchResource>();
      newReses.add( this.box_notes );
      this.box_notes.addChildren( this.Nlist );
      if( this.firstTime )
        this.initProgress.moveNextMinor();
      if( this.firstTime )
        this.initProgress.moveNextMajorTask( 2 );
      Connection conn = new Connection( this.computingElement,
                                        this.box_notes,
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
      this.diagram.removeChild( this.box_queue );// //////////////
      this.box_queue.addChildren( this.Qlist );
      this.diagram.addChild( this.box_queue );
      this.removedResources.clear();
    }
    // Only show the progress bar the first time
    if( this.firstTime ) {
      this.initProgress.close();
      this.firstTime = false;
    }
  }

  void Sort( final int cont,
             final int sortArg1,
             final int sortArg2,
             final List list,
             final Box box,
             final boolean x )
  {
    if( cont == sortArg1 ) {
      Collections.sort( list );
      if( x ) {
        box.setName( Messages.getString( "BoxQueue.SortedByName" ) );//$NON-NLS-1$
      } else {
        box.setName( Messages.getString( "BoxNode.SortedByName" ) );//$NON-NLS-1$
      }
      box.removeChildren( list );
      this.diagram.removeChild( box );
      box.addChildren( list );
      this.diagram.addChild( box );
      this.sortedQ = 0;
      this.sortedN = 0;
    } else if( cont == sortArg2 ) {
      Collections.sort( list );
      if( x ) {
        box.setName( Messages.getString( "BoxQueue.SortedByState" ) );//$NON-NLS-1$
      } else {
        box.setName( Messages.getString( "BoxNode.SortedByState" ) );//$NON-NLS-1$
      }
      box.removeChildren( list );
      this.diagram.removeChild( box );
      box.addChildren( list );
      this.diagram.addChild( box );
      this.sortedQ = 0;
      this.sortedN = 0;
    }
  }
}
