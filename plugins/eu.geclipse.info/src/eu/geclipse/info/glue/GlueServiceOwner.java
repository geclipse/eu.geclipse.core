package eu.geclipse.info.glue;

import java.util.Date;

public class GlueServiceOwner extends AbstractGlueTable
  implements java.io.Serializable
{

  private static final long serialVersionUID = 1L;
  public GlueService glueService; // GlueService_UniqueId
  public String Owner; // PK
  public Date MeasurementDate;
  public Date MeasurementTime;
  public GlueIndex glueIndex;

  public String getID() {
    return Owner;
  }

  public void setID( final String id ) {
    Owner = id;
  }
}
