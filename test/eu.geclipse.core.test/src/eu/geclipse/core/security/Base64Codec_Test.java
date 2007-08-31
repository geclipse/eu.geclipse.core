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
 *    Markus Knauer - initial API and implementation
 *****************************************************************************/
package eu.geclipse.core.security;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Testclass for the {@link Base64Codec} class
 */
public class Base64Codec_Test {

  private String encodedStr = "VGhpcyBpcyBhIFRlc3QuLi4gW117fV8rLT0hJCVeJg=="; //$NON-NLS-1$
  private String decodedStr = "This is a Test... []{}_+-=!$%^&"; //$NON-NLS-1$

  /**
   * Test method for
   * {@link eu.geclipse.core.security.Base64Codec#encode(java.lang.String)}.
   */
  @Test
  public void testEncodeString()
  {
    String str = Base64Codec.encode( this.decodedStr );
    assertNotNull( str );
    assertTrue( str.length() > 0 );
    assertTrue( str.equals( this.encodedStr ) );
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.security.Base64Codec#encode(byte[])}.
   */
  @Test
  public void testEncodeByteArray()
  {
    byte[] decoded = this.decodedStr.getBytes();
    byte[] bs = Base64Codec.encode( decoded );
    assertNotNull( bs );
    assertTrue( bs.length > 0 );
    byte[] bytes = this.encodedStr.getBytes();
    assertTrue( bytes.length == bs.length );
    for( int ii = 0; ii < bs.length; ii++ ) {
      assertTrue( bs[ ii ] == bytes[ ii ] );
    }
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.security.Base64Codec#decode(java.lang.String)}.
   */
  @Test
  public void testDecodeString()
  {
    String str = Base64Codec.decode( this.encodedStr );
    assertNotNull( str );
    assertTrue( str.length() > 0 );
    assertTrue( str.equals( this.decodedStr ) );
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.security.Base64Codec#decode(byte[])}.
   */
  @Test
  public void testDecodeByteArray()
  {
    byte[] encoded = this.encodedStr.getBytes();
    byte[] bs = Base64Codec.decode( encoded );
    assertNotNull( bs );
    assertTrue( bs.length > 0 );
    byte[] bytes = this.decodedStr.getBytes();
    assertTrue( bytes.length == bs.length );
    for( int ii = 0; ii < bs.length; ii++ ) {
      assertTrue( bs[ ii ] == bytes[ ii ] );
    }
  }
}
