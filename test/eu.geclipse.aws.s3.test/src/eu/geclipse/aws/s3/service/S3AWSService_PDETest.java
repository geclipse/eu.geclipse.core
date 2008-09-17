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
import java.util.List;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IPath;
import org.jets3t.service.S3Service;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.geclipse.aws.s3.test.util.AWSVoTestUtil;
import eu.geclipse.aws.s3.test.util.S3ServiceTestUtil;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Test class for the {@link S3Service} entity.
 * 
 * @author Moritz Post
 */
public class S3AWSService_PDETest {

  /** Dummy name used for testing. */
  private static final String DUMMY_NAME = "dummy-name"; //$NON-NLS-1$

  /** The {@link S3Service} under test. */
  private S3AWSService s3AWSService;

  /**
   * The {@link S3AWSServiceCreator} creates the {@link s3AWSService} under
   * test.
   */
  private S3AWSServiceCreator serviceCreator;

  /**
   * The {@link AWSVirtualOrganization} acting as the parent of this
   * {@link s3AWSService}.
   */
  private AWSVirtualOrganization awsVo;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    this.serviceCreator = S3ServiceTestUtil.getS3AWSServiceCreator();
    this.awsVo = AWSVoTestUtil.getAwsVo();
    this.s3AWSService = ( S3AWSService )this.serviceCreator.create( this.awsVo );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3AWSService#S3AWSService(eu.geclipse.aws.s3.service.S3AWSServiceCreator, eu.geclipse.aws.vo.AWSVirtualOrganization)}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testS3AWSServiceS3AWSServiceCreatorAWSVirtualOrganization()
    throws ProblemException
  {
    List<IGridElementCreator> voCreators = GridModel.getCreatorRegistry().getCreators();

    S3AWSServiceCreator S3AWSServiceCreator = null;
    for( IGridElementCreator gridElementCreator : voCreators ) {
      if( gridElementCreator instanceof S3AWSServiceCreator ) {
        S3AWSServiceCreator = ( S3AWSServiceCreator )gridElementCreator;
        S3AWSServiceCreator.setServiceName( S3ServiceTestUtil.S3_SERVICE_NAME );
        S3AWSServiceCreator.setServiceURL( S3ServiceTestUtil.S3_URL );
      }
    }

    S3AWSService service = new S3AWSService( S3AWSServiceCreator,
                                             AWSVoTestUtil.getAwsVo() );
    Assert.assertNotNull( service );
    Assert.assertEquals( S3ServiceTestUtil.S3_SERVICE_NAME, service.getName() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3AWSService#canContain(eu.geclipse.core.model.IGridElement)}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testCanContain() throws ProblemException {
    S3ServiceProperties serviceProperties = new S3ServiceProperties( this.s3AWSService );
    Assert.assertTrue( this.s3AWSService.canContain( serviceProperties ) );

    S3InfoService infoService = new S3InfoService( this.s3AWSService );
    Assert.assertTrue( this.s3AWSService.canContain( infoService ) );

    Assert.assertFalse( this.s3AWSService.canContain( AWSVoTestUtil.getAwsVo() ) );
    Assert.assertFalse( this.s3AWSService.canContain( AWSVoTestUtil.getAwsVo()
      .getProperties() ) );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3AWSService#apply(eu.geclipse.aws.s3.service.S3AWSServiceCreator)}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testApply() throws ProblemException {
    Assert.assertEquals( S3ServiceTestUtil.S3_SERVICE_NAME,
                         this.s3AWSService.getName() );

    S3AWSServiceCreator serviceCreator = S3ServiceTestUtil.getS3AWSServiceCreator();
    serviceCreator.setName( S3AWSService_PDETest.DUMMY_NAME );

    this.s3AWSService.apply( serviceCreator );

    Assert.assertEquals( S3AWSService_PDETest.DUMMY_NAME,
                         this.s3AWSService.getName() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3AWSService#getHostName()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testGetHostName() throws ProblemException {
    Assert.assertEquals( S3ServiceTestUtil.S3_URL,
                         this.s3AWSService.getHostName() );
    this.s3AWSService.getProperties().setS3Url( null );
    Assert.assertNull( this.s3AWSService.getHostName() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.service.S3AWSService#getURI()}.
   * 
   * @throws URISyntaxException
   * @throws ProblemException
   */
  @Test
  public void testGetURI() throws URISyntaxException, ProblemException {
    Assert.assertEquals( new URI( S3ServiceTestUtil.S3_URL ),
                         this.s3AWSService.getURI() );
    this.s3AWSService.getProperties().setS3Url( null );
    Assert.assertNull( this.s3AWSService.getURI() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3AWSService#getFileStore()}.
   */
  @Test
  public void testGetFileStore() {
    IFileStore fileStore = this.s3AWSService.getFileStore();
    Assert.assertEquals( S3AWSService.STORAGE_NAME, fileStore.getName() );

  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.service.S3AWSService#getName()}.
   */
  @Test
  public void testGetName() {
    Assert.assertEquals( S3ServiceTestUtil.S3_SERVICE_NAME,
                         this.s3AWSService.getName() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.service.S3AWSService#getParent()}.
   */
  @Test
  public void testGetParent() {
    IGridContainer parent = this.s3AWSService.getParent();
    Assert.assertEquals( this.awsVo, parent );
  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.service.S3AWSService#getPath()}.
   */
  @Test
  public void testGetPath() {
    IPath path = this.s3AWSService.getPath();
    Assert.assertEquals( S3AWSService.STORAGE_NAME, path.toFile().getName() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3AWSService#getResource()}.
   */
  @Test
  public void testGetResource() {
    Assert.assertNull( this.s3AWSService.getResource() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.service.S3AWSService#isLocal()}.
   */
  @Test
  public void testIsLocal() {
    Assert.assertTrue( this.s3AWSService.isLocal() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.service.S3AWSService#isLazy()}.
   */
  @Test
  public void testIsLazy() {
    Assert.assertFalse( this.s3AWSService.isLazy() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3AWSService#equals(java.lang.Object)}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testEqualsObject() throws ProblemException {
    Assert.assertTrue( this.s3AWSService.equals( this.s3AWSService ) );

    // test against newly created s3AWSService with the same name
    S3AWSService S3AWSService2 = S3ServiceTestUtil.getS3AWSService();
    Assert.assertTrue( this.s3AWSService.equals( S3AWSService2 ) );

    // test against newly created s3AWSService with different name
    S3AWSServiceCreator serviceCreator2 = S3ServiceTestUtil.getS3AWSServiceCreator();
    serviceCreator2.setServiceName( S3AWSService_PDETest.DUMMY_NAME );
    S3AWSService S3AWSService3 = ( S3AWSService )serviceCreator2.create( AWSVoTestUtil.getAwsVo() );
    S3AWSService3.getProperties()
      .setServiceName( S3AWSService_PDETest.DUMMY_NAME );
    Assert.assertFalse( this.s3AWSService.equals( S3AWSService3 ) );

    Assert.assertFalse( this.s3AWSService.equals( new Object() ) );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3AWSService#getProperties()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testGetProperties() throws ProblemException {
    Assert.assertNotNull( this.s3AWSService.getProperties() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3AWSService#getInfoService()}.
   */
  @Test
  public void testGetInfoService() {
    Assert.assertNotNull( this.s3AWSService.getInfoService() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.s3.service.S3AWSService#load()} and
   * {@link eu.geclipse.aws.s3.service.S3AWSService#save()}..
   * 
   * @throws ProblemException
   */
  @Test
  public void testLoadAndSave() throws ProblemException {
    Assert.assertEquals( S3ServiceTestUtil.S3_SERVICE_NAME,
                         this.s3AWSService.getProperties().getServiceName() );

    this.s3AWSService.save();

    this.s3AWSService.getProperties()
      .setServiceName( S3AWSService_PDETest.DUMMY_NAME );

    Assert.assertEquals( S3AWSService_PDETest.DUMMY_NAME,
                         this.s3AWSService.getProperties().getServiceName() );

    this.s3AWSService.load();

    Assert.assertEquals( S3ServiceTestUtil.S3_SERVICE_NAME,
                         this.s3AWSService.getProperties().getServiceName() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.service.S3AWSService#getSupportedResources()}.
   */
  @Test
  public void testGetSupportedResources() {
    Assert.assertNull( this.s3AWSService.getSupportedResources() );
  }

}
