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
public class GlueHost extends AbstractGlueTable implements java.io.Serializable
{

  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public String Name;

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
  public Long UpTime;

  /**
   * 
   */
  public Long FileCounterRegularOpen;

  /**
   * 
   */
  public Long ProcessCounterTotal;

  /**
   * 
   */
  public Long ProcessCounterUninterruptibleSleep;

  /**
   * 
   */
  public Long ProcessCounterRunnable;

  /**
   * 
   */
  public Long ProcessCounterSleeping;

  /**
   * 
   */
  public Long ProcessCounterStopped;

  /**
   * 
   */
  public Long ProcessCounterZombie;

  /**
   * 
   */
  public Long ArchitectureSMPSize;

  /**
   * 
   */
  public Long ArchitectureSMTSize;

  /**
   * 
   */
  public String ProcessorVendor;

  /**
   * 
   */
  public String ProcessorModel;

  /**
   * 
   */
  public Long ProcessorClockSpeed;

  /**
   * 
   */
  public Long ProcessorLoadLast1Min;

  /**
   * 
   */
  public Long ProcessorLoadLast5Min;

  /**
   * 
   */
  public Long ProcessorLoadLast15Min;

  /**
   * 
   */
  public Long ProcessorLoadUser;

  /**
   * 
   */
  public Long ProcessorLoadNice;

  /**
   * 
   */
  public Long ProcessorLoadSystem;

  /**
   * 
   */
  public Long ProcessorLoadIdle;

  /**
   * 
   */
  public Long BenchmarkBogoMIPS;

  /**
   * 
   */
  public Long SocketCounterTotal;

  /**
   * 
   */
  public Long SocketCounterTCP;

  /**
   * 
   */
  public Long SocketCounterUDP;

  /**
   * 
   */
  public Long SocketCounterRAW;

  /**
   * 
   */
  public String OperatingSystemName;

  /**
   * 
   */
  public String OperatingSystemRelease;

  /**
   * 
   */
  public String OperatingSystemVersion;

  /**
   * 
   */
  public Long MainMemoryRAMSize;

  /**
   * 
   */
  public Long MainMemoryRAMUsed;

  /**
   * 
   */
  public Long MainMemoryRAMAvailable;

  /**
   * 
   */
  public Long MainMemoryRAMShared;

  /**
   * 
   */
  public Long MainMemoryRAMBuffer;

  /**
   * 
   */
  public Long MainMemoryRAMCached;

  /**
   * 
   */
  public Long MainMemoryVirtualSize;

  /**
   * 
   */
  public Long MainMemoryVirtualAvailable;

  /**
   * 
   */
  public Long MainMemoryVirtualUsed;

  /**
   * 
   */
  public Long MemoryPageCounterFrequencyRead;

  /**
   * 
   */
  public Long MemoryPageCounterLastRead;

  /**
   * 
   */
  public Long MemoryPageCounterFrequencyWrite;

  /**
   * 
   */
  public Long MemoryPageCounterLastWrite;

  /**
   * 
   */
  public Long SwapPageCounterFrequencyRead;

  /**
   * 
   */
  public Long SwapPageCounterLastRead;

  /**
   * 
   */
  public Long SwapPageCounterFrequencyWrite;

  /**
   * 
   */
  public Long SwapPageCounterLastWrite;

  /**
   * 
   */
  public Long InterruptCounterFrequency;

  /**
   * 
   */
  public Long InterruptCounterLast;

  /**
   * 
   */
  public Long ContextSwitchCounterFrequency;

  /**
   * 
   */
  public Long ContextSwitchCounterLast;

  /**
   * 
   */
  public String RoleName1;

  /**
   * 
   */
  public String RoleName2;

  /**
   * 
   */
  public String RoleName3;

  /**
   * 
   */
  public String Category;

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
  public ArrayList<GlueBatchJob> glueBatchJobList = new ArrayList<GlueBatchJob>();

  /**
   * 
   */
  public ArrayList<GlueBatchQueue> glueBatchQueueList = new ArrayList<GlueBatchQueue>();

  /**
   * 
   */
  public ArrayList<GlueBatchSystem> glueBatchSystemList = new ArrayList<GlueBatchSystem>();

  /**
   * 
   */
  public ArrayList<GlueHostLocalFileSystem> glueHostLocalFileSystemList = new ArrayList<GlueHostLocalFileSystem>();

  /**
   * 
   */
  public ArrayList<GlueHostNetworkAdapter> glueHostNetworkAdapterList = new ArrayList<GlueHostNetworkAdapter>();

  /**
   * 
   */
  public ArrayList<GlueHostPoolAccount> glueHostPoolAccountList = new ArrayList<GlueHostPoolAccount>();

  /**
   * 
   */
  public ArrayList<GlueHostProcess> glueHostProcessList = new ArrayList<GlueHostProcess>();

  /**
   * 
   */
  public ArrayList<GlueHostRole> glueHostRoleList = new ArrayList<GlueHostRole>();

  /**
   * 
   */
  public ArrayList<GlueVO> glueVOList = new ArrayList<GlueVO>();

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
}
