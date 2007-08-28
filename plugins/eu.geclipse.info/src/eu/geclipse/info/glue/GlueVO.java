package eu.geclipse.info.glue;

import java.util.Date;

public class GlueVO extends AbstractGlueTable implements java.io.Serializable {

  private static final long serialVersionUID = 1L;
  public GlueIndex glueIndex;
  public GlueHost glueHost; // GlueHostUniqueID
  public String Name; // PK
  public Date MeasurementDate;
  public Date MeasurementTime;

  public String getID() {
    return Name;
  }

  public void setID( final String id ) {
    Name = id;
  }
}
