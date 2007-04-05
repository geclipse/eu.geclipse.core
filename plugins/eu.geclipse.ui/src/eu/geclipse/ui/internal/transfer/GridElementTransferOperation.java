package eu.geclipse.ui.internal.transfer;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import eu.geclipse.core.GridException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.UIProblems;

public abstract class GridElementTransferOperation
    extends WorkspaceModifyOperation {
  
  private IGridContainer globalTarget;
  
  private IGridElement[] elements;
  
  public GridElementTransferOperation( final IGridContainer target,
                                       final IGridElement[] elements ) {
    this.globalTarget = target;
    this.elements = elements;
  }
  
  public IGridElement[] getElements() {
    return this.elements;
  }
  
  public IGridContainer getTarget() {
    return this.globalTarget;
  }
  
  @Override
  protected void execute( final IProgressMonitor monitor )
      throws CoreException, InvocationTargetException, InterruptedException {
    
    IProgressMonitor localMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
      
    String errors = ""; //$NON-NLS-1$
    localMonitor.beginTask( "Transfering elements", this.elements.length );
    try {
      
      for ( IGridElement element : this.elements ) {
        
        IProgressMonitor subMonitor = new SubProgressMonitor( localMonitor, 1 );
        try {
          transferElement( this.globalTarget, element, subMonitor );
        } catch ( CoreException cExc ) {
          // TODO mathias proper exception handling
          errors += cExc.getLocalizedMessage() + "; "; //$NON-NLS-1$
        } finally {
          subMonitor.done();
        }
        
        if ( monitor.isCanceled() ) {
          throw new OperationCanceledException();
        }
        
      }
      
    } finally {
      localMonitor.done();
      if ( errors.length() > 0 ) {
        throw new GridException( UIProblems.ELEMENT_TRANSFER_FAILED, errors );
      }
    }
    
  }
  
  protected abstract void transferElement( final IGridContainer target,
                                           final IGridElement element,
                                           final IProgressMonitor monitor )
    throws CoreException;
  
}
