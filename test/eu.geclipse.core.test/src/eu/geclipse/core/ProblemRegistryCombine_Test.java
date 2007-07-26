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
import eu.geclipse.core.internal.Activator;
import org.eclipse.core.runtime.IStatus;

/**
 * This class tests the interaction of methods in the class ProblrmRegistry.
 * and AbstractProblem. The methods are also tested individually in associated
 * test classes, i.e. ProblemRegistry_Test and AbstractProblem_Test.
 * Workflow: 
 * 1. create a problem, set the reason
 * 2. create another problem
 * 3. get the id, status of the second problem
 * 4. get the id, status of the first problem
 */

public class ProblemRegistryCombine_Test {

  /**setup
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception
  {
    // nothing to setup currently
  }
 
  /**
   * Tested methods include: <code>eu.geclipse.core.ProblrmRegistry#uniqueID/code>, 
   * <code>eu.geclipse.core.ProblrmRegistry#createProblem/code>,
   * <code>eu.geclipse.core.AbstractProblem#getID/code>, 
   * <code>eu.geclipse.core.AbstractProblem#setReason/code>, 
   * <code>eu.geclipse.core.AbstractProblem#getStatus/code>
   */
  
  @Test
  public void testCreateProblemGetsolutions ()
  {
    IProblem problem1,problem2;
    int id;
    Throwable exec = new Throwable ();
    IStatus status;
    id = ProblemRegistry.uniqueID();
    problem1 = ProblemRegistry.createProblem( id, "test problem1", exec, null, Activator.PLUGIN_ID ); //$NON-NLS-1$
    problem1.addReason( "unknown" ); //$NON-NLS-1$
    id = ProblemRegistry.uniqueID();
    problem2 = ProblemRegistry.createProblem( id, "test problem2", exec, null, Activator.PLUGIN_ID ); //$NON-NLS-1$
    Assert.assertNotNull( problem1 );
    Assert.assertNotNull( problem2 );
    Assert.assertEquals( new Integer( 1 ),new Integer( problem1.getID() ));
    Assert.assertEquals( new Integer( 2 ),new Integer( problem2.getID() ));
    Assert.assertEquals( problem1.getText(),"test problem1" ); //$NON-NLS-1$
    Assert.assertEquals( problem2.getText(),"test problem2" ); //$NON-NLS-1$
    status = problem1.getStatus();
    Assert.assertEquals( new Integer( 4 ),new Integer( status.getSeverity() ));
    Assert.assertEquals( "test problem1",status.getMessage() ); //$NON-NLS-1$
    status = problem2.getStatus();
    Assert.assertEquals( "test problem2",status.getMessage() ); //$NON-NLS-1$
  }
}
