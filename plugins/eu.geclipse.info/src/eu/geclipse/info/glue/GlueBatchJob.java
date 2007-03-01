package eu.geclipse.info.glue;

import java.util.Date;


public class GlueBatchJob extends AbstractGlueTable implements java.io.Serializable{
  /* (non-Javadoc)
   * @see eu.geclipse.info.glue.AbstractGlueTable#getID()
   */
  @Override
  public String getID(){return GlueBatchJobGlobalID;}

  public void setID(String id){ GlueBatchJobGlobalID=id;}

  public String GlueBatchJobGlobalID; //PK
  public GlueHost glueHost; //GlueHostUniqueID
  public Integer LocalID;
  public String Status;
  public String Name;
  public String LocalOwner;
  public String GlobalOwner;
  public String ExecutionTarget;
  public String CPUTime;
  public String WallTime;
  public String RAMUsed;
  public String VirtualUsed;
  public String CreationTime;
  public String StartTime;
  public String GlueBatchSystemType;
  public String Queue;
  public String VO;
  public Date MeasurementDate;
  public Date MeasurementTime;

}
