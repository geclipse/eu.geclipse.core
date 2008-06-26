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

package eu.geclipse.core.model.impl;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


/**This class tests the method in class {@link ResourceGridElement}
 * @author tao-j
 *
 */
public class ResourceGridElement_PDETest {

  private static ResourceGridElement element;
  
  /**initialization with an object of the class under test
   * it needs a IResource as input, so a project is created for this
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
 // create a project as an IResource
    String projectName = "jietest"; //$NON-NLS-1$
    IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
    IProject project = workspaceRoot.getProject( projectName );
    if (workspaceRoot.findMember( "jietest" ) == null ) { //$NON-NLS-1$
    IProjectDescription desc = project.getWorkspace().newProjectDescription( projectName );
    project.create( desc, null );}
    element = new ResourceGridElement(project);
  }

  /**
   * tests the method ResourceGridElement#ResourceGridElement(org.eclipse.core.resources.IResource}
   */
  @Test
  public void testResourceGridElement() {
    Assert.assertNotNull( element );
  }

  /**
   * tests the method {@link ResourceGridElement#getFileStore()}
   */
  @Test
  public void testGetFileStore() {
    IFileStore store = element.getFileStore();
    Assert.assertNotNull( store );
    Assert.assertTrue( store.toString().contains( "test" ) ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link ResourceGridElement#getName()}
   */
  @Test
  public void testGetName() {
    Assert.assertEquals( "jietest", element.getName() ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link ResourceGridElement#getParent()}
   */
  @Test
  public void testGetParent() {
    Assert.assertNotNull( element.getParent() );
  }

  /**
   * tests the method {@link ResourceGridElement#getPath()}
   */
  @Test
  public void testGetPath() {
    Assert.assertEquals( "/jietest", element.getPath().toString() ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link ResourceGridElement#getResource()}
   */
  @Test
  public void testGetResource() {
    Assert.assertEquals( "jietest", element.getResource().getName() ); //$NON-NLS-1$
  }

  /**
   * tests the method {@link ResourceGridElement#isLocal()}
   */
  @Test
  public void testIsLocal() {
    Assert.assertTrue( element.isLocal() );
  }
}
