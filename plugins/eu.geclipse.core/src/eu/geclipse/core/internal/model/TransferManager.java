/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.internal.model;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridTransfer;
import eu.geclipse.core.model.ITransferManager;

/**
 * Internal implementation of the {@link ITransferManager} interface. 
 */
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
  
  /**
   * Private constructor.
   */
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
    // TODO mathias
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IStorableElementManager#saveElements()
   */
  public void saveElements() throws GridModelException {
    // TODO mathias
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.ITransferManager#queueTransfer(eu.geclipse.core.model.IGridElement[],
   *                                                            eu.geclipse.core.model.IGridContainer)
   */
  public void queueTransfer( final IGridElement[] sources,
                             final IGridContainer target )
      throws GridModelException {
    GridTransferRoot transfer = new GridTransferRoot( sources, target );
    addElement( transfer );
    transfer.startTransfer();
  }
  
}
