package eu.geclipse.ui.internal.transfer;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
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
    
    IProgressMonitor localMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
  
    IResource resource = element.getResource();
    String name = resource.getName();
    boolean fetchChildren = target.isLazy() && target.isDirty();

    localMonitor.beginTask( name, fetchChildren ? 2 : 1 );

    try {
      
      if ( fetchChildren && !localMonitor.isCanceled() ) {
        localMonitor.subTask( name + " (preparing)" );
        target.getChildren( new SubProgressMonitor( localMonitor, 1 ) );
      }
      
      if ( target.findChild( name ) != null ) {
        IStatus status = new Status( IStatus.ERROR,
                                     Activator.PLUGIN_ID,
                                     IStatus.CANCEL,
                                     "A child with the same name (" + name + ") already exists",
                                     null );
        throw new CoreException( status );
      }
      
      if ( !localMonitor.isCanceled() ) {
        localMonitor.subTask( name + " (moving)" );
        IPath path = target.getPath().append( name );
        resource.move( path, IResource.NONE, new SubProgressMonitor( localMonitor, 1 ) );
      }
    
    } finally {
      localMonitor.done();
    }
    
  }
  
}
