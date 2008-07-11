/******************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
 *    Jie Tao - test class (Plug-in test)
 *****************************************************************************/

package eu.geclipse.core.model.impl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IVoManager;
import eu.geclipse.core.reporting.ProblemException;


/**this class tests all methods in the class {@link GenericVirtualOrganization}
 * @author tao-j
 *
 */
public class GenericVirtualOrganization_PDETest {
  
  static private GenericVirtualOrganization gvo;
  static private GenericVoCreator creator;
  /**initial setup for an object of the tested class
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    IVoManager vomanager = GridModel.getVoManager();
    creator = new GenericVoCreator();
    creator.setVoName( "test" ) ; //$NON-NLS-1$  
    vomanager.create( creator);
    gvo = new GenericVirtualOrganization(creator);
  }

  /**
   * tests the method {@link GenericVirtualOrganization#
   * canContain(eu.geclipse.core.model.IGridElement)}
   */
  @Test
  public void testCanContain() {
   Assert.assertFalse( gvo.canContain( GridModel.getRoot() ) );
  }

  /**
   * tests the method GenericVirtualOrganization#loadChild(String)
   */
  @Test
  public void testLoadChild() {
   String name = ".generic_vo_properties"; //$NON-NLS-1$
   Assert.assertNull( gvo.loadChild( name ));
  }

  /**
   * tests the method GenericVirtualOrganization()
   */
  @Test
  public void testGenericVirtualOrganizationGenericVoCreator() {
    Assert.assertNotNull( gvo );
  }

  /**
   * tests the method GenericVirtualOrganization()
   * @throws URISyntaxException 
   * @throws CoreException 
   */
  //can not be tested due to dependency
  /*@Test
  public void testGenericVirtualOrganizationIFileStore() throws URISyntaxException, CoreException {
    String local = "file://" + GridTestStub.setUpLocalDir(); //$NON-NLS-1$
    URI localuri = new URI( local );
    String scheme = localuri.getScheme();
    IFileSystem fileSystem = EFS.getFileSystem( scheme );
    IFileStore store = fileSystem.getStore( localuri );
    Assert.assertNotNull( new GenericVirtualOrganization(store) );
  }*/

  /**
   * tests the method {@link GenericVirtualOrganization#getTypeName()}
   */
  @Test
  public void testGetTypeName() {
  Assert.assertEquals( "Generic VO", gvo.getTypeName() ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link GenericVirtualOrganization#isLazy()}
   */
  @Test
  public void testIsLazy() {
  Assert.assertFalse( gvo.isLazy() );
  }

  /**
   * tests the method {@link GenericVirtualOrganization#getName()}
   */
  @Test
  public void testGetName() {
    Assert.assertEquals( "test", gvo.getName() ); //$NON-NLS-1$
  }

  /**
   * tests the method GenericVirtualOrganization#apply(GenericVoCreator)
   * @throws ProblemException 
   */
  @Test
  public void testApply() throws ProblemException {
    gvo.apply( creator );
  }

  /**
   * tests the method {@link GenericVirtualOrganization#getWizardId()}
   */
  @Test
  public void testGetWizardId() {
   Assert.assertEquals( "eu.geclipse.ui.wizards.GenericVoWizard", gvo.getWizardId() ); //$NON-NLS-1$
  }
}
