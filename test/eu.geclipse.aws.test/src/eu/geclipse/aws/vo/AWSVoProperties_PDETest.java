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

package eu.geclipse.aws.vo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.MalformedURLException;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import eu.geclipse.aws.test.util.AWSVoTestUtil;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Unit tests for the {@link AWSVoProperties} class.
 * 
 * @author Moritz Post
 */
public class AWSVoProperties_PDETest {

  /** Default name for created {@link AWSVirtualOrganization}s. */
  private static final String VO_NAME = "MyAWSVo"; //$NON-NLS-1$

  /**
   * The {@link AWSVoCreator} used to create the {@link AWSVirtualOrganization}s
   * for the test cases.
   */
  private static AWSVoCreator awsVoCreator;

  /**
   * Get the {@link AWSVoCreator} for the test cases.
   * 
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpClass() throws Exception {
    AWSVoProperties_PDETest.awsVoCreator = AWSVoTestUtil.getAwsVoCreator();
    if( AWSVoProperties_PDETest.awsVoCreator == null ) {
      fail( "Could not obtain awsVoCreator" ); //$NON-NLS-1$
    }
  }

  /**
   * Setup the {@link AWSVoCreator} name.
   */
  @Before
  public void setUp() {
    if( AWSVoProperties_PDETest.awsVoCreator != null ) {
      AWSVoProperties_PDETest.awsVoCreator.setVoName( AWSVoProperties_PDETest.VO_NAME );
    }
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.vo.AWSVoProperties#AWSVoProperties(eu.geclipse.aws.vo.AWSVirtualOrganization, eu.geclipse.aws.vo.AWSVoCreator)}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testAWSVoPropertiesAWSVirtualOrganizationAWSVoCreator()
    throws ProblemException
  {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVoProperties_PDETest.awsVoCreator.create( null );
    assertNotNull( new AWSVoProperties( awsVo,
                                        AWSVoProperties_PDETest.awsVoCreator ) );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.vo.AWSVoProperties#AWSVoProperties(eu.geclipse.aws.vo.AWSVirtualOrganization)}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testAWSVoPropertiesAWSVirtualOrganization()
    throws ProblemException
  {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVoProperties_PDETest.awsVoCreator.create( null );
    assertNotNull( new AWSVoProperties( awsVo ) );
  }

  /**
   * Test method for {@link eu.geclipse.aws.vo.AWSVoProperties#load()}.
   * 
   * @throws ProblemException
   * @throws MalformedURLException
   */
  @Test
  @Ignore("Nothing is currently stored so we can not test if storing works.")
  public void testLoadAndSave() throws ProblemException {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVoProperties_PDETest.awsVoCreator.create( null );
    AWSVoProperties_PDETest.awsVoCreator.apply( awsVo );
    AWSVoProperties properties = awsVo.getProperties();
    // save to file store
    properties.save();

    // set new url but vo will be loaded from file store
    AWSVoProperties_PDETest.awsVoCreator.canCreate( awsVo.getFileStore() );

    AWSVirtualOrganization awsVo2 = ( AWSVirtualOrganization )AWSVoProperties_PDETest.awsVoCreator.create( null );
    AWSVoProperties properties2 = awsVo2.getProperties();
    assertNotNull( properties2 );

    // test if properties are correctly stored here
    // ...
  }

  /**
   * Test method for {@link eu.geclipse.aws.vo.AWSVoProperties#getFileStore()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testGetFileStore() throws ProblemException {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVoProperties_PDETest.awsVoCreator.create( null );
    IFileStore propsFileStore = awsVo.getProperties().getFileStore();

    assertNotNull( propsFileStore );
    assertTrue( propsFileStore.getParent()
      .getName()
      .equals( AWSVoProperties_PDETest.VO_NAME ) );
  }

  /**
   * Test method for {@link eu.geclipse.aws.vo.AWSVoProperties#getName()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testGetName() throws ProblemException {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVoProperties_PDETest.awsVoCreator.create( null );
    AWSVoProperties properties = awsVo.getProperties();
    assertTrue( properties.getName().equals( AWSVoProperties.STORAGE_NAME ) );
  }

  /**
   * Test method for {@link eu.geclipse.aws.vo.AWSVoProperties#getParent()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testGetParent() throws ProblemException {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVoProperties_PDETest.awsVoCreator.create( null );
    IGridContainer parent = awsVo.getProperties().getParent();
    assertEquals( awsVo, parent );
  }

  /**
   * Test method for {@link eu.geclipse.aws.vo.AWSVoProperties#getPath()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testGetPath() throws ProblemException {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVoProperties_PDETest.awsVoCreator.create( null );
    IPath path = awsVo.getProperties().getPath();
    assertNotNull( path );
    assertTrue( path.lastSegment().equals( AWSVoProperties.STORAGE_NAME ) );
  }

  /**
   * Test method for {@link eu.geclipse.aws.vo.AWSVoProperties#getResource()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testGetResource() throws ProblemException {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVoProperties_PDETest.awsVoCreator.create( null );
    IResource resource = awsVo.getProperties().getResource();
    assertNull( resource );
  }

  /**
   * Test method for {@link eu.geclipse.aws.vo.AWSVoProperties#isLocal()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testIsLocal() throws ProblemException {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVoProperties_PDETest.awsVoCreator.create( null );
    boolean local = awsVo.getProperties().isLocal();
    assertTrue( local );
  }

}
