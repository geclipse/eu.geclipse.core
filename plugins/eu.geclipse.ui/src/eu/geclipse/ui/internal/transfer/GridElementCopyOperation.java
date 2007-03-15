package eu.geclipse.ui.internal.transfer;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;

public class GridElementCopyOperation
    extends WorkspaceModifyOperation {
  
  private IGridContainer targetContainer;
  
  private IGridElement[] elements;
  
  private boolean move;
  
  public GridElementCopyOperation( final IGridContainer target,
                                   final IGridElement[] elements,
                                   final boolean move ) {
    this.targetContainer = target;
    this.elements = elements;
    this.move = move;
  }

  @Override
  protected void execute( final IProgressMonitor monitor )
      throws CoreException, InvocationTargetException, InterruptedException {
    
    IProgressMonitor localMonitor = monitor;
    if ( localMonitor == null ) {
      localMonitor = new NullProgressMonitor();
    }
    
    localMonitor.beginTask( "Copying elements", this.elements.length );
    for ( IGridElement element : this.elements ) {
      if ( this.targetContainer.canContain( element ) ) {
        IProgressMonitor subMonitor = new SubProgressMonitor( localMonitor, 1 );
        if ( this.move && isMovable( this.targetContainer, element ) ) {
          moveElement( this.targetContainer, element, subMonitor );
        } else {
          copyElement( this.targetContainer, element, subMonitor );
        }
      }
    }
    localMonitor.done();
    
  }
  
  private void copyElement( final IGridContainer target,
                            final IGridElement element,
                            final IProgressMonitor monitor )
      throws CoreException {
    if ( element.isVirtual() ) {
      // TODO mathias
    } else {
      IResource resource = element.getResource();
      String name = resource.getName();
      IPath destination = target.getPath().append( name );
      resource.copy( destination, false, monitor );
    }
    monitor.done();
  }
  
  private boolean isMovable( final IGridContainer target,
                             final IGridElement element ) {
    return target.isLocal() && element.isLocal();
  }
  
  private void moveElement( final IGridContainer target,
                            final IGridElement element,
                            final IProgressMonitor monitor )
      throws CoreException {
    if ( element.isVirtual() ) {
      // TODO mathias
    } else {
      IResource resource = element.getResource();
      String name = resource.getName();
      IPath destination = target.getPath().append( name );
      resource.move( destination, false, monitor );
    }
    monitor.done();
  }

}
