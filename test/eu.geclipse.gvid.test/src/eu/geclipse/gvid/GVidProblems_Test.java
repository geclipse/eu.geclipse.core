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

package eu.geclipse.gvid;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.IProblem;
import eu.geclipse.gvid.internal.GVidStatsEvent;

/**test class for {@link GVidProblems}
 * @author tao-j
 *
 */

public class GVidProblems_Test {

  private static GVidProblems gvidproblem;
  /**initial setups; create a GVidProblems class
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    gvidproblem = new GVidProblems();
  }

  /**tests the method {@link GVidProblems#getProblem(int, Throwable)}
   * with a true and a false case
   */
  @Test
  public void testGetProblem() {
    Throwable exc = new Throwable();
    IProblem problem = gvidproblem.getProblem( 0, exc );
    Assert.assertNull( problem );
    int problemid = GVidProblems.CODEC_INSTATIATION_FAILED;
    problem = gvidproblem.getProblem( problemid, exc );
    Assert.assertNotNull( problem );
  }
}
