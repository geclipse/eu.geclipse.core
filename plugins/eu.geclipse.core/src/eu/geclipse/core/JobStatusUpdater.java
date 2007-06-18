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
 *****************************************************************************/
package eu.geclipse.core;

import java.util.Enumeration;
import java.util.Hashtable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.internal.model.JobManager;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.core.model.IGridJobStatusListener;

/**
 * The class for updating job status in the background. There is one JobStatusUpdater 
 * for each job and tt is started when a job is added to project.  
 * @see JobManager
 * @see IGridJobStatus
 * @see IGridJob
 *
 */
public class JobStatusUpdater extends Job {

  /**
   * Hashtable that hold infomation about registered listeners and the statuses for
   * which listener should be notified
   * @seeIGridJobStatusListener
   */
  Hashtable<IGridJobStatusListener, Integer> listeners=new Hashtable<IGridJobStatusListener, Integer>();
//  static Hashtable<String, JobStatusUpdater> updaters = new Hashtable<String, JobStatusUpdater>();
  
  IGridJob job;

  public JobStatusUpdater( final IGridJob job ) {
    super( "Grid Job Status Updater" );
    this.job = job;
//    updaters.put( job.getID().getJobID(), this );
  }

  @Override
  protected IStatus run( final IProgressMonitor monitor ) {
    int oldType = IGridJobStatus.UNKNOWN; 
    int newType = IGridJobStatus.UNKNOWN; 
    IGridJobStatus status = this.job.getJobStatus();
    if(status!=null){
      oldType=status.getType();
    }
    // IGridJobStatus jobStatus = updateService.getJobStatus( job.getID() );
    try{
      this.job.updateJobStatus();
      status = this.job.getJobStatus();
      if(status!=null){
        newType=status.getType();
      }
      if( oldType != newType ) {
      // status changed, notify all listeners;
      for(Enumeration<IGridJobStatusListener> e=listeners.keys();e.hasMoreElements();){
        IGridJobStatusListener listener =e.nextElement();
        int trigger=listeners.get( listener );
        if( (newType & trigger)>0){
          listener.statusChanged( job );
        }
      }
    }
    }
    catch(RuntimeException e){
      Activator.logException( e );
    }
    if( this.job.getJobStatus().canChange() ) {
      schedule( 30000 );
    }
    else{
      JobManager.getManager().removeUpdater( this );
    }
    return Status.OK_STATUS;
  }
  

  /**
   * Add status listener for the updater. The listener will be notify, when the status
   * of the job will change. 
   * @param status - aggregate value of IGridJobStatus types, for which listener 
   * should be notified.
   * @param listener - listener to be notifies about the change.
   */
  public void addJobStatusListener( final int status, final IGridJobStatusListener listener ) {
    listeners.put( listener, status );
  }

  /**
   * Removes registrered job status listener
   * @param listener
   */
  public void removeJobStatusListener( final IGridJobStatusListener listener ) {
    listeners.remove( listener );
  }

}