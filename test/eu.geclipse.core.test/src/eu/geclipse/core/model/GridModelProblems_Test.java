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

package eu.geclipse.core.model;


import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import eu.geclipse.core.IProblem;



/**This class tests all methods in the class {@link GridModelProblems}
 * @author tao-j
 *
 */
public class GridModelProblems_Test {

  GridModelProblems problem;
  
  /** initial setup; generates an object for testing
   * @throws Exception
   */
  
  @Before
  public void setUp() throws Exception {
    this.problem = new GridModelProblems();
  }

  /**
   * tests the method {@link GridModelProblems#getProblem(int, Throwable)}
   */
  @Test
  public void testGetProblem() {
    IProblem test;
    int problemID;
    Throwable exc = new Throwable();
    problemID = GridModelProblems.JOB_SUBMIT_FAILED;
    test = this.problem.getProblem( problemID, exc );
    Assert.assertNotNull( test );
    Assert.assertEquals( "Failed to submit job", test.getText() ); //$NON-NLS-1$
  }
}
