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
package eu.geclipse.servicejob.model.submittable.job;

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
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.servicejob.Activator;
import eu.geclipse.servicejob.model.ServiceJobStates;

/**
 * Updater for the job based service jobs.
 */
public class ServiceJobUpdater extends Job {

  private Set<IGridJobID> jobIDs = new HashSet<IGridJobID>();
  private List<IServiceJobStatusListener> listeners = new ArrayList<IServiceJobStatusListener>();
  private AbstractSubmittableServiceJob serviceJob;

  /**
   * Constructor of the ServiceJobUpdater class.
   * 
   * @param name Name of the updater.
   * @param serviceJob Parent service job for this updater.
   */
  public ServiceJobUpdater( final String name, final AbstractSubmittableServiceJob serviceJob ) {
    super( name );
    this.serviceJob = serviceJob;
    this.listeners.add( ( IServiceJobStatusListener )GridModel.getServiceJobManager() );
  }

  /**
   * Method adds new jobIDs for the updater to check.
   * 
   * @param jobIDsToAdd IDs of the jobs to be checked by this updater.
   */
  public void addSubJobs( final List<IGridJobID> jobIDsToAdd ) {
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
        IGridElementCreator srvCreator = GridModel.getCreatorRegistry().getCreator( jobID, IGridJobService.class );
        if( srvCreator != null ) {
          try {
            IGridJobService jobService = ( IGridJobService )srvCreator.create( null );
            IVirtualOrganization vo = null;
            if( this.serviceJob != null && this.serviceJob.getProject() != null ) {
              vo = this.serviceJob.getProject().getVO();
            }
            IGridJobStatus newStatus = jobService.getJobStatus( jobID, vo, false, monitor );
            lastRefreshTime = newStatus.getLastUpdateTime();
            if( newStatus.canChange() ) {
              this.serviceJob.setJobResult( jobID,
                                      lastRefreshTime,
                                      Messages.getString("SubmittableJobUpdater.running_status"), //$NON-NLS-1$
                                      ServiceJobStates.RUNNING.getAlias() );
            } else {
              jobIDsToDelete.add( jobID );
              this.serviceJob.setJobResult( jobID,
                                      lastRefreshTime,
                                      Messages.getString("SubmittableJobUpdater.finished_status"), //$NON-NLS-1$
                                      newStatus.isSuccessful()?
                                         ServiceJobStates.OK.getAlias():
                                         ServiceJobStates.WARNING.getAlias());
              this.serviceJob.computeJobResult( jobID, newStatus );
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
      this.serviceJob.computeServiceJobResult();
    }
    for( IServiceJobStatusListener listener : this.listeners ) {
      listener.statusChanged( this.serviceJob );
    }
    return status;
  }
}
