package eu.geclipse.info.glue;

import java.util.ArrayList;
import java.util.Date;

public class GlueSEControlProtocol extends AbstractGlueTable
  implements java.io.Serializable
{

  private static final long serialVersionUID = 1L;

  public String UniqueID; // PK
  public String keyName = "UniqueID";
  public GlueSE glueSE; // GlueSEUniqueID
  public String LocalID;
  public String Endpoint;
  public String Type;
  public String Version;
  public Date MeasurementDate;
  public Date MeasurementTime;
  public ArrayList<GlueSEControlProtocolCapability> glueSEControlProtocolCapabilityList = new ArrayList<GlueSEControlProtocolCapability>();
  public GlueIndex glueIndex;

  public String getID() {
    return UniqueID;
  }

  public void setID( final String id ) {
    UniqueID = id;
  }
}
