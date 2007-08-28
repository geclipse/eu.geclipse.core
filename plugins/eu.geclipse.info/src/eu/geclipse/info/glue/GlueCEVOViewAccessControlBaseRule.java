package eu.geclipse.info.glue;

import java.util.Date;
public class GlueCEVOViewAccessControlBaseRule extends AbstractGlueTable implements java.io.Serializable{

  private static final long serialVersionUID = 1L;
  
  public GlueCEVOView glueCEVOView; //GlueCEVOViewUniqueID
  public String Value; //PK
  public Date MeasurementDate;
  public Date MeasurementTime;
  
  public GlueIndex glueIndex;

  public String getID() {
    return Value;
  }

  public void setID( final String id ) {
    Value = id;
  }
}
