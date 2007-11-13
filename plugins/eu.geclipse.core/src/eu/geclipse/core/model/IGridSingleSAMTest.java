package eu.geclipse.core.model;


public interface IGridSingleSAMTest extends IGridTest {
  
  public String getIdentifierName();
  public String getResult();
  public IGridTest getParentTest();
  public void setParentTest( final IGridSAMTest parent );
  
}
