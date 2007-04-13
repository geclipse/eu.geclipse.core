package eu.geclipse.ui.internal.transfer;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.internal.Activator;

public class GridElementMoveOperation
    extends GridElementTransferOperation {
  
  public GridElementMoveOperation( final IGridContainer target,
                                   final IGridElement[] elements ) {
    super( target, elements );
  }
  
  protected void transferElement( final IGridContainer target,
                                  final IGridElement element,
                                  final IProgressMonitor monitor )
      throws CoreException {
    
    IResource resource = element.getResource();
    String name = resource.getName();
    
    if ( target.isLazy() && target.isDirty() ) {
      target.getChildren( monitor );
    }
    
    if ( target.findChild( name ) != null ) {
      IStatus status = new Status( IStatus.ERROR,
                                   Activator.PLUGIN_ID,
                                   IStatus.CANCEL,
                                   "A child with the same name (" + name + ") already exists",
                                   null );
      throw new CoreException( status );
    }
    
    IPath destination = target.getPath().append( name );
    resource.move( destination, IResource.NONE, monitor );
    
  }
  
}
