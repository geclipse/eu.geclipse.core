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
 *    Jie Tao - test class (Plug-in test)
 *****************************************************************************/

package eu.geclipse.glite.editor;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;


/**test class for {@link JdlEditor}
 * @author tao-j
 *
 */
public class JdlEditor_PDETest {
  
  JdlEditor editor;
  /**initial setups; create a JdlEditor class
   * @throws Exception
   */

  @Before
  public void setUp() throws Exception {
    this.editor = new JdlEditor();
  }


  /**tests the method {@link JdlEditor#JdlEditor()}
   * 
   */
  @Test
  public void testJdlEditor() {
    Assert.assertNotNull( this.editor );
  }
}
