/* Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *    Jie Tao - test class (Junit test)
 *****************************************************************************/

package eu.geclipse.core.io;

import java.io.IOException;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


/**this class tests the methods in the class {@link PipedInputStream}
 * @author tao-j
 *
 */
public class PipedInputStream_Test {

  private static PipedInputStream input;
  
  /**initialization
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    input = new eu.geclipse.core.io.PipedInputStream();
  }

  @Test
  public void testReadByteArrayIntInt() throws IOException {
    byte[] b = {12,34,56};
    int result = input.read( b, 0, 3 );
    Assert.assertEquals( new Integer(3), new Integer(result) );
  }

  @Test
  public void testAvailable() {
    
  }

  @Test
  public void testClose() {
  
  }

  @Test
  public void testPipedInputStreamPipedOutputStream() {
   
  }

  @Test
  public void testPipedInputStream() {
    
  }

  @Test
  public void testConnect() {
    
  }

  @Test
  public void testWriteInt() {
  
  }

  @Test
  public void testWriteByteArrayIntInt() {
   
  }
}
