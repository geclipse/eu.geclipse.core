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

import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import eu.geclipse.core.model.IGridProject;

/**
 * {@link IFile} and {@link IFolder} implementation in order to wrap
 * {@link GridConnectionElement}s.
 */
public class GridConnectionRootAdapter
    extends GridConnectionFolderAdapter
    implements IFile, IFolder {

  GridConnectionRootAdapter( final GridConnection connection ) {
    super( connection );
  }
  
  /**
   * Get the associated filesytem file.
   * 
   * @return The associated FS-file.
   */
  public IFile getFsFile() {
    return ( ( GridConnection ) getGridConnection() ).getFsFile();
  }
  
  public IFileStore getFsFileStore() {
    return ( ( GridConnection ) getGridConnection() ).getFsFileStore();
  }
  
  @Override
  public void accept( final IResourceVisitor visitor )
      throws CoreException {
    IFile fsFile = getFsFile();
    if ( fsFile != null ) {
      getFsFile().accept( visitor );
    } else {
      super.accept( visitor );
    }
  }

  @Override
  public void accept( final IResourceProxyVisitor visitor,
                      final int memberFlags )
      throws CoreException {
    IFile fsFile = getFsFile();
    if ( fsFile != null ) {
      getFsFile().accept( visitor, memberFlags );
    } else {
      super.accept( visitor, memberFlags );
    }
  }

  @Override
  public void accept( final IResourceVisitor visitor,
                      final int depth,
                      final boolean includePhantoms )
      throws CoreException {
    IFile fsFile = getFsFile();
    if ( fsFile != null ) {
      getFsFile().accept( visitor, depth, includePhantoms );
    } else {
      super.accept( visitor, depth, includePhantoms );
    }
  }

  @Override
  public void accept( final IResourceVisitor visitor,
                      final int depth,
                      final int memberFlags )
      throws CoreException {
    IFile fsFile = getFsFile();
    if ( fsFile != null ) {
      getFsFile().accept( visitor, depth, memberFlags );
    } else {
      super.accept( visitor, depth, memberFlags );
    }
  }

  public void appendContents( final InputStream source,
                              final int updateFlags,
                              final IProgressMonitor monitor )
      throws CoreException {
    IFile fsFile = getFsFile();
    if ( fsFile != null ) {
      getFsFile().appendContents( source, updateFlags, monitor );
    } else {
      notYetImplemented();
    }
  }

  public void appendContents( final InputStream source,
                              final boolean force,
                              final boolean keepHistory,
                              final IProgressMonitor monitor )
      throws CoreException {
    IFile fsFile = getFsFile();
    if ( fsFile != null ) {
      getFsFile().appendContents( source, force, keepHistory, monitor );
    } else {
      notYetImplemented();
    }
  }
  
  @Override
  public void clearHistory( final IProgressMonitor monitor )
      throws CoreException {
    IFile fsFile = getFsFile();
    if ( fsFile != null ) {
      getFsFile().clearHistory( monitor );
    } else {
      notYetImplemented();
    }
  }
  
  @Override
  public boolean contains( final ISchedulingRule rule ) {
    return getFsFile().contains( rule );
  }

  @Override
  public void copy( final IPath destination,
                    final boolean force,
                    final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().copy( destination, force, monitor );
  }

  @Override
  public void copy( final IPath destination,
                    final int updateFlags,
                    final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().copy( destination, updateFlags, monitor );
  }

  @Override
  public void copy( final IProjectDescription description,
                    final boolean force,
                    final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().copy( description, force, monitor );
  }

  @Override
  public void copy( final IProjectDescription description,
                    final int updateFlags,
                    final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().copy( description, updateFlags, monitor );
  }

  public void create( final InputStream source,
                      final boolean force,
                      final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().create( source, force, monitor );
  }

  public void create( final InputStream source,
                      final int updateFlags,
                      final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().create( source, updateFlags, monitor );
  }

  @Override
  public void createLink( final IPath localLocation,
                          final int updateFlags,
                          final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().createLink( localLocation, updateFlags, monitor );
  }

  @Override
  public void createLink( final URI location,
                          final int updateFlags,
                          final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().createLink( location, updateFlags, monitor );
  }
  
  @Override
  public IMarker createMarker( final String type )
      throws CoreException {
    return getFsFile().createMarker( type );
  }

  @Override
  public IResourceProxy createProxy() {
    return getFsFile().createProxy();
  }
  
  @Override
  public void delete( final boolean force,
                      final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().delete( force, monitor );
  }

  @Override
  public void delete( final int updateFlags,
                      final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().delete( updateFlags, monitor );
  }

  @Override
  public void delete( final boolean force,
                      final boolean keepHistory,
                      final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().delete( force, keepHistory, monitor );
  }
  
  @Override
  public void deleteMarkers( final String type,
                             final boolean includeSubtypes,
                             final int depth )
      throws CoreException {
    getFsFile().deleteMarkers( type, includeSubtypes, depth );
  }

  @Override
  public boolean exists() {
    return getFsFile().exists();
  }

  @Override
  public IMarker findMarker( final long id )
      throws CoreException {
    return getFsFile().findMarker( id );
  }

  @Override
  public IMarker[] findMarkers( final String type,
                                final boolean includeSubtypes,
                                final int depth )
      throws CoreException {
    return getFsFile().findMarkers( type, includeSubtypes, depth );
  }

  public String getCharset() throws CoreException {
    return getFsFile().getCharset();
  }

  public String getCharset( final boolean checkImplicit )
      throws CoreException {
    return getFsFile().getCharset( checkImplicit );
  }

  public String getCharsetFor( final Reader reader )
      throws CoreException {
    return getFsFile().getCharsetFor( reader );
  }

  public IContentDescription getContentDescription()
      throws CoreException {
    return getFsFile().getContentDescription();
  }

  public InputStream getContents()
      throws CoreException {
    return getFsFile().getContents();
  }

  public InputStream getContents( final boolean force )
      throws CoreException {
    return getFsFile().getContents( force );
  }

  @SuppressWarnings("deprecation")
  public int getEncoding()
      throws CoreException {
    return getFsFile().getEncoding();
  }
  
  @Override
  public String getFileExtension() {
    return GridConnection.FILE_EXTENSION;
  }
  
  @Override
  public IPath getFullPath() {
    return getFsFile().getFullPath();
  }
  
  public IFileState[] getHistory( final IProgressMonitor monitor )
      throws CoreException {
    return getFsFile().getHistory( monitor );
  }
  
  @Override
  public long getLocalTimeStamp() {
    return getFsFile().getLocalTimeStamp();
  }

  @Override
  public IPath getLocation() {
    return getFsFile().getLocation();
  }

  @Override
  public URI getLocationURI() {
    return getFsFile().getLocationURI();
  }

  @Override
  public IMarker getMarker( final long id ) {
    return getFsFile().getMarker( id );
  }

  @Override
  public long getModificationStamp() {
    return getFsFile().getModificationStamp();
  }
  
  @Override
  public String getName() {
    return getFsFileStore().getName();
  }

  @Override
  public IContainer getParent() {
    return getFsFile().getParent();
  }

  @Override
  public String getPersistentProperty( final QualifiedName key )
      throws CoreException {
    return getFsFile().getPersistentProperty( key );
  }

  @Override
  public IProject getProject() {
    GridConnectionElement connection = getGridConnection();
    IGridProject project = connection.getProject();
    return project == null ? null : ( IProject ) project.getResource();
  }

  @Override
  public IPath getProjectRelativePath() {
    return getFsFile().getProjectRelativePath();
  }

  @Override
  public IPath getRawLocation() {
    return getFsFile().getRawLocation();
  }

  @Override
  public URI getRawLocationURI() {
    return getFsFile().getRawLocationURI();
  }

  @Override
  public ResourceAttributes getResourceAttributes() {
    return getFsFile().getResourceAttributes();
  }

  @Override
  public Object getSessionProperty( final QualifiedName key )
      throws CoreException {
    return getFsFile().getSessionProperty( key );
  }

  @Override
  public int getType() {
    
    int result = IResource.FOLDER;
    
    IFile fsFile = getFsFile();
    if ( fsFile != null ) {
      result = fsFile.getType();
    }
    
    return result;
    
  }

  @Override
  public IWorkspace getWorkspace() {
    return getFsFile().getWorkspace();
  }
  
  @Override
  public boolean isAccessible() {
    boolean result = true;
    IFile fsFile = getFsFile();
    if ( fsFile != null ) {
      result = fsFile.isAccessible();
    }
    return result;
  }
  
  @Override
  public boolean isConflicting( final ISchedulingRule rule ) {
    boolean result = false;
    IFile fsFile = getFsFile();
    if ( fsFile != null ) {
      result = fsFile.isConflicting( rule );
    }
    return result;
  }

  @Override
  public boolean isDerived() {
    boolean result = false;
    IFile fsFile = getFsFile();
    if ( fsFile != null ) {
      result = fsFile.isDerived();
    }
    return result;
  }

  @Override
  public boolean isLinked() {
    boolean result = false;
    IFile file = getFsFile();
    if ( file != null ) {
      result = file.isLinked();
    }
    return result;
  }

  @Override
  public boolean isLinked( final int options ) {
    boolean result = false;
    IFile fsFile = getFsFile();
    if ( fsFile != null ) {
      result = fsFile.isLinked( options );
    }
    return result;
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean isLocal( final int depth ) {
    boolean result = true;
    IFile fsFile = getFsFile();
    if ( fsFile != null ) {
      result = fsFile.isLocal( depth );
    }
    return result;
  }

  @Override
  public boolean isPhantom() {
    boolean result = false;
    IFile fsFile = getFsFile();
    if ( fsFile != null ) {
      result = fsFile.isPhantom();
    }
    return result;
  }
  
  @Override
  public boolean isReadOnly() {
    boolean result = false;
    IFile fsFile = getFsFile();
    if ( fsFile != null ) {
      result = fsFile.isReadOnly();
    }
    return result;
  }

  @Override
  public boolean isSynchronized( final int depth ) {
    boolean result = false;
    IFile file = getFsFile();
    if ( file != null ) {
      result = file.isLinked( depth );
    }
    return result;
  }

  @Override
  public boolean isTeamPrivateMember() {
    boolean result = false;
    IFile fsFile = getFsFile();
    if ( fsFile != null ) {
      result = fsFile.isTeamPrivateMember();
    }
    return result;
  }
  
  @Override
  public void move( final IPath destination,
                    final boolean force,
                    final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().move( destination, force, monitor );
  }

  @Override
  public void move( final IPath destination,
                    final int updateFlags,
                    final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().move( destination, updateFlags, monitor );
  }

  @Override
  public void move( final IProjectDescription description,
                    final int updateFlags,
                    final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().move( description, updateFlags, monitor );
  }

  @Override
  public void move( final IProjectDescription description,
                    final boolean force,
                    final boolean keepHistory,
                    final IProgressMonitor monitor ) 
      throws CoreException {
    getFsFile().move( description, force, keepHistory, monitor );
  }

  @Override
  public void move( final IPath destination,
                    final boolean force,
                    final boolean keepHistory,
                    final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().move( destination, force, keepHistory, monitor );
  }
  
  @Override
  public void refreshLocal( final int depth,
                            final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().refreshLocal( depth, monitor );
  }

  @Override
  public void revertModificationStamp( final long value )
      throws CoreException {
    getFsFile().revertModificationStamp( value );
  }

  @SuppressWarnings("deprecation")
  public void setCharset( final String newCharset )
      throws CoreException {
    getFsFile().setCharset( newCharset );
  }

  public void setCharset( final String newCharset,
                          final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().setCharset( newCharset, monitor );
  }

  public void setContents( final InputStream source,
                           final int updateFlags,
                           final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().setContents( source, updateFlags, monitor );
  }

  public void setContents( final IFileState source,
                           final int updateFlags,
                           final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().setContents( source, updateFlags, monitor );
  }

  public void setContents( final InputStream source,
                           final boolean force,
                           final boolean keepHistory,
                           final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().setContents( source, force, keepHistory, monitor );
  }

  public void setContents( final IFileState source,
                           final boolean force,
                           final boolean keepHistory,
                           final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().setContents( source, force, keepHistory, monitor );
  }
  
  @Override
  public void setDerived( final boolean isDerived )
      throws CoreException {
    getFsFile().setDerived( isDerived );
  }

  @SuppressWarnings("deprecation")
  @Override
  public void setLocal( final boolean flag,
                        final int depth,
                        final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().setLocal( flag, depth, monitor );
  }

  @Override
  public long setLocalTimeStamp( final long value )
      throws CoreException {
    return getFsFile().setLocalTimeStamp( value );
  }

  @Override
  public void setPersistentProperty( final QualifiedName key,
                                     final String value )
      throws CoreException {
    getFsFile().setPersistentProperty( key, value );
  }

  @SuppressWarnings("deprecation")
  @Override
  public void setReadOnly( final boolean readOnly ) {
    getFsFile().setReadOnly( readOnly );
  }

  @Override
  public void setResourceAttributes( final ResourceAttributes attributes )
      throws CoreException {
    getFsFile().setResourceAttributes( attributes );
  }

  @Override
  public void setSessionProperty( final QualifiedName key,
                                  final Object value )
      throws CoreException {
    getFsFile().setSessionProperty( key, value );
  }

  @Override
  public void setTeamPrivateMember( final boolean isTeamPrivate )
      throws CoreException {
    getFsFile().setTeamPrivateMember( isTeamPrivate );
  }

  @Override
  public void touch( final IProgressMonitor monitor )
      throws CoreException {
    getFsFile().touch( monitor );
  }
  
}
