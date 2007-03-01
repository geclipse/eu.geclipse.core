package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueHostPoolAccount extends AbstractGlueTable implements java.io.Serializable{
  public GlueIndex glueIndex;

  public String getID(){return Prefix;}

  public void setID(String id){ Prefix=id;}

  public GlueHost glueHost; //GlueHostUniqueID
  public String Prefix; //PK
  public String AssignedTo;
  public Integer Total;
  public Integer Free;
  public Date MeasurementDate;
  public Date MeasurementTime;

}
