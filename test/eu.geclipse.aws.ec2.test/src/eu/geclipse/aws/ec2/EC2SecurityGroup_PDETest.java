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

package eu.geclipse.aws.ec2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.xerox.amazonws.ec2.GroupDescription;

import eu.geclipse.aws.ec2.test.util.EC2ServiceTestUtil;

/**
 * A test class for {@link EC2SecurityGroup}.
 * 
 * @author Moritz Post
 */
public class EC2SecurityGroup_PDETest {

  /** A dummy string used for testing this class */
  private static final String DUMMY_STRING = "dummyString"; //$NON-NLS-1$

  /**
   * A {@link GroupDescription} used for providing {@link EC2SecurityGroup}
   * data.
   */
  private GroupDescription groupDescription;

  /** The {@link EC2SecurityGroup} under test. */
  private EC2SecurityGroup securityGroup;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    this.groupDescription = new GroupDescription( EC2SecurityGroup_PDETest.DUMMY_STRING,
                                                  EC2SecurityGroup_PDETest.DUMMY_STRING,
                                                  EC2SecurityGroup_PDETest.DUMMY_STRING );
    this.securityGroup = new EC2SecurityGroup( null,
                                               EC2ServiceTestUtil.getEc2Service(),
                                               this.groupDescription );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.EC2SecurityGroup#EC2SecurityGroup(eu.geclipse.aws.ec2.service.EC2Service, com.xerox.amazonws.ec2.GroupDescription)}
   * .
   */
  @Test
  public void testEC2SecurityGroup() {
    assertNotNull( this.securityGroup );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2SecurityGroup#getName()}.
   */
  @Test
  public void testGetName() {
    assertTrue( this.securityGroup.getName()
      .equals( EC2SecurityGroup_PDETest.DUMMY_STRING ) );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.EC2SecurityGroup#getGroupDescription()}.
   */
  @Test
  public void testGetGroupDescription() {
    assertEquals( this.groupDescription,
                  this.securityGroup.getGroupDescription() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.EC2SecurityGroup#setGroupDescription(com.xerox.amazonws.ec2.GroupDescription)}
   * .
   */
  @Test
  public void testSetGroupDescription() {
    GroupDescription groupDescription2 = new GroupDescription( EC2SecurityGroup_PDETest.DUMMY_STRING,
                                                               EC2SecurityGroup_PDETest.DUMMY_STRING,
                                                               EC2SecurityGroup_PDETest.DUMMY_STRING );

    assertEquals( this.groupDescription,
                  this.securityGroup.getGroupDescription() );

    this.securityGroup.setGroupDescription( groupDescription2 );

    assertEquals( groupDescription2, this.securityGroup.getGroupDescription() );
  }

}
