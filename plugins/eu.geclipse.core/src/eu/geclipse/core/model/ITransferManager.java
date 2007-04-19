package eu.geclipse.core.model;

import org.eclipse.core.runtime.IProgressMonitor;

public interface ITransferManager
    extends IGridElementManager, IStorableElementManager {
  
  public void queueTransfer( final IGridElement[] sources,
                             final IGridContainer target )
    throws GridModelException;
  
}
