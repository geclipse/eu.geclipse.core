package eu.geclipse.ui.internal.actions;

import java.net.URL;
import java.util.Iterator;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.ui.internal.Activator;

public class DeleteConnectionAction
    extends BaseSelectionListenerAction {
  
  protected DeleteConnectionAction() {
    super( "D&elete@DEL" );
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/obj16/invalid_element_obj.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }
  
  @Override
  public void run() {
    
    IStructuredSelection selection = getStructuredSelection();
    Iterator< ? > iter = selection.iterator();

    while ( iter.hasNext() ) {
      try {
        IGridConnection connection = ( IGridConnection ) iter.next();
        if ( connection.isGlobal() ) {
          deleteGlobalConnection( connection );
        } else {
          deleteLocalConnection( connection );
        }
      } catch ( CoreException cExc ) {
        // TODO mathias proper error handling
        Activator.logException( cExc );
      }
    }
    
  }
  
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    
    boolean result = true;
   
    Iterator< ? > iter = selection.iterator();
    while ( iter.hasNext() && result ) {
      Object next = iter.next();
      if ( ! ( next instanceof IGridConnection ) ) {
        result = false;
      }
    }
    
    return result;
    
  }
  
  private void deleteGlobalConnection( final IGridConnection connection )
      throws CoreException {
    
    IGridElementManager manager = connection.getManager();
    manager.removeElement( connection );
    IFileStore fileStore = connection.getFileStore();
    fileStore.delete( EFS.NONE, null );
    
  }
  
  private void deleteLocalConnection( final IGridConnection connection )
      throws CoreException {
    
    IResource resource = connection.getResource();
    resource.delete( IResource.NONE, null );
    
  }

}
