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
 *    Ariel Garcia      - overridden getAdapter() for access control
 *****************************************************************************/

package eu.geclipse.core.filesystem.internal.filesystem;

import java.net.URI;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

import eu.geclipse.core.Extensions;
import eu.geclipse.core.filesystem.GEclipseFileSystem;
import eu.geclipse.core.filesystem.internal.Activator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IProtectable;
import eu.geclipse.core.model.impl.AbstractGridContainer;
import eu.geclipse.core.model.impl.ContainerMarker;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Internal implementation of the {@link IGridConnectionElement}.
 */
public class ConnectionElement
    extends AbstractGridContainer
    implements IGridConnectionElement {
  
  /**
   * Table used for caching all available adapters.
   */
  private static Hashtable< Class< ? extends IGridElement >, List< IGridElementCreator > > adapters;
  
  /**
   * UID used for the IMountable implementation.
   */
  private static String MOUNT_UID = Messages.getString("ConnectionElement.mount_uid"); //$NON-NLS-1$

  /**
   * The corresponding resource.
   */
  private IResource resource;
  
  /**
   * An error that may have occurred during the last fetch operation.
   */
  private Throwable fetchError;
  
  /**
   * Create a new connection element from the specified resource.
   * 
   * @param resource The resource from which to create a new element.
   * This resource has either to be a folder linked to a g-Eclipse URI
   * or has to be a child of such a folder.
   */
  public ConnectionElement( final IResource resource ) {
    Assert.isNotNull( resource );
    this.resource = resource;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnectionElement#canAdaptToElement(java.lang.Class)
   */
  public boolean canAdaptToElement( final Class< ? extends IGridElement > type ) {
    List< IGridElementCreator > elementAdapters = getElementAdapters( this, type );
    return ! elementAdapters.isEmpty();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#canContain(eu.geclipse.core.model.IGridElement)
   */
  @Override
  public boolean canContain( final IGridElement element ) {
    return isFolder(); // && ( element instanceof IGridConnectionElement );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnectionElement#getElementAdapter(java.lang.Class)
   */
  public IGridElement getElementAdapter( final Class< ? extends IGridElement > type ) {
    
    IGridElement result = null;
    
    List< IGridElementCreator > elementAdapters = getElementAdapters( this, type );
    IResource res = getResource();
    
    for ( IGridElementCreator adapter : elementAdapters ) {
      if ( adapter.canCreate( res ) ) {
        try {
          result = adapter.create( getParent() );
          break;
        } catch ( ProblemException pExc ) {
          // Just ignore and have a try with the next creator
        }
      }
    }
    
    return result;
    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnectionElement#getConnectionFileInfo()
   */
  public IFileInfo getConnectionFileInfo() throws CoreException {
    return getConnectionFileStore().fetchInfo();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnectionElement#getCachedConnectionFileStore(org.eclipse.core.runtime.IProgressMonitor)
   */
  public IFileStore getCachedConnectionFileStore( final IProgressMonitor monitor )
      throws CoreException {
    
    IFileStore result = null;
    SubMonitor sMonitor = SubMonitor.convert( monitor, 10 );
    
    try {
      result = getConnectionFileStore();
      sMonitor.worked( 1 );
      ( ( GEclipseFileStore ) result ).cacheInputStream( this, monitor );
      sMonitor.worked( 9 );
    } finally {
      sMonitor.done();
    }
    
    return result;
    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnectionElement#getConnectionFileStore()
   */
  public IFileStore getConnectionFileStore() throws CoreException {
    IFileStore result = null;
    IResource res = getResource();
    if ( res != null ) {
      URI uri = res.getLocationURI();
      GEclipseFileSystem fileSystem = new GEclipseFileSystem();
      result = fileSystem.getStore( uri ); 
    }
    return result;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnectionElement#getError()
   */
  public String getError() {
    String error = null;
    if ( this.fetchError != null ) {
      error = this.fetchError.getLocalizedMessage();
    }
    return error;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IMountable#getMountPoint(eu.geclipse.core.model.IMountable.MountPointID)
   */
  public MountPoint getMountPoint( final MountPointID mountID ) {
    
    MountPoint result = null;
    
    if ( MOUNT_UID.equals( mountID.getUID() ) ) {
      result = new MountPoint( getName(), getURI() );
    }
    
    return result;
    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IMountable#getMountPointIDs()
   */
  public MountPointID[] getMountPointIDs() {
    MountPointID[] result = null;
    //if ( isFolder() ) {
      result = new MountPointID[] { new MountPointID( MOUNT_UID, getURI().getScheme() ) };  
    //}
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.getURI#getURI()
   */  
  public URI getURI() {
    URI result = null;
    try {
      GEclipseFileStore fileStore = ( GEclipseFileStore ) getConnectionFileStore();
      result = fileStore.getSlave().toURI();
    } catch ( CoreException cExc ) {
      Activator.logException( cExc );
    }
    return result;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#hasChildren()
   */
  @Override
  public boolean hasChildren() {
    return isFolder() ? super.hasChildren() : false;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnectionElement#isFolder()
   */
  public boolean isFolder() {
    return getResource().getType() == IResource.FOLDER;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#isHidden()
   */
  @Override
  public boolean isHidden() {
    return false;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#isLazy()
   */
  public boolean isLazy() {
    return true;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isLocal()
   */
  public boolean isLocal() {
    
    boolean result = false;
    
    try {
      GEclipseFileStore fileStore = ( GEclipseFileStore ) getConnectionFileStore();
      result = fileStore.isLocal();
    } catch ( CoreException cExc ) {
      Activator.logException( cExc );
    }
      
    return result;
    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnectionElement#isValid()
   */
  public boolean isValid() {
    return getError() == null;
  }
  
  public void releaseCachedConnectionFileStore( final IFileStore fileStore ) {
    if ( fileStore instanceof GEclipseFileStore ) {
      ( ( GEclipseFileStore ) fileStore ).discardCachedInputStream();
    }
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#fetchChildren(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected IStatus fetchChildren( final IProgressMonitor monitor ) {
    
    SubMonitor sMonitor = SubMonitor.convert(
        monitor,
        String.format( Messages.getString("ConnectionElement.fetching_children_progress"), getName() ), //$NON-NLS-1$
        100
    );
    
    IStatus result = Status.CANCEL_STATUS;
    this.fetchError = null;
    
    // The child fetching is done in 3 steps:
    // 1) Reset the file store and refresh the folder. This ensures that all
    //    formerly available IResources are deleted.
    // 2) Load the file stores children directly without a refresh. This ensures
    //    that this long lasting operation is not blocked by other workspace
    //    operations.
    // 3) Locally refresh the folder. Since the file store is not active at this
    //    time not remote actions are initiated. Instead just the formerly
    //    fetched children are returned.
    
    try {
      
      IResource res = getResource();
      GEclipseFileStore fileStore = ( GEclipseFileStore ) getConnectionFileStore();

      // Step 1: Reset and Refresh
      /*fileStore.reset();
      fileStore.setExternalMonitor( sMonitor.newChild( 10 ) );
      res.refreshLocal( IResource.DEPTH_INFINITE, null );*/
      
      // Step 2: Activate and fetch children remotely
      fileStore.setActive( GEclipseFileStore.FETCH_CHILDREN_ACTIVE_POLICY );
      fileStore.childNames( EFS.NONE, sMonitor.newChild( 90 ) );
      
      // Step 3: Refresh again
      SubMonitor ssMonitor = sMonitor.newChild( 10 );
      ssMonitor.subTask( Messages.getString("ConnectionElement.task_refreshing") ); //$NON-NLS-1$
      fileStore.setExternalMonitor( ssMonitor );
      res.refreshLocal( IResource.DEPTH_ONE, ssMonitor );
      fileStore.setExternalMonitor( null ); // don't use this external monitor in next operations, because it could be cancelled
      result = Status.OK_STATUS;
      
      if ( res instanceof IContainer ) {
        IResource[] members = ( ( IContainer ) res ).members();
        if ( ( members == null ) || ( members.length == 0 ) ) {
          addElement( ContainerMarker.getEmptyFolderMarker( this ) );
        } else {
          for ( IResource member : members ) {
            addElement( new ConnectionElement( member ) );
          }
        }
      }
      
    } catch ( Throwable t ) {
      
      this.fetchError = t;
      result = new Status( IStatus.ERROR, Activator.PLUGIN_ID, Messages.getString("ConnectionElement.fetch_error"), t ); //$NON-NLS-1$
      try {
        addElement( ContainerMarker.getErrorMarker( this, t ) );
      } catch ( ProblemException pExc ) {
        Activator.logException( pExc );
      }
      
    } finally {
      sMonitor.done();
    }
    
    return sMonitor.isCanceled() ? Status.CANCEL_STATUS : result;
    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getFileStore()
   */
  public IFileStore getFileStore() {
    IResource res = getResource();
    IPath path = res.getWorkspace().getRoot().getLocation().append( res.getFullPath() );
    IFileSystem fileSystem = EFS.getLocalFileSystem();
    IFileStore fileStore = fileSystem.getStore( path );
    return fileStore;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getName()
   */
  public String getName() {
    return getResource().getName();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getParent()
   */
  public IGridContainer getParent() {
    IGridContainer parent = null;
    IPath parentPath = getPath().removeLastSegments( 1 );
    IGridElement parentElement = GridModel.getRoot().findElement( parentPath );
    if ( parentElement instanceof IGridContainer ) {
      parent = ( IGridContainer ) parentElement;
    }
    return parent;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getPath()
   */
  public IPath getPath() {
    return getResource().getFullPath();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getResource()
   */
  public IResource getResource() {
    return this.resource;
  }
  
  /**
   * Get all registered adapters for the specified grid element type. This method will
   * cached any found adapter for later use.
   * 
   * @param source The connection element to be adapted.
   * @param target The type to which to adapt this connection element.
   * @return A possibly empty list of adapters.
   * @see Extensions#getRegisteredElementCreatorConfigurations(Class, Class)
   */
  private static List< IGridElementCreator > getElementAdapters( final IGridConnectionElement source,
                                                                 final Class< ? extends IGridElement > target ) {
    
    List< IGridElementCreator > result = null;
    
    if ( adapters != null ) {
      result = adapters.get( target );
    }
    
    
    if ( result == null ) {
      
      result = new ArrayList< IGridElementCreator >();
      IResource resource = source.getResource();
      
      List< IConfigurationElement > elements
        = GridModel.getCreatorRegistry().getConfigurations( resource.getClass(), target );
      
      if ( elements != null ) {
        for ( IConfigurationElement element : elements ) {
          try {
            IGridElementCreator creator
              = ( IGridElementCreator ) element.createExecutableExtension( Extensions.GRID_ELEMENT_CREATOR_EXECUTABLE );
            if ( creator.canCreate( resource ) ) {
              result.add( creator );
            }
          } catch ( Throwable exc ) {
            // Do nothing, just catch and ignore this creator for being as failsafe as possible
          }
        }
      }
      
      if ( ! result.isEmpty() ) {
        if ( adapters == null ) {
          adapters = new Hashtable< Class< ? extends IGridElement >, List< IGridElementCreator > >();
        }
        adapters.put( target, result );
      }
      
    }
    
    return result;

  }

  @Override
  public void refresh( final IProgressMonitor monitor ) throws ProblemException {
    
    try {
      IFileStore fileStore = getConnectionFileStore();
      
      if( fileStore instanceof GEclipseFileStore ) {
        GEclipseFileStore geclFS = ( GEclipseFileStore )fileStore;
        geclFS.setActive( GEclipseFileStore.FETCH_CHILDREN_ACTIVE_POLICY );      
      }      
    } catch( CoreException exception ) {
      Activator.logException( exception );
    }    
 
    super.refresh( monitor );
  }
  
  /*
   * (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#getAdapter(java.lang.Class)
   */
  @Override
  @SuppressWarnings("unchecked")
  public Object getAdapter( final Class adapter ) {
    Object result = null;
    
    // If we are required to adapt to IProtectable, we have to return the one from the slave
    if ( adapter == IProtectable.class ) {
      try {
        result = getConnectionFileStore().getAdapter( adapter );
      } catch ( CoreException ce ) {
        // Just continue if no FileStore is available
        Activator.logException( ce );
      }
    }
    
    // Otherwise do the default
    if ( result == null ) {
      result = super.getAdapter( adapter );
    }
    
    return result;
  }

}
