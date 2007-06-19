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
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import eu.geclipse.globus.auth.GlobusProxyDescription;

/**This class tests the methods in the AuthenticationTokenManager class 
 * 
 * @author tao-j
 *
 */ 

public class AuthenticationTokenManager_PDETest {

  IAuthenticationToken token;
  AuthenticationTokenManager authtokenmanager;
  GlobusProxyDescription description;
  
  /** generates an authorization token maneger for all tests in this class
   * 
   * @throws Exception
   */
  
  @Before
  public void setUp() throws Exception
  {
    this.authtokenmanager = AuthenticationTokenManager.getManager(); 
    File certFile = new File( "C:\\Dokumente und Einstellungen\\Tao-j\\Eigene Dateien\\usercert.pem" ); //$NON-NLS-1$
    File keyFile = new File( "C:\\Dokumente und Einstellungen\\Tao-j\\Eigene Dateien\\private-key.pem" ); //$NON-NLS-1$
    this.description = new GlobusProxyDescription( certFile, keyFile );
    PasswordManager.registerPassword( keyFile.getPath(), "gamma" ); //$NON-NLS-1$

  }

  /** tests the functionality of method
   * {@link AuthenticationTokenManager#getManager()}
   * it must not be null
   */
  @Test
  public void testGetManager()
  {
    Assert.assertNotNull( this.authtokenmanager );
  }

  /** Tests the method {@link AuthenticationTokenManager#createToken(IAuthenticationTokenDescription)}
   * Ceats a token using own cert and key file. Register the passwd for key
   * the created token must not be null and put on the correct directory
   * @throws AuthenticationException 
   */
  @Test
  public void testCreateToken() throws AuthenticationException
  {
    // create a token
    this.token = this.authtokenmanager.createToken( this.description );
    Assert.assertNotNull( this.token );
    Assert.assertEquals( this.description, this.token.getDescription() );
    this.authtokenmanager.destroyToken( this.token );
  }

  /** Tests the method {@link AuthenticationTokenManager#findToken(IAuthenticationTokenDescription)}
   * Two cases: in the true case a token is created and must be found.
   * in the false case another description is used. So a null shall be returned.
   * @throws AuthenticationException
   */
  @Test
  public void testFindToken() throws AuthenticationException
  {
    IAuthenticationToken testtoken;
    this.token = this.authtokenmanager.createToken( this.description );
    testtoken = this.authtokenmanager.findToken( this.description );
    Assert.assertEquals( this.token, testtoken );
    testtoken = this.authtokenmanager.findToken( new GlobusProxyDescription() );  
    Assert.assertNull( testtoken );
    this.authtokenmanager.destroyToken( this.token );
  }

  /** test the method {@link AuthenticationTokenManager#getTokens()}
   * Approach: create a token, check if the method can find it
   * @throws AuthenticationException
   */
  @Test
  public void testGetTokens() throws AuthenticationException
  {
    IAuthenticationToken testtoken1 = this.authtokenmanager.createToken( this.description );
    List< IAuthenticationToken > tokenlist;
    tokenlist = this.authtokenmanager.getTokens();
    Assert.assertEquals( new Integer( 1 ), new Integer( tokenlist.size()) );
    Assert.assertEquals( testtoken1,tokenlist.get( 0 ) );
    IAuthenticationToken testtoken2 = this.authtokenmanager.createToken( this.description );
    tokenlist = this.authtokenmanager.getTokens();
    Assert.assertEquals( new Integer( 2 ), new Integer( tokenlist.size()) );
    Assert.assertEquals( testtoken2,tokenlist.get( 1 ) );   
    this.authtokenmanager.destroyToken( testtoken1 );
    this.authtokenmanager.destroyToken( testtoken2 );
  }
  
  /** Tests the method {@link AuthenticationTokenManager#destroyToken(IAuthenticationToken)}
   * by: creat a token then destroy it. Examine if the manager can find it.
   * @throws AuthenticationException
   */

  @Test
  public void testDestroyToken() throws AuthenticationException
  {
    this.token = this.authtokenmanager.createToken( this.description );
    IAuthenticationToken testtoken;
    testtoken = this.authtokenmanager.findToken( this.description );
    Assert.assertEquals( this.token, testtoken);
    this.authtokenmanager.destroyToken( this.token );
    testtoken = this.authtokenmanager.findToken( this.description );
    Assert.assertNull( testtoken );
  }

  /** Tests the method {@link AuthenticationTokenManager#getTokenCount()}
   * by: create tokens ans then check the size
   * @throws AuthenticationException 
   *
   */
  @Test
  public void testGetTokenCount() throws AuthenticationException
  {
    Assert.assertEquals( new Integer( 0 ), new Integer( this.authtokenmanager.getTokenCount() ));
    IAuthenticationToken testtoken1 = this.authtokenmanager.createToken( this.description );
    IAuthenticationToken testtoken2 = this.authtokenmanager.createToken( this.description );
    Assert.assertEquals( new Integer( 2 ), new Integer( this.authtokenmanager.getTokenCount() ));
    this.authtokenmanager.destroyToken( testtoken1 );
    this.authtokenmanager.destroyToken( testtoken2 );
  }

  /** Tests the method {@link AuthenticationTokenManager#isEmpty()}
   * 
   * @throws AuthenticationException
   */
  @Test
  public void testIsEmpty() throws AuthenticationException
  {
    Assert.assertTrue( this.authtokenmanager.isEmpty());
    this.authtokenmanager.createToken( this.description );
    Assert.assertTrue( !this.authtokenmanager.isEmpty());
  }

  /** Tests the method {@link AuthenticationTokenManager#setDefaultToken(IAuthenticationToken)}
   * by: using the AbstractAuthTokenProvider.staticRequestToken() to see
   * if the default is correctly set
   * @throws AuthenticationException
   */
  @Test
  public void testSetDefaultToken() throws AuthenticationException
  {
    IAuthenticationToken testtoken2 = this.authtokenmanager.createToken( this.description );
    this.authtokenmanager.setDefaultToken( testtoken2 );
    Assert.assertEquals( testtoken2, AbstractAuthTokenProvider.staticRequestToken() ); 
  }

  /** Tests the method {@link AuthenticationTokenManager#getDefaultToken()}
   * by setting one then getting it
   * @throws AuthenticationException
   */
  @Test
  public void testGetDefaultToken() throws AuthenticationException
  {
    IAuthenticationToken testtoken1 = this.authtokenmanager.createToken( this.description );
    this.authtokenmanager.setDefaultToken( testtoken1 );
    Assert.assertEquals( testtoken1, this.authtokenmanager.getDefaultToken() ); 
  }

  /** Tests the method {@link AuthenticationTokenManager#addContentChangeListener()}
   * and the method {@link AuthenticationTokenManager#removeContentChangeListener()}
   *
   */

  @Test
  public void testAddToken()
  {
    // can not be teste due to the private feature;
  }

  @Test
  public void testRemoveToken()
  {
    // can not be teste due to the private feature;
  }

  @Test
  public void testFireContentChanged()
  {
    // can not be teste due to the private feature;
  }
}
