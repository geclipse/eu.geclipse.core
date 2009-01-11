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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import eu.geclipse.aws.test.util.AWSVoTestUtil;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.aws.vo.AWSVirtualOrganization_PDETest;
import eu.geclipse.aws.vo.AWSVoCreator;
import eu.geclipse.core.auth.AuthenticationException;
import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Unit test for the {@link AWSAuthTokenDescription} class.
 * 
 * @author Moritz Post
 */
public class AWSAuthTokenDescription_PDETest {

  /** The instance of the {@link AWSAuthTokenDescription} under test. */
  private AWSAuthTokenDescription awsAuthTokenDesc;

  /**
   * The {@link AWSVirtualOrganization_PDETest} to bind the
   * {@link AWSAuthTokenDescription} under test to.
   */
  private static AWSVirtualOrganization awsVo;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    // get vo manager
    AWSVoCreator awsVoCreator = AWSVoTestUtil.getAwsVoCreator();
    if( awsVoCreator == null ) {
      fail( "Could not obtain awsVoCreator" ); //$NON-NLS-1$
    } else {
      // get vo
      AWSAuthTokenDescription_PDETest.awsVo = ( AWSVirtualOrganization )awsVoCreator.create( null );

      this.awsAuthTokenDesc = new AWSAuthTokenDescription( AWSAuthTokenDescription_PDETest.awsVo );
    }
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.auth.AWSAuthTokenDescription#AWSAuthTokenDescription(eu.geclipse.aws.vo.AWSVirtualOrganization)}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testEC2AuthTokenDescriptionEC2VirtualOrganization()
    throws ProblemException
  {
    assertNotNull( this.awsAuthTokenDesc );
    assertEquals( AWSVoTestUtil.getAwsVo(), this.awsAuthTokenDesc.getAwsVo() );
  }

  /**
   * Test method for
   * {@link AWSAuthTokenDescription#AWSAuthTokenDescription(String)}
   */
  @Test
  public void testEC2AuthTokenDescriptionString() {
    AWSAuthTokenDescription awsAuthTokenDescription = new AWSAuthTokenDescription( AWSVoTestUtil.AWS_ACCESS_ID );
    assertEquals( AWSVoTestUtil.AWS_ACCESS_ID,
                  awsAuthTokenDescription.getAwsAccessId() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.auth.AWSAuthTokenDescription#AWSAuthTokenDescription(eu.geclipse.aws.vo.AWSVirtualOrganization, java.lang.String, java.lang.String)}.
   */
  @Test
  public void testEC2AuthTokenDescriptionEC2VirtualOrganizationStringString() {
    assertNotNull( this.awsAuthTokenDesc );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.auth.AWSAuthTokenDescription#createToken()}.
   * 
   * @throws AuthenticationException
   */
  @Test
  public void testCreateToken() throws AuthenticationException {
    IAuthenticationToken ec2AuthToken = this.awsAuthTokenDesc.createToken();
    assertTrue( ec2AuthToken instanceof AWSAuthToken );
    assertEquals( this.awsAuthTokenDesc, ec2AuthToken.getDescription() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.auth.AWSAuthTokenDescription#getTokenTypeName()}.
   */
  @Test
  public void testGetTokenTypeName() {
    String tokenTypeName = this.awsAuthTokenDesc.getTokenTypeName();
    assertTrue( tokenTypeName.equals( "AWS Authentication Token" ) ); //$NON-NLS-1$
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.auth.AWSAuthTokenDescription#getWizardId()}.
   */
  @Test
  public void testGetWizardId() {
    String wizardId = this.awsAuthTokenDesc.getWizardId();
    assertTrue( wizardId.equals( "eu.geclipse.aws.ui.wizards.awsAuthTokenWizard" ) ); //$NON-NLS-1$

  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.auth.AWSAuthTokenDescription#matches(eu.geclipse.core.auth.IAuthenticationTokenDescription)}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testMatches() throws ProblemException {
    assertTrue( this.awsAuthTokenDesc.matches( this.awsAuthTokenDesc ) );

    AWSVoCreator awsVoCreator = AWSVoTestUtil.getAwsVoCreator();
    awsVoCreator.setAwsAccessId( "dummyAwsAccessId" ); //$NON-NLS-1$
    AWSVirtualOrganization ec2Vo2 = ( AWSVirtualOrganization )awsVoCreator.create( null );

    AWSAuthTokenDescription authTokenDescription2 = new AWSAuthTokenDescription( ec2Vo2 );

    assertFalse( this.awsAuthTokenDesc.matches( authTokenDescription2 ) );

    AWSAuthTokenDescription authTokenDescription3 = new AWSAuthTokenDescription( AWSAuthTokenDescription_PDETest.awsVo );

    assertTrue( this.awsAuthTokenDesc.matches( authTokenDescription3 ) );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.auth.AWSAuthTokenDescription#getAwsVo()}.
   */
  @Test
  public void testGetEc2Vo() {
    assertEquals( AWSAuthTokenDescription_PDETest.awsVo,
                  this.awsAuthTokenDesc.getAwsVo() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.auth.AWSAuthTokenDescription#getAwsAccessId()}.
   */
  @Test
  public void testGetAwsAccessId() {
    assertTrue( this.awsAuthTokenDesc.getAwsAccessId()
      .equals( AWSVoTestUtil.AWS_ACCESS_ID ) );
  }

}
