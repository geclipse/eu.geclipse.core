/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

/**
 * 
 */
package eu.geclipse.aws.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test class for the static methods in {@link AWSUtil}.
 * 
 * @author Moritz Post
 */
public class AWSUtil_PDETest {

  /**
   * Test method for
   * {@link eu.geclipse.aws.util.AWSUtil#transformAWSAccountNumber(java.lang.String)}
   * .
   */
  @Test
  public void testTransformAWSAccountNumber() {
    String accountNo = "1234-1234-1234"; //$NON-NLS-1$
    String expectedResultAccountNo = "123412341234"; //$NON-NLS-1$

    String resultAccountNo = AWSUtil.transformAWSAccountNumber( accountNo );
    assertEquals( expectedResultAccountNo, resultAccountNo );

    accountNo = "1A34-1234-1234"; //$NON-NLS-1$
    resultAccountNo = AWSUtil.transformAWSAccountNumber( accountNo );
    assertNull( resultAccountNo );

    accountNo = "12334-1234-1234  "; //$NON-NLS-1$
    resultAccountNo = AWSUtil.transformAWSAccountNumber( accountNo );
    assertNull( resultAccountNo );

    accountNo = "14-1234-1234"; //$NON-NLS-1$
    resultAccountNo = AWSUtil.transformAWSAccountNumber( accountNo );
    assertNull( resultAccountNo );

    accountNo = null;
    resultAccountNo = AWSUtil.transformAWSAccountNumber( accountNo );
    assertNull( resultAccountNo );

    accountNo = "123412341234"; //$NON-NLS-1$
    resultAccountNo = AWSUtil.transformAWSAccountNumber( accountNo );
    assertEquals( expectedResultAccountNo, resultAccountNo );
  }

}
