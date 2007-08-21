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
 *    Jie Tao - test class (Junit test)
 *****************************************************************************/

package eu.geclipse.core.filesystem;

import java.net.URI;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;



/**tests the class {@link GEclipseURI}
 * @author tao-j
 *
 */
public class GEclipseURI_Test {

  private static GEclipseURI gecluri;
  private static URI localuri;
  /**initial setups for all tests
   * 
   * @throws Exception
   */
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String local = "file:///D/geclipsetest"; //$NON-NLS-1$
    localuri = new URI( local );
    gecluri = new GEclipseURI( localuri );
  }

  /**tests the method {@link GEclipseURI}
   * 
   */
  @Test
  public void testGEclipseURI() {
    Assert.assertNotNull( gecluri );
  }

  /**tests the method {@link GEclipseURI#getScheme()}
   * 
   */
  @Test
  public void testGetScheme() {
    Assert.assertEquals( "gecl", GEclipseURI.getScheme() ); //$NON-NLS-1$
  }

  /**tests the method {@link GEclipseURI#getSlaveScheme()}
   * 
   */
  @Test
  public void testGetSlaveScheme() {
    Assert.assertEquals( "file", gecluri.getSlaveScheme() ); //$NON-NLS-1$
  }

  /**tests the method {@link GEclipseURI#toMasterURI()}
   * 
   */
  @Test
  public void testToMasterURI() {
    Assert.assertTrue( gecluri.toMasterURI().toString().contains( "/D/geclipsetest" ) ); //$NON-NLS-1$
  }

  /**tests the method {@link GEclipseURI#toSlaveURI()}
   * 
   */
  @Test
  public void testToSlaveURI() {
    Assert.assertEquals( localuri, gecluri.toSlaveURI() );
  }

  /**tests the method {@link GEclipseURI#toString()}
   * 
   */
  @Test
  public void testToString() {
    Assert.assertTrue( gecluri.toString().contains( "/D/geclipsetest" ) ); //$NON-NLS-1$
  }
}
