package eu.geclipse.info.glue;

import java.util.ArrayList;
import java.util.Date;

public class GlueCluster extends AbstractGlueTable
  implements java.io.Serializable
{
  private static final long serialVersionUID = 1L;

  public String UniqueID; //PK
  public String keyName = "UniqueID";
  public String Name;
  public String InformationServiceURL;
  public GlueSite glueSite; //GlueSite_UniqueId
  public Date MeasurementDate;
  public Date MeasurementTime;
  public ArrayList<GlueSubCluster> glueSubClusterList = new ArrayList<GlueSubCluster>();
  public ArrayList<GlueCE> glueCEList = new ArrayList<GlueCE>();
  public GlueIndex glueIndex;

  public String getID() {
    return UniqueID;
  }

  public void setID( final String id ) {
    UniqueID = id;
  }
}
