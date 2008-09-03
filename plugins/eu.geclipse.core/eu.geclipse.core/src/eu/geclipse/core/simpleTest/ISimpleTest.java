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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.core.simpleTest;

/**
 * This interface is the base of the simple test mechanisms
 * within g-Eclipse. 
 * 
 * @author harald
 *
 */
public interface ISimpleTest {

  /**
   * Get the simple test description that was used to create this test.
   * 
   * @return The description from which this test was created.
   */
  ISimpleTestDescription getDescription();
}
