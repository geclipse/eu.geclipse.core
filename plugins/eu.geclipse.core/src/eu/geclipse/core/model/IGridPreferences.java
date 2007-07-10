package eu.geclipse.core.model;

import java.net.URI;

public interface IGridPreferences {

  public void createGlobalConnection( final String name, final URI uri ) throws GridModelException;

}
