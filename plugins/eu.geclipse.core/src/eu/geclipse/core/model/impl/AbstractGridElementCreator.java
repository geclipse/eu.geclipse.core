package eu.geclipse.core.model.impl;

import eu.geclipse.core.model.IGridElementCreator;

public abstract class AbstractGridElementCreator
    implements IGridElementCreator {
  
  private Object internalObject;
  
  public boolean canCreate( final Object fromObject ) {
    boolean result = internalCanCreate( fromObject );
    if ( result ) {
      setObject( fromObject );
    }
    return result;
  }

  public Object getObject() {
    return this.internalObject;
  }
  
  protected void setObject( final Object object ) {
    this.internalObject = object;
  }
  
  protected abstract boolean internalCanCreate( final Object fromObject );
  
}
