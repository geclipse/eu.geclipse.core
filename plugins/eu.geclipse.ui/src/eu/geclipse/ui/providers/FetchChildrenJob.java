package eu.geclipse.ui.providers;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.util.MasterMonitor;
import eu.geclipse.ui.internal.Activator;

public class FetchChildrenJob extends Job {
  
  private IGridContainer container;
  
  private IProgressMonitor externalMonitor;
  
  public FetchChildrenJob( final IGridContainer container ) {
    super( "Fetching Children of " + container.getName() );
    this.container = container;
  }
  
  public void setExternalMonitor( final IProgressMonitor monitor ) {
    this.externalMonitor = monitor;
  }

  @Override
  protected IStatus run( final IProgressMonitor monitor ) {

    IStatus result = Status.CANCEL_STATUS;
    MasterMonitor mMonitor = new MasterMonitor( monitor, this.externalMonitor );
    
    try {
      this.container.setDirty();
      this.container.getChildren( mMonitor );
      result = Status.OK_STATUS;
    } catch ( GridModelException gmExc ) {
      result = new Status( IStatus.ERROR, Activator.PLUGIN_ID, "Fetch Error", gmExc );
    }
    
    return result;
    
  }

}
