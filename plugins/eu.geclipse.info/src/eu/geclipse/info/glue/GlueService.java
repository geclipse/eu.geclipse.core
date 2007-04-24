package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;

public class GlueService extends AbstractGlueTable implements java.io.Serializable{
  private static final long serialVersionUID = 1L;
  public String getID(){return uniqueId;}
  public void setID(String id){ uniqueId=id;}

  public String uniqueId; //PK
  public String name;
  public String type;
  public String version;
  public String endpoint;
  public String wsdl;
  public String semantics;
  public String uri;
  public String status;
  
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
