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

import java.io.IOException;
import java.net.InetAddress;


/**
 * @author harald
 *
 */
public class PingTest extends AbstractSimpleTest {

  /**
   * Create a new <code>PingTest</code> from the specified {@link ISimpleTestDescription}.
   * 
   * @param description The test description holding the information needed to create the 
   * test. This should always be a {@link PingTestDescription}.
   */
  public PingTest ( final ISimpleTestDescription description ) {
    super( description );
  }
  
  /**
   * Tries to reach a host and returns the time it took to reach it. 
   * 
   * @param address The machine to be pinged
   * @return Returns the time it took to reach the host in ms, -1 if the operation failed.
   */
  public double ping( final InetAddress address ) {
    boolean reached = false;
    double ret = -1;
    long start;
    long end;

    if ( null != address ) {
      start = System.nanoTime();
      try {
        reached = address.isReachable( 3000 );
      } catch( IOException e ) {
        // No code needed
      }
      end = System.nanoTime();
    
      if ( reached )
        ret = ( ( double ) ( end - start ) ) / 1000000; // Covert from ns to ms
    }
    
    return ret;
  }
  
}
