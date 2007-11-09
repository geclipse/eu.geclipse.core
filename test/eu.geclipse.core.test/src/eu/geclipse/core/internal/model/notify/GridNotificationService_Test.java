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

package eu.geclipse.core.internal.model.notify;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;


/**this class tests the methods in the class {@link GridNotificationService}
 * @author tao-j
 *
 */
public class GridNotificationService_Test {

  private static GridNotificationService service;
  
  /**initialization
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    service = GridNotificationService.getInstance();
  }

  /**
   * tests the methods {@link GridNotificationService#getInstance()}
   */
  @Test
  public void testGetInstance() {
    Assert.assertNotNull( service );
  }

  /**
   * tests the methods {@link GridNotificationService#
   * addListener(eu.geclipse.core.model.IGridModelListener)}
   * and {@link GridNotificationService#
   * removeListener(eu.geclipse.core.model.IGridModelListener)}
   */
  @Test
  public void testAddRemoveListener() {
    IGridModelListener listener = new IGridModelListener() {
      public void gridModelChanged( final IGridModelEvent event ) {
        //nothing to handle
      }
    };
    service.addListener( listener );
    service.removeListener( listener );
  }

  /**
   * tests the methods {@link GridNotificationService#lock()}
   * and {@link GridNotificationService#unlock()}
   */
  @Test
  public void testLockUnlock() {
    service.lock();
    service.unlock();
  }

  /**
   * tests the methods {@link GridNotificationService#
   * queueEvent(eu.geclipse.core.model.IGridModelEvent)}
   */
  @Test
  public void testQueueEvent() {
    service.queueEvent( new GridModelEvent(IGridModelEvent.ELEMENTS_REMOVED,null,null) );
  }

}
