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
 *    Jie Tao - test class (Junit test)
 *****************************************************************************/

package eu.geclipse.gvid.internal;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


/**test class for {@link GVidStatsEvent}
 * @author tao-j
 *
 */
public class GVidStatsEvent_Test {
  
  private static GVidStatsEvent stateevent;

  /**initial setups; create a GVidStateEvent class
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    GVidStatsEvent_Test source = new GVidStatsEvent_Test();
    stateevent = new GVidStatsEvent(source,1,1,1);
  }

  /**tests the method {@link GVidStatsEvent#GVidStatsEvent(Object, int, long, long)}
   * 
   */
  @Test
  public void testGVidStatsEvent() {
    Assert.assertNotNull( stateevent );
  }

  /**tests the method {@link GVidStatsEvent#getFps()}
   * 
   */
  @Test
  public void testGetFps() {
    Assert.assertEquals( new Integer( 1 ), new Integer( stateevent.getFps()) );
  }

  /**tests the method {@link GVidStatsEvent#getRecvSpeed()}
   * 
   */
  @Test
  public void testGetRecvSpeed() {
    Assert.assertEquals( new Long( 1 ), new Long( stateevent.getRecvSpeed()) );
  }
  
  /**tests the method {@link GVidStatsEvent#getSendSpeed()}
   * 
   */

  @Test
  public void testGetSendSpeed() {
    Assert.assertEquals( new Long( 1 ), new Long( stateevent.getSendSpeed()));
  }
}
