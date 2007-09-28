/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.gvid.uncompressed;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.junit.Before;
import org.junit.Test;
import eu.geclipse.gvid.codec.uncompressed.UncompressedDecoder;
import eu.geclipse.gvid.internal.Connection;
import eu.geclipse.gvid.internal.Events;

/**
 * Test for the <code>eu.geclipse.gvid.codec.uncompressed.UncompressedDecoder</code>
 * class.
 */
public class UncompressedDecoder_Test {
  private UncompressedDecoder decoder;
  private PipedInputStream inIn;
  private PipedOutputStream inOut;
  private PipedInputStream outIn;
  private PipedOutputStream outOut;
  private Connection connection;
  private Events events;
  
  /**
   * Initialisation of the decoder.
   * @throws Exception IOException
   */
  @Before
  public void setUp() throws Exception {
    byte[] miniImage = {
      0x00, 0x00, 0x00, 0x02,
      0x00, 0x00, 0x00, 0x03,
      0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
      0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
      0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, };
    this.inIn = new PipedInputStream();
    this.inOut = new PipedOutputStream();
    this.inIn.connect( this.inOut );
    this.outIn = new PipedInputStream();
    this.outOut = new PipedOutputStream();
    this.outIn.connect( this.outOut );
    this.connection = new Connection( this.inIn, this.outOut );
    this.events = new Events( this.connection );
    this.decoder = new UncompressedDecoder();
    this.decoder.init( this.connection , this.events );
    this.inOut.write( miniImage );
  }
  
  /**
   * Test method for
   * {@link eu.geclipse.gvid.codec.uncompressed.UncompressedDecoder#decodeNextFrame()}.
   * @throws Exception IOException
   */
  @Test
  public void testDecodeNextFrame() throws Exception {
    assertTrue( this.decoder.decodeNextFrame() );
    assertFalse( this.decoder.decodeNextFrame() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.gvid.codec.uncompressed.UncompressedDecoder#getImage()}.
   * @throws Exception IOException
   */
  @Test
  public void testGetImage() throws Exception {
    assertNotNull( this.decoder.getImage() );
    this.decoder.decodeNextFrame();
    assertNotNull( this.decoder.getImage() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.gvid.codec.uncompressed.UncompressedDecoder#getXSize()}.
   * @throws Exception IOException
   */
  @Test
  public void testGetXSize() throws Exception {
    this.decoder.decodeNextFrame();
    assertTrue( this.decoder.getXSize() == 2 );
  }

  /**
   * Test method for
   * {@link eu.geclipse.gvid.codec.uncompressed.UncompressedDecoder#getYSize()}.
   * @throws Exception IOException
   */
  @Test
  public void testGetYSize() throws Exception {
    this.decoder.decodeNextFrame();
    assertTrue( this.decoder.getYSize() == 3 );
  }
}
