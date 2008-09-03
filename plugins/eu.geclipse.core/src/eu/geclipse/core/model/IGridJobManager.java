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
 *    Szymon Mueller - additional methods for updaters
 *****************************************************************************/

package eu.geclipse.core.model;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * A job manager is able to manage {@link IGridJob}.
 */
public interface IGridJobManager
    extends IGridElementManager {
  
  public void addJobStatusListener( IGridJobStatusListener listener );

  public void addJobStatusListener(IGridJob[] jobs, int status, IGridJobStatusListener listener);

  public void removeJobStatusListener(IGridJobStatusListener listener);

  /**
   * Stops refreshing all job status updaters
   */
  public void pauseAllUpdaters();
  
  /**
   * Starts refreshing all job status udapters
   */
  public void wakeUpAllUpdaters();
  
  /**
   * Tells updaters of the given jobs to update statues of the jobs.
   * @param selectedJobs to have its status updated
   */
  public void updateJobsStatus( final ArrayList< IGridJob > selectedJobs );
  
  /**
   * Tells updater of the given job that status has been changed externally
   * (i.e. job status wasn't updated in job status updater).
   * @param job Job, which status has changed.
   */
  public void jobStatusChanged( final IGridJob job );

  /**
   * Tells updater of the given job that status has been updated (maybe no changed)
   * @param job
   */
  public void jobStatusUpdated( IGridJob job );
  
  /**
   * Cancel current operation in job status updater and remove it from job scheduler
   * @param job the job, for which updater will be removed
   * @param waitForUpdater <code>true</code> if this metchod should wait to moment, when job updater finish current operation
   * @param monitor optionally monitor, which is checked during waiting for finishing updater
   */
  public void removeJobStatusUpdater( final IGridJob job, final boolean waitForUpdater, final IProgressMonitor monitor );
}
