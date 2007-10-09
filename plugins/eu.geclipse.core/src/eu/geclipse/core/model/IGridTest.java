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
 *      - Szymon Mueller     
 *****************************************************************************/
package eu.geclipse.core.model;

import java.util.Calendar;

import org.eclipse.core.runtime.jobs.IJobStatus;



/**
 * Interface for structural tests and simple tests.
 */
public interface IGridTest extends IGridElement {
  
  /**
   * Method to access date of a test run.
   * @return date on which this test was submitted
   */
  public Calendar getDate();
  
  public IGridJobSubmissionService getSubmissionService();
  
  public IGridJobDescription getJSDLDescription();
  
  /**
   * Method returns IGridTest representing it's parent (structural) test, or null if it is parent Test
   * @return
   */
  public IGridTest getParentTest();
  
  /**
   * Returns tested grid resource.
   * @return resource which was tested by this test
   */
  public IGridResource getTestedResource();
  
  public boolean isStructural();
  
  /**
   * Returns 
   * @return
   */
  public String getInterpretation();
  //TODO change this method to return enumeration type
}
