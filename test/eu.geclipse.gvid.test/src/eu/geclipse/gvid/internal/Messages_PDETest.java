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
 *    Jie Tao, FZK - test class for software quality
 *****************************************************************************/

package eu.geclipse.gvid.internal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for the <code>eu.geclipse.gvid.internal.Messages</code>
 * class.
 */
public class Messages_PDETest {
  private final String string1 = "GVidClient.gvid"; //$NON-NLS-1$
  private final String string2 = "failmessage"; //$NON-NLS-1$
  private final String string3 = "GVidClient.cantInstanciateDecoder"; //$NON-NLS-1$
  private final String string4 = "Connection.amountGreaterBufferContents"; //$NON-NLS-1$
  private final Object testarg1 = new Integer( 5 );
  private final Object[] testarg2 = { new Integer( 5 ), "buffer1" }; //$NON-NLS-1$
  
  /**
   * @brief Set up the number of frames to test the Messages class.
   * @throws Exception IOException
   */
  @Before
  public void setUp() throws Exception {// currently nothing to be inserted
  }

  /**
   * @brief Test method for
   * {@link eu.geclipse.gvid.internal.Messages#getString(String)}.
   * @throws Exception IOException
   */
  @Test 
  public void testgetString() throws Exception { 
    Assert.assertEquals( Messages.getString( this.string1 ), "GVid" ); //$NON-NLS-1$
    Assert.assertEquals( Messages.getString( this.string2 ), "!failmessage!" ); //$NON-NLS-1$
  }
  
  /**
   * @brief Test method for
   * {@link eu.geclipse.gvid.internal.Messages#formatMessage(String,Object)}.
   * @throws Exception IOException
   */
  @Test
  public void testformatMessage() throws Exception {  
    Assert.assertEquals( Messages.formatMessage( this.string3,this.testarg1 ),
      "Cannot instantiate decoder for codec \"5\"\nPlease set a codec in the preferences dialog." ); //$NON-NLS-1$
    Assert.assertEquals( Messages.formatMessage( this.string4,this.testarg2[0],this.testarg2[1] ),
      "amount of bytes to drop (5) is greater than number of bytes in buffer (buffer1)" ); //$NON-NLS-1$
  }
}
