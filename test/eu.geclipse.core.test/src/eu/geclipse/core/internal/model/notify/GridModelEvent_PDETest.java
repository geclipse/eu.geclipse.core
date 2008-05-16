/******************************************************************************
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

package eu.geclipse.core.internal.model.notify;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;


/**this class tests the method in the class {@link GridModelEvent}
 * @author tao-j
 *
 */
public class GridModelEvent_PDETest {
  
  private static GridModelEvent event;

  /**initial setup for an object
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    int type = IGridModelEvent.ELEMENTS_ADDED;
    IGridElement source = GridModel.getRoot();
    event = new GridModelEvent(type,source,null);
  }

  /**
   * tests the method {@link GridModelEvent#GridModelEvent(int, IGridElement, IGridElement[])}
   */
  @Test
  public void testGridModelEvent() {
    Assert.assertNotNull( event );
  }

  /**
   * tests the method {@link GridModelEvent#getElements()}
   */
  @Test
  public void testGetElements() {
    Assert.assertNull( event.getElements() );
  }

  /**
   * tests the method {@link GridModelEvent#getSource()}
   */
  @Test
  public void testGetSource() {
    Assert.assertEquals( GridModel.getRoot().getName(), event.getSource().getName() );
  }

  /**
   * tests the method {@link GridModelEvent#getType()}
   */
  @Test
  public void testGetType() {
    Assert.assertEquals( new Integer(IGridModelEvent.ELEMENTS_ADDED), new Integer(event.getType()));
  }
}
