package eu.geclipse.info.glue;

import java.util.ArrayList;
import java.util.Date;


public class GlueSE extends AbstractGlueTable implements java.io.Serializable {

  private static final long serialVersionUID = 1L;
  public String UniqueID; // PK
  public String keyName = "UniqueID";
  public GlueSL glueSL; // GlueSLUniqueID
  public String Name;
  public Long Port;
  public Long CurrentIOLoad;
  public String InformationServiceURL;
  public Long SizeTotal;
  public Long SizeFree;
  public String Architecture;
  public GlueSite glueSite; // GlueSite_UniqueId
  public Date MeasurementDate;
  public Date MeasurementTime;
  public ArrayList<GlueSL> glueSLList = new ArrayList<GlueSL>();
  public ArrayList<GlueCESEBind> glueCESEBindList = new ArrayList<GlueCESEBind>();
  public ArrayList<GlueSA> glueSAList = new ArrayList<GlueSA>();
  public ArrayList<GlueSEAccessProtocol> glueSEAccessProtocolList = new ArrayList<GlueSEAccessProtocol>();
  public ArrayList<GlueSEControlProtocol> glueSEControlProtocolList = new ArrayList<GlueSEControlProtocol>();
  public GlueIndex glueIndex;

  public String getID() {
    return UniqueID;
  }

  public void setID( final String id ) {
    UniqueID = id;
  }
}
