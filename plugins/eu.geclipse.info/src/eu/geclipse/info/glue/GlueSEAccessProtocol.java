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
 *      - Nikolaos Tsioutsias (tnikos@yahoo.com)
 *****************************************************************************/

package eu.geclipse.info.glue;

import java.util.ArrayList;
import java.util.Date;

import javax.naming.directory.Attributes;

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
  public Date MeasurementDate;

  /**
   * 
   */
  public Date MeasurementTime;

  /**
   * 
   */
  public ArrayList<GlueSEAccessProtocolCapability> glueSEAccessProtocolCapabilityList = new ArrayList<GlueSEAccessProtocolCapability>();

  /**
   * 
   */
  public ArrayList<GlueSEAccessProtocolSupportedSecurity> glueSEAccessProtocolSupportedSecurityList = new ArrayList<GlueSEAccessProtocolSupportedSecurity>();

  /**
   * 
   */
  public GlueIndex glueIndex;

  /* (non-Javadoc)
   * @see eu.geclipse.info.glue.AbstractGlueTable#getID()
   */
  @Override
  public String getID() {
    return this.UniqueID;
  }

  public void setID( final String id ) {
    this.UniqueID = id;
  }
  
  public void processGlueRecord( final Attributes attributes )
  {
    this.UniqueID = GlueUtility.getStringAttribute( "GlueChunkKey", attributes );
    this.UniqueID = this.UniqueID.substring( this.UniqueID.indexOf( '=' ) + 1 );
    
    //this.AccessTime
    this.Endpoint = GlueUtility.getStringAttribute( "GlueSEAccessProtocolEndpoint", attributes );
    this.Port = GlueUtility.getLongAttribute( "GlueSEAccessProtocolPort", attributes );
    this.Type = GlueUtility.getStringAttribute( "GlueSEAccessProtocolType", attributes );
    this.Version = GlueUtility.getStringAttribute( "GlueSEAccessProtocolVersion", attributes );
  }
  
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
