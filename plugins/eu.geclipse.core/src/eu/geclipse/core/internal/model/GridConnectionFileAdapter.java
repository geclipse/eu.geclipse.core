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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URI;
import java.text.DecimalFormat;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentDescription;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridRoot;

/**
 * {@link IFile} implementation in order to wrap
 * {@link GridConnectionElement}s.
 */
public class GridConnectionFileAdapter
    extends GridConnectionResourceAdapter
    implements IFile {
  
  private String charset; 
  
  GridConnectionFileAdapter( final GridConnectionElement connection ) {
    super( connection );
  }

  public void appendContents( final InputStream source,
                              final int updateFlags,
                              final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  public void appendContents( final InputStream source,
                              final boolean force,
                              final boolean keepHistory,
                              final IProgressMonitor monitor )
      throws CoreException {
    appendContents( source,
                    ( keepHistory ? KEEP_HISTORY : IResource.NONE )
                    | ( force ? FORCE : IResource.NONE ),
                    monitor );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#copy(org.eclipse.core.runtime.IPath, int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public void copy( final IPath destination,
                    final int updateFlags,
                    final IProgressMonitor monitor )
      throws CoreException {
    IPath path = destination.removeLastSegments( 1 );
    IGridRoot gridRoot = GridModel.getRoot();
    IGridElement element = gridRoot.findElement( path );
    IContainer container = ( IContainer ) element.getResource();
    checkExists( container );
    IFile file = container.getFile( new Path( destination.lastSegment() ) );
    InputStream contents = getContents();
    file.create( contents, updateFlags, monitor );
  }

  public void create( final InputStream source,
                      final boolean force,
                      final IProgressMonitor monitor )
      throws CoreException {
    create( source, ( force ? FORCE : IResource.NONE ), monitor );
  }

  public void create( final InputStream source,
                      final int updateFlags,
                      final IProgressMonitor monitor )
      throws CoreException {
    
    IProgressMonitor localMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
      
    localMonitor.beginTask( "Creating " + getName(), 1 );

    try {
    
      BufferedInputStream biStream = new BufferedInputStream( source );
      IFileStore fileStore = getFileStore();
      OutputStream oStream = fileStore.openOutputStream( EFS.NONE, monitor );
      BufferedOutputStream boStream = new BufferedOutputStream( oStream );
      int b;
      long bcount = 0;
      
      try {
        while ( ( b = biStream.read() ) != -1 ) {
          boStream.write( b );
          bcount++;
          handleProgress( localMonitor, bcount );
          if ( localMonitor.isCanceled() ) break;
        }
        boStream.close();
        biStream.close();
      } catch ( IOException ioExc ) {
        IStatus status = new Status( IStatus.ERROR,
                                     Activator.PLUGIN_ID,
                                     IStatus.CANCEL,
                                     Messages.getString("GridConnectionFileAdapter.create_failed"), //$NON-NLS-1$
                                     ioExc );
        throw new CoreException( status );
      }
      
      localMonitor.worked( 1 );
      
      GridConnectionElement child = getGridConnection();
      IGridContainer parent = child.getParent();
      if ( parent instanceof GridConnectionElement ) {
        GridConnectionElement gce = ( GridConnectionElement ) parent;
        gce.addChild( child );
      } else {
        parent.setDirty();
        parent.getChildren( monitor );
      }
      
    } finally {
      localMonitor.done();
    }
    
  }

  public void createLink( final IPath localLocation,
                          final int updateFlags,
                          final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias    
    notYetImplemented();
  }

  public void createLink( final URI location,
                          final int updateFlags,
                          final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias    
    notYetImplemented();
  }

  public void delete( final boolean force,
                      final boolean keepHistory,
                      final IProgressMonitor monitor )
      throws CoreException {
    delete( ( keepHistory ? KEEP_HISTORY : IResource.NONE )
            | ( force ? FORCE : IResource.NONE ),
            monitor );
  }

  public String getCharset() throws CoreException {
    return getCharset( true );
  }

  public String getCharset( final boolean checkImplicit )
      throws CoreException {
    String result = exists() ? this.charset : null;
    if ( checkImplicit && ( result != null ) ) {
      getParent().getDefaultCharset( checkImplicit );
    }
    return null;
  }

  public String getCharsetFor( final Reader reader )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
    return null;
  }

  public IContentDescription getContentDescription()
      throws CoreException {
    return null;
  }

  public InputStream getContents()
      throws CoreException {
    InputStream result = null;
    IFileStore fileStore = getFileStore();
    if ( fileStore != null ) {
      result = fileStore.openInputStream( EFS.NONE, null );
    }
    return result;
  }

  public InputStream getContents( final boolean force )
      throws CoreException {
    return getContents();
  }

  public int getEncoding()
      throws CoreException {
    // TODO mathias
    notYetImplemented();
    return 0;
  }
  
  public IFileState[] getHistory( final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
    return null;
  }

  public void move( final IPath destination,
                    final boolean force,
                    final boolean keepHistory,
                    final IProgressMonitor monitor )
      throws CoreException {
    move( destination,
          ( keepHistory ? KEEP_HISTORY : IResource.NONE )
          | ( force ? FORCE : IResource.NONE ),
          monitor );
  }

  public void setCharset( final String newCharset )
      throws CoreException {
    setCharset( newCharset, new NullProgressMonitor() );
  }

  public void setCharset( final String newCharset,
                          final IProgressMonitor monitor )
      throws CoreException {
    this.charset = newCharset;
  }

  public void setContents( final InputStream source,
                           final int updateFlags,
                           final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  public void setContents( final IFileState source,
                           final int updateFlags,
                           final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  public void setContents( final InputStream source,
                           final boolean force,
                           final boolean keepHistory,
                           final IProgressMonitor monitor )
      throws CoreException {
    setContents( source,
                 ( keepHistory ? KEEP_HISTORY : IResource.NONE )
                 | ( force ? FORCE : IResource.NONE ),
                 monitor );
  }

  public void setContents( final IFileState source,
                           final boolean force,
                           final boolean keepHistory,
                           final IProgressMonitor monitor )
      throws CoreException {
    setContents( source,
                 ( keepHistory ? KEEP_HISTORY : IResource.NONE )
                 | ( force ? FORCE : IResource.NONE ),
                 monitor );
  }

  public int getType() {
    return IResource.FILE;
  }
  
  private void handleProgress( final IProgressMonitor monitor,
                               final long bytes ) {
    final long kb = 1024;
    final long mb = 1024*kb;
    final long gb = 1024*mb;
    if ( bytes < 1024 ) {
      monitor.subTask( getName() + " (" + bytes + "B transfered)" );
    } else if ( ( bytes < 1048576 ) && ( ( bytes % 1024 ) == 0 ) ) {
      monitor.subTask( getName() + " (" + ( bytes/1024 ) + "kB transfered)" );
    } else if ( ( bytes % ( 1048576/10 ) ) == 0 ) {
      DecimalFormat format = new DecimalFormat("#,###,##0.0");
      String mbstring = format.format( bytes/1048576. );
      monitor.subTask( getName() + " (" + mbstring + "MB transfered)" );
    }
  }
  
}
