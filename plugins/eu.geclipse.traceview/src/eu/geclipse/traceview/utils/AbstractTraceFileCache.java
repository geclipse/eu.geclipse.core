package eu.geclipse.traceview.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

public abstract class AbstractTraceFileCache extends AbstractTrace {
  private static File tmpDir = new File( System.getProperty( "java.io.tmpdir" ) );
  private static String metaDataFilename = "cache.data";
  protected TraceCacheFile[] cacheFiles;
  protected File cacheDir;
  protected File dataFile;
  protected Properties metaData;
  protected int[] cacheFileNr;
  protected int[] cacheIndex;
  protected int[] maxLogClk;
  protected Map<String, Integer> sourceFilenames = new HashMap<String, Integer>();
  private int cacheFileCount;

  abstract public int getEventSize();

  public boolean openCacheDir( final String tracefileName, final long lastModified ) throws IOException {
    boolean cacheLoaded = false;
    this.cacheDir = new File(tmpDir, "tracecache_" + Integer.toHexString( tracefileName.hashCode() ) );
    this.dataFile = new File(cacheDir, metaDataFilename);
    this.cacheFileNr = new int[getNumberOfProcesses()];
    this.cacheIndex = new int[getNumberOfProcesses()];
    this.maxLogClk = new int[getNumberOfProcesses()];
    this.metaData = new Properties();
    if ( cacheDir.exists() && dataFile.exists() ) {
      long modTime = dataFile.lastModified();
      loadCacheMetadata();
      cacheFileCount = Integer.parseInt( ( String )metaData.get( "cachefilecount" ) );
      for(int fileNr = 0; fileNr < cacheFileCount; fileNr++) {
        long cacheModTime = new File(cacheDir, "cachefile_" + fileNr).lastModified();
        if ( cacheModTime < modTime ) modTime = cacheModTime;
      }
      if ( modTime > lastModified ) {
        for(int fileNr = 0; fileNr < cacheFileCount; fileNr++) {
          String mapping = ( String )metaData.get( "file_"+fileNr );
          StringTokenizer tok = new StringTokenizer(mapping, ",");
          int idx = 0;
          while (tok.hasMoreTokens()) {
            String token = tok.nextToken();
            int procNr = Integer.parseInt( token );
            cacheFileNr[procNr] = fileNr;
            cacheIndex[procNr] = idx;
            idx++;
          }
        }
        String filename = "";
        for( int i = 0; filename != null; i++ ) {
          filename = ( String )metaData.get( "sourcefile_"+i );
          if ( filename != null ) sourceFilenames.put( filename, i );
        }
        cacheLoaded = true;
      }
    } else {
      cacheDir.mkdir();
    }
    if ( !cacheLoaded ) {
      createCacheFileMapping();
    }
    openCacheFiles();
    return cacheLoaded;
  }

  protected void createCacheFileMapping() {
    this.cacheFileCount = getNumberOfProcesses();
    this.cacheFileNr = new int[getNumberOfProcesses()];
    this.cacheIndex = new int[getNumberOfProcesses()];
    for (int i = 0; i < cacheFileNr.length; i++) {
      cacheFileNr[i] = i;
    }
  }

  private void openCacheFiles() throws FileNotFoundException {
    cacheFiles = new TraceCacheFile[cacheFileCount];
    for (int fileNr = 0; fileNr < cacheFileCount; fileNr++ ) {
      cacheFiles[fileNr] = new TraceCacheFile(cacheDir, fileNr);
    }
  }
  
  private void loadCacheMetadata() throws IOException {
    metaData = new Properties();
    FileInputStream in = new FileInputStream(dataFile);
    metaData.load( in );
    in.close();
    for (int i = 0; i < getNumberOfProcesses(); i++) {
      String count = ( String )metaData.get( "eventcount_" + i );
      if (count != null) {
        maxLogClk[i] = Integer.parseInt( count );
      }
    }
  }

  protected void saveCacheMetadata() throws IOException {
    try {
    for (int i = 0; i < getNumberOfProcesses(); i++) {
      metaData.put( "eventcount_" + i, Integer.toString( maxLogClk[i] ) );
    }
    metaData.put( "cachefilecount", Integer.toString( cacheFileCount ) );
    for(int fileNr = 0; fileNr < cacheFileCount; fileNr++) {
      StringBuilder mapping = new StringBuilder();
      for( int procNr = 0; procNr < getNumberOfProcesses(); procNr++ ) {
        if (cacheFileNr[procNr] == fileNr) {
          if (mapping.length() != 0) mapping.append( ',' );
          mapping.append( procNr );
        }
      }
      metaData.put( "file_" + fileNr, mapping.toString() );
    }
    FileOutputStream out = new FileOutputStream(dataFile);
    metaData.store( out, "Trace file cache meta data" );
    out.close();
    }catch(Exception e) {
      e.printStackTrace();
    }
  }

  void write( final int processId, final int logicalClock, final int offset, final int value ) {
    int eventOffset = getEventSize() * ( cacheIndex[processId] 
                      + getNumberOfProcesses() * logicalClock ) + offset;
    try {
      cacheFiles[cacheFileNr[processId]].write(eventOffset, value);
    } catch( IOException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  int read( final int processId, final int logicalClock, final int offset ) {
    try {
      int eventOffset = getEventSize() * (cacheIndex[processId]
                                                     + getNumberOfProcesses() * logicalClock ) + offset;
      return cacheFiles[cacheFileNr[processId]].read(eventOffset);
    } catch( IOException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return 0;
  }

  int getMaximumLogicalClock( final int processNr ) {
    return this.maxLogClk[processNr];
  }
  
  void setMaximumLogicalClock( final int processNr, final int value) {
    this.maxLogClk[processNr] = value;
  }

  String getSourceFilenameForIndex( final int index ) {
    return (String)metaData.get( "sourcefile_"+index );
  }

  int addSourceFilename( final String filename ) {
    int index;
    if ( sourceFilenames.containsKey( filename ) ) {
      index = sourceFilenames.get( filename );
    } else {
      index = sourceFilenames.size();
      metaData.put( "sourcefile_"+index, filename );
      sourceFilenames.put( filename, index );
    }
    return index;
  }

  protected void enableMemoryMap() throws IOException {
    for (int fileNr = 0; fileNr < getNumberOfProcesses(); fileNr++ ) {
      cacheFiles[fileNr].enableMemoryMap();
    }
  }
}
