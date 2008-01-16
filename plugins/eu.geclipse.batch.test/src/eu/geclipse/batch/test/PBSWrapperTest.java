/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initialial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.geclipse.batch.BatchException;
import eu.geclipse.batch.BatchProblems;
import eu.geclipse.batch.IBatchService;
import eu.geclipse.batch.pbs.PBSBatchService;
import eu.geclipse.batch.pbs.PBSBatchServiceDescription;

/**
 * Class to execute tests on the PBSWrapper.
 */
public class PBSWrapperTest {
  /**
   * The PBS wrapper object.
   */
  private IBatchService pbsWrapper;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    this.pbsWrapper = new PBSBatchService( new PBSBatchServiceDescription(), null );
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
    // No code needed yet
  }

  /**
   * This method tests that all the methods of the PBSWrapper can be called
   * without setting up a connection to the pbs service first, without
   * crashing.
   */
  @SuppressWarnings("boxing")
  @Test
  public final void testPBSWrapperWithoutConnection() {
    try {
      this.pbsWrapper.enableQueue( "test" ); //$NON-NLS-1$
    } catch( BatchException e ) {
      Assert.assertEquals( BatchProblems.CONNECTION_FAILED, e.getProblem().getID() );
    } 

    try {
      this.pbsWrapper.disableQueue( "test" ); //$NON-NLS-1$
    } catch( BatchException e ) {
      Assert.assertEquals( BatchProblems.CONNECTION_FAILED, e.getProblem().getID() );
    } 
  }

}
