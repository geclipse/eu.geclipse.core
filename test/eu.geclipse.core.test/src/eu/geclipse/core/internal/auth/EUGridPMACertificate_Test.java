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
 *    Jie Tao - test class (Junit test)
 *****************************************************************************/

package eu.geclipse.core.internal.auth;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


/**this class tests the methods in {@link EUGridPMACertificate}
 * @author tao-j
 *
 */
public class EUGridPMACertificate_Test {

  private static EUGridPMACertificate pmca;
  private static byte[] info = {'#','@','(','#',')','$','I','d',':',
    0x33,0x39,0x33,0x66,0x37,0x38,0x36,0x33,'.','i','n','f','o',','};
  /**initialization with an UGridPMACertificate object
   * @throws Exception
   */
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    byte[] cert = {45,45,45,45,45,66,69,71,73,78,32,67,69,
              82,84,73,70,73,67,65,84,69};
    pmca = new EUGridPMACertificate(cert,info);
  }

  /**tests the method EUGridPMACertificate#getFileNames()
   * 
   */
  @Test
  public void testGetFileNames() {
    String[] result = pmca.getFileNames();
    Assert.assertEquals( "N/A.eugridpma", result[0] ); //$NON-NLS-1$
  }

  /**tests the method {@link EUGridPMACertificate#write(IPath)}
   * @throws IOException 
   * 
   */
  //commented due to automatic tests in the nightly build system
  /*@Test
  public void testWriteIPath() throws IOException {
    IPath certpath = new Path( "D:\\geclipsetest\\certfile"); //$NON-NLS-1$
    pmca.write( certpath );
  }*/

  /**tests the method EUGridPMACertificate#EUGridPMACertificate(byte[], byte[])
   * 
   */
  @Test
  public void testEUGridPMACertificate() {
    Assert.assertNotNull( pmca );
  }

  /**tests the method EUGridPMACertificate#readFromFile(IPath)
   * @throws IOException 
   * 
   */
  //commented due to automatic tests in the nightly build system
  /*@Test
  public void testReadFromFileIPath() throws IOException {
    IPath certpath = new Path( "C:\\Dokumente und Einstellungen\\Tao-j\\Eigene Dateien\\usercert.pem" ); //$NON-NLS-1$
    EUGridPMACertificate gridcert = EUGridPMACertificate.readFromFile( certpath );
    Assert.assertEquals( "", gridcert.getID() ); //$NON-NLS-1$
  }*/

  /**tests the method EUGridPMACertificate#getInfoData()
   * 
   */
  @Test
  public void testGetInfoData() {
    Assert.assertEquals( info, pmca.getInfoData() );
  }
}
