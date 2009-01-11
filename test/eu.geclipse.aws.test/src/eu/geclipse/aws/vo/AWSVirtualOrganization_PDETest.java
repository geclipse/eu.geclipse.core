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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.aws.test.util.AWSVoTestUtil;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridResourceCategory;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Test cases for {@link AWSVirtualOrganization}.
 * 
 * @author Moritz Post
 */
public class AWSVirtualOrganization_PDETest {

  /** Default name for created {@link AWSVirtualOrganization}s. */
  private static final String VO_NAME = "MyAWSVo"; //$NON-NLS-1$

  /** Another name for a vo used to differentiate from the default name. */
  private static final String VO_NAME_2 = "MySecondAWSVo"; //$NON-NLS-1$

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
    AWSVirtualOrganization_PDETest.awsVoCreator = AWSVoTestUtil.getAwsVoCreator();
    if( AWSVirtualOrganization_PDETest.awsVoCreator == null ) {
      fail( "Could not obtain awsVoCreator" ); //$NON-NLS-1$
    }
  }

  /**
   * Setup the {@link AWSVoCreator} name.
   */
  @Before
  public void setUp() {
    if( AWSVirtualOrganization_PDETest.awsVoCreator != null ) {
      AWSVirtualOrganization_PDETest.awsVoCreator.setVoName( AWSVirtualOrganization_PDETest.VO_NAME );
    }
  }

  /**
   * Test method for {@link AWSVirtualOrganization#AWSVirtualOrganization()}.
   */
  @Test
  public void testAWSVirtualOrganization() {
    // trivial default constructor
    assertNotNull( new AWSVirtualOrganization() );
  }

  /**
   * Test method for
   * {@link AWSVirtualOrganization#AWSVirtualOrganization(org.eclipse.core.filesystem.IFileStore)}.
   */
  @Test
  public void testAWSVirtualOrganizationIFileStore() {
    IFileStore voManagerFileStore = GridModel.getVoManager().getFileStore();
    assertNotNull( new AWSVirtualOrganization( voManagerFileStore ) );
    voManagerFileStore = null;
    assertNotNull( new AWSVirtualOrganization( voManagerFileStore ) );
  }

  /**
   * Test method for
   * {@link AWSVirtualOrganization#AWSVirtualOrganization(eu.geclipse.aws.vo.AWSVoCreator)}.
   */
  @Test
  public void testAWSVirtualOrganizationAWSVoCreator() {
    assertNotNull( new AWSVirtualOrganization( AWSVirtualOrganization_PDETest.awsVoCreator ) );
  }

  /**
   * Test method for
   * {@link AWSVirtualOrganization#canContain(eu.geclipse.core.model.IGridElement)}.
   */
  @Test
  public void testCanContain() {
    AWSVirtualOrganization awsVo = new AWSVirtualOrganization();
    AWSVoProperties voProperties = new AWSVoProperties( awsVo );

    assertFalse( awsVo.canContain( null ) );
    assertTrue( awsVo.canContain( voProperties ) );
  }

  /**
   * Test method for {@link AWSVirtualOrganization#load()}.
   * 
   * @throws ProblemException thrown when the vo creator can't be applied to the
   *             vo.
   */
  @Test
  public void testLoad() throws ProblemException {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVirtualOrganization_PDETest.awsVoCreator.create( null );

    // we always have at least the AWSInfoService and the AWSProperties (2
    // children)
    int childCount = awsVo.getChildCount();
    assertTrue( childCount >= 2 );

    // save so that we can load something afterwards
    awsVo.save();

    awsVo.load();

    // one child less than before is expected since the awsinfoservice is not
    // persisted and the load method removes all contained children before
    // loading the persisted ones, therefore one less than before is present
    // after the call to load()
    assertEquals( childCount - 1, awsVo.getChildCount() );
  }

  /**
   * Test method for {@link AWSVirtualOrganization#loadChild(java.lang.String)}.
   * 
   * @throws ProblemException thrown when the vo creator can't be applied to the
   *             vo
   */
  @Test
  public void testLoadChild() throws ProblemException {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVirtualOrganization_PDETest.awsVoCreator.create( null );
    // the method is actually not implemented see source javadoc
    assertNull( awsVo.loadChild( null ) );
    assertNull( awsVo.loadChild( "" ) ); //$NON-NLS-1$
    assertNull( awsVo.loadChild( "dummy" ) ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link AWSVirtualOrganization#apply(AWSVoCreator)}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testApply() throws ProblemException {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVirtualOrganization_PDETest.awsVoCreator.create( null );
    assertTrue( awsVo.getName().equals( AWSVirtualOrganization_PDETest.VO_NAME ) );

    AWSVirtualOrganization_PDETest.awsVoCreator.setVoName( AWSVirtualOrganization_PDETest.VO_NAME_2 );
    awsVo.apply( AWSVirtualOrganization_PDETest.awsVoCreator );
    assertTrue( awsVo.getName()
      .equals( AWSVirtualOrganization_PDETest.VO_NAME_2 ) );
  }

  /**
   * Test method for {@link AWSVirtualOrganization#getTypeName()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testGetTypeName() throws ProblemException {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVirtualOrganization_PDETest.awsVoCreator.create( null );
    assertTrue( awsVo.getTypeName().equals( "AWS VO" ) ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link AWSVirtualOrganization#getWizardId()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testGetWizardId() throws ProblemException {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVirtualOrganization_PDETest.awsVoCreator.create( null );
    assertTrue( awsVo.getWizardId()
      .equals( "eu.geclipse.aws.ui.wizard.awsVoWizard" ) ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link AWSVirtualOrganization#isLazy()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testIsLazy() throws ProblemException {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVirtualOrganization_PDETest.awsVoCreator.create( null );
    assertFalse( awsVo.isLazy() );
  }

  /**
   * Test method for {@link AWSVirtualOrganization#getName()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testGetName() throws ProblemException {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVirtualOrganization_PDETest.awsVoCreator.create( null );
    assertTrue( awsVo.getName().equals( AWSVirtualOrganization_PDETest.VO_NAME ) );
  }

  /**
   * Test method for {@link AWSVirtualOrganization#equals(java.lang.Object)}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testEqualsObject() throws ProblemException {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVirtualOrganization_PDETest.awsVoCreator.create( null );
    AWSVirtualOrganization awsVo2 = ( AWSVirtualOrganization )AWSVirtualOrganization_PDETest.awsVoCreator.create( null );
    AWSVirtualOrganization_PDETest.awsVoCreator.setVoName( AWSVirtualOrganization_PDETest.VO_NAME_2 );
    awsVo2.apply( AWSVirtualOrganization_PDETest.awsVoCreator );
    assertFalse( awsVo.equals( null ) );
    assertFalse( awsVo.equals( new Object() ) );
    assertFalse( awsVo.equals( awsVo2 ) );
    assertTrue( awsVo.equals( awsVo ) );
  }

  /**
   * Test method for {@link AWSVirtualOrganization#getProperties()}.
   * 
   * @throws ProblemException
   */
  @Test
  public void testGetProperties() throws ProblemException {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVirtualOrganization_PDETest.awsVoCreator.create( null );
    assertNotNull( awsVo.getProperties() );
  }

  /**
   * Test method for
   * {@link AWSVirtualOrganization#getChildren(org.eclipse.core.runtime.IProgressMonitor, Class)}
   * 
   * @throws Exception
   */
  @Test
  public void testGetChildren() throws Exception {
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVirtualOrganization_PDETest.awsVoCreator.create( null );
    NullProgressMonitor monitor = new NullProgressMonitor();

    List<IGridElement> children = awsVo.getChildren( monitor, null );
    assertNotNull( children );
    assertTrue( children.size() == 0 );

    List<AWSVoProperties> children2 = awsVo.getChildren( monitor,
                                                         AWSVoProperties.class );
    assertNotNull( children2 );
    assertTrue( children2.size() == 1 );

    List<AWSVirtualOrganization> children3 = awsVo.getChildren( monitor,
                                                                AWSVirtualOrganization.class );
    assertNotNull( children3 );
    assertTrue( children3.size() == 0 );
  }

  /**
   * Test method for {@link AWSVirtualOrganization#getSupportedCategories()}
   * 
   * @throws Exception
   */
  @Test
  public void testGetSupportedResources() throws Exception {
    AWSVirtualOrganization awsVo = AWSVoTestUtil.getAwsVo();
    IGridResourceCategory[] supportedCategories = awsVo.getSupportedCategories();

    assertTrue( supportedCategories.length == AWSVirtualOrganization.STANDARD_RESOURCE_CATEGORIES.length );
  }
}
