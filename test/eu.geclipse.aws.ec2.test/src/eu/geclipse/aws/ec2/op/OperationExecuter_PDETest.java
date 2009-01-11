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
import java.util.List;

import org.junit.Test;

import com.xerox.amazonws.ec2.ImageDescription;

import eu.geclipse.aws.ec2.FTEC2WithException;
import eu.geclipse.aws.ec2.FTEC2WithResult;
import eu.geclipse.aws.ec2.op.EC2OpDescribeImagesByExec;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.op.OperationSet;

/**
 * Test class for the {@link OperationExecuter}.
 * 
 * @author Moritz Post
 */
public class OperationExecuter_PDETest {

  /** The identifier of "---dummy-owner---" as an AMI owner. */
  private static final String AMI_OWNER_DUMMY = "---dummy-owner---"; //$NON-NLS-1$

  /** The identifier of "amazon" as an AMI owner. */
  private static final String AMI_OWNER_ALL = "all"; //$NON-NLS-1$

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.OperationExecuter#execOp(eu.geclipse.aws.ec2.op.IOperation)}.
   * 
   * @throws Exception
   */
  @Test
  public void testExecOp() throws Exception {

    String[] owner = {
      OperationExecuter_PDETest.AMI_OWNER_ALL
    };

    // correct query
    EC2OpDescribeImagesByExec opDescribeImagesByExec = new EC2OpDescribeImagesByExec( FTEC2WithResult.getInstance(),
                                                                                      owner );

    OperationExecuter opExec = new OperationExecuter();
    opExec.execOp( opDescribeImagesByExec );
    List<ImageDescription> result = opDescribeImagesByExec.getResult();

    assertTrue( result.size() > 0 );
    assertTrue( result.get( 0 ) != null );

    String[] owner2 = {
      OperationExecuter_PDETest.AMI_OWNER_DUMMY
    };

    // faulty query
    opDescribeImagesByExec = new EC2OpDescribeImagesByExec( FTEC2WithException.getInstance(),
                                                            owner2 );

    opExec.execOp( opDescribeImagesByExec );
    assertNull( opDescribeImagesByExec.getResult() );
    assertNotNull( opDescribeImagesByExec.getException() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.OperationExecuter#execOpGroup(eu.geclipse.aws.ec2.op.OperationSet)}.
   * 
   * @throws Exception
   */
  @Test
  public void testExecOpGroup() throws Exception {
    String[] owner = {
      OperationExecuter_PDETest.AMI_OWNER_ALL
    };

    // correct query
    int numElements = 5;
    ArrayList<EC2OpDescribeImagesByExec> ops = new ArrayList<EC2OpDescribeImagesByExec>( numElements );

    for( int i = 0; i < numElements; i++ ) {
      ops.add( new EC2OpDescribeImagesByExec( FTEC2WithResult.getInstance(),
                                              owner ) );
    }

    OperationSet opGroup = new OperationSet( ops );

    OperationExecuter opExec = new OperationExecuter();
    opExec.execOpGroup( opGroup );

    for( EC2OpDescribeImagesByExec operation : ops ) {
      assertNotNull( operation );
      assertNotNull( operation.getResult() );
      assertTrue( operation.getResult().size() > 0 );
    }

    // setup wrong access credentials
    ops = new ArrayList<EC2OpDescribeImagesByExec>( numElements );

    for( int i = 0; i < numElements; i++ ) {
      ops.add( new EC2OpDescribeImagesByExec( FTEC2WithException.getInstance(),
                                              owner ) );
    }
    opGroup = new OperationSet( ops );
    opExec.execOpGroup( opGroup );

    for( EC2OpDescribeImagesByExec operation : ops ) {
      assertNotNull( operation );
      assertNotNull( operation.getException() );
    }
  }
}
