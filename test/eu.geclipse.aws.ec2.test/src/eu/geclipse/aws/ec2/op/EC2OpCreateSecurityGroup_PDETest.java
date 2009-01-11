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
 * Test class for {@link EC2OpCreateSecurityGroup}.
 * 
 * @author Moritz Post
 */
public class EC2OpCreateSecurityGroup_PDETest {

  /** A dummy string. */
  private static final String DUMMY = "DUMMY"; //$NON-NLS-1$

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpCreateSecurityGroup#EC2OpCreateSecurityGroup(eu.geclipse.aws.ec2.IEC2, java.lang.String, java.lang.String)}
   * .
   */
  @Test
  public void testEC2OpCreateSecurityGroup() {
    EC2OpCreateSecurityGroup opCreateSecurityGroup = new EC2OpCreateSecurityGroup( FTEC2WithResult.getInstance(),
                                                                                   EC2OpCreateSecurityGroup_PDETest.DUMMY,
                                                                                   EC2OpCreateSecurityGroup_PDETest.DUMMY );

    assertNotNull( opCreateSecurityGroup );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpCreateSecurityGroup#getResult()} and
   * {@link eu.geclipse.aws.ec2.op.EC2OpCreateSecurityGroup#getException()}.
   */
  @Test
  public void testGetResultGetException() {

    EC2OpCreateSecurityGroup op = new EC2OpCreateSecurityGroup( FTEC2WithResult.getInstance(),
                                                                EC2OpCreateSecurityGroup_PDETest.DUMMY,
                                                                EC2OpCreateSecurityGroup_PDETest.DUMMY );
    assertNotNull( op );

    op.run();
    assertNull( op.getResult() );
    assertNull( op.getException() );

    op = new EC2OpCreateSecurityGroup( FTEC2WithException.getInstance(),
                                       EC2OpCreateSecurityGroup_PDETest.DUMMY,
                                       EC2OpCreateSecurityGroup_PDETest.DUMMY );
    op.run();
    assertNull( op.getResult() );
    assertNotNull( op.getException() );
  }

}
