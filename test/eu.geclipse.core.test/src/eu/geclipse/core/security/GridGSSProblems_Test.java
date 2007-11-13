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

package eu.geclipse.core.security;

import org.ietf.jgss.GSSException;
import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.IProblem;
import eu.geclipse.core.ProblemRegistry;
import eu.geclipse.core.model.GridModelProblems;


/**tests the methods in the class {@link GridGSSProblems}
 * @author tao-j
 *
 */
public class GridGSSProblems_Test {

  private static GridGSSProblems problem;
  
  /**initialization
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    problem = new GridGSSProblems();
  }

  /**
   * tests the method {@link GridGSSProblems#getProblem(int, Throwable)}
   */
  @Test
  public void testGetProblemID() {
    int problemID;
    IProblem newproblem;
    Throwable exc = new Throwable();
    //use a model problem as a false case
    problemID = GridModelProblems.ELEMENT_LOAD_FAILED;
    newproblem = problem.getProblem( problemID, exc );
    Assert.assertNull( newproblem );
    problemID = GridGSSProblems.BAD_BINDINGS_GSS_PROBLEM;
    newproblem = problem.getProblem( problemID, exc );
    Assert.assertNull( newproblem );
  }
}
