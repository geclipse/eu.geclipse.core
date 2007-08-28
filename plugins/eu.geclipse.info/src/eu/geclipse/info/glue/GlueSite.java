package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;

public class GlueSite extends AbstractGlueTable implements java.io.Serializable
{

  private static final long serialVersionUID = 1L;
  public GlueIndex glueIndex;
  public String UniqueId; // PK
  public String keyName = "UniqueId";
  public String Name;
  public String Description;
  public String SysAdminContact;
  public String UserSupportContact;
  public String SecurityContact;
  public String Location;
  public Double Latitude;
  public Double Longitude;
  public String Web;
  public Date MeasurementDate;
  public Date MeasurementTime;
  public ArrayList<GlueSE> glueSEList = new ArrayList<GlueSE>();
  public ArrayList<GlueCluster> glueClusterList = new ArrayList<GlueCluster>();
  public ArrayList<GlueService> glueServiceList = new ArrayList<GlueService>();
  public ArrayList<GlueSiteInfo> glueSiteInfoList = new ArrayList<GlueSiteInfo>();
  public ArrayList<GlueSiteSponsor> glueSiteSponsorList = new ArrayList<GlueSiteSponsor>();

  public String getID() {
    return UniqueId;
  }

  public void setID( final String id ) {
    UniqueId = id;
  }
}
