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
 *      - Nikolaos Tsioutsias
 *****************************************************************************/

package eu.geclipse.info.glue;

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

  /**
   * 
   */
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
  //public Date MeasurementDate;
  
  /**
   * 
   */
 // public Date MeasurementTime;
  
  /**
   * 
   */
  public ArrayList<GlueCEAccessControlBaseRule> glueCEAccessControlBaseRuleList = 
    new ArrayList<GlueCEAccessControlBaseRule>();
  
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
  private GlueIndex glueIndex;

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
   * Process the glue record and fill the object with values
   * @param attributes The attributes to process
   */
  public void processGlueRecord(final Attributes attributes){
    this.glueIndex = GlueIndex.getInstance();
    
    try {
      Attribute attr=attributes.get( "GlueCEUniqueID" ); //$NON-NLS-1$
      if(attr!=null){
        String id=attr.get(0).toString();
        this.UniqueID=id;


        this.ApplicationDir = GlueUtility.getStringAttribute( "GlueCEInfoApplicationDir", attributes); //$NON-NLS-1$
        this.DataDir = GlueUtility.getStringAttribute( "GlueCEInfoDataDir", attributes); //$NON-NLS-1$
        this.GatekeeperPort = GlueUtility.getStringAttribute( "GlueCEInfoGatekeeperPort", attributes); //$NON-NLS-1$
        this.HostName = GlueUtility.getStringAttribute( "GlueCEInfoHostName", attributes); //$NON-NLS-1$
        this.JobManager = GlueUtility.getStringAttribute( "GlueCEInfoJobManager", attributes); //$NON-NLS-1$
        this.LRMSType = GlueUtility.getStringAttribute( "GlueCEInfoLRMSType", attributes); //$NON-NLS-1$
        this.LRMSVersion = GlueUtility.getStringAttribute( "GlueCEInfoLRMSVersion", attributes); //$NON-NLS-1$
        this.TotalCPUs = GlueUtility.getLongAttribute( "GlueCEInfoTotalCPUs", attributes); //$NON-NLS-1$
        this.Name = GlueUtility.getStringAttribute( "GlueCEName", attributes); //$NON-NLS-1$
        this.AssignedJobSlots = 
          GlueUtility.getLongAttribute( "GlueCEPolicyAssignedJobSlots", attributes); //$NON-NLS-1$
        this.MaxCPUTime = GlueUtility.getLongAttribute( "GlueCEPolicyMaxCPUTime", attributes); //$NON-NLS-1$
        this.MaxRunningJobs = GlueUtility.getLongAttribute( "GlueCEPolicyMaxRunningJobs", attributes); //$NON-NLS-1$
        this.MaxTotalJobs = GlueUtility.getLongAttribute( "GlueCEPolicyMaxTotalJobs", attributes); //$NON-NLS-1$
        this.MaxWallClockTime = 
          GlueUtility.getLongAttribute( "GlueCEPolicyMaxWallClockTime", attributes); //$NON-NLS-1$
        this.Priority = GlueUtility.getLongAttribute( "GlueCEPolicyPriority", attributes); //$NON-NLS-1$
        this.EstimatedResponseTime = 
          GlueUtility.getLongAttribute( "GlueCEStateEstimatedResponseTime", attributes); //$NON-NLS-1$
        this.FreeCpus = GlueUtility.getLongAttribute( "GlueCEStateFreeCPUs", attributes); //$NON-NLS-1$
        this.FreeJobSlots = GlueUtility.getLongAttribute( "GlueCEStateFreeJobSlots", attributes); //$NON-NLS-1$
        this.RunningJobs = GlueUtility.getIntegerAttribute( "GlueCEStateRunningJobs", attributes); //$NON-NLS-1$
        this.Status = GlueUtility.getStringAttribute( "GlueCEStateStatus", attributes); //$NON-NLS-1$
        this.TotalJobs = GlueUtility.getLongAttribute( "GlueCEStateTotalJobs", attributes); //$NON-NLS-1$
        this.WaitingJobs = GlueUtility.getIntegerAttribute( "GlueCEStateWaitingJobs", attributes); //$NON-NLS-1$
        this.WorstResponseTime = 
          GlueUtility.getLongAttribute( "GlueCEStateWorstResponseTime", attributes); //$NON-NLS-1$
        this.DefaultSE = GlueUtility.getStringAttribute( "GlueCEInfoDefaultSE", attributes ); //$NON-NLS-1$
        this.InformationServiceURL = 
          GlueUtility.getStringAttribute( "GlueInformationServiceURL", attributes ); //$NON-NLS-1$
        this.InformationServiceURL = 
          this.InformationServiceURL.substring( 0, this.InformationServiceURL.indexOf( "/mds-" ) ); //$NON-NLS-1$
        this.GlueForeignKey = GlueUtility.getStringAttribute( "GlueForeignKey", attributes ); //$NON-NLS-1$
        this.GRAMVersion = GlueUtility.getStringAttribute( "GRAMVersion", attributes ); //$NON-NLS-1$
        this.tableName = "GlueCE"; //$NON-NLS-1$
        this.byRefOnly=false;
        
        attr=attributes.get( "GlueCEAccessControlBaseRule" ); //$NON-NLS-1$
        if(attr!=null){
          NamingEnumeration<?> ne = attr.getAll();
          while( ne.hasMoreElements() ) {
            String vo=ne.next().toString();
            GlueCEAccessControlBaseRule rule= new GlueCEAccessControlBaseRule();
            this.glueIndex.getGlueCEAccessControlBaseRule( vo );
            rule.Value=vo;
            rule.byRefOnly=false;
            rule.glueCE = this;
            boolean exists = false;
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
        
        attr=attributes.get( "GlueCEInfoContactString" ); //$NON-NLS-1$
        if(attr!=null){
          NamingEnumeration<?> ne = attr.getAll();
          while( ne.hasMoreElements() ) {
            String myContactString = ne.next().toString();
            GlueCEContactString contactString = new GlueCEContactString();
            contactString.byRefOnly = false;
            contactString.Value = myContactString;
            
            boolean exists = false;
            for (int i=0; i<this.glueCEContactStringList.size(); i++)
            {
              if (this.glueCEContactStringList.get( i ).Value.equalsIgnoreCase( myContactString ))
                exists = true;
            }
            if (!exists){
              this.glueCEContactStringList.add(contactString);
            }
          }
        }
      }
    } catch( NamingException e ) {
     //ignore missing fields
    }
  }

}
