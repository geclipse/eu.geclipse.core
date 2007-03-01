package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueServiceData extends AbstractGlueTable implements java.io.Serializable{
  public GlueIndex glueIndex;

  public String getID(){return DataKey;}

  public void setID(String id){ DataKey=id;}

  public GlueService glueService; //GlueService_UniqueId
  public String DataKey; //PK
  public String DataValue;
  public Date MeasurementDate;
  public Date MeasurementTime;

}
