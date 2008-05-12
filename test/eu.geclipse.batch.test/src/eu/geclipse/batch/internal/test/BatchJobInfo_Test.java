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
 *      - Christodoulos Efstathiades (cs05ce1@cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.internal.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.batch.BatchJobInfo;
import eu.geclipse.batch.IBatchJobInfo;

/**
 * @author cs05ce1
 */
public class BatchJobInfo_Test {

  private static BatchJobInfo job;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    job = new BatchJobInfo( "job1", "job", null, null, null, null, null ); //$NON-NLS-1$ //$NON-NLS-2$
  }

  /**
   * Test method for {@link eu.geclipse.batch.BatchJobInfo#getQueueName()}.
   */
  @Test
  public void testGetQueueName() {
    job.setQueueName( "queue" ); //$NON-NLS-1$
    assertEquals( job.getQueueName(), "queue" ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.BatchJobInfo#getStatus()}.
   */
  @Test
  public void testGetStatus() {
    job.setStatus( IBatchJobInfo.JobState.H );
    assertEquals( job.getStatus(), IBatchJobInfo.JobState.H );
  }

  /**
   * Test method for {@link eu.geclipse.batch.BatchJobInfo#getTimeUse()}.
   */
  @Test
  public void testGetTimeUse() {
    job.setTimeUse( "100" ); //$NON-NLS-1$
    assertEquals( job.getTimeUse(), "100" ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.BatchJobInfo#getUserAccount()}.
   */
  @Test
  public void testGetUserAccount() {
    job.setUserAccount( "user1" ); //$NON-NLS-1$
    assertEquals( job.getUserAccount(), "user1" ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.BatchJobInfo#getJobId()}.
   */
  @Test
  public void testGetJobId() {
    assertEquals( job.getJobId(), "job1" ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.BatchJobInfo#getJobName()}.
   */
  @Test
  public void testGetJobName() {
    assertEquals( job.getJobName(), "job" ); //$NON-NLS-1$
  }
}
