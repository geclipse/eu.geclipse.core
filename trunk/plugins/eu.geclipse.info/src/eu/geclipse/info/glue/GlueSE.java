package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
public class GlueSE extends AbstractGlueTable implements java.io.Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public GlueIndex glueIndex;

  public String getID(){return UniqueID;}

  public void setID(String id){ UniqueID=id;}

  public String UniqueID; //PK
  public String keyName = "UniqueID";
  public GlueSL glueSL; //GlueSLUniqueID
  public String Name;
  public Long Port;
  public Long CurrentIOLoad;
  public String InformationServiceURL;
  public Long SizeTotal;
  public Long SizeFree;
  public String Architecture;
  public GlueSite glueSite; //GlueSite_UniqueId
  public Date MeasurementDate;
  public Date MeasurementTime;
  public ArrayList<GlueSL> glueSLList = new ArrayList<GlueSL>();
  public ArrayList<GlueCESEBind> glueCESEBindList = new ArrayList<GlueCESEBind>();
  public ArrayList<GlueSA> glueSAList = new ArrayList<GlueSA>();
  public ArrayList<GlueSEAccessProtocol> glueSEAccessProtocolList = new ArrayList<GlueSEAccessProtocol>();
  public ArrayList<GlueSEControlProtocol> glueSEControlProtocolList = new ArrayList<GlueSEControlProtocol>();

}
