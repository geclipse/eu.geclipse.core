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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.aws.IAWSServiceCreator;
import eu.geclipse.aws.test.util.AWSVoTestUtil;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.reporting.ProblemException;

/**
 * PDETest class for the {@link AWSVoCreator} implementation of the
 * {@link IGridElementCreator} interface.
 * 
 * @author Moritz Post
 */
public class AWSVoCreator_PDETest {

  /** The instance of the {@link AWSVoCreator} to run tests against. */
  private static AWSVoCreator awsVoCreator;

  /**
   * To execute a test a {@link AWSVoCreator} is required. Therefore we query
   * {@link GridModel#getVoCreators()} to supply us with one.
   * <p>
   * Also we create {@link IFileStore}s to run tests against
   * 
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpClass() throws Exception {
    AWSVoCreator_PDETest.awsVoCreator = AWSVoTestUtil.getAwsVoCreator();
    if( AWSVoCreator_PDETest.awsVoCreator == null ) {
      fail( "Could not obtain awsVoCreator" ); //$NON-NLS-1$
    }
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.vo.AWSVoCreator#canCreate(java.lang.Class)}.
   */
  @Test
  public void testCanCreateClassOfQextendsIGridElement() {
    assertTrue( AWSVoCreator_PDETest.awsVoCreator.canCreate( AWSVirtualOrganization.class ) );
    assertTrue( AWSVoCreator_PDETest.awsVoCreator.canCreate( IGridContainer.class ) );
    assertFalse( AWSVoCreator_PDETest.awsVoCreator.canCreate( String.class ) );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.vo.AWSVoCreator#canCreate(org.eclipse.core.filesystem.IFileStore)}
   * . //
   * 
   * @throws CoreException when the {@link IFileStore} file can not be converted
   *           to a {@link File}, this exception is thrown
   */
  @Test
  public void testCanCreateIFileStore() throws CoreException {

    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )AWSVoCreator_PDETest.awsVoCreator.create( null );
    IFileStore awsVoFileStore = awsVo.getFileStore();

    // filestore does exist but without correct children
    assertFalse( AWSVoCreator_PDETest.awsVoCreator.canCreate( awsVoFileStore.getParent() ) );

    // correct filestore is present
    awsVo.getProperties().save();
    assertTrue( AWSVoCreator_PDETest.awsVoCreator.canCreate( awsVoFileStore ) );

    // filestore is null
    awsVoFileStore = null;
    assertFalse( AWSVoCreator_PDETest.awsVoCreator.canCreate( awsVoFileStore ) );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.vo.AWSVoCreator#create(eu.geclipse.core.model.IGridContainer)}
   * .
   * 
   * @throws CoreException problem working with the {@link GridModel}
   */
  @Test
  public void testCreate() throws CoreException {

    // create new AWSVo (default)
    IGridElement awsVo = AWSVoCreator_PDETest.awsVoCreator.create( null );
    assertTrue( awsVo instanceof AWSVirtualOrganization );

    // create AWSVo from store
    // correct filestore is present
    AWSVoProperties voProperties = new AWSVoProperties( ( AWSVirtualOrganization )awsVo );
    voProperties.save();

    IGridElement awsVo2 = AWSVoCreator_PDETest.awsVoCreator.create( null );
    assertTrue( awsVo2 instanceof AWSVirtualOrganization );
    assertTrue( ( ( AWSVirtualOrganization )awsVo2 ).getChildCount() > 0 );
  }

  /**
   * Test method for {@link eu.geclipse.aws.vo.AWSVoCreator#getVoName()}.
   */
  @Test
  public void testGetSetVoName() {
    AWSVoCreator_PDETest.awsVoCreator.setVoName( "" ); //$NON-NLS-1$
    assertTrue( AWSVoCreator_PDETest.awsVoCreator.getVoName().equals( "" ) ); //$NON-NLS-1$

    AWSVoCreator_PDETest.awsVoCreator.setVoName( null );
    assertTrue( AWSVoCreator_PDETest.awsVoCreator.getVoName() == null );

    AWSVoCreator_PDETest.awsVoCreator.setVoName( "VoName" ); //$NON-NLS-1$
    assertTrue( AWSVoCreator_PDETest.awsVoCreator.getVoName().equals( "VoName" ) ); //$NON-NLS-1$
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.vo.AWSVoCreator#apply(eu.geclipse.aws.vo.AWSVirtualOrganization)}
   * .
   * 
   * @throws ProblemException when a problem occurs while fetching the
   *           {@link AWSVoProperties}
   */
  @Test
  public void testApply() throws ProblemException {
    String voName = "MyAWSVo"; //$NON-NLS-1$

    // test vanilla vo
    AWSVirtualOrganization awsVo = new AWSVirtualOrganization();
    assertNull( awsVo.getProperties() );
    assertNull( awsVo.getName() );

    AWSVoCreator_PDETest.awsVoCreator.setVoName( voName );

    // apply settings from creator and test the populated vo
    AWSVoCreator_PDETest.awsVoCreator.apply( awsVo );

    assertTrue( awsVo.getName().equals( voName ) );
  }

  /**
   * Test method for {@link eu.geclipse.aws.vo.AWSVoCreator#getExtensionID()}.
   */
  @Test
  public void testGetExtensionID() {
    assertTrue( AWSVoCreator_PDETest.awsVoCreator.getExtensionID()
      .equals( "eu.geclipse.aws.vo.awsVoCreator" ) ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link AWSVoCreator#getAWSServiceCreators()}
   * 
   * @throws Exception
   */
  @Test
  public void testGetAWSServiceCreators() throws Exception {
    List<IAWSServiceCreator> serviceCreators = AWSVoCreator.getAWSServiceCreators();
    assertNotNull( serviceCreators );
  }
}
