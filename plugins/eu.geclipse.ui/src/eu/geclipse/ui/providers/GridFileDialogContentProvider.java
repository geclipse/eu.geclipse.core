package eu.geclipse.ui.providers;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;

public class GridFileDialogContentProvider
    extends GridModelContentProvider {

  public Object[] getChildren( final Object parentElement ) {
    Object[] children = null;
    if ( parentElement instanceof IFileStore ) {
      try {
        children = ( ( IFileStore ) parentElement ).childStores( EFS.NONE , null );
      } catch (CoreException e) {
        // Silently ignored
      }
    } else {
      children = super.getChildren( parentElement );
    }
    return children;
  }
  
  public Object getParent( final Object element ) {
    Object parent = null;
    if ( element instanceof IFileStore ) {
      parent = ( ( IFileStore ) element ).getParent();
    } else {
      parent = super.getParent( element );
    }
    return parent;
  }
  
  public boolean hasChildren( final Object element ) {
    boolean result = false;
    if ( element instanceof IFileStore ) {
      Object[] children = getChildren( element );
      result = ( children != null ) && ( children.length > 0 ); 
    } else {
      result = super.hasChildren( element );
    }
    return result;
  }
  
}
