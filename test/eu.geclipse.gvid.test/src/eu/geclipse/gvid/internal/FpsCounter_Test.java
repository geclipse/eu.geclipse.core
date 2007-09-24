/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Jie Tao, FZK - test class for software quality
 *****************************************************************************/

package eu.geclipse.gvid.internal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.lang.Thread;

/**
 * Test for the <code>eu.geclipse.gvid.internal.FpsCounter</code>
 * class.
 */

  public class FpsCounter_Test {
    private FpsCounter fpscounter;

  /**
   * @brief Set up the number of frames to test the FpaCounter class.
   * @throws Exception IOException
   */
  @Before
  public void setUp() throws Exception {
            this.fpscounter = new FpsCounter();
          }

  /**
   * @brief Test method for
   * {@link eu.geclipse.gvid.internal.FpsCounter_Test#getFps()}.
   * @throws Exception IOException
   */
      
   @Test
   public void testgetFps() throws Exception {
     long starttime,endtime;
     int i, counter, frames;
          
     starttime = System.currentTimeMillis();
     for ( i=0; i<100; i++ ) {
     this.fpscounter.incFrameCount();
     Thread.sleep (100);
     }
     endtime = System.currentTimeMillis();
     counter = this.fpscounter.getFps();
     frames =  (i * 1000 / (int) (endtime-starttime));
     Assert.assertEquals( new Integer( counter ), new Integer( frames ));
   }
}
