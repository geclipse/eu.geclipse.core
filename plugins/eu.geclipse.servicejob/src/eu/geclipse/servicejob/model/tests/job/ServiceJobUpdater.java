/*******************************************************************************
 * Copyright (c) 2008, g-Eclipse Consortium All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/
 * Contributors:
 *     Szymon Mueller - PSNC - Initial API and implementation
 ******************************************************************************/
package eu.geclipse.servicejob.model.tests.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridJobService;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.core.model.IServiceJobStatusListener;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.servicejob.Activator;
import eu.geclipse.servicejob.model.ServiceJobStates;

/**
 * Updater for the job based test.
 */
public class ServiceJobUpdater extends Job {

  private Set<IGridJobID> jobIDs = new HashSet<IGridJobID>();
  private List<IServiceJobStatusListener> listeners = new ArrayList<IServiceJobStatusListener>();
  private AbstractSubmittableServiceJob test;

  /**
   * Constructor of the JobTestUpdater class.
   * 
   * @param name Name of the updater.
   * @param test Parent test for this updater.
   */
  public ServiceJobUpdater( final String name, final AbstractSubmittableServiceJob test ) {
    super( name );
    this.test = test;
    this.listeners.add( ( IServiceJobStatusListener )GridModel.getTestManager() );
  }

  /**
   * Method adds new jobIDs for the updater to check.
   * 
   * @param jobIDsToAdd IDs of the jobs to be checked by this updater.
   */
  public void addSubTests( final List<IGridJobID> jobIDsToAdd ) {
    for( IGridJobID jobID : jobIDsToAdd ) {
      this.jobIDs.add( jobID );
    }
  }

  @Override
  protected IStatus run( final IProgressMonitor monitor ) {
    IStatus status = Status.OK_STATUS;
    Date lastRefreshTime = null;
    List<IGridJobID> jobIDsToDelete = new ArrayList<IGridJobID>();
    for( IGridJobID jobID : this.jobIDs ) {
      if( this.jobIDs.contains( jobID ) ) {
        IGridElementCreator srvCreator = GridModel.getElementCreator( jobID,
                                                                      IGridJobService.class );
        if( srvCreator != null ) {
          try {
            IGridJobService jobService = ( IGridJobService )srvCreator.create( null );
            // TODO szymon !!! pass correct VO instead of null
            IGridJobStatus newStatus = jobService.getJobStatus( jobID, null, monitor );
            lastRefreshTime = newStatus.getLastUpdateTime();
            if( newStatus.canChange() ) {
              this.test.setJobResult( jobID,
                                      lastRefreshTime,
                                      Messages.getString("JobTestUpdater.running_status"), //$NON-NLS-1$
                                      ServiceJobStates.RUNNING.getAlias() );
            } else {
              jobIDsToDelete.add( jobID );
              //TODO maybe change to some sort of FINISHED result enum ?
              this.test.setJobResult( jobID,
                                      lastRefreshTime,
                                      Messages.getString("JobTestUpdater.finished_status"), //$NON-NLS-1$
                                      ServiceJobStates.OK.getAlias() );
              this.test.computeJobResult( jobID, newStatus );
            }
          } catch( ProblemException exc ) {
            Activator.logException( exc );
          }
        } else {
          // TODO szymon handle situation, when service cannot be created/found
        }
      }
    }
    for( IGridJobID jobIDToDelete : jobIDsToDelete ) {
      this.jobIDs.remove( jobIDToDelete );
    }
    jobIDsToDelete.clear();
    if( this.jobIDs.size() > 0 ) {
      this.schedule( 60000 );
    } else {
      this.test.computeTestResult();
    }
    for( IServiceJobStatusListener listener : this.listeners ) {
      listener.statusChanged( this.test );
    }
    return status;
  }
}
