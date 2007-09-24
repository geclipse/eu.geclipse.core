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

import java.lang.Exception;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * An entry in a tar file, either a folder or a file.
 * 
 * @author ariel
 */
public class TarEntry {

  // The block size used by tar
  protected static final int BLOCK_SZ = 512;

  // The tar header parameters
  private static final int OFFSET_NAME = 0;
  private static final int OFFSET_SIZE = 124;
  private static final int OFFSET_CHECKSUM = 148;
  private static final int OFFSET_TYPE = 156;
  private static final int OFFSET_MAGIC = 257;
  private static final int OFFSET_VERSION = 263;
  private static final int OFFSET_PREFIX = 345;
  private static final int LENGTH_NAME = 100;
  private static final int LENGTH_SIZE = 12;
  private static final int LENGTH_CHECKSUM = 8;
  //private static final int LENGTH_TYPE = 1;
  private static final int LENGTH_MAGIC = 6;
  //private static final int LENGTH_VERSION = 2;
  private static final int LENGTH_PREFIX = 155;

  // The markers used for the entry types
  private static final byte ENTRY_TYPE_FILE     = '0';
  private static final byte ENTRY_TYPE_FILE_ALT = '\u0000';
  private static final byte ENTRY_TYPE_HARDLINK = '1';
  private static final byte ENTRY_TYPE_SOFTLINK = '2';
  private static final byte ENTRY_TYPE_CHARDEV  = '3';
  private static final byte ENTRY_TYPE_BLOCKDEV = '4';
  private static final byte ENTRY_TYPE_DIR      = '5';
  private static final byte ENTRY_TYPE_FIFO     = '6';  

  // Other constant values used in the headers
  private static final byte SPACE = ' ';
  private static final String MAGIC = "ustar "; //$NON-NLS-1$  

  // The flags describing the type of entry
  private static final byte ENTRY_FLAG_NULL = '-';
  private static final byte ENTRY_FLAG_FILE = 'f';
  private static final byte ENTRY_FLAG_DIR = 'd';
  
  
  // The file or folder <code>path</code> associated with this entry
  private IPath entryPath;
  
  // The size of the entry, up to 8GBy in a 'ustar' tar
  private long entrySize;
  
  // The type of entry
  private byte entryType;

  /**
   * Creates a new TarEntry from the given metadata block.
   * 
   * @param header A 512 bytes block which is to be parsed for tar metadata
   * @throws TarArchiveException if the header could not be parsed or
   * some unsupported feature was found
   */
  public TarEntry( final byte[] header ) throws TarArchiveException {

    // The metadata block must be BLOCK_SZ bytes
    if ( header.length != BLOCK_SZ ) {
      throw new TarArchiveException( TarProblems.WRONG_HEADER_SIZE );
    }

    // Check if the block is a null entry
    if ( assertNullEntry( header ) ) {
      this.entryType = ENTRY_FLAG_NULL;
      this.entrySize = 0;
      return;
    }
    
    // Verify the header checksum
    verifyChecksum( header );
    
    /*
     * Check the tar type/version.
     * We only support the standard 'ustar' format, where the type field
     * should contain the magic string, followed by a space and a null as version
     */
    String version = readString( header, OFFSET_MAGIC, LENGTH_MAGIC );
    if ( ! version.equals( MAGIC )
        || (header[ OFFSET_VERSION ] != SPACE)
        || (header[ OFFSET_VERSION + 1 ] != 0) ) {
      throw new TarArchiveException( TarProblems.UNSUPPORTED_ENTRY_TYPE );
    }
        
    /*
     * Get the filename: it could be distributed in two fields,
     *      the filename (eventually with full path)
     *      the prefix (eventually null)
     */
    String name = readString( header, OFFSET_NAME, LENGTH_NAME );
    String prefix = readString( header, OFFSET_PREFIX, LENGTH_PREFIX );
    if ( prefix.length() != 0 ) {
      // Check if we need to add a path-separator
      String sep = System.getProperty( "file.separator" ); //$NON-NLS-1$
      if ( ! prefix.endsWith( sep ) ) {
        prefix.concat( sep );
      }
      name = prefix.concat( name );
    }
    this.entryPath = new Path( name );

    /*
     * Get the entry size, stored as octal number in ASCII encoding,
     * terminated by either a null or a space.
     */
    String size = readString( header, OFFSET_SIZE, LENGTH_SIZE - 1 );
    try {
      size = size.replaceAll( "^ +", "" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.entrySize = Long.parseLong( size, 8 );
    } catch ( Exception exc ) {
      throw new TarArchiveException( TarProblems.INVALID_ENTRY_SIZE, exc );
    }
    if ( (header[ OFFSET_SIZE + LENGTH_SIZE - 1 ] != 0)
      && (header[ OFFSET_SIZE + LENGTH_SIZE - 1 ] != SPACE) ) {
        throw new TarArchiveException( TarProblems.INVALID_ENTRY_SIZE ) ;
    }

    // Get the entry type, a single char flag
    switch ( (char) header[ OFFSET_TYPE ] ) {
      case ENTRY_TYPE_FILE:
      case ENTRY_TYPE_FILE_ALT:
        this.entryType = ENTRY_FLAG_FILE;
        break;
      case ENTRY_TYPE_HARDLINK:
      case ENTRY_TYPE_SOFTLINK:
      case ENTRY_TYPE_CHARDEV:
      case ENTRY_TYPE_BLOCKDEV:
      case ENTRY_TYPE_FIFO:
        throw new TarArchiveException( TarProblems.UNSUPPORTED_ENTRY_TYPE );
      case ENTRY_TYPE_DIR:
        this.entryType = ENTRY_FLAG_DIR;
        break;
      default:
        throw new TarArchiveException( TarProblems.INVALID_ENTRY_TYPE );
    }
  }
  

  /*
   * Read a string from a buffer, using the default encoding.
   * Needed because the String( byte[], ... ) constructor does
   * not stop at null bytes, leaving a string of the wrong size.
   */
  private String readString( final byte[] buffer,
                             final int offset,
                             final int length )
  {
    StringBuffer result = new StringBuffer();

    int i = 0;
    while ( (buffer[ offset + i ] != 0) && i < length ) {
      result = result.append( (char) buffer[ offset + i ] );
      i++;
    }
    return result.toString();
  }
  
  /*
   * Verify the header's checksum
   */
  private void verifyChecksum( final byte[] header )
    throws TarArchiveException
  {
    int checksum = 0;
    int storedChecksum;
    
    /*
     * The checksum is calculated by taking the sum of the byte values
     * of the header block with the checksum bytes taken to be ascii spaces.
     */
    for ( int i = 0 ; i < OFFSET_CHECKSUM ; i++ ) {
      checksum += header[i];
    }
    // Add the checksum bytes, counted as spaces
    checksum += LENGTH_CHECKSUM * SPACE;
    for ( int i = OFFSET_CHECKSUM + LENGTH_CHECKSUM ; i < BLOCK_SZ ; i++ ) {
      checksum += header[i];
    }

    /*
     * The checksum is stored as a six digit octal number with leading
     * zeroes or spaces, followed by a null and then a space.
     */
    String s = readString( header, OFFSET_CHECKSUM, LENGTH_CHECKSUM - 2 );
    try {
      s = s.replaceAll( "^ +", "" ); //$NON-NLS-1$ //$NON-NLS-2$
      storedChecksum = Integer.parseInt( s, 8 );
    } catch ( Exception exc ) {
      throw new TarArchiveException( TarProblems.BAD_HEADER_CHECKSUM, exc );
    }
    if ( (header[ OFFSET_CHECKSUM + LENGTH_CHECKSUM - 2 ] != 0)
      || (header[ OFFSET_CHECKSUM + LENGTH_CHECKSUM - 1 ] != SPACE) ) {
      throw new TarArchiveException( TarProblems.BAD_HEADER_CHECKSUM ) ;
    }
    
    if ( checksum != storedChecksum ) {
      throw new TarArchiveException( TarProblems.BAD_HEADER_CHECKSUM );
    }
  }

  /*
   * Check if the block is a null entry.
   */
  private boolean assertNullEntry( final byte[] header ) {
    boolean isNull = true;
    
    /*
     * We loop over the block again, we could reuse the checksum
     * but that complicates the logic which is not an advantage
     * being the block only 512 bytes
     */
    for ( int i : header ) {
      if ( i != 0 ) {
        isNull = false;
        break;
      }
    }
    return isNull;
  }


  /**
   * Asserts whether the entry is null (end of tar marker).
   * 
   * @return <code>true</code> if the entry is null
   */
  public boolean isNull() {
    return this.entryType == ENTRY_FLAG_NULL;
  }

  /**
   * Asserts whether the entry is a directory.
   * 
   * @return <code>true</code> if the entry is a folder
   */
  public boolean isDirectory() {
    return this.entryType == ENTRY_FLAG_DIR;
  }

  /**
   * Get the file or folder <code>path</code> associated with this entry.
   * 
   * @return A Path object corresponding to the entry's filename
   */
  public IPath getPath() {
    return this.entryPath;
  }

  /**
   * Get the size of the entry.
   * 
   * @return The entry's size
   */
  public long getSize() {
    return this.entrySize;
  }

}
