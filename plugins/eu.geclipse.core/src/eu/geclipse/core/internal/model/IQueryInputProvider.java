package eu.geclipse.core.internal.model;

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElement;

public interface IQueryInputProvider {

  public IGridElement[] getInput() throws GridModelException;
  
}
