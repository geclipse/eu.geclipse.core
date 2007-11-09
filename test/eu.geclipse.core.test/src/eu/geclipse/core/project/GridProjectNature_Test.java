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

package eu.geclipse.core.project;

import org.eclipse.core.internal.resources.NatureManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


/**tests the methods in the class {@link GridProjectNature}
 * @author tao-j
 *
 */
public class GridProjectNature_Test {
  
  private static GridProjectNature nature;
  private static IProject project;
/**initialization
 * @throws Exception
 */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    nature = new GridProjectNature();
 // create a project as an IResource
    String projectName = "test"; //$NON-NLS-1$
    IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
    project = workspaceRoot.getProject( projectName );
    IProjectDescription desc = project.getWorkspace().newProjectDescription( projectName );
    project.create( desc, null );
    nature.setProject( project ); //this is needed for configure, otherwise nullpointerExeption
  }

  /**
   * tests the method {@link GridProjectNature#configure()}
   * @throws CoreException 
   */
  @Test
  public void testConfigure() throws CoreException {
    nature.configure();
  }

  /**
   * tests the method {@link GridProjectNature#deconfigure()}
   * @throws CoreException 
   */
  @Test
  public void testDeconfigure() throws CoreException {
    nature.deconfigure();
  }

  /**
   * tests the method {@link GridProjectNature#getProject()}
   */
  @Test
  public void testGetProject() {
    Assert.assertEquals( project, nature.getProject() );
  }

  /**
   * tests the method {@link GridProjectNature#setProject(org.eclipse.core.resources.IProject)}
   */
  @Test
  public void testSetProject() {
    nature.setProject( null );
    Assert.assertNull( nature.getProject() );
  }

  /**
   * tests the method {@link GridProjectNature#getID()}
   */
  @Test
  public void testGetID() {
    Assert.assertEquals( "eu.geclipse.core.project.GridProjectNature", GridProjectNature.getID() ); //$NON-NLS-1$
  }
}
