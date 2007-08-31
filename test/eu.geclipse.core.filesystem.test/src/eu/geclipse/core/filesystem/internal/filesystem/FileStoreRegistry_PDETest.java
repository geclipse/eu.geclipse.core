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
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.filesystem.GEclipseFileSystem;
import eu.geclipse.core.filesystem.GEclipseURI;

/**tests the class {@link FileStoreRegistry}
 * @author tao-j
 *
 */
public class FileStoreRegistry_PDETest {

  private static FileStoreRegistry filestoreregistry;
  /**initial setups for all tests
   * in order to have a folder for test, a project must be created fro which a VO setting is required
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    filestoreregistry = FileStoreRegistry.getInstance();
  }

  /**tests the method {@link FileStoreRegistry#getInstance()}
   * 
   */
  @Test
  public void testGetInstance() {
    Assert.assertNotNull( filestoreregistry );
  }

  /**combined tests of the methods putStore(GEclipseFileStore)
   * and getStore(GEclipseURI)and getStore(IFileStore)
   * @throws URISyntaxException
   * @throws CoreException
   */
  @Test
  public void testPutGetStore() throws URISyntaxException, CoreException {
    String local = "file:///D/geclipsetest"; //$NON-NLS-1$
    URI localuri = new URI( local );
    String scheme = localuri.getScheme();
    IFileSystem fileSystem = EFS.getFileSystem( scheme );
    IFileStore localfile = fileSystem.getStore( localuri );
    GEclipseFileSystem geclipsefilesystem = new GEclipseFileSystem();
    GEclipseFileStore store = new GEclipseFileStore( geclipsefilesystem, localfile );
    filestoreregistry.putStore( store );
    Assert.assertEquals( store, filestoreregistry.getStore( new GEclipseURI( store.toURI() )) );
    Assert.assertEquals( store, filestoreregistry.getStore( store ) );
  }
}
