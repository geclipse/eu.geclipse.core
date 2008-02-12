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
//import eu.geclipse.core.auth.AuthenticationProblems;
import eu.geclipse.core.model.GridModelProblems;

/**
 * This is a combined test between different classes and packages
 * Including: eu.eclipse.core.IproblemProvider, ProblemRegistry, Solution, 
 * SolutionRegistry, CoreProblems, eu.geclipse.core.auth.AuthenticationProblems,
 * eu.geclipse.core.model.GridmodelProblems
 * @author jie
 *
 */

public class ProblemSolutionProviderRegistry_Test {

  CoreProblems coreproblem;
  SolutionRegistry solutionregister;
  GridModelProblems gridmodelproblem;
  //AuthenticationProblems authproblem;
  
  /**setup
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception
  {
    this.coreproblem = new CoreProblems();
    //this.authproblem = new AuthenticationProblems();
    this.solutionregister = SolutionRegistry.getRegistry();
    this.gridmodelproblem = new GridModelProblems();
  }
  
  /**
   * Test for the <code>eu.geclipse.core.SolutionRegistry#getRegistry/code>,
   * <code>eu.geclipse.core.CoreProblems#getProblem/code>,
   * <code>eu.geclipse.core.auth.AuthentiicationProblems#getProblem/code>,
   * <code>eu.geclipse.core.model.GridModelProblems#getProblem/code>,
   * <code>eu.geclipse.core.AbstractProblem#getSolutions/code>,
   * <code>eu.geclipse.core.Solution#getID/code>,
   * <code>eu.geclipse.core.Solution#getText/code>,
   * <code>eu.geclipse.core.AbstractProblem#getText/code>,
   * methods. Worklow:
   * 1. assume there be a "connection failed" error
   * 2. find the solution(s) for it
   * 3. assume a "certiication load ailed" error
   * 4. find the solution
   * 5. assume a "fetch children failed" error
   * 6. search the solution
   * @author jie
   */
  
  @Test
  public void testProblem_Provider_Solu_Registry()
  {
    int problem_id;
    IProblem problem;
    List< ISolution > solutions = new ArrayList< ISolution >();
    Throwable exc = new Throwable ();
    problem_id = CoreProblems.CONNECTION_FAILED;
    problem = this.coreproblem.getProblem( problem_id, exc );
    solutions = problem.getSolutions( this.solutionregister );
    Assert.assertEquals( new Integer( SolutionRegistry.CHECK_INTERNET_CONNECTION ),
                         new Integer( solutions.get( 0 ).getID() ));
    Assert.assertEquals( "Check your Internet connection",solutions.get( 0 ).getText() ); //$NON-NLS-1$
    Assert.assertEquals( new Integer( SolutionRegistry.CHECK_SERVER_URL ),
                         new Integer( solutions.get( 1 ).getID() ));
    Assert.assertEquals( "Check your server URL",solutions.get( 1 ).getText() ); //$NON-NLS-1$
    Assert.assertEquals( new Integer( SolutionRegistry.CHECK_PROXY_SETTINGS ),
                         new Integer( solutions.get( 2 ).getID() ));
    Assert.assertEquals( "Check your proxy settings",solutions.get( 2 ).getText() ); //$NON-NLS-1$
    
//    problem_id = AuthenticationProblems.CERTIFICATE_LOAD_FAILED;
//    problem = this.authproblem.getProblem( problem_id, exc );
//    solutions = problem.getSolutions( this.solutionregister );
//    Assert.assertEquals( new Integer( 0 ),new Integer( solutions.size()));
//    Assert.assertEquals( "Unable to load certificate",problem.getText()); //$NON-NLS-1$
    
    problem_id = GridModelProblems.FETCH_CHILDREN_FAILED;
    problem = this.gridmodelproblem.getProblem( problem_id, exc );
    solutions = problem.getSolutions( this.solutionregister );
    Assert.assertEquals( new Integer( SolutionRegistry.CHECK_CA_CERTIFICATES ),
                         new Integer( solutions.get( 0 ).getID() ));
    Assert.assertEquals( "Check your imported CA-certificates",solutions.get( 0 ).getText() ); //$NON-NLS-1$
    Assert.assertEquals( new Integer( SolutionRegistry.CHECK_AUTH_TOKENS ),
                         new Integer( solutions.get( 1 ).getID() ));
    Assert.assertEquals( "Check your authentication tokens",solutions.get( 1 ).getText() ); //$NON-NLS-1$
  }
}
