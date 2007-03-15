package eu.geclipse.core.model;

public interface IGridModelNotifier {
  
  public void addGridModelListener( final IGridModelListener listener );
  
  public void removeGridModelListener( final IGridModelListener listener );
  
}
