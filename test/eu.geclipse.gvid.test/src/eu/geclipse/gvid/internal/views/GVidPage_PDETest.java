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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.internal.PartPane;
import org.eclipse.ui.internal.WorkbenchPartReference;
import org.ietf.jgss.GSSException;
import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.IBidirectionalConnection;
import eu.geclipse.gvid.IGVidPage;
import eu.geclipse.gvid.internal.GVidClient;
import eu.geclipse.test.GridTestStub;

/**tests the class {@link GVidPage}
 * @author tao-j
 *
 */
public class GVidPage_PDETest {

  private static GVidPage gvidpage;
  private static GVidClient gvidclient;

  /**setups for all tests; create a GVidPage class
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    final InputStream in;
    final OutputStream out;
    String path = GridTestStub.setUpLocalDir() + "testfile"; //$NON-NLS-1$
    in = new FileInputStream( path );
    path = GridTestStub.setUpLocalDir() + "output"; //$NON-NLS-1$
    out = new FileOutputStream( path );
    gvidclient = new GVidClient(in, out);
    GVidView gvidview = new GVidView();
  }

  @Test
  public void testGVidPage() {
    Assert.assertNotNull( gvidpage );
  }

  @Test
  public void testStartClient() {
  }

  @Test
  public void testStopClient() {
    
  }

  @Test
  public void testStatsUpdated() {
   
  }

  @Test
  public void testGetTabName() {
    
  }

  @Test
  public void testSetTabName() {
    
  }
}
