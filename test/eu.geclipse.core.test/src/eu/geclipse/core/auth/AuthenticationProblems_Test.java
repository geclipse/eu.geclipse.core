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

package eu.geclipse.core.auth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import eu.geclipse.core.IProblem;

/**This class tests the functionality of each method in the class AuthenticationProblems.
 * 
 * @author tao-j
 *
 */

public class AuthenticationProblems_Test {

  AuthenticationProblems authproblem;
  
  /**setup
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception
  {
    this.authproblem = new AuthenticationProblems();
  }

  /** test the method {@link AuthenticationProblems#getProblem(int, Throwable)}
   * Both a true and a false case is immulated.
   *
   */
  @Test
  public void testGetProblem()
  {
    IProblem problem;
    int problemID;
    Throwable exc = new Throwable();
    problemID = AuthenticationProblems.CERTIFICATE_LOAD_FAILED;
    problem = this.authproblem.getProblem( problemID, exc );
    Assert.assertNotNull( problem );
    Assert.assertEquals( "Unable to load certificate", problem.getText() ); //$NON-NLS-1$
  }

  /** Tests the method {@link AuthenticationProblems#createProblem}
   * 
   */
  @Test
  public void testCreateProblem()
  {
   IProblem problem;
   int problemid = 1;
   Throwable exc = new Throwable();
   problem = this.authproblem.createProblem( problemid, 
                    "token failed", exc, null ); //$NON-NLS-1$
   Assert.assertNotNull( problem );
   Assert.assertEquals( "token failed", problem.getText() ); //$NON-NLS-1$
  }
}
