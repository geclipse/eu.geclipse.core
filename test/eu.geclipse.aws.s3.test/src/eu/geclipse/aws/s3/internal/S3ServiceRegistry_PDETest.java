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

package eu.geclipse.aws.s3.internal;

import junit.framework.Assert;

import org.junit.Test;

import eu.geclipse.core.auth.AuthenticationTokenManager;

/**
 * Test class for the {@link S3ServiceRegistry} class. Not much testing possible
 * since the most interesting method triggers UI related code (ask for auth
 * token).
 * 
 * @author Moritz Post
 */
public class S3ServiceRegistry_PDETest {

  /**
   * Test method for {@link eu.geclipse.aws.ec2.S3ServiceRegistry#getRegistry()}.
   */
  @Test
  public void testGetRegistry() {
    S3ServiceRegistry registry1 = S3ServiceRegistry.getRegistry();
    S3ServiceRegistry registry2 = S3ServiceRegistry.getRegistry();
    Assert.assertEquals( registry1, registry2 );
  }

  /**
   * Test method for
   * {@link S3ServiceRegistry#contentChanged(eu.geclipse.core.auth.ISecurityManager)}.
   */
  @Test
  public void testContentChanged() {
    S3ServiceRegistry registry = S3ServiceRegistry.getRegistry();
    registry.contentChanged( null );
    registry.contentChanged( AuthenticationTokenManager.getManager() );
  }

}
