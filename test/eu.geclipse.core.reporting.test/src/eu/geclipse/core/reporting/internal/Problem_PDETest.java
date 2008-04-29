/*****************************************************************************
 * Copyright (c) 2006, 2007, 2008 g-Eclipse Consortium 
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

package eu.geclipse.core.reporting.internal;

import org.eclipse.core.runtime.IStatus;
import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.reporting.ISolution;
import eu.geclipse.core.reporting.ReportingPlugin;


/** tests the methods in class {@link Problem}
 * @author tao-j
 *
 */
public class Problem_PDETest {

  private static Problem testproblem;
  /**initialization
   * create an object
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    testproblem = new Problem (IProblemReporting.UNKNOWN_PROBLEM_ID, "test",  //$NON-NLS-1$
       null, "jie.tao@iwr.fzk.de", "eu.geclise.core.reporting.test"); //$NON-NLS-1$ //$NON-NLS-2$
  }

  /**
   * tests the method {@link Problem#Problem(String, String, Throwable, String, String)}
   */
  @Test
  public void testProblem() {
   Assert.assertNotNull( testproblem );
  }

  /**
   * tests the methods {@link Problem#addReason(String)}
   * and {@link Problem#getReasons()}
   */
  @Test
  public void testAddReason_GetReasons() {
   testproblem.addReason( "for test" ); //$NON-NLS-1$
   testproblem.addReason( "only for test" ); //$NON-NLS-1$
   String[] result = testproblem.getReasons();
   Assert.assertEquals( new Integer(2), new Integer(result.length) );
   Assert.assertEquals( "for test", result[0] ); //$NON-NLS-1$
  }

  /**
   * tests the methods {@link Problem#addSolution(eu.geclipse.core.reporting.ISolution)}
   * and {@link Problem#getSolutions()}
   */
  @Test
  public void testAdd_GetSolutionISolution() {
   ISolution solution = ReportingPlugin.getReportingService()
   .createSolution( "test solution", null ); //$NON-NLS-1$
   testproblem.addSolution( solution );
   ISolution[] result = testproblem.getSolutions();
   Assert.assertEquals( new Integer(1), new Integer(result.length) );
   Assert.assertEquals( "test solution", result[0].getDescription() ); //$NON-NLS-1$
  }

  /**
   * tests the methods {@link Problem#addSolution(String, String)}
   */
  @Test
  public void testAddSolutionStringString() {
  testproblem.addSolution( "no solution for test", "test" ); //$NON-NLS-1$ //$NON-NLS-2$
  ISolution[] result = testproblem.getSolutions();
  Assert.assertEquals( new Integer(1), new Integer(result.length) );
  Assert.assertEquals( "test solution", result[0].getDescription() ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link Problem#getDescription()}
   */
  @Test
  public void testGetDescription() {
   Assert.assertEquals( "test", testproblem.getDescription() ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link Problem#getException()}
   */
  @Test
  public void testGetException() {
    Assert.assertNull( testproblem.getException() );
  }

  /**
   * tests the method {@link Problem#getID()}
   */
  @Test
  public void testGetID() {
    Assert.assertEquals( IProblemReporting.UNKNOWN_PROBLEM_ID, testproblem.getID() );
  }

  
  /**
   * tests the method {@link Problem#getMailTo()}
   */
  @Test
  public void testGetMailTo() {
    Assert.assertEquals( "jie.tao@iwr.fzk.de", testproblem.getMailTo() ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link Problem#getPluginID()}
   */
  @Test
  public void testGetPluginID() {
    Assert.assertEquals( "eu.geclise.core.reporting.test", testproblem.getPluginID() ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link Problem#hasReasons()}
   */
  @Test
  public void testHasReasons() {
   Assert.assertTrue( testproblem.hasReasons() );
  }

  /**
   * tests the method {@link Problem#hasSolutions()}
   */
  @Test
  public void testHasSolutions() {
    Assert.assertTrue( testproblem.hasSolutions());
  }

  /**
   * tests the method {@link Problem#getStatus()}
   */
  @Test
  public void testGetStatus() {
    IStatus status = testproblem.getStatus();
    Assert.assertNotNull( status );
    Assert.assertEquals( "test",status.getMessage()); //$NON-NLS-1$
  }
}
