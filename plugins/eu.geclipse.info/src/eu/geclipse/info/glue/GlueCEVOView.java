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

import javax.naming.directory.Attributes;

/**
 * @author George Tsouloupas
 * TODO Write Comments
 */
public class GlueCEVOView extends AbstractGlueTable
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
  //public GlueCE glueCE; // GlueCEUniqueID

  /**
   * 
   */
  public String LocalID;

  /**
   * 
   */
  public Long RunningJobs;

  /**
   * 
   */
  public Long WaitingJobs;

  /**
   * 
   */
  public Long TotalJobs;

  /**
   * 
   */
  public Long FreeJobSlots;

  /**
   * 
   */
  public Long EstimatedResponseTime;

  /**
   * 
   */
  public Long WorstResponseTime;

  /**
   * 
   */
  public String DefaultSE;

  /**
   * 
   */
  public String ApplicationDir;

  /**
   * 
   */
  public String DataDir;

  /**
   * 
   */
  //public Long FreeCpus;

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

  /**
   * 
   */
  //public ArrayList<GlueCEVOViewAccessControlBaseRule> glueCEVOViewAccessControlBaseRuleList 
  //  = new ArrayList<GlueCEVOViewAccessControlBaseRule>();

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

  /**
   * Set the unique id
   * @param id
   */
  public void setID( final String id ) {
    this.UniqueID = id;
  }
  
  /**
   * Fill the GlueCEVOView
   * @param attributes
   */
  public void processGlueRecord(final Attributes attributes)
  {
    this.byRefOnly = false;
    this.ApplicationDir = GlueUtility.getStringAttribute( "GlueCEInfoApplicationDir", attributes ); //$NON-NLS-1$
    this.DataDir = GlueUtility.getStringAttribute( "GlueCEInfoDataDir", attributes ); //$NON-NLS-1$
    this.DefaultSE = GlueUtility.getStringAttribute( "GlueCEInfoDefaultSE", attributes ); //$NON-NLS-1$
    this.EstimatedResponseTime = GlueUtility.getLongAttribute( "GlueCEStateEstimatedResponseTime", attributes ); //$NON-NLS-1$
    this.FreeJobSlots = GlueUtility.getLongAttribute( "GlueCEStateFreeJobSlots", attributes ); //$NON-NLS-1$
    this.setKey( GlueUtility.getStringAttribute( "GlueChunkKey", attributes ) ); //$NON-NLS-1$
    this.keyName = GlueUtility.getStringAttribute( "GlueChunkKey", attributes ); //$NON-NLS-1$
    this.LocalID = GlueUtility.getStringAttribute( "GlueVOViewLocalID", attributes ); //$NON-NLS-1$
    this.RunningJobs = GlueUtility.getLongAttribute( "GlueCEStateRunningJobs", attributes ); //$NON-NLS-1$
    this.tableName = "GlueCEVOView"; //$NON-NLS-1$
    this.TotalJobs = GlueUtility.getLongAttribute( "GlueCEStateTotalJobs", attributes ); //$NON-NLS-1$
    this.UniqueID = GlueUtility.getStringAttribute( "GlueChunkKey", attributes ); //$NON-NLS-1$
    this.WaitingJobs = GlueUtility.getLongAttribute( "GlueCEStateWaitingJobs", attributes ); //$NON-NLS-1$
    this.WorstResponseTime = GlueUtility.getLongAttribute( "GlueCEStateWorstResponseTime", attributes ); //$NON-NLS-1$
    this.setVoType( "glite" ); //$NON-NLS-1$
  }
}
