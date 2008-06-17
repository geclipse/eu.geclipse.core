/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
 *    Ariel Garcia      - updated to new problem reporting
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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.internal.model.VoManager;
import eu.geclipse.core.internal.model.ProjectVo;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridApplicationManager;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridJobService;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IGridResourceCategory;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IStorableElement;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;


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
    return ( element instanceof IGridService )
      || ( element instanceof IGridApplicationManager );
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
  
  public IGridApplicationManager getApplicationManager() {
    return null;
  }
  
  public IGridResource[] getAvailableResources( final IGridResourceCategory category,
                                                final boolean exclusive,
                                                final IProgressMonitor monitor )
      throws ProblemException {
    IGridInfoService infoService = getInfoService();
    return infoService.fetchResources( this, this, category, false, null, monitor );
  }
  
  public IGridComputing[] getComputing( final IProgressMonitor monitor ) throws GridModelException {
    IGridComputing[] computing = null;
    IGridInfoService infoService = getInfoService();
    if ( infoService != null ) {
      computing = infoService.fetchComputing( this, this, monitor );
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
  public IGridService[] getServices( final IProgressMonitor monitor )
      throws GridModelException {
    IGridService[] result = null;
    IGridInfoService infoService = getInfoService();
    if ( infoService != null ) {
      IGridService[] services = infoService.fetchServices( this, this, monitor );
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
  
  public IGridStorage[] getStorage( final IProgressMonitor monitor ) throws GridModelException {
    IGridStorage[] storage = null;
    IGridInfoService infoService = getInfoService();
    if ( infoService != null ) {
      storage = infoService.fetchStorage( this, this, monitor );
    }
    return storage;
  }
  
  public IGridResourceCategory[] getSupportedResources() {
    return ProjectVo.standardResources;
  }
  
  public IGridJobService[] getJobSubmissionServices( final IProgressMonitor monitor ) throws GridModelException {
    List< IGridJobService > jsServices
      = new ArrayList< IGridJobService >();
    IGridService[] services = getServices( monitor );
    if ( services == null ) {
      services = new IGridService[ 0 ];
    }
    for ( IGridService service : services ) {
      if ( service instanceof IGridJobService ) {
        jsServices.add( ( IGridJobService ) service );
      }
    }
    return jsServices.toArray( new IGridJobService[ jsServices.size() ] );
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
      throw new GridModelException( ICoreProblems.MODEL_ELEMENT_LOAD_FAILED,
                                    cExc,
                                    Activator.PLUGIN_ID );
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
  
  protected IGridService[] filterServices( final IGridService[] services,
                                           final Class< ? extends IGridService > type,
                                           final boolean remove ) {
    
    List< IGridService > resultList = new ArrayList< IGridService >();
    
    for ( IGridService service : services ) {
      boolean isType = type.isAssignableFrom( service.getClass() );
      if ( ( remove && ! isType ) || ( ! remove && isType ) ) {
        resultList.add( service );
      }
    }
    
    return resultList.toArray( new IGridService[ resultList.size() ] );
    
  }
  
  /**
   * Load a child with the given name.
   * 
   * @param childName The child's name.
   * @return The element that was loaded or null if no such element
   * could be loaded.
   */
  protected abstract IGridElement loadChild( final String childName );
  
  protected void saveChild( @SuppressWarnings("unused")
                            final IGridElement child ) {
    // empty implementation
  }
  
}
