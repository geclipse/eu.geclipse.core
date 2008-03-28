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

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.ICoreProblems;


/**
 * @author tao-j
 *
 */
/**tests the methods in class {@link ProblemFactory}
 * @author tao-j
 *
 */
public class ProblemFactory_PDETest {

  private static ProblemFactory factory;
  
  /**initialization
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    factory = ProblemFactory.getFactory();
  }

  /**
   * tests the method {@link ProblemFactory#getFactory()}
   */
  @Test
  public void testGetFactory() {
    Assert.assertNotNull( factory );
  }

  /**
   * tests the method {@link ProblemFactory#getProblem(String, String, Throwable, String)}
   */
  @Test
  public void testGetProblem() {
    Problem  problem = factory.getProblem( ICoreProblems.AUTH_CERTIFICATE_LOAD_FAILED, 
        "test", null, "eu.geclipse.core.reporting.test" ); //$NON-NLS-1$ //$NON-NLS-2$
    Assert.assertNotNull( problem );
    Assert.assertEquals( "test", problem.getDescription() ); //$NON-NLS-1$
  }
}
