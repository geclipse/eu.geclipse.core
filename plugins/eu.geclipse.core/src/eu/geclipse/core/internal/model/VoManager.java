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
import eu.geclipse.core.Preferences;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.GridModelProblems;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IStorableElement;
import eu.geclipse.core.model.IStorableElementCreator;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.IVoManager;

/**
 * Internal implementation of the
 * {@link eu.geclipse.core.model.IVoManager} interface.
 * This is not intended to be accessible from the outside. Instead
 * the {@link eu.geclipse.core.model.GridModel#getVoManager()}
 * method should be used.
 * 
 * This class is used as a singleton.
 */
public class VoManager
    extends AbstractDefaultGridElementManager
    implements IVoManager {
  
  /**
   * The name of this manager. This is also used as the storage area.
   */
  public static final String NAME = ".vos"; //$NON-NLS-1$
  
  /**
   * The singleton.
   */
  private static VoManager singleton;
    
  /**
   * Private constructor to ensure to have only one instance of
   * this class. This can be obtained by {@link #getManager()}.
   */
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
   * Static implementation of the {@link #getFileStore()} method that
   * is needed to avoid cyclic dependencies when the model is created.
   * 
   * @return The managers file store.
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
      IStorableElementCreator creator
        = GridModel.getStorableElementCreator( childStore );
      if ( creator != null ) {
        create( creator );
      }
    }
    
    String defaultVoName = Preferences.getDefaultVoName();
    if ( defaultVoName != null ) {
      IGridElement defaultVo = findChild( defaultVoName );
      setDefault( defaultVo );
    }
    
    if ( hasChildren() ) {
      updateDefault();
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
    
    IVirtualOrganization defaultVo
      = ( IVirtualOrganization ) getDefault();
    if ( defaultVo != null ) {
      Preferences.setDefaultVoName( defaultVo.getName() );
      Preferences.save();
    }
    
  }
  
}
