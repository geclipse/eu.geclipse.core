package eu.geclipse.info.glue;

import java.util.Date;

public class GlueCESEBind extends AbstractGlueTable implements java.io.Serializable{
  public GlueIndex glueIndex;
  private String key;
  public void setID(String id){ key=id;}
  
  public GlueCE glueCE; //GlueCEUniqueID
  public GlueSE glueSE; //GlueSEUniqueID
  public String Accesspoint;
  public String MountInfo;
  public Integer Weight;
  public Date MeasurementDate;
  public Date MeasurementTime;

}
