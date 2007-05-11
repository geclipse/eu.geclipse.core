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
 * This class tests the functionality of each method in the class ProblrmRegistry.
 * The methods are handled as a single unit. Combined tests are performed in another
 * test class, i.e. ProblemRegistryCombine_Test.
 */

public class ProblemRegistry_Test {
 
  @Before
  public void setUp() throws Exception
  {
    // currently no need of setup
  }

  /**
   * Test for the <code>eu.geclipse.core.ProblrmRegistry#getRegistry/code>
   * method.
   * @author jie
   */
  
  @Test
  public void testGetRegistry()
  {
    ProblemRegistry newregistry;
    newregistry = ProblemRegistry.getRegistry();
    Assert.assertNotNull( newregistry );
  }

  /**
   * Test for the <code>eu.geclipse.core.ProblrmRegistry#createProblem/code>
   * method.
   * @author jie
   */ 
  @Test
  public void testCreateProblem()
  {
    IProblem problem;
    int id = 1;
    String message = "test problem"; //$NON-NLS-1$
    Throwable exec = new Throwable ();
    String plugin_id = "eu.geclipse.core"; //$NON-NLS-1$  
    String reason = "CA not available"; //$NON-NLS-1$ 
    problem = ProblemRegistry.createProblem( id,message,exec,null,plugin_id );
    Assert.assertNotNull( problem );
    Assert.assertEquals( message,problem.getText() );
    problem.setReason( reason );
    Assert.assertEquals( problem.getText(),"test problem: CA not available" ); //$NON-NLS-1$
  }

  /**
   * Test for the <code>eu.geclipse.core.ProblrmRegistry#getProblem/code>
   * method (three parameters).
   * @author jie
   */ 
  
  @Test
  public void testGetProblemIntThrowableString()
  {
    //  can not reference to this method in the private class 
  }

  /**
   * Test for the <code>eu.geclipse.core.ProblrmRegistry#getProblem/code>
   * method (two parameters).
   * @author jie
   */ 
  
  @Test
  public void testGetProblemIntThrowable()
  {
    // can not reference to this method in the private class 
  }

  /**
   * Test for the <code>eu.geclipse.core.ProblrmRegistry#findProblem/code>
   * method.
   * @author jie
   */ 
  
  @Test
  public void testFindProblem()
  {
    //  can not reference to this method in the private class 
  }

  @Test
  public void testUniqueID()
  {
    int id;
    id = ProblemRegistry.uniqueID ();
    Assert.assertEquals( new Integer( id ),new Integer( 1 ));
    id = ProblemRegistry.uniqueID ();
    id = ProblemRegistry.uniqueID ();
    Assert.assertEquals( new Integer( id ),new Integer( 3 ));
  }
}
