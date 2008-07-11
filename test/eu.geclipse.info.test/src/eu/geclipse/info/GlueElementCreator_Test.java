/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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

package eu.geclipse.info;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.geclipse.core.reporting.ProblemException;


/**test class for {@link GlueElementCreator}
 * @author tao-j
 *
 */
public class GlueElementCreator_Test {

  GlueElementCreator creator;
  /**initial setups; create a GlueElementCreator class
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    this.creator = new GlueElementCreator();
  }

  /**tests the method GlueElementCreator#internalCanCreate(Object)
   * 
   */
  @Test
  public void testInternalCanCreate() {
   Assert.assertFalse( this.creator.internalCanCreate( this ) );
  }

  /**tests the method {@link GlueElementCreator#create(eu.geclipse.core.model.IGridContainer)}
   * @throws ProblemException 
   * 
   */
  @Test
  public void testCreate() throws ProblemException {
    Assert.assertNull( this.creator.create( null ) );
  }

  /**tests the method {@link GlueElementCreator#canCreate(Class)}
   * 
   */
  @Test
  public void testCanCreateClassOfQextendsIGridElement() {
   Assert.assertFalse( this.creator.canCreate( null ));
  }
}
