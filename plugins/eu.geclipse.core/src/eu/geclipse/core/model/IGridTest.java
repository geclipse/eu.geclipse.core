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

import java.util.Calendar;



/**
 * Interface for structural tests and simple tests.
 */
public interface IGridTest extends IGridElement {
  
  /**
   * Method to access date of a test run.
   * @return date on which this test was submitted
   */
  public Calendar getDate();
  
  /**
   * Returns tested grid resource.
   * @return resource which was tested by this test
   */
  public IGridResource getTestedResource();
  
  /**
   * Returns 
   * @return
   */
  public String getInterpretation();
  //TODO change this method to return enumeration type
}
