package eu.geclipse.core.model;

import java.util.EventListener;

public interface IGridModelListener
    extends EventListener {
  
  public void gridModelChanged( final IGridModelEvent event );
  
}
