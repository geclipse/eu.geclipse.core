package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueServiceOwner extends AbstractGlueTable implements java.io.Serializable{
  public GlueIndex glueIndex;

  public String getID(){return Owner;}

  public void setID(String id){ Owner=id;}

  public GlueService glueService; //GlueService_UniqueId
  public String Owner; //PK
  public Date MeasurementDate;
  public Date MeasurementTime;

}
