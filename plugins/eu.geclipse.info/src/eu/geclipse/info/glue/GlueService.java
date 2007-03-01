package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueService extends AbstractGlueTable implements java.io.Serializable{
  public GlueIndex glueIndex;

  public String getID(){return UniqueId;}

  public void setID(String id){ UniqueId=id;}

  public String UniqueId; //PK
  public String keyName = "UniqueId";
  public String Name;
  public String Type;
  public String Version;
  public String Endpoint;
  public String WSDL;
  public String Semantics;
  public GlueSite glueSite; //GlueSite_UniqueId
  public Date MeasurementDate;
  public Date MeasurementTime;
  public ArrayList<GlueServiceAccessControlRule> glueServiceAccessControlRuleList = new ArrayList<GlueServiceAccessControlRule>();
  public ArrayList<GlueServiceAssociation> glueServiceAssociationList = new ArrayList<GlueServiceAssociation>();
  public ArrayList<GlueServiceAssociation> glueServiceAssociationList1 = new ArrayList<GlueServiceAssociation>();
  public ArrayList<GlueServiceData> glueServiceDataList = new ArrayList<GlueServiceData>();
  public ArrayList<GlueServiceOwner> glueServiceOwnerList = new ArrayList<GlueServiceOwner>();
  public ArrayList<GlueServiceStatus> glueServiceStatusList = new ArrayList<GlueServiceStatus>();

}
