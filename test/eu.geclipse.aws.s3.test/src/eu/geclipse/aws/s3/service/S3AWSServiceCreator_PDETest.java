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

import eu.geclipse.aws.s3.test.util.AWSVoTestUtil;
import eu.geclipse.aws.s3.test.util.S3ServiceTestUtil;
import eu.geclipse.core.model.GridModelException;

/**
 * Test class for the {@link S3AWSServiceCreator} class.
 * 
 * @author Moritz Post
 */
public class S3AWSServiceCreator_PDETest {

  /** The {@link S3AWSServiceCreator} class under test. */
  private S3AWSServiceCreator serviceCreator;

  /**
   * Prepares the required {@link S3AWSServiceCreator}s.
   */
  @Before
  public void setUp() {
    this.serviceCreator = S3ServiceTestUtil.getS3AWSServiceCreator();
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3AWSServiceCreator#internalCanCreate(java.lang.Object)}.
   */
  @Test
  public void testInternalCanCreate() {
    Assert.assertTrue( this.serviceCreator.internalCanCreate( S3ServiceTestUtil.S3_URL ) );
    Assert.assertFalse( this.serviceCreator.internalCanCreate( "dummy" ) ); //$NON-NLS-1$
    Assert.assertFalse( this.serviceCreator.internalCanCreate( "" ) ); //$NON-NLS-1$
    Assert.assertFalse( this.serviceCreator.internalCanCreate( null ) );
    Assert.assertFalse( this.serviceCreator.internalCanCreate( new Integer( 0 ) ) );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3AWSServiceCreator#canCreate(java.lang.Class)}.
   */
  @Test
  public void testCanCreateClassOfQextendsIGridElement() {
    Assert.assertTrue( this.serviceCreator.canCreate( S3AWSService.class ) );
    Assert.assertFalse( this.serviceCreator.canCreate( S3AWSServiceCreator.class ) );
    Assert.assertFalse( this.serviceCreator.canCreate( String.class ) );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3AWSServiceCreator#create(eu.geclipse.core.model.IGridContainer)}.
   * 
   * @throws GridModelException
   */
  @Test
  public void testCreate() throws GridModelException {
    S3AWSService S3AWSService = ( S3AWSService )this.serviceCreator.create( null );
    Assert.assertNull( S3AWSService );
    S3AWSService = ( S3AWSService )this.serviceCreator.create( AWSVoTestUtil.getAwsVo() );
    Assert.assertNotNull( S3AWSService );
    String s3Url = S3AWSService.getProperties().getS3Url();
    Assert.assertEquals( S3ServiceTestUtil.S3_URL, s3Url );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.internal.service.S3AWSServiceCreator#canCreate(org.eclipse.core.filesystem.IFileStore)}.
   * 
   * @throws GridModelException
   */
  @Test
  public void testCanCreateIFileStore() throws GridModelException {
    S3AWSService S3AWSService = S3ServiceTestUtil.getS3AWSService();
    Assert.assertNotNull( S3AWSService );
    IFileStore fileStore = S3AWSService.getFileStore();
    Assert.assertFalse( this.serviceCreator.canCreate( fileStore ) );
    S3AWSService.save();
    fileStore = S3AWSService.getFileStore();
    Assert.assertTrue( this.serviceCreator.canCreate( fileStore ) );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3AWSServiceCreator#getExtensionID()}.
   */
  @Test
  public void testGetExtensionID() {
    Assert.assertEquals( "eu.geclipse.aws.s3.service.s3AWSServiceCreator", //$NON-NLS-1$
                         this.serviceCreator.getExtensionID() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3AWSServiceCreator#getServiceName()} and
   * {@link eu.geclipse.aws.s3.service.S3AWSServiceCreator#setServiceName(java.lang.String)}.
   */
  @Test
  public void testSetGetServiceName() {
    String serviceName = "dummyName"; //$NON-NLS-1$
    this.serviceCreator.setServiceName( serviceName );
    Assert.assertEquals( serviceName, this.serviceCreator.getServiceName() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3AWSServiceCreator#getName()} and
   * {@link eu.geclipse.aws.s3.service.S3AWSServiceCreator#getServiceURL()}.
   */
  @Test
  public void testSetGetName() {
    String serviceName = "dummyName"; //$NON-NLS-1$
    this.serviceCreator.setName( serviceName );
    Assert.assertEquals( serviceName, this.serviceCreator.getName() );
  }

  /**
   * Test method for {@link S3AWSServiceCreator#setServiceURL(java.lang.String)}
   * and {@link S3AWSServiceCreator#getServiceURL()}.
   */
  @Test
  public void testSetServiceURL() {
    String serviceName = "dummyUrl"; //$NON-NLS-1$
    this.serviceCreator.setServiceURL( serviceName );
    Assert.assertEquals( serviceName, this.serviceCreator.getServiceURL() );
  }

}
