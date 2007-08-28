/*****************************************************************************
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

package eu.geclipse.core.filesystem.internal.filesystem;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.filesystem.GEclipseFileSystem;

/**tests the class {@link FileSystemManager}
 * @author tao-j
 *
 */
public class FileSystemManager_PDETest {
  
  private static FileSystemManager filesystemmanager;

  /**initial setups for all tests
   * in order to have a folder for test, a project must be created fro which a VO setting is required
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    filesystemmanager = FileSystemManager.getInstance();
  }

  /**tests the method {@link FileSystemManager#getInstance()}
   * 
   */
  @Test
  public void testGetInstance() {
   Assert.assertNotNull( filesystemmanager );
  }

  /**tests the method {@link FileSystemManager#getStore(GEclipseFileSystem, URI)}
   * @throws URISyntaxException
   */
  @Test
  public void testGetStore() throws URISyntaxException {
    String local = "file:///D/geclipsetest"; //$NON-NLS-1$
    URI localuri = new URI( local );
    GEclipseFileSystem geclipsefilesystem = new GEclipseFileSystem();
    GEclipseFileStore store = filesystemmanager.getStore( geclipsefilesystem, localuri );
    Assert.assertNotNull( store );
    Assert.assertEquals( "geclipsetest", store.fetchInfo().getName() ); //$NON-NLS-1$
  }
}
