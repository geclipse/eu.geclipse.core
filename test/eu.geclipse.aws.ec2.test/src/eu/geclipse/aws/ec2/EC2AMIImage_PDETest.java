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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import com.xerox.amazonws.ec2.ImageDescription;

import eu.geclipse.aws.ec2.service.EC2Service;
import eu.geclipse.aws.ec2.test.util.AWSVoTestUtil;
import eu.geclipse.aws.ec2.test.util.EC2ServiceTestUtil;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Test class for {@link EC2AMIImage}.
 * 
 * @author Moritz Post
 */
public class EC2AMIImage_PDETest {

  /** The ID for the synthetic AMI. */
  private static final String AMI_ID = "ami-123456789"; //$NON-NLS-1$

  /** The location for the synthetic AMI. */
  private static final String AMI_LOCATION = "buckName/path"; //$NON-NLS-1$

  /** The owner for the synthetic AMI. */
  private static final String AMI_OWNER = "987654321"; //$NON-NLS-1$

  /** The state for the synthetic AMI. */
  private static final String AMI_STATE = "available"; //$NON-NLS-1$

  /** The default {@link ImageDescription} to encapsulate. */
  private ImageDescription imageDescription;

  /** The {@link EC2AMIImage} under test. */
  private EC2AMIImage ec2AmiImage;

  /** The parent {@link EC2Service} for the {@link EC2AMIImage}. */
  private EC2Service ec2Service;

  /**
   * @throws ProblemException
   */
  @Before
  public void setUp() throws ProblemException {
    this.imageDescription = new ImageDescription( EC2AMIImage_PDETest.AMI_ID,
                                                  EC2AMIImage_PDETest.AMI_LOCATION,
                                                  EC2AMIImage_PDETest.AMI_OWNER,
                                                  EC2AMIImage_PDETest.AMI_STATE,
                                                  true,
                                                  null );
    this.ec2Service = new EC2Service( EC2ServiceTestUtil.getEC2ServiceCreator(),
                                      AWSVoTestUtil.getAwsVo() );
    this.ec2AmiImage = new EC2AMIImage( null,
                                        this.ec2Service,
                                        this.imageDescription );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.EC2AMIImage#EC2AMIImage(eu.geclipse.aws.ec2.test.util.AWSVirtualOrganization, com.xerox.amazonws.ec2.ImageDescription)}
   * .
   * 
   * @throws ProblemException
   */
  @Test
  public void testEC2AMIImage() throws ProblemException {
    EC2Service ec2Service = new EC2Service( EC2ServiceTestUtil.getEC2ServiceCreator(),
                                            AWSVoTestUtil.getAwsVo() );
    EC2AMIImage ec2AmiImage = new EC2AMIImage( null,
                                               ec2Service,
                                               this.imageDescription );
    assertNotNull( ec2AmiImage );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2AMIImage#getHostName()}.
   * 
   * @throws MalformedURLException
   */
  @Test
  public void testGetHostName() throws MalformedURLException {
    URL url = new URL( EC2ServiceTestUtil.EC2_URL );
    assertTrue( this.ec2AmiImage.getHostName().equalsIgnoreCase( url.getHost() ) );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2AMIImage#getURI()}.
   * 
   * @throws URISyntaxException
   */
  @Test
  public void testGetURI() throws URISyntaxException {
    URI uri = new URI( EC2ServiceTestUtil.EC2_URL );
    assertTrue( this.ec2AmiImage.getURI().equals( uri ) );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2AMIImage#getFileStore()}.
   */
  @Test()
  public void testGetFileStore() {
    assertNull( this.ec2AmiImage.getFileStore() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2AMIImage#getName()}.
   */
  @Test
  public void testGetName() {
    String name = EC2AMIImage_PDETest.AMI_ID + " (" //$NON-NLS-1$
                  + EC2AMIImage_PDETest.AMI_LOCATION
                  + ")"; //$NON-NLS-1$
    assertTrue( this.ec2AmiImage.getName().equals( name ) );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2AMIImage#getEC2Service()}.
   */
  @Test
  public void testGetEC2Service() {
    assertEquals( this.ec2AmiImage.getEC2Service(), this.ec2Service );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2AMIImage#getPath()}.
   */
  @Test
  public void testGetPath() {
    assertNull( this.ec2AmiImage.getPath() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2AMIImage#getResource()}.
   */
  @Test
  public void testGetResource() {
    assertNull( this.ec2AmiImage.getResource() );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2AMIImage#isLocal()}.
   */
  @Test
  public void testIsLocal() {
    assertFalse( this.ec2AmiImage.isLocal() );
  }

}
