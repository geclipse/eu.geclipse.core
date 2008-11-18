package eu.geclipse.core.model;

import eu.geclipse.core.config.IConfiguration;
import eu.geclipse.core.reporting.ProblemException;

public interface IConfigurableElementCreator
    extends IGridElementCreator {

  public IGridElement create( final IGridContainer parent,
                              final IConfiguration configuration )
      throws ProblemException;
  
  public IConfiguration getConfiguration();
  
  public void setConfiguration( final IConfiguration configuration );
  
}
