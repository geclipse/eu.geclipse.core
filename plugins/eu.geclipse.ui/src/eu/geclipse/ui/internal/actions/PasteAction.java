package eu.geclipse.ui.internal.actions;

import java.util.List;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.ui.part.ResourceTransfer;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.core.model.IGridContainer;

public class PasteAction extends TransferAction {
  
  public PasteAction(  final Clipboard clipboard  ) {
    super( "Paste", clipboard );
  }
  
  @Override
  public void run() {
    IStructuredSelection selection = getStructuredSelection();
    if ( selection.size() == 1 ) {
      Object element = selection.getFirstElement();
      if ( isDropTarget( element ) ) {
        IContainer destination
          = ( IContainer )( ( IGridContainer ) element ).getResource();
        ResourceTransfer transfer = ResourceTransfer.getInstance();
        Object contents = getClipboard().getContents( transfer );
        if ( ( contents != null ) && ( contents instanceof IResource[] ) ) {
          IResource[] resources = ( IResource[] ) contents;
          try {
            copyResources( resources, destination );
          } catch( CoreException cExc ) {
            // TODO mathias proper error handling
            Activator.logException( cExc );
          }
        }
      }
    }
  }
  
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    
    boolean result = false;
      
    List< ? > list = selection.toList();
    if ( list.size() == 1 ) {
      Object obj = list.get( 0 );
      if ( isDropTarget( obj ) ) {
        result = true;
      }
    }
    
    return result;
    
  }
  
}
