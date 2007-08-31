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
 *    Jie Tao - test class (Plug-in test)
 *****************************************************************************/

package eu.geclipse.gvid.internal;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;



/**tests the class {@link GVidClient}
 * @author tao-j
 *
 */
public class GVidClient_PDETest {
  
  private static GVidClient gvidclient;

  /**setups for all tests; create a GVidClient class
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    InputStream in;
    OutputStream out;
    String path = "d:/geclipsetest/" + "testfile"; //$NON-NLS-1$ //$NON-NLS-2$
    in = new FileInputStream( path );
    path = "d:/geclipsetest/" + "output"; //$NON-NLS-1$ //$NON-NLS-2$
    out = new FileOutputStream( path );
    gvidclient = new GVidClient(in, out);
  }

  /**tests the method {@link GVidClient#GVidClient(InputStream, OutputStream)}
   * 
   */
  @Test
  public void testGVidClient() {
    Assert.assertNotNull( gvidclient );
  }

  /**tests the method {@link GVidClient#getFocusCycleRootAncestor()}
   * it is false
   */
  @Test
  public void testGetFocusTraversalKeysEnabled() {
    Assert.assertFalse( gvidclient.getFocusTraversalKeysEnabled() );
  }

  /**tests the method {@link GVidClient#stop()}
   * after stop, isrunning must be false
   */
  @Test
  public void testStop() {
    gvidclient.stop();
    Assert.assertFalse( gvidclient.isRunning());
  }

  /**tests the method {@link GVidClient#isRunning()}
   * it is initially false
   */
  @Test
  public void testIsRunning() {
    Assert.assertFalse( gvidclient.isRunning()); 
    }

  /**tests the method {@link GVidClient#getPreferredSize()}
   * it is initially false
   */
  @Test
  public void testGetPreferredSize() {
    Dimension graphics = gvidclient.getPreferredSize();
    Assert.assertEquals( new Integer( 100 ), new Integer( graphics.width) );
    Assert.assertEquals( new Integer( 100 ), new Integer( graphics.height) );
  }
}
