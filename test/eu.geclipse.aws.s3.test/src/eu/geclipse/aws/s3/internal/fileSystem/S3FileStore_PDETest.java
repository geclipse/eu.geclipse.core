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

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import eu.geclipse.aws.s3.IS3Constants;
import eu.geclipse.aws.s3.internal.S3ServiceRegistry;

/**
 * Test class for {@link S3FileStore}.
 * <p>
 * More abstraction necessary to test the functionality currently relying on the
 * {@link S3ServiceRegistry}.
 * 
 * @author Moritz Post
 */
public class S3FileStore_PDETest {

  /** A {@link FileStore} name */
  private static final String FILE_STORE_NAME = "fileStoreName"; //$NON-NLS-1$

  /** A {@link FileStore} name */
  private static final String FILE_STORE_NAME2 = "fileStoreName2"; //$NON-NLS-1$

  /** A {@link S3FileStore} object acting as the parent under test. */
  private S3FileStore fileStoreParent;

  /** A {@link S3FileStore} object acting as the child under test. */
  private S3FileStore fileStoreChild;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    this.fileStoreParent = new S3FileStore( S3FileStore_PDETest.FILE_STORE_NAME );
    this.fileStoreChild = new S3FileStore( this.fileStoreParent,
                                           S3FileStore_PDETest.FILE_STORE_NAME2 );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.internal.fileSystem.S3FileStore#S3FileStore(java.lang.String)}.
   */
  @Test
  public void testS3FileStoreString() {
    S3FileStore fileStore = new S3FileStore( S3FileStore_PDETest.FILE_STORE_NAME );
    Assert.assertNotNull( fileStore );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.internal.fileSystem.S3FileStore#S3FileStore(eu.geclipse.aws.s3.internal.fileSystem.S3FileStore, java.lang.String)}.
   */
  @Test
  public void testS3FileStoreS3FileStoreString() {
    S3FileStore fileStoreParent = new S3FileStore( S3FileStore_PDETest.FILE_STORE_NAME );
    Assert.assertNotNull( fileStoreParent );
    S3FileStore fileStoreChild = new S3FileStore( fileStoreParent,
                                                  S3FileStore_PDETest.FILE_STORE_NAME2 );
    Assert.assertNotNull( fileStoreChild );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.internal.fileSystem.S3FileStore#childNames(int, org.eclipse.core.runtime.IProgressMonitor)}.
   */
  @Test
  @Ignore
  public void testChildNamesIntIProgressMonitor() {
    // Not yet implemented. Dependencies on network code
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.internal.fileSystem.S3FileStore#delete(int, org.eclipse.core.runtime.IProgressMonitor)}.
   */
  @Test
  @Ignore
  public void testDeleteIntIProgressMonitor() {
    // Not yet implemented. Dependencies on network code
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.internal.fileSystem.S3FileStore#fetchInfo(int, org.eclipse.core.runtime.IProgressMonitor)}.
   */
  @Test
  @Ignore
  public void testFetchInfoIntIProgressMonitor() {
    // Not yet implemented. Dependencies on network code
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.internal.fileSystem.S3FileStore#getChild(java.lang.String)}.
   */
  @Test
  public void testGetChildString() {
    IFileStore child = this.fileStoreParent.getChild( S3FileStore_PDETest.FILE_STORE_NAME2 );
    Assert.assertEquals( S3FileStore_PDETest.FILE_STORE_NAME2, child.getName() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.internal.fileSystem.S3FileStore#getName()}.
   */
  @Test
  public void testGetName() {
    Assert.assertEquals( S3FileStore_PDETest.FILE_STORE_NAME,
                         this.fileStoreParent.getName() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.internal.fileSystem.S3FileStore#getParent()}.
   */
  @Test
  public void testGetParent() {
    Assert.assertEquals( this.fileStoreParent, this.fileStoreChild.getParent() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.internal.fileSystem.S3FileStore#mkdir(int, org.eclipse.core.runtime.IProgressMonitor)}.
   */
  @Test
  @Ignore
  public void testMkdirIntIProgressMonitor() {
    // Not yet implemented. Dependencies on network code
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.internal.fileSystem.S3FileStore#openInputStream(int, org.eclipse.core.runtime.IProgressMonitor)}.
   */
  @Test
  @Ignore
  public void testOpenInputStreamIntIProgressMonitor() {
    // Not yet implemented. Dependencies on network code
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.internal.fileSystem.S3FileStore#openOutputStream(int, org.eclipse.core.runtime.IProgressMonitor)}.
   */
  @Test
  @Ignore
  public void testOpenOutputStreamIntIProgressMonitor() {
    // Not yet implemented. Dependencies on network code
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.internal.fileSystem.S3FileStore#toURI()}.
   */
  @Test
  public void testToURI() {
    URI uri = this.fileStoreParent.toURI();
    Assert.assertEquals( "s3://" + S3FileStore_PDETest.FILE_STORE_NAME, uri.toString() ); //$NON-NLS-1$
    uri = this.fileStoreChild.toURI();
    String expectedUri = "s3://" //$NON-NLS-1$
                         + S3FileStore_PDETest.FILE_STORE_NAME
                         + IS3Constants.S3_PATH_SEPARATOR
                         + S3FileStore_PDETest.FILE_STORE_NAME2;
    Assert.assertEquals( expectedUri, uri.toString() );
  }

  /**
   * Test method for
   * {@link eu.geclipse.aws.s3.internal.fileSystem.S3FileStore#internalFetchInfo()}.
   */
  @Test
  @Ignore
  public void testInternalFetchInfo() {
    // Not yet implemented. Dependencies on network code
  }

}
