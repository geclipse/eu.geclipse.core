/* Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *    Jie Tao - test class (Junit test)
 *****************************************************************************/

package eu.geclipse.core.portforward;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


/**this class tests the methods in the class {@link Forward}
 * @author tao-j
 *
 */
public class Forward_Test {

  private static Forward forward;
  /**initialization: an object for reference
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    forward = new Forward( ForwardType.LOCAL,20,"geclipse-host", 2000); //$NON-NLS-1$
  }

  /**
   * tests the method {@link Forward#Forward()}
   */
  @Test
  public void testForward() {
   Forward ff = new Forward();
   Assert.assertNotNull( ff );
  }

  /**
   * tests the method {@link Forward#Forward(ForwardType, int, String, int)}
   */
  @Test
  public void testForwardForwardTypeIntStringInt() {
    Assert.assertNotNull( forward );
  }

  /**
   * tests the method {@link Forward#getBindPort()}
   */
  @Test
  public void testGetBindPort() {
   Assert.assertEquals( new Integer(20), new Integer(forward.getBindPort()) );
  }

  /**
   * tests the method {@link Forward#setBindPort(int)}
   */
  @Test
  public void testSetBindPort() {
    forward.setBindPort( 40 );
    Assert.assertEquals( new Integer(40), new Integer(forward.getBindPort()) );
  }

  /**
   * tests the method {@link Forward#getHostname()}
   */
  @Test
  public void testGetHostname() {
    Assert.assertEquals( "geclipse-host", forward.getHostname() ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link Forward#setHostname(String)}
   */
  @Test
  public void testSetHostname() {
    forward.setHostname( "local" ); //$NON-NLS-1$
    Assert.assertEquals( "local", forward.getHostname() ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link Forward#getPort()}
   */
  @Test
  public void testGetPort() {
    Assert.assertEquals( new Integer(2000), new Integer(forward.getPort()) );
  }

  /**
   * tests the method {@link Forward#setPort(int)}
   */
  @Test
  public void testSetPort() {
    forward.setPort( 4000 );
    Assert.assertEquals( new Integer(4000), new Integer(forward.getPort()) );
  }

  /**
   * tests the method {@link Forward#getType()}
   */
  @Test
  public void testGetType() {
   Assert.assertEquals( ForwardType.LOCAL, forward.getType() );
  }

  /**
   * tests the method {@link Forward#setType(ForwardType)}
   */
  @Test
  public void testSetType() {
    forward.setType( ForwardType.REMOTE );
    Assert.assertEquals( ForwardType.REMOTE, forward.getType() );
  }
}
