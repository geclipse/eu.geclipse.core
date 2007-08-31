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
}
