package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueSubClusterSoftwareRunTimeEnvironment extends AbstractGlueTable implements java.io.Serializable{
  public GlueIndex glueIndex;

  public String getID(){return Value;}

  public void setID(String id){ Value=id;}

  public String Value; //PK
  public GlueSubCluster glueSubCluster; //GlueSubClusterUniqueID
  public Date MeasurementDate;
  public Date MeasurementTime;

}
