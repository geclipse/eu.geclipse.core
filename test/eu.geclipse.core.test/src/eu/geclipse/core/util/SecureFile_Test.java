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
 *    Jie Tao - test class (Junit test)
 *****************************************************************************/

package eu.geclipse.core.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


/**tests the methods in the class {@link SecureFile}
 * @author tao-j
 *
 */
public class SecureFile_Test {

  private static SecureFile sfile;
  
  /**initialization
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String path = new String("/test/"); //$NON-NLS-1$
    sfile = new SecureFile(path);
  }

  /**
   * tests the method {@link SecureFile#createNewFile()}
   * @throws IOException 
   */
  @Test
  public void testCreateNewFile() throws IOException {
    //Assert.assertTrue( sfile.createNewFile() );
    //sfile.delete();
  }

  /**
   * tests the method {@link SecureFile#mkdir()}
   */
  @Test
  public void testMkdir() {
    //Assert.assertTrue( sfile.mkdir());
    //sfile.delete();
  }

  /**
   * tests the method {@link SecureFile#mkdirs()}
   */
  @Test
  public void testMkdirs() {
    //Assert.assertTrue( sfile.mkdirs());
    //sfile.delete();
  }

  /**
   * tests the method {@link SecureFile#SecureFile(String)}
   */
  @Test
  public void testSecureFileString() {
    Assert.assertNotNull( sfile );
  }

  /**
   * tests the method {@link SecureFile#SecureFile(String, String)}
   */
  @Test
  public void testSecureFileStringString() {
  SecureFile sfile2 = new SecureFile("parent","child"); //$NON-NLS-1$ //$NON-NLS-2$
  Assert.assertNotNull( sfile2 );
  }


  /**
   * tests the method {@link SecureFile#SecureFile(java.net.URI)}
   * @throws URISyntaxException 
   */
  @Test
  public void testSecureFileURI() throws URISyntaxException {
    SecureFile sfile2 = new SecureFile(new URI("file:///test")); //$NON-NLS-1$
    Assert.assertNotNull( sfile2 );
  }

  /**
   * tests the method {@link SecureFile#setSecure()}
   * @throws IOException 
   */
  @Test
  public void testSetSecure() throws IOException {
   //Assert.assertTrue( sfile.setSecure());
  }

  /**
   * tests the method {@link SecureFile#isSecure()}
   */
  @Test
  public void testIsSecure() {
    Assert.assertFalse( sfile.isSecure() );
  }

  /**
   * tests the method {@link SecureFile#createTempFile(String, String)}
   * @throws IOException 
   */
  @Test
  public void testCreateTempFileStringString() throws IOException {
   SecureFile.createTempFile( "pre", "suf" ); //$NON-NLS-1$ //$NON-NLS-2$
   sfile.delete();
  }


  /**
   * tests the method {@link SecureFile#renameTo(java.io.File)}
   */
  @Test
  public void testRenameToFile() {
    SecureFile dest = new SecureFile("dest");  //$NON-NLS-1$
    sfile.renameTo( dest );
  }
}
