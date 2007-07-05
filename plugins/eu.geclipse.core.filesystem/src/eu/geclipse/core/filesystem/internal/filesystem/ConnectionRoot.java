package eu.geclipse.core.filesystem.internal.filesystem;

import java.net.URI;

import org.eclipse.core.resources.IResource;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElementManager;

public class ConnectionRoot
    extends ConnectionElement
    implements IGridConnection {
  
  

  public ConnectionRoot( final IGridContainer parent,
                         final IResource resource ) {
    super( parent, resource );
  }

  public URI getURI() {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean isGlobal() {
    // TODO Auto-generated method stub
    return false;
  }

  public IGridElementManager getManager() {
    return GridModel.getConnectionManager();
  }

  public void load() throws GridModelException {
    // TODO Auto-generated method stub

  }

  public void save() throws GridModelException {
    // TODO Auto-generated method stub

  }

}
