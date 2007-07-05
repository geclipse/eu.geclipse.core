package eu.geclipse.core.filesystem;

import java.net.URI;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;

import eu.geclipse.core.filesystem.internal.Activator;
import eu.geclipse.core.filesystem.internal.filesystem.ConnectionRoot;
import eu.geclipse.core.filesystem.internal.filesystem.IFileSystemProperties;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;

public class FileSystemCreator
    extends AbstractGridElementCreator {

  public boolean canCreate( final Class<? extends IGridElement> elementType ) {
    // TODO Auto-generated method stub
    return false;
  }

  public IGridElement create( final IGridContainer parent )
      throws GridModelException {
    IFolder folder = ( IFolder ) getObject();
    ConnectionRoot connection = new ConnectionRoot( parent, folder );
    try {
      IFileInfo info = connection.getConnectionFileInfo();
      if ( info instanceof FileInfo ) {
        ( ( FileInfo ) info ).setExists( true );
      }
    } catch ( CoreException cExc ) {
      // Should never happen, if it does just log it
      Activator.logException( cExc );
    }
    return connection;
  }
  
  @Override
  protected boolean internalCanCreate( final Object fromObject ) {
    boolean result = false;
    if ( fromObject instanceof IFolder ) {
      IFolder folder = ( IFolder ) fromObject;
      if ( folder.isLinked() ) {
        URI uri = folder.getRawLocationURI();
        String scheme = uri.getScheme();
        if ( IFileSystemProperties.SCHEME.equals( scheme ) ) {
          result = true;
        }
      }
    }
    return result;
  }

}
