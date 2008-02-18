/*****************************************************************************
 * Copyright (c) 2006, 2007, 2008 g-Eclipse Consortium 
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
package eu.geclipse.core.reporting.internal;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import eu.geclipse.core.ICoreSolutions;

/**tests the methods in class {@link SolutionFactory}
 * @author tao-j
 *
 */
public class SolutionFactory_PDETest {

  private static SolutionFactory factory;
  /**initialization
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    factory = SolutionFactory.getFactory();
  }

  /**
   * tests the method {@link SolutionFactory#getFactory()}
   */
  @Test
  public void testGetFactory() {
    Assert.assertNotNull( factory );
  }

  /**
   * tests the method {@link SolutionFactory#getSolution(String, String)}
   */
  @Test
  public void testGetSolution() {
    Solution solution = factory.getSolution( ICoreSolutions.AUTH_CHECK_CA_CERTIFICATES, null );
    Assert.assertNotNull( solution );
    Assert.assertEquals( "Check your imported CA-certificates", solution.getDescription() ); //$NON-NLS-1$
    Assert.assertNotNull( factory.getSolution( ICoreSolutions.AUTH_CHECK_DATA, null ) );
    Assert.assertNotNull( factory.getSolution( ICoreSolutions.AUTH_CHECK_TOKENS, null ) );
    Assert.assertNotNull( factory.getSolution( ICoreSolutions.AUTH_CHECK_VO_SETTINGS, null ) );
    Assert.assertNotNull( factory.getSolution( ICoreSolutions.IO_DOWNLOAD_FILE_AGAIN, null ) );
    Assert.assertNotNull( factory.getSolution( ICoreSolutions.IO_DOWNLOAD_FROM_ANOTHER_SOURCE, null ) );
    Assert.assertNotNull( factory.getSolution( ICoreSolutions.NET_CHECK_FIREWALL, null ) );
    Assert.assertNotNull( factory.getSolution( ICoreSolutions.NET_CHECK_HOSTNAME_MATCHES_IP_ADDRESS, null ) );
    Assert.assertNotNull( factory.getSolution( ICoreSolutions.NET_CHECK_INTERNET_CONNECTION, null ) );
    Assert.assertNotNull( factory.getSolution( ICoreSolutions.NET_CHECK_PORT_ALREADY_IN_USE, null ) );
    Assert.assertNotNull( factory.getSolution( ICoreSolutions.NET_CHECK_PROXY_SETTINGS, null ) );
    Assert.assertNotNull( factory.getSolution( ICoreSolutions.NET_CHECK_PUBLIC_IP_ADDRESS, null ) );
    Assert.assertNotNull( factory.getSolution( ICoreSolutions.NET_CHECK_SERVER_URL, null ) );
    Assert.assertNotNull( factory.getSolution( ICoreSolutions.NET_CHECK_TIMEOUT_SETTINGS, null ) );
    Assert.assertNotNull( factory.getSolution( ICoreSolutions.NET_CONTACT_SERVER_ADMIN, null ) );
    Assert.assertNotNull( factory.getSolution( ICoreSolutions.NET_USE_ANOTHER_PORT, null ) );
    Assert.assertNotNull( factory.getSolution( ICoreSolutions.SYS_CHECK_SYSTEM_TIME, null ) );  
  }
}
