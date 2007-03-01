package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueServiceAccessControlRule extends AbstractGlueTable implements java.io.Serializable{
  private String key;
  public void setID(String id){ key=id;}
  
  public GlueService glueService; //GlueServiceUniqueID
  public String Value;
  public Date MeasurementDate;
  public Date MeasurementTime;

}
