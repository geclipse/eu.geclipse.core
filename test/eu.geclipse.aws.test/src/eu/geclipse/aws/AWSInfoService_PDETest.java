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

package eu.geclipse.aws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Before;
import org.junit.Test;

import eu.geclipse.aws.test.util.AWSVoTestUtil;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.aws.vo.AWSVoCreator;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Test class for the {@link AWSInfoService}.
 * 
 * @author Moritz Post
 */
public class AWSInfoService_PDETest {

  /** The {@link IVirtualOrganization} this {@link IGridInfoService} is bound to. */
  private AWSVirtualOrganization awsVo;

  /** The local {@link AWSInfoService} to run tests against. */
  private AWSInfoService infoService;

  /**
   * Create an {@link AWSInfoService} to run tests against.
   * 
   * @throws ProblemException
   */
  @Before
  public void setUp() throws ProblemException {
    this.awsVo = AWSVoTestUtil.getAwsVo();
    assertNotNull( this.awsVo );
    this.infoService = new AWSInfoService( this.awsVo );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.AWSInfoService#AWSInfoService(eu.geclipse.aws.vo.AWSVirtualOrganization)}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testAWSInfoService() throws ProblemException {
    AWSVirtualOrganization awsVo = AWSVoTestUtil.getAwsVo();
    assertNotNull( awsVo );
    AWSInfoService infoService = new AWSInfoService( awsVo );
    assertEquals( awsVo.getTypeName(), infoService.getVoType() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.AWSInfoService#getStore()}.
   * <p>
   * This method is not applicable for the AWS implementation.
   */
  @Test
  public void testGetStore() {
    assertNull( this.infoService.getStore() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.AWSInfoService#getTopTreeElements()}.
   * <p>
   * This method is not applicable for the AWS implementation.
   */
  @Test
  public void testGetTopTreeElements() {
    assertNull( this.infoService.getTopTreeElements() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.AWSInfoService#getVoType()}.
   */
  @Test
  public void testGetVoType() {
    assertEquals( this.awsVo.getTypeName(), this.infoService.getVoType() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.AWSInfoService#scheduleFetch(org.eclipse.core.runtime.IProgressMonitor)}.
   * <p>
   * This method is not doing anything. Therefore not testable. Test method just
   * invokes the method under test to see if an exception is thrown.
   */
  @Test
  public void testScheduleFetch() {
    this.infoService.scheduleFetch( new NullProgressMonitor() );
    // no asserting possible since no side effects are achieved
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.AWSInfoService#setVO(eu.geclipse.core.model.IVirtualOrganization)}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testSetVO() throws ProblemException {
    assertEquals( this.awsVo.getTypeName(), this.infoService.getVoType() );

    // create new AWSVo
    AWSVoCreator awsVoCreator = AWSVoTestUtil.getAwsVoCreator();
    String dummyVoName = "AWSInfoService.setVo()-dummyVoName"; //$NON-NLS-1$
    awsVoCreator.setVoName( dummyVoName );
    AWSVirtualOrganization awsVo2 = ( AWSVirtualOrganization )awsVoCreator.create( null );

    // could the new vo be set?
    this.infoService.setVO( awsVo2 );
    assertEquals( awsVo2, this.infoService.getParent() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.AWSInfoService#getHostName()}.
   */
  @Test
  public void testGetHostName() {
    assertNull( this.infoService.getHostName() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.AWSInfoService#getURI()}.
   */
  @Test
  public void testGetURI() {
    URI uri = this.infoService.getURI();
    assertNotNull( uri );
  }

  /**
   * Test method for {@link eu.geclipse.aws.AWSInfoService#getFileStore()}.
   */
  @Test
  public void testGetFileStore() {
    IFileStore fileStore = this.infoService.getFileStore();
    assertEquals( AWSInfoService.STORAGE_NAME, fileStore.getName() );
    assertEquals( this.awsVo.getFileStore(), fileStore.getParent() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.AWSInfoService#getName()}.
   */
  @Test
  public void testGetName() {
    assertNotNull( this.infoService.getName() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.AWSInfoService#getParent()}.
   */
  @Test
  public void testGetParent() {
    assertEquals( this.awsVo, this.infoService.getParent() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.AWSInfoService#getPath()}.
   */
  @Test
  public void testGetPath() {
    IPath path = this.infoService.getPath();
    assertEquals( AWSInfoService.STORAGE_NAME, path.toFile().getName() );
    assertEquals( this.awsVo.getPath().toOSString(), path.toFile().getParent() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.AWSInfoService#getResource()}.
   */
  @Test
  public void testGetResource() {
    assertNull( this.infoService.getResource() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.AWSInfoService#isLocal()}.
   */
  @Test
  public void testIsLocal() {
    assertTrue( this.infoService.isLocal() );
  }

}
