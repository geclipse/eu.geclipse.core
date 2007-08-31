/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *    Szymon Mueller
 *****************************************************************************/
package eu.geclipse.core.internal.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.geclipse.core.JobStatusUpdater;
import eu.geclipse.core.Preferences;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridJobManager;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.core.model.IGridJobStatusListener;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;

/**
 * Helper class to schedule {@link JobStatusUpdater} updates
 */
class JobScheduler extends Job {

  /**
   * Normal priority to start updating {@link JobStatusUpdater}
   */
  public static final int UPDATER_NORMAL_PRIORITY = 1;
  
  /**
   * High priority to start updating {@link JobStatusUpdater}, used for jobs which were
   * manually set to update
   */
  public static final int UPDATER_HIGH_PRIORITY = 2;
  
  /**
   * Singleton of this class 
   */
  private static JobScheduler singleton;
  
  /**
   * Hashtable holding updaters and their priorities to start/resume updating
   */
  private Hashtable< JobStatusUpdater, Integer > sleepingUpdaters = new Hashtable<JobStatusUpdater, Integer >();

  /**
   * Constructor for JobScheduler
   * @param name 
   */
  public JobScheduler( final String name ) {
    super( Messages.getString( "JobManager.scheduler_name" ) ); //$NON-NLS-1$
    schedule( 30000 );
  }

  /**
   * Gets the shared singleton of this class
   * 
   * @return Gets singleton of this class.
   */
  public static JobScheduler getJobScheduler() {
    if( singleton == null ) {
      singleton = new JobScheduler( "" ); //$NON-NLS-1$
    }
    return singleton;
  }

  @Override
  public IStatus run( final IProgressMonitor monitor ) {
    if( Preferences.getUpdateJobsStatus() && this.sleepingUpdaters.size() > 0 )
    {
      JobStatusUpdater updaterToWakeUp = null;
      if( this.sleepingUpdaters.contains( new Integer( UPDATER_HIGH_PRIORITY ) ) )
      {
        JobStatusUpdater tempUpdater = null;
        for( Enumeration<JobStatusUpdater> enumeration = this.sleepingUpdaters.keys(); enumeration.hasMoreElements(); )
        {
          tempUpdater = enumeration.nextElement();
          if( this.sleepingUpdaters.get( tempUpdater ).intValue() == UPDATER_HIGH_PRIORITY )
          {
            updaterToWakeUp = tempUpdater;
          }
        }
        if ( updaterToWakeUp != null ) {
          updaterToWakeUp.wakeUp();
          updaterToWakeUp.schedule( Preferences.getUpdateJobsPeriod() );
        }
      } else {
        updaterToWakeUp = this.sleepingUpdaters.keys().nextElement();
        updaterToWakeUp.wakeUp();
        updaterToWakeUp.schedule( Preferences.getUpdateJobsPeriod() );
      }
      this.sleepingUpdaters.remove( updaterToWakeUp );
    }
    schedule( 30000 );
    return Status.OK_STATUS;
  }

  /**
   * Method for adding gridJob with chosen priority to queue of updaters waiting
   * for start
   * 
   * @param updater
   * @param priority
   */
  public void addUpdaterToQueue( final JobStatusUpdater updater, final int priority ) {
    this.sleepingUpdaters.put( updater, new Integer( priority ) );
  }

  /**
   * 
   * @param numberOfRunningUpdaters 
   * @return number of running jobs after wake up
   */
  public int wakeUpdaters( final int numberOfRunningUpdaters ) {
    int actualRunningUpdaters = numberOfRunningUpdaters;
    for( Enumeration<JobStatusUpdater> e = this.sleepingUpdaters.keys(); e.hasMoreElements(); )
    {
      JobStatusUpdater updater = e.nextElement();
      updater.wakeUp();
      updater.schedule( Preferences.getUpdateJobsPeriod() );
      actualRunningUpdaters++;
    }
    return actualRunningUpdaters;
  }

 
  /**
   * Searches hashtable of sleeping updaters for updater and wakes him up 
   * @param updater to be awaken
   * @return True if updater was found and woken up 
   */
  public boolean wakeUpdater( final JobStatusUpdater updater ) {
    boolean result = false;
    for( Enumeration<JobStatusUpdater> e= this.sleepingUpdaters.keys(); e.hasMoreElements(); ) {
      JobStatusUpdater sleepingUpdater = e.nextElement();
      if (updater == sleepingUpdater) {
        sleepingUpdater.setManualUpdate( true );
        sleepingUpdater.wakeUp();
        sleepingUpdater.schedule( 1000 );
        result = true;
        this.sleepingUpdaters.put( sleepingUpdater, new Integer( UPDATER_HIGH_PRIORITY ) );
      }
    }
    return result;
  }
 
}

/**
 * Core implementation of an {@link IGridJobManager}.
 */
public class JobManager extends AbstractGridElementManager
  implements IGridJobManager, IGridJobStatusListener, IGridModelListener
{

  /**
   * The name of this manager.
   */
  private static final String NAME = ".jobs"; //$NON-NLS-1$
  /**
   * The singleton.
   */
  private static JobManager singleton;
  /**
   * Hashtable for holding information about updaters assigned to jobs.
   */
//  private Hashtable<IGridJob, JobStatusUpdater> updatersByJob = new Hashtable<IGridJob, JobStatusUpdater>();
  private Hashtable<IGridJobID, JobStatusUpdater> updaters = new Hashtable<IGridJobID, JobStatusUpdater>();
  
  private List< IGridJobStatusListener > globalListeners = new ArrayList< IGridJobStatusListener >();

//  private boolean listenerRegistered=false;
  
  private int numberOfRunningUpdaters = 0;
  
  /**
   * Private constructor to ensure to have only one instance of this class. This
   * can be obtained by {@link #getManager()}.
   */
  private JobManager() {
    // empty implementation
  }

  @Override
  public boolean addElement( final IGridElement element )
    throws GridModelException
  {
//fast workaround. 
//TODO pawelw -correct it    
//    if( this.listenerRegistered == false ){
//      this.listenerRegistered = true;
//      GridModel.getRoot().addGridModelListener( this );
//    }

    
    boolean flag;
    flag = super.addElement( element );
    if( element instanceof IGridJob ) {
      JobScheduler scheduler = JobScheduler.getJobScheduler();
      JobStatusUpdater updater = new JobStatusUpdater( ( ( IGridJob )element ) );
      this.updaters.put( ( ( IGridJob )element ).getID(), updater );
      updater.setSystem( true );
      if( Preferences.getUpdateJobsStatus() ) {
        int updatePeriod = Preferences.getUpdateJobsPeriod();
        updater.schedule( updatePeriod ); 
        this.numberOfRunningUpdaters++;
      } else {
        scheduler.addUpdaterToQueue( updater, JobScheduler.UPDATER_NORMAL_PRIORITY );
      }
      updater.addJobStatusListener( IGridJobStatus._ALL, this );
    }
    return flag;
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridJobManager#startUpdater(eu.geclipse.core.model.IGridJobID)
   */
  public void startUpdater( final IGridJobID id)
    throws GridModelException
  {
    JobStatusUpdater updater = new JobStatusUpdater( id );
    this.updaters.put( id, updater );
    updater.setSystem( true );
    int updatePeriod = Preferences.getUpdateJobsPeriod();
    updater.schedule( updatePeriod );
  }

  public void pauseAllUpdaters(){
    JobScheduler scheduler = JobScheduler.getJobScheduler();
    for (Enumeration<IGridJobID> e = this.updaters.keys();e.hasMoreElements(); ){
      IGridJobID jobId = e.nextElement();
      JobStatusUpdater updater = this.updaters.get( jobId );
      updater.sleep();
      this.numberOfRunningUpdaters--;
      scheduler.addUpdaterToQueue( updater, JobScheduler.UPDATER_NORMAL_PRIORITY );
    }
  }
  
  public void wakeUpAllUpdaters() {
    int newNumberOfRunningUpdaters = JobScheduler.getJobScheduler().wakeUpdaters( this.numberOfRunningUpdaters );
    setNumberOfRunningUpdaters( newNumberOfRunningUpdaters );
  }
  
  public void updateJobsStatus( final ArrayList<IGridJob> selectedJobs ) {
    for( Enumeration<IGridJobID> enumJobIds = this.updaters.keys(); enumJobIds.hasMoreElements(); )
    {
      IGridJobID jobId = enumJobIds.nextElement();
      // if ( jobId == jobs.getID() ) {
      // JobStatusUpdater updater = this.updaters.get( jobId );
      // if ( !JobScheduler.getJobScheduler().wakeUpdater( updater ) ) {
      // updater.sleep();
      // updater.wakeUp();
      // updater.schedule( 1000 );
      // }
      // }
      for( Iterator<IGridJob> iterSelectedJobs = selectedJobs.iterator(); iterSelectedJobs.hasNext(); )
      {
        IGridJob selectedJob = iterSelectedJobs.next();
        if( jobId == selectedJob.getID() ) {
          JobStatusUpdater updater = this.updaters.get( jobId );
          if( !JobScheduler.getJobScheduler().wakeUpdater( updater ) ) {
            updater.sleep();
            updater.wakeUp();
            updater.schedule( 1000 );
          }
        }
      }
    }
  }
  
  /**
   * 
   * @return Number of running updaters
   */
  public int getNumberOfRunningUpdaters() {
    return this.numberOfRunningUpdaters;
  }
  
  /**
   * @param number of running updaters to set.
   */
  public void setNumberOfRunningUpdaters( final int number ) {
    this.numberOfRunningUpdaters = number;
  }

  /**
   * Get the singleton instance of the <code>JobManager</code>.
   * 
   * @return The singleton.
   */
  public static JobManager getManager() {
    if( singleton == null ) {
      singleton = new JobManager();
    }
    return singleton;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.internal.model.AbstractGridElementManager#getName()
   */
  public String getName() {
    return NAME;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridElementManager#canManage(eu.geclipse.core.model.IGridElement)
   */
  public boolean canManage( final IGridElement element ) {
    return element instanceof IGridJob;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridJobManager#addJobStatusListener(eu.geclipse.core.model.IGridJobStatusListener)
   */
  public void addJobStatusListener( final IGridJobStatusListener listener ) {
    if( ! this.globalListeners.contains( listener ) ) {
      this.globalListeners.add( listener );
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridJobManager#addJobStatusListener(java.util.List,
   *      int, eu.geclipse.core.model.IGridJobStatusListener)
   */
  public void addJobStatusListener( final IGridJob[] jobs,
                                    final int status,
                                    final IGridJobStatusListener listener )
  {
    JobStatusUpdater updater;
    for( IGridJob job : jobs ) {
      updater = this.updaters.get( job.getID() );
      updater.addJobStatusListener( status, listener );
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridJobManager#addJobStatusListener(java.util.List,
   *      int, eu.geclipse.core.model.IGridJobStatusListener)
   */
  public void addJobStatusListener( final IGridJobID[] ids,
                                    final int status,
                                    final IGridJobStatusListener listener )
  {
    JobStatusUpdater updater;
    for( IGridJobID id : ids ) {
      updater = this.updaters.get( id );
      updater.addJobStatusListener( status, listener );
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridJobManager#removeJobStatusListener(eu.geclipse.core.model.IGridJobStatusListener)
   */
  public void removeJobStatusListener( final IGridJobStatusListener listener ) {
    JobStatusUpdater updater;
    for( Enumeration<JobStatusUpdater> e = this.updaters.elements(); e.hasMoreElements(); )
    {
      updater = e.nextElement();
      updater.removeJobStatusListener( listener );
    }
    this.globalListeners.remove( listener );
  }

  /**
   * Removes updater from JobManager. It is called by updater, when it finished
   * it works (job status cannot change any more)
   * 
   * @param updater
   */
  public void removeUpdater( final JobStatusUpdater updater ) {
    Collection<JobStatusUpdater> values = this.updaters.values();
    while( values.remove( updater ) ) {
      // empty block};
    }
  }

  public void statusChanged( final IGridJob job ) {
    for ( IGridJobStatusListener listener : this.globalListeners ) {
      listener.statusChanged( job );
    }
  }

  // void waitForJob(final String id) throws InterruptedException,
  // IllegalAccessException{
  // JobStatusUpdater jsu = updaters.get( id );
  //
  // if(jsu==null){
  // throw new IllegalAccessException("No updater for job id: "+id);
  // }
  // jsu.join();
  // }
  //
  // void waitForJob(final IGridJobID id) throws InterruptedException,
  // IllegalAccessException{
  // waitForJob(id.getJobID());
  // }
  /**
   * Wait until updater for the given job finishes, which means that the job
   * status is final and cannot be changed any more.
   * @param job
   * @throws InterruptedException
   * @throws NoSuchElementException
   */
  void waitForJob( final IGridJob job )
    throws InterruptedException, NoSuchElementException
  {
    JobStatusUpdater updater = this.updaters.get( job.getID() );
    if( updater == null ) {
      throw new NoSuchElementException();
    }
    updater.join();
  }

  /**
   * Wait until updater for the given job finishes, which means that the job
   * status is final and cannot be changed any more.
   * @param job
   * @throws InterruptedException
   * @throws NoSuchElementException
   */
  void waitForJob( final IGridJobID id )
    throws InterruptedException, NoSuchElementException
  {
    JobStatusUpdater updater = this.updaters.get( id );
    if( updater == null ) {
      throw new NoSuchElementException();
    }
    updater.join();
  }

  /**
   * Removes the specified {@link IGridJobStatusListener} from the list of listeners.
   * 
   * @param jobs The jobs from which the listener should be removed.
   * @param listener The listener to be removed.
   */
  public void removeJobStatusListener( final IGridJob[] jobs, final IGridJobStatusListener listener ) {

    JobStatusUpdater updater;
    for(int i=0;i<jobs.length;i++){
      updater=this.updaters.get( jobs[i].getID() );
      updater.removeJobStatusListener( listener );
    }
    this.globalListeners.remove( listener );
  }

  /**
   * Removes the specified {@link IGridJobStatusListener} from the list of listeners.
   * 
   * @param ids The ids of the jobs from which the listener should be removed.
   * @param listener The listener to be removed.
   */
  public void removeJobStatusListener( final IGridJobID[] ids, final IGridJobStatusListener listener ) {
    JobStatusUpdater updater;
    for(int i=0;i<ids.length;i++){
      updater=this.updaters.get( ids[i] );
      updater.removeJobStatusListener( listener );
    }
    this.globalListeners.remove( listener );
  }

  public void gridModelChanged( final IGridModelEvent event ) {
    if( event.getType() == IGridModelEvent.ELEMENTS_REMOVED ) {
      IGridElement[] removedElements = event.getElements();
      for( IGridElement elem : removedElements ) {
        if( elem instanceof IGridJob ) {
          IGridJob job = ( IGridJob )elem;
          JobStatusUpdater updater = this.updaters.get( job.getID() );
          // check if updater is currently running and wait until it finishes
          while( updater.getState() == Job.RUNNING ) {
            try {
              Thread.sleep( 1000 );
            } catch( InterruptedException e ) {
              // empty block
            }
          }
          // cancel updater
          updater.cancel();
          // remove updater from updaters list
          this.removeUpdater( updater );
        }
      }
    }
  }

}
