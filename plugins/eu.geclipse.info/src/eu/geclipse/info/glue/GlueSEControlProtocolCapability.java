package eu.geclipse.info.glue;

import java.util.Date;
public class GlueSEControlProtocolCapability extends AbstractGlueTable implements java.io.Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public GlueIndex glueIndex;

  public String getID(){return Value;}

  public void setID(String id){ Value=id;}

  public GlueSEControlProtocol glueSEControlProtocol; //GlueSEControlProtocolUniqueID
  public String Value; //PK
  public Date MeasurementDate;
  public Date MeasurementTime;

}
