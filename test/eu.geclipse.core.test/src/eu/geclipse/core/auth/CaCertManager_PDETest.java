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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import eu.geclipse.core.GridException;

/**This class tests the methods in the CaCertManager class 
 * 
 * @author tao-j
 *
 */ 
public class CaCertManager_PDETest {

  CaCertManager cacertmanager;
  URL url;
  String caid;
  
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


  /** Tests the method {@link CaCertManager#importFromRepository()}
   * this test must be first performed, because other methods tests need CA
   */
  @Test
  public void testImportFromRepository() throws GridException
  {
    this.cacertmanager.importFromRepository( this.url, new NullProgressMonitor() );
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
    this.caid = calist[0].getID();
    Assert.assertNotNull( this.caid );
  }

  /** Tests the method {@link CaCertManager#getCertificate()}
   * with a true case (the avilable CA) and  afalse case (the wrong CA id)
   */
  @Test
  public void testGetCertificate()
  {
    ICaCertificate[] calist;
    calist = this.cacertmanager.getCertificates();
    this.caid = calist[0].getID();
    Assert.assertNotNull( this.caid );
    Assert.assertNull( this.cacertmanager.getCertificate( "myca" ) ); //$NON-NLS-1$
    Assert.assertNotNull( this.cacertmanager.getCertificate( this.caid ) );
  }

  /** Tests the method {@link CaCertManager#getCertLocation()}
   * it must not be at the junit-workspace
   */
  @Test
  public void testGetCaCertLocation()
  {
  Assert.assertEquals( "C:\\Dokumente und Einstellungen\\Tao-j\\workspaces\\junit-workspace\\.metadata\\.plugins\\eu.geclipse.core\\.certs", //$NON-NLS-1$
                             this.cacertmanager.getCaCertLocation().toOSString() );
  }
  
  /** Tests the method {@link CaCertManager#DeleteCertificate()}
   * it must not be deleted
   */
  @Test
  public void testDeleteCertificate()
  {
    ICaCertificate[] calist;
    calist = this.cacertmanager.getCertificates();
    this.caid = calist[0].getID();
    this.cacertmanager.deleteCertificate( this.caid );
    Assert.assertNull( this.cacertmanager.getCertificate(this.caid) );
  }

  @Test
  public void testImportFromDirectory() throws IOException
  {
   File calocate = new File ("C:\\Dokumente und Einstellungen\\Tao-j\\workspaces\\runtime-EclipseApplication\\.metadata\\.plugins\\eu.geclipse.core\\.certs"); //$NON-NLS-1$
   this.cacertmanager.importFromDirectory( calocate, new NullProgressMonitor() );
   Assert.assertNotNull( this.cacertmanager.getCertificates() );
   ICaCertificate[]calist = this.cacertmanager.getCertificates();
   this.caid = calist[0].getID();
   this.cacertmanager.deleteCertificate( this.caid );
  }
}
