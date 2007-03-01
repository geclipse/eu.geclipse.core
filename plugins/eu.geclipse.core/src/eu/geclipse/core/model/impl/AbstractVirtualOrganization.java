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

package eu.geclipse.core.model.impl;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.internal.model.VoManager;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IStorableElement;
import eu.geclipse.core.model.IVirtualOrganization;

/**
 * Abstract implementation of the
 * {@link eu.geclipse.core.model.IVirtualOrganization} interface.
 */
public abstract class AbstractVirtualOrganization
    extends AbstractGridContainer
    implements IVirtualOrganization {
  
  protected AbstractVirtualOrganization() {
    super( null );
  }
  
  @Override
  public IFileStore getFileStore() {
    IFileStore fileStore = VoManager.getVoManagerStore().getChild( getName() );
    IFileInfo fileInfo = fileStore.fetchInfo();
    if ( !fileInfo.exists() ) {
      try {
        fileStore.mkdir( EFS.NONE, null );
      } catch( CoreException cExc ) {
        Activator.logException( cExc );
      }
    }
    return fileStore;
  }
  
  public IGridInfoService getInfoService() {
    IGridInfoService infoService = null;
    IGridElement[] children = getChildren( null );
    for ( IGridElement child : children ) {
      if ( child instanceof IGridInfoService ) {
        infoService = ( IGridInfoService ) child;
        break;
      }
    }
    return infoService;
  }
  
  @Override
  public IGridContainer getParent() {
    return VoManager.getManager();
  }
  
  public IGridService[] getServices() {
    List< IGridService > services = new ArrayList< IGridService >();
    IGridElement[] children = getChildren( null );
    for ( IGridElement child : children ) {
      if ( child instanceof IGridService ) {
        services.add( ( IGridService ) child );
      }
    }
    return services.toArray( new IGridService[ services.size() ] );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isLocal()
   */
  public boolean isLocal() {
    return true;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isVirtual()
   */
  public boolean isVirtual() {
    return true;
  }
  
  public void load() throws GridModelException {
    deleteAll();
    IFileStore fileStore = getFileStore();
    try {
      IFileStore[] childStores = fileStore.childStores( EFS.NONE, null );
      for ( IFileStore child : childStores ) {
        IGridElement element = loadChild( child.getName() );
        if ( element != null ) {
          addElement( element );
        }
      }
    } catch ( CoreException cExc ) {
      throw new GridModelException( cExc );
    }
  }
  
  public void save() throws GridModelException {
    IGridElement[] children = getChildren( null );
    for ( IGridElement child : children ) {
      if ( child instanceof IStorableElement ) {
        ( ( IStorableElement ) child ).save();
      }
    }
  }
  
  protected abstract IGridElement loadChild( final String childName );
  
}
