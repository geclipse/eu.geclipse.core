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
import eu.geclipse.aws.ec2.op.EC2OpDescribeAvailabilityZones;

/**
 * Test cases for the {@link EC2OpDescribeAvailabilityZones} class.
 * 
 * @author Moritz Post
 */
public class EC2OpDescribeAvailabilityZones_PDETest {

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeAvailabilityZones#EC2OpDescribeAvailabilityZones()}.
   */
  @Test
  public void testEC2OpDescribeAvailabilityZones() {
    EC2OpDescribeAvailabilityZones op = new EC2OpDescribeAvailabilityZones( FTEC2WithResult.getInstance() );
    assertNotNull( op );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeAvailabilityZones#EC2OpDescribeAvailabilityZones(java.lang.String[])}.
   */
  @Test
  public void testEC2OpDescribeAvailabilityZonesStringArray() {
    EC2OpDescribeAvailabilityZones op = new EC2OpDescribeAvailabilityZones( FTEC2WithResult.getInstance() );
    assertNotNull( op );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeAvailabilityZones#EC2OpDescribeAvailabilityZones(java.util.List)}.
   */
  @Test
  public void testEC2OpDescribeAvailabilityZonesListOfString() {
    // Not testable because test data from the life system is very unreliable
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeAvailabilityZones#run()},
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeAvailabilityZones#getResult()}
   * and
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeAvailabilityZones#getException()}.
   * 
   * @throws Exception
   */
  @Test
  public void testRunGetResultGetException() throws Exception {
    EC2OpDescribeAvailabilityZones op = new EC2OpDescribeAvailabilityZones( FTEC2WithResult.getInstance() );
    op.run();
    assertNotNull( op.getResult() );
    assertNull( op.getException() );

    op = new EC2OpDescribeAvailabilityZones( FTEC2WithException.getInstance() );
    op.run();
    assertNull( op.getResult() );
    assertNotNull( op.getException() );
  }

}
