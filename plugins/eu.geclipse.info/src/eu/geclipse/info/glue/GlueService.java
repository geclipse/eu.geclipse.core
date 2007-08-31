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

import java.util.Date;
import java.util.ArrayList;

/**
 * @author George Tsouloupas
 * TODO Write Comments
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
}
