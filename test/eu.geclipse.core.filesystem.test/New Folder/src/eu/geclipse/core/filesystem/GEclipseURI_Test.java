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
 *    Mathias Stümpert - some refinements
 *****************************************************************************/

package eu.geclipse.core.filesystem;

import java.net.URI;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;



/**tests the class {@link GEclipseURI}
 * @author tao-j
 *
 */
public class GEclipseURI_Test {

  private static final String GECL_SCHEME
    = "gecl"; //$NON-NLS-1$
  
  private static final String GECL_QUERY
    = "geclslave=file"; //$NON-NLS-1$
  
  private static final String LOCAL_PATH
    = "/"; //$NON-NLS-1$
  
  private static final URI SLAVE_URI
    = EFS.getLocalFileSystem().getStore( new Path( LOCAL_PATH ) ).toURI();
  
  private static URI MASTER_URI;
    
  private static GEclipseURI gecluri;
  
  /**initial setups for all tests
   * 
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    MASTER_URI = new URI( GECL_SCHEME,
                          SLAVE_URI.getUserInfo(),
                          SLAVE_URI.getHost(),
                          SLAVE_URI.getPort(),
                          SLAVE_URI.getPath(),
                          GECL_QUERY,
                          SLAVE_URI.getFragment() );
    gecluri = new GEclipseURI( SLAVE_URI );
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
    Assert.assertEquals( GECL_SCHEME, GEclipseURI.getScheme() );
  }

  /**tests the method {@link GEclipseURI#getSlaveScheme()}
   * 
   */
  @Test
  public void testGetSlaveScheme() {
    Assert.assertEquals( EFS.getLocalFileSystem().getScheme(), gecluri.getSlaveScheme() );
  }

  /**tests the method {@link GEclipseURI#toMasterURI()}
   * 
   */
  @Test
  public void testToMasterURI() {
    Assert.assertEquals( MASTER_URI, gecluri.toMasterURI() );
  }

  /**tests the method {@link GEclipseURI#toSlaveURI()}
   * 
   */
  @Test
  public void testToSlaveURI() {
    Assert.assertEquals( SLAVE_URI, gecluri.toSlaveURI() );
  }

  /**tests the method {@link GEclipseURI#toString()}
   * 
   */
  @Test
  public void testToString() {
    Assert.assertEquals( MASTER_URI.toString(), gecluri.toString() );
  }
}
