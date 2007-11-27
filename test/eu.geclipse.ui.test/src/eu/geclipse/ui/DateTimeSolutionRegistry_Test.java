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

package eu.geclipse.ui;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.ISolution;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.ui.widgets.DateTimeText;


/**tests the methods in class {@link DateTimeSolutionRegistry}
 * @author tao-j
 *
 */
public class DateTimeSolutionRegistry_Test {

  private static DateTimeSolutionRegistry registry;
  /**initialization
   * @throws Exception
   */
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    registry = DateTimeSolutionRegistry.getRegistry();
  }

  /**
   * tests the method {@link DateTimeSolutionRegistry#getRegistry()}
   */
  @Test
  public void testGetRegistry() {
    Assert.assertNotNull( registry );
  }
}
