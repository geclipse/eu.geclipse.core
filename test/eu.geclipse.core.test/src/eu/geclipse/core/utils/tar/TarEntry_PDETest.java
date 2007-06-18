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

package eu.geclipse.core.utils.tar;

import org.junit.Test;
import static org.junit.Assert.*;
import junit.framework.AssertionFailedError;
import java.util.Random;
import org.eclipse.core.runtime.Path;
import eu.geclipse.core.util.tar.TarArchiveException;
import eu.geclipse.core.util.tar.TarEntry;

/**
 * Testclass for the {@link TarEntry} class
 * 
 * @author ariel
 */
public class TarEntry_PDETest {

  // The block size used by tar
  private static final int BLOCK_SZ = 512;

  // An empty header
  private static final byte[] header_null = new byte[ BLOCK_SZ ];

  // A file header built by gnu tar
  private static final String header_file_name = "modules/btsco/Makefile"; //$NON-NLS-1$
  private static final long   header_file_size = 372;
  private static final byte[] header_file = {
    109,111,100,117,108,101,115,47,98,116,115,99,111,47,77,97,
      107,101,102,105,108,101,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,48,48,48,48,54,52,52,0,48,48,48,48,
      48,48,48,0,48,48,48,48,48,48,48,0,48,48,48,48,
    48,48,48,48,53,54,52,0,49,48,52,55,53,51,54,51,
      49,50,54,0,48,49,52,48,49,50,0,32,48,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,117,115,116,97,114,32,32,0,114,111,111,116,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,114,111,111,116,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0    
  };

  // A file header with different storing of the octal numbers
  private static final String header_file2_name = "vmmon-only/linux/driver.h"; //$NON-NLS-1$
  private static final long   header_file2_size = 2408;
  private static final byte[] header_file2 = {
    118,109,109,111,110,45,111,110,108,121,47,108,105,110,117,120,
      47,100,114,105,118,101,114,46,104,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,49,48,48,54,52,52,32,0,32,32,32,32,
      32,48,32,0,32,32,32,32,32,48,32,0,32,32,32,32,
    32,32,32,52,53,53,48,32,49,48,51,53,48,52,53,50,
      53,51,51,32,32,49,52,48,55,55,0,32,48,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,117,115,116,97,114,32,32,0,114,111,111,116,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,114,111,111,116,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
  };

  // A folder header built by gnu tar
  private static final String header_dir_name = "modules/btsco/debian/"; //$NON-NLS-1$
  private static final long   header_dir_size = 0;
  private static final byte[] header_dir = {
    109,111,100,117,108,101,115,47,98,116,115,99,111,47,100,101,
      98,105,97,110,47,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,48,48,48,48,55,53,53,0,48,48,48,48,
      48,48,48,0,48,48,48,48,48,48,48,0,48,48,48,48,
    48,48,48,48,48,48,48,0,49,48,52,55,53,51,54,51,
      49,50,54,0,48,49,51,53,54,55,0,32,53,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,117,115,116,97,114,32,32,0,114,111,111,116,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,114,111,111,116,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
  };

  // A folder header with different storing of the octal numbers
  private static final String header_dir2_name = "vmmon-only/"; //$NON-NLS-1$
  private static final long   header_dir2_size = 0;
  private static final byte[] header_dir2 = {
    118,109,109,111,110,45,111,110,108,121,47,0,0,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,32,52,48,55,53,53,32,0,32,32,32,32,
      32,48,32,0,32,32,32,32,32,48,32,0,32,32,32,32,
    32,32,32,32,32,32,48,32,49,48,51,53,48,52,53,50,
      53,51,51,32,32,49,49,49,55,51,0,32,53,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,117,115,116,97,114,32,32,0,114,111,111,116,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,114,111,111,116,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
  };


  /**
   * Method for testing the class constructor
   * {@link eu.geclipse.core.util.tar.TarEntry#TarEntry()} against
   * invalid or corrupt headers.
   */
  @Test
  public void testTarEntry_invalidHeaders()
  {
    /*
     * Test for an exception if the header size is wrong
     */
    try {
      new TarEntry( new byte[ BLOCK_SZ + 1 ] );
      throw new AssertionFailedError();
    } catch ( TarArchiveException tExc ) {
      // The test was ok, we expect an exception here
    }

    /*
     * Test for an exception if the header checksum fails
     */
    Random rand = new Random();
    int pos = rand.nextInt( BLOCK_SZ );
    byte[] corruptedHeader = new byte[ BLOCK_SZ ];
    System.arraycopy( header_file, 0, corruptedHeader, 0, BLOCK_SZ );
    // XOR the byte with some arbitrary value
    corruptedHeader[ pos ] = (byte) (corruptedHeader[ pos ] ^ 1);
    try {
      new TarEntry( corruptedHeader );
      throw new AssertionFailedError();
    } catch ( TarArchiveException tExc ) {
      // The test was ok, we expect an exception here
    }
  }

  /**
   * Method for testing the class constructor
   * {@link eu.geclipse.core.util.tar.TarEntry#TarEntry()} against
   * valid headers.
   */
  @Test
  public void testTarEntry_validHeaders()
  {
    TarEntry entry;
    
    // Test with good headers, all should succeed
    try {
      // Test building a null entry
      new TarEntry( header_null );

      // Test building a file entry
      entry = new TarEntry( header_file );
      assertTrue( header_file_size == entry.getSize() );
      assertEquals( new Path( header_file_name ), entry.getPath() );
      
      // Test building a different file entry
      entry = new TarEntry( header_file2 );
      assertTrue( header_file2_size == entry.getSize() );
      assertEquals( new Path( header_file2_name ), entry.getPath() );

      // Test building a folder entry
      entry = new TarEntry( header_dir );
      assertTrue( header_dir_size == entry.getSize() );
      assertEquals( new Path( header_dir_name ), entry.getPath() );
      
      // Test building a different folder entry
      entry = new TarEntry( header_dir2 );
      assertTrue( header_dir2_size == entry.getSize() );
      assertEquals( new Path( header_dir2_name ), entry.getPath() );

    } catch ( TarArchiveException tExc ) {
      throw new AssertionFailedError();
    }
        
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.util.tar.TarEntry#isNull()}.
   */
  @Test
  public void testIsNull()
  {
    TarEntry entry;
    
    try {
      // Test with a null header
      entry = new TarEntry( header_null );
      assertTrue( entry.isNull() );
      
      // Test with a non-null header
      entry = new TarEntry( header_file );
      assertFalse( entry.isNull() );
      
    } catch ( TarArchiveException tExc ) {
      throw new AssertionFailedError();
    }
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.util.tar.TarEntry#isDirectory()}.
   */
  @Test
  public void testIsDirectory()
  {
    TarEntry entry;
    
    try {
      // Test with a file header
      entry = new TarEntry( header_file );
      assertFalse( entry.isDirectory() );

      // Test with a file header
      entry = new TarEntry( header_dir );
      assertTrue( entry.isDirectory() );
      
    } catch ( TarArchiveException tExc ) {
      throw new AssertionFailedError();
    }
  }

}
