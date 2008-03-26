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
   * Hashtable holding paused updaters and their priorities to start/resume updating
   */
  private Hashtable< JobStatusUpdater, Integer > sleepingUpdaters = new Hashtable< JobStatusUpdater, Integer >();
  
  /**
   * Hashtable holding working updaters and their priorities
   */
  private Hashtable< JobStatusUpdater, Integer > workingUpdaters = new Hashtable< JobStatusUpdater, Integer>();
  
  /**
   * Constructor for JobScheduler
   * @param name 
   */
  public JobScheduler( final String name ) {
    super( Messages.getString( "JobManager.scheduler_name" ) ); //$NON-NLS-1$
    schedule( 30000 );
  }

  
  /**
   * Gets number of running job status updaters
   * @return Number of the currently running updaters
   */
  public int getNumberOfRunningUpdaters() {
    return this.workingUpdaters.size();
  }
  
  /**
   * Gets the shared singleton of this class
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
    if( Preferences.getUpdateJobsStatus() && this.sleepingUpdaters.size() > 0 
        && ( getNumberOfRunningUpdaters() < Preferences.getUpdatersLimit() ) )
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
      this.workingUpdaters.put(  updaterToWakeUp, new Integer( UPDATER_NORMAL_PRIORITY ));
      this.sleepingUpdaters.remove( updaterToWakeUp );
    }
    
    //checks if there should be updaters put to sleep due to limits
    while ( getNumberOfRunningUpdaters() > Preferences.getUpdatersLimit() ) {
      JobStatusUpdater updater = this.workingUpdaters.keys().nextElement();
      pauseUpdater( updater );
    }
    
    schedule( 30000 );
    return Status.OK_STATUS;
  }

  /**
   * Schedules updater, depending on the preference settings and actual number
   * of running updaters. 
   * If updater is eligible to start, this metod schedules it,
   * if not, it's put into the list of the waiting updaters. 
   * @param updater Updater to be scheduled.
   */
  public void scheduleNewUpdater( final JobStatusUpdater updater ) {
    if( Preferences.getUpdateJobsStatus() && ( getNumberOfRunningUpdaters() < Preferences.getUpdatersLimit() ) ) {
      int updatePeriod = Preferences.getUpdateJobsPeriod();
      updater.schedule( updatePeriod ); 
      this.workingUpdaters.put( updater, new Integer( UPDATER_NORMAL_PRIORITY ) );
    } else {
      this.sleepingUpdaters.put( updater, new Integer( UPDATER_NORMAL_PRIORITY ) );
    }
  }

  /**
   * Pauses all of the running updaters.
   */
  public void pauseAllUpdaters() {
    for( Enumeration<JobStatusUpdater> e = this.workingUpdaters.keys(); e.hasMoreElements(); ) {
      JobStatusUpdater updater = e.nextElement();
      pauseUpdater( updater );
    }
  }
  
  /**
   * Pauses specified updater.
   * @param updater Updater to be paused.
   * @return True when updater was paused.
   */
  public boolean pauseUpdater( final JobStatusUpdater updater ) {
    boolean result = false;
    if ( this.workingUpdaters.containsKey( updater ) ) {
      result = true;
      updater.sleep();
      this.sleepingUpdaters.put( updater, new Integer( UPDATER_NORMAL_PRIORITY ) );
      this.workingUpdaters.remove( updater );
    }
    return result;
  }
  
  /**
   * Resumes all updaters.
   */
  public void resumeAllUpdaters() {
    for ( Enumeration< JobStatusUpdater > e = this.sleepingUpdaters.keys(); e.hasMoreElements(); ) {
      JobStatusUpdater updater = e.nextElement();
      resumeUpdater( updater );
    }
  }
  
  /**
   * Resume specified updater.
   * @param updater Updater to be resumed.
   * @return True if updater was resumed.
   */
  public boolean resumeUpdater ( final JobStatusUpdater updater ) {
    boolean result = false;
    if ( this.sleepingUpdaters.containsKey( updater ) ) {
      result = true;
      updater.wakeUp();
      this.workingUpdaters.put( updater, new Integer( UPDATER_NORMAL_PRIORITY ) );
      this.sleepingUpdaters.remove( updater );
    }
    return result;
  }

  /**
   * Removes specified updater from lists of running and sleeping updaters 
   * @param updater
   */
  public void clearUpdater( final JobStatusUpdater updater ) {
    this.sleepingUpdaters.remove( updater );
    this.workingUpdaters.remove( updater );
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

  Hashtable<IGridJobID, JobStatusUpdater> updaters = new Hashtable<IGridJobID, JobStatusUpdater>();
  
  private List< IGridJobStatusListener > globalListeners = new ArrayList< IGridJobStatusListener >();

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
    // fast workaround.
    // TODO pawelw -correct it
    // if( this.listenerRegistered == false ){
    // this.listenerRegistered = true;
    // GridModel.getRoot().addGridModelListener( this );
    // }
    boolean flag;
    flag = super.addElement( element );
    if( element instanceof IGridJob ) {
      //JobStatusUpdater updater = new JobStatusUpdater( ( ( IGridJob )element ) );
//      this.updaters.put( ( ( IGridJob )element ).getID(), updater );
      JobStatusUpdater updater = getUpdater( ( IGridJob ) element );
      if ( updater != null ) {
        updater.setSystem( true );
        JobScheduler.getJobScheduler().scheduleNewUpdater( updater );
        updater.addJobStatusListener( IGridJobStatus._ALL, this );
      }
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
    JobStatusUpdater updater = getUpdater( id );
    updater.setSystem( true );
    JobScheduler.getJobScheduler().scheduleNewUpdater( updater );
  }

  public void pauseAllUpdaters(){
    JobScheduler.getJobScheduler().pauseAllUpdaters();
  }
  
  /**
   * Pauses specified updater.
   * @param updater Updater to be paused.
   */
  public void pauseUpdater( final JobStatusUpdater updater ) {
    JobScheduler.getJobScheduler().pauseUpdater( updater );
  }
  
  /**
   * Pauses updater for the specified job.
   * @param jobId Id of the job, for which updater should be paused.
   */
  public void pauseUpdater( final IGridJobID jobId ) {
    JobStatusUpdater updater = this.updaters.get( jobId );
    if ( updater != null ) {
      JobScheduler.getJobScheduler().pauseUpdater( updater );
    }
  }
  
  public void wakeUpAllUpdaters() {
    JobScheduler.getJobScheduler().resumeAllUpdaters();
  }
  
  public void updateJobsStatus( final ArrayList<IGridJob> selectedJobs ) {
    for( Enumeration<IGridJobID> enumJobIds = this.updaters.keys(); enumJobIds.hasMoreElements(); )
    {
      IGridJobID jobId = enumJobIds.nextElement();
      for( Iterator<IGridJob> iterSelectedJobs = selectedJobs.iterator(); iterSelectedJobs.hasNext(); )
      {
        IGridJob selectedJob = iterSelectedJobs.next();
        if( jobId == selectedJob.getID() ) {
          JobStatusUpdater updater = this.updaters.get( jobId );
          updater.sleep();
          updater.wakeUp();
          updater.schedule( 1000 );
        }
      }
    }
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
    JobStatusUpdater updater = null;
    for( IGridJob job : jobs ) {
//        for (JobStatusUpdater updaterTemp : this.updaters.values() ){
//          if (updaterTemp.getJob().equals(job)){
//            updaterTemp.addJobStatusListener( status, listener );
//          }
//        }
      updater = getUpdater( job );
      if ( updater != null )
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
      updater = getUpdater( id );
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
      JobScheduler.getJobScheduler().clearUpdater ( updater );
    }
  }

  public void statusChanged( final IGridJob job ) {
    for ( IGridJobStatusListener listener : this.globalListeners ) {
      listener.statusChanged( job );
    }
  }
  
  public void statusUpdated( final IGridJob job ) {
    for ( IGridJobStatusListener listener : this.globalListeners ) {
      listener.statusUpdated( job );
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
    JobStatusUpdater updater = getUpdater( job );
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
    JobStatusUpdater updater = getUpdater( id );
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
          JobStatusUpdater updater = this.updaters.remove( job.getID() );
          // check if updater is currently running and wait until it finishes
          if ( updater != null ) {
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
  
  public void jobStatusChanged ( final IGridJob job ) {
    JobStatusUpdater updater = getUpdater( job );
    if ( updater != null ) {
      updater.statusUpdated( job.getJobStatus() );
    }
  }

  /**
   * Method gets updater for the specified job. If the updater doesn't exist, it is created
   * and added to updaters list. This method shouldn't be used to check if updaters list 
   * contains updater for the given job - use this.updaters.contains( IGridJob.getID() ) instead.
   * 
   * @param job Job for which updater should be recived.
   * @return Updater form list of registered udpaters if updater existed before or new updater if
   * job status can change or null if job status can't change
   */
  public JobStatusUpdater getUpdater( final IGridJob job ) {
    JobStatusUpdater result = null;
    if ( !( this.updaters.containsKey( job.getID() ) ) ) {
      if ( job.getJobStatus().canChange() ) {
        JobStatusUpdater updater = new JobStatusUpdater( job );
        this.updaters.put( job.getID(), updater );
        result = updater;
      }
    } else {
      result = this.updaters.get( job.getID() );
    }
    return result;
  }
  
  /**
   * Method gets updater for the job with specified id. If the updater doesn't exist, it is created
   * and added to updaters list. This method shouldn't be used to check if updaters list 
   * contains updater for the given job id - use this.updaters.contains( IGridJobID ) instead.
   * 
   * @param jobID Job for which updater should be recived.
   * @return Updater form list of registered udpaters.
   */
  public JobStatusUpdater getUpdater( final IGridJobID jobID ) {
    JobStatusUpdater result = null;
    if ( !( this.updaters.contains( jobID ) ) ) {
      JobStatusUpdater updater = new JobStatusUpdater( jobID );
      this.updaters.put( jobID, updater );
      result = updater;
    } else {
      result = this.updaters.get(  jobID );
    }
    return result;
  }

  public void jobStatusUpdated( final IGridJob job ) {
    for( IGridJobStatusListener listener : this.globalListeners ) {
      listener.statusUpdated( job );
    }
    
  }
  
}
