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
 *    Jie Tao - test class (Junit test)
 *****************************************************************************/

package eu.geclipse.info.model;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.info.glue.GlueService;


/**test class for {@link GridGlueService}
 * @author tao-j
 *
 */
public class GridGlueService_Test {

  private static GridGlueService service;
  /**initial setup
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    GlueService ser = new GlueService();
    ser.endpoint = "processGlueCERows"; //$NON-NLS-1$
    service = new GridGlueService( null,ser );
  }

 /**tests the method {@link GridGlueService#getName()}
   * 
   */
  @Test
  public void testGetName() {
  Assert.assertEquals( "processGlueCERows", service.getName() );  //$NON-NLS-1$
  }

  /**tests the method {@link GridGlueService#
   * GridGlueService(eu.geclipse.core.model.IGridContainer, GlueService)}
   * 
   */
  @Test
  public void testGridGlueService() {
   Assert.assertNotNull( service );
  }
  
  /**tests the method {@link GridGlueService#getURI()}
   * 
   */
  @Test
  public void testGetURI() {
    Assert.assertEquals( "processGlueCERows", service.getURI().toString() ); //$NON-NLS-1$
  }

  /**tests the method {@link GridGlueService#getGlueService()}
   * 
   */
  @Test
  public void testGetGlueService() {
    Assert.assertNotNull( service.getGlueService() );
  }
}
