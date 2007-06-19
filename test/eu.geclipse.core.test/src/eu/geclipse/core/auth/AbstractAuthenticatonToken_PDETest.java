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

package eu.geclipse.core.auth;

import org.eclipse.core.runtime.IPath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**This class tests the methods in the AbstractAuthenticatonToken class 
 * 
 * @author tao-j
 *
 */

public class AbstractAuthenticatonToken_PDETest {

  @Before
  public void setUp() throws Exception
  {
    // needs nothing currently
  }

  /** test the functionality of method
   * {@link AbstractAuthenticatonToken#getTokenLocation()}
   * 
   */
  
  @Test
  public void testGetTokenLocation()
  {
    IPath location = AbstractAuthenticationToken.getTokenLocation();
    Assert.assertNotNull( location );
    Assert.assertEquals( "C:/Dokumente und Einstellungen/Tao-j/workspaces/junit-workspace/.metadata/.plugins/eu.geclipse.core/.tokens", location.toString() ); //$NON-NLS-1$
    Assert.assertEquals( "C:\\Dokumente und Einstellungen\\Tao-j\\workspaces\\junit-workspace\\.metadata\\.plugins\\eu.geclipse.core\\.tokens",location.toOSString() ); //$NON-NLS-1$
  }

  @Test
  public void testGetDescription()
  {
 // can not be tested due to private feature. There is no way to create this class
 // so the method can not be called even though they are public
  }

  @Test
  public void testSetActive()
  {
  //  can not be tested due to private feature
  }

  @Test
  public void testValidate()
  {
  //  can not be tested due to private feature
  }

  @Test
  public void testFireTokenStateChanged()
  {
  //  can not be tested due to private feature
  }

  @Test
  public void testGetTokenFile()
  {
  //  can not be tested due to private feature
  }
}
