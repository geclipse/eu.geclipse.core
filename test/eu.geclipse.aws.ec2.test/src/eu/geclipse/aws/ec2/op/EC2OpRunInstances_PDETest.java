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
import eu.geclipse.aws.ec2.op.AMILaunchConfiguration;
import eu.geclipse.aws.ec2.op.EC2OpRunInstances;

/**
 * Test class for the {@link EC2OpRunInstances} class.
 * 
 * @author Moritz Post
 */
public class EC2OpRunInstances_PDETest {

  /** A dummy AMI id. */
  private static final String AMI_ID2 = "ami-id2"; //$NON-NLS-1$

  /** Another dummy AMI id. */
  private static final String AMI_ID = "ami-id"; //$NON-NLS-1$

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpRunInstances#EC2OpRunInstances(eu.geclipse.aws.ec2.op.AMILaunchConfiguration)}.
   */
  @Test
  public void testEC2OpRunInstances() {
    AMILaunchConfiguration launchConfiguration = new AMILaunchConfiguration( EC2OpRunInstances_PDETest.AMI_ID );
    EC2OpRunInstances opRunInstances = new EC2OpRunInstances( FTEC2WithResult.getInstance(),
                                                              launchConfiguration );
    assertNotNull( opRunInstances );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpRunInstances#run()},
   * {@link eu.geclipse.aws.ec2.op.EC2OpRunInstances#getResult()} and
   * {@link eu.geclipse.aws.ec2.op.EC2OpRunInstances#getException()}.
   */
  @Test
  public void testRunGetResultGetException() {
    AMILaunchConfiguration launchConfiguration = new AMILaunchConfiguration( EC2OpRunInstances_PDETest.AMI_ID );
    EC2OpRunInstances op = new EC2OpRunInstances( FTEC2WithResult.getInstance(),
                                                  launchConfiguration );
    assertNotNull( op );

    op.run();
    assertNotNull( op.getResult() );
    assertNull( op.getException() );

    op = new EC2OpRunInstances( FTEC2WithException.getInstance(),
                                launchConfiguration );
    op.run();
    assertNull( op.getResult() );
    assertNotNull( op.getException() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpRunInstances#getLaunchConfig()}.
   */
  @Test
  public void testGetLaunchConfig() {
    AMILaunchConfiguration launchConfiguration = new AMILaunchConfiguration( EC2OpRunInstances_PDETest.AMI_ID );
    EC2OpRunInstances opRunInstances = new EC2OpRunInstances( FTEC2WithResult.getInstance(),
                                                              launchConfiguration );
    assertEquals( launchConfiguration, opRunInstances.getLaunchConfig() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpRunInstances#setLaunchConfig(eu.geclipse.aws.ec2.op.AMILaunchConfiguration)}.
   */
  @Test
  public void testSetLaunchConfig() {
    AMILaunchConfiguration launchConfiguration = new AMILaunchConfiguration( EC2OpRunInstances_PDETest.AMI_ID );
    AMILaunchConfiguration launchConfiguration2 = new AMILaunchConfiguration( EC2OpRunInstances_PDETest.AMI_ID2 );
    EC2OpRunInstances opRunInstances = new EC2OpRunInstances( FTEC2WithResult.getInstance(),
                                                              launchConfiguration );
    assertEquals( launchConfiguration, opRunInstances.getLaunchConfig() );
    opRunInstances.setLaunchConfig( launchConfiguration2 );
    assertNotSame( launchConfiguration, opRunInstances.getLaunchConfig() );
  }
}
