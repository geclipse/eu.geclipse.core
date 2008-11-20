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
 *    Pawel Wolniewicz 
 *    Szymon Mueller
 *****************************************************************************/
package eu.geclipse.core;

import java.util.Enumeration;
import java.util.Hashtable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.internal.model.JobManager;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.core.model.IGridJobStatusListener;

/**
 * The class for updating job status in the background. There is one JobStatusUpdater 
 * for each job and it is started when a job is added to project.  
 * @see JobManager
 * @see IGridJobStatus
 * @see IGridJob
 *
 */
public class JobStatusUpdater extends Job {
  /**
   * Hashtable that hold information about registered listeners and the statuses for
   * which listener should be notified
   * @seeIGridJobStatusListener
   */
  private Hashtable<IGridJobStatusListener, Integer> listeners=new Hashtable<IGridJobStatusListener, Integer>();
  private IGridJob job = null;
  private IGridJobStatus lastStatus=null;
  
  /**
   * Flag set to <code>true</code>, when {@link IGridJob} is removed, so
   * {@link JobStatusUpdater} should stop to check current job status
   */
  private boolean jobRemoved;
  
  /**
   * Constructor
   * @param job for which this updater is created
   */
  public JobStatusUpdater( final IGridJob job ) {
    super( Messages.getString("JobStatusUpdater.name") ); //$NON-NLS-1$
    this.job = job;
  }
  
  
  /**
   * @return Job for which this updater is running.
   */
  public IGridJob getJob() {
    return this.job;
  }

  @Override
  protected IStatus run( final IProgressMonitor monitor ) {
    SubMonitor subMonitor = SubMonitor.convert( monitor );
    testCancelled( subMonitor );
    if( Preferences.getUpdateJobsStatus() ) {
      int oldType = -1;
      if( this.lastStatus != null ) {
        oldType = this.lastStatus.getType();
      }
      int newType = IGridJobStatus.UNKNOWN;
      IGridJobStatus newStatus = null;
      try {
        if( this.job != null ) {
          testCancelled( subMonitor );
          this.job.updateJobStatus( subMonitor, false );
          newStatus = this.job.getJobStatus();
        }
        if( newStatus != null ) {
          newType = newStatus.getType();
          this.lastStatus = newStatus;
        }
        if( oldType != newType ) {
          for( Enumeration<IGridJobStatusListener> e = this.listeners.keys(); e.hasMoreElements(); )
          {
            testCancelled( subMonitor );
            IGridJobStatusListener listener = e.nextElement();
            int trigger = this.listeners.get( listener ).intValue();
            if( ( newType & trigger ) > 0 ) {
              listener.statusChanged( this.job );              
            }
            listener.statusUpdated( this.job );
          }
        } else {
          for( Enumeration<IGridJobStatusListener> e = this.listeners.keys(); e.hasMoreElements(); )
          {
            testCancelled( subMonitor );
            IGridJobStatusListener listener = e.nextElement();
            listener.statusUpdated( this.job );
          }
        }
      } catch( RuntimeException e ) {
        Activator.logException( e );
      }
      if( newStatus != null && newStatus.getReason() != null && newStatus.getReason().equals( "Token request canceled" ) ) {
        if( Preferences.getJobUpdaterCancelBehaviour() ) {
          Preferences.setUpdateJobsStatus( false );
          JobManager.getManager().pauseUpdater( this );
        }
      }
      if( !this.jobRemoved
          && !monitor.isCanceled()
          && newStatus != null
          && newStatus.canChange() )
      {
        int updatePeriod = Preferences.getUpdateJobsPeriod();
        schedule( updatePeriod );
      } else {
        JobManager.getManager().removeJobStatusUpdater( this.job,
                                                        false,
                                                        monitor );
      }
    }
    return Status.OK_STATUS;
  }

  private void testCancelled( final SubMonitor subMonitor ) {
    if( subMonitor.isCanceled() ) {
      throw new OperationCanceledException();
    }
    
  }


  /**
   * Used when job status was updated outside of the updater. Checks if status
   * changed from previous update. If so informs all listeners that status has
   * changed.
   * 
   * @param newStatus Fetched status
   */
  public void statusUpdated( final IGridJobStatus newStatus ) {
    int oldType = -1;
    if( this.lastStatus != null ) {
      oldType = this.lastStatus.getType();
    }
    if( newStatus != null ) {
      int newType = newStatus.getType();
      if( oldType != newType ) {
        this.lastStatus = newStatus;
        for( Enumeration<IGridJobStatusListener> e = this.listeners.keys(); e.hasMoreElements(); )
        {
          IGridJobStatusListener listener = e.nextElement();
          int trigger = this.listeners.get( listener ).intValue();
          if( ( newType & trigger ) > 0 ) {
            listener.statusChanged( this.job );
          }
          listener.statusUpdated( this.job );
        }
      } else {
        for( Enumeration<IGridJobStatusListener> e = this.listeners.keys(); e.hasMoreElements(); )
        {
          IGridJobStatusListener listener = e.nextElement();
          listener.statusUpdated( this.job );
        }
      }
    }
  }
  
  /**
   * Add status listener for the updater. The listener will be notify, when the
   * status of the job will change.
   * 
   * @param status - aggregate value of IGridJobStatus types, for which listener
   *            should be notified.
   * @param listener - listener to be notifies about the change.
   */
  public void addJobStatusListener( final int status, final IGridJobStatusListener listener ) {
    this.listeners.put( listener, Integer.valueOf( status ) );
  }

  /**
   * Removes registrered job status listener
   * @param listener
   */
  public void removeJobStatusListener( final IGridJobStatusListener listener ) {
    this.listeners.remove( listener );
  }

  /**
   * Mark {@link IGridJob} connected with this updater as removed, what means
   * that this updater should stop to schedule itself.<br/> Note, that this is
   * not the same as cancelled, because cancel interupt only currect updating,
   * so next updating will be scheduled
   */
  public void setRemoved() {
    this.jobRemoved = true;
  }
  
  public void wakeUpdater() {
    wakeUp();
  }

}