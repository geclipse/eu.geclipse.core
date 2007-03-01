package eu.geclipse.core.model.impl;

import eu.geclipse.core.model.IGridJobCreator;
import eu.geclipse.core.model.IGridJobDescription;


public abstract class AbstractGridJobCreator
    extends AbstractGridElementCreator
    implements IGridJobCreator {
  
  private IGridJobDescription internalDescription;

  public boolean canCreate( final IGridJobDescription description ) {
    boolean result = internalCanCreate( description );
    if ( result ) {
      this.internalDescription = description;
    }
    return result;
  }
  
  public IGridJobDescription getDescription() {
    return this.internalDescription;
  }
  
  protected abstract boolean internalCanCreate( final IGridJobDescription description );
  
}
