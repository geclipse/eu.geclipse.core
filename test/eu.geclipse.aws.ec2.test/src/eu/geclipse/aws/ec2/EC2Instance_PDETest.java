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

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import com.xerox.amazonws.ec2.InstanceType;
import com.xerox.amazonws.ec2.ReservationDescription;

import eu.geclipse.aws.ec2.test.util.EC2ServiceTestUtil;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Test class for the {@link EC2Instance} entity.
 * 
 * @author Moritz Post
 */
public class EC2Instance_PDETest {

  /** A dummy string used to fill the {@link #dummyInstance}. */
  private static final String DUMMY_STRING = "Dummy_String"; //$NON-NLS-1$

  /** The instance backing up the {@link EC2Instance} with data. */
  private ReservationDescription.Instance dummyInstance;

  /** The ec2instance used to run tests against. */
  private EC2Instance ec2Instance;

  /** The time the AMI launched */
  private Calendar dummyLaunchTime;

  /**
   * Creates an instance of {@link EC2Instance}. The instance is used to run
   * tests against.
   * 
   * @throws ProblemException
   */
  @Before
  public void setUp() throws ProblemException {

    ReservationDescription reservationDescription = new ReservationDescription( EC2Instance_PDETest.DUMMY_STRING,
                                                                                EC2Instance_PDETest.DUMMY_STRING );
    this.dummyLaunchTime = Calendar.getInstance();
    this.dummyInstance = reservationDescription.new Instance( EC2Instance_PDETest.DUMMY_STRING,
                                                              EC2Instance_PDETest.DUMMY_STRING,
                                                              EC2Instance_PDETest.DUMMY_STRING,
                                                              EC2Instance_PDETest.DUMMY_STRING,
                                                              EC2Instance_PDETest.DUMMY_STRING,
                                                              0,
                                                              EC2Instance_PDETest.DUMMY_STRING,
                                                              EC2Instance_PDETest.DUMMY_STRING,
                                                              InstanceType.DEFAULT,
                                                              this.dummyLaunchTime,
                                                              EC2Instance_PDETest.DUMMY_STRING,
                                                              EC2Instance_PDETest.DUMMY_STRING,
                                                              EC2Instance_PDETest.DUMMY_STRING );
    this.ec2Instance = new EC2Instance( null,
                                        EC2ServiceTestUtil.getEc2Service(),
                                        this.dummyInstance );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.EC2Instance#EC2Instance(eu.geclipse.aws.ec2.service.EC2Service, com.xerox.amazonws.ec2.ReservationDescription.Instance)}
   * .
   * 
   * @throws ProblemException
   */
  @Test
  public void testEC2Instance() throws ProblemException {
    EC2Instance ec2Instance = new EC2Instance( null,
                                               EC2ServiceTestUtil.getEc2Service(),
                                               this.dummyInstance );
    assertNotNull( ec2Instance );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2Instance#getHostName()}.
   */
  @Test
  public void testGetHostName() {
    assertEquals( EC2Instance_PDETest.DUMMY_STRING,
                  this.ec2Instance.getHostName() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2Instance#getName()}.
   */
  @Test
  public void testGetName() {
    StringBuilder strBuilder = new StringBuilder( this.ec2Instance.getInstanceId() );
    strBuilder.append( " (" ); //$NON-NLS-1$
    strBuilder.append( this.ec2Instance.getImageId() );
    strBuilder.append( ") - " ); //$NON-NLS-1$
    strBuilder.append( this.ec2Instance.getState() );
    strBuilder.toString();
    assertEquals( strBuilder.toString(), this.ec2Instance.getName() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2Instance#getInstanceId()}.
   */
  @Test
  public void testGetInstanceId() {
    assertEquals( EC2Instance_PDETest.DUMMY_STRING,
                  this.ec2Instance.getInstanceId() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.EC2Instance#getAvailabilityZone()}.
   */
  @Test
  public void testGetAvailabilityZone() {
    assertEquals( EC2Instance_PDETest.DUMMY_STRING,
                  this.ec2Instance.getAvailabilityZone() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2Instance#getDnsName()}.
   */
  @Test
  public void testGetDnsName() {
    assertEquals( EC2Instance_PDETest.DUMMY_STRING,
                  this.ec2Instance.getDnsName() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2Instance#getImageId()}.
   */
  @Test
  public void testGetImageId() {
    assertEquals( EC2Instance_PDETest.DUMMY_STRING,
                  this.ec2Instance.getImageId() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2Instance#getInstanceType()}.
   */
  @Test
  public void testGetInstanceType() {
    assertEquals( InstanceType.DEFAULT.getTypeId(),
                  this.ec2Instance.getInstanceType() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2Instance#getKernelId()}.
   */
  @Test
  public void testGetKernelId() {
    assertEquals( EC2Instance_PDETest.DUMMY_STRING,
                  this.ec2Instance.getKernelId() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2Instance#getKeyName()}.
   */
  @Test
  public void testGetKeyName() {
    assertEquals( EC2Instance_PDETest.DUMMY_STRING,
                  this.ec2Instance.getKeyName() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2Instance#getLaunchTime()}.
   */
  @Test
  public void testGetLaunchTime() {
    assertEquals( this.dummyLaunchTime, this.ec2Instance.getLaunchTime() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2Instance#getPrivateDnsName()}
   * .
   */
  @Test
  public void testGetPrivateDnsName() {
    assertEquals( EC2Instance_PDETest.DUMMY_STRING,
                  this.ec2Instance.getPrivateDnsName() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2Instance#getRamdiskId()}.
   */
  @Test
  public void testGetRamdiskId() {
    assertEquals( EC2Instance_PDETest.DUMMY_STRING,
                  this.ec2Instance.getRamdiskId() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2Instance#getReason()}.
   */
  @Test
  public void testGetReason() {
    assertEquals( EC2Instance_PDETest.DUMMY_STRING,
                  this.ec2Instance.getReason() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2Instance#getState()}.
   */
  @Test
  public void testGetState() {
    assertEquals( EC2Instance_PDETest.DUMMY_STRING, this.ec2Instance.getState() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2Instance#getStateCode()}.
   */
  @Test
  public void testGetStateCode() {
    assertEquals( 0, this.ec2Instance.getStateCode() );
  }

}
