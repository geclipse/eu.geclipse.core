package eu.geclipse.info.glue;

import java.util.Date;

public class GlueHostRemoteFileSystem extends AbstractGlueTable
  implements java.io.Serializable
{

  private static final long serialVersionUID = 1L;

  public GlueSubCluster glueSubCluster; // GlueSubClusterUniqueID
  public String Name; // PK
  public String Root;
  public Long Size;
  public Long AvailableSpace;
  public String ReadOnly;
  public String Type;
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
