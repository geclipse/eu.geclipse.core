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
import static org.junit.Assert.assertTrue;

import org.eclipse.core.filesystem.IFileStore;
import org.junit.Before;
import org.junit.Test;

import eu.geclipse.aws.ec2.test.util.EC2ServiceTestUtil;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Test class for the {@link EC2ServiceProperties} class.
 * 
 * @author Moritz Post
 */
public class EC2ServiceProperties_PDETest {

  /** The {@link EC2ServiceProperties} under test. */
  private EC2ServiceProperties ec2ServiceProperties;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    this.ec2ServiceProperties = new EC2ServiceProperties( EC2ServiceTestUtil.getEc2Service() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceProperties#EC2ServiceProperties(eu.geclipse.aws.ec2.service.EC2Service)}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testEC2ServicePropertiesEC2Service() throws ProblemException {
    new EC2ServiceProperties( EC2ServiceTestUtil.getEc2Service() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceProperties#EC2ServiceProperties(eu.geclipse.aws.ec2.service.EC2Service, eu.geclipse.aws.ec2.service.EC2ServiceCreator)}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testEC2ServicePropertiesEC2ServiceEC2ServiceCreator()
    throws ProblemException
  {
    new EC2ServiceProperties( EC2ServiceTestUtil.getEc2Service(),
                              EC2ServiceTestUtil.getEC2ServiceCreator() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceProperties#load()} and
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceProperties#save()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testLoadSave() throws ProblemException {
    String dummyUrl = "testUrl"; //$NON-NLS-1$
    this.ec2ServiceProperties.setEc2Url( dummyUrl );
    this.ec2ServiceProperties.save();

    EC2ServiceProperties serviceProperties = EC2ServiceTestUtil.getEc2Service()
      .getProperties();
    serviceProperties.load();
    assertEquals( dummyUrl, serviceProperties.getEc2Url() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceProperties#getFileStore()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testGetFileStore() throws ProblemException {
    EC2Service ec2Service = EC2ServiceTestUtil.getEc2Service();
    EC2ServiceProperties properties = ec2Service.getProperties();

    IFileStore propertiesfileStore = properties.getFileStore();
    assertNotNull( propertiesfileStore );
    assertEquals( ec2Service.getFileStore(), propertiesfileStore.getParent() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceProperties#getName()}.
   */
  @Test
  public void testGetName() {
    assertEquals( EC2ServiceProperties.STORAGE_NAME,
                  this.ec2ServiceProperties.getName() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceProperties#getParent()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testGetParent() throws ProblemException {
    EC2Service ec2Service = EC2ServiceTestUtil.getEc2Service();
    EC2ServiceProperties properties = ec2Service.getProperties();
    IGridContainer parent = properties.getParent();
    assertNotNull( parent );
    assertEquals( ec2Service, parent );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceProperties#getPath()}.
   */
  @Test
  public void testGetPath() {
    assertNotNull( this.ec2ServiceProperties.getPath() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceProperties#getResource()}.
   */
  @Test
  public void testGetResource() {
    assertNull( this.ec2ServiceProperties.getResource() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceProperties#isLocal()}.
   */
  @Test
  public void testIsLocal() {
    assertTrue( this.ec2ServiceProperties.isLocal() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceProperties#getEc2Url()} and
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceProperties#setEc2Url(java.lang.String)}.
   */
  @Test
  public void testGetSetEc2Url() {
    String dummyEc2Url = "dummy"; //$NON-NLS-1$
    this.ec2ServiceProperties.setEc2Url( dummyEc2Url );
    String ec2Url = this.ec2ServiceProperties.getEc2Url();
    assertEquals( dummyEc2Url, ec2Url );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceProperties#getServiceName()}
   * and
   * {@link eu.geclipse.aws.ec2.service.EC2ServiceProperties#setServiceName(java.lang.String)}.
   */
  @Test
  public void testGetServiceName() {
    String dummyServiceName = "dummy"; //$NON-NLS-1$
    this.ec2ServiceProperties.setServiceName( dummyServiceName );
    String serviceName = this.ec2ServiceProperties.getServiceName();
    assertEquals( dummyServiceName, serviceName );
  }

}
