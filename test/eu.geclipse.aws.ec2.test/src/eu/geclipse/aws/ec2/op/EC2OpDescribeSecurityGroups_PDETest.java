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

import eu.geclipse.aws.ec2.FTEC2WithException;
import eu.geclipse.aws.ec2.FTEC2WithResult;
import eu.geclipse.aws.ec2.op.EC2OpDescribeSecurityGroups;

/**
 * Test cases for {@link EC2OpDescribeSecurityGroups}.
 * 
 * @author Moritz Post
 */
public class EC2OpDescribeSecurityGroups_PDETest {

  /** The "default" security group. */
  private static final String SECURITY_GROUP_DEFAULT = "default"; //$NON-NLS-1$

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeSecurityGroups#EC2OpDescribeSecurityGroups()}.
   */
  @Test
  public void testEC2OpDescribeSecurityGroups() {
    EC2OpDescribeSecurityGroups op = new EC2OpDescribeSecurityGroups( FTEC2WithResult.getInstance() );
    assertNotNull( op );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeSecurityGroups#EC2OpDescribeSecurityGroups(java.lang.String[])}.
   */
  @Test
  public void testEC2OpDescribeSecurityGroupsStringArray() {
    String[] securityGroups = {
      EC2OpDescribeSecurityGroups_PDETest.SECURITY_GROUP_DEFAULT
    };
    EC2OpDescribeSecurityGroups opDescribeSecurityGroups = new EC2OpDescribeSecurityGroups( FTEC2WithResult.getInstance(),
                                                                                            securityGroups );
    assertNotNull( opDescribeSecurityGroups );

    String[] securityGroups2 = {};
    opDescribeSecurityGroups = new EC2OpDescribeSecurityGroups( FTEC2WithResult.getInstance(),
                                                                securityGroups2 );
    assertNotNull( opDescribeSecurityGroups );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeSecurityGroups#EC2OpDescribeSecurityGroups(java.util.List)}.
   */
  @Test
  public void testEC2OpDescribeSecurityGroupsListOfString() {
    List<String> securityGroups = new ArrayList<String>( 1 );
    securityGroups.add( EC2OpDescribeSecurityGroups_PDETest.SECURITY_GROUP_DEFAULT );
    EC2OpDescribeSecurityGroups opDescribeSecurityGroups = new EC2OpDescribeSecurityGroups( FTEC2WithResult.getInstance(),
                                                                                            securityGroups );
    assertNotNull( opDescribeSecurityGroups );

    securityGroups.remove( EC2OpDescribeSecurityGroups_PDETest.SECURITY_GROUP_DEFAULT );
    assertTrue( securityGroups.size() == 0 );
    opDescribeSecurityGroups = new EC2OpDescribeSecurityGroups( FTEC2WithResult.getInstance(),
                                                                securityGroups );
    assertNotNull( opDescribeSecurityGroups );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.op.EC2OpDescribeSecurityGroups#run()}.
   * 
   * @throws Exception
   */
  @Test
  public void testRunGetResultGetException() throws Exception {
    List<String> securityGroups = new ArrayList<String>( 1 );
    securityGroups.add( EC2OpDescribeSecurityGroups_PDETest.SECURITY_GROUP_DEFAULT );
    EC2OpDescribeSecurityGroups op = new EC2OpDescribeSecurityGroups( FTEC2WithResult.getInstance(),
                                                                      securityGroups );
    assertNotNull( op );

    op.run();
    assertNotNull( op.getResult() );
    assertNull( op.getException() );

    op = new EC2OpDescribeSecurityGroups( FTEC2WithException.getInstance() );
    op.run();
    assertNull( op.getResult() );
    assertNotNull( op.getException() );
  }

}
