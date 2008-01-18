/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
package eu.geclipse.batch.internal.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.batch.IQueueInfo;
import eu.geclipse.batch.internal.QueueInfo;

/**
 * Testing all the methods in the QueueInfo class
 * @author hgjermund
 *
 */
public class QueueInfo_Test {

  private static QueueInfo queue;
  
  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    queue = new QueueInfo( "queue1", 0, null, null, null, 0, 0, null, 
                           IQueueInfo.QueueState.D, IQueueInfo.QueueRunState.R );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#QueueInfo(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, eu.geclipse.batch.IQueueInfo.QueueState, eu.geclipse.batch.IQueueInfo.QueueRunState)}.
   */
  @Test
  public void testQueueInfo() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getLm()}.
   */
  @Test
  public void testGetLm() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#setLm(java.lang.String)}.
   */
  @Test
  public void testSetLm() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getMemory()}.
   */
  @Test
  public void testGetMemory() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#setMemory(int)}.
   */
  @Test
  public void testSetMemory() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getNode()}.
   */
  @Test
  public void testGetNode() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#setNode(java.lang.String)}.
   */
  @Test
  public void testSetNode() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getQue()}.
   */
  @Test
  public void testGetQue() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#setQue(int)}.
   */
  @Test
  public void testSetQue() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getRun()}.
   */
  @Test
  public void testGetRun() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#setRun(int)}.
   */
  @Test
  public void testSetRun() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getState()}.
   */
  @Test
  public void testGetState() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getStateAsString()}.
   */
  @Test
  public void testGetStateAsString() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#setState(eu.geclipse.batch.IQueueInfo.QueueState)}.
   */
  @Test
  public void testSetState() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getRunState()}.
   */
  @Test
  public void testGetRunState() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getRunStateAsString()}.
   */
  @Test
  public void testGetRunStateAsString() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#setRunState(eu.geclipse.batch.IQueueInfo.QueueRunState)}.
   */
  @Test
  public void testSetRunState() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getTimeCPU()}.
   */
  @Test
  public void testGetTimeCPU() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#setTimeCPU(java.lang.String)}.
   */
  @Test
  public void testSetTimeCPU() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getTimeWall()}.
   */
  @Test
  public void testGetTimeWall() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#setTimeWall(java.lang.String)}.
   */
  @Test
  public void testSetTimeWall() {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getQueueName()}.
   */
  @Test
  public void testGetQueueName() {
    fail( "Not yet implemented" );
  }
}
