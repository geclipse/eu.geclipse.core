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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.geclipse.core.GridException;
import eu.geclipse.core.jobs.internal.Activator;
import eu.geclipse.core.model.IGridJob;


/**
 * Job, which downloads from grid output files for submitted job
 */
public class JobOutputsDownloader extends Job {
  private IGridJob gridJob;

  /**
   * @param gridJob job, for which output files should be downloaded
   */
  JobOutputsDownloader( final IGridJob gridJob ) {
    super( createDownloaderName( gridJob ) );
    this.gridJob = gridJob;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected IStatus run( final IProgressMonitor monitor ) {
    IStatus status = null;
    try {
      status = this.gridJob.downloadOutputs( monitor );
    } catch( GridException exception ) {
      status = exception.getStatus();
      if( status == null ) {
        status = new Status( IStatus.ERROR,
                             Activator.PLUGIN_ID,
                             0,
                             Messages.getString( "JobOutputsDownloader.errCannotDownloadJobOutput" ), exception ); //$NON-NLS-1$
      }
    }
    // default status if wasn't returned anywhere
    if( status == null ) {
      status = new Status( IStatus.ERROR,
                           Activator.PLUGIN_ID,
                           0,
                           Messages.getString( "JobOutputsDownloader.errCannotDownloadJobOutput" ), null ); //$NON-NLS-1$      
    }
    return status;
  }
  
  private static String createDownloaderName( final IGridJob gridJob ) {
    String jobName = gridJob.getName();
    
    if( jobName == null 
        || jobName.length() == 0 ) {
      jobName = gridJob.getID().getJobID();
    }
    
    return Messages.getString("JobOutputsDownloader.jobName") + jobName; //$NON-NLS-1$
  }

  
  /**
   * @return the gridJob
   */
  IGridJob getGridJob() {
    return this.gridJob;
  }
}