package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
public class GlueHost extends AbstractGlueTable implements java.io.Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public GlueIndex glueIndex;

  public String getID(){return UniqueID;}

  public void setID(String id){ UniqueID=id;}

  public String Name;
  public String UniqueID; //PK
  public String keyName = "UniqueID";
  public Long UpTime;
  public Long FileCounterRegularOpen;
  public Long ProcessCounterTotal;
  public Long ProcessCounterUninterruptibleSleep;
  public Long ProcessCounterRunnable;
  public Long ProcessCounterSleeping;
  public Long ProcessCounterStopped;
  public Long ProcessCounterZombie;
  public Long ArchitectureSMPSize;
  public Long ArchitectureSMTSize;
  public String ProcessorVendor;
  public String ProcessorModel;
  public Long ProcessorClockSpeed;
  public Long ProcessorLoadLast1Min;
  public Long ProcessorLoadLast5Min;
  public Long ProcessorLoadLast15Min;
  public Long ProcessorLoadUser;
  public Long ProcessorLoadNice;
  public Long ProcessorLoadSystem;
  public Long ProcessorLoadIdle;
  public Long BenchmarkBogoMIPS;
  public Long SocketCounterTotal;
  public Long SocketCounterTCP;
  public Long SocketCounterUDP;
  public Long SocketCounterRAW;
  public String OperatingSystemName;
  public String OperatingSystemRelease;
  public String OperatingSystemVersion;
  public Long MainMemoryRAMSize;
  public Long MainMemoryRAMUsed;
  public Long MainMemoryRAMAvailable;
  public Long MainMemoryRAMShared;
  public Long MainMemoryRAMBuffer;
  public Long MainMemoryRAMCached;
  public Long MainMemoryVirtualSize;
  public Long MainMemoryVirtualAvailable;
  public Long MainMemoryVirtualUsed;
  public Long MemoryPageCounterFrequencyRead;
  public Long MemoryPageCounterLastRead;
  public Long MemoryPageCounterFrequencyWrite;
  public Long MemoryPageCounterLastWrite;
  public Long SwapPageCounterFrequencyRead;
  public Long SwapPageCounterLastRead;
  public Long SwapPageCounterFrequencyWrite;
  public Long SwapPageCounterLastWrite;
  public Long InterruptCounterFrequency;
  public Long InterruptCounterLast;
  public Long ContextSwitchCounterFrequency;
  public Long ContextSwitchCounterLast;
  public String RoleName1;
  public String RoleName2;
  public String RoleName3;
  public String Category;
  public Date MeasurementDate;
  public Date MeasurementTime;
  public ArrayList<GlueBatchJob> glueBatchJobList = new ArrayList<GlueBatchJob>();
  public ArrayList<GlueBatchQueue> glueBatchQueueList = new ArrayList<GlueBatchQueue>();
  public ArrayList<GlueBatchSystem> glueBatchSystemList = new ArrayList<GlueBatchSystem>();
  public ArrayList<GlueHostLocalFileSystem> glueHostLocalFileSystemList = new ArrayList<GlueHostLocalFileSystem>();
  public ArrayList<GlueHostNetworkAdapter> glueHostNetworkAdapterList = new ArrayList<GlueHostNetworkAdapter>();
  public ArrayList<GlueHostPoolAccount> glueHostPoolAccountList = new ArrayList<GlueHostPoolAccount>();
  public ArrayList<GlueHostProcess> glueHostProcessList = new ArrayList<GlueHostProcess>();
  public ArrayList<GlueHostRole> glueHostRoleList = new ArrayList<GlueHostRole>();
  public ArrayList<GlueVO> glueVOList = new ArrayList<GlueVO>();

}
