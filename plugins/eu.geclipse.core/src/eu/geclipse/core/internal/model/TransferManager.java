package eu.geclipse.core.internal.model;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.GridModelProblems;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridTransfer;
import eu.geclipse.core.model.ITransferManager;


public class TransferManager
    extends AbstractGridElementManager
    implements ITransferManager {
  
  /**
   * The name of this manager. This is also used as the storage area.
   */
  public static final String NAME = ".transfers"; //$NON-NLS-1$
  
  /**
   * The singleton.
   */
  private static TransferManager singleton;
  
  private TransferManager() {
    try {
      loadElements();
    } catch ( GridModelException gmExc ) {
      Activator.logException( gmExc );
    }
  }
  
  /**
   * Get the singleton instance of the <code>TransferManager</code>.
   * 
   * @return The singleton.
   */
  public static TransferManager getManager() {
    if ( singleton == null ) {
      singleton = new TransferManager();
    }
    return singleton;
  }
  
  /**
   * Static implementation of the {@link #getFileStore()} method that
   * is needed to avoid cyclic dependencies when the model is created.
   * 
   * @return The managers file store.
   */
  public static IFileStore getTransferManagerStore() {
    IFileStore managerStore = getManagerStore();
    IFileStore childStore = managerStore.getChild( NAME );
    IFileInfo childInfo = childStore.fetchInfo();
    if ( !childInfo.exists() ) {
      try {
        childStore.mkdir( EFS.NONE, null );
      } catch( CoreException cExc ) {
        Activator.logException( cExc );
      }
    }
    return childStore;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementManager#canManage(eu.geclipse.core.model.IGridElement)
   */
  public boolean canManage( final IGridElement element ) {
    return element instanceof IGridTransfer;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getName()
   */
  public String getName() {
    return NAME;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IStorableElementManager#loadElements()
   */
  public void loadElements() throws GridModelException {

    IFileStore fileStore = getFileStore();
    
    IFileStore[] childStores;
    try {
      childStores = fileStore.childStores( EFS.NONE, null );
    } catch( CoreException cExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_LOAD_FAILED, cExc );
    }
    
    for ( IFileStore childStore : childStores ) {
      /*IFileStore remoteStore
        = GridConnection.loadFromFsFile( childStore );
      IGridConnection connection
        = new GridConnection( this, remoteStore, childStore.getName() );
      addElement( connection );*/
    }
        
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IStorableElementManager#saveElements()
   */
  public void saveElements() throws GridModelException {
    // TODO mathias
  }
  
  public void queueTransfer( final IGridElement[] sources,
                             final IGridContainer target )
      throws GridModelException {
    GridTransferRoot transfer = new GridTransferRoot( sources, target );
    addElement( transfer );
    transfer.startTransfer();
  }
  
}
