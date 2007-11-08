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

/**
 * @author George Tsouloupas
 * TODO Write Comments
 */
public class GlueCE extends AbstractGlueTable implements java.io.Serializable{
  
  private static final long serialVersionUID = 1L;
  
  /**
   * 
   */
  public String UniqueID; //PK

  /**
   * 
   */
  public String keyName = "UniqueID"; //$NON-NLS-1$

  public String GlueForeignKey = ""; //$NON-NLS-1$
  /**
   * 
   */
  public String Name;

  /**
   * 
   */
  public GlueCluster glueCluster; //GlueClusterUniqueID

  /**
   * 
   */
  public Long TotalCPUs;

  /**
   * 
   */
  public String LRMSType;

  /**
   * 
   */

  /**
   * 
   */
  public String LRMSVersion;

  /**
   * 
   */
  public String GRAMVersion;

  /**
   * 
   */
  public String HostName;

  /**
   * 
   */
  public String GatekeeperPort;

  /**
   * 
   */
  public Integer RunningJobs;

  /**
   * 
   */
  public Integer WaitingJobs;

  /**
   * 
   */
  public Long TotalJobs;

  /**
   * 
   */
  public String Status;

  /**
   * 
   */
  public Long WorstResponseTime;

  /**
   * 
   */
  public Long EstimatedResponseTime;

  /**
   * 
   */
  public Long FreeCpus;

  /**
   * 
   */
  public Long Priority;

  /**
   * 
   */
  public Long MaxRunningJobs;

  /**
   * 
   */
  public Long MaxTotalJobs;

  /**
   * 
   */
  public Long MaxCPUTime;

  /**
   * 
   */
  public Long MaxWallClockTime;

  /**
   * 
   */
  public String InformationServiceURL;

  /**
   * 
   */
  public String JobManager;

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
  public String DefaultSE;
  
  /**
   * 
   */
  public Long FreeJobSlots;
  
  /**
   * 
   */
  public Long AssignedCPUs;
  
  /**
   * 
   */
  public Long AssignedJobSlots;
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
  public ArrayList<GlueCEAccessControlBaseRule> glueCEAccessControlBaseRuleList = new ArrayList<GlueCEAccessControlBaseRule>();
  
  /**
   * 
   */
  public ArrayList<GlueCEContactString> glueCEContactStringList = new ArrayList<GlueCEContactString>();
  
  /**
   * 
   */
  public ArrayList<GlueCESEBind> glueCESEBindList = new ArrayList<GlueCESEBind>();
  
  /**
   * 
   */
  public ArrayList<GlueCEVOView> glueCEVOViewList = new ArrayList<GlueCEVOView>();

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

  public void setID( final String id ) {
    this.UniqueID = id;
  }
  
  public void processGlueRecord(final Attributes attributes){
    glueIndex = GlueIndex.getInstance();
    
    try {
      Attribute attr=attributes.get( "GlueCEUniqueID" );
      if(attr!=null){
        String id=attr.get(0).toString();
        this.UniqueID=id;


        this.ApplicationDir = GlueUtility.getStringAttribute( "GlueCEInfoApplicationDir", attributes);
        this.DataDir = GlueUtility.getStringAttribute( "GlueCEInfoDataDir", attributes);
        this.GatekeeperPort = GlueUtility.getStringAttribute( "GlueCEInfoGatekeeperPort", attributes);
        this.HostName = GlueUtility.getStringAttribute( "GlueCEInfoHostName", attributes);
        this.JobManager = GlueUtility.getStringAttribute( "GlueCEInfoJobManager", attributes);
        this.LRMSType = GlueUtility.getStringAttribute( "GlueCEInfoLRMSType", attributes);
        this.LRMSVersion = GlueUtility.getStringAttribute( "GlueCEInfoLRMSVersion", attributes);
        this.TotalCPUs = GlueUtility.getLongAttribute( "GlueCEInfoTotalCPUs", attributes);
        this.Name = GlueUtility.getStringAttribute( "GlueCEName", attributes);
        this.AssignedJobSlots = GlueUtility.getLongAttribute( "GlueCEPolicyAssignedJobSlots", attributes);
        this.MaxCPUTime = GlueUtility.getLongAttribute( "GlueCEPolicyMaxCPUTime", attributes);
        this.MaxRunningJobs = GlueUtility.getLongAttribute( "GlueCEPolicyMaxRunningJobs", attributes);
        this.MaxTotalJobs = GlueUtility.getLongAttribute( "GlueCEPolicyMaxTotalJobs", attributes);
        this.MaxWallClockTime = GlueUtility.getLongAttribute( "GlueCEPolicyMaxWallClockTime", attributes);
        this.Priority = GlueUtility.getLongAttribute( "GlueCEPolicyPriority", attributes);
        this.EstimatedResponseTime = GlueUtility.getLongAttribute( "GlueCEStateEstimatedResponseTime", attributes);
        this.FreeCpus = GlueUtility.getLongAttribute( "GlueCEStateFreeCPUs", attributes);
        this.FreeJobSlots = GlueUtility.getLongAttribute( "GlueCEStateFreeJobSlots", attributes);
        this.RunningJobs = GlueUtility.getIntegerAttribute( "GlueCEStateRunningJobs", attributes);
        this.Status = GlueUtility.getStringAttribute( "GlueCEStateStatus", attributes);
        this.TotalJobs = GlueUtility.getLongAttribute( "GlueCEStateTotalJobs", attributes);
        this.WaitingJobs = GlueUtility.getIntegerAttribute( "GlueCEStateWaitingJobs", attributes);
        this.WorstResponseTime = GlueUtility.getLongAttribute( "GlueCEStateWorstResponseTime", attributes);
        
        this.InformationServiceURL = GlueUtility.getStringAttribute( "GlueInformationServiceURL", attributes );
        this.InformationServiceURL = this.InformationServiceURL.substring( 0, this.InformationServiceURL.indexOf( "/mds-" ) );
        
        this.byRefOnly=false;
        
        attr=attributes.get( "GlueCEAccessControlBaseRule" );
        if(attr!=null){
          NamingEnumeration<?> ne = attr.getAll();
          while( ne.hasMoreElements() ) {
            String vo=ne.next().toString();
            GlueCEAccessControlBaseRule rule= new GlueCEAccessControlBaseRule();
            glueIndex.getGlueCEAccessControlBaseRule( vo );
            rule.Value=vo;
            rule.byRefOnly=false;
            Boolean exists = false;
            for (int i=0; i<this.glueCEAccessControlBaseRuleList.size(); i++)
            {
              if (this.glueCEAccessControlBaseRuleList.get( i ).Value.equalsIgnoreCase( vo ))
                exists = true;
            }
            if (!exists){
              this.glueCEAccessControlBaseRuleList.add(rule);
            }
          }
        }
      }
    } catch( NamingException e ) {
     //ignore missing fields
    }
  }

}
