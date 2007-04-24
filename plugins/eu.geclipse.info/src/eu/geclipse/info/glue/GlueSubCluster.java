package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
public class GlueSubCluster extends AbstractGlueTable implements java.io.Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public GlueIndex glueIndex;

  public String getID(){return UniqueID;}

  public void setID(String id){ UniqueID=id;}

  public String UniqueID; //PK
  public String keyName = "UniqueID";
  public String Name;
  public GlueCluster glueCluster; //GlueClusterUniqueID
  public Long RAMSize;
  public Long RAMAvailable;
  public Long VirtualSize;
  public Long VirtualAvailable;
  public String PlatformType;
  public Long SMPSize;
  public String OSName;
  public String OSRelease;
  public String OSVersion;
  public String Vendor;
  public String Model;
  public String Version;
  public Long ClockSpeed;
  public String InstructionSet;
  public String OtherProcessorDescription;
  public Long CacheL1;
  public Long CacheL1I;
  public Long CacheL1D;
  public Long CacheL2;
  public Long BenchmarkSF00;
  public Long BenchmarkSI00;
  public String InboundIP;
  public String OutboundIP;
  public String InformationServiceURL;
  public Long PhysicalCPUs;
  public Long LogicalCPUs;
  public String TmpDir;
  public String WNTmpDir;
  public Date MeasurementDate;
  public Date MeasurementTime;
  public ArrayList<GlueHostRemoteFileSystem> glueHostRemoteFileSystemList = new ArrayList<GlueHostRemoteFileSystem>();
  public ArrayList<GlueSubClusterLocation> glueSubClusterLocationList = new ArrayList<GlueSubClusterLocation>();
  public ArrayList<GlueSubClusterSoftwareRunTimeEnvironment> glueSubClusterSoftwareRunTimeEnvironmentList = new ArrayList<GlueSubClusterSoftwareRunTimeEnvironment>();

}
