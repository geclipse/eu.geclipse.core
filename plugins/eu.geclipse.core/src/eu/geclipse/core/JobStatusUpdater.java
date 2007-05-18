package eu.geclipse.core;

import java.util.Hashtable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobID;

public class JobStatusUpdater extends Job {

  static Hashtable<String, JobStatusUpdater> updaters = new Hashtable<String, JobStatusUpdater>();
  
  IGridJob job;

  public JobStatusUpdater( final IGridJob job ) {
    super( "Grid Job Status Updater" );
    this.job = job;
    updaters.put( job.getID().getJobID(), this );
  }

  protected IStatus run( final IProgressMonitor monitor ) {
    int oldType = job.getJobStatus().getType();
    // IGridJobStatus jobStatus = updateService.getJobStatus( job.getID() );
    try{
      job.updateJobStatus();
      int newType = job.getJobStatus().getType();
      if( oldType != newType ) {
      // TODO pawelw - status changed, notify all listeners;
    }
    }
    catch(RuntimeException e){
      Activator.logException( e );
    }
    if( job.getJobStatus().canChange() ) {
      schedule( 10000 );
    }
    else{
      updaters.remove( job.getID().getJobID() );
    }
    return Status.OK_STATUS;
  }
  
  static void waitForJob(final String id) throws InterruptedException, IllegalAccessException{
    JobStatusUpdater jsu = updaters.get( id );
    if(jsu==null){
      throw new IllegalAccessException("No updater for job id: "+id);
    }
    else{
      jsu.join();
    }
    
  }

  static void waitForJob(final IGridJobID id) throws InterruptedException, IllegalAccessException{
    waitForJob(id.getJobID());
  }

  static void waitForJob(final IGridJob job) throws InterruptedException, IllegalAccessException{
    waitForJob(job.getID().getJobID());
  }

}