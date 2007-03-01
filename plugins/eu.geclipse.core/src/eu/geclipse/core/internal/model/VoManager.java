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
import org.eclipse.core.runtime.IStatus;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelStatus;
import eu.geclipse.core.model.IStorableElement;
import eu.geclipse.core.model.IStorableElementCreator;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.IVoManager;
import eu.geclipse.core.model.impl.GridModelStatus;

/**
 * Internal implementation of the
 * {@link eu.geclipse.core.model.IVoManager} interface.
 * This is not intended to be accessible from the outside. Instead
 * the {@link eu.geclipse.core.model.IGridRoot#getVoManager()}
 * method should be used.
 * 
 * This class is used as a singleton.
 */
public class VoManager
    extends AbstractDefaultGridElementManager
    implements IVoManager {
  
  private static final String NAME = ".vos"; //$NON-NLS-1$
  
  /**
   * The singleton.
   */
  private static VoManager singleton;
    
  private VoManager() {
    try {
      loadElements();
    } catch ( GridModelException gmExc ) {
      Activator.logException( gmExc );
    }
  }
  
  /**
   * Get the singleton instance of the <code>VoManager</code>.
   * 
   * @return The singleton.
   */
  public static VoManager getManager() {
    if ( singleton == null ) {
      singleton = new VoManager();
    }
    return singleton;
  }
  
  /**
   * Get the {@link IFileStore} were the vo managers stores its data.
   * 
   * @return The file store were the vo manager will save its
   * data to.
   */
  public static IFileStore getVoManagerStore() {
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
    return element instanceof IVirtualOrganization;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#getFileStore()
   */
  @Override
  public IFileStore getFileStore() {
    return getVoManagerStore();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.AbstractGridElementManager#getName()
   */
  @Override
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
      IGridModelStatus status = new GridModelStatus(
        IStatus.ERROR,
        IStatus.CANCEL,
        "Unable to open the manager's file store.",
        cExc
      );
      throw new GridModelException( status );
    }
    
    for ( IFileStore childStore : childStores ) {
      IStorableElementCreator creator
        = GridModel.getStorableElementCreator( childStore );
      if ( creator != null ) {
        create( creator );
      }
    }
    
    if ( hasChildren() ) {
      updateDefault();
      fireContentChanged();
    }
    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IStorableElementManager#saveElements()
   */
  public void saveElements() throws GridModelException {
    IGridElement[] elements = getChildren( null );
    for ( IGridElement element : elements ) {
      if ( element instanceof IStorableElement ) {
        ( ( IStorableElement ) element ).save();  
      }
    }
  }
  
}
