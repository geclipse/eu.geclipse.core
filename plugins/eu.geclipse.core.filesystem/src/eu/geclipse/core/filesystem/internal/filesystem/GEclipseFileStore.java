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

package eu.geclipse.core.filesystem.internal.filesystem;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.filesystem.provider.FileStore;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.filesystem.GEclipseFileSystem;
import eu.geclipse.core.filesystem.GEclipseURI;
import eu.geclipse.core.util.MasterMonitor;

/**
 * Implementation of {@link IFileStore} for the g-Eclipse file system.
 */
public class GEclipseFileStore
    extends FileStore
    implements IFileStore {
  
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
   * Determines if this store is active.
   */
  private boolean isActive;
  
  /**
   * The names of the children if yet fetched.
   */
  private String[] childNames;
  
  /**
   * External progress monitor used to show progress information.
   */
  private IProgressMonitor externalMonitor;
  
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
    setActive( false );
  }

  /**
   * Create a new master store from the specified slave store. The
   * file store will be a child store.
   * 
   * @param parent The parent store of this file store.
   * @param slave The slave store for which to create the master store.
   */
  private GEclipseFileStore( final GEclipseFileStore parent,
                             final IFileStore slave ) {
    Assert.isNotNull( parent );
    Assert.isNotNull( slave );
    this.fileSystem = ( GEclipseFileSystem ) parent.getFileSystem();
    this.parent = parent;
    this.slave = slave;
    setActive( false );
  }
  
  /**
   * Activate this store. After a store is activated the
   * {@link #childNames(int, IProgressMonitor)} method will delegate its
   * work to the slave store. If {@link #childNames(int, IProgressMonitor)}
   * is called without activating the store the cached names are returned.
   */
  public void activate() {
    setActive( true );
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#childNames(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public String[] childNames( final int options,
                              final IProgressMonitor monitor )
      throws CoreException {
    
    String[] result = this.childNames;
    
    if ( isActive() ) {
      setActive( false );
      if ( result != null ) {
        FileStoreRegistry registry = FileStoreRegistry.getInstance();
        for ( String name : result ) {
          IFileStore child = getChild( name );
          registry.removeStore( child );
        }
      }
      result = getSlave().childNames( options, monitor( monitor ) );
      this.childNames = result;
    }
    
    if ( result == null ) {
      result = FileStore.EMPTY_STRING_ARRAY;
    }
    
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#delete(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public void delete( final int options,
                      final IProgressMonitor monitor )
      throws CoreException {
    getSlave().delete( options, monitor( monitor ) );
    FileStoreRegistry registry = FileStoreRegistry.getInstance();
    registry.removeStore( this );
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#fetchInfo(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public IFileInfo fetchInfo( final int options,
                              final IProgressMonitor monitor )
      throws CoreException {
    IFileInfo fileInfo = getSlave().fetchInfo( options, monitor( monitor ) );
    return fileInfo; 
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
        result = new GEclipseFileStore( this, child );
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
      result = registry.getStore( dir );
      if ( result == null ) {
        result = new GEclipseFileStore( this, dir );
        registry.putStore( result );
      }
    }
    return result;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#openInputStream(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public InputStream openInputStream( final int options,
                                      final IProgressMonitor monitor )
      throws CoreException {
    return getSlave().openInputStream( options, monitor( monitor ) );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#openOutputStream(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public OutputStream openOutputStream( final int options,
                                        final IProgressMonitor monitor )
      throws CoreException {
    return getSlave().openOutputStream( options, monitor( monitor ) );
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
   * Reset this file store. Resetting a file store means deleting the cached
   * child names.
   */
  public void reset() {
    this.childNames = null;
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
  private boolean isActive() {
    return this.isActive;
  }
  
  /**
   * Set this stores activation state.
   * 
   * @param active True if the store should be active.
   */
  private void setActive( final boolean active ) {
    this.isActive = active;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#move(org.eclipse.core.filesystem.IFileStore, int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public void move( final IFileStore destination,
                    final int options,
                    final IProgressMonitor monitor ) throws CoreException
  {
    boolean done = false;
    if( destination instanceof GEclipseFileStore ) {
      IFileStore src = this.slave;
      IFileStore dst = ( ( GEclipseFileStore )destination ).getSlave();

      if( src.getFileSystem().getScheme().equalsIgnoreCase( dst.getFileSystem().getScheme() ) ) {
        String srcHost = src.toURI().getHost();
        
        if( srcHost != null
            && srcHost.equalsIgnoreCase( dst.toURI().getHost() ) ) {
          src.move( dst, options, monitor );
          done = true;
        }
      }
    }
    if (!done) {
      super.move( destination, options, monitor( monitor ) );
    }
  }
  
  private IProgressMonitor monitor( final IProgressMonitor monitor ) {
    return new MasterMonitor( monitor, this.externalMonitor );
  }

}
