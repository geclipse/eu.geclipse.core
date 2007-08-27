package eu.geclipse.core.internal.model.notify;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.internal.model.GridRoot;

public class ResourceNotificationService
    implements IResourceChangeListener {
  
  private static ResourceNotificationService instance; 
  
  private ResourceNotificationService() {
    ResourcesPlugin.getWorkspace().addResourceChangeListener( this );
  }
  
  public static ResourceNotificationService getInstance() {
    
    if ( instance == null ) {
      instance = new ResourceNotificationService();
    }
    
    return instance;
    
  }
  
  public void resourceChanged( final IResourceChangeEvent event ) {
    
    IResourceDelta delta = event.getDelta();
    
    if ( delta != null ) {
    
      GridNotificationService gridNotificationService
        = GridRoot.getGridNotificationService();
      
      gridNotificationService.lock();
      
      GridElementLifecycleManager visitor = new GridElementLifecycleManager();
      
      try {
        delta.accept( visitor );
      } catch ( CoreException cExc ) {
        Activator.logException( cExc );
      }
      
      gridNotificationService.unlock();
      
    }
    
  }

}
