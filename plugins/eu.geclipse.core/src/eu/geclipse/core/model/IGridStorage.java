package eu.geclipse.core.model;

import java.net.URI;

public interface IGridStorage extends IGridResource {
  
  public URI[] getAccessTokens();
  
}
