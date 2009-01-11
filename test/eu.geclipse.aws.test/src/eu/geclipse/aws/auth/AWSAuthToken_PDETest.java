/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.auth;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import eu.geclipse.aws.test.util.AWSVoTestUtil;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.aws.vo.AWSVoCreator;
import eu.geclipse.core.auth.AuthenticationException;
import eu.geclipse.core.reporting.ProblemException;

/**
 * @author Moritz Post
 */
public class AWSAuthToken_PDETest {

  /** The {@link AWSAuthTokenDescription} to spawn a {@link AWSAuthToken}. */
  private AWSAuthTokenDescription awsTokenDesc;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    AWSVoCreator awsVoCreator = AWSVoTestUtil.getAwsVoCreator();
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )awsVoCreator.create( null );

    this.awsTokenDesc = new AWSAuthTokenDescription( awsVo );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.auth.AWSAuthToken#AWSAuthToken(eu.geclipse.core.auth.IAuthenticationTokenDescription)}.
   */
  @Test
  public void testAWSAuthToken() {
    AWSAuthToken authToken = new AWSAuthToken( this.awsTokenDesc );
    assertNotNull( authToken );
  }

  /**
   * Test method for {@link eu.geclipse.aws.auth.AWSAuthToken#getID()}.
   */
  @Test
  public void testGetID() {
    AWSAuthToken authToken = new AWSAuthToken( this.awsTokenDesc );
    assertNotNull( authToken );
    assertNotNull( authToken.getID() );
    assertTrue( authToken.getID().length() > 0 );
  }

  /**
   * Test method for {@link eu.geclipse.aws.auth.AWSAuthToken#getTimeLeft()}.
   */
  @Test
  public void testGetTimeLeft() {
    AWSAuthToken authToken = new AWSAuthToken( this.awsTokenDesc );
    assertTrue( authToken.getTimeLeft() == -1 );
  }

  /**
   * Test method for {@link eu.geclipse.aws.auth.AWSAuthToken#isActive()} and
   * {@link eu.geclipse.aws.auth.AWSAuthToken#setActive(boolean)} .
   * 
   * @throws AuthenticationException
   * @throws ProblemException
   */
  @Test
  public void testActive() throws AuthenticationException, ProblemException {
    AWSVoCreator awsVoCreator = AWSVoTestUtil.getAwsVoCreator();
    awsVoCreator.setVoName( "AWS Virtual Organization" ); //$NON-NLS-1$
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )awsVoCreator.create( null );
    awsVoCreator.apply( awsVo );

    AWSAuthTokenDescription awsTokenDesc = new AWSAuthTokenDescription( awsVo );
    AWSAuthToken awsAuthToken = new AWSAuthToken( awsTokenDesc );
    assertFalse( awsAuthToken.isActive() );
    awsAuthToken.setActive( true );
    assertTrue( awsAuthToken.isActive() );
    awsAuthToken.setActive( true );
    assertTrue( awsAuthToken.isActive() );
    awsAuthToken.setActive( false );
    assertFalse( awsAuthToken.isActive() );
    awsAuthToken.setActive( false );
    assertFalse( awsAuthToken.isActive() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.auth.AWSAuthToken#validate(org.eclipse.core.runtime.IProgressMonitor)}.
   * and {@link eu.geclipse.aws.auth.AWSAuthToken#isValid()}.
   * 
   * @throws ProblemException
   * @throws AuthenticationException
   */
  @Test
  @Ignore("Currently validaton is not possible since there is no concrete service to test the token against.")
  public void testValidIProgressMonitor()
    throws ProblemException, AuthenticationException
  {
    AWSVoCreator awsVoCreator = AWSVoTestUtil.getAwsVoCreator();
    awsVoCreator.setVoName( "AWS Virtual Organization" ); //$NON-NLS-1$
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )awsVoCreator.create( null );
    awsVoCreator.apply( awsVo );

    AWSAuthTokenDescription awsTokenDesc = new AWSAuthTokenDescription( awsVo );
    AWSAuthToken awsAuthToken = new AWSAuthToken( awsTokenDesc );

    awsAuthToken.validate( new NullProgressMonitor() );
    assertTrue( awsAuthToken.isValid() );

    awsTokenDesc = new AWSAuthTokenDescription( awsVo );
    awsAuthToken = new AWSAuthToken( awsTokenDesc );
    try {
      awsAuthToken.validate( new NullProgressMonitor() );
      fail( "An AuthenticationException should have been thrown" ); //$NON-NLS-1$
    } catch( AuthenticationException authExc ) {
      // nothing to do here
    } finally {
      assertFalse( awsAuthToken.isValid() );
    }
  }
}
