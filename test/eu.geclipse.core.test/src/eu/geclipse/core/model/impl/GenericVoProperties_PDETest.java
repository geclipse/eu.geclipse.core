/******************************************************************************
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
 *    Jie Tao - test class (Plug-in test)
 *****************************************************************************/

package eu.geclipse.core.model.impl;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IVoManager;


/**this class tests the methods in the class {@link GenericVoProperties}
 * @author tao-j
 *
 */
public class GenericVoProperties_PDETest {

  private static GenericVoProperties property;
  private static GenericVirtualOrganization vo;
  /**initial setup for an object of the tested class
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    GenericVoCreator creator = new GenericVoCreator();
    //the following is to create a creator and give it a name and then store it 
    // this establishes a directory .vo in the junit-test workspace. Some methods access this directory
    creator.setVoName( "test" ) ; //$NON-NLS-1$ 
    IVoManager vomanager = GridModel.getVoManager();
    vomanager.create( creator );
    vomanager.saveElements();
    vo = new GenericVirtualOrganization(creator);
    property = new GenericVoProperties(vo);
  }

  /**
   * tests the method GenericVoProperties#GenericVoProperties(GenericVirtualOrganization)
   */
  @Test
  public void testGenericVoProperties() {
    Assert.assertNotNull( property );
  }

  /**
   * tests the method {@link GenericVoProperties#getFileStore()}
   */
  @Test
  public void testGetFileStore() {
    Assert.assertNotNull( property.getFileStore() );
  }

  /**
   * tests the method {@link GenericVoProperties#getName()}
   */
  @Test
  public void testGetName() {
    Assert.assertEquals( ".generic_vo_properties", property.getName() ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link GenericVoProperties#getParent()}
   */
  @Test
  public void testGetParent() {
    Assert.assertEquals( vo, property.getParent() );
  }

  /**
   * tests the method {@link GenericVoProperties#getPath()}
   */
  @Test
  public void testGetPath() {
    Assert.assertEquals( ".vos/test/.generic_vo_properties", property.getPath().toString() ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link GenericVoProperties#getResource()}
   */
  @Test
  public void testGetResource() {
   Assert.assertNull( property.getResource() );
  }

  /**
   * tests the method {@link GenericVoProperties#isLocal()}
   */
  @Test
  public void testIsLocal() {
    Assert.assertTrue( property.isLocal() );
  }

  /**
   * tests the method {@link GenericVoProperties#load()}
   */
  @Test
  public void testLoad() {
    // empty implementation of the original method
  }

  /**
   * tests the method {@link GenericVoProperties#save()}
   * @throws GridModelException 
   */
  @Test
  public void testSave() throws GridModelException {
    property.save();
  }
}
