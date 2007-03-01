package eu.geclipse.info.glue;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
public class GlueSubCluster extends AbstractGlueTable implements java.io.Serializable{
  public GlueIndex glueIndex;

  public String getID(){return UniqueID;}

  public void setID(String id){ UniqueID=id;}

  public String UniqueID; //PK
  public String keyName = "UniqueID";
  public String Name;
  public GlueCluster glueCluster; //GlueClusterUniqueID
  public Integer RAMSize;
  public Integer RAMAvailable;
  public Integer VirtualSize;
  public Integer VirtualAvailable;
  public String PlatformType;
  public Integer SMPSize;
  public String OSName;
  public String OSRelease;
  public String OSVersion;
  public String Vendor;
  public String Model;
  public String Version;
  public Integer ClockSpeed;
  public String InstructionSet;
  public String OtherProcessorDescription;
  public Integer CacheL1;
  public Integer CacheL1I;
  public Integer CacheL1D;
  public Integer CacheL2;
  public Integer BenchmarkSF00;
  public Integer BenchmarkSI00;
  public String InboundIP;
  public String OutboundIP;
  public String InformationServiceURL;
  public Integer PhysicalCPUs;
  public Integer LogicalCPUs;
  public String TmpDir;
  public String WNTmpDir;
  public Date MeasurementDate;
  public Date MeasurementTime;
  public ArrayList<GlueHostRemoteFileSystem> glueHostRemoteFileSystemList = new ArrayList<GlueHostRemoteFileSystem>();
  public ArrayList<GlueSubClusterLocation> glueSubClusterLocationList = new ArrayList<GlueSubClusterLocation>();
  public ArrayList<GlueSubClusterSoftwareRunTimeEnvironment> glueSubClusterSoftwareRunTimeEnvironmentList = new ArrayList<GlueSubClusterSoftwareRunTimeEnvironment>();

}
