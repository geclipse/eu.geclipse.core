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

import java.io.File;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import eu.geclipse.globus.auth.GlobusProxy;
import eu.geclipse.globus.auth.GlobusProxyDescription;

/**This class tests the methods in the AbstractAuthTokenProvider class 
 * 
 * @author tao-j
 *
 */ 

public class AbstractAuthTokenProvider_PDETest {

  IAuthenticationToken token;
  AuthenticationTokenManager authtokenmanager = AuthenticationTokenManager.getManager();
  
  @Before
  public void setUp() throws Exception
  {
//  create a token for test
    GlobusProxyDescription description;
    Assert.assertNotNull( this.authtokenmanager );
    File certFile = new File( "C:\\Dokumente und Einstellungen\\Tao-j\\Eigene Dateien\\usercert.pem" ); //$NON-NLS-1$
    File keyFile = new File( "C:\\Dokumente und Einstellungen\\Tao-j\\Eigene Dateien\\private-key.pem" ); //$NON-NLS-1$
    description = new GlobusProxyDescription( certFile, keyFile );
    PasswordManager.registerPassword( keyFile.getPath(), "gamma" ); //$NON-NLS-1$
    this.token = this.authtokenmanager.createToken( description );
    this.token.validate();
    this.token.setActive( true );
  }
  
  /** this test verifies the functionality of method
   * {@link AbstractAuthTokenProvider#staticRequestToken()}
   * by first creating a token than comparing this one with 
   * that delivered by this method
   * the created token must be deleted for the next test, because with
   * setup a new token will be generated for this test
   * @throws AuthenticationException 
   */

  @Test
  public void testStaticRequestToken() throws AuthenticationException
  {
    IAuthenticationToken testtoken;
    testtoken = AbstractAuthTokenProvider.staticRequestToken();
    Assert.assertNotNull( testtoken );
    Assert.assertEquals( this.token, testtoken );
    this.token.setActive( false );
    testtoken = AbstractAuthTokenProvider.staticRequestToken();
    Assert.assertEquals( this.token, testtoken );
    Assert.assertTrue( testtoken instanceof GlobusProxy );
    this.authtokenmanager.destroyToken( this.token );  
  }

  /** this test verifies the functionality of method
   * {@link AbstractAuthTokenProvider#staticRequestToken(IAuthenticationTokenDescription)}
   * by calling this method and then checking whether a token is returned 
   * and corresponds the created one
   * @throws AuthenticationException 
   */
  @Test
  public void testStaticRequestTokenIAuthenticationTokenDescription() throws AuthenticationException
  {
    IAuthenticationToken testtoken;
    testtoken = AbstractAuthTokenProvider.staticRequestToken( new GlobusProxyDescription ());
    Assert.assertNotNull( testtoken );
    Assert.assertTrue( testtoken instanceof GlobusProxy );
    Assert.assertEquals( this.token.getID(), testtoken.getID() );
    this.authtokenmanager.destroyToken( this.token );  
  }
}
