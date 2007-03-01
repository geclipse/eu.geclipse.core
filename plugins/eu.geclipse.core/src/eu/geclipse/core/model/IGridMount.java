package eu.geclipse.core.model;
  
public interface IGridMount extends IGridResource, IGridContainer {
  
  public String getError();
  
  public boolean isFolder();
  
  public boolean isRoot();
  
  public boolean isValid();
  
}
