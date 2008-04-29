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

import eu.geclipse.batch.internal.WorkerNodeInfo;
import eu.geclipse.batch.IWorkerNodeInfo;

/**
 * Testing all the methods in the WorkerNodeInfo class
 * @author hgjermund
 *
 */
public class WorkerNodeInfo_Test {

  private static WorkerNodeInfo wnInfo;
  
  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    wnInfo = new WorkerNodeInfo( "geclipse.test.com", null, 0, null, null, null, null ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.WorkerNodeInfo#WorkerNodeInfo(java.lang.String, eu.geclipse.batch.IWorkerNodeInfo.WorkerNodeState, int, java.lang.String, java.lang.String, java.lang.String, java.util.List)}.
   */
  @Test
  public void testWorkerNodeInfo() {
    assertNotNull( wnInfo );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.WorkerNodeInfo#getNp()}.
   */
  @Test
  public void testGetNp() {
    wnInfo.setNp( 5 );
    assertTrue( 5 == wnInfo.getNp() );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.WorkerNodeInfo#setNp(int)}.
   */
  @Test
  public void testSetNp() {
    wnInfo.setNp( 15 );
    assertTrue( 15 == wnInfo.getNp() );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.WorkerNodeInfo#getProperties()}.
   */
  @Test
  public void testGetProperties() {
    wnInfo.setProperties( "property 1" ); //$NON-NLS-1$
    assertEquals( "property 1", wnInfo.getProperties() ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.WorkerNodeInfo#setProperties(java.lang.String)}.
   */
  @Test
  public void testSetProperties() {
    wnInfo.setProperties( "property 2" ); //$NON-NLS-1$
    assertEquals( "property 2", wnInfo.getProperties() ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.WorkerNodeInfo#getState()}.
   */
  @Test
  public void testGetState() {
    wnInfo.setState( IWorkerNodeInfo.WorkerNodeState.free );
    assertEquals( IWorkerNodeInfo.WorkerNodeState.free, wnInfo.getState() );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.WorkerNodeInfo#getStateAsString()}.
   */
  @Test
  public void testGetStateAsString() {
    wnInfo.setState(  IWorkerNodeInfo.WorkerNodeState.free );
    assertEquals( wnInfo.getStateAsString(), "free" ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.WorkerNodeInfo#setState(eu.geclipse.batch.IWorkerNodeInfo.WorkerNodeState)}.
   */
  @Test
  public void testSetState() {
    wnInfo.setState(  IWorkerNodeInfo.WorkerNodeState.busy );
    assertEquals( wnInfo.getState(), IWorkerNodeInfo.WorkerNodeState.busy );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.WorkerNodeInfo#getStatus()}.
   */
  @Test
  public void testGetStatus() {
    wnInfo.setStatus( "working" ); //$NON-NLS-1$
    assertEquals( wnInfo.getStatus(), "working" ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.WorkerNodeInfo#setStatus(java.lang.String)}.
   */
  @Test
  public void testSetStatus() {
    wnInfo.setStatus( "working2" ); //$NON-NLS-1$
    assertEquals( wnInfo.getStatus(), "working2" ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.WorkerNodeInfo#getType()}.
   */
  @Test
  public void testGetType() {
    wnInfo.setType( "type" ); //$NON-NLS-1$
    assertEquals( wnInfo.getType(), "type" ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.WorkerNodeInfo#setType(java.lang.String)}.
   */
  @Test
  public void testSetType() {
    wnInfo.setType( "type2" ); //$NON-NLS-1$
    assertEquals( wnInfo.getType(), "type2" ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.WorkerNodeInfo#getWnFQN()}.
   */
  @Test
  public void testGetWnFQN() {
    assertEquals( wnInfo.getWnFQN(), "geclipse.test.com" ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.WorkerNodeInfo#getStateFromString(java.lang.String)}.
   */
  @Test
  public void testGetStateFromString() {
    assertEquals( WorkerNodeInfo.getStateFromString( "free" ), IWorkerNodeInfo.WorkerNodeState.free ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.WorkerNodeInfo#getKernelVersion()}.
   */
  @Test
  public void testGetKernelVersion() {
    assertNotNull( wnInfo.getKernelVersion() );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.WorkerNodeInfo#getTotalMem()}.
   */
  @Test
  public void testGetTotalMem() {
    assertNotNull( wnInfo.getTotalMem() );
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.WorkerNodeInfo#getJobs()}.
   */
  @Test
  public void testGetJobs() {
    assertNull( wnInfo.getJobs() );
  }
}
