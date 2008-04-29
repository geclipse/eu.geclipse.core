/******************************************************************************
 * Copyright (c) 2007, 2008 g-Eclipse consortium
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

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

/**
 * 
 * @author George Tsouloupas
 *
 */
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
  public ArrayList<GlueService> glueServiceList = new ArrayList<GlueService>();
  
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
  //public ArrayList<GlueServiceAssociation> glueServiceAssociationList1 = new ArrayList<GlueServiceAssociation>();

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

  protected boolean isSupported = false;
  /* (non-Javadoc)
   * @see eu.geclipse.info.glue.AbstractGlueTable#getID()
   */
  @Override
  public String getID() {
    return this.uniqueId;
  }

  /**
   * Set this.uniqueId
   * @param id
   */
  public void setID( final String id ) {
    this.uniqueId = id;
  }
  
  /**
   * Checks if the current service is supported by g-eclipse 
   * @return true if it is supported and false otherwise.
   */
  public boolean isSupported()
  {
    return this.isSupported;
  }
}