/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.s3.internal.fileSystem;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.filesystem.IFileStore;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link S3FileSystem}.
 * 
 * @author Moritz Post
 */
public class S3FileSystem_PDETest {

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.internal.fileSystem.S3FileSystem#canDelete()}.
   */
  @Test
  public void testCanDelete() {
    S3FileSystem fileSystem = new S3FileSystem();
    Assert.assertTrue( fileSystem.canDelete() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.internal.fileSystem.S3FileSystem#canWrite()}.
   */
  @Test
  public void testCanWrite() {
    S3FileSystem fileSystem = new S3FileSystem();
    Assert.assertTrue( fileSystem.canDelete() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.internal.fileSystem.S3FileSystem#getStore(java.net.URI)}.
   * 
   * @throws URISyntaxException
   */
  @Test
  public void testGetStoreURI() throws URISyntaxException {
    S3FileSystem fs = new S3FileSystem();
    IFileStore store = fs.getStore( new URI( "s3://root" ) ); //$NON-NLS-1$
    Assert.assertTrue( store instanceof S3FileStore );
    Assert.assertEquals( store.getName(), "root" ); //$NON-NLS-1$
    Assert.assertTrue( store.getFileSystem() instanceof S3FileSystem );

    fs = new S3FileSystem();
    store = fs.getStore( new URI( "s3://root/bundle" ) ); //$NON-NLS-1$
    Assert.assertTrue( store instanceof S3FileStore );
    Assert.assertEquals( store.getName(), "bundle" ); //$NON-NLS-1$
    Assert.assertTrue( store.getFileSystem() instanceof S3FileSystem );
  }

}
