package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueSubClusterLocation extends AbstractGlueTable implements java.io.Serializable{
  public GlueIndex glueIndex;

  public String getID(){return UniqueID;}

  public void setID(String id){ UniqueID=id;}

  public String UniqueID; //PK
  public String keyName = "UniqueID";
  public GlueSubCluster glueSubCluster; //GlueSubClusterniqueID
  public String LocalID;
  public String Name;
  public String Version;
  public String Path;
  public Date MeasurementDate;
  public Date MeasurementTime;

}
