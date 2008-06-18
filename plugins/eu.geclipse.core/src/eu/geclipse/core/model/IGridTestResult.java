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

import java.io.InputStream;
import java.util.Date;

/**
 * Interface for single grid test result.<br>
 * <br>
 * Basic implementation of this interface (in form of SOJO bean) is in
 * eu.geclipse.test.framework plug-in (<code>GridTestResult</code> class).
 */
public interface IGridTestResult {

  /**
   * Method to access specific test result. This may be just text, image, audio,
   * video or application. See also {@link IGridTestResult#getResultType()},
   * {@link IGridTestResult#getResultInputStream()} and
   * {@link IGridTestResult#getStringResult()}.
   * 
   * @return test result
   */
  Object getResult();

  /**
   * Method to access specific test result in form of InputStream. This input
   * may be used to show results in specific editor. This method may return
   * null.
   * 
   * @return test result's input stream
   */
  InputStream getResultInputStream();

  /**
   * Method to access type of result's data. Preferably this should be file
   * extension that will determine Eclipse editor in which data should be
   * opened. See also {@link IGridTestResult#getResult()} and
   * {@link IGridTestResult#getResultInputStream()}.
   * 
   * @return extension of file to which test result's input stream may be saved
   */
  String getResultType();

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
  Date getRunDate();

  /**
   * In case of complex tests this method returns sub-test name. If test was
   * simple test returned value will be name of the test.
   * 
   * @return sub-test name
   */
  String getSubTestName();

  /**
   * Textual interpretation of test's result (see
   * {@link IGridTestResult#getResult()}).
   * 
   * @return String representing human-readable summary of test's result
   */
  String getStringResult();
}
