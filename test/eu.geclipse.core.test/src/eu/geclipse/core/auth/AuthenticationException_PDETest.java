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

package eu.geclipse.core.auth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import eu.geclipse.core.CoreProblems;

/** this class test the constructors in class AuthenticationException
 * all constructors are tested
 * @author tao-j
 *
 */
public class AuthenticationException_PDETest {

  int problemid;
  
  @Before
  public void setUp() throws Exception
  {
    this.problemid = CoreProblems.JOB_SUBMISSION_FAILED;
  }

  /** Tests the method {@link AuthenticationException#AuthenticationException(int)}
   * 
   */
  @Test
  public void testAuthenticationExceptionInt()
  {
    AuthenticationException auex = new AuthenticationException (this.problemid);
    Assert.assertEquals( new Integer( this.problemid ), new Integer( auex.getProblem().getID()) );
  }

  /** Tests the method {@link AuthenticationException#AuthenticationException(intstring)}
   * 
   */
  @Test
  public void testAuthenticationExceptionIntString()
  {
   String message = new String( "jobsubfailed"); //$NON-NLS-1$
   AuthenticationException auex = new AuthenticationException (this.problemid,message);
   Assert.assertEquals( new Integer( this.problemid ), new Integer( auex.getProblem().getID()) );
   Assert.assertEquals("Job Submission failed",auex.getProblem().getText() ); //$NON-NLS-1$
  }

  /** Tests the method {@link AuthenticationException#AuthenticationException(intthrowable)}
   * 
   */
  @Test
  public void testAuthenticationExceptionIntThrowable()
  {
    Throwable exc = new Throwable();
    AuthenticationException auex = new AuthenticationException (this.problemid,exc);
    Assert.assertEquals( new Integer( this.problemid ), new Integer( auex.getProblem().getID()) );
  }
}
