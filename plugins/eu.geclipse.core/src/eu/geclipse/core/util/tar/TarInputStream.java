/*****************************************************************************
 * Copyright (c) 2007 g-Eclipse Consortium 
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
 *    Ariel Garcia - initial implementation
 *****************************************************************************/

package eu.geclipse.core.util.tar;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * This class provides tar extraction capabilities.
 * It does <b>not</b> extend {@link InputStream}.
 * 
 * @author ariel
 */
public class TarInputStream {

  // The block size used by tar
  private static final int BLOCK_SZ = TarEntry.BLOCK_SZ;
  
  // The block mask used to calculate the remainder
  private static final int BLOCK_MASK = TarEntry.BLOCK_SZ - 1;
  
  
  // The stream to read
  private InputStream tarStream;

  // The current tared entry in the stream
  private TarEntry currentEntry;

  // Flag to indicate if the entry data is still waiting to be read
  private boolean dataPending;
  
  /**
   * Creates a new <code>TarInputStream</code> instance, for reading
   * a tar file.
   * 
   * @param inStream The InputStream to read from
   */
  public TarInputStream( final InputStream inStream ) {
    this.tarStream = inStream;
    this.currentEntry = null;
    this.dataPending = false;
  }

  /**
   * Gets the next entry in the tar archive.
   * 
   * @return A TarEntry object, the next entry in the tar
   * @throws TarArchiveException if some issue was found reading
   * the tar stream
   */
  public TarEntry getNextEntry() throws TarArchiveException {

    TarEntry nextEntry;
    
    // Pending data must be flushed first
    if ( (this.currentEntry != null) && this.dataPending ) {
      copyEntryContents( (OutputStream) null );
    }
    
    try {
      // Read the next block, might be a new entry or a null block
      nextEntry = new TarEntry( readBlock( BLOCK_SZ ) );

      /*
       * Two consecutive null entries mark the end of the tarball.
       * The docs don't tell if a single null entry is allowed,
       * we skip over it and continue.
       */
      if ( nextEntry.isNull() )  {
        nextEntry = new TarEntry( readBlock( BLOCK_SZ ) );
        if ( nextEntry.isNull() ) {
          return null;
        }
      }
    } catch ( IOException ioExc ) {
      throw new TarArchiveException( TarProblems.UNSPECIFIED_IO_PROBLEM,
                                     ioExc );
    }
    
    // Data will follow only if entry's size is not zero
    if ( nextEntry.getSize() > 0 ) {
      this.dataPending = true;
    }

    this.currentEntry = nextEntry;

    return this.currentEntry;
  }

  /**
   * Private method to read a block from the tar stream.
   * 
   * @return The newly read block
   * @throws IOException if reading the stream failed
   */
  private byte[] readBlock( int size ) throws IOException {
    byte[] block = new byte[ size ];

    /*
     * Not all InputStream subclasses implement the available() method,
     * or even return always 1 on available data, like InflaterInputStream,
     * so don't rely on it for knowing how many bytes can be read.
     */
    int off = 0;
    int len = size;
    int n = 0;
    
    do {
      n = this.tarStream.read( block, off, len );
      off += n;
      len -= n;
    } while ( off < size );
    
    return block;
  }

  /**
   * Copy the contents of the entry to the given output stream.
   * 
   * @throws TarArchiveException if some issue was found reading
   * the tar stream
   */
  public void copyEntryContents( final OutputStream outStream )
    throws TarArchiveException
  {
    /*
     * The files are stored in BLOCK_SZ byte blocks.
     * Calculate how many full blocks are needed, and the remainder.
     */
    long size = this.currentEntry.getSize();
    long blocks = size / BLOCK_SZ;
    int remain = (int) size & BLOCK_MASK;
    
    if ( remain != 0 ) {
      blocks++;
    }

    // There are probably better ways for large files...
    byte[] buffer = new byte[ BLOCK_SZ ];
    int oSize = BLOCK_SZ;
    try {
      while ( blocks > 0 ) {
        buffer = readBlock( BLOCK_SZ );
        // We use outStream = null to flush the data
        if ( outStream != null ) {
          // The last block can be partially filled
          if ( (blocks == 1) && (remain != 0) ) {
            oSize = remain;
          }
          outStream.write( buffer, 0, oSize );
        }
        blocks--;
      }
    } catch (IOException ioExc) {
      throw new TarArchiveException( TarProblems.UNSPECIFIED_IO_PROBLEM );
    }
    
    // File data has been read from the tarStream now
    this.dataPending = false;
  }
  
}
