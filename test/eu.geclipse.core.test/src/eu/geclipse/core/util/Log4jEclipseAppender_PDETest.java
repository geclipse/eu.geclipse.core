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
 *    Jie Tao - test class (Plug-in test)
 *****************************************************************************/

package eu.geclipse.core.util;

import org.eclipse.core.runtime.ILog;
import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.internal.Activator;



/**tests the methods in the class {@link Log4jEclipseAppender}
 * @author tao-j
 *
 */
public class Log4jEclipseAppender_PDETest {

  private static Log4jEclipseAppender appender;
  /**initialization
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    ILog log = Activator.getDefault().getLog();
    appender = new Log4jEclipseAppender(log);
  }

  /**
   * tests the method {@link Log4jEclipseAppender#requiresLayout()}
   */
  @Test
  public void testRequiresLayout() {
   Assert.assertFalse( appender.requiresLayout() );
  }

  /**
  * tests the method {@link Log4jEclipseAppender#close()}
  */
  @Test
  public void testClose() {
    // no original implementation
  }

  /**
   * tests the method {@link Log4jEclipseAppender#Log4jEclipseAppender(ILog)}
   */
  @Test
  public void testLog4jEclipseAppender() {
    Assert.assertNotNull( appender );
  }
}
