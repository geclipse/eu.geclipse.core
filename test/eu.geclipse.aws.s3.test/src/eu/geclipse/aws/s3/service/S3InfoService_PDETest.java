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

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IPath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.geclipse.aws.s3.test.util.AWSVoTestUtil;
import eu.geclipse.aws.s3.test.util.S3ServiceTestUtil;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;

/**
 * Test class for the {@link S3InfoService}.
 * 
 * @author Moritz Post
 */
public class S3InfoService_PDETest {

  /** The {@link S3InfoService} to run tests against. */
  private S3InfoService infoService;

  /** The parent of the {@link S3InfoService} under test. */
  private S3AWSService S3AWSService;

  /**
   * Creates an {@link S3InfoService} to test against.
   * 
   * @throws GridModelException
   */
  @Before
  public void setUp() throws GridModelException {
    this.S3AWSService = S3ServiceTestUtil.getS3AWSService();
    this.infoService = new S3InfoService( this.S3AWSService );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3InfoService#S3InfoService(eu.geclipse.aws.s3.service.S3AWSService)}.
   * 
   * @throws GridModelException
   */
  @Test
  public void testS3InfoService() throws GridModelException {
    GridModel.getVoManager().delete( AWSVoTestUtil.getAwsVo() );
    S3InfoService infoService = new S3InfoService( S3ServiceTestUtil.getS3AWSService() );
    Assert.assertNotNull( infoService );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3InfoService#getHostName()}.
   * 
   * @throws GridModelException
   */
  @Test
  public void testGetHostName() throws GridModelException {
    Assert.assertEquals( S3ServiceTestUtil.S3_URL,
                         this.infoService.getHostName() );
    this.S3AWSService.getProperties().setS3Url( null );
    Assert.assertNull( this.infoService.getHostName() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.service.S3InfoService#getURI()}.
   * 
   * @throws URISyntaxException
   * @throws GridModelException
   */
  @Test
  public void testGetURI() throws URISyntaxException, GridModelException {
    Assert.assertEquals( new URI( S3ServiceTestUtil.S3_URL ),
                         this.infoService.getURI() );
    this.S3AWSService.getProperties().setS3Url( null );
    Assert.assertNull( this.infoService.getURI() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3InfoService#getFileStore()}.
   */
  @Test
  public void testGetFileStore() {
    IFileStore fileStore = this.infoService.getFileStore();
    Assert.assertEquals( S3InfoService.STORAGE_NAME, fileStore.getName() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.service.S3InfoService#getName()}.
   */
  @Test
  public void testGetName() {
    Assert.assertEquals( S3InfoService.STORAGE_NAME, this.infoService.getName() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3InfoService#getParent()}.
   */
  @Test
  public void testGetParent() {
    IGridContainer parent = this.infoService.getParent();
    Assert.assertEquals( this.S3AWSService, parent );
  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.service.S3InfoService#getPath()}.
   */
  @Test
  public void testGetPath() {
    IPath path = this.infoService.getPath();
    Assert.assertEquals( S3InfoService.STORAGE_NAME, path.toFile().getName() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3InfoService#getResource()}.
   */
  @Test
  public void testGetResource() {
    Assert.assertNull( this.infoService.getResource() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.service.S3InfoService#isLocal()}.
   */
  @Test
  public void testIsLocal() {
    Assert.assertFalse( this.infoService.isLocal() );
  }

}
