package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueHostProcess extends AbstractGlueTable implements java.io.Serializable{
  public GlueIndex glueIndex;

  public String getID(){return Name;}

  public void setID(String id){ Name=id;}

  public GlueHost glueHost; //GlueHostUniqueID
  public String Name; //PK
  public String Command;
  public Integer FirstStarted;
  public Integer LastStarted;
  public Integer MemUsageOneMax;
  public Integer MemUsageAverage;
  public Integer CPUUsageOneMax;
  public Integer CPUUsageAll;
  public Integer TimeUsageOneMax;
  public Integer TimeUsageAll;
  public Integer NumberOfInstances;
  public String Status;
  public Date MeasurementDate;
  public Date MeasurementTime;

}
