/*****************************************************************************
 * Copyright (c) 2007, 2008 g-Eclipse Consortium 
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
 *                 - updated to new problem reporting
 *****************************************************************************/

package eu.geclipse.core.util.tar;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.reporting.ProblemException;

/**
 * This class provides tar extraction capabilities.
 * It does <b>not</b> extend {@link InputStream}.
 * 
 * @author agarcia
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
   * @throws ProblemException if some issue was found reading the tar stream
   */
  public TarEntry getNextEntry() throws ProblemException {

    TarEntry nextEntry;
    
    // Pending data must be flushed first
    if ( ( this.currentEntry != null ) && this.dataPending ) {
      copyEntryContents( (OutputStream) null );
    }
    
    try {
      byte[] block = new byte[ BLOCK_SZ ];
      
      // Read the next block, might be a new entry or a null block
      readBlock( block, BLOCK_SZ );
      nextEntry = new TarEntry( block );

      /*
       * Two consecutive null entries mark the end of the tarball.
       * The docs don't tell if a single null entry is allowed,
       * we skip over it and continue.
       */
      if ( nextEntry.isNull() )  {
        readBlock( block, BLOCK_SZ );
        nextEntry = new TarEntry( block );
      }
    } catch ( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.IO_UNSPECIFIED_PROBLEM,
                                  ioExc,
                                  Activator.PLUGIN_ID );
    }
    
    if ( ! nextEntry.isNull() ) {
    
      // Data will follow only if entry's size is not zero
      if ( nextEntry.getSize() > 0 ) {
        this.dataPending = true;
      }
  
      this.currentEntry = nextEntry;
    }
    
    // Two consecutive null entries mark the end of the tarball
    return nextEntry.isNull() ? null : this.currentEntry;
  }

  /**
   * Private method to read a block from the tar stream.
   * 
   * @param block A byte array where the read data shall be stored.
   * @param size The amount of bytes to read from the stream. Must
   *             not exceed the size of <code>block</code> or an
   *             IndexOutOfBoundsException will be thrown.
   * @throws IOException if reading the stream failed
   * @throws ProblemException if EOF was reached while reading a TAR entry
   */
  private void readBlock( final byte[] block, final int size )
      throws IOException, ProblemException {
    
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
      if ( n == -1 ) {
        throw new ProblemException( ICoreProblems.IO_CORRUPTED_FILE,
                                    Messages.getString( "TarInputStream.EOF_reading_tar" ), //$NON-NLS-1$
                                    Activator.PLUGIN_ID );
      }
      off += n;
      len -= n;
    } while ( off < size );
    
  }

  /**
   * Copy the contents of the entry to the given output stream.
   * 
   * @throws ProblemException if some issue was found reading the tar stream
   */
  public void copyEntryContents( final OutputStream outStream )
    throws ProblemException
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
        readBlock( buffer, BLOCK_SZ );
        // We use outStream = null to flush the data
        if ( outStream != null ) {
          // The last block can be partially filled
          if ( ( blocks == 1 ) && ( remain != 0 ) ) {
            oSize = remain;
          }
          outStream.write( buffer, 0, oSize );
        }
        blocks--;
      }
    } catch ( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.IO_UNSPECIFIED_PROBLEM,
                                  ioExc,
                                  Activator.PLUGIN_ID );
    }
    
    // File data has been read from the tarStream now
    this.dataPending = false;
  }
  
}
