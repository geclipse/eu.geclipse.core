/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - George Tsouloupas (georget@cs.ucy.ac.cy)
 *      - Nikolaos Tsioutsias
 *****************************************************************************/

package eu.geclipse.info.glue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.naming.directory.Attributes;

/**
 * 
 * @author George Tsouloupas
 *
 */
public class GlueSEAccessProtocol extends AbstractGlueTable
  implements java.io.Serializable
{

  private static final long serialVersionUID = 1L;


  /**
   * 
   */
  public String UniqueID; // PK

  /**
   * 
   */
  public String keyName = "UniqueID"; //$NON-NLS-1$

  /**
   * 
   */
  public GlueSE glueSE; // GlueSEUniqueID

  /**
   * 
   */
  public String Type;

  /**
   * 
   */
  public Long Port;

  /**
   * 
   */
  public String Version;

  /**
   * 
   */
  public Long AccessTime;

  /**
   * 
   */
  public String LocalID;

  /**
   * 
   */
  public String Endpoint;

  /**
   * 
   */
  //public Date MeasurementDate;

  /**
   * 
   */
  //public Date MeasurementTime;

  /**
   * 
   */
  public ArrayList<GlueSEAccessProtocolCapability> glueSEAccessProtocolCapabilityList
    = new ArrayList<GlueSEAccessProtocolCapability>();

  /**
   * 
   */
  public ArrayList<GlueSEAccessProtocolSupportedSecurity> glueSEAccessProtocolSupportedSecurityList
    = new ArrayList<GlueSEAccessProtocolSupportedSecurity>();

  /**
   * 
   */
  //private GlueIndex glueIndex;

  /* (non-Javadoc)
   * @see eu.geclipse.info.glue.AbstractGlueTable#getID()
   */
  @Override
  public String getID() {
    return this.UniqueID;
  }

  /**
   * Set this.UniqueID
   * @param id
   */
  public void setID( final String id ) {
    this.UniqueID = id;
  }
  
  /**
   * Process the attributes and fill the values of the GlueSE 
   * @param attributes
   */
  public void processGlueRecord( final Attributes attributes )
  {
    final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss"; //$NON-NLS-1$
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
    
    this.UniqueID = GlueUtility.getStringAttribute( "GlueChunkKey", attributes ); //$NON-NLS-1$
    this.UniqueID = this.UniqueID.substring( this.UniqueID.indexOf( '=' ) + 1 );
    
    this.Endpoint = GlueUtility.getStringAttribute( "GlueSEAccessProtocolEndpoint", attributes ); //$NON-NLS-1$
    this.Port = GlueUtility.getLongAttribute( "GlueSEAccessProtocolPort", attributes ); //$NON-NLS-1$
    this.Type = GlueUtility.getStringAttribute( "GlueSEAccessProtocolType", attributes ); //$NON-NLS-1$
    this.Version = GlueUtility.getStringAttribute( "GlueSEAccessProtocolVersion", attributes ); //$NON-NLS-1$
    this.setRetrievalTime( sdf.format(cal.getTime()) );
  }
  
  /*
  public boolean equals(final GlueSEAccessProtocol otherObject)
  {
    boolean result = false;
    
    if (otherObject!= null && this.Endpoint.equals( otherObject.Endpoint ) 
        && this.Port.equals( otherObject.Port ) 
        && this.Type.equals( otherObject.Type ) 
        && this.Version.equals( otherObject.Version ))
      result = true;
    
    return result;
  }
  */
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ( ( Endpoint == null )
                                                    ? 0
                                                    : Endpoint.hashCode() );
    result = prime * result + ( ( Port == null )
                                                ? 0
                                                : Port.hashCode() );
    result = prime * result + ( ( Type == null )
                                                ? 0
                                                : Type.hashCode() );
    result = prime * result + ( ( Version == null )
                                                   ? 0
                                                   : Version.hashCode() );
    return result;
  }

  /**
   * It compares two GlueSE
   * @param otherObject the object to compare the current object with
   * @return Returns true if both objects have the same values in the following 
   * fields: Endpoint, Port, Type and Version. False otherwise or 
   * if otherObject=null.
   */
  @Override
  public boolean equals( Object obj ) {
    if( this == obj )
      return true;
    if( obj == null )
      return false;
    if( getClass() != obj.getClass() )
      return false;
    final GlueSEAccessProtocol other = ( GlueSEAccessProtocol )obj;
    if( Endpoint == null ) {
      if( other.Endpoint != null )
        return false;
    } else if( !Endpoint.equals( other.Endpoint ) )
      return false;
    if( Port == null ) {
      if( other.Port != null )
        return false;
    } else if( !Port.equals( other.Port ) )
      return false;
    if( Type == null ) {
      if( other.Type != null )
        return false;
    } else if( !Type.equals( other.Type ) )
      return false;
    if( Version == null ) {
      if( other.Version != null )
        return false;
    } else if( !Version.equals( other.Version ) )
      return false;
    return true;
  }
}
