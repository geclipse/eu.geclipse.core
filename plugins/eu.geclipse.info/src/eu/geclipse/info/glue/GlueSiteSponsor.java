package eu.geclipse.info.glue;

import java.util.Date;

public class GlueSiteSponsor extends AbstractGlueTable
  implements java.io.Serializable
{

  private static final long serialVersionUID = 1L;
  public GlueIndex glueIndex;
  public GlueSite glueSite; // GlueSite_UniqueId
  public String Sponsor; // PK
  public Date MeasurementDate;
  public Date MeasurementTime;

  public String getID() {
    return Sponsor;
  }

  public void setID( final String id ) {
    Sponsor = id;
  }
}
