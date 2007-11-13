package eu.geclipse.core.model;

import java.util.List;


public interface IGridSAMTest extends IGridTest, IGridElement, IManageable {
  
  public String getName();
  
  public String getRegion();
  
  public String getSiteName();
  
  public String getNodeName();
  
  public String getStatus();
  
  public List<IGridSingleSAMTest> getTests();
  
  public void setTests( final List<IGridSingleSAMTest> tests );
}
