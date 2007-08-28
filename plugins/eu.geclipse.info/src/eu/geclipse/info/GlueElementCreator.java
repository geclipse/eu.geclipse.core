package eu.geclipse.info;

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;
import eu.geclipse.info.glue.AbstractGlueTable;

public class GlueElementCreator extends AbstractGridElementCreator {

  @Override
  protected boolean internalCanCreate( final Object fromObject ) {
    return ( fromObject instanceof AbstractGlueTable );
  }

  public IGridElement create( final IGridContainer parent ) throws GridModelException
  {
    return null;
  }

  public boolean canCreate( final Class<? extends IGridElement> elementType ) {
    //TODO What will implement IGridElement
    return false;
  }
}
