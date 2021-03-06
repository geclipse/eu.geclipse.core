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

package eu.geclipse.aws.ec2.op;

import static org.junit.Assert.*;

import org.junit.Test;

import eu.geclipse.aws.ec2.FTEC2WithException;
import eu.geclipse.aws.ec2.FTEC2WithResult;

/**
 * Test class for {@link EC2OpDeleteSecurityGroup}.
 * 
 * @author Moritz Post
 */
public class EC2OpDeleteSecurityGroup_PDETest {

  /** A dummy string used for testing. */
  private static final String DUMMY_STING = "string"; //$NON-NLS-1$

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpDeleteSecurityGroup#EC2OpDeleteSecurityGroup(eu.geclipse.aws.ec2.IEC2, java.lang.String)}.
   */
  @Test
  public void testEC2OpDeleteSecurityGroupIEC2StringStringStringIntInt() {
    EC2OpDeleteSecurityGroup opDescribeSecurityGroups = new EC2OpDeleteSecurityGroup( FTEC2WithResult.getInstance(),
                                                                                      EC2OpDeleteSecurityGroup_PDETest.DUMMY_STING );
    assertNotNull( opDescribeSecurityGroups );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpDeleteSecurityGroup#getResult()} and
   * {@link eu.geclipse.aws.ec2.op.EC2OpDeleteSecurityGroup#getException()}.
   */
  @Test
  public void testGetResultGetException() {
    EC2OpDeleteSecurityGroup op = new EC2OpDeleteSecurityGroup( FTEC2WithResult.getInstance(),
                                                                EC2OpDeleteSecurityGroup_PDETest.DUMMY_STING );
    op.run();
    assertNull( op.getResult() );
    assertNull( op.getException() );

    op = new EC2OpDeleteSecurityGroup( FTEC2WithException.getInstance(),
                                       EC2OpDeleteSecurityGroup_PDETest.DUMMY_STING );
    op.run();
    assertNull( op.getResult() );
    assertNotNull( op.getException() );
  }

}
