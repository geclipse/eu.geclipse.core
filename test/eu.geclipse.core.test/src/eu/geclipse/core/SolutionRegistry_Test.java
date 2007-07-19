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

package eu.geclipse.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the functionality of each method in the 
 * class SolutionRegistry.
 * 
 */

public class SolutionRegistry_Test {
  SolutionRegistry solutionregistry;

  /**setup
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception
  {
    this.solutionregistry = SolutionRegistry.getRegistry();
  }

  /**
   * Test for the <code>eu.geclipse.core.SolutionRegistry#getRegistry/code>
   * method.
   * @author jie
   */
  
  @Test
  public void testGetRegistry()
  {
    SolutionRegistry newregistry;
    newregistry = SolutionRegistry.getRegistry();
    Assert.assertNotNull( newregistry );
  }

  /**
   * Test for the <code>eu.geclipse.core.SolutionRegistry#getSolution/code>
   * method.
   * @author jie
   */
  
  @Test
  public void testGetSolution()
  {
    ISolution solution;
    int solutionID;
    solutionID = SolutionRegistry.SERVER_DOWN;
    solution = this.solutionregistry.getSolution( solutionID );
    Assert.assertEquals( new Integer( SolutionRegistry.SERVER_DOWN ),
                         new Integer( solution.getID() ));
    Assert.assertEquals( "The server may be down. Contact the administrator.",solution.getText() ); //$NON-NLS-1$
  }

  /**
   * Test for the <code>eu.geclipse.core.SolutionRegistry#UniqueID/code>
   * method.
   * @author jie
   */
  
  @Test
  public void testUniqueID()
  {
    int id;
    id = SolutionRegistry.uniqueID ();
    Assert.assertEquals( new Integer( id ),new Integer( 1 ));
    id = SolutionRegistry.uniqueID ();
    id = SolutionRegistry.uniqueID ();
    Assert.assertEquals( new Integer( id ),new Integer( 3 ));
  }
}
