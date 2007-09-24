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

import java.net.URI;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.info.glue.GlueSE;


/**test class for {@link GridGlueStorage}
 * @author tao-j
 *
 */
public class GridGlueStorage_Test {

  private static GridGlueStorage storage;
  /**initial setup
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    GlueSE ser = new GlueSE();
    storage = new GridGlueStorage(null,ser);
  }

  /**tests the method {@link GridGlueStorage#
   * GridGlueStorage(eu.geclipse.core.model.IGridContainer, GlueSE)}
   * 
   */
  @Test
  public void testGridGlueStorage() {
    Assert.assertNotNull( storage );
  }

  /**tests the method {@link GridGlueStorage#getAccessTokens()}
   * 
   */
  @Test
  public void testGetAccessTokens() {
    URI[] urilist = storage.getAccessTokens();
    Assert.assertNotNull( urilist );
  }

  /**tests the method {@link GridGlueStorage#getGlueSe()}
   * 
   */
  @Test
  public void testGetGlueSe() {
    Assert.assertNotNull(storage.getGlueSe());
  }
}
