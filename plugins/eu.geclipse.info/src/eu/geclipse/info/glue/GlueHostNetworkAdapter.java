package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueHostNetworkAdapter extends AbstractGlueTable implements java.io.Serializable{
  public GlueIndex glueIndex;

  public String getID(){return Name;}

  public void setID(String id){ Name=id;}

  public GlueHost glueHost; //GlueHostUniqueID
  public String Name; //PK
  public String IPAddress;
  public Integer MTU;
  public Integer TXRate;
  public Integer RXRate;
  public String LastOctetsTX;
  public String LastOctetsRX;
  public String OutboundIP;
  public String InboundIP;
  public Date MeasurementDate;
  public Date MeasurementTime;

}
