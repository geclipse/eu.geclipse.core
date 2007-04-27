package eu.geclipse.info.glue;

import java.util.Date;
public class GlueHostProcess extends AbstractGlueTable implements java.io.Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public GlueIndex glueIndex;

  public String getID(){return Name;}

  public void setID(String id){ Name=id;}

  public GlueHost glueHost; //GlueHostUniqueID
  public String Name; //PK
  public String Command;
  public Long FirstStarted;
  public Long LastStarted;
  public Long MemUsageOneMax;
  public Long MemUsageAverage;
  public Long CPUUsageOneMax;
  public Long CPUUsageAll;
  public Long TimeUsageOneMax;
  public Long TimeUsageAll;
  public Long NumberOfInstances;
  public String Status;
  public Date MeasurementDate;
  public Date MeasurementTime;

}
