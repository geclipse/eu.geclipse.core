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
 *    Jie Tao - test class (Plugin test)
 *****************************************************************************/

package eu.geclipse.core.internal.model.notify;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


/**this class tests the methods in the class {@link ResourceNotificationService}
 * @author tao-j
 *
 */
public class ResourceNotificationService_PDETest {

  private static ResourceNotificationService service;
  
  /**initialization
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    service = ResourceNotificationService.getInstance();
  }

  /**
   * tests the method {@link ResourceNotificationService#getInstance()}
   */
  @Test
  public void testGetInstance() {
    Assert.assertNotNull( service );
  }

}
