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
 *    Jie Tao - test class (Junit test)
 *****************************************************************************/

package eu.geclipse.core.internal.auth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

/**this class tests the methods in {@link PEMCertificate}
 * @author tao-j
 *
 */
public class PEMCertificate_Test {

  private static PEMCertificate pemca;
  private static byte[] cert = {45,45,45,45,45,66,69,71,73,78,32,67,69,
    82,84,73,70,73,67,65,84,69};
  /**initialization with an PEMCertificate object
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    pemca = new PEMCertificate("geclipsetest", cert); //$NON-NLS-1$
  }

  /**tests the method PEMCertificate#getFileNames()
   * 
   */
  @Test
  public void testGetFileNames() {
    String[] result = pemca.getFileNames();
    Assert.assertEquals( "geclipsetest.pemcert", result[0] ); //$NON-NLS-1$
  }

  /**tests the method PEMCertificate#PEMCertificate(String, byte[])
   * 
   */
  @Test
  public void testPEMCertificate() {
    Assert.assertNotNull( pemca );
  }

  /**tests the method PEMCertificate#readFromFile(IPath)
   * @throws IOException 
   * 
   */
  @Test
  public void testReadFromFile() throws IOException {
    IPath certpath = new Path( "C:\\Dokumente und Einstellungen\\Tao-j\\Eigene Dateien\\usercert.pem" ); //$NON-NLS-1$
    PEMCertificate gridcert = PEMCertificate.readFromFile( certpath );
    Assert.assertEquals( "usercert", gridcert.getID() ); //$NON-NLS-1$
  }

  /**tests the method {@link PEMCertificate#write(IPath)}
   * @throws IOException 
   * 
   */
  // commented due to automatic tests
  /*@Test
  public void testWriteIPath() throws IOException {
      IPath certpath = new Path( "D:\\geclipsetest\\certfile"); //$NON-NLS-1$
      pemca.write( certpath );
  }*/
  
  /**tests the method PEMCertificate#read(IPath)
   * @throws IOException 
   * @throws IOException 
   * 
   */
  // commented due to automatic tests
 /* @Test
  public void testRead() throws IOException {
    IPath certpath = new Path( "file://d:/geclipsetest/gilda_ca.pem"); //$NON-NLS-1$
    byte[] result = PEMCertificate.read( certpath );
    Assert.assertEquals( new Integer( 32 ), new Integer( result[0]) );
  }*/

  /**tests the method PEMCertificate#secureClose(java.io.InputStream)
   * 
   */
  @Test
  public void testSecureCloseInputStream() {
    ByteArrayInputStream baiStream = new ByteArrayInputStream( cert );
    PEMCertificate.secureClose( baiStream );
  }

  /**tests the method PEMCertificate#secureClose(java.io.OutputStream)
   * 
   */
  @Test
  public void testSecureCloseOutputStream() {
    ByteArrayOutputStream baiStream = new ByteArrayOutputStream( 500 );
    PEMCertificate.secureClose( baiStream );
  }

  /**tests the method PEMCertificate#secureClose(java.io.Reader)
   * 
   */
  @Test
  public void testSecureCloseReader() {
    BufferedReader reader = new BufferedReader(
              new InputStreamReader( new ByteArrayInputStream( cert )));
   PEMCertificate.secureClose( reader );
  }

  /**tests the method PEMCertificate#secureClose(java.io.Writer)
   * @throws IOException 
   * 
   */
  @Test
  public void testSecureCloseWriter() throws IOException {
    BufferedWriter bWriter = new BufferedWriter( new FileWriter("test") ); //$NON-NLS-1$
    PEMCertificate.secureClose( bWriter );
  }

  
  /**tests the method PEMCertificate#write(byte[], IPath)
   * @throws IOException 
   * 
   */
  @Test
  public void testWriteByteArrayIPath() throws IOException {
    IPath certpath = new Path( "D:\\geclipsetest\\certfile"); //$NON-NLS-1$
    PEMCertificate.write( cert,certpath );
  }
}
