package eu.geclipse.core.internal.model;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;


public class GridTransferRoot
    extends GridTransfer {
  
  private String name;
  
  private IGridContainer target;
  
  public GridTransferRoot( final IGridElement[] sources,
                           final IGridContainer target ) {
    super( null, null );
    this.target = target;
    this.name = "transfer@" + System.currentTimeMillis();
    
    for ( IGridElement element : sources ) {
      try {
        GridTransfer transfer = new GridTransfer( this, element );
        addElement( transfer );
      } catch( GridModelException gmExc ) {
        // Should never happen. If it does we just log it
        Activator.logException( gmExc );
      }
    }
    
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getFileStore()
   */
  @Override
  public IFileStore getFileStore() {
    return TransferManager.getTransferManagerStore().getChild( getName() );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getName()
   */
  @Override
  public String getName() {
    return this.name;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getParent()
   */
  @Override
  public IGridContainer getParent() {
    return TransferManager.getManager();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.GridTransfer#getTarget()
   */
  @Override
  public IGridContainer getTarget() {
    return this.target;
  }
  
  public void startTransfer() {
    
    
    
  }
  
  @Override
  protected void prepareTransfer( final IProgressMonitor monitor )
      throws GridModelException {
    
    IProgressMonitor localMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
      
    localMonitor.beginTask( "Preparing transfer", getChildCount() );
    
    try {
      
      IGridElement[] children = getChildren( null );
      for ( IGridElement child : children ) {
        GridTransfer transfer = ( GridTransfer ) child;
        transfer.prepareTransfer( new SubProgressMonitor( localMonitor, 1 ) );
      }
      
    } finally {
      localMonitor.done();
    }
    
  }

}
