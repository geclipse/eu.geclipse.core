package eu.geclipse.core.internal.model;

import java.net.URI;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridStorage;

public class StorageWrapper
    extends WrappedElement
    implements IGridStorage {

  public StorageWrapper( final IGridContainer parent,
                         final IGridStorage storage ) {
    super( parent, storage );
  }

  public URI[] getAccessTokens() {
    return ( ( IGridStorage ) getWrappedElement() ).getAccessTokens();
  }

  public URI getURI() {
    return ( ( IGridStorage ) getWrappedElement() ).getURI();
  }

}
