package eu.geclipse.ui.internal.actions;

import java.util.List;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ResourceTransfer;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.views.GridModelViewPart;

public class PasteAction extends TransferAction {
  
  GridModelViewPart view;
  
  public PasteAction( final GridModelViewPart view,
                      final Clipboard clipboard ) {
    super( "&Paste@Ctrl+V", clipboard );
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    ImageDescriptor pasteImage 
        = sharedImages.getImageDescriptor( ISharedImages.IMG_TOOL_PASTE );
    setImageDescriptor( pasteImage );
    this.view = view;
  }
  
  @Override
  public void run() {
    IStructuredSelection selection = getStructuredSelection();
    if ( selection.size() == 1 ) {
      final Object element = selection.getFirstElement();
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
        Display display = PasteAction.this.view.getSite().getShell().getDisplay();
        display.syncExec( new Runnable() {
          public void run() {
            IGridContainer container = ( IGridContainer ) element;
            container.setDirty();
            PasteAction.this.view.refreshViewer( container );
          }
        } );
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
