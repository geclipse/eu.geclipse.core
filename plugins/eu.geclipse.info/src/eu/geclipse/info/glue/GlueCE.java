package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;

public class GlueCE extends AbstractGlueTable implements java.io.Serializable{
  
  private static final long serialVersionUID = 1L;
  
  public String UniqueID; //PK
  public String keyName = "UniqueID";
  public String Name;
  public GlueCluster glueCluster; //GlueClusterUniqueID
  public Long TotalCPUs;
  public String LRMSType;
  public String LRMSVersion;
  public String GRAMVersion;
  public String HostName;
  public String GatekeeperPort;
  public Integer RunningJobs;
  public Integer WaitingJobs;
  public Long TotalJobs;
  public String Status;
  public Long WorstResponseTime;
  public Long EstimatedResponseTime;
  public Long FreeCpus;
  public Long Priority;
  public Long MaxRunningJobs;
  public Long MaxTotalJobs;
  public Long MaxCPUTime;
  public Long MaxWallClockTime;
  public String InformationServiceURL;
  public String JobManager;
  public String ApplicationDir;
  public String DataDir;
  public String DefaultSE;
  public Long FreeJobSlots;
  public Long AssignedCPUs;
  public Long AssignedJobSlots;
  public Date MeasurementDate;
  public Date MeasurementTime;
  public ArrayList<GlueCEAccessControlBaseRule> glueCEAccessControlBaseRuleList = new ArrayList<GlueCEAccessControlBaseRule>();
  public ArrayList<GlueCEContactString> glueCEContactStringList = new ArrayList<GlueCEContactString>();
  public ArrayList<GlueCESEBind> glueCESEBindList = new ArrayList<GlueCESEBind>();
  public ArrayList<GlueCEVOView> glueCEVOViewList = new ArrayList<GlueCEVOView>();

  public GlueIndex glueIndex;

  public String getID() {
    return UniqueID;
  }

  public void setID( final String id ) {
    UniqueID = id;
  }

}
