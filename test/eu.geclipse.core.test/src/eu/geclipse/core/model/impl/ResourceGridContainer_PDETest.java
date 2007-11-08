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
 *    Jie Tao - test class (Plug-in test)
 *****************************************************************************/

package eu.geclipse.core.model.impl;

import org.junit.Assert;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.BeforeClass;
import org.junit.Test;



/**this class tests the methods in the class {@link ResourceGridContainer}
 * @author tao-j
 *
 */
public class ResourceGridContainer_PDETest {

  private static ResourceGridContainer container;
  
  /**initial setup: generate an object of the tested class
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
 // create a project as an IResource
    String projectName = "test"; //$NON-NLS-1$
    IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
    IProject project = workspaceRoot.getProject( projectName );
    IProjectDescription desc = project.getWorkspace().newProjectDescription( projectName );
    project.create( desc, null );
    container = new ResourceGridContainer(project);
  }

  /**
   * tests the method ResourceGridContainer#
   * fetchChildren(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Test
  public void testFetchChildren() {
    container.fetchChildren( new NullProgressMonitor() );
  }

  /**
   * tests the method ResourceGridContainer#
   * ResourceGridContainer(org.eclipse.core.resources.IResource)
   */
  @Test
  public void testResourceGridContainer() {
    Assert.assertNotNull( container );
  }

  /**
   * tests the method {@link ResourceGridContainer#isLazy()}
   */
  @Test
  public void testIsLazy() {
    Assert.assertFalse( container.isLazy() );
  }

  @Test
  public void testGetFileStore() {
    
  }

  @Test
  public void testGetName() {
    
  }

  @Test
  public void testGetParent() {
    
  }

  @Test
  public void testGetPath() {
 
  }

  @Test
  public void testGetResource() {
    
  }

  @Test
  public void testIsLocal() {
    
  }

  @Test
  public void testFindCreator() {
   
  }
}
