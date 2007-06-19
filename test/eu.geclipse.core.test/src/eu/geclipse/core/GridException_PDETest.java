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
 *    Jie Tao - test class (Plug-in test)
 *****************************************************************************/

package eu.geclipse.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a test class for the functionality of each method in the 
 * class GridException.
 * @author jie
 *
 */

public class GridException_PDETest {
  GridException gridexception;
  CoreProblems coreproblem;

  @Before
  public void setUp() throws Exception
  {
    this.coreproblem = new CoreProblems();
  }

  /**
   * Test for the <code>eu.geclipse.core.GridException#gridException/code>
   * (1 parameters: int). 
   * 
   * @author jie
   */
  
  @Test
  public void testGridExceptionInt()
  {
    int problem_id = 5;
    StackTraceElement[] stacktraceelements;
    this.gridexception = new GridException( problem_id );
    Assert.assertNotNull( this.gridexception );
    Throwable exc = new Throwable ();
    stacktraceelements = exc.getStackTrace();
    Assert.assertEquals( "eu.geclipse.core.GridException_PDETest",stacktraceelements[0].getClassName() ); //$NON-NLS-1$
    Assert.assertEquals( "testGridExceptionInt",stacktraceelements[0].getMethodName() ); //$NON-NLS-1$ 
  }

  /**
   * Test for the <code>eu.geclipse.core.GridException#gridException/code>
   * (2 parameters: int, string). 
   * 
   * @author jie
   */
  
  @Test
  public void testGridExceptionIntString()
  {
    int problem_id = CoreProblems.JOB_SUBMISSION_FAILED;
    StackTraceElement[] stacktraceelements;
    this.gridexception = new GridException( problem_id,"TEST" ); //$NON-NLS-1$
    Assert.assertNotNull( this.gridexception );
    Assert.assertEquals( "Job Submission failed",this.gridexception.getMessage() ); //$NON-NLS-1$
    Throwable exc = new Throwable ();
    stacktraceelements = exc.getStackTrace();
    Assert.assertEquals( "eu.geclipse.core.GridException_PDETest",stacktraceelements[0].getClassName() ); //$NON-NLS-1$
    Assert.assertEquals( "testGridExceptionIntString",stacktraceelements[0].getMethodName() ); //$NON-NLS-1$ 
  }

  /**
   * Test for the <code>eu.geclipse.core.GridException#gridException/code>
   * (2 parameters: int, thrwable). 
   * 
   * @author jie
   */
  
  @Test
  public void testGridExceptionIntThrowable()
  {
    int problem_id = 2;
    StackTraceElement[] stacktraceelements;
    Throwable exc = new Throwable ();
    this.gridexception = new GridException( problem_id,exc );
    Assert.assertNotNull( this.gridexception );
    stacktraceelements = exc.getStackTrace();
    Assert.assertEquals( "eu.geclipse.core.GridException_PDETest",stacktraceelements[0].getClassName() ); //$NON-NLS-1$
    Assert.assertEquals( "testGridExceptionIntThrowable",stacktraceelements[0].getMethodName() ); //$NON-NLS-1$ 
  }

  /**
   * Test for the <code>eu.geclipse.core.GridException#gridException/code>
   * (3 parameters: int, thrwable, string). 
   * 
   * @author jie
   */
  
  @Test
  public void testGridExceptionIntThrowableString()
  {
    int problem_id = CoreProblems.CONNECTION_FAILED;
    StackTraceElement[] stacktraceelements;
    Throwable exc = new Throwable ();
    this.gridexception = new GridException( problem_id,exc,"test" ); //$NON-NLS-1$
    Assert.assertNotNull( this.gridexception );
    Assert.assertEquals("Unable to establish connection",this.gridexception.getMessage() ); //$NON-NLS-1$
    stacktraceelements = exc.getStackTrace();
    Assert.assertEquals( "eu.geclipse.core.GridException_PDETest",stacktraceelements[0].getClassName() ); //$NON-NLS-1$
    Assert.assertEquals( "testGridExceptionIntThrowableString",stacktraceelements[0].getMethodName() ); //$NON-NLS-1$ 
  }

  /**
   * Test for the <code>eu.geclipse.core.GridException#gridException/code>
   * and <code>eu.geclipse.core.GridException#getProblem/code>. 
   * 
   * @author jie
   */
  
  @Test
  public void testGridExceptionIProblem()
  {
    IProblem problem;
    int problem_id;
    StackTraceElement[] stacktraceelements;
    Throwable exc = new Throwable ();
    problem_id = CoreProblems.CONNECTION_TIMEOUT;
    problem = this.coreproblem.getProblem( problem_id, exc );
    this.gridexception = new GridException( problem );
    Assert.assertEquals("A timeout has occurred on a socket read or accept",this.gridexception.getMessage() ); //$NON-NLS-1$
    Assert.assertEquals( problem,this.gridexception.getProblem() );
    stacktraceelements = exc.getStackTrace();
    Assert.assertEquals( "eu.geclipse.core.GridException_PDETest",stacktraceelements[0].getClassName() ); //$NON-NLS-1$
    Assert.assertEquals( "testGridExceptionIProblem",stacktraceelements[0].getMethodName() ); //$NON-NLS-1$ 
  }
}
