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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.compare.IContentChangeListener;
import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.core.runtime.ListenerList;

import eu.geclipse.batch.IBatchJobInfo;
import eu.geclipse.batch.IBatchJobInfo.JobState;
import eu.geclipse.batch.internal.BatchJobInfo;
import eu.geclipse.core.reporting.ProblemException;

/**
 * The <code>BatchJobManager</code> manages all types of jobs present in the 
 * batch system.
 */
public class BatchJobManager implements IContentChangeNotifier {
  /**
   * The internal list of managed jobs.
   */
  private LinkedHashMap< String, IBatchJobInfo >  jobs; 
  
  /**
   * This list holds the currently registered IContentChangeListeners. 
   */
  private ListenerList ccListeners;
  
  private boolean done;
  /**
   * Constructor
   */
  public BatchJobManager() {
    this.jobs = new LinkedHashMap< String, IBatchJobInfo > ();
    this.ccListeners = new ListenerList();
    this.done = false;
  }
  
  /**
   * Searches for a job that matches the specified jobId. If no job
   * could be found <code>null</code> is returned.
   * 
   * @param jobId The unique id of the job.
   * @return The job with the specific jobId or null if no such job could be 
   * found.
   */
  public synchronized IBatchJobInfo findJob( final String jobId ) {
    return this.jobs.get( jobId );
  }
  
  
  /**
   * Update our local state with the current state, by only keeping the 
   * active jobs. I.e. remove all the jobs that are not active anymore. 
   * 
   * @param currentActiveJobs These are the currently active jobs.
   */
  public synchronized void removeOld( final List< IBatchJobInfo > currentActiveJobs ) {
    IBatchJobInfo jobInfo;
    boolean changed = false;
    
    Iterator< String > iter = this.jobs.keySet().iterator();
    
    while ( iter.hasNext() ) {
      String jobId = iter.next();
      boolean found = false;
      for ( int i = 0; i < currentActiveJobs.size(); ++i ) {
        jobInfo = currentActiveJobs.get( i );
        if ( jobId.equals( jobInfo.getJobId() ) )
          found = true;
      }
      
      // Queue is removed
      if ( !found ) {
        iter.remove();
        //this.removedResources.add( this.jobs.get( jobId ) );
        changed = true;
      }
    }
    
    if ( changed )
      this.fireContentChanged();
  }
  
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
  public synchronized IBatchJobInfo addMerge( final String jobId, final String jobName, final String userAccount,
                        final String timeUse, final JobState status, final String queueName,
                        final IBatchService batchWrapper ) {
    IBatchJobInfo jobInfo = null; 
    boolean changed = false;
    
    // We don't update any of the jobs when we are done
    if ( !this.done ) {
      jobInfo = this.findJob( jobId );
    
      if ( null == jobInfo ) {
        jobInfo = new BatchJobInfo( jobId, jobName, userAccount, timeUse, status, queueName, batchWrapper );
        this.jobs.put( jobId, jobInfo );
        changed = true;
      } else { // Did the state of this job change ??
        if ( jobInfo.getStatus() != status ) {
          jobInfo.setStatus( status );
          changed = true;
        }
        if ( !jobInfo.getTimeUse().equals( timeUse ) ) {
          jobInfo.setTimeUse( timeUse );
         changed = true;
        }
      }
    }
    
    if ( changed ) 
      this.fireContentChanged();
    
    return jobInfo;
  }
  
  /**
   * Get all jobs that are currently available.
   * 
   * @return A copy of the internal list of managed jobs.
   */
  public synchronized List< IBatchJobInfo > getJobs() {
    return new ArrayList< IBatchJobInfo >( this.jobs.values() );
  }

  /**
   * Get all jobs in the specified queue that are currently available.
   * 
   * @param queueName The name of the queue that we want the jobs from 
   * @return A copy of the internal list of managed jobs in the specified 
   * queue.
   */
  public synchronized List< IBatchJobInfo > getJobs( final String queueName ) {
    IBatchJobInfo jobInfo;
    List < IBatchJobInfo > retJobs = new ArrayList< IBatchJobInfo >();
    Iterator< IBatchJobInfo > iter = this.jobs.values().iterator();
    
    while ( iter.hasNext() ) {
      jobInfo = iter.next();
      
      if ( jobInfo.getQueueName().equals( queueName ) ) {
        retJobs.add( jobInfo );
      }
    }

    return retJobs;
  }

  /**
   * Returns a list of the jobs that are specified in the incoming list. 
   * @param jobIds A list of Ids for the jobs to be returned.
   * @return Returns a list of {@link IBatchJobInfo} jobs.
   */
  public synchronized List< IBatchJobInfo > getJobs( final List< String > jobIds ) {
    IBatchJobInfo jobInfo;
    List < IBatchJobInfo > retJobs = new ArrayList< IBatchJobInfo >();

    for ( String jobId : jobIds ) {
      jobInfo = this.jobs.get( jobId );
      
      if ( null != jobInfo )
        retJobs.add( jobInfo );
    }
    
    return retJobs;
  }
  
  /**
   * Get the number of currently available jobs.
   * 
   * @return The number of jobs that are currently contained in the internal 
   * list of jobs in the batch system.
   */
  public synchronized int getJobCount() {
    return this.jobs.size();
  }
  
  /**
   * Determine if this job manager's list of managed jobs is currently empty.
   * 
   * @return True if there are no jobs available, false otherwise.
   */
  public synchronized boolean isEmpty() {
    return getJobCount() == 0;
  }
  
  /**
   * Add a new {@link IContentChangeListener} to the list of listeners. The 
   * listeners are always informed when a new job is added to or an old job 
   * is removed from the internal list of managed jobs.
   * 
   * @param listener The {@link IContentChangeListener} that should be added 
   *                 to the list of listeners.
   * @see #removeContentChangeListener(IContentChangeListener)
   * @see org.eclipse.compare.IContentChangeNotifier#
   *     addContentChangeListener(org.eclipse.compare.IContentChangeListener)
   */
  public void addContentChangeListener( final IContentChangeListener listener ) {
    this.ccListeners.add( listener );
  }

  
  /**
   * Remove the specified {@link IContentChangeListener} from the list of 
   * listener. This listener will not longer be informed about changes made 
   * to the list of managed jobs.
   * 
   * @param listener The {@link IContentChangeListener} that should be removed.
   * @see org.eclipse.compare.IContentChangeNotifier#
   *    removeContentChangeListener(org.eclipse.compare.IContentChangeListener)
   */
  public void removeContentChangeListener( final IContentChangeListener listener ) {
    this.ccListeners.remove( listener );
  }
  
  /**
   * Remove the specified job from the list of managed jobs.
   * 
   * @param job The job to be removed.
   * @return True if the job was contained in the list of managed jobs.
   */
  protected /*synchronized */boolean removeJob( final IBatchJobInfo job ) {
    IBatchJobInfo removedJob = this.jobs.remove( job.getJobId() );
    
//    if ( null != removedJob ) { 
//      fireContentChanged();
//    }

    return null != removedJob;
  }
  
  /**
   * Delete the specified {@link IBatchJobInfo}. This means especially that the
   * specified job is removed from the list of currently managed jobs. 
   *  
   * @param job The {@link IBatchJobInfo} to be deleted.
   * @throws ProblemException If the job could not be successfully deleted.
   */
  public /*synchronized */void deleteJob( final IBatchJobInfo job ) throws ProblemException {
    if ( job.isDeletable() ) {
      job.deleteJob();
      removeJob( job );
    }
  }
  
  /**
   * Move the specified {@link IBatchJobInfo}. This means especially that the
   * specified job is also removed from the list of currently managed jobs. 
   *  
   * @param job The {@link IBatchJobInfo} to be deleted.
   * @param destQueue The destination queue.
   * @param destServer The destination server.
   * @throws ProblemException If the job could not be successfully moved.
   */
  public /*synchronized */void moveJob( final IBatchJobInfo job, final String destQueue, 
                       final String destServer ) throws ProblemException {
    if ( job.isMovable() ) {
      job.moveJob( destQueue, destServer );
      removeJob( job );
    }
  }

  /**
   * Puts the specified {@link IBatchJobInfo} on hold.
   *
   * @param job The {@link IBatchJobInfo} to be put on hold.
   * @throws ProblemException If command is not executed successfully
   */
  public /*synchronized */void holdJob( final IBatchJobInfo job ) throws ProblemException {
    if ( job.isHoldable() ) {
      job.holdJob();
      job.setStatus( IBatchJobInfo.JobState.H );
//      fireContentChanged();
    }
  }

  /**
   * Release the specified {@link IBatchJobInfo}.
   *
   * @param job The {@link IBatchJobInfo} to be released.
   * @throws ProblemException If command is not executed successfully
   */
  public /*synchronized */void releaseJob( final IBatchJobInfo job ) throws ProblemException {
    if ( job.isReleasable() ) {
      job.releaseJob();
      job.setStatus( IBatchJobInfo.JobState.Q );
//      fireContentChanged();
    }
  }

  /**
   * ReRun the specified {@link IBatchJobInfo}.
   *
   * @param job The {@link IBatchJobInfo} to be reRun.
   * @throws ProblemException If command is not executed successfully
   */
  public /*synchronized */void reRunJob( final IBatchJobInfo job ) throws ProblemException {
    if ( job.isReRunnable() ) {
      job.reRunJob();
//      fireContentChanged();
    }
  }

  /**
   * This method clears all the jobs managed by this manager.
   */
  public synchronized void removeAll() {
    this.done = true;
    this.jobs.clear();
    
    this.fireContentChanged();
  }
  
  /**
   * Notify all registered IContentChangeListeners about content changes, 
   * i.e. a new job was added or an existing job was removed.
   * NOTE: WARNING make sure that all the methods that are called performs
   * their actions within a asyncExec block.
   */
  protected synchronized void fireContentChanged() {
    Object[] list = this.ccListeners.getListeners();

    for ( int i = 0 ; i < list.length ; i++ ) {
      if ( list[i] instanceof IContentChangeListener ) {
        IContentChangeListener listener = ( IContentChangeListener ) list[i];
        listener.contentChanged( this );
      }
    }
  }
}
