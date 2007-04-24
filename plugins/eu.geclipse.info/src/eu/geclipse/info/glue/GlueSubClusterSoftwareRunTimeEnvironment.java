package eu.geclipse.info.glue;

import java.util.Date;
public class GlueSubClusterSoftwareRunTimeEnvironment extends AbstractGlueTable implements java.io.Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public GlueIndex glueIndex;

  public String getID(){return Value;}

  public void setID(String id){ Value=id;}

  public String Value; //PK
  public GlueSubCluster glueSubCluster; //GlueSubClusterUniqueID
  public Date MeasurementDate;
  public Date MeasurementTime;

}
