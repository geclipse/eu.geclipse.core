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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.core.filesystem.provider.FileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.filesystem.GEclipseFileSystem;
import eu.geclipse.core.filesystem.GEclipseURI;
import eu.geclipse.core.filesystem.internal.Activator;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IProtectable;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.util.MasterMonitor;

/**
 * Implementation of {@link IFileStore} for the g-Eclipse file system.
 */
public class GEclipseFileStore
    extends FileStore {
  
  public static final int FETCH_CHILDREN_ACTIVE_POLICY = 0x01;
  
  public static final int FETCH_INFO_ACTIVE_POLICY = 0x02;
  
  public static final int MOVE_COPY_ACTIVE_POLICY = 0x04;
  
  /**
   * The file system.
   */
  private GEclipseFileSystem fileSystem;
  
  /**
   * The parent store. May be <code>null</code>.
   */
  private GEclipseFileStore parent;
 
  /**
   * The slave store.
   */
  private IFileStore slave;
  
  /**
   * The slave store's file info.
   */
  private IFileInfo fileInfo;
  
  /**
   * Determines if this store is active.
   */
  private int active;
  
  /**
   * External progress monitor used to show progress information.
   */
  private IProgressMonitor externalMonitor;
  
  private CachedInputStream ciStream;

  private IFileInfo[] cachedInfos;
  
  /**
   * Create a new master store from the specified slave store. The
   * file store will be a root store.
   * 
   * @param fileSystem The file system for which to create the store.
   * @param slave The slave store for which to create the master store.
   */
  protected GEclipseFileStore( final GEclipseFileSystem fileSystem,
                               final IFileStore slave ) {
    Assert.isNotNull( fileSystem );
    Assert.isNotNull( slave );
    this.fileSystem = fileSystem;
    IFileStore slaveParent = slave.getParent();
    if ( slaveParent != null ) {
      FileStoreRegistry registry = FileStoreRegistry.getInstance();
      this.parent = registry.getStore( slaveParent );
    } else {
      this.parent = null;
    }
    this.slave = slave;
    this.fileInfo = new FileInfo( getNameWithoutConnect( slave ) );    
    
    clearActive( FETCH_CHILDREN_ACTIVE_POLICY | FETCH_INFO_ACTIVE_POLICY );
    setActive( MOVE_COPY_ACTIVE_POLICY );
    
  }

  /**
   * Create a new master store from the specified slave store. The
   * file store will be a child store.
   * 
   * @param parent The parent store of this file store.
   * @param slave The slave store for which to create the master store.
   * @param name 
   */
  private GEclipseFileStore( final GEclipseFileStore parent,
                             final IFileStore slave, final String name ) {
    Assert.isNotNull( parent );
    Assert.isNotNull( slave );
    this.fileSystem = ( GEclipseFileSystem ) parent.getFileSystem();
    this.parent = parent;
    this.slave = slave;
    this.fileInfo = new FileInfo( name );
    clearActive( FETCH_CHILDREN_ACTIVE_POLICY );
    setActive( FETCH_INFO_ACTIVE_POLICY | MOVE_COPY_ACTIVE_POLICY );  // file info is not correct now, so fetch it during next occasion
  }
  /**
   * Create a new master store from the specified slave store. The
   * file store will be a child store.
   * 
   * @param parent The parent store of this file store.
   * @param slave The slave store for which to create the master store.
   */
  private GEclipseFileStore( final GEclipseFileStore parent,
                             final IFileStore slave,
                             final IFileInfo info ) {
    Assert.isNotNull( parent );
    Assert.isNotNull( slave );
    this.fileSystem = ( GEclipseFileSystem ) parent.getFileSystem();
    this.parent = parent;
    this.slave = slave;
    this.fileInfo = info;
    clearActive( FETCH_CHILDREN_ACTIVE_POLICY | FETCH_INFO_ACTIVE_POLICY );
    setActive( MOVE_COPY_ACTIVE_POLICY );
  }

  /**
   * Activate this store. After a store is activated the
   * {@link #childNames(int, IProgressMonitor)} method will delegate its
   * work to the slave store. If {@link #childNames(int, IProgressMonitor)}
   * is called without activating the store the cached names are returned.
   * @param policy
   */
  public void setActive( final int policy ) {
    this.active |= policy;
  }
  
  /**
   * Caches the input stream of this file store. This method creates a
   * {@link CachedInputStream} and calls its cache method. Therefore the
   * content of this file store is fetched to the local memory. Whenever
   * {@link #openInputStream(int, IProgressMonitor)} is called after caching
   * the input stream the cached stream is returned instead of the normal
   * one. To release the cache consumers have to call {@link #discardCachedInputStream()}
   * in order to free the used system resources.
   * @param cElement 
   * 
   * @param monitor A {@link IProgressMonitor} used to monitor the caching
   * procedure.
   * @throws CoreException If the caching fails.
   * @see #discardCachedInputStream()
   */
  public void cacheInputStream( final IGridConnectionElement cElement, final IProgressMonitor monitor )
      throws CoreException {
    
    SubMonitor sMonitor
      = SubMonitor.convert(
          monitor( monitor ),
          Messages.getString("GEclipseFileStore.caching_progress"), //$NON-NLS-1$
          10
      );

    try {
      InputStream siStream = openInputStream( EFS.NONE, sMonitor.newChild( 1 ) );
      setActive( FETCH_INFO_ACTIVE_POLICY );
      IFileInfo info = fetchInfo( EFS.NONE, sMonitor.newChild( 1 ) );
      cElement.getResource().refreshLocal( IResource.DEPTH_ZERO, monitor );
      this.ciStream = new CachedInputStream( siStream, ( int ) info.getLength() );
  
      try {
        this.ciStream.cache( sMonitor.newChild( 8 ) );
      } catch ( IOException ioExc ) {
        throw new ProblemException(
            ICoreProblems.IO_UNSPECIFIED_PROBLEM,
            String.format( Messages.getString("GEclipseFileStore.caching_error"), getName() ), //$NON-NLS-1$
            ioExc,
            Activator.PLUGIN_ID );
      }
      
    } finally {
      sMonitor.done();
    }
    
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#childNames(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public String[] childNames( final int options,
                              final IProgressMonitor monitor )
      throws CoreException {
    IFileInfo[] infos = FileStore.EMPTY_FILE_INFO_ARRAY;
    String[] result = FileStore.EMPTY_STRING_ARRAY;
    
    if ( isActive( FETCH_CHILDREN_ACTIVE_POLICY ) ) {
      clearActive( FETCH_CHILDREN_ACTIVE_POLICY );
            
      infos = getSlave().childInfos( options, monitor( monitor ) );
      setCachedInfos( infos );
      
      ArrayList<String> names = new ArrayList<String>( infos.length );
      
      for ( IFileInfo info : infos ) {
        names.add( info.getName() );        
      }
      
      result = names.toArray( new String[ names.size() ] );
    }
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#move(org.eclipse.core.filesystem.IFileStore, int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public void copy( final IFileStore destination,
                    final int options,
                    final IProgressMonitor monitor )
      throws CoreException {
    if ( isActive( MOVE_COPY_ACTIVE_POLICY ) ) {
      getSlave().copy( slave( destination ), options, monitor( monitor ) );
    }
  }

  private void setCachedInfos( final IFileInfo[] infos ) {
    if ( this.cachedInfos != null ) {
      FileStoreRegistry registry = FileStoreRegistry.getInstance();
      for ( IFileInfo info : this.cachedInfos ) {
        IFileStore child = getSlave().getChild( info.getName().trim() );
        registry.removeStore( child );
      }
    }
    
    this.cachedInfos = infos;

    FileStoreRegistry registry = FileStoreRegistry.getInstance();
    for( IFileInfo info : this.cachedInfos ) {
      registry.putStore( new GEclipseFileStore( this, getSlave().getChild( info.getName() ), info ) );
    }    
  }  
  
  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#delete(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public void delete( final int options,
                      final IProgressMonitor monitor )
      throws CoreException {
    getSlave().delete( options, monitor( monitor ) );
    
    removeCachedStoreWithChildren( this );
    
    if( this.parent != null ) {
      this.parent.setActive( FETCH_CHILDREN_ACTIVE_POLICY );
    }
  }
  
  private void removeCachedStoreWithChildren( final GEclipseFileStore fileStore ) {
    FileStoreRegistry registry = FileStoreRegistry.getInstance();
    registry.removeStore( this );
    
    if( this.cachedInfos != null ) {
      for( IFileInfo childInfo : this.cachedInfos ) {
        if( childInfo.getName() != null 
            && childInfo.getName().length() > 0 ) {
          IFileStore child = getSlave().getChild( childInfo.getName() );
          
          if( child != null ) {
            registry.removeStore( child );
          }
        }
      }
    }
  }
  
  /**
   * If the input stream of this file store was formerly cached with
   * {@link #cacheInputStream(IGridConnectionElement, IProgressMonitor)} this method releases
   * the cache and frees all used system resources. Subsequent calls to
   * {@link #openInputStream(int, IProgressMonitor)} return the original
   * stream of the slave instead of a cached one.
   * 
   * @see #cacheInputStream(IGridConnectionElement, IProgressMonitor)
   */
  public void discardCachedInputStream() {
    if ( this.ciStream != null ) {
      this.ciStream.discard();
      this.ciStream = null;
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#childInfos(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public IFileInfo[] childInfos( final int options, final IProgressMonitor monitor )
  throws CoreException
  {
    if ( isActive( FETCH_CHILDREN_ACTIVE_POLICY ) ) {
      clearActive( FETCH_CHILDREN_ACTIVE_POLICY );      
      setCachedInfos( getSlave().childInfos( options, monitor ) );      
    }
    if ( this.cachedInfos == null ) {
      this.cachedInfos = FileStore.EMPTY_FILE_INFO_ARRAY;
    }
    return this.cachedInfos;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#fetchInfo(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public IFileInfo fetchInfo( final int options,
                              final IProgressMonitor monitor )
      throws CoreException {
    
    if ( isActive( FETCH_INFO_ACTIVE_POLICY ) ) {
      clearActive( FETCH_INFO_ACTIVE_POLICY );
      this.fileInfo = getSlave().fetchInfo( options, monitor( monitor ) );
    }
    
    if ( this.fileInfo == null ) {
      this.fileInfo = new FileInfo();
    }
    
    return this.fileInfo;
     
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#getChild(java.lang.String)
   */
  @Override
  public IFileStore getChild( final String name ) {
    
    GEclipseFileStore result = null;
    
    IFileStore child = getSlave().getChild( name.trim() );
    
    if ( child != null ) {
      FileStoreRegistry registry = FileStoreRegistry.getInstance();
      result = registry.getStore( child );
      if ( result == null ) {
        result = new GEclipseFileStore( this, child, name );
        registry.putStore( result );
      }
    }
    
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#getFileSystem()
   */
  @Override
  public IFileSystem getFileSystem() {
    return this.fileSystem;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#getName()
   */
  @Override
  public String getName() {
    return getSlave().getName();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#getParent()
   */
  @Override
  public IFileStore getParent() {
    return this.parent;
  }
  
  /**
   * Get this file stores slave store.
   * 
   * @return The slave store of this file store.
   */
  public IFileStore getSlave() {
    return this.slave;
  }
  
  /**
   * Determines if this file store is a local store. Local stores
   * are stores that have a slave store coming from the
   * {@link EFS#getLocalFileSystem()}.
   * 
   * @return True if this store is a local mount.
   */
  public boolean isLocal() {
    String scheme = toGEclipseURI().getSlaveScheme();
    IFileSystem localFileSystem = EFS.getLocalFileSystem();
    String localScheme = localFileSystem.getScheme();
    return scheme.equals( localScheme );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#mkdir(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public IFileStore mkdir( final int options,
                           final IProgressMonitor monitor)
      throws CoreException {
    GEclipseFileStore result = this;
    if ( ! fetchInfo().exists() ) {
      IFileStore dir = getSlave().mkdir( options, monitor( monitor ) );
      FileStoreRegistry registry = FileStoreRegistry.getInstance();
      result = new GEclipseFileStore( this, dir, dir.getName() );
      registry.putStore( result );
    }
    return result;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#move(org.eclipse.core.filesystem.IFileStore, int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public void move( final IFileStore destination,
                    final int options,
                    final IProgressMonitor monitor )
      throws CoreException {
    if ( isActive( MOVE_COPY_ACTIVE_POLICY ) ) {
      getSlave().move( slave( destination ), options, monitor( monitor ) );
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#openInputStream(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public InputStream openInputStream( final int options,
                                      final IProgressMonitor monitor )
      throws CoreException {
    return this.ciStream != null ? this.ciStream : getSlave().openInputStream( options, monitor( monitor ) );
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.filesystem.provider.FileStore#openOutputStream(int,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public OutputStream openOutputStream( final int options,
                                        final IProgressMonitor monitor )
    throws CoreException
  {
    URI oldUri = getSlave().toURI();
    OutputStream outputStream = getSlave().openOutputStream( options,
                                                             monitor( monitor ) );
    URI currentUri = getSlave().toURI();
    // Gria stagers changes its URI during creating output stream for new
    // stager, so we have to update uri in cache
    if( !currentUri.equals( oldUri ) ) {
      FileStoreRegistry registry = FileStoreRegistry.getInstance();
      registry.removeStore( new GEclipseURI( oldUri ) );
      registry.putStore( this );
    }
    setActive( FETCH_INFO_ACTIVE_POLICY );
    return outputStream;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#putInfo(org.eclipse.core.filesystem.IFileInfo, int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public void putInfo( final IFileInfo info,
                       final int options,
                       final IProgressMonitor monitor)
      throws CoreException {
    getSlave().putInfo( info, options, monitor( monitor ) );
  }
  
  /**
   * Set an external progress monitor.
   * 
   * @param monitor The progress monitor.
   */
  public void setExternalMonitor( final IProgressMonitor monitor ) {
    this.externalMonitor = monitor;
  }
  
  /**
   * Get the {@link GEclipseURI} associated with this file store.
   * 
   * @return The {@link GEclipseURI} created from the
   * slave's <code>URI</code>.
   */
  public GEclipseURI toGEclipseURI() {
    return new GEclipseURI( getSlave().toURI() );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#toURI()
   */
  @Override
  public URI toURI() {
    return toGEclipseURI().toMasterURI();
  }
  
  /**
   * Determines if this file store is currently active.
   * 
   * @return True if this store is active, i.e. if the next time when
   * {@link #childNames(int, IProgressMonitor)} is called this call will
   * be delegated to the slave store.
   */
  private boolean isActive( final int policy ) {
    return ( this.active & policy ) != 0;
  }
  
  /**
   * Set this stores activation state.
   * 
   * @param active True if the store should be active.
   */
  public void clearActive( final int policy ) {
    this.active &= ~policy;
  }

  private IProgressMonitor monitor( final IProgressMonitor monitor ) {
    return new MasterMonitor( monitor, this.externalMonitor );
  }
  
  private IFileStore slave( final IFileStore store ) {
    IFileStore result = store;
    while ( ( result != null ) && ( result instanceof GEclipseFileStore ) ) {
      result = ( ( GEclipseFileStore ) result ).getSlave();
    }
    return result;
  }
  
  private String getNameWithoutConnect( final IFileStore slaveStore ) {
    String name = null;
    
    String path = slaveStore.toURI().getPath();
    if( path != null ) {
      name = new Path( path ).lastSegment();
    }
    
    if( name == null 
        || name.length() == 0 ) {
      name = Messages.getString("GEclipseFileStore.filenameUnknown"); // eclipse workspace doesn't like resources with name "" //$NON-NLS-1$
    }        
    
    return name;
  }
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.core.runtime.PlatformObject#getAdapter(java.lang.Class)
   */
  @Override
  @SuppressWarnings("unchecked")
  public Object getAdapter( final Class adapter ) {
    Object result = null;
    
    // If we are required to adapt to IProtectable, we have to return the one from the slave
    if ( adapter == IProtectable.class ) {
      if ( this.slave != null ) {
        if ( this.slave instanceof IProtectable ) {
          result = this.slave;
        }
      }
    }
    
    if ( result == null ) {
      if ( adapter.isAssignableFrom( getClass() ) ) {
        result = this;
      } else if ( adapter.isAssignableFrom( this.slave.getClass() ) ) {
        result = this.slave;      
      } else {
        result = this.slave.getAdapter( adapter );
      }
    }
    
    // Otherwise do the default
    if ( result == null ) {
      result = super.getAdapter( adapter );
    }
    
    return result;
  }
}
