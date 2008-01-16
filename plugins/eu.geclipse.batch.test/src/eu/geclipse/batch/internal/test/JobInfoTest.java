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
 *
 *****************************************************************************/
package eu.geclipse.batch.internal.test;

import eu.geclipse.batch.internal.BatchJobInfo;
import junit.framework.TestCase;

/**
 * @author cs05ce1
 */
public class JobInfoTest extends TestCase {

  private BatchJobInfo job;

  /**
   * 
   * @param name
   */
  public JobInfoTest( final String name ) {
    super( name );
  }

  /**
   * @throws java.lang.Exception
   */
  @Override
  protected void setUp() throws Exception {
    this.job = new BatchJobInfo( null, null, null, null, null, null, null );
  }

  /**
   * @throws java.lang.Exception
   */
  @Override
  protected void tearDown() throws Exception {
    // No code needed
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.BatchJobInfo#getQueueName()}.
   */
  public void testGetQueueName() {
    this.job.setQueueName( null );
    assertNotNull( "Queue Name is set to NULL", this.job.getQueueName() ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.BatchJobInfo#getStatus()}.
   */
  public void testGetStatus() {
    this.job.setStatus( null );
    assertNotNull( "Status is set to NULL", this.job.getStatus() ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.BatchJobInfo#getTimeUse()}.
   */
  public void testGetTimeUse() {
    assertNotNull( "Time Use is set to NULL", this.job.getTimeUse() ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.BatchJobInfo#getUserAccount()}.
   */
  public void testGetUserAccount() {
    this.job.setUserAccount( null );
    assertNotNull( "User Account is set to NULL", this.job.getUserAccount() ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.BatchJobInfo#getJobId()}.
   */
  public void testGetJobId() {
    assertNotNull( "Job ID is set to NULL", this.job.getJobId() ); //$NON-NLS-1$
  }

  /**
   * Test method for {@link eu.geclipse.batch.internal.BatchJobInfo#getJobName()}.
   */
  public void testGetJobName() {
    assertNotNull( "Job Name is set to NULL", this.job.getJobName() ); //$NON-NLS-1$
  }
}
