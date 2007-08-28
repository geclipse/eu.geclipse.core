package eu.geclipse.info.glue;

import java.util.Date;

public class GlueCESEBind extends AbstractGlueTable
  implements java.io.Serializable
{
  
  private static final long serialVersionUID = 1L;

  public GlueCE glueCE; // GlueCEUniqueID
  public GlueSE glueSE; // GlueSEUniqueID
  public String Accesspoint;
  public String MountInfo;
  public Long Weight;
  public Date MeasurementDate;
  public Date MeasurementTime;
  public GlueIndex glueIndex;

  public void setID( final String id ) {
    key = id;
  }
}
