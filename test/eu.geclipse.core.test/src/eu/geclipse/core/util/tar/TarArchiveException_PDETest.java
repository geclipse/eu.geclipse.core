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

package eu.geclipse.core.util.tar;

import org.junit.Assert;

import org.junit.Test;


/**tests the methods in the class {@link TarArchiveException}
 * @author tao-j
 *
 */
public class TarArchiveException_PDETest {

  /**
   * tests the method {@link TarArchiveException#TarArchiveException(int)}
   */
  @Test
  public void testTarArchiveExceptionInt() {
    TarArchiveException exce = new TarArchiveException(TarProblems.UNSPECIFIED_IO_PROBLEM);
    Assert.assertNotNull( exce );
  }

  /**
   * tests the method {@link TarArchiveException#TarArchiveException(int, String)}
   */
  @Test
  public void testTarArchiveExceptionIntString() {
    TarArchiveException exce = new TarArchiveException(TarProblems.UNSPECIFIED_IO_PROBLEM,"this is a test"); //$NON-NLS-1$
    Assert.assertNotNull( exce );
  }

  /**
   * tests the method {@link TarArchiveException#TarArchiveException(int, Throwable)}
   */
  @Test
  public void testTarArchiveExceptionIntThrowable() {
    Throwable thr = new Throwable();
    TarArchiveException exce = new TarArchiveException(TarProblems.UNSPECIFIED_IO_PROBLEM,thr);
    Assert.assertNotNull( exce );

  }
}
