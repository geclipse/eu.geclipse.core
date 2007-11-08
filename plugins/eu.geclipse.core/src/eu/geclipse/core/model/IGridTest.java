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
import java.util.List;

import org.eclipse.core.runtime.jobs.IJobStatus;



/**
 * Interface for structural tests and simple tests.
 */
public interface IGridTest {
  
  public enum TestType{
    SAM,
    OTHER,
    SINGLE
  }
  
  /**
   * Method returning test name
   * @return String
   */
  public String getName();
  
  /**
   * Method returning type of the test
   * @return TestType
   */
  public TestType getType();
  
  /*
   * Method responsible for returning children
   */
  //public List<IGridTest> getChildrenTests();
  
  //public String getType();
  
  //public boolean hasChildrenTests();
  

}
