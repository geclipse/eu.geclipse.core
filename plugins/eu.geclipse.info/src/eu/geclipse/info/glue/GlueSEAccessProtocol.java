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

import java.util.ArrayList;

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
    this.UniqueID = GlueUtility.getStringAttribute( "GlueChunkKey", attributes ); //$NON-NLS-1$
    this.UniqueID = this.UniqueID.substring( this.UniqueID.indexOf( '=' ) + 1 );
    
    //this.AccessTime
    this.Endpoint = GlueUtility.getStringAttribute( "GlueSEAccessProtocolEndpoint", attributes ); //$NON-NLS-1$
    this.Port = GlueUtility.getLongAttribute( "GlueSEAccessProtocolPort", attributes ); //$NON-NLS-1$
    this.Type = GlueUtility.getStringAttribute( "GlueSEAccessProtocolType", attributes ); //$NON-NLS-1$
    this.Version = GlueUtility.getStringAttribute( "GlueSEAccessProtocolVersion", attributes ); //$NON-NLS-1$
  }
  
  /**
   * It compares two GlueSE
   * @param otherObject the object to compare the current object with
   * @return Returns true if both objects have the same values in the following 
   * fields: Endpoint, Port, Type and Version. False otherwise or 
   * if otherObject=null.
   */
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
}
