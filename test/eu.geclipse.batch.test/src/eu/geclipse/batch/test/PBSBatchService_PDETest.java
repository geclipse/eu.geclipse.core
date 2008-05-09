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
package eu.geclipse.batch.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.geclipse.batch.Extensions;
import eu.geclipse.batch.IBatchService;
import eu.geclipse.batch.IBatchServiceDescription;
import eu.geclipse.batch.ui.BatchServiceManager;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Class to execute tests on the PBSWrapper.
 */
public class PBSBatchService_PDETest {
  /**
   * The PBS wrapper object.
   */
  private IBatchService pbsWrapper;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    List< IBatchServiceDescription > services = Extensions.getRegisteredBatchServiceDescriptions(); 
    
    // Do we have an implementation to open this batch service
    for ( IBatchServiceDescription description : services ) {
      if ( description.supportsService( "pbs" ) ) { //$NON-NLS-1$
        try {
          this.pbsWrapper = BatchServiceManager.getManager().createService( description, "test.batch" ); //$NON-NLS-1$
        } catch( ProblemException e ) {
          // No code needed
        }
      }
    }
    //this.pbsWrapper = new PBSBatchService( new PBSBatchServiceDescription(), "test.batch" ); //$NON-NLS-1$
  }

  /**
   * This method tests that all the methods of the PBSWrapper can be called
   * without setting up a connection to the pbs service first, without
   * crashing.
   */
  @SuppressWarnings("boxing")
  @Test
  public final void testPBSWrapperWithoutConnection() {
    boolean except = false;
    
    try {
      this.pbsWrapper.enableQueue( "test" ); //$NON-NLS-1$
    } catch( ProblemException e ) {
      except = true; 
    } 

    Assert.assertTrue( except );
      
    except = false;
    
    try {
      this.pbsWrapper.disableQueue( "test" ); //$NON-NLS-1$
    } catch( ProblemException e ) {
      except = true; 
    } 

    Assert.assertTrue( except );
  }

}
