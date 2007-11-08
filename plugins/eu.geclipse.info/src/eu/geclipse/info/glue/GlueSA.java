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

import java.util.Date;
import java.util.ArrayList;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

/**
 * @author George Tsouloupas
 * TODO Write Comments
 */
public class GlueSA extends AbstractGlueTable implements java.io.Serializable {

  private static final long serialVersionUID = 1L;
  

  /**
   * 
   */
  public String keyName = "UniqueID"; //$NON-NLS-1$

  /**
   * 
   */
  public String Root;

  /**
   * 
   */
  public GlueSE glueSE; // GlueSEUniqueID

  /**
   * 
   */
  public Long PolicyMaxFileSize;

  /**
   * 
   */
  public Long PolicyMinFileSize;

  /**
   * 
   */
  public Long PolicyMaxData;

  /**
   * 
   */
  public Long PolicyMaxNumFiles;

  /**
   * 
   */
  public Long PolicyMaxPinDuration;

  /**
   * 
   */
  public Long PolicyQuota;

  /**
   * 
   */
  public String PolicyFileLifeTime;

  /**
   * 
   */
  public Long StateAvailableSpace;

  /**
   * 
   */
  public Long StateUsedSpace;

  /**
   * 
   */
  public String LocalID;

  /**
   * 
   */
  public String Path;

  /**
   * 
   */
  public String Type;

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
  public ArrayList<GlueSAAccessControlBaseRule> glueSAAccessControlBaseRuleList = new ArrayList<GlueSAAccessControlBaseRule>();

  /**
   * 
   */
  public GlueIndex glueIndex;

  private String UniqueID;

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
    this.byRefOnly = false;
   
    this.keyName = GlueUtility.getStringAttribute( "GlueChunkKey", attributes );
    this.LocalID = GlueUtility.getStringAttribute( "GlueSALocalID", attributes );
    this.Path = GlueUtility.getStringAttribute( "GlueSAPath", attributes );
    this.PolicyFileLifeTime = GlueUtility.getStringAttribute( "GlueSAPolicyFileLifeTime", attributes );
    this.PolicyMaxData = GlueUtility.getLongAttribute( "GlueSAPolicyMaxData", attributes );
    this.PolicyMaxFileSize = GlueUtility.getLongAttribute( "GlueSAPolicyMaxFileSize", attributes );
    this.PolicyMaxNumFiles = GlueUtility.getLongAttribute( "GlueSAPolicyMaxNumFiles", attributes );
    this.PolicyMaxPinDuration = GlueUtility.getLongAttribute( "GlueSAPolicyMaxPinDuration", attributes ); 
    this.PolicyMinFileSize = GlueUtility.getLongAttribute( "GlueSAPolicyMinFileSize", attributes );
    this.PolicyQuota = GlueUtility.getLongAttribute( "GlueSAPolicyQuota", attributes );
    this.Root = GlueUtility.getStringAttribute( "GlueSARoot", attributes );
    this.StateAvailableSpace = GlueUtility.getLongAttribute( "GlueSAStateAvailableSpace", attributes );
    this.StateUsedSpace = GlueUtility.getLongAttribute( "GlueSAStateUsedSpace", attributes );
    this.Type = GlueUtility.getStringAttribute( "GlueSAType", attributes );
    
    try {
      Attribute attr=attributes.get( "GlueSAAccessControlBaseRule" );
      if(attr!=null){
        NamingEnumeration<?> ne = attr.getAll();
        while( ne.hasMoreElements() ) {
          String vo=ne.next().toString();
          GlueSAAccessControlBaseRule rule= new GlueSAAccessControlBaseRule();
          rule.Value=vo;
          rule.byRefOnly=false;
          Boolean exists = false;
          for (int i=0; i<this.glueSAAccessControlBaseRuleList.size(); i++)
          {
            if (this.glueSAAccessControlBaseRuleList.get( i ).Value.equalsIgnoreCase( vo ))
              exists = true;
          }
          if (!exists){
            this.glueSAAccessControlBaseRuleList.add(rule);
          }
        }
      }
    } catch( NamingException e ) {
      //ignore missing fields
    }
  }
}
