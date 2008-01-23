/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.core.model;

import java.util.Date;

/**
 * Interface for single grid test result.<br>
 * <br>
 * Basic implementation of this interface (in form of SOJO bean) is in
 * eu.geclipse.test.framework plug-in (<code>GridTestResult</code> class).
 */
public interface IGridTestResult {

  /**
   * Method to access String representation of test result (e.g OK, FAILED,
   * etc.).
   * 
   * @return test result
   */
  String getResult();

  /**
   * Method to access name of the tested resource (e.g. host's URL).
   * 
   * @return name of tested resource in form of String
   */
  String getResourceName();

  /**
   * Method to access information when this test was run.
   * 
   * @return date of test's run
   */
  Date getDate();

  /**
   * In case of complex tests this method returns sub-test name. If test was
   * simple test returned value will be name of the test.
   * 
   * @return sub-test name
   */
  String getSubTestName();
}
