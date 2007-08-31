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
public class GlueCE extends AbstractGlueTable implements java.io.Serializable{
  
  private static final long serialVersionUID = 1L;
  
  public String UniqueID; //PK
  public String keyName = "UniqueID"; //$NON-NLS-1$
  public String Name;
  public GlueCluster glueCluster; //GlueClusterUniqueID
  public Long TotalCPUs;
  public String LRMSType;
  public String LRMSVersion;
  public String GRAMVersion;
  public String HostName;
  public String GatekeeperPort;
  public Integer RunningJobs;
  public Integer WaitingJobs;
  public Long TotalJobs;
  public String Status;
  public Long WorstResponseTime;
  public Long EstimatedResponseTime;
  public Long FreeCpus;
  public Long Priority;
  public Long MaxRunningJobs;
  public Long MaxTotalJobs;
  public Long MaxCPUTime;
  public Long MaxWallClockTime;
  public String InformationServiceURL;
  public String JobManager;
  public String ApplicationDir;
  public String DataDir;
  public String DefaultSE;
  public Long FreeJobSlots;
  public Long AssignedCPUs;
  public Long AssignedJobSlots;
  public Date MeasurementDate;
  public Date MeasurementTime;
  public ArrayList<GlueCEAccessControlBaseRule> glueCEAccessControlBaseRuleList = new ArrayList<GlueCEAccessControlBaseRule>();
  public ArrayList<GlueCEContactString> glueCEContactStringList = new ArrayList<GlueCEContactString>();
  public ArrayList<GlueCESEBind> glueCESEBindList = new ArrayList<GlueCESEBind>();
  public ArrayList<GlueCEVOView> glueCEVOViewList = new ArrayList<GlueCEVOView>();

  public GlueIndex glueIndex;

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
