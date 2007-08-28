package eu.geclipse.info.glue;

import java.util.Date;

public class GlueServiceAssociation extends AbstractGlueTable
  implements java.io.Serializable
{

  private static final long serialVersionUID = 1L;
  public GlueService glueService1; // GlueService_UniqueId_1
  public GlueService glueService2; // GlueService_UniqueId_2
  public Date MeasurementDate;
  public Date MeasurementTime;

  public void setID( final String id ) {
    key = id;
  }
}
