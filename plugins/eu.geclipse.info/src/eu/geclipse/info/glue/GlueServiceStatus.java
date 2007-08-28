package eu.geclipse.info.glue;

import java.util.Date;

public class GlueServiceStatus extends AbstractGlueTable
  implements java.io.Serializable
{

  private static final long serialVersionUID = 1L;
  public GlueService glueService; // GlueService_UniqueId
  public String Status;
  public String Message;
  public Date MeasurementDate;
  public Date MeasurementTime;

  public void setID( final String id ) {
    key = id;
  }
}
