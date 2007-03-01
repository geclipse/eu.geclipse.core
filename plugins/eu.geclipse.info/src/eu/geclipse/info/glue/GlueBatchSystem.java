package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueBatchSystem extends AbstractGlueTable implements java.io.Serializable{
  public GlueIndex glueIndex;

  public String getID(){return Type;}

  public void setID(String id){ Type=id;}

  public GlueHost glueHost; //GlueHostUniqueID
  public String Type; //PK
  public Integer TotalJobSlots;
  public Integer FreeJobSlots;
  public Integer NodeCount;
  public Double CPULoadAvg;
  public Integer RAMTotal;
  public Integer RAMUsed;
  public Integer NodeDownCount;
  public String NodeDownList;
  public Date MeasurementDate;
  public Date MeasurementTime;

}
