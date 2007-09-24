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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.core.jobs;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

import eu.geclipse.core.model.IGridJob;


/**
 * Manages downloading of output files for submitted and done jobs
 */
public class JobOutputsDownloaderManager {
  /**
   * Information how job was scheduled
   */
  public enum ScheduleStatus {
    /**
     * Downloading was scheduled
     */
    SUCCESSFULLY_SCHEDULED,
    
    /**
     * Downloading was not scheduled, because downloading for this job is already scheduled or running 
     */
    ALREADY_SCHEDULED    
  }
  
  static private JobOutputsDownloaderManager singletone; 
  Map<String, JobOutputsDownloader> downloaders;
  private IJobChangeListener jobChangeListener;
  
  /**
   * 
   */
  private JobOutputsDownloaderManager() {
    super();
    this.downloaders = Collections.synchronizedMap( new HashMap<String, JobOutputsDownloader>() );
  }
  
  /**
   * @return the manager
   */
  static public JobOutputsDownloaderManager getManager() {
    if( singletone == null ) {
      singletone = new JobOutputsDownloaderManager();
      
    }
    return singletone;
  }
  
  /**
   * Schedule task for downloading of output files produces by job during execution
   * @param gridJob job, for which output files should be downloaded
   * @param userAction <code>true</code> if downloading was start by user, <code>false</code> if task should be running in background 
   * @return schedule status
   */
  public ScheduleStatus scheduleDownloading( final IGridJob gridJob, final boolean userAction )
  {
    ScheduleStatus status = ScheduleStatus.SUCCESSFULLY_SCHEDULED;
    JobOutputsDownloader downloader = this.downloaders.get( gridJob.getID()
      .getJobID() );
    if( downloader == null ) {
      downloader = createNewDownloader( gridJob, userAction );
    } else {
      switch( downloader.getState() ) {
        case Job.NONE:
          downloader.schedule();
        break;
        case Job.SLEEPING:
          downloader.wakeUp();
          status = ScheduleStatus.ALREADY_SCHEDULED;
        break;
        
        case Job.WAITING:
        case Job.RUNNING:
          status = ScheduleStatus.ALREADY_SCHEDULED;
          break;
      }
    }
    return status;
  }

  private JobOutputsDownloader createNewDownloader( final IGridJob gridJob,
                                   final boolean userAction )
  {
    JobOutputsDownloader downloader;
    downloader = new JobOutputsDownloader( gridJob );
    this.downloaders.put( gridJob.getID().getJobID(), downloader );
    downloader.setUser( userAction );
    downloader.addJobChangeListener( getJobChangeListener() );
    downloader.schedule();
    return downloader;
  }

  private IJobChangeListener getJobChangeListener() {
    if( this.jobChangeListener == null ) {
      this.jobChangeListener = new JobChangeAdapter() {

        /* (non-Javadoc)
         * @see org.eclipse.core.runtime.jobs.JobChangeAdapter#done(org.eclipse.core.runtime.jobs.IJobChangeEvent)
         */
        @Override
        public void done( final IJobChangeEvent event ) {
          JobOutputsDownloader downloader = (JobOutputsDownloader)event.getJob();
          JobOutputsDownloaderManager.this.downloaders.remove( downloader.getGridJob().getID().getJobID() );
          downloader.removeJobChangeListener( this );
          super.done( event );
        }};
    }
    
    return this.jobChangeListener;
  }  
}
