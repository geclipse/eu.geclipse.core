package eu.geclipse.info.glue;

import java.util.Date;

public class GlueSL extends AbstractGlueTable implements java.io.Serializable {

  private static final long serialVersionUID = 1L;
  public GlueIndex glueIndex;
  public String UniqueID; // PK
  public String keyName = "UniqueID";
  public GlueSE glueSE; // GlueSEUniqueID
  public String Name;
  public String ArchitectureType;
  public Long MaxIOCapacity;
  public String InformationServiceURL;
  public Date MeasurementDate;
  public Date MeasurementTime;

  public String getID() {
    return UniqueID;
  }

  public void setID( final String id ) {
    UniqueID = id;
  }
}
