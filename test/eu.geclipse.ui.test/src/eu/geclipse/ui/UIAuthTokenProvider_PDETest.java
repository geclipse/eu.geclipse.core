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

package eu.geclipse.ui;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.core.auth.IAuthenticationTokenDescription;
import eu.geclipse.globus.auth.GlobusProxyDescription;
import eu.geclipse.test.GridTestStub;


/**tests the methods in class {@link UIAuthTokenProvider}
 * @author tao-j
 *
 */
public class UIAuthTokenProvider_PDETest {

  private static UIAuthTokenProvider provider;
  /**initialization for an object and a token
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    GridTestStub.setUpGlobusToken();
    provider = new UIAuthTokenProvider();
  }

  /**
   * tests the method {@link UIAuthTokenProvider#UIAuthTokenProvider()}
   */
  @Test
  public void testUIAuthTokenProvider() {
   Assert.assertNotNull( provider );
  }

  /**
   * tests the method {@link UIAuthTokenProvider#requestToken()}
   */
  @Test
  public void testRequestToken() {
   Assert.assertNotNull( provider.requestToken() );
   Assert.assertTrue( provider.requestToken().getID().contains( "Globus" ) ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link UIAuthTokenProvider#requestToken
   * (eu.geclipse.core.auth.IAuthenticationTokenDescription)}
   */
  @Test
  public void testRequestTokenIAuthenticationTokenDescription() {
    IAuthenticationTokenDescription testtoken = new GlobusProxyDescription();
    Assert.assertNotNull( provider.requestToken( testtoken ));
  }

  /**
   * tests the method UIAuthTokenProvider#validateToken(IAuthenticationToken)
   * @throws InvocationTargetException 
   * @throws InterruptedException 
   */
  @Test
  public void testValidateToken() throws InvocationTargetException, InterruptedException {
   IAuthenticationToken token = provider.requestToken();
   provider.validateToken( token );
   Assert.assertTrue( token.isValid() );
  }

  /**
   * tests the method UIAuthTokenProvider#activateToken(IAuthenticationToken)
   * @throws InterruptedException 
   * @throws InvocationTargetException 
   */
  @Test
  public void testActivateToken() throws InvocationTargetException, InterruptedException {
    IAuthenticationToken token = provider.requestToken();
    provider.activateToken( token );
    Assert.assertTrue( token.isActive() );
  }
}
