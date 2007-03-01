package eu.geclipse.core.internal.model;

import java.net.URI;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

public class GridMountFolderAdapter
    extends GridMountResourceAdapter
    implements IFolder {

  GridMountFolderAdapter( final GridMount gridMount ) {
    super( gridMount );
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
    // TODO mathias
    return null;
  }

  public IFolder getFolder( final String name ) {
    // TODO mathias
    return null;
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
    // TODO mathias
    notYetImplemented();
    return null;
  }

  public String getDefaultCharset( final boolean checkImplicit )
      throws CoreException {
    return getDefaultCharset( true );
  }

  public IFile getFile( final IPath path ) {
    // TODO mathias
    return null;
  }

  public IFolder getFolder( final IPath path ) {
    // TODO mathias
    return null;
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
    // TODO mathias
    notYetImplemented();
    return null;
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
  
}
