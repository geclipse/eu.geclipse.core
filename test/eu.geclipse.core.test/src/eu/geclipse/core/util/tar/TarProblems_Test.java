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

package eu.geclipse.core.util.tar;

import org.junit.Assert;

import org.junit.Test;


/**tests the methods in the class {@link TarProblems}}
 * @author tao-j
 *
 */
public class TarProblems_Test {

  /**
   * tests the method {@link TarProblems#getProblem(int, Throwable)}
   */
  @Test
  public void testGetProblem() {
    TarProblems problem = new TarProblems();
    Throwable exc = new Throwable();
    int ID = TarProblems.BAD_HEADER_CHECKSUM;
    Assert.assertNotNull( problem.getProblem( ID, exc ) );
    Assert.assertEquals( "Bad checksum in tar entry, file might be corrupted", problem.getProblem( ID, exc ).getText() ); //$NON-NLS-1$
  }
}
