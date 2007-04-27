package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
public class GlueSEControlProtocol extends AbstractGlueTable implements java.io.Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public GlueIndex glueIndex;

  public String getID(){return UniqueID;}

  public void setID(String id){ UniqueID=id;}

  public String UniqueID; //PK
  public String keyName = "UniqueID";
  public GlueSE glueSE; //GlueSEUniqueID
  public String LocalID;
  public String Endpoint;
  public String Type;
  public String Version;
  public Date MeasurementDate;
  public Date MeasurementTime;
  public ArrayList<GlueSEControlProtocolCapability> glueSEControlProtocolCapabilityList = new ArrayList<GlueSEControlProtocolCapability>();

}
