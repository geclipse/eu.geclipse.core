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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.internal.model.VoManager;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.GridModelProblems;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridJobSubmissionService;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IStorableElement;
import eu.geclipse.core.model.IVirtualOrganization;

/**
 * Abstract implementation of the
 * {@link eu.geclipse.core.model.IVirtualOrganization} interface.
 */
public abstract class AbstractVirtualOrganization
    extends AbstractGridContainer
    implements IVirtualOrganization {
  
  /**
   * Create a new VO.
   */
  protected AbstractVirtualOrganization() {
    super();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#canContain(eu.geclipse.core.model.IGridElement)
   */
  @Override
  public boolean canContain( final IGridElement element ) {
    return element instanceof IGridService;
  }
  
  @Override
  public void dispose() {
    IFileStore fileStore = getFileStore();
    try {
      fileStore.delete( EFS.NONE, null );
    } catch( CoreException cExc ) {
      Activator.logException( cExc );
    }
  }
  
  public IGridComputing[] getComputing() throws GridModelException {
    IGridComputing[] computing = null;
    IGridInfoService infoService = getInfoService();
    if ( infoService != null ) {
      computing = infoService.fetchComputing( this, this, null );
    }
    return computing;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#getFileStore()
   */
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
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IVirtualOrganization#getInfoService()
   */
  public IGridInfoService getInfoService()
      throws GridModelException {
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
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#getParent()
   */
  public IGridContainer getParent() {
    return VoManager.getManager();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#getPath()
   */
  public IPath getPath() {
    IPath path = new Path( VoManager.NAME );
    return path.append( getName() );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getResource()
   */
  public IResource getResource() {
    return null;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IVirtualOrganization#getServices()
   */
  public IGridService[] getServices()
      throws GridModelException {
    IGridService[] result = null;
    IGridInfoService infoService = getInfoService();
    if ( infoService != null ) {
      IGridService[] services = infoService.fetchServices( this, this, null );
      if ( ( services != null ) && ( services.length > 0 ) ) {
        result = new IGridService[ services.length + 1 ];
        System.arraycopy( services, 0, result, 0, services.length );
        result[ services.length ] = infoService;
      } else {
        result = new IGridService[ 1 ];
        result[ 0 ] = infoService;
      }
    }
    return result;
  }
  
  public IGridStorage[] getStorage() throws GridModelException {
    IGridStorage[] storage = null;
    IGridInfoService infoService = getInfoService();
    if ( infoService != null ) {
      storage = infoService.fetchStorage( this, this, null );
    }
    return storage;
  }
  
  public IGridJobSubmissionService[] getJobSubmissionServices() throws GridModelException {
    List< IGridJobSubmissionService > jsServices
      = new ArrayList< IGridJobSubmissionService >();
    IGridService[] services = getServices();
    for ( IGridService service : services ) {
      if ( service instanceof IGridJobSubmissionService ) {
        jsServices.add( ( IGridJobSubmissionService ) service );
      }
    }
    return jsServices.toArray( new IGridJobSubmissionService[ jsServices.size() ] );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isLocal()
   */
  public boolean isLocal() {
    return true;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IStorableElement#load()
   */
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
      throw new GridModelException( GridModelProblems.ELEMENT_LOAD_FAILED, cExc );
    }
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IStorableElement#save()
   */
  public void save() throws GridModelException {
    IGridElement[] children = getChildren( null );
    for ( IGridElement child : children ) {
      if ( child instanceof IStorableElement ) {
        ( ( IStorableElement ) child ).save();
      } else {
        saveChild( child );
      }
    }
  }
  
  /**
   * Load a child with the given name.
   * 
   * @param childName The child's name.
   * @return The element that was loaded or null if no such element
   * could be loaded.
   */
  protected abstract IGridElement loadChild( final String childName );
  
  protected void saveChild( final IGridElement child ) {
    // empty implementation
  }
  
}
