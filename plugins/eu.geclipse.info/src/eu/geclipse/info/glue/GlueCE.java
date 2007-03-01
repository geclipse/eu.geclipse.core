package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;

public class GlueCE extends AbstractGlueTable implements java.io.Serializable{
  public GlueIndex glueIndex;

  public String getID(){return UniqueID;}

  public void setID(String id){ UniqueID=id;}

  public String UniqueID; //PK
  public String keyName = "UniqueID";
  public String Name;
  public GlueCluster glueCluster; //GlueClusterUniqueID
  public Integer TotalCPUs;
  public String LRMSType;
  public String LRMSVersion;
  public String GRAMVersion;
  public String HostName;
  public String GatekeeperPort;
  public Integer RunningJobs;
  public Integer WaitingJobs;
  public Integer TotalJobs;
  public String Status;
  public Integer WorstResponseTime;
  public Integer EstimatedResponseTime;
  public Integer FreeCpus;
  public Integer Priority;
  public Integer MaxRunningJobs;
  public Integer MaxTotalJobs;
  public Integer MaxCPUTime;
  public Integer MaxWallClockTime;
  public String InformationServiceURL;
  public String JobManager;
  public String ApplicationDir;
  public String DataDir;
  public String DefaultSE;
  public Integer FreeJobSlots;
  public Integer AssignedCPUs;
  public Integer AssignedJobSlots;
  public Date MeasurementDate;
  public Date MeasurementTime;
  public ArrayList<GlueCEAccessControlBaseRule> glueCEAccessControlBaseRuleList = new ArrayList<GlueCEAccessControlBaseRule>();
  public ArrayList<GlueCEContactString> glueCEContactStringList = new ArrayList<GlueCEContactString>();
  public ArrayList<GlueCESEBind> glueCESEBindList = new ArrayList<GlueCESEBind>();
  public ArrayList<GlueCEVOView> glueCEVOViewList = new ArrayList<GlueCEVOView>();

}
