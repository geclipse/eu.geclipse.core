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
 * A simple test description that is dedicated to ping. 
 * 
 * @author harald
 *
 */
public class PingTestDescription implements ISimpleTestDescription {

  public ISimpleTest createSimpleTest() {
    return new PingTest( this );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.simpleTest.ISimpleTestDescription#getSimpleTestTypeName()
   */
  public String getSimpleTestTypeName() {
    return Messages.getString( "PingTestDescription.testTypeName" ); //$NON-NLS-1$
  }

  /**
   * Returns whether the argument test matches this test.     
   * @param otherTest The test we are comparing against
   * @return Returns <code>true</code> if the test matches, <code>false</code>
   * otherwise.  
   */
  public boolean matches( final ISimpleTestDescription otherTest ) {
    boolean result = false;
    
    if ( otherTest instanceof PingTestDescription ) {
      result = true;
    }
    
    return result;
    
  }

  
}
