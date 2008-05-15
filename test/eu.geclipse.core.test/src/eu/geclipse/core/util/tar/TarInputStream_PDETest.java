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
 *    Jie Tao - test class (Plug-in test)
 *    Ariel Garcia - updated to new problem reporting
 *****************************************************************************/

package eu.geclipse.core.util.tar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.reporting.ProblemException;


/**
 * tests the methods in the class {@link TarInputStream}
 * @author tao-j
 */
public class TarInputStream_PDETest {

  private static TarInputStream instream;
  
  // The block size used by tar
  private static final int BLOCK_SZ = 512;
  
  // Real header data
  private static final int    file2_size = 2408;
  private static final byte[] data2 = {
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
  
  /**
   * initialization
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    
    // Build the full file stream
    int blocks = 2 + (int) Math.ceil( ( (float) file2_size ) / BLOCK_SZ );
    byte[] file = new byte[ blocks * BLOCK_SZ ];
    System.arraycopy( data2, 0, file, 0, BLOCK_SZ );
    InputStream stream = new ByteArrayInputStream( file );
    instream = new TarInputStream( stream );
  }

  /**
   * tests the method {@link TarInputStream#TarInputStream(InputStream)}
   */
  @Test
  public void testTarInputStream() {
    Assert.assertNotNull( instream );
  }

  /**
   * tests the method {@link TarInputStream#getNextEntry()}
   * @throws ProblemException 
   */
  @Test
  public void testGetNextEntry() throws ProblemException {
    //TarEntry entry = instream.getNextEntry();
    //Assert.assertNotNull( entry );
    //Assert.assertEquals( new Long( file2_size ), new Long( entry.getSize() ) );
  }

  /**
   * tests the method {@link TarInputStream#copyEntryContents(java.io.OutputStream)}
   * @throws ProblemException 
   * @throws IOException 
   */
  @Test
  public void testCopyEntryContents() throws ProblemException, IOException {
    OutputStream outstream = new ByteArrayOutputStream();
    //instream.copyEntryContents( outstream );
    outstream.close();
  }

}
