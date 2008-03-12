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
 *    Jie Tao - test class (Junit test)
 *****************************************************************************/

package eu.geclipse.core.reporting.internal;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


/**tests the method {@link Solution}
 * @author tao-j
 *
 */


public class Solution_Test {

  private static String UNKNOWN_SOLUTION_FOR_TEST = "eu.geclipse.core.reporting.test.unknown"; //$NON-NLS-1$
  private static Solution solution;
  
  /**initialization
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    solution = new Solution(UNKNOWN_SOLUTION_FOR_TEST,"test",null); //$NON-NLS-1$
  }

  /**
   * tests the method {@link Solution#Solution(String, String, eu.geclipse.core.reporting.ISolver)}
   */
  @Test
  public void testSolution() {
    Assert.assertNotNull( solution );
  }

  /**
   * tests the method {@link Solution#getDescription()}
   */
  @Test
  public void testGetDescription() {
   Assert.assertEquals( "test", solution.getDescription() ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link Solution#getID()}
   */
  @Test
  public void testGetID() {
    Assert.assertEquals( UNKNOWN_SOLUTION_FOR_TEST, solution.getID() );
  }

  /**
   * tests the method {@link Solution#isActive()}
   */
  @Test
  public void testIsActive() {
   Assert.assertFalse( solution.isActive() );
  }

  /**
   * tests the method {@link Solution#
   * setInitializationData(org.eclipse.core.runtime.IConfigurationElement, String, Object)}
   * @throws CoreException 
   */
  @Test
  public void testSetInitializationData() throws CoreException {
    solution.setInitializationData( null, "test", null ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link Solution#solve()}
   * @throws InvocationTargetException 
   */
  @Test
  public void testSolve() throws InvocationTargetException {
   solution.solve();
  }
}
