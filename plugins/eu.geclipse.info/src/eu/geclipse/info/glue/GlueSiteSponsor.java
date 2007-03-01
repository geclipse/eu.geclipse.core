package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueSiteSponsor extends AbstractGlueTable implements java.io.Serializable{
  public GlueIndex glueIndex;

  public String getID(){return Sponsor;}

  public void setID(String id){ Sponsor=id;}

  public GlueSite glueSite; //GlueSite_UniqueId
  public String Sponsor; //PK
  public Date MeasurementDate;
  public Date MeasurementTime;

}
