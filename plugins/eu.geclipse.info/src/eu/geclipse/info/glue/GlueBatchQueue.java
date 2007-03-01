package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueBatchQueue extends AbstractGlueTable implements java.io.Serializable{
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
