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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IPath;
import org.junit.Before;
import org.junit.Test;

import eu.geclipse.aws.ec2.test.util.AWSVoTestUtil;
import eu.geclipse.aws.ec2.test.util.EC2ServiceTestUtil;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Test class for the {@link EC2Service} entity.
 * 
 * @author Moritz Post
 */
public class EC2Service_PDETest {

  /** Dummy name used for testing. */
  private static final String DUMMY_NAME = "dummy-name"; //$NON-NLS-1$

  /** The {@link EC2Service} under test. */
  private EC2Service ec2Service;

  /** The {@link EC2ServiceCreator} creates the {@link EC2Service} under test. */
  private EC2ServiceCreator serviceCreator;

  /**
   * The {@link AWSVirtualOrganization} acting as the parent of this
   * {@link EC2Service}.
   */
  private AWSVirtualOrganization awsVo;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    this.serviceCreator = EC2ServiceTestUtil.getEC2ServiceCreator();
    this.awsVo = AWSVoTestUtil.getAwsVo();
    this.ec2Service = ( EC2Service )this.serviceCreator.create( this.awsVo );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2Service#EC2Service(eu.geclipse.aws.ec2.service.EC2ServiceCreator, eu.geclipse.aws.vo.AWSVirtualOrganization)}
   * .
   * 
   * @throws ProblemException
   */
  @Test
  public void testEC2ServiceEC2ServiceCreatorAWSVirtualOrganization()
    throws ProblemException
  {
    List<IGridElementCreator> voCreators = GridModel.getCreatorRegistry()
      .getCreators();

    EC2ServiceCreator ec2ServiceCreator = null;
    for( IGridElementCreator gridElementCreator : voCreators ) {
      if( gridElementCreator instanceof EC2ServiceCreator ) {
        ec2ServiceCreator = ( EC2ServiceCreator )gridElementCreator;
        ec2ServiceCreator.setServiceName( EC2ServiceTestUtil.EC2_SERVICE_NAME );
        ec2ServiceCreator.setServiceURL( EC2ServiceTestUtil.EC2_URL );
      }
    }

    EC2Service service = new EC2Service( ec2ServiceCreator,
                                         AWSVoTestUtil.getAwsVo() );
    assertNotNull( service );
    assertEquals( EC2ServiceTestUtil.EC2_SERVICE_NAME, service.getName() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2Service#canContain(eu.geclipse.core.model.IGridElement)}
   * .
   * 
   * @throws ProblemException
   */
  @Test
  public void testCanContain() throws ProblemException {
    EC2ServiceProperties serviceProperties = new EC2ServiceProperties( this.ec2Service );
    assertTrue( this.ec2Service.canContain( serviceProperties ) );

    EC2InfoService infoService = new EC2InfoService( AWSVoTestUtil.getAwsVo(),
                                                     this.ec2Service );
    assertTrue( this.ec2Service.canContain( infoService ) );

    assertFalse( this.ec2Service.canContain( AWSVoTestUtil.getAwsVo() ) );
    assertFalse( this.ec2Service.canContain( AWSVoTestUtil.getAwsVo()
      .getProperties() ) );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2Service#apply(eu.geclipse.aws.ec2.service.EC2ServiceCreator)}
   * .
   * 
   * @throws ProblemException
   */
  @Test
  public void testApply() throws ProblemException {
    assertEquals( EC2ServiceTestUtil.EC2_SERVICE_NAME,
                  this.ec2Service.getName() );

    EC2ServiceCreator serviceCreator = EC2ServiceTestUtil.getEC2ServiceCreator();
    serviceCreator.setName( EC2Service_PDETest.DUMMY_NAME );

    this.ec2Service.apply( serviceCreator );

    assertEquals( EC2Service_PDETest.DUMMY_NAME, this.ec2Service.getName() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2Service#getHostName()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testGetHostName() throws ProblemException {
    assertEquals( EC2ServiceTestUtil.EC2_URL, this.ec2Service.getHostName() );
    this.ec2Service.getProperties().setEc2Url( null );
    assertNull( this.ec2Service.getHostName() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.service.EC2Service#getURI()}.
   * 
   * @throws URISyntaxException
   * @throws ProblemException
   */
  @Test
  public void testGetURI() throws URISyntaxException, ProblemException {
    assertEquals( new URI( EC2ServiceTestUtil.EC2_URL ),
                  this.ec2Service.getURI() );
    this.ec2Service.getProperties().setEc2Url( null );
    assertNull( this.ec2Service.getURI() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2Service#getFileStore()}.
   */
  @Test
  public void testGetFileStore() {
    IFileStore fileStore = this.ec2Service.getFileStore();
    assertEquals( EC2Service.STORAGE_NAME, fileStore.getName() );

  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.service.EC2Service#getName()}.
   */
  @Test
  public void testGetName() {
    assertEquals( EC2ServiceTestUtil.EC2_SERVICE_NAME,
                  this.ec2Service.getName() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.service.EC2Service#getParent()}.
   */
  @Test
  public void testGetParent() {
    IGridContainer parent = this.ec2Service.getParent();
    assertEquals( this.awsVo, parent );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.service.EC2Service#getPath()}.
   */
  @Test
  public void testGetPath() {
    IPath path = this.ec2Service.getPath();
    assertEquals( EC2Service.STORAGE_NAME, path.toFile().getName() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2Service#getResource()}.
   */
  @Test
  public void testGetResource() {
    assertNull( this.ec2Service.getResource() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.service.EC2Service#isLocal()}.
   */
  @Test
  public void testIsLocal() {
    assertTrue( this.ec2Service.isLocal() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.service.EC2Service#isLazy()}.
   */
  @Test
  public void testIsLazy() {
    assertFalse( this.ec2Service.isLazy() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2Service#equals(java.lang.Object)}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testEqualsObject() throws ProblemException {
    assertTrue( this.ec2Service.equals( this.ec2Service ) );

    // test against newly created EC2Service with the same name
    EC2Service ec2Service2 = EC2ServiceTestUtil.getEc2Service();
    assertTrue( this.ec2Service.equals( ec2Service2 ) );

    // test against newly created EC2Service with different name
    EC2ServiceCreator serviceCreator2 = EC2ServiceTestUtil.getEC2ServiceCreator();
    serviceCreator2.setServiceName( EC2Service_PDETest.DUMMY_NAME );
    EC2Service ec2Service3 = ( EC2Service )serviceCreator2.create( AWSVoTestUtil.getAwsVo() );
    ec2Service3.getProperties().setServiceName( EC2Service_PDETest.DUMMY_NAME );
    assertFalse( this.ec2Service.equals( ec2Service3 ) );

    assertFalse( this.ec2Service.equals( new Object() ) );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2Service#getProperties()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testGetProperties() throws ProblemException {
    assertNotNull( this.ec2Service.getProperties() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2Service#getInfoService()}.
   */
  @Test
  public void testGetInfoService() {
    assertNotNull( this.ec2Service.getInfoService() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.service.EC2Service#load()} and
   * {@link eu.geclipse.aws.ec2.service.EC2Service#save()}..
   * 
   * @throws ProblemException
   */
  @Test
  public void testLoadAndSave() throws ProblemException {
    assertEquals( EC2ServiceTestUtil.EC2_SERVICE_NAME,
                  this.ec2Service.getProperties().getServiceName() );

    this.ec2Service.save();

    this.ec2Service.getProperties()
      .setServiceName( EC2Service_PDETest.DUMMY_NAME );

    assertEquals( EC2Service_PDETest.DUMMY_NAME,
                  this.ec2Service.getProperties().getServiceName() );

    this.ec2Service.load();

    assertEquals( EC2ServiceTestUtil.EC2_SERVICE_NAME,
                  this.ec2Service.getProperties().getServiceName() );
  }
}
