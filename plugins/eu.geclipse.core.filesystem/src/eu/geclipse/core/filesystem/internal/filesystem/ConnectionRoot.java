package eu.geclipse.core.filesystem.internal.filesystem;

import java.net.URI;

import org.eclipse.core.resources.IFolder;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.core.model.IGridProject;

public class ConnectionRoot
    extends ConnectionElement
    implements IGridConnection {
  
  public ConnectionRoot( final IFolder folder ) {
    super( folder );
  }
  
  public URI getURI() {
    return null;
  }

  public boolean isGlobal() {
    IGridProject project = getProject();
    return project.isHidden();
  }
  
  @Override
  public boolean isHidden() {
    return getName().startsWith( "." ); //$NON-NLS-1$
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
