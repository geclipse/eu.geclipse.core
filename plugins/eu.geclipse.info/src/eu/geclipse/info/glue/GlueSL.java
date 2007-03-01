package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueSL extends AbstractGlueTable implements java.io.Serializable{
  public GlueIndex glueIndex;

  public String getID(){return UniqueID;}

  public void setID(String id){ UniqueID=id;}

  public String UniqueID; //PK
  public String keyName = "UniqueID";
  public GlueSE glueSE; //GlueSEUniqueID
  public String Name;
  public String ArchitectureType;
  public Integer MaxIOCapacity;
  public String InformationServiceURL;
  public Date MeasurementDate;
  public Date MeasurementTime;

}
