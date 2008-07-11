/******************************************************************************
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

package eu.geclipse.core.internal.model;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.reporting.ProblemException;


/**this class tests the methods in {@link GridProject}
 * @author tao-j
 *
 */
public class GridProject_PDETest {

  private static GridProject gridproject;
  /**initialization with a GridProject object
   * before this a project has to be created
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // create a project
    String projectName = "test"; //$NON-NLS-1$
    IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
    IProject project = workspaceRoot.getProject( projectName );
    IProjectDescription desc = project.getWorkspace().newProjectDescription( projectName );
    project.create( desc, null );
    // create a gridproject
    project.open( null );
    project.getFile( "output" ).create( null, 0, null ); //$NON-NLS-1$
    gridproject = new GridProject( project );
  }

  /**tests the method {@link GridProject#getProject()}
   * 
   */
  @Test
  public void testGetProject() {
   Assert.assertEquals( gridproject, gridproject.getProject() );
  }

  /**tests the method {@link GridProject#canContain(IGridElement)}
   * 
   */
  @Test
  public void testCanContain() {
   Assert.assertTrue( gridproject.canContain( null ) );
  }

  /**tests the method {@link GridProject#getChildCount()}
   * There are two children. the first one is .project
   * and the second one is the created output
   */
  @Test
  public void testGetChildCount() {
  Assert.assertEquals( new Integer( 2 ), new Integer( gridproject.getChildCount()) );
  }

  /**tests the method {@link GridProject#
   * getChildren(org.eclipse.core.runtime.IProgressMonitor)}
   * the second child must be output
   * @throws ProblemException 
   */
  @Test
  public void testGetChildren() throws ProblemException {
    IGridElement[] children = gridproject.getChildren( null ) ;
    Assert.assertEquals( "output", children[1].getName() ); //$NON-NLS-1$
  }

  /**tests the method GridProject#
   * fetchChildren(org.eclipse.core.runtime.IProgressMonitor)
   * 
   */
  @Test
  public void testFetchChildren() {
   Assert.assertTrue( gridproject.fetchChildren( null ).isOK() );
  }

  /**tests the method GridProject#GridProject(IProject)
   * 
   */
  @Test
  public void testGridProject() {
    Assert.assertNotNull( gridproject );
  }

  /**tests the method {@link GridProject#getVO()}
   * the return is null because no VO is specified with the project
   */
  @Test
  public void testGetVO() {
   Assert.assertNull( gridproject.getVO() );
  }

  /**tests the method {@link GridProject#isGridProject()}
   * the result is false because the created project is not a grid one
   */
  @Test
  public void testIsGridProject() {
    Assert.assertFalse( gridproject.isGridProject() );
  }

  /**tests the method {@link GridProject#isOpen()}
   * the result is true, because the project was opened for creating a file
   */
  @Test
  public void testIsOpen() {
   Assert.assertTrue( gridproject.isOpen() );
  }
}
