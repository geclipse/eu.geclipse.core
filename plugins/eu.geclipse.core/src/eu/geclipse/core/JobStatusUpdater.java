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
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.internal.model.JobManager;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.core.model.IGridJobStatusListener;
import eu.geclipse.core.model.IGridJobStatusService;

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
  Hashtable<IGridJobStatusListener, Integer> listeners=new Hashtable<IGridJobStatusListener, Integer>();
//  static Hashtable<String, JobStatusUpdater> updaters = new Hashtable<String, JobStatusUpdater>();
  
  IGridJobID jobID;
  IGridJob job;
  IGridJobStatus lastStatus=null;
  
  /**
   * Constructor
   * @param job for which this updater is created
   */
  public JobStatusUpdater( final IGridJob job ) {
    super( Messages.getString("JobStatusUpdater.name") ); //$NON-NLS-1$
    this.job = job;
  }
  
  /**
   * Constructor
   * @param jobID of job for which this updater is created
   */
  public JobStatusUpdater( final IGridJobID jobID ) {
    super( Messages.getString("JobStatusUpdater.name") ); //$NON-NLS-1$
    this.jobID = jobID;
  }
  
  
  /**
   * @return Job for which this updater is running.
   */
  public IGridJob getJob() {
    return this.job;
  }

  @Override
  protected IStatus run( final IProgressMonitor monitor ) {
    if( this.job != null ) {
      // if( JobManager.getManager().getNumberOfRunningUpdaters() <=
      // Preferences.getUpdatersLimit() )
      // {
      if( Preferences.getUpdateJobsStatus() ) {
        int oldType = -1;
        if( this.lastStatus != null ) {
          oldType = this.lastStatus.getType();
        }
        int newType = IGridJobStatus.UNKNOWN;
        IGridJobStatus newStatus = null;
        try {
          if( this.job != null ) {
            this.job.updateJobStatus();
            newStatus = this.job.getJobStatus();
          } else {
            List<IGridElementCreator> elementCreators = GridModel.getElementCreators( IGridJobStatusService.class );
            for( IGridElementCreator creator : elementCreators ) {
              if( creator.canCreate( this.jobID ) ) {
                try {
                  IGridJobStatusService service = ( IGridJobStatusService )creator.create( null );
                  newStatus = service.getJobStatus( this.jobID );
                } catch( GridModelException e ) {
                  // empty implementation
                } catch( GridException e ) {
                  // empty implementation
                }
              }
            }
          }
          if( newStatus != null ) {
            newType = newStatus.getType();
            this.lastStatus = newStatus;
          }
          if( oldType != newType )
            for( Enumeration<IGridJobStatusListener> e = this.listeners.keys(); e.hasMoreElements(); )
            {
              IGridJobStatusListener listener = e.nextElement();
              int trigger = this.listeners.get( listener ).intValue();
              if( ( newType & trigger ) > 0 ) {
                listener.statusChanged( this.job );
              }
            }
        } catch( RuntimeException e ) {
          Activator.logException( e );
        }
        if( newStatus != null && newStatus.canChange() ) {
          int updatePeriod = Preferences.getUpdateJobsPeriod();
          schedule( updatePeriod );
        } else {
          JobManager.getManager().removeUpdater( this );
        }
      }
    }
    return Status.OK_STATUS;
  }

  /**
   * Used when job status was updated outside of the updater.
   * Checks if status changed from previous update. If so informs
   * all listeners that status has changed.
   * @param newStatus Fetched status 
   */
  public void statusUpdated( final IGridJobStatus newStatus ) {
    int oldType = -1;
    if( this.lastStatus != null ) {
      oldType = this.lastStatus.getType();
    }
    if ( newStatus != null && this.lastStatus !=null ) {
        int newType = newStatus.getType();
        if ( oldType != newType ) {
          this.lastStatus = newStatus;
          for( Enumeration<IGridJobStatusListener> e = this.listeners.keys(); e.hasMoreElements(); )
          {
            IGridJobStatusListener listener = e.nextElement();
            int trigger = this.listeners.get( listener ).intValue();
            if( ( newType & trigger ) > 0 ) {
              listener.statusChanged( this.job );
            }
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
    this.listeners.put( listener, new Integer( status ) );
  }

  /**
   * Removes registrered job status listener
   * @param listener
   */
  public void removeJobStatusListener( final IGridJobStatusListener listener ) {
    this.listeners.remove( listener );
  }

}