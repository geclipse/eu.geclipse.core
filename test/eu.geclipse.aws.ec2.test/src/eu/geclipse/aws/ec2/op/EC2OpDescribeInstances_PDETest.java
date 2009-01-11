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
package eu.geclipse.aws.ec2.op;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eu.geclipse.aws.ec2.FTEC2WithException;
import eu.geclipse.aws.ec2.FTEC2WithResult;
import eu.geclipse.aws.ec2.op.EC2OpDescribeInstances;

/**
 * Test class for the class {@link EC2OpDescribeInstances}.
 * 
 * @author Moritz Post
 */
public class EC2OpDescribeInstances_PDETest {

  /** Dummy instance id. */
  private static final String DUMMY_INSTANCE_ID = "dummy-instance-id"; //$NON-NLS-1$

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeInstances#EC2OpDescribeInstances(eu.geclipse.aws.ec2.IEC2)}.
   */
  @Test
  public void testEC2OpDescribeInstancesIEC2Service() {
    EC2OpDescribeInstances opDescribeInstances = new EC2OpDescribeInstances( FTEC2WithResult.getInstance() );
    assertNotNull( opDescribeInstances );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeInstances#EC2OpDescribeInstances(eu.geclipse.aws.ec2.IEC2, java.lang.String[])}.
   */
  @Test
  public void testEC2OpDescribeInstancesIEC2ServiceStringArray() {
    String[] instancesArray = {
      EC2OpDescribeInstances_PDETest.DUMMY_INSTANCE_ID
    };
    EC2OpDescribeInstances opDescribeInstances = new EC2OpDescribeInstances( FTEC2WithResult.getInstance(),
                                                                             instancesArray );
    assertNotNull( opDescribeInstances );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeInstances#EC2OpDescribeInstances(eu.geclipse.aws.ec2.IEC2, java.util.List)}.
   */
  @Test
  public void testEC2OpDescribeInstancesIEC2ServiceListOfString() {
    List<String> instancesList = new ArrayList<String>();
    instancesList.add( EC2OpDescribeInstances_PDETest.DUMMY_INSTANCE_ID );

    EC2OpDescribeInstances opDescribeInstances = new EC2OpDescribeInstances( FTEC2WithResult.getInstance(),
                                                                             instancesList );
    assertNotNull( opDescribeInstances );

    instancesList.remove( 0 );
    opDescribeInstances = new EC2OpDescribeInstances( FTEC2WithResult.getInstance(),
                                                      instancesList );
    assertNotNull( opDescribeInstances );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeInstances#run()},
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeInstances#getResult()}
   * and
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeInstances#getException()}
   */
  @Test
  public void testRunGetResultGetException() {
    List<String> instancesList = new ArrayList<String>();
    instancesList.add( EC2OpDescribeInstances_PDETest.DUMMY_INSTANCE_ID );
    EC2OpDescribeInstances op = new EC2OpDescribeInstances( FTEC2WithResult.getInstance(),
                                                            instancesList );
    assertNotNull( op );

    op.run();
    assertNotNull( op.getResult() );
    assertNull( op.getException() );

    op = new EC2OpDescribeInstances( FTEC2WithException.getInstance() );
    op.run();
    assertNull( op.getResult() );
    assertNotNull( op.getException() );
  }

}
