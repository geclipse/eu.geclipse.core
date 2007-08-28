package eu.geclipse.info.glue;

import java.util.Date;

public class GlueHostPoolAccount extends AbstractGlueTable
  implements java.io.Serializable
{

  private static final long serialVersionUID = 1L;

  public GlueHost glueHost; // GlueHostUniqueID
  public String Prefix; // PK
  public String AssignedTo;
  public Long Total;
  public Long Free;
  public Date MeasurementDate;
  public Date MeasurementTime;
  public GlueIndex glueIndex;

  public String getID() {
    return Prefix;
  }

  public void setID( final String id ) {
    Prefix = id;
  }
}
