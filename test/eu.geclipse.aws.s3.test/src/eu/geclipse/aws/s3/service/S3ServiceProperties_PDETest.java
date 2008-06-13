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

package eu.geclipse.aws.s3.service;

import org.eclipse.core.filesystem.IFileStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.geclipse.aws.s3.test.util.S3ServiceTestUtil;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;

/**
 * Test class for the {@link EC2ServiceProperties} class.
 * 
 * @author Moritz Post
 */
public class S3ServiceProperties_PDETest {

  /** The {@link EC2ServiceProperties} under test. */
  private S3ServiceProperties s3ServiceProperties;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    this.s3ServiceProperties = new S3ServiceProperties( S3ServiceTestUtil.getS3AWSService() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3ServiceProperties#S3ServiceProperties(eu.geclipse.aws.s3.service.EC2Service)}.
   * 
   * @throws GridModelException
   */
  @Test
  public void testS3ServicePropertiesEC2Service() throws GridModelException {
    new S3ServiceProperties( S3ServiceTestUtil.getS3AWSService() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3ServiceProperties#S3ServiceProperties(eu.geclipse.aws.s3.service.EC2Service, eu.geclipse.aws.s3.service.EC2ServiceCreator)}.
   * 
   * @throws GridModelException
   */
  @Test
  public void testS3ServicePropertiesEC2ServiceEC2ServiceCreator()
    throws GridModelException
  {
    new S3ServiceProperties( S3ServiceTestUtil.getS3AWSService(),
                             S3ServiceTestUtil.getS3AWSServiceCreator() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3ServiceProperties#load()} and
   * {@link eu.geclipse.aws.s3.service.S3ServiceProperties#save()}.
   * 
   * @throws GridModelException
   */
  @Test
  public void testLoadSave() throws GridModelException {
    String dummyUrl = "testUrl"; //$NON-NLS-1$
    this.s3ServiceProperties.setS3Url( dummyUrl );
    this.s3ServiceProperties.save();

    S3ServiceProperties serviceProperties = S3ServiceTestUtil.getS3AWSService()
      .getProperties();
    serviceProperties.load();
    Assert.assertEquals( dummyUrl, serviceProperties.getS3Url() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3ServiceProperties#getFileStore()}.
   * 
   * @throws GridModelException
   */
  @Test
  public void testGetFileStore() throws GridModelException {
    S3AWSService s3Service = S3ServiceTestUtil.getS3AWSService();
    S3ServiceProperties properties = s3Service.getProperties();

    IFileStore propertiesfileStore = properties.getFileStore();
    Assert.assertNotNull( propertiesfileStore );
    Assert.assertEquals( s3Service.getFileStore(),
                         propertiesfileStore.getParent() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3ServiceProperties#getName()}.
   */
  @Test
  public void testGetName() {
    Assert.assertEquals( S3ServiceProperties.STORAGE_NAME,
                         this.s3ServiceProperties.getName() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3ServiceProperties#getParent()}.
   * 
   * @throws GridModelException
   */
  @Test
  public void testGetParent() throws GridModelException {
    S3AWSService s3Service = S3ServiceTestUtil.getS3AWSService();
    S3ServiceProperties properties = s3Service.getProperties();
    IGridContainer parent = properties.getParent();
    Assert.assertNotNull( parent );
    Assert.assertEquals( s3Service, parent );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3ServiceProperties#getPath()}.
   */
  @Test
  public void testGetPath() {
    Assert.assertNotNull( this.s3ServiceProperties.getPath() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3ServiceProperties#getResource()}.
   */
  @Test
  public void testGetResource() {
    Assert.assertNull( this.s3ServiceProperties.getResource() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3ServiceProperties#isLocal()}.
   */
  @Test
  public void testIsLocal() {
    Assert.assertTrue( this.s3ServiceProperties.isLocal() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3ServiceProperties#getEc2Url()} and
   * {@link eu.geclipse.aws.s3.service.S3ServiceProperties#setEc2Url(java.lang.String)}.
   */
  @Test
  public void testGetSetS3Url() {
    String dummyEc2Url = "dummy"; //$NON-NLS-1$
    this.s3ServiceProperties.setS3Url( dummyEc2Url );
    String s3Url = this.s3ServiceProperties.getS3Url();
    Assert.assertEquals( dummyEc2Url, s3Url );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3ServiceProperties#getServiceName()} and
   * {@link eu.geclipse.aws.s3.service.S3ServiceProperties#setServiceName(java.lang.String)}.
   */
  @Test
  public void testGetServiceName() {
    String dummyServiceName = "dummy"; //$NON-NLS-1$
    this.s3ServiceProperties.setServiceName( dummyServiceName );
    String serviceName = this.s3ServiceProperties.getServiceName();
    Assert.assertEquals( dummyServiceName, serviceName );
  }

}
