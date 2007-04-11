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
  public Long TotalJobSlots;
  public Long FreeJobSlots;
  public Long NodeCount;
  public Double CPULoadAvg;
  public Long RAMTotal;
  public Long RAMUsed;
  public Long NodeDownCount;
  public String NodeDownList;
  public Date MeasurementDate;
  public Date MeasurementTime;

}
