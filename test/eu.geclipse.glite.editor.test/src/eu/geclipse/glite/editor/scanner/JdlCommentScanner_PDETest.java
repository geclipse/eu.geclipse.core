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

package eu.geclipse.glite.editor.scanner;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


/**test class for {@link JdlCommentScanner}
 * @author tao-j
 *
 */
public class JdlCommentScanner_PDETest {

 private static JdlCommentScanner scanner;
  
  /**initial setups; create a JdlCommentScanner class
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    scanner = new JdlCommentScanner();
  }

  /**tests the method {@link JdlCommentScanner#JdlCommentScanner()}
   * 
   */
  @Test
  public void testJdlCommentScanner() {
    Assert.assertNotNull( scanner );
    Assert.assertEquals( new Integer( 0 ), new Integer( scanner.getTokenLength()));
  }
}
