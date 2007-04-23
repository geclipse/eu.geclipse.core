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

import java.net.URI;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import eu.geclipse.core.model.IGridElement;

/**
 * {@link IFolder} implementation in order to wrap
 * {@link GridConnectionElement}s.
 */
public class GridConnectionFolderAdapter
    extends GridConnectionResourceAdapter
    implements IFolder {

  GridConnectionFolderAdapter( final GridConnectionElement connection ) {
    super( connection );
  }

  public void create( final boolean force,
                      final boolean local,
                      final IProgressMonitor monitor )
      throws CoreException {
    create( ( force ? FORCE : IResource.NONE ), local, monitor );
  }

  public void create( final int updateFlags,
                      final boolean local,
                      final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
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

  public IFile getFile( final String name ) {
    IFileStore child = getFileStore().getChild( name );
    IFileInfo childInfo = child.fetchInfo();
    GridConnectionElement parent = getGridConnection();
    GridConnectionElement connection
      = new GridConnectionElement( parent, child, childInfo );
    IFile file = new GridConnectionFileAdapter( connection ); 
    return file;
  }

  public IFolder getFolder( final String name ) {
    IFileStore child = getFileStore().getChild( name );
    IFileInfo childInfo = child.fetchInfo();
    GridConnectionElement parent = getGridConnection();
    GridConnectionElement connection
      = new GridConnectionElement( parent, child, childInfo );
    IFolder folder = new GridConnectionFolderAdapter( connection ); 
    return folder;
  }

  public void move( final IPath destination,
                    final boolean force,
                    final boolean keepHistory,
                    final IProgressMonitor monitor )
      throws CoreException {
    move( destination,
          ( keepHistory ? KEEP_HISTORY : IResource.NONE )
          | (force ? FORCE : IResource.NONE),
          monitor );
  }

  public boolean exists( final IPath path ) {
    // TODO mathias
    return false;
  }

  public IFile[] findDeletedMembersWithHistory( final int depth,
                                                final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
    return null;
  }

  public IResource findMember( final String name ) {
    // TODO mathias
    return null;
  }

  public IResource findMember( final IPath path ) {
    // TODO mathias
    return null;
  }

  public IResource findMember( final String name,
                               final boolean includePhantoms ) {
    // TODO mathias
    return null;
  }

  public IResource findMember( final IPath path,
                               final boolean includePhantoms ) {
    // TODO mathias
    return null;
  }

  public String getDefaultCharset()
      throws CoreException {
    return getDefaultCharset( true );
  }

  public String getDefaultCharset( final boolean checkImplicit )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
    return null;
  }

  public IFile getFile( final IPath path ) {
    IFolder folder = this;
    for ( int i = 0 ; i < path.segmentCount() - 1 ; i++ ) {
      folder = folder.getFolder( path.segment( i ) );
    }
    IFile file = folder.getFile( path.lastSegment() );
    return file;
  }

  public IFolder getFolder( final IPath path ) {
    IFolder folder = this;
    for ( int i = 0 ; i < path.segmentCount() - 1 ; i++ ) {
      folder = folder.getFolder( path.segment( i ) );
    }
    folder = folder.getFolder( path.lastSegment() );
    return folder;
  }

  public IResource[] members()
      throws CoreException {
    return members( IResource.NONE );
  }

  public IResource[] members( final boolean includePhantoms )
      throws CoreException {
    return members( includePhantoms ? INCLUDE_PHANTOMS : IResource.NONE );
  }

  public IResource[] members( final int memberFlags )
      throws CoreException {
    
    IResource[] members = null;
    
    GridConnectionElement connection = getGridConnection();
    IGridElement[] children = connection.getChildren( null );
    if ( children != null ) {
      members = new IResource[ children.length ];
      for ( int i = 0 ; i < children.length ; i++ ) {
        members[ i ] = children[ i ].getResource();
      }
    }
    
    return members;
    
  }

  public void setDefaultCharset( final String charset )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  public void setDefaultCharset( final String charset,
                                 final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }
  
  public int getType() {
    return IResource.FOLDER;
  }
}
