package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueHost extends AbstractGlueTable implements java.io.Serializable{
  public GlueIndex glueIndex;

  public String getID(){return UniqueID;}

  public void setID(String id){ UniqueID=id;}

  public String Name;
  public String UniqueID; //PK
  public String keyName = "UniqueID";
  public Integer UpTime;
  public Integer FileCounterRegularOpen;
  public Integer ProcessCounterTotal;
  public Integer ProcessCounterUninterruptibleSleep;
  public Integer ProcessCounterRunnable;
  public Integer ProcessCounterSleeping;
  public Integer ProcessCounterStopped;
  public Integer ProcessCounterZombie;
  public Integer ArchitectureSMPSize;
  public Integer ArchitectureSMTSize;
  public String ProcessorVendor;
  public String ProcessorModel;
  public Integer ProcessorClockSpeed;
  public Integer ProcessorLoadLast1Min;
  public Integer ProcessorLoadLast5Min;
  public Integer ProcessorLoadLast15Min;
  public Integer ProcessorLoadUser;
  public Integer ProcessorLoadNice;
  public Integer ProcessorLoadSystem;
  public Integer ProcessorLoadIdle;
  public Integer BenchmarkBogoMIPS;
  public Integer SocketCounterTotal;
  public Integer SocketCounterTCP;
  public Integer SocketCounterUDP;
  public Integer SocketCounterRAW;
  public String OperatingSystemName;
  public String OperatingSystemRelease;
  public String OperatingSystemVersion;
  public Integer MainMemoryRAMSize;
  public Integer MainMemoryRAMUsed;
  public Integer MainMemoryRAMAvailable;
  public Integer MainMemoryRAMShared;
  public Integer MainMemoryRAMBuffer;
  public Integer MainMemoryRAMCached;
  public Integer MainMemoryVirtualSize;
  public Integer MainMemoryVirtualAvailable;
  public Integer MainMemoryVirtualUsed;
  public Integer MemoryPageCounterFrequencyRead;
  public Integer MemoryPageCounterLastRead;
  public Integer MemoryPageCounterFrequencyWrite;
  public Integer MemoryPageCounterLastWrite;
  public Integer SwapPageCounterFrequencyRead;
  public Integer SwapPageCounterLastRead;
  public Integer SwapPageCounterFrequencyWrite;
  public Integer SwapPageCounterLastWrite;
  public Integer InterruptCounterFrequency;
  public Integer InterruptCounterLast;
  public Integer ContextSwitchCounterFrequency;
  public Integer ContextSwitchCounterLast;
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
