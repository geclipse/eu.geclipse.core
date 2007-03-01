package eu.geclipse.core.internal.model;

import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.content.IContentDescription;

public class GridMountFileAdapter
    extends GridMountResourceAdapter
    implements IFile {
  
  private String charset; 
  
  GridMountFileAdapter( final GridMount gridMount ) {
    super( gridMount );
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
    this.charset = charset;
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

}