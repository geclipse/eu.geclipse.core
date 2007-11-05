package eu.geclipse.core.internal.model;

import eu.geclipse.core.model.IGridElement;

public interface IQueryFilter {
  
  public IGridElement[] filter( final IGridElement[] input );

}
