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
package eu.geclipse.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the functionality of each method in the class Preferences.
 * In most cases, the test is associated with both Set and Get methods
 */

public class Preferences_Test {
  private Preferences preferences;

  @Before
  public void setUp() throws Exception
  {
    this.preferences = new Preferences ();
  }
  
  /**
   * Test for the <code>eu.geclipse.core.Preferences#setDefaultVoName/code>
   * and <code>eu.geclipse.core.Preferences#getDefaultVoName/code>.
   * The set and got values must be identical!
   * @author jie
   */
  
  @Test
  public void testSet_GetDefaultVoName()
  {
    String vo_set = "geclipse"; //$NON-NLS-1$  
    String vo_get;
    Preferences.setDefaultVoName( vo_set );
    vo_get = Preferences.getDefaultVoName();
    Assert.assertNotNull( vo_get );
    Assert.assertEquals( vo_get,vo_set );
  }
  
}
