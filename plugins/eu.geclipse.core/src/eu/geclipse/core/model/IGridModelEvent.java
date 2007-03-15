package eu.geclipse.core.model;

public interface IGridModelEvent {
  
  public static final int ELEMENTS_ADDED = 1;
  
  public static final int ELEMENTS_REMOVED = 2;
  
  public IGridElement[] getElements();
  
  public IGridElement getSource();
  
  public int getType();
  
}
