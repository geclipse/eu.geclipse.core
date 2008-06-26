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
    queue = new QueueInfo( "queue1", 0, null, null, null, 0, 0, null,  //$NON-NLS-1$
                           IQueueInfo.QueueState.D, IQueueInfo.QueueRunState.R );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#QueueInfo(java.lang.String, 
   * int, java.lang.String, java.lang.String, java.lang.String, int, int, 
   * java.lang.String, eu.geclipse.batch.IQueueInfo.QueueState, eu.geclipse.batch.IQueueInfo.QueueRunState)}.
   */
  @Test
  public void testQueueInfo() {
    assertNotNull( queue );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getLm()}.
   */
  @Test
  public void testGetLm() {
    queue.setLm( "test" ); //$NON-NLS-1$
    assertTrue( "test".equals( queue.getLm() ) ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#setLm(java.lang.String)}.
   */
  @Test
  public void testSetLm() {
    queue.setLm( "test2" ); //$NON-NLS-1$
    assertTrue( "test2".equals( queue.getLm() ) ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getMemory()}.
   */
  @Test
  public void testGetMemory() {
    queue.setMemory( 100 ); 
    assertTrue( 100 == queue.getMemory() );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#setMemory(int)}.
   */
  @Test
  public void testSetMemory() {
    queue.setMemory( 101 ); 
    assertTrue( 101 == queue.getMemory() );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getNode()}.
   */
  @Test
  public void testGetNode() {
    queue.setNode( "node" ); //$NON-NLS-1$ 
    assertTrue( "node".equals( queue.getNode() ) ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#setNode(java.lang.String)}.
   */
  @Test
  public void testSetNode() {
    queue.setNode( "node1" ); //$NON-NLS-1$ 
    assertTrue( "node1".equals( queue.getNode() ) ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getQue()}.
   */
  @Test
  public void testGetQue() {
    queue.setQue( 100 ); 
    assertTrue( 100 == queue.getQue() ); 
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#setQue(int)}.
   */
  @Test
  public void testSetQue() {
    queue.setQue( 101 ); 
    assertTrue( 101 == queue.getQue() ); 
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getRun()}.
   */
  @Test
  public void testGetRun() {
    queue.setRun( 100 ); 
    assertTrue( 100 == queue.getRun() ); 
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#setRun(int)}.
   */
  @Test
  public void testSetRun() {
    queue.setRun( 101 ); 
    assertTrue( 101 == queue.getRun() ); 
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getState()}.
   */
  @Test
  public void testGetState() {
    queue.setState( QueueInfo.QueueState.D ); 
    assertEquals( QueueInfo.QueueState.D, queue.getState() ); 
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#setState(eu.geclipse.batch.IQueueInfo.QueueState)}.
   */
  @Test
  public void testSetState() {
    queue.setState( QueueInfo.QueueState.E ); 
    assertEquals( QueueInfo.QueueState.E, queue.getState() ); 
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getRunState()}.
   */
  @Test
  public void testGetRunState() {
    queue.setRunState( QueueInfo.QueueRunState.R ); 
    assertEquals( QueueInfo.QueueRunState.R, queue.getRunState() ); 
  }

  /**
   * Test method for 
   * {@link eu.geclipse.batch.internal.QueueInfo#setRunState(eu.geclipse.batch.IQueueInfo.QueueRunState)}.
   */
  @Test
  public void testSetRunState() {
    queue.setRunState( QueueInfo.QueueRunState.S ); 
    assertEquals( QueueInfo.QueueRunState.S, queue.getRunState() ); 
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getTimeCPU()}.
   */
  @Test
  public void testGetTimeCPU() {
    queue.setTimeCPU( "10:10" );  //$NON-NLS-1$
    assertTrue( "10:10".equals( queue.getTimeCPU() ) );  //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#setTimeCPU(java.lang.String)}.
   */
  @Test
  public void testSetTimeCPU() {
    queue.setTimeCPU( "11:10" );  //$NON-NLS-1$
    assertTrue( "11:10".equals( queue.getTimeCPU() ) );  //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getTimeWall()}.
   */
  @Test
  public void testGetTimeWall() {
    queue.setTimeWall( "10:10" );  //$NON-NLS-1$
    assertTrue( "10:10".equals( queue.getTimeWall() ) );  //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#setTimeWall(java.lang.String)}.
   */
  @Test
  public void testSetTimeWall() {
    queue.setTimeWall( "11:10" );  //$NON-NLS-1$
    assertTrue( "11:10".equals( queue.getTimeWall() ) );  //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.QueueInfo#getQueueName()}.
   */
  @Test
  public void testGetQueueName() {
    assertTrue( "queue1".equals( queue.getQueueName() ) ); //$NON-NLS-1$
  }
}
