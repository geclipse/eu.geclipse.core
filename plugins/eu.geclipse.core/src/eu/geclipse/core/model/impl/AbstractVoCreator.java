package eu.geclipse.core.model.impl;

import eu.geclipse.core.config.IConfiguration;
import eu.geclipse.core.model.IConfigurableElementCreator;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.reporting.ProblemException;

public abstract class AbstractVoCreator
    extends AbstractGridElementCreator
    implements IConfigurableElementCreator {

  public IGridElement create( final IGridContainer parent,
                              final IConfiguration configuration )
      throws ProblemException {
    setConfiguration( configuration );
    return create( parent );
  }

  public IConfiguration getConfiguration() {
    Object source = getSource();
    return source instanceof IConfiguration
      ? ( IConfiguration ) source
      : null;
  }

  public void setConfiguration( final IConfiguration configuration ) {
    setSource( configuration );
  }

}
