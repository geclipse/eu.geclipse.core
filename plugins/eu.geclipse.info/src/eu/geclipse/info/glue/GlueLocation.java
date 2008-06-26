package eu.geclipse.info.glue;


public class GlueLocation extends AbstractGlueTable{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public String UniqueID;
  
  public String schemaVersionMinor;
  public String version;
  
  public String name;
  
  public String locationPath;
  
  public  String schemaVersionMajor;
  
  public GlueSubCluster subCluster;
  
  @Override
  public String getID() {
    return this.UniqueID;
  }

  /**
   * Set this.name
   * @param id
   */
  public void setID( final String id ) {
    this.UniqueID = id;
  }
}
