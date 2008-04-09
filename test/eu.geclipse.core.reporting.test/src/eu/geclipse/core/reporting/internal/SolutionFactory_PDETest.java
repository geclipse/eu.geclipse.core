/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
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

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * tests the methods in class {@link SolutionFactory}
 * 
 * @author tao-j
 */
public class SolutionFactory_PDETest {

  private static final String SOLUTION_ID 
    = "eu.geclipse.core.reporting.test.ExampleSolution1"; //$NON-NLS-1$
  private static SolutionFactory factory;

  /**
   * initialization
   * 
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    factory = SolutionFactory.getFactory();
  }

  /**
   * tests the method {@link SolutionFactory#getFactory()}
   */
  @Test
  public void testGetFactory() {
    Assert.assertNotNull( factory );
  }

  /**
   * tests the method {@link SolutionFactory#getSolution(String, String)}
   */
  @Test
  public void testGetSolution() {
    Solution solution = factory.getSolution( SOLUTION_ID, null );
    String expectedSolutionMessage = "This is the Example Solution 1"; //$NON-NLS-1$
    Assert.assertEquals( expectedSolutionMessage, solution.getDescription() );
  }
}
