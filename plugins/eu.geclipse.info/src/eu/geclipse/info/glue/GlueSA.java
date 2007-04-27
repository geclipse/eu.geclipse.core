package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
public class GlueSA extends AbstractGlueTable implements java.io.Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public GlueIndex glueIndex;

  public String getID(){return UniqueID;}

  public void setID(String id){ UniqueID=id;}

  private String UniqueID;
  public String keyName = "UniqueID";
  public String Root;
  public GlueSE glueSE; //GlueSEUniqueID
  public Long PolicyMaxFileSize;
  public Long PolicyMinFileSize;
  public Long PolicyMaxData;
  public Long PolicyMaxNumFiles;
  public Long PolicyMaxPinDuration;
  public Long PolicyQuota;
  public String PolicyFileLifeTime;
  public Long StateAvailableSpace;
  public Long StateUsedSpace;
  public String LocalID;
  public String Path;
  public String Type;
  public Date MeasurementDate;
  public Date MeasurementTime;
  public ArrayList<GlueSAAccessControlBaseRule> glueSAAccessControlBaseRuleList = new ArrayList<GlueSAAccessControlBaseRule>();

}
