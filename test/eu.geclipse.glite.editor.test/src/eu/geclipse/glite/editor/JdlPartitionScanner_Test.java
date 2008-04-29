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

package eu.geclipse.glite.editor;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;


/**tests class for {@link JdlPartitionScanner}
 * @author tao-j
 *
 */
public class JdlPartitionScanner_Test {
 JdlPartitionScanner scanner;
 
  /**initialization
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    this.scanner = new JdlPartitionScanner();
  }

  /**tests the method {@link JdlPartitionScanner#JdlPartitionScanner()}
   * 
   */
  @Test
  public void testJdlPartitionScanner() {
    Assert.assertNotNull( this.scanner );
  }
}
