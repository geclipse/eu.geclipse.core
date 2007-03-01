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
 *    Markus Knauer - initial API and implementation
 *****************************************************************************/
package eu.geclipse.core.auth;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for the <code>eu.geclipse.core.auth.AuthenticationTokenManager</code>
 * class.
 * 
 * @author markus
 */
public class AuthenticationTokenManager_Test {

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception
  {
    // TODO mknauer
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception
  {
    // TODO mknauer
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.auth.AuthenticationTokenManager#getManager()}.
   */
  @Test
  public void testGetManager()
  {
    assertNotNull( AuthenticationTokenManager.getManager() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.auth.AuthenticationTokenManager#createToken(eu.geclipse.core.auth.IAuthenticationTokenDescription)}.
   */
  @Test
  public void testCreateToken()
  {
    fail( "Not yet implemented" ); // TODO //$NON-NLS-1$
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.auth.AuthenticationTokenManager#findToken(eu.geclipse.core.auth.IAuthenticationTokenDescription)}.
   */
  @Test
  public void testFindToken()
  {
    fail( "Not yet implemented" ); // TODO //$NON-NLS-1$
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.auth.AuthenticationTokenManager#getTokens()}.
   */
  @Test
  public void testGetTokens()
  {
    fail( "Not yet implemented" ); // TODO //$NON-NLS-1$
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.auth.AuthenticationTokenManager#destroyToken(eu.geclipse.core.auth.IAuthenticationToken)}.
   */
  @Test
  public void testDestroyToken()
  {
    fail( "Not yet implemented" ); // TODO //$NON-NLS-1$
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.auth.AuthenticationTokenManager#getTokenCount()}.
   */
  @Test
  public void testGetTokenCount()
  {
    fail( "Not yet implemented" ); // TODO //$NON-NLS-1$
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.auth.AuthenticationTokenManager#isEmpty()}.
   */
  @Test
  public void testIsEmpty()
  {
    fail( "Not yet implemented" ); // TODO //$NON-NLS-1$
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.auth.AuthenticationTokenManager#setDefaultToken(eu.geclipse.core.auth.IAuthenticationToken)}.
   */
  @Test
  public void testSetDefaultToken()
  {
    fail( "Not yet implemented" ); // TODO //$NON-NLS-1$
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.auth.AuthenticationTokenManager#getDefaultToken()}.
   */
  @Test
  public void testGetDefaultToken()
  {
    fail( "Not yet implemented" ); // TODO //$NON-NLS-1$
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.auth.AuthenticationTokenManager#addContentChangeListener(org.eclipse.compare.IContentChangeListener)}.
   */
  @Test
  public void testAddContentChangeListener()
  {
    fail( "Not yet implemented" ); // TODO //$NON-NLS-1$
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.auth.AuthenticationTokenManager#removeContentChangeListener(org.eclipse.compare.IContentChangeListener)}.
   */
  @Test
  public void testRemoveContentChangeListener()
  {
    fail( "Not yet implemented" ); // TODO //$NON-NLS-1$
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.auth.AuthenticationTokenManager#addToken(eu.geclipse.core.auth.IAuthenticationToken)}.
   */
  @Test
  public void testAddToken()
  {
    fail( "Not yet implemented" ); // TODO //$NON-NLS-1$
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.auth.AuthenticationTokenManager#removeToken(eu.geclipse.core.auth.IAuthenticationToken)}.
   */
  @Test
  public void testRemoveToken()
  {
    fail( "Not yet implemented" ); // TODO //$NON-NLS-1$
  }

  /**
   * Test method for
   * {@link eu.geclipse.core.auth.AuthenticationTokenManager#fireContentChanged()}.
   */
  @Test
  public void testFireContentChanged()
  {
    fail( "Not yet implemented" ); // TODO //$NON-NLS-1$
  }
}
