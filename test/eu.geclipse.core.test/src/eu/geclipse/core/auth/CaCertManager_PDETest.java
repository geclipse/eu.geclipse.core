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

package eu.geclipse.core.auth;

import java.net.URL;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**This class tests the methods in the CaCertManager class 
 * 
 * @author tao-j
 *
 */ 
public class CaCertManager_PDETest {

  CaCertManager cacertmanager;
  URL url;
  String caid;
  
  /**setup
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception
  {
    
    this.cacertmanager = CaCertManager.getManager();
    this.url = new URL ("http://www.eugridpma.org/distribution/igtf/current/accredited/tgz/"); //$NON-NLS-1$
  } 
  
  /** Tests the method {@link CaCertManager#getManager()}
   * 
   */
  @Test
  public void testGetManager()
  {
   Assert.assertNotNull( this.cacertmanager );
  }


  /** Tests the method {@link CaCertManager#getCertificates()}
   * it must not be null
   */
  @Test
  public void testGetCertificates()
  {
    ICaCertificate[] calist;
    calist = this.cacertmanager.getCertificates();
    Assert.assertNotNull( calist );
  }

  /** Tests the method {@link CaCertManager#getCertificate(String)}
   * with a true case (the avilable CA) and  afalse case (the wrong CA id)
   */
  @Test
  public void testGetCertificate()
  {
    Assert.assertNull( this.cacertmanager.getCertificate( "myca" ) ); //$NON-NLS-1$
  }

  /** Tests the method getCertLocation
   * it must not be at the junit-workspace
   */
  @Test
  public void testGetCaCertLocation()
  {
    //removed for nightly build
  //Assert.assertEquals( "C:\\Dokumente und Einstellungen\\Tao-j\\workspaces\\junit-workspace\\.metadata\\.plugins\\eu.geclipse.core\\.certs", //$NON-NLS-1$
                         //    this.cacertmanager.getCaCertLocation().toOSString() );
  }
  
  /** Tests the method {@link CaCertManager#deleteCertificate(String)}
   * it must not be deleted
   */
  @Test
  public void testDeleteCertificate()
  {
    this.cacertmanager.deleteCertificate( "myca" ); //$NON-NLS-1$
    Assert.assertNull( this.cacertmanager.getCertificate("myca") ); //$NON-NLS-1$
  }

  /* removed for nightly build due to the path
   @Test
  public void testImportFromDirectory() throws IOException
  {
   File calocate = new File ("C:\\Dokumente und Einstellungen\\Tao-j\\workspaces\\runtime-EclipseApplication\\.metadata\\.plugins\\eu.geclipse.core\\.certs"); //$NON-NLS-1$
   this.cacertmanager.importFromDirectory( calocate, new NullProgressMonitor() );
   Assert.assertNotNull( this.cacertmanager.getCertificates() );
   ICaCertificate[]calist = this.cacertmanager.getCertificates();
   this.caid = calist[0].getID();
   this.cacertmanager.deleteCertificate( this.caid );
  }*/
}
