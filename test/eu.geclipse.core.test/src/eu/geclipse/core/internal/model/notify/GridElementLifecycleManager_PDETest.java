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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


/**this class tests the methods in class {@link GridElementLifecycleManager}
 * @author tao-j
 *
 */
public class GridElementLifecycleManager_PDETest {

  private static IProject project;
  /**initialization for a project
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
 // create a project as an IResource for the next tests
    String projectName = "test"; //$NON-NLS-1$
    IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
    project = workspaceRoot.getProject( projectName );
    IProjectDescription desc = project.getWorkspace().newProjectDescription( projectName );
    project.create( desc, null );
  }

  /**
   * tests the method {@link GridElementLifecycleManager#
   * findCreator(org.eclipse.core.resources.IResource)}
   */
  @Test
  public void testFindCreator() {
    Assert.assertNotNull( GridElementLifecycleManager.findCreator( project ) );
    Assert.assertTrue( GridElementLifecycleManager.findCreator( project ).toString().
                       contains( "GridProjectCreator" ) ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link GridElementLifecycleManager#
   * findStandardCreator(org.eclipse.core.resources.IResource)}
   */
  @Test
  public void testFindStandardCreator() {
    Assert.assertNotNull( GridElementLifecycleManager.findStandardCreator( project ) );
    Assert.assertTrue( GridElementLifecycleManager.findStandardCreator( project ).toString().
                       contains( "GridProjectCreator" ) ); //$NON-NLS-1$
  }
}
