package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
public class GlueCluster extends AbstractGlueTable implements java.io.Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public GlueIndex glueIndex;

  public String getID(){return UniqueID;}

  public void setID(String id){ UniqueID=id;}

  public String UniqueID; //PK
  public String keyName = "UniqueID";
  public String Name;
  public String InformationServiceURL;
  public GlueSite glueSite; //GlueSite_UniqueId
  public Date MeasurementDate;
  public Date MeasurementTime;
  public ArrayList<GlueSubCluster> glueSubClusterList = new ArrayList<GlueSubCluster>();
  public ArrayList<GlueCE> glueCEList = new ArrayList<GlueCE>();

}
