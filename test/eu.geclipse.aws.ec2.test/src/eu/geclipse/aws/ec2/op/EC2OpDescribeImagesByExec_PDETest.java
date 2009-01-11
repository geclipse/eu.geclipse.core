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

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import eu.geclipse.aws.ec2.FTEC2WithException;
import eu.geclipse.aws.ec2.FTEC2WithResult;
import eu.geclipse.aws.ec2.op.EC2OpDescribeAvailabilityZones;
import eu.geclipse.aws.ec2.op.EC2OpDescribeImagesByExec;

/**
 * Test class for {@link EC2OpDescribeImagesByExec}
 * 
 * @author Moritz Post
 */
public class EC2OpDescribeImagesByExec_PDETest {

  /** The identifier of "amazon" as an AMI owner. */
  private static final String AMI_OWNER_ALL = "all"; //$NON-NLS-1$

  /** A list of owners, able to act as identifiers to the AWS AMIs. */
  private static String[] owners = new String[]{
    EC2OpDescribeImagesByExec_PDETest.AMI_OWNER_ALL
  };

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeImagesByExec#EC2OpDescribeImagesByExec(java.lang.String[])}.
   */
  @Test
  public void testEC2OpDescribeImagesByExecStringArray() {
    EC2OpDescribeImagesByExec op = new EC2OpDescribeImagesByExec( FTEC2WithResult.getInstance(),
                                                                  EC2OpDescribeImagesByExec_PDETest.owners );
    assertNotNull( op );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeImagesByExec#EC2OpDescribeImagesByExec(java.util.List)}.
   */
  @Test
  public void testEC2OpDescribeImagesByExecListOfString() {
    List<String> ownerList = Arrays.asList( EC2OpDescribeImagesByExec_PDETest.owners );
    EC2OpDescribeImagesByExec op = new EC2OpDescribeImagesByExec( FTEC2WithResult.getInstance(),
                                                                  ownerList );
    assertNotNull( op );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeImagesByExec#run()},
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeImagesByExec#getResult()}
   * and
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeImagesByExec#getException()}.
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
