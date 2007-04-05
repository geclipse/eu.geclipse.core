package eu.geclipse.ui.internal.transfer;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;

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
    IPath destination = target.getPath().append( name );
    resource.move( destination, IResource.NONE, monitor );
  }
  
}
