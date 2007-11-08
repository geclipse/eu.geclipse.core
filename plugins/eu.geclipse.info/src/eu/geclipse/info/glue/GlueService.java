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

public class GlueService extends AbstractGlueTable
  implements java.io.Serializable
{

  private static final long serialVersionUID = 1L;
 

  /**
   * 
   */
  public String uniqueId; // PK

  /**
   * 
   */
  public String name;

  /**
   * 
   */
  public String type;

  /**
   * 
   */
  public String version;

  /**
   * 
   */
  public String endpoint;

  /**
   * 
   */
  public String wsdl;

  /**
   * 
   */
  public String semantics;

  /**
   * 
   */
  public String uri;

  /**
   * 
   */
  public String status;

  /**
   * 
   */
  public GlueSite glueSite; // GlueSite_UniqueId

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
  public ArrayList<GlueServiceAccessControlRule> glueServiceAccessControlRuleList = new ArrayList<GlueServiceAccessControlRule>();

  /**
   * 
   */
  public ArrayList<GlueServiceAssociation> glueServiceAssociationList = new ArrayList<GlueServiceAssociation>();

  /**
   * 
   */
  public ArrayList<GlueServiceAssociation> glueServiceAssociationList1 = new ArrayList<GlueServiceAssociation>();

  /**
   * 
   */
  public ArrayList<GlueServiceData> glueServiceDataList = new ArrayList<GlueServiceData>();

  /**
   * 
   */
  public ArrayList<GlueServiceOwner> glueServiceOwnerList = new ArrayList<GlueServiceOwner>();

  /**
   * 
   */
  public ArrayList<GlueServiceStatus> glueServiceStatusList = new ArrayList<GlueServiceStatus>();

  /* (non-Javadoc)
   * @see eu.geclipse.info.glue.AbstractGlueTable#getID()
   */
  @Override
  public String getID() {
    return this.uniqueId;
  }

  public void setID( final String id ) {
    this.uniqueId = id;
  }
  
  public void processGlueRecord( final Attributes attributes )
  {
    this.uniqueId = GlueUtility.getStringAttribute( "GlueServiceUniqueID", attributes );
    this.byRefOnly = false;
    this.endpoint = GlueUtility.getStringAttribute( "GlueServiceEndpoint", attributes );
    

    this.name = GlueUtility.getStringAttribute( "GlueServiceName", attributes );
    this.semantics = GlueUtility.getStringAttribute( "GlueServiceSemantics", attributes );
    this.status = GlueUtility.getStringAttribute( "GlueServiceStatus", attributes );
    this.type = GlueUtility.getStringAttribute( "GlueServiceType", attributes );
    this.version = GlueUtility.getStringAttribute( "GlueServiceVersion", attributes );
    this.wsdl = GlueUtility.getStringAttribute( "GlueServiceWSDL", attributes );
    this.uri = GlueUtility.getStringAttribute( "GlueServiceURI", attributes );
    
    
    try {
      Attribute attr=attributes.get( "GlueServiceAccessControlRule" );
      if(attr!=null){
        NamingEnumeration<?> ne = attr.getAll();
        while( ne.hasMoreElements() ) {
          String vo=ne.next().toString();
          GlueServiceAccessControlRule rule= new GlueServiceAccessControlRule();
          rule.value=vo;
          rule.byRefOnly=false;
          Boolean exists = false;
          for (int i=0; i<this.glueServiceAccessControlRuleList.size(); i++)
          {
            if (this.glueServiceAccessControlRuleList.get( i ).value.equalsIgnoreCase( vo ))
              exists = true;
          }
          if (!exists){
            this.glueServiceAccessControlRuleList.add(rule);
          }
        }
      }
    } catch( NamingException e ) {
   //ignore missing fields
    }
    
  }
  
  public Boolean isSupported()
  {
    Boolean result = false;
    
    if (this.type.equalsIgnoreCase( "org.glite.wms" ))
      result = true;
    else if ((this.type.equalsIgnoreCase( "srm" ) || this.type.equalsIgnoreCase( "srm_v1" ))
             && this.version.equals( "2.1.1" ))
      result = true;
    else if (this.type.equalsIgnoreCase( "lcg-local-file-catalog" ))
      result = true;
    else if (this.type.equalsIgnoreCase("org.glite.ce"))
      result = true;
    
    return result;
  }
}
