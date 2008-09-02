package eu.geclipse.ui.wizards;

import java.net.URI;

import eu.geclipse.core.model.IGridContainer;

public interface IConnectionUriProcessor {

  public URI processURI( final IGridContainer parent, final URI uri );
  
}
