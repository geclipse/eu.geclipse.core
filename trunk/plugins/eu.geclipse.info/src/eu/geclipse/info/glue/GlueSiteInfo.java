package eu.geclipse.info.glue;

import java.util.Date;
public class GlueSiteInfo extends AbstractGlueTable implements java.io.Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public GlueIndex glueIndex;

  public String getID(){return OtherInfo;}

  public void setID(String id){ OtherInfo=id;}

  public GlueSite glueSite; //GlueSite_UniqueId
  public String OtherInfo; //PK
  public Date MeasurementDate;
  public Date MeasurementTime;

}
