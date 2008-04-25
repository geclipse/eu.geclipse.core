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
 *    Jie Tao - test class (Junit test)
 *****************************************************************************/

package eu.geclipse.core.internal.model;


import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import eu.geclipse.core.model.IGridConnection;

/**this class tests the methods in {@link ConnectionManager}
 * @author tao-j
 *
 */
public class ConnectionManager_Test {
  private static ConnectionManager manager;
  
  /**initialization with a ConnectionManager object
   * @throws Exception
   */
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    manager = ConnectionManager.getManager();
  }

  /**tests the method {@link ConnectionManager#getManager()}
   * 
   */
  @Test
  public void testGetManager() {
    Assert.assertNotNull( manager );
  }

  /**tests the method {@link ConnectionManager#canManage(eu.geclipse.core.model.IGridElement)}
   * 
   */
  @Test
  public void testCanManage() {
    Assert.assertFalse( manager.canManage( null ) );
    // must be commented due to dependency to eu.geclipse.test
    /*String local = "file://" + GridTestStub.setUpLocalDir(); //$NON-NLS-1$
    URI localuri = new URI( local );
    String scheme = localuri.getScheme();
    GEclipseURI geclipseuri = new GEclipseURI( localuri );
    IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
    IProject proj = workspaceRoot.getProject( "TestGridProject" ); //$NON-NLS-1$
    folder = proj.getFolder( "test" ); //$NON-NLS-1$
    folder.createLink( geclipseuri.toMasterURI(), 0, null);*/
  }

  /**tests the method {@link ConnectionManager#getGlobalConnections()}
   * 
   */
  @Test
  public void testGetGlobalConnections() {
    IGridConnection[] connections = manager.getGlobalConnections();
    Assert.assertNull( connections );
  }

  /**tests the method {@link ConnectionManager#getName()}
   * 
   */
  @Test
  public void testGetName() {
   Assert.assertEquals( ".connections", manager.getName() ); //$NON-NLS-1$
  }
}
