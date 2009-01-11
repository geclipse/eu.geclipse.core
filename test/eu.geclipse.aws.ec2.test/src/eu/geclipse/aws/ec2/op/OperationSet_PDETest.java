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

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.aws.ec2.FTEC2WithResult;
import eu.geclipse.aws.ec2.op.EC2OpDescribeImagesByExec;
import eu.geclipse.aws.ec2.op.IOperation;
import eu.geclipse.aws.ec2.op.OperationSet;

/**
 * Test class for {@link OperationSet}.
 * 
 * @author Moritz Post
 */
public class OperationSet_PDETest {

  /** Number of {@link IOperation}s to produce per default. */
  private static final int NUM_OPS = 5;

  /** The identifier of "amazon" as an AMI owner. */
  private static final String AMI_OWNER_ALL = "all"; //$NON-NLS-1$

  /** A list of {@link IOperation}s acting as test data. */
  private static ArrayList<EC2OpDescribeImagesByExec> ops;

  /** A list of owners, able to act as identifiers to the AWS AMIs. */
  private static String[] owners = new String[]{
    OperationSet_PDETest.AMI_OWNER_ALL
  };

  /**
   * Setup 5 {@link IOperation}s to test with
   */
  @BeforeClass
  public static void setUpClass() {
    OperationSet_PDETest.ops = new ArrayList<EC2OpDescribeImagesByExec>( OperationSet_PDETest.NUM_OPS );
    for( int i = 0; i < OperationSet_PDETest.NUM_OPS; i++ ) {
      OperationSet_PDETest.ops.add( new EC2OpDescribeImagesByExec( FTEC2WithResult.getInstance(),
                                                                   OperationSet_PDETest.owners ) );
    }
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.OperationSet#OperationGroup()}.
   */
  @Test
  public void testOperationGroup() {
    OperationSet opGroup = new OperationSet();
    assertNotNull( opGroup );
    assertNotNull( opGroup.getOps() );
    assertTrue( opGroup.getOps().size() == 0 );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.OperationSet#OperationGroup(java.util.List)}.
   */
  @Test
  public void testOperationGroupListOfQextendsIOperation() {
    OperationSet opGroup = new OperationSet( OperationSet_PDETest.ops );
    assertNotNull( opGroup );
    assertNotNull( opGroup.getOps() );
    assertTrue( opGroup.getOps().size() == OperationSet_PDETest.NUM_OPS );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.OperationSet#OperationGroup(eu.geclipse.aws.ec2.op.IOperation[])}.
   */
  @Test
  public void testOperationGroupIOperationArray() {
    IOperation[] opArray = OperationSet_PDETest.ops.toArray( new IOperation[ OperationSet_PDETest.ops.size() ] );
    OperationSet opGroup = new OperationSet( opArray );
    assertNotNull( opGroup );
    assertNotNull( opGroup.getOps() );
    assertTrue( opGroup.getOps().size() == OperationSet_PDETest.NUM_OPS );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.OperationSet#addOp(eu.geclipse.aws.ec2.op.IOperation)}.
   */
  @Test
  public void testAddOp() {
    OperationSet opGroup = new OperationSet();
    EC2OpDescribeImagesByExec opDescribeImagesByExec = new EC2OpDescribeImagesByExec( FTEC2WithResult.getInstance(),
                                                                                      OperationSet_PDETest.owners );
    opGroup.addOp( opDescribeImagesByExec );
    assertNotNull( opGroup );
    assertNotNull( opGroup.getOps() );
    assertTrue( opGroup.getOps().size() == 1 );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.OperationSet#removeOp(eu.geclipse.aws.ec2.op.IOperation)}.
   */
  @Test
  public void testRemoveOp() {
    OperationSet opGroup = new OperationSet();
    EC2OpDescribeImagesByExec opDescribeImagesByExec = new EC2OpDescribeImagesByExec( FTEC2WithResult.getInstance(),
                                                                                      OperationSet_PDETest.owners );
    opGroup.addOp( opDescribeImagesByExec );
    assertNotNull( opGroup );
    assertNotNull( opGroup.getOps() );
    assertTrue( opGroup.getOps().size() == 1 );
    opGroup.removeOp( opDescribeImagesByExec );
    assertNotNull( opGroup );
    assertNotNull( opGroup.getOps() );
    assertTrue( opGroup.getOps().size() == 0 );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.OperationSet#getOps()}.
   */
  @Test
  public void testGetOps() {
    IOperation[] opArray = OperationSet_PDETest.ops.toArray( new IOperation[ OperationSet_PDETest.ops.size() ] );
    OperationSet opGroup = new OperationSet( opArray );
    assertNotNull( opGroup );
    assertNotNull( opGroup.getOps() );
    assertTrue( opGroup.getOps().size() == OperationSet_PDETest.NUM_OPS );

    opGroup = new OperationSet();
    assertNotNull( opGroup.getOps() );
    assertTrue( opGroup.getOps().size() == 0 );
  }

}
