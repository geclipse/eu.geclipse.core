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

package eu.geclipse.aws.ec2;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Moritz Post
 */
public class EC2_PDETest {

  /** The url of the Amazon EC2 webservices. */
  private static final String AWS_EC2_URL = "https://ec2.amazonaws.com/"; //$NON-NLS-1$

  /** A dummy access Id for the Amazon EC2 services. */
  private static final String AWS_ACCESS_ID = "awsAccessId"; //$NON-NLS-1$

  /** A dummy secret Id for the Amazon EC2 services. */
  private static final String AWS_SECRET_ID = "awsSecretId"; //$NON-NLS-1$

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.EC2#initEc2(java.lang.String, java.lang.String, java.lang.String)}
   * .
   * 
   * @throws Exception
   */
  @Test
  public void testInitEc2Service() throws Exception {
    IEC2 ec2 = EC2Registry.getRegistry().getEC2( EC2_PDETest.AWS_ACCESS_ID );
    boolean initResult = ec2.initEc2( EC2_PDETest.AWS_EC2_URL,
                                      EC2_PDETest.AWS_ACCESS_ID,
                                      EC2_PDETest.AWS_SECRET_ID );
    assertTrue( initResult );

    ec2 = EC2Registry.getRegistry().getEC2( EC2_PDETest.AWS_ACCESS_ID );
    try {
      ec2.initEc2( null, EC2_PDETest.AWS_ACCESS_ID, EC2_PDETest.AWS_SECRET_ID );
      fail( "An exception should have been thrown" ); //$NON-NLS-1$
    } catch( Exception e ) {
      assertNotNull( e.getMessage() );
    }
    try {
      ec2.initEc2( EC2_PDETest.AWS_EC2_URL, null, EC2_PDETest.AWS_SECRET_ID );
      fail( "An exception should have been thrown" ); //$NON-NLS-1$
    } catch( Exception e ) {
      assertNotNull( e.getMessage() );
    }
    try {
      ec2.initEc2( EC2_PDETest.AWS_EC2_URL, EC2_PDETest.AWS_ACCESS_ID, null );
      fail( "An exception should have been thrown" ); //$NON-NLS-1$
    } catch( Exception e ) {
      assertNotNull( e.getMessage() );
    }
  }

  /**
   * Test method for {@link EC2#isInitialized()}.
   * 
   * @throws Exception
   */
  @Test
  public void isInitialized() throws Exception {
    IEC2 ec2 = EC2Registry.getRegistry().getEC2( EC2_PDETest.AWS_ACCESS_ID );
    assertFalse( ec2.isInitialized() );
    boolean initResult = ec2.initEc2( EC2_PDETest.AWS_EC2_URL,
                                      EC2_PDETest.AWS_ACCESS_ID,
                                      EC2_PDETest.AWS_SECRET_ID );
    assertTrue( initResult );

    assertTrue( ec2.isInitialized() );

  }
}
