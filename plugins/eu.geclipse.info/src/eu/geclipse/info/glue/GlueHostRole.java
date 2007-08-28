package eu.geclipse.info.glue;

import java.util.Date;

public class GlueHostRole extends AbstractGlueTable
  implements java.io.Serializable
{

  private static final long serialVersionUID = 1L;
  public GlueHost glueHost; // GlueHostUniqueID
  public String Name; // PK
  public Date MeasurementDate;
  public Date MeasurementTime;
  public GlueIndex glueIndex;

  public String getID() {
    return Name;
  }

  public void setID( final String id ) {
    Name = id;
  }
}
