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

package eu.geclipse.gvid.internal;

import static org.junit.Assert.assertTrue;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for the <code>eu.geclipse.gvid.internal.Connection</code>
 * class.
 */
public class Connection_Test {
  private PipedInputStream inIn;
  private PipedOutputStream inOut;
  private PipedInputStream outIn;
  private PipedOutputStream outOut;
  private Connection connection;

  /**
   * Set up input and output streams to test connection class.
   * @throws Exception IOException
   */
  @Before
  public void setUp() throws Exception {
    this.inIn = new PipedInputStream();
    this.inOut = new PipedOutputStream();
    this.inIn.connect( this.inOut );
    this.outIn = new PipedInputStream();
    this.outOut = new PipedOutputStream();
    this.outIn.connect( this.outOut );
    this.connection = new Connection( this.inIn, this.outOut );
  }

  /**
   * Test method for
   * {@link eu.geclipse.gvid.internal.Connection#getNumBytesInBuffer()}.
   * @throws Exception IOException
   */
  @Test
  public void testGetNumBytesInBuffer() throws Exception {
    assertTrue( this.connection.getNumBytesInBuffer() == 0 );
    byte[] bytes = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    this.inOut.write( bytes );
    this.inOut.flush();
    assertTrue( this.connection.getNumBytesInBuffer() == 10 );
    this.inOut.write( bytes );
    this.inOut.flush();
    assertTrue( this.connection.getNumBytesInBuffer() == 20 );    
  }

  /**
   * Test method for
   * {@link eu.geclipse.gvid.internal.Connection#dropBytes(int)}.
   * @throws Exception IOException
   */
  @Test
  public void testDropBytes() throws Exception {
    byte[] bytes = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    this.inOut.write( bytes );
    this.inOut.flush();
    assertTrue( this.connection.getNumBytesInBuffer() == 10 );
    this.connection.dropBytes( 4 );
    assertTrue( this.connection.getNumBytesInBuffer() == 6 );
    this.connection.dropBytes( 0 );
    assertTrue( this.connection.getNumBytesInBuffer() == 6 );
    this.connection.dropBytes( 10 );
    assertTrue( this.connection.getNumBytesInBuffer() == 0 );
  }

  /**
   * Test method for
   * {@link eu.geclipse.gvid.internal.Connection#readData(byte[],int)}.
   * @throws Exception IOException
   */
  @Test
  public void testReadData() throws Exception {
    byte[] bytes = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    byte[] bytes2 =  new byte[20];
    this.inOut.write( bytes );
    this.inOut.flush();
    assertTrue( this.connection.readData( bytes2, 20 ) == 10 );
    for( int i = 0; i < bytes.length; i++ ) {
      assertTrue( bytes[i] == bytes2[i] );
    }
  }

  /**
   * Test method for
   * {@link eu.geclipse.gvid.internal.Connection#readData(byte[],int)}.
   * @throws Exception IOException
   */
  @Test
  public void testReadData2() throws Exception {
    byte[] bytes = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    byte[] bytes2 =  new byte[20];
    this.inOut.write( bytes );
    this.inOut.flush();
    assertTrue( this.connection.readData( bytes2, 20, 5 ) == 10 );
    for( int i = 0; i < bytes.length; i++ ) {
      assertTrue( bytes[i] == bytes2[i+5] );
    }
  }

  /**
   * Test method for
   * {@link eu.geclipse.gvid.internal.Connection#readByte()}.
   * @throws Exception IOException
   */
  @Test
  public void testReadByte() throws Exception {
    byte[] bytes = { ( byte )0x01, ( byte )0x23, ( byte )0x45, ( byte )0x67,
                     ( byte )0x89, ( byte )0xAB, ( byte )0xCD, ( byte )0xEF };
    this.inOut.write( bytes );
    this.inOut.flush();
    assertTrue( this.connection.readByte() == ( byte ) 0x01 );
    assertTrue( this.connection.readByte() == ( byte ) 0x23 );
    assertTrue( this.connection.readByte() == ( byte ) 0x45 );
    assertTrue( this.connection.readByte() == ( byte ) 0x67 );
    assertTrue( this.connection.readByte() == ( byte ) 0x89 );
    assertTrue( this.connection.readByte() == ( byte ) 0xAB );
    assertTrue( this.connection.readByte() == ( byte ) 0xCD );
    assertTrue( this.connection.readByte() == ( byte ) 0xEF );
  }

  /**
   * Test method for
   * {@link eu.geclipse.gvid.internal.Connection#readUint16()}.
   * @throws Exception IOException
   */
  @Test
  public void testReadUint16() throws Exception {
    byte[] bytes = { ( byte )0x01, ( byte )0x23, ( byte )0x45, ( byte )0x67,
                     ( byte )0x89, ( byte )0xAB, ( byte )0xCD, ( byte )0xEF };
    this.inOut.write( bytes );
    this.inOut.flush();
    assertTrue( this.connection.readUint16() == ( short ) 0x0123 );
    assertTrue( this.connection.readUint16() == ( short ) 0x4567 );
    assertTrue( this.connection.readUint16() == ( short ) 0x89AB );
    assertTrue( this.connection.readUint16() == ( short ) 0xCDEF );
  }

  /**
   * Test method for
   * {@link eu.geclipse.gvid.internal.Connection#readUint32()}.
   * @throws Exception IOException
   */
  @Test
  public void testReadUint32() throws Exception {
    byte[] bytes = { ( byte )0x01, ( byte )0x23, ( byte )0x45, ( byte )0x67,
                     ( byte )0x89, ( byte )0xAB, ( byte )0xCD, ( byte )0xEF };
    this.inOut.write( bytes );
    this.inOut.flush();
    assertTrue( this.connection.readUint32() == 0x01234567 );
    assertTrue( this.connection.readUint32() == 0x89ABCDEF );
  }
}
