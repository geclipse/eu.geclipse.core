package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueServiceAssociation extends AbstractGlueTable implements java.io.Serializable{
  private String key;
  public void setID(String id){ key=id;}
  
  public GlueService glueService1; //GlueService_UniqueId_1
  public GlueService glueService2; //GlueService_UniqueId_2
  public Date MeasurementDate;
  public Date MeasurementTime;

}
