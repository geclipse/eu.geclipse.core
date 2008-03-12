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
 *
 *****************************************************************************/

package eu.geclipse.info.glue;

import java.util.ArrayList;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import eu.geclipse.info.internal.Activator;

/**
 * @author George Tsouloupas
 * TODO Write Comments
 */
public class GlueSite extends AbstractGlueTable implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public String UniqueId; // PK

  /**
   * 
   */
  public String keyName = "UniqueId"; //$NON-NLS-1$

  /**
   * 
   */
  public String Name;

  /**
   * 
   */
  public String Description;

  /**
   * 
   */
  public String SysAdminContact;

  /**
   * 
   */
  public String UserSupportContact;

  /**
   * 
   */
  public String SecurityContact;

  /**
   * 
   */
  public String Location;

  /**
   * 
   */
  public Double Latitude;

  /**
   * 
   */
  public Double Longitude;

  /**
   * 
   */
  public String Web;
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
  public ArrayList< GlueSE > glueSEList = new ArrayList< GlueSE >();

  /**
   * 
   */
  public ArrayList< GlueCluster > glueClusterList = new ArrayList< GlueCluster >();

  /**
   * 
   */
  public ArrayList< GlueService > glueServiceList = new ArrayList< GlueService >();

  /**
   * 
   */
  public ArrayList< GlueSiteInfo > glueSiteInfoList = new ArrayList< GlueSiteInfo >();

  /**
   * 
   */
  public ArrayList< GlueSiteSponsor > glueSiteSponsorList = new ArrayList< GlueSiteSponsor >();

  /* (non-Javadoc)
   * @see eu.geclipse.info.glue.AbstractGlueTable#getID()
   */
  @Override
  public String getID() {
    return this.UniqueId;
  }

  /**
   * Set this.UniqueId
   * @param id
   */
  public void setID( final String id ) {
    this.UniqueId = id;
  }
  
  /**
   * 
   * @param attributes
   */
  public void processGlueRecord( final Attributes attributes ) {
    this.UniqueId = GlueUtility.getStringAttribute( "GlueSiteUniqueID", attributes ); //$NON-NLS-1$
    this.keyName = GlueUtility.getStringAttribute( "GlueSiteUniqueID", attributes ); //$NON-NLS-1$
    this.Name = GlueUtility.getStringAttribute( "GlueSiteName", attributes ); //$NON-NLS-1$
    this.Description = GlueUtility.getStringAttribute( "GlueSiteDescription", attributes ); //$NON-NLS-1$
    this.SysAdminContact = GlueUtility.getStringAttribute( "GlueSiteSysAdminContact", attributes ); //$NON-NLS-1$
    this.UserSupportContact = GlueUtility.getStringAttribute( "GlueSiteUserSupportContact", attributes ); //$NON-NLS-1$
    this.SecurityContact = GlueUtility.getStringAttribute( "GlueSiteSecurityContact", attributes ); //$NON-NLS-1$
    this.Location = GlueUtility.getStringAttribute( "GlueSiteLocation", attributes ); //$NON-NLS-1$
    try {
      this.Latitude = new Double( GlueUtility.getStringAttribute( "GlueSiteLatitude", attributes ) ); //$NON-NLS-1$
    } catch( NumberFormatException e ) {
      // Ignore Exception
    }
    try {
      this.Longitude = new Double( GlueUtility.getStringAttribute( "GlueSiteLongitude", attributes ) ); //$NON-NLS-1$
    } catch( NumberFormatException e ) {
      // Ignore Exception
    }
    this.Web = GlueUtility.getStringAttribute( "GlueSiteWeb", attributes ); //$NON-NLS-1$
    this.byRefOnly = false;
    this.tableName = "GlueSite"; //$NON-NLS-1$
    
    // Get the other info
    Attribute attr = attributes.get( "GlueSiteOtherInfo" ); //$NON-NLS-1$
    if( attr != null ) {
      try {
        NamingEnumeration< ? > ne = attr.getAll();
        while( ne.hasMoreElements() ) {
          String otherInfo =ne.next().toString();
          GlueSiteInfo mySiteInfo = new GlueSiteInfo();
          mySiteInfo.key = otherInfo;
          mySiteInfo.byRefOnly = false;
          mySiteInfo.OtherInfo = otherInfo;
          mySiteInfo.tableName = "GlueSiteOtherInfo"; //$NON-NLS-1$
          
          boolean exists = false;
          for ( int i = 0; i < this.glueSiteInfoList.size(); i++ ) {
            if ( this.glueSiteInfoList.get( i ).OtherInfo.equalsIgnoreCase( otherInfo ) )
              exists = true;
          }
          if ( !exists ) {
            this.glueSiteInfoList.add( mySiteInfo );
          }
        }
      } catch( NamingException e ) {
        // Just log the exception 
        Activator.logException( e );
      }
    }
    
    // Get the sponsor
    attr = attributes.get( "GlueSiteSponsor" ); //$NON-NLS-1$
    if( attr != null ) {
      try {
        NamingEnumeration< ? > ne = attr.getAll();
        while( ne.hasMoreElements() ) {
          String sponsorName = ne.next().toString();
          GlueSiteSponsor mySiteSponsor = new GlueSiteSponsor();
          mySiteSponsor.key = sponsorName;
          mySiteSponsor.byRefOnly = false;
          mySiteSponsor.Sponsor = sponsorName;
          mySiteSponsor.tableName = "GlueSiteSponsor"; //$NON-NLS-1$
            
          boolean exists = false;
          for ( int i = 0; i < this.glueSiteSponsorList.size(); i++ )
          {
            if ( this.glueSiteSponsorList.get( i ).Sponsor.equalsIgnoreCase( sponsorName ) )
              exists = true;
          }
          if ( !exists ) {
            this.glueSiteSponsorList.add( mySiteSponsor );
          }
        }
      } catch( NamingException e ) {
        // Just log the exception 
        Activator.logException( e );
      }
    }
  }
}
