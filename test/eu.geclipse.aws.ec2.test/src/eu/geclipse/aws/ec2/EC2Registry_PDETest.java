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

import org.junit.Test;

import eu.geclipse.aws.ec2.test.util.AWSVoTestUtil;
import eu.geclipse.core.auth.AuthenticationTokenManager;

/**
 * Test class for {@link EC2Registry}.
 * 
 * @author Moritz Post
 */
public class EC2Registry_PDETest {

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2Registry#getRegistry()}.
   */
  @Test
  public void testGetRegistry() {
    EC2Registry registry = EC2Registry.getRegistry();
    assertNotNull( registry );
  }

  /**
   * Test method for {@link eu.geclipse.aws.ec2.EC2Registry#clear()}.
   */
  @Test
  public void testClear() {
    EC2Registry registry = EC2Registry.getRegistry();

    IEC2 ec2 = registry.getEC2( AWSVoTestUtil.AWS_ACCESS_ID );
    assertNotNull( ec2 );

    registry.clear();
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.EC2Registry#getEC2(java.lang.String)}.
   */
  @Test
  public void testGetEC2() {
    EC2Registry registry = EC2Registry.getRegistry();
    IEC2 ec2 = registry.getEC2( AWSVoTestUtil.AWS_ACCESS_ID );
    assertNotNull( ec2 );

    IEC2 ec22 = registry.getEC2( AWSVoTestUtil.AWS_ACCESS_ID_2 );
    assertNotNull( ec22 );

    assertNotSame( ec2, ec22 );

    assertTrue( !ec2.equals( ec22 ) );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.ec2.EC2Registry#contentChanged(eu.geclipse.core.auth.ISecurityManager)}.
   */
  @Test
  public void testContentChanged() {
    AuthenticationTokenManager manager = AuthenticationTokenManager.getManager();
    EC2Registry registry = EC2Registry.getRegistry();
    registry.contentChanged( manager );
  }

}
