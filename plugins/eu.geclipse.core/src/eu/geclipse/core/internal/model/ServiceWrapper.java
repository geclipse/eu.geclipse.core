package eu.geclipse.core.internal.model;

import java.net.URI;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridService;

public class ServiceWrapper
    extends WrappedElement
    implements IGridService {

  public ServiceWrapper( final IGridContainer parent,
                         final IGridElement wrapped ) {
    super( parent, wrapped );
  }

  public URI getURI() {
    return ( ( IGridService ) getWrappedElement() ).getURI();
  }

}
