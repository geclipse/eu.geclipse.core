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

package eu.geclipse.core.util;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


/**tests the methods in the class {@link HostnameChecker}
 * @author tao-j
 */
public class HostnameChecker_Test {

  /**initialization
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // nothing is needed
  }

  /**
   * tests the method {@link HostnameChecker#checkLabel(String)}
   */
  @Test
  public void testCheckLabel() {
    String ss = new String("host1"); //$NON-NLS-1$
    Assert.assertTrue( HostnameChecker.checkLabel( ss ) );
  }

  /**
   * tests the method {@link HostnameChecker#checkHostname(String)}
   */
  @Test
  public void testCheckHostname() {
    String name = new String("myhost.12"); //$NON-NLS-1$
    Assert.assertTrue( HostnameChecker.checkHostname( name ) );
 
  }
}
