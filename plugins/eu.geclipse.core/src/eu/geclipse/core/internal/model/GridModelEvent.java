package eu.geclipse.core.internal.model;

import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;

public class GridModelEvent implements IGridModelEvent {
  
  private int type;
  
  private IGridElement source;
  
  private IGridElement[] elements;
  
  public GridModelEvent( final int type,
                         final IGridElement source,
                         final IGridElement[] elements ) {
    this.type = type;
    this.source = source;
    this.elements = elements;
  }

  public IGridElement[] getElements() {
    return this.elements;
  }

  public IGridElement getSource() {
    return this.source;
  }
  
  public int getType() {
    return this.type;
  }

}
