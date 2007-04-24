package eu.geclipse.info.glue;

import java.util.Date;
public class GlueHostNetworkAdapter extends AbstractGlueTable implements java.io.Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public GlueIndex glueIndex;

  public String getID(){return Name;}

  public void setID(String id){ Name=id;}

  public GlueHost glueHost; //GlueHostUniqueID
  public String Name; //PK
  public String IPAddress;
  public Long MTU;
  public Long TXRate;
  public Long RXRate;
  public String LastOctetsTX;
  public String LastOctetsRX;
  public String OutboundIP;
  public String InboundIP;
  public Date MeasurementDate;
  public Date MeasurementTime;

}
