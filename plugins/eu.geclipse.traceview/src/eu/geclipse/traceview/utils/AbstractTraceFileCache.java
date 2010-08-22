/*****************************************************************************
 * Copyright (c) 2009 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Thomas Koeckerbauer MNM-Team, LMU Munich - initial API and implementation
 *****************************************************************************/

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
import java.util.Vector;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;

import eu.geclipse.traceview.internal.Activator;
import eu.geclipse.traceview.preferences.PreferenceConstants;

final class OffsetEntry {
  final int id;
  final int[] buffer;
  final int elementCount;
  final int intOffset;
  final int mask;
  final int shift;
  final int fillPosShift;
  final int mask2;
  final int bits;
  final boolean signed;
  final boolean needsRead;
  final static int[] signedTable = new int[32];

  static {
    for (int i = 0; i < 32; i++) {
      signedTable[i] = makeMask( i + 1 , 31 - i );
    }
  }  
  
  public OffsetEntry( final int id, final int bits, final boolean signed, final int[] intOffset, final int[] shift ) {
    this( id, 1, bits, signed, intOffset, shift);
  }  

  public OffsetEntry( final int id, final int elementCount, final int bits, final boolean signed, final int[] intOffset, final int[] shift ) {
    if (shift[0] == 32) {
      shift[0] = 0;
      intOffset[0]++;
    }
    this.id = id;
    this.bits = bits;
    this.signed = signed;
    this.intOffset = intOffset[0];
    this.fillPosShift = shift[0];
    this.elementCount = elementCount;
    this.buffer = new int[((shift[0] + this.bits*elementCount) / 32)+1];
    if ((shift[0] + this.bits)>=32) {
      int bits1 = 32 - shift[0];
      int bits2 = bits - bits1;
      this.mask = makeMask( shift[0], bits1 );
      this.needsRead = true;
      this.shift = shift[0] - bits2;
      this.mask2 = makeMask( 0, bits2 );
    } else {
      this.shift = shift[0];
      this.mask = makeMask( this.shift, bits );
      this.needsRead = this.mask != 0xffffffff;
      this.mask2 = 0; // not used
    }
    intOffset[0] += this.buffer.length;
    shift[0] = (shift[0] + this.bits*elementCount) % 32;
  }

  static int makeMask( final int shift, final int bits ) {
    int mask = 0;
    for (int i = 0; i < bits; i++) mask |= 1 << (shift+i);
    return mask;
  }

  void checkRange( final int value ) {
    if (signed) {
      int max = makeMask( 0, bits-1 );
      int min = 0-max-1;
      if (value > max || value < min) 
        System.out.println( "Value out of range (id: " + id + ", value: " + value + ", min: " + min + ", max: " + max + ")" );
    } else {
      long max = makeMask( 0, bits );
      max &= 0x00000000ffffffffl;
      if (value > max) 
        System.out.println( "Value out of range (id: " + id + ", value: " + value + ", max: " + max + ")" );
    }    
  }

  void write( final int value ) {
//    checkRange( value ); // for debugging only
    buffer[0] &= ~mask;
    buffer[0] |= (value << shift) & mask;
    if (mask2 != 0) {
      buffer[1] &= ~mask2;
      buffer[1] |= value & mask2;
    }
  }

  void writeArray( final int[] value ) {
    int j=0;
    int m1 = mask;
    int m2 = mask2;
    int sh = shift;
    int fillPosSh = fillPosShift;
    for (int i = 0; i < elementCount; i++) {
      buffer[j] &= ~m1;
      buffer[j] |= (value[i] << sh) & m1;
      if (m2 != 0) {
        j++;
        buffer[j] &= ~m2;
        buffer[j] |= value[i] & m2;
      }
      long lm1 = m1 & 0x00000000ffffffffl;
      lm1 <<= bits;
      long lm2 = m2 & 0x00000000ffffffffl;
      lm2 <<= bits;
      if ((0x00000000ffffffffl & lm1) != 0l) {
        m1 = (int)(0x00000000ffffffffl & lm1);
        m2 = (int)(0x00000000ffffffffl & (lm1>>>32 | lm2));
      } else {
        m1 = (int)(0x00000000ffffffffl & (lm1>>>32 | lm2));
        m2 = (int)(0x00000000ffffffffl & (lm2>>>32));
      }
      fillPosSh += bits;
      if (fillPosSh >= 32) fillPosSh -= 32;
      if (m2 != 0) {
        sh = 32 - bits;
      } else {
        sh = fillPosSh;
      }
    }
  }

  int read() {
    int data = (buffer[0] & mask) >>> shift;
    if (mask2 != 0) {
      data |= buffer[1] & mask2;
    }
    if (signed && 0 != ( data & (1<<(bits-1)))) {
      data |= OffsetEntry.signedTable[bits-1];
    }
    return data;
  }

  void readArray( final int[] data ) {
    int j = 0;
    int m1 = mask;
    int m2 = mask2;
    int sh = shift;
    int fillPosSh = fillPosShift;
    for (int i = 0; i < elementCount; i++) {
      data[i] = (buffer[j] & m1) >>> sh;
      if (m2 != 0) {
        j++;
        data[i] |= buffer[j] & m2;
      }
      long lm1 = m1 & 0x00000000ffffffffl;
      lm1 <<= bits;
      long lm2 = m2 & 0x00000000ffffffffl;
      lm2 <<= bits;
      if ((0x00000000ffffffffl & lm1) != 0l) {
        m1 = (int)(0x00000000ffffffffl & lm1);
        m2 = (int)(0x00000000ffffffffl & (lm1>>>32 | lm2));
      } else {
        m1 = (int)(0x00000000ffffffffl & (lm1>>>32 | lm2));
        m2 = (int)(0x00000000ffffffffl & (lm2>>>32));
      }

      if (signed && 0 != ( data[i] & (1<<(bits-1)))) {
        data[i] |= OffsetEntry.signedTable[bits-1];
      }

      fillPosSh += bits;
      if (fillPosSh >= 32) fillPosSh -= 32;
      if (m2 != 0) {
        sh = 32 - bits;
      } else {
        sh = fillPosSh;
      }
    }
  }
}

public abstract class AbstractTraceFileCache extends AbstractTrace {
  private static String metaDataFilename = "cache.data"; //$NON-NLS-1$
  protected TraceCacheFile[] cacheFiles;
  protected int[] procsInFile;
  protected File cacheDir;
  protected File dataFile;
  protected Properties metaData;
  protected int[] cacheFileNr;
  protected int[] cacheIndex;
  protected int[] maxLogClk;
  protected final Map<String, Integer> sourceFilenames = new HashMap<String, Integer>();
  private int cacheFileCount;
  private final Vector<OffsetEntry> entries = new Vector<OffsetEntry>();
  private final int[] intOffset = new int[1];
  private final int[] shift = new int[1];
  private String tracefileName;

  abstract public int estimateMaxLogicalClock();

  final public int getBitsForMaxValue(int value) {
    int bits = 32;
    while( ( (1<<(bits-1)) & value ) == 0 || bits == 1 ) {
      bits--;
    }
    return bits;
  }
  
  final public int getEventSize() {
    return (shift[0] == 0) ? intOffset[0] : intOffset[0] + 1;
  }
  
  final public void addEntry( int id, int bits, boolean signed ) {
    if (bits > 32) bits = 32;
    if (id >= entries.size()) entries.setSize( id+1 );
    OffsetEntry entry = new OffsetEntry(id, bits, signed, intOffset, shift);
    entries.set( id, entry );
  }  

  final public void addEntry( int id, int elementCount, int bits, boolean signed ) {
    if (bits > 32) bits = 32;
    if (id >= entries.size()) entries.setSize( id+1 );
    OffsetEntry entry = new OffsetEntry(id, elementCount, bits, signed, intOffset, shift);
    entries.set( id, entry );
  }  

  final public boolean openCacheDir( final String tracefileName, final String traceOptions, final long lastModified ) throws IOException {
    boolean cacheLoaded = false;
    this.tracefileName = tracefileName;
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    File tmpDir = new File( store.getString( PreferenceConstants.P_CACHE_DIR ) );
    this.cacheDir = new File(tmpDir, "tracecache_" + Integer.toHexString( traceOptions.hashCode() + tracefileName.hashCode() ) );
    this.dataFile = new File(cacheDir, metaDataFilename);
    this.cacheFileNr = new int[getNumberOfProcesses()];
    this.cacheIndex = new int[getNumberOfProcesses()];
    this.maxLogClk = new int[getNumberOfProcesses()];
    this.metaData = new Properties();
    if ( cacheDir.exists() && dataFile.exists() ) {
      long modTime = dataFile.lastModified();
      loadCacheMetadata();
      cacheFileCount = Integer.parseInt( ( String )metaData.get( "cachefilecount" ) );
      this.procsInFile = new int[cacheFileCount];
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
            procsInFile[fileNr] = idx;
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

  final protected void createCacheFileMapping() {
    Vector<Integer> factors = factorize( getNumberOfProcesses() );
    this.cacheFileCount = 1;
    for (int i = factors.size()-1; i>=0; i--) {
      int factor = factors.get( i ).intValue();
      if (this.cacheFileCount * factor <= 32) this.cacheFileCount *= factor;
    }
    int procsPerFile = getNumberOfProcesses() / this.cacheFileCount;
    this.procsInFile = new int[this.cacheFileCount];
    for (int fileNr = 0; fileNr < this.cacheFileCount; fileNr++ ) {
      this.procsInFile[fileNr] = procsPerFile;
    }
    this.cacheFileNr = new int[getNumberOfProcesses()];
    this.cacheIndex = new int[getNumberOfProcesses()];
    for (int i = 0; i < getNumberOfProcesses(); i++) {
      this.cacheFileNr[i] = i / procsPerFile;
      this.cacheIndex[i] = i % procsPerFile;
    }
  }

  final private Vector<Integer> factorize(int x) {
    Vector<Integer> resultVector = new Vector<Integer>();
    int d = 2;
    do {
      if (x % d != 0) d++;
      else {
        resultVector.add( Integer.valueOf(d) );
        x /= d;
        d = 2;
      }
    } while (x != 1);
    return resultVector;
  }

  final private void openCacheFiles() throws FileNotFoundException {
    cacheFiles = new TraceCacheFile[cacheFileCount];
    for (int fileNr = 0; fileNr < cacheFileCount; fileNr++ ) {
      cacheFiles[fileNr] = new TraceCacheFile(cacheDir, fileNr);
    }
  }
  
  final private void loadCacheMetadata() throws IOException {
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

  final protected void saveCacheMetadata() throws IOException {
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
    metaData.store( out, "Trace file cache meta data (" + tracefileName + ')' );
    out.close();
    }catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  final void write( final int processId, final int logicalClock, final int id, final int value ) {
    try {
      TraceCacheFile file = cacheFiles[cacheFileNr[processId]];
      OffsetEntry off = entries.get( id );
      int offset = calcEventOffset( processId, logicalClock, off.intOffset );
      synchronized( off ) {
        if (off.needsRead) {
          file.read( offset, off.buffer );
        }
        off.write( value );
        file.write( offset, off.buffer );
      }
    } catch( IOException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  final void writeArray( final int processId, final int logicalClock, final int id, final int[] value ) {
    try {
      TraceCacheFile file = cacheFiles[cacheFileNr[processId]];
      OffsetEntry off = entries.get( id );
      int offset = calcEventOffset( processId, logicalClock, off.intOffset );
      synchronized( off ) {
        if (off.needsRead) {
          file.read( offset, off.buffer );
        }
        off.writeArray( value );
        file.write( offset, off.buffer );
      }
    } catch( IOException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  final int read( final int processId, final int logicalClock, final int id ) {
    try {
      TraceCacheFile file = cacheFiles[cacheFileNr[processId]];
      OffsetEntry off = entries.get( id );
      int offset = calcEventOffset( processId, logicalClock, off.intOffset );
      synchronized( off ) {
        file.read( offset, off.buffer );
        return off.read();        
      }
    } catch( IOException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return 0;
  }

  final void readArray( final int processId, final int logicalClock, final int id, final int[] data ) {
    try {
      TraceCacheFile file = cacheFiles[cacheFileNr[processId]];
      OffsetEntry off = entries.get( id );
      int offset = calcEventOffset( processId, logicalClock, off.intOffset );
      synchronized( off ) {
        file.read( offset, off.buffer );
        off.readArray( data );
      }
    } catch( IOException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private final int calcEventOffset( final int processId, final int logicalClock, final int offset ) {
    return getEventSize() * (this.cacheIndex[processId] + procsInFile[cacheFileNr[processId]] * logicalClock ) + offset;
  }
  
  final int getMaximumLogicalClock( final int processNr ) {
    return this.maxLogClk[processNr];
  }
  
  final void setMaximumLogicalClock( final int processNr, final int value) {
    this.maxLogClk[processNr] = value;
  }

  final String getSourceFilenameForIndex( final int index ) {
    return (String)metaData.get( "sourcefile_"+index );
  }

  final int addSourceFilename( final String filename ) {
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

  protected final void enableMemoryMap() {
    try {
      for (int fileNr = 0; fileNr < this.cacheFileCount; fileNr++ ) {
          cacheFiles[fileNr].enableMemoryMap();
      }
    } catch( IOException e ) {
      Activator.logStatus( new Status( IStatus.WARNING, Activator.PLUGIN_ID, "Could not create memory map for all trace cache files, continuing without" ) );
    }
  }
}
