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
import java.util.Date;

/**
 * @author George Tsouloupas
 * TODO Write Comments
 */
public class GlueSE extends AbstractGlueTable implements java.io.Serializable {

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
  public GlueSL glueSL; // GlueSLUniqueID

  /**
   * 
   */
  public String Name;

  /**
   * 
   */
  public Long Port;

  /**
   * 
   */
  public Long CurrentIOLoad;

  /**
   * 
   */
  public String InformationServiceURL;

  /**
   * 
   */
  public Long SizeTotal;

  /**
   * 
   */
  public Long SizeFree;

  /**
   * 
   */
  public String Architecture;

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
  public ArrayList<GlueSL> glueSLList = new ArrayList<GlueSL>();

  /**
   * 
   */
  public ArrayList<GlueCESEBind> glueCESEBindList = new ArrayList<GlueCESEBind>();

  /**
   * 
   */
  public ArrayList<GlueSA> glueSAList = new ArrayList<GlueSA>();

  /**
   * 
   */
  public ArrayList<GlueSEAccessProtocol> glueSEAccessProtocolList = new ArrayList<GlueSEAccessProtocol>();

  /**
   * 
   */
  public ArrayList<GlueSEControlProtocol> glueSEControlProtocolList = new ArrayList<GlueSEControlProtocol>();

  /**
   * 
   */
  public GlueIndex glueIndex;

  @Override
  public String getID() {
    return this.UniqueID;
  }

  /**
   * @param id
   */
  public void setID( final String id ) {
    this.UniqueID = id;
  }
}
