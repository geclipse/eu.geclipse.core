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
 * This interface is the base for all classes that describe specialized
 * simple tests.
 *  
 * @author harald
 *
 */
public interface ISimpleTestDescription {
  /**
   * Create a simple test from this <code>ISimpleTestDescription</code>.
   * 
   * @return A new simple test that is created from the settings
   *         of this <code>ISimpleTestDescription</code>.
   */
  public ISimpleTest createSimpleTest();
  
  /**
   * Get the name of the simple test type that is described by this description. This
   * is a short name like "Ping" or "Trace Route" that is used in UI
   * components to reference this description.
   * 
   * @return The name of the type of simple tests that is described by this description.
   */
  public String getSimpleTestTypeName();

  /**
   * Returns whether the argument test matches this test.     
   * @param otherTest The test we are comparing against
   * @return Returns <code>true</code> if the test matches, <code>false</code>
   * otherwise.  
   */
  public boolean matches( final ISimpleTestDescription otherTest );
}
