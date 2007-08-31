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

/**
 * @author George Tsouloupas
 * TODO Write Comments
 */
public class GlueBatchJob extends AbstractGlueTable implements java.io.Serializable{

  /**
   * 
   */
  public String GlueBatchJobGlobalID; //PK
  /**
   * 
   */
  public GlueHost glueHost; //GlueHostUniqueID
  /**
   * 
   */
  public Long LocalID;
  /**
   * 
   */
  public String Status;
  /**
   * 
   */
  public String Name;
  /**
   * 
   */
  public String LocalOwner;
  /**
   * 
   */
  public String GlobalOwner;
  /**
   * 
   */
  public String ExecutionTarget;
  /**
   * 
   */
  public String CPUTime;
  /**
   * 
   */
  public String WallTime;
  /**
   * 
   */
  public String RAMUsed;
  /**
   * 
   */
  public String VirtualUsed;
  /**
   * 
   */
  public String CreationTime;
  /**
   * 
   */
  public String StartTime;
  /**
   * 
   */
  public String GlueBatchSystemType;
  /**
   * 
   */
  public String Queue;
  /**
   * 
   */
  public String VO;
  /**
   * 
   */
  public Date MeasurementDate;
  /**
   * 
   */
  public Date MeasurementTime;

  private static final long serialVersionUID = 1L;

  /* (non-Javadoc)
   * @see eu.geclipse.info.glue.AbstractGlueTable#getID()
   */
  @Override
  public String getID() {
    return this.GlueBatchJobGlobalID;
  }

  /**
   * @param id
   */
  public void setID( final String id ) {
    this.GlueBatchJobGlobalID = id;
  }

}
