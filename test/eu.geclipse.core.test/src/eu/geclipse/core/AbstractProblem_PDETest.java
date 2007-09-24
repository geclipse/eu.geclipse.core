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

package eu.geclipse.core;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import eu.geclipse.core.internal.Activator;

/** this class test the methods in class AmstractProblems
 * several protected methods or methords using private variables can not be tested
 * @author tao-j
 *
 */

public class AbstractProblem_PDETest {

  /**setup
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception
  {
    // currently no setup
  }

  /** this test validates the functionality of 
   * {@link AbstractProblem#addSolution(int)}
   * and {@link AbstractProblem#getID()}
   * and {@link AbstractProblem#getSolutions(SolutionRegistry)}
   * using the approach of adding and reading an solution
   */
  
  @Test
  public void testAddSolutionInt()
  {
    int solutionID = SolutionRegistry.CHECK_AUTH_TOKENS;
    IProblem problem;
    Throwable exc = new Throwable ();
    CoreProblems coreproblem = new CoreProblems();
    int problem_id = CoreProblems.JOB_SUBMISSION_FAILED;
    problem = coreproblem.getProblem( problem_id, exc );
    problem.addSolution( solutionID );
    List< ISolution > solutions = new ArrayList< ISolution >();
    SolutionRegistry solutionregister = SolutionRegistry.getRegistry();
    solutions = problem.getSolutions( solutionregister );
    Assert.assertEquals( new Integer( solutionID ), new Integer( solutions.get( 0 ).getID()));
  }
  
  /** this test validates the functionality of 
   * {@link AbstractProblem#addSolution(ISolution)}
   * and {@link AbstractProblem#getID()}
   * and {@link AbstractProblem#getSolutions(SolutionRegistry)}
   * by comparing the added solution with the acquired one
   */
  
  @Test
  public void testAddSolutionISolution()
  {
    SolutionRegistry solutionregistry = SolutionRegistry.getRegistry();
    int solutionID = SolutionRegistry.CHECK_PROXY_SETTINGS;
    ISolution solution = solutionregistry.getSolution( solutionID );
    IProblem problem;
    Throwable exc = new Throwable ();
    CoreProblems coreproblem = new CoreProblems();
    int problem_id = CoreProblems.CONNECTION_FAILED;
    problem = coreproblem.getProblem( problem_id, exc );
    problem.addSolution( solution );
    List< ISolution > solutions = new ArrayList< ISolution >();
    solutions = problem.getSolutions( solutionregistry );
    Assert.assertEquals( solution, solutions.get( 2 ));
  }


  /** this test validates the functionality of 
   * {@link AbstractProblem#addReason(String)}
   * and {@link AbstractProblem#getReasons()}
   */
  
  @Test
  public void testAddReason()
  {
    IProblem problem;
    int id;
    Throwable exec = new Throwable ();
    id = ProblemRegistry.uniqueID();
    problem = ProblemRegistry.createProblem( id, "test problem", exec, null, Activator.PLUGIN_ID ); //$NON-NLS-1$
    problem.addReason( "unknown" ); //$NON-NLS-1$
    List< String > reasons = problem.getReasons();
    Assert.assertNotNull( reasons );
    Assert.assertEquals( new Double( 1 ), new Double( reasons.size() ) );
    Assert.assertEquals( "unknown", reasons.get( 0 ) ); //$NON-NLS-1$
    Assert.assertEquals( "test problem", problem.getText()); //$NON-NLS-1$
  }

  /** this test validates the functionality of 
   * {@link AbstractProblem#getStatus()}
   */
  
  @Test
  public void testGetStatus()
  {
    IProblem problem;
    int id;
    Throwable exec = new Throwable ();
    IStatus status;
    id = ProblemRegistry.uniqueID();
    problem = ProblemRegistry.createProblem( id, "test problem", exec, null, Activator.PLUGIN_ID ); //$NON-NLS-1$
    status = problem.getStatus();
    Assert.assertEquals( new Integer( 4 ),new Integer( status.getSeverity() ));
  }
}
