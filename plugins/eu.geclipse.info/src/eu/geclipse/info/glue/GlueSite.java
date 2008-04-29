/******************************************************************************
 * Copyright (c) 2007,2008 g-Eclipse consortium
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
    // do nothing. 
  }
}
