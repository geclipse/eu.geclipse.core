/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
 *    Jie Tao      - test class (Plug-in test)
 *    Ariel Garcia - updated to new problem reporting
 *****************************************************************************/

package eu.geclipse.core.auth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ReportingPlugin;


/**
 * This class tests all the constructors in the AuthenticationException class.
 * 
 * @author tao-j
 */
public class AuthenticationException_PDETest {

  IProblem problem;
  String problemId;
  String problemDesc;
  
  /**
   * Setup
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception
  {
    this.problemId = ICoreProblems.JOB_SUBMISSION_FAILED;
    this.problem = ReportingPlugin.getReportingService()
                     .getProblem( this.problemId, null, null, Activator.PLUGIN_ID );
    this.problemDesc = this.problem.getDescription();
  }

  /**
   * Test the method {@link AuthenticationException#AuthenticationException(String,String)}
   */
  @Test
  public void testAuthenticationExceptionStrStr()
  {
    AuthenticationException auExc = new AuthenticationException( this.problemId,
                                                                 Activator.PLUGIN_ID );
    Assert.assertNotNull( auExc );
    Assert.assertEquals( this.problemDesc, auExc.getProblem().getDescription() );
  }
  
  /**
   * Test the method {@link AuthenticationException#AuthenticationException(String,Throwable,String)}
   */
  @Test
  public void testAuthenticationExceptionStrThrowableStr()
  {
    Throwable exception = new Throwable();
    AuthenticationException auExc = new AuthenticationException( this.problemId,
                                                                 exception,
                                                                 Activator.PLUGIN_ID );
    Assert.assertNotNull( auExc );
    Assert.assertEquals( exception, auExc.getProblem().getException() );
  }
  
  /**
   * Test the method {@link AuthenticationException#AuthenticationException(String,String,String)}
   */
  @Test
  public void testAuthenticationExceptionStrStrStr()
  {
    String message = new String( "Test new description" ); //$NON-NLS-1$
    AuthenticationException auExc = new AuthenticationException( this.problemId,
                                                                 message,
                                                                 Activator.PLUGIN_ID );
    Assert.assertNotNull( auExc );
    Assert.assertEquals( message, auExc.getProblem().getDescription() );
  }
  
  /**
   * Test the method {@link AuthenticationException#AuthenticationException(String,String,Throwable,String)}
   */
  @Test
  public void testAuthenticationExceptionStrStrThrowableStr()
  {
    String message = new String( "Test new description" ); //$NON-NLS-1$
    Throwable exception = new Throwable();
    AuthenticationException auExc = new AuthenticationException( this.problemId,
                                                                 message,
                                                                 exception,
                                                                 Activator.PLUGIN_ID );
    Assert.assertNotNull( auExc );
    Assert.assertEquals( message, auExc.getProblem().getDescription() );
    Assert.assertEquals( exception, auExc.getProblem().getException() );
  }
  
  /**
   * Test the method {@link AuthenticationException#AuthenticationException(IProblem)}
   */
  @Test
  public void testAuthenticationExceptionIProblem()
  {
    AuthenticationException auExc = new AuthenticationException( this.problem );
    Assert.assertNotNull( auExc );
    Assert.assertEquals( this.problem, auExc.getProblem() );
  }

}
