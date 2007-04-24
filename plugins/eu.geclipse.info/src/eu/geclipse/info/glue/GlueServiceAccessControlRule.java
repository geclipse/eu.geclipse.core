package eu.geclipse.info.glue;

import java.util.Date;
public class GlueServiceAccessControlRule extends AbstractGlueTable implements java.io.Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public void setID(String id){ key=id;}
  
  public GlueService glueService; //GlueServiceUniqueID
  public String value;
  public Date MeasurementDate;
  public Date MeasurementTime;

}
