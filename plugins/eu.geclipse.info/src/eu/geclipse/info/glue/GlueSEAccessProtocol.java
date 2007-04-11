package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueSEAccessProtocol extends AbstractGlueTable implements java.io.Serializable{
  public GlueIndex glueIndex;

  public String getID(){return UniqueID;}

  public void setID(String id){ UniqueID=id;}

  public String UniqueID; //PK
  public String keyName = "UniqueID";
  public GlueSE glueSE; //GlueSEUniqueID
  public String Type;
  public Long Port;
  public String Version;
  public Long AccessTime;
  public String LocalID;
  public String Endpoint;
  public Date MeasurementDate;
  public Date MeasurementTime;
  public ArrayList<GlueSEAccessProtocolCapability> glueSEAccessProtocolCapabilityList = new ArrayList<GlueSEAccessProtocolCapability>();
  public ArrayList<GlueSEAccessProtocolSupportedSecurity> glueSEAccessProtocolSupportedSecurityList = new ArrayList<GlueSEAccessProtocolSupportedSecurity>();

}
