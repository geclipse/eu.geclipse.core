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

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IPath;
import org.junit.Before;
import org.junit.Test;

import eu.geclipse.aws.ec2.test.util.AWSVoTestUtil;
import eu.geclipse.aws.ec2.test.util.EC2ServiceTestUtil;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Test class for the {@link EC2InfoService}.
 * 
 * @author Moritz Post
 */
public class EC2InfoService_PDETest {

  /** The {@link EC2InfoService} to run tests against. */
  private EC2InfoService infoService;

  /** The parent of the {@link EC2InfoService} under test. */
  private EC2Service ec2Service;

  /**
   * Creates an {@link EC2InfoService} to test against.
   * 
   * @throws ProblemException
   */
  @Before
  public void setUp() throws ProblemException {
    this.ec2Service = EC2ServiceTestUtil.getEc2Service();
    this.infoService = new EC2InfoService( AWSVoTestUtil.getAwsVo(),
                                           this.ec2Service );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2InfoService#EC2InfoService(eu.geclipse.aws.vo.AWSVirtualOrganization, eu.geclipse.aws.ec2.service.EC2Service, eu.geclipse.aws.ec2.IEC2)}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testEC2InfoService() throws ProblemException {
    GridModel.getVoManager().delete( AWSVoTestUtil.getAwsVo() );
    EC2InfoService infoService = new EC2InfoService( AWSVoTestUtil.getAwsVo(),
                                                     EC2ServiceTestUtil.getEc2Service() );
    assertNotNull( infoService );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2InfoService#getHostName()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testGetHostName() throws ProblemException {
    assertEquals( EC2ServiceTestUtil.EC2_URL, this.infoService.getHostName() );
    this.ec2Service.getProperties().setEc2Url( null );
    assertNull( this.infoService.getHostName() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.service.EC2InfoService#getURI()}.
   * 
   * @throws URISyntaxException
   * @throws ProblemException
   */
  @Test
  public void testGetURI() throws URISyntaxException, ProblemException {
    assertEquals( new URI( EC2ServiceTestUtil.EC2_URL ),
                  this.infoService.getURI() );
    this.ec2Service.getProperties().setEc2Url( null );
    assertNull( this.infoService.getURI() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2InfoService#getFileStore()}.
   */
  @Test
  public void testGetFileStore() {
    IFileStore fileStore = this.infoService.getFileStore();
    assertEquals( EC2InfoService.STORAGE_NAME, fileStore.getName() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2InfoService#getName()}.
   */
  @Test
  public void testGetName() {
    assertEquals( EC2InfoService.STORAGE_NAME, this.infoService.getName() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2InfoService#getParent()}.
   */
  @Test
  public void testGetParent() {
    IGridContainer parent = this.infoService.getParent();
    assertEquals( this.ec2Service, parent );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2InfoService#getPath()}.
   */
  @Test
  public void testGetPath() {
    IPath path = this.infoService.getPath();
    assertEquals( EC2InfoService.STORAGE_NAME, path.toFile().getName() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2InfoService#getResource()}.
   */
  @Test
  public void testGetResource() {
    assertNull( this.infoService.getResource() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.service.EC2InfoService#isLocal()}.
   */
  @Test
  public void testIsLocal() {
    assertFalse( this.infoService.isLocal() );
  }

}
