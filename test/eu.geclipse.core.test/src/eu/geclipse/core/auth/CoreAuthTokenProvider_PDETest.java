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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import eu.geclipse.test.GridTestStub;

/** Tests all methods in class CoreAuthToken Provider
 * a Plug-in Junit test
 * 
 * @author tao-j
 *
 */
public class CoreAuthTokenProvider_PDETest {

  CoreAuthTokenProvider provider;
  
  //initialize a class for accessing the methods within it
  @Before
  public void setUp() throws Exception
  {
    this.provider = new CoreAuthTokenProvider();
  }

  /** Tests the methord {@link CoreAuthTokenProvider#requestToken()}
   * the return must be null if no token is available and
   * not null if a token exists
   * @throws Exception 
   */
  @Test
  public void testRequestToken() throws Exception
  {
   Assert.assertNull( this.provider.requestToken() );
   // now create a token and then test again
   GridTestStub.setUpVomsToken();
   Assert.assertNotNull( this.provider.requestToken() );
  }

  @Test
  public void testRequestTokenIAuthenticationTokenDescription()
  {
    IAuthenticationTokenDescription description = null;
    Assert.assertNotNull(this.provider.requestToken( description ) );
  }
}
