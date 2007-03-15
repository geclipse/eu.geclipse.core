package eu.geclipse.ui.internal.actions;

import java.util.List;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import eu.geclipse.core.model.IGridElement;

public class CopyAction extends TransferAction {
  
  protected CopyAction( final Clipboard clipboard ) {
    super( "&Copy@Ctrl+C", clipboard );
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    ImageDescriptor pasteImage 
        = sharedImages.getImageDescriptor( ISharedImages.IMG_TOOL_COPY );
    setImageDescriptor( pasteImage );
  }
  
  @Override
  public void run() {
    List< ? > data = getStructuredSelection().toList();
    Object[] dataArray = getData( data.toArray( new Object[ data.size() ] ) );
    Transfer[] dataTypes = getDataTypes( dataArray );
    getClipboard().setContents( dataArray, dataTypes );
  }
  
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    
    boolean enabled = super.updateSelection( selection );
    
    if ( enabled ) {
      
      IPath referenceLocation = null;
      List< ? > selectedObjects = selection.toList();
      
      for ( Object selectedObject : selectedObjects ) {
        
        enabled = isDragSource( selectedObject );
        if ( !enabled ) {
          break;
        }
        
        IPath location = getLocation( selectedObject );
        if ( location == null ) {
          enabled = false;
        }
        
        if ( referenceLocation == null ) {
          referenceLocation = location;
        } else if ( referenceLocation.equals( location ) ) {
          enabled = false;
        }
        
        if ( !enabled ) {
          break;
        }
        
      }
    }
    
    return enabled;
    
  }
  
  private IPath getLocation( final Object obj ) {
    IPath location = null;
    if ( obj instanceof IResource ) {
      location = ( ( IResource ) obj ).getFullPath();
    } else if ( obj instanceof IGridElement ) {
      location = ( ( IGridElement ) obj ).getPath(); 
    }
    return location;
  }
  
}
