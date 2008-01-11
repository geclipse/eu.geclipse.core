package eu.geclipse.info.glue;


public class GriaService extends AbstractGlueTable{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public String uniqueId;
  public String name;
  public String uri;
  
  @Override
  public String getID() {
    return this.uniqueId;
  }
  
  public void setID( final String id ) {
    this.uniqueId = id;
  }
}
