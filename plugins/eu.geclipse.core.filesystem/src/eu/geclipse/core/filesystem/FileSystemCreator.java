package eu.geclipse.core.filesystem;

import java.net.URI;

import org.eclipse.core.resources.IFolder;

import eu.geclipse.core.filesystem.internal.filesystem.ConnectionRoot;
import eu.geclipse.core.filesystem.internal.filesystem.FileSystemManager;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;

public class FileSystemCreator
    extends AbstractGridElementCreator {

  @Override
  protected boolean internalCanCreate( final Object fromObject ) {
    boolean result = false;
    if ( fromObject instanceof IFolder ) {
      IFolder folder = ( IFolder ) fromObject;
      if ( folder.isLinked() ) {
        URI uri = folder.getRawLocationURI();
        String scheme = uri.getScheme();
        if ( FileSystemManager.SCHEME.equals( scheme ) ) {
          result = true;
        }
      }
    }
    return result;
  }

  public boolean canCreate( final Class<? extends IGridElement> elementType ) {
    // TODO Auto-generated method stub
    return false;
  }

  public IGridElement create( final IGridContainer parent )
      throws GridModelException {
    IFolder folder = ( IFolder ) getObject();
    ConnectionRoot connection = new ConnectionRoot( parent, folder ); 
    return connection;
  }

}
