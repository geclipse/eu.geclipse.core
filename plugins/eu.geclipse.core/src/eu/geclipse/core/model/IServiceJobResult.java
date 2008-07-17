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
public interface IServiceJobResult {

  /**
   * Method to access information when this test was run.
   * 
   * @return date of test's run
   */
  Date getRunDate();

  /**
   * Method to access name of the tested resource (e.g. host's URL).
   * 
   * @return name of tested resource in form of String
   */
  String getResourceName();

  /**
   * In case of complex tests this method returns sub-test name. If test was
   * simple test returned value will be name of the test.
   * 
   * @return sub-test name
   */
  String getSubTestName();

  /**
   * Method to access type of result's data. Preferably this should be file
   * extension that will determine Eclipse editor in which data should be
   * opened. See also {@link IServiceJob#getInputStreamForResult(IServiceJobResult)}
   * and {@link IServiceJobResult#getResultRawData()}.
   * 
   * @return extension of file to which test result's input stream may be saved
   *         (preferably without "." at the beginning, e.g. "TXT", not ".TXT")
   */
  String getResultType();

  /**
   * Method to access content of Output > Result > ResultData GTDL element
   * corresponding to this test result instance. Returned String is unmodified
   * content taken from GTDL file.
   * 
   * @return string content of Output > Result > ResultData GTDL element
   */
  String getResultRawData();

  /**
   * Textual interpretation of test's result.
   * 
   * @return String representing human-readable summary of test's result
   */
  String getResultSummary();

  /**
   * Human-readable test's result. Represents either the state in which test is
   * (pending, running...) or - in case when test was finished - test's result
   * in form of String (OK, ERROR, WARRNING...).
   * 
   * @return String representing human-readable summary of test's result
   */
  String getResultEnum();
}
