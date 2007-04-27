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
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.GridModelProblems;
import eu.geclipse.core.model.IConnectionManager;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridElement;

/**
 * Internal implementation of the
 * {@link eu.geclipse.core.model.IConnectionManager} interface.
 * This is not intended to be accessible from the outside. Instead
 * the {@link eu.geclipse.core.model.GridModel#getConnectionManager()}
 * method should be used.
 * 
 * This class is used as a singleton.
 */
public class ConnectionManager
    extends AbstractGridElementManager
    implements IConnectionManager {
  
  /**
   * The name of this manager. This is also used as the storage area.
   */
  public static final String NAME = ".connections"; //$NON-NLS-1$
  
  /**
   * The singleton.
   */
  private static ConnectionManager singleton;
  
  private ConnectionManager() {
    try {
      loadElements();
    } catch ( GridModelException gmExc ) {
      Activator.logException( gmExc );
    }
  }
  
  /**
   * Get the singleton instance of the <code>ConnectionManager</code>.
   * 
   * @return The singleton.
   */
  public static ConnectionManager getManager() {
    if ( singleton == null ) {
      singleton = new ConnectionManager();
    }
    return singleton;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementManager#canManage(eu.geclipse.core.model.IGridElement)
   */
  public boolean canManage( final IGridElement element ) {
    return element instanceof IGridConnection;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.AbstractGridElementManager#getName()
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
      IFileStore remoteStore
        = GridConnection.loadFromFsFile( childStore );
      IGridConnection connection
        = new GridConnection( this, remoteStore, childStore.getName() );
      addElement( connection );
    }
        
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IStorableElementManager#saveElements()
   */
  public void saveElements() throws GridModelException {
    // TODO mathias
  }
  
}
