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

package eu.geclipse.aws.s3;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.jets3t.service.model.S3Bucket;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.geclipse.aws.s3.service.S3AWSService;
import eu.geclipse.aws.s3.test.util.S3ServiceTestUtil;
import eu.geclipse.core.model.GridModelException;

/**
 * Test class for the {@link S3BucketStorage} class.
 * 
 * @author Moritz Post
 */
public class S3BucketStorage_PDETest {

  /** The name of the test bucket. */
  private static final String BUCKET_NAME = "BucketName"; //$NON-NLS-1$

  /** The bucket storage under test. */
  private S3BucketStorage bucketStorage;

  /** The parent service. */
  private S3AWSService service;

  /** The contained bucket. */
  private S3Bucket bucket;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    this.service = S3ServiceTestUtil.getS3AWSService();
    this.bucket = new S3Bucket( S3BucketStorage_PDETest.BUCKET_NAME );
    this.bucketStorage = new S3BucketStorage( this.service, this.bucket );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.S3BucketStorage#S3BucketStorage(eu.geclipse.aws.s3.service.S3AWSService, org.jets3t.service.model.S3Bucket)}.
   * 
   * @throws GridModelException
   */
  @Test
  public void testS3BucketStorage() throws GridModelException {
    S3AWSService s3AWSService = S3ServiceTestUtil.getS3AWSService();
    S3Bucket bucket = new S3Bucket( S3BucketStorage_PDETest.BUCKET_NAME );
    S3BucketStorage bucketStorage = new S3BucketStorage( s3AWSService, bucket );
    Assert.assertNotNull( bucketStorage );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.S3BucketStorage#getAccessTokens()}.
   */
  @Test
  public void testGetAccessTokens() {
    URI[] accessTokens = this.bucketStorage.getAccessTokens();
    String expectedName = "s3://" //$NON-NLS-1$
                          + IS3Constants.S3_ROOT
                          + "/" //$NON-NLS-1$
                          + this.bucketStorage.getName();
    Assert.assertEquals( expectedName, accessTokens[ 0 ].toString() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.S3BucketStorage#getName()}.
   */
  @Test
  public void testGetName() {
    Assert.assertEquals( S3BucketStorage_PDETest.BUCKET_NAME,
                         this.bucketStorage.getName() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.S3BucketStorage#getHostName()}.
   * 
   * @throws MalformedURLException
   */
  @Test
  public void testGetHostName() throws MalformedURLException {
    URL url = new URL( S3ServiceTestUtil.S3_URL );
    Assert.assertEquals( url.getHost(), this.bucketStorage.getHostName() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.S3BucketStorage#getURI()}.
   * 
   * @throws URISyntaxException
   */
  @Test
  public void testGetURI() throws URISyntaxException {
    URI uri = new URI( S3ServiceTestUtil.S3_URL + this.bucketStorage.getName() );
    Assert.assertEquals( uri, this.bucketStorage.getURI() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.S3BucketStorage#getFileStore()}.
   */
  @Test
  public void testGetFileStore() {
    Assert.assertNull( this.bucketStorage.getFileStore() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.S3BucketStorage#getParent()}.
   */
  @Test
  public void testGetParent() {
    Assert.assertEquals( this.service, this.bucketStorage.getParent() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.S3BucketStorage#getPath()}.
   */
  @Test
  public void testGetPath() {
    Assert.assertNull( this.bucketStorage.getPath() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.S3BucketStorage#getResource()}.
   */
  @Test
  public void testGetResource() {
    Assert.assertNull( this.bucketStorage.getResource() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.S3BucketStorage#isLocal()}.
   */
  @Test
  public void testIsLocal() {
    Assert.assertFalse( this.bucketStorage.isLocal() );
  }

}
