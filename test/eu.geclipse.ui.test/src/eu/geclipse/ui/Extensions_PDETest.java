/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *    Jie Tao - test class (Plug-in test)
 *****************************************************************************/

package eu.geclipse.ui;

import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.junit.Assert;

import org.junit.Test;



/**tests the methods in class {@link Extensions}
 * @author tao-j
 *
 */
public class Extensions_PDETest {

  /**
   * tests the method {@link Extensions#getRegisteredAuthTokenUIFactories()}
   */
  @Test
  public void testGetRegisteredAuthTokenUIFactories() {
    List<IAuthTokenUIFactory> resultList = Extensions.getRegisteredAuthTokenUIFactories();
    Assert.assertNotNull( resultList );
    Assert.assertTrue( resultList.get( 0 ).toString().contains( "Factory" ) ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link Extensions#getRegisteredEFSExtension(String)}
   */
  @Test
  public void testGetRegisteredEFSExtension() {
    IConfigurationElement element = Extensions.getRegisteredEFSExtension( "file" ); //$NON-NLS-1$
    Assert.assertNotNull( element );
    Assert.assertEquals( "filesystem", element.getName() ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link Extensions#getRegisteredEFSExtensions()}
   */
  @Test
  public void testGetRegisteredEFSExtensions() {
    List< IConfigurationElement > elements = Extensions.getRegisteredEFSExtensions();
    Assert.assertNotNull( elements );
    Assert.assertEquals( "filesystem", elements.get( 0 ).getName() ); //$NON-NLS-1$
  }
}
