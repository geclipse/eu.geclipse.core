/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.model;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.geclipse.core.model.impl.JSDLJobDescription;

/**
 * Test for the {@link GridModel} class.
 */
public class GridModel_PDETest {
  
  /**
   * Initialize the Grid model.
   * 
   * @throws Exception if error occures.
   */
  @Before
  public void setUp() throws Exception {
    GridModel.getRoot();
  }

  /**
   * Tests the {@link GridModel#getConnectionManager()}-method.
   */
  @Test
  public void testGetConnectionManager() {
    IConnectionManager connectionManager = GridModel.getConnectionManager();
    Assert.assertNotNull( connectionManager );
  }

  /**
   * Tests the {@link GridModel#getJobManager()}-method.
   */
  @Test
  public void testGetJobManager() {
    IJobManager jobManager = GridModel.getJobManager();
    Assert.assertNotNull( jobManager );
  }

  /**
   * Tests the {@link GridModel#getRoot()}-method.
   */
  @Test
  public void testGetRoot() {
    IGridRoot root = GridModel.getRoot();
    Assert.assertNotNull( root );
  }

  /**
   * Tests the {@link GridModel#getStandardCreators()}-method.
   */
  @Test
  public void testGetStandardCreators() {
    List<IGridElementCreator> standardCreators = GridModel.getStandardCreators();
    Assert.assertFalse( standardCreators.isEmpty() );
  }

  /**
   * Tests the {@link GridModel#getElementCreators()}-method.
   */
  @Test
  public void testGetElementCreators()
  {
    List< IGridElementCreator > elementCreators = GridModel.getElementCreators();
    Assert.assertFalse( elementCreators.isEmpty() );
  }

  /**
   * Tests the {@link GridModel#getElementCreators(Class)}-method.
   */
  @Test
  public void testGetElementCreatorsClassOfQextendsIGridElement() {
    List<IGridElementCreator> elementCreators = GridModel.getElementCreators( IGridElement.class );
    Assert.assertFalse( elementCreators.isEmpty() );
  }

  /**
   * Tests the {@link GridModel#getStorableElementCreators()}-method.
   */
  @Test
  public void testGetStorableElementCreators() {
    List<IStorableElementCreator> storableElementCreators = GridModel.getStorableElementCreators();
    Assert.assertFalse( storableElementCreators.isEmpty() );
  }

  /**
   * Tests the {@link GridModel#getJobCreators()}-method.
   */
  @Test
  public void testGetJobCreators() {
    List<IGridJobCreator> jobCreators = GridModel.getJobCreators();
    Assert.assertFalse( jobCreators.isEmpty() );
  }

  /**
   * Tests the {@link GridModel#getJobCreators(IGridJobDescription)}-method.
   */
  @Test
  public void testGetJobCreatorsIGridJobDescription() {
    // TODO mathias create a valid JSDL file since the null approach breaks
    IGridJobDescription jobDescription = new JSDLJobDescription( ( IFile )null );
    List<IGridJobCreator> jobCreators = GridModel.getJobCreators( jobDescription );
    Assert.assertNotNull( jobCreators );
    Assert.assertFalse( jobCreators.isEmpty() );
  }

  /**
   * Tests the {@link GridModel#getTransferManager()}-method.
   */
  @Test
  public void testGetTransferManager() {
    ITransferManager transferManager = GridModel.getTransferManager();
    Assert.assertNotNull( transferManager );
  }

  /**
   * Tests the {@link GridModel#getVoManager()}-method.
   */
  @Test
  public void testGetVoManager() {
    IVoManager voManager = GridModel.getVoManager();
    Assert.assertNotNull( voManager );
  }
}
