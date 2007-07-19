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

package eu.geclipse.core;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import eu.geclipse.core.model.GridModelProblems;

/**This class tests the functionality of each method in the class CoreProblems.
 * 
 * @author tao-j
 *
 */

public class CoreProblems_Test {
  
  CoreProblems coreproblem;

  /**setup
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception
  {
    this.coreproblem = new CoreProblems();
  }

  /** test the method {@link CoreProblems#getProblem(int, Throwable)}
   * Both a true and a false case is immulated.
   *
   */
  
  @Test
  public void testGetProblem()
  {
    IProblem problem;
    int problemID;
    Throwable exc = new Throwable();
    problemID = GridModelProblems.ELEMENT_LOAD_FAILED;
    problem = this.coreproblem.getProblem( problemID, exc );
    Assert.assertNull( problem );
    problemID = CoreProblems.CONNECTION_FAILED;
    problem = this.coreproblem.getProblem( problemID, exc );
    Assert.assertNotNull( problem );
    Assert.assertEquals( "Unable to establish connection", problem.getText() ); //$NON-NLS-1$
    List< ISolution > solutions = new ArrayList< ISolution >();
    SolutionRegistry solutionregister = SolutionRegistry.getRegistry();
    solutions = problem.getSolutions( solutionregister );
    Assert.assertEquals( "Check your Internet connection",solutions.get( 0 ).getText() ); //$NON-NLS-1$
    Assert.assertEquals( new Integer( SolutionRegistry.CHECK_SERVER_URL ),
                         new Integer( solutions.get( 1 ).getID() ));
    Assert.assertEquals( "Check your server URL",solutions.get( 1 ).getText() ); //$NON-NLS-1$
    Assert.assertEquals( new Integer( SolutionRegistry.CHECK_PROXY_SETTINGS ),
                         new Integer( solutions.get( 2 ).getID() ));
  }
}
