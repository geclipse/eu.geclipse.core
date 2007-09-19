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

package eu.geclipse.gvid.internal.views;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.InputStream;
import java.io.OutputStream;


import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


/**tests the class GVidPage
 * @author tao-j
 *
 */
public class GVidPage_PDETest {

  private static GVidPage gvidpage;
  
  /**setups for all tests; create a GVidPage class
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    //final InputStream in;
    //final OutputStream out;
    //String path = "d:/geclipsetest/" + "testfile"; //$NON-NLS-1$ //$NON-NLS-2$
    //in = new FileInputStream( path );
    //path = "d:/geclipsetest/" + "output"; //$NON-NLS-1$ //$NON-NLS-2$
    //out = new FileOutputStream( path );
  }

  /**tests the method GvidPage
   * 
   */
  @Test
  public void testGVidPage() {
    Assert.assertNull( gvidpage );
  }
}
