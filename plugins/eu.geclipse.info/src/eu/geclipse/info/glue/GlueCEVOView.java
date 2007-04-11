package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueCEVOView extends AbstractGlueTable implements java.io.Serializable{
  public GlueIndex glueIndex;

  public String getID(){return UniqueID;}

  public void setID(String id){ UniqueID=id;}

  public String UniqueID; //PK
  public String keyName = "UniqueID";
  public GlueCE glueCE; //GlueCEUniqueID
  public String LocalID;
  public Long RunningJobs;
  public Long WaitingJobs;
  public Long TotalJobs;
  public Long FreeJobSlots;
  public Long EstimatedResponseTime;
  public Long WorstResponseTime;
  public String DefaultSE;
  public String ApplicationDir;
  public String DataDir;
  public Long FreeCpus;
  public Date MeasurementDate;
  public Date MeasurementTime;
  public ArrayList<GlueCEVOViewAccessControlBaseRule> glueCEVOViewAccessControlBaseRuleList = new ArrayList<GlueCEVOViewAccessControlBaseRule>();

}
