/*****************************************************************************
 * All rights reserved. This program and the accompanying materials
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
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

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ISolution;


/**
 * tests the methods in class {@link ReportingService}
 * 
 * @author tao-j
 */
public class ReportingService_PDETest {

  private static final String PROBLEM_ID 
    = "eu.geclipse.core.reporting.test.ExampleProblem"; //$NON-NLS-1$
  private static final String SOLUTION_ID 
    = "eu.geclipse.core.reporting.test.ExampleSolution1"; //$NON-NLS-1$
  private static ReportingService service;
  
  /**
   * initialization
   * 
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    service = ReportingService.getService();
  }

  /**
   * tests the methods {@link ReportingService#getService()}
   */
  @Test
  public void testGetService() {
    Assert.assertNotNull( service );
  }

  /**
   * tests the methods
   * {@link ReportingService#createProblem(String, Throwable, String, String)}
   */
  @Test
  public void testCreateProblem() {
    IProblem problem = service.createProblem( "test", null, null, null ); //$NON-NLS-1$
    Assert.assertNotNull( problem );
    Assert.assertEquals( "test", problem.getDescription() ); //$NON-NLS-1$
  }

  /**
   * tests the methods
   * {@link ReportingService#getProblem(String, String, Throwable, String)}
   */
  @Test
  public void testGetProblem() {
    IProblem problem = service.getProblem( PROBLEM_ID, null, null, null );
    String expectedMessage = "This is the Example Problem"; //$NON-NLS-1$
    Assert.assertEquals( expectedMessage, problem.getDescription() );
  }

  /**
   * tests the methods
   * {@link ReportingService#createSolution(String, eu.geclipse.core.reporting.ISolver)}
   */
  @Test
  public void testCreateSolution() {
    ISolution solution = service.createSolution( "test", null ); //$NON-NLS-1$
    Assert.assertNotNull( solution );
    Assert.assertEquals( "test", solution.getDescription() ); //$NON-NLS-1$
  }

  /**
   * tests the methods {@link ReportingService#getSolution(String, String)}
   */
  @Test
  public void testGetSolution() {
    Assert.assertNotNull( service.getSolution( SOLUTION_ID, null ) );
  }
}
