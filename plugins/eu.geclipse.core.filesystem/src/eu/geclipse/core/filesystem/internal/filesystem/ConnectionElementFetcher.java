package eu.geclipse.core.filesystem.internal.filesystem;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;

import eu.geclipse.core.filesystem.internal.Activator;

public class ConnectionElementFetcher extends Job {
  
  private ConnectionElement parent;
  
  public ConnectionElementFetcher( final ConnectionElement element ) {
    super( element.getName() + ".fetcher.job" );
    this.parent = element;
  }

  @Override
  protected IStatus run( final IProgressMonitor monitor ) {
    
    IStatus status = Status.OK_STATUS;
    
    IProgressMonitor lMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
      
    lMonitor.beginTask( "Fetching children of " + this.parent.getName(), 100 );
    
    try {
    
      IResource resource = this.parent.getResource();
      FileStore fileStore = ( FileStore ) this.parent.getConnectionFileStore();
      fileStore.reset();
      resource.refreshLocal( IResource.DEPTH_INFINITE, new SubProgressMonitor( lMonitor, 10 ) );
      fileStore.activate();
      resource.refreshLocal( IResource.DEPTH_ONE, new SubProgressMonitor( lMonitor, 90 ) );
      
    } catch ( CoreException cExc ) {
      status = new Status( IStatus.ERROR,
                           Activator.PLUGIN_ID,
                           "Unable to fetch children of " + this.parent.getName(),
                           cExc ); 
    }
    
    return status;
    
  }

}
