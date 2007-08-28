package eu.geclipse.info.glue;

import java.util.Date;

public class GlueSiteInfo extends AbstractGlueTable
  implements java.io.Serializable
{

  private static final long serialVersionUID = 1L;
  public GlueIndex glueIndex;
  public GlueSite glueSite; // GlueSite_UniqueId
  public String OtherInfo; // PK
  public Date MeasurementDate;
  public Date MeasurementTime;

  public String getID() {
    return OtherInfo;
  }

  public void setID( final String id ) {
    OtherInfo = id;
  }
}
