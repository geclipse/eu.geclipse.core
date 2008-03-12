/* Copyright (c) 2006, 2007 g-Eclipse Consortium 
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

package eu.geclipse.core.project;

import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


/**tests the methods in the class {@link GridProjectBuilder}
 * @author tao-j
 *
 */
public class GridProjectBuilder_Test {

  private static GridProjectBuilder builder;
  
  /**initialization
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    builder = new GridProjectBuilder();
  }

  /**
   * tests the methods GridProjectBuilder#
   * build(int, java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
   * @throws CoreException 
   */
  @Test
  public void testBuildIntMapIProgressMonitor() throws CoreException {
    Assert.assertNull( builder.build( 0, null, null ) );
  }

  /**
   * tests the method {@link GridProjectBuilder#getID()}
   */
  @Test
  public void testGetID() {
    Assert.assertEquals( "eu.geclipse.core.project.GridProjectBuilder", GridProjectBuilder.getID() ); //$NON-NLS-1$
  }
}
