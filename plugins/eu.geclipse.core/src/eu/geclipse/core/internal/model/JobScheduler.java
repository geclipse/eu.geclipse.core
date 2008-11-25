/*****************************************************************************
 * Copyright (c) 2008, g-Eclipse Consortium 
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
 *    Szymon Mueller - PSNC - Initial API and implementation
 *****************************************************************************/
package eu.geclipse.core.internal.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.geclipse.core.JobStatusUpdater;
import eu.geclipse.core.Preferences;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobStatus;


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
  
  private ArrayList<JobStatusUpdater> startingUpdaters = new ArrayList<JobStatusUpdater>();
  
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
    
    if( this.startingUpdaters.size() > 0 ) {
      Iterator<JobStatusUpdater> iterator = this.startingUpdaters.iterator();
      int updatePeriod = Preferences.getUpdateJobsPeriod();
      ArrayList<JobStatusUpdater> resumedUpdaters = new ArrayList<JobStatusUpdater>();
      while( iterator.hasNext() && Preferences.getUpdateJobsStatus() ) {
        JobStatusUpdater updater = iterator.next();
        IGridJob job = updater.getJob();
        IGridJobStatus updateJobStatus = job.updateJobStatus( monitor, false );
        if( updateJobStatus.getReason() != null && updateJobStatus.getReason().equals( "Token request canceled" )) {
          if( Preferences.getJobUpdaterCancelBehaviour() ) {
            Preferences.setUpdateJobsStatus( false );
          }
        } else {
          updater.statusUpdated( updateJobStatus );
          if( updateJobStatus.canChange() ) {
            updater.schedule( updatePeriod );
            this.workingUpdaters.put( updater, Integer.valueOf( UPDATER_NORMAL_PRIORITY ) );
          }
          resumedUpdaters.add( updater );
        }
      }
      for( JobStatusUpdater up: resumedUpdaters ) {
        this.startingUpdaters.remove( up );
      }
    }
    
    if( Preferences.getUpdateJobsStatus() && this.sleepingUpdaters.size() > 0 
        && ( getNumberOfRunningUpdaters() < Preferences.getUpdatersLimit() ) )
    {
      JobStatusUpdater updaterToWakeUp = null;
      if( this.sleepingUpdaters.contains( Integer.valueOf( UPDATER_HIGH_PRIORITY ) ) )
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
      this.workingUpdaters.put(  updaterToWakeUp, Integer.valueOf( UPDATER_NORMAL_PRIORITY ) );
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
      this.startingUpdaters.add( updater );
    } else {
      this.sleepingUpdaters.put( updater, Integer.valueOf( UPDATER_NORMAL_PRIORITY ) );
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
      this.sleepingUpdaters.put( updater, Integer.valueOf( UPDATER_NORMAL_PRIORITY ) );
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
      updater.wakeUpdater();
      this.workingUpdaters.put( updater, Integer.valueOf( UPDATER_NORMAL_PRIORITY ) );
      this.sleepingUpdaters.remove( updater );
      updater.schedule( 30000 );
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

