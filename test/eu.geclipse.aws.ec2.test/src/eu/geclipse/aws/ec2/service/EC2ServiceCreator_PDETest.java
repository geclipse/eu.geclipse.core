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

package eu.geclipse.aws.ec2.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import eu.geclipse.aws.ec2.test.util.AWSVoTestUtil;
import eu.geclipse.aws.ec2.test.util.EC2ServiceTestUtil;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Test class for the {@link EC2ServiceCreator} class.
 * 
 * @author Moritz Post
 */
public class EC2ServiceCreator_PDETest {

  /** The {@link EC2ServiceCreator} class under test. */
  private EC2ServiceCreator serviceCreator;

  /**
   * Prepares the required {@link EC2ServiceCreator}s.
   */
  @Before
  public void setUp() {
    this.serviceCreator = EC2ServiceTestUtil.getEC2ServiceCreator();
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceCreator#create(eu.geclipse.core.model.IGridContainer)}
   * .
   * 
   * @throws ProblemException
   */
  @Test
  public void testCreate() throws ProblemException {
    EC2Service ec2Service = ( EC2Service )this.serviceCreator.create( null );
    assertNull( ec2Service );
    ec2Service = ( EC2Service )this.serviceCreator.create( AWSVoTestUtil.getAwsVo() );
    assertNotNull( ec2Service );
    String ec2Url = ec2Service.getProperties().getEc2Url();
    assertEquals( EC2ServiceTestUtil.EC2_URL, ec2Url );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceCreator#getExtensionID()}.
   */
  @Test
  public void testGetExtensionID() {
    assertEquals( "eu.geclipse.aws.ec2.service.ec2ServiceCreator", //$NON-NLS-1$
                  this.serviceCreator.getExtensionID() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceCreator#getServiceName()} and
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceCreator#setServiceName(java.lang.String)}
   * .
   */
  @Test
  public void testSetGetServiceName() {
    String serviceName = "dummyName"; //$NON-NLS-1$
    this.serviceCreator.setServiceName( serviceName );
    assertEquals( serviceName, this.serviceCreator.getServiceName() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceCreator#getName()} and
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceCreator#getServiceURL()}.
   */
  @Test
  public void testSetGetName() {
    String serviceName = "dummyName"; //$NON-NLS-1$
    this.serviceCreator.setName( serviceName );
    assertEquals( serviceName, this.serviceCreator.getName() );
  }

  /**
   * Test method for {@link EC2ServiceCreator#setServiceURL(java.lang.String)}
   * and {@link EC2ServiceCreator#getServiceURL()}.
   */
  @Test
  public void testSetServiceURL() {
    String serviceName = "dummyUrl"; //$NON-NLS-1$
    this.serviceCreator.setServiceURL( serviceName );
    assertEquals( serviceName, this.serviceCreator.getServiceURL() );
  }

}
