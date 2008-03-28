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

import org.junit.Before;
import org.junit.Test;

import eu.geclipse.info.glue.GlueCE;


/**test class for {@link GridGlueComputing}
 * @author tao-j
 *
 */
public class GridGlueComputing_Test {

  GridGlueComputing comput;
  /**initial setup
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    GlueCE gluece = new GlueCE();
    this.comput = new GridGlueComputing( null,gluece);
  }

  /**tests the method {@link GridGlueComputing#
   * GridGlueComputing(eu.geclipse.core.model.IGridContainer, GlueCE)}
   * 
   */
  @Test
  public void testGridGlueComputing() {
   Assert.assertNotNull( this.comput );
  }

  /**tests the method {@link GridGlueComputing#getGlueCe()}
   * 
   */
  @Test
  public void testGetGlueCe() {
    GlueCE ce = this.comput.getGlueCe();
    Assert.assertNotNull( ce );
    Assert.assertNull( ce.ApplicationDir);
  }
}
