package eu.geclipse.info.glue;

import java.util.Date;
public class GlueBatchQueue extends AbstractGlueTable implements java.io.Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public GlueIndex glueIndex;

  public String getID(){return QueueName;}

  public void setID(String id){ QueueName=id;}

  public GlueHost glueHost; //GlueHostUniqueID
  public String QueueName; //PK
  public String JobFinishedTotal;
  public String JobFinishedOK;
  public String JobFinishedKO;
  public String JobRunning;
  public String JobWaiting;
  public Date MeasurementDate;
  public Date MeasurementTime;

}
