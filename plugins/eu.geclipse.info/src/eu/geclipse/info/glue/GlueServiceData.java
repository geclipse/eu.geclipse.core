package eu.geclipse.info.glue;

import java.util.Date;

public class GlueServiceData extends AbstractGlueTable
  implements java.io.Serializable
{

  private static final long serialVersionUID = 1L;
  public GlueService glueService; // GlueService_UniqueId
  public String DataKey; // PK
  public String DataValue;
  public Date MeasurementDate;
  public Date MeasurementTime;
  public GlueIndex glueIndex;

  public String getID() {
    return DataKey;
  }

  public void setID( final String id ) {
    DataKey = id;
  }
}
