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
package eu.geclipse.batch.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.IContentChangeListener;
import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.core.runtime.ListenerList;

import eu.geclipse.batch.IBatchJobInfo;
import eu.geclipse.core.reporting.ProblemException;

/**
 * The <code>BatchJobManager</code> manages all types of jobs present in the batch system.
 */
public class BatchJobManager implements IContentChangeNotifier {
  
  /**
   * The singleton that holds the instance of this
   * <code>BatchJobManager</code>.
   */
  static private BatchJobManager singleton = null;
  
  /**
   * The internal list of managed jobs.
   */
  private List<IBatchJobInfo> jobs = new ArrayList<IBatchJobInfo>();
  
  /**
   * This list holds the currently registered IContentChangeListeners. 
   */
  private ListenerList ccListeners = new ListenerList();
  
  /**
   * Private constructor. Use the {@link getManager} method to get the singleton.
   */
  private BatchJobManager() {
    // empty implementation
  }
  
  /**
   * Get the singleton. There is only one instance of the <code>BatchJobManager</code>
   * that is created and returned by this method.
   * 
   * @return The singleton.
   */
  public static BatchJobManager getManager() {
    if ( singleton == null ) {
      singleton = new BatchJobManager();
    }
    return singleton;
  }
  
  /**
   * Searches for a job that matches the specified jobId. If no job
   * could be found <code>null</code> is returned.
   * 
   * @param jobId The unique id of the job.
   * @return The job with the specific jobId or null if no such job could be found.
   */
  public IBatchJobInfo findJob( final String jobId ) {
    IBatchJobInfo resultJob = null;
    for ( IBatchJobInfo job : this.jobs ) {
      if ( job.getJobId().equals( jobId ) ) {
        resultJob = job;
        break;
      }
    }
    return resultJob;
  }
  
  /**
   * Get all jobs that are currently available.
   * 
   * @return A copy of the internal list of managed jobs.
   */
  public List< IBatchJobInfo > getJobs() {
    return new ArrayList< IBatchJobInfo >( this.jobs );
  }

  /**
   * Get the number of currently available jobs.
   * 
   * @return The number of jobs that are currently contained in the internal list of
   * jobs in the batch system.
   */
  public int getJobCount() {
    return this.jobs.size();
  }
  
  /**
   * Determine if this job manager's list of managed jobs is currently empty.
   * 
   * @return True if there are no jobs available, false otherwise.
   */
  public boolean isEmpty() {
    return getJobCount() == 0;
  }
  
  /**
   * Add a new {@link IContentChangeListener} to the list of listeners. The listeners are
   * always informed when a new job is added to or an old job is removed from the
   * internal list of managed jobs.
   * 
   * @param listener The {@link IContentChangeListener} that should be added to the list
   *                 of listeners.
   * @see #removeContentChangeListener(IContentChangeListener)
   * @see org.eclipse.compare.IContentChangeNotifier#addContentChangeListener(org.eclipse.compare.IContentChangeListener)
   */
  public void addContentChangeListener( final IContentChangeListener listener ) {
    this.ccListeners.add( listener );
  }

  
  /**
   * Remove the specified {@link IContentChangeListener} from the list of listener. This
   * listener will not longer be informed about changes made to the list of managed jobs.
   * 
   * @param listener The {@link IContentChangeListener} that should be removed.
   * @see org.eclipse.compare.IContentChangeNotifier#removeContentChangeListener(org.eclipse.compare.IContentChangeListener)
   */
  public void removeContentChangeListener( final IContentChangeListener listener ) {
    this.ccListeners.remove( listener );
  }
  
  /**
   * Add the specified IJobInfo to the list of managed jobs.
   * 
   * @param job The job to be added.
   */
  protected void addJob( final IBatchJobInfo job ) {
    this.jobs.add( job );
    fireContentChanged();
  }
  
  /**
   * Clear all the jobs from the managed list.
   */
  protected void clear() {
    this.jobs.clear();
    fireContentChanged();
  }

  /**
   * Remove the specified job from the list of managed jobs.
   * 
   * @param job The job to be removed.
   * @return True if the job was contained in the list of managed jobs.
   */
  protected boolean removeJob( final IBatchJobInfo job ) {
    boolean result = this.jobs.remove( job );
    if ( result ) {
      fireContentChanged();
    }
    return result;
  }
  
  /**
   * Delete the specified {@link IBatchJobInfo}. This means especially that the
   * specified job is removed from the list of currently managed jobs. 
   *  
   * @param job The {@link IBatchJobInfo} to be deleted.
   * @throws ProblemException If the job could not be successfully deleted.
   */
  public void deleteJob( final IBatchJobInfo job ) throws ProblemException {
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
  public void moveJob( final IBatchJobInfo job, final String destQueue, 
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
  public void holdJob( final IBatchJobInfo job ) throws ProblemException {
    if ( job.isHoldable() ) {
      job.holdJob();
      job.setStatus( IBatchJobInfo.JobState.H );
      fireContentChanged();
    }
  }

  /**
   * Release the specified {@link IBatchJobInfo}.
   *
   * @param job The {@link IBatchJobInfo} to be released.
   * @throws ProblemException If command is not executed successfully
   */
  public void releaseJob( final IBatchJobInfo job ) throws ProblemException {
    if ( job.isReleasable() ) {
      job.releaseJob();
      job.setStatus( IBatchJobInfo.JobState.Q );
      fireContentChanged();
    }
  }

  /**
   * Notify all registered IContentChangeListeners about content changes, i.e. a new job was
   * added or an existing job was removed.
   */
  protected void fireContentChanged() {
    Object[] list = this.ccListeners.getListeners();
    for ( int i = 0 ; i < list.length ; i++ ) {
      if ( list[i] instanceof IContentChangeListener ) {
        IContentChangeListener listener = ( IContentChangeListener ) list[i];
        listener.contentChanged( this );
      }
    }
  }
  
}
