package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueHostLocalFileSystem extends AbstractGlueTable implements java.io.Serializable{
  public GlueIndex glueIndex;

  public String getID(){return Name;}

  public void setID(String id){ Name=id;}

  public GlueHost glueHost; //GlueHostUniqueID
  public String Name; //PK
  public String Root;
  public String Type;
  public Long Size;
  public Long AvailableSpace;
  public String ReadOnly;
  public Long INodeTotal;
  public Long INodeFree;
  public String GlueHostStorageExtentName;
  public String GlueHostStorageExtentSize;
  public String GlueHostStorageExtentReadRate;
  public String GlueHostStorageExtentWriteRate;
  public Date MeasurementDate;
  public Date MeasurementTime;

}
