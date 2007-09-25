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
 *    Jie Tao - test class (Plug-in test)
 *****************************************************************************/

package eu.geclipse.core.internal.auth;

import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.GridException;
import eu.geclipse.core.auth.ICaCertificate;

/**this class tests the methods in {@link PEMCertificateLoader}
 * @author tao-j
 *
 */

public class PEMCertificateLoader_PDETest {


  private static PEMCertificateLoader loader;
  /**initialization with a PEMCertificateLoader object
   * and set proxy
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    loader = new PEMCertificateLoader();
    //set proxy for internet connection
    IProxyService proxyservice = eu.geclipse.core.internal.Activator.getDefault().getProxyService(); 
    String httpproxyhost = "proxy.fzk.de"; //$NON-NLS-1$
    int httpport = 8000;
    String sslproxyhost = "proxy.fzk.de"; //$NON-NLS-1$
    int sslport = 443;
    proxyservice.setProxiesEnabled( true );
    eu.geclipse.core.test.internal.ProxyData[] proxies = {
      new eu.geclipse.core.test.internal.ProxyData(IProxyData.HTTP_PROXY_TYPE,httpproxyhost,httpport,false),
      new eu.geclipse.core.test.internal.ProxyData(IProxyData.HTTPS_PROXY_TYPE,sslproxyhost,sslport,false)
    };
    proxyservice.setProxyData( proxies );
  }

  /**tests the method {@link PEMCertificateLoader#getCertificate(IPath)}
   * @throws GridException 
   * 
   */
  @Test
  public void testGetCertificateIPath() throws GridException {
      IPath certpath = new Path( "file:///d:/geclipsetest/"); //$NON-NLS-1$
      ICaCertificate cert = loader.getCertificate( certpath );
      Assert.assertNull( cert );
  }

  /**tests the method {@link PEMCertificateLoader#getCertificate
   *   (URI, String, org.eclipse.core.runtime.IProgressMonitor)}
   * @throws URISyntaxException 
   * @throws GridException 
   */

  //commented due to automatic test
  /*@Test
  public void testGetCertificateURIStringIProgressMonitor() throws URISyntaxException, GridException {
    URI uri = new URI( 
    "file:///c:/Dokumente und Einstellungen/Tao-j/
    workspaces/runtime-EclipseApplication/.metadata/.plugins/eu.geclipse.core/.certs"); //$NON-NLS-1$
    Assert.assertEquals
    ( "AEGIS",loader.getCertificate( uri, "AEGIS.info", new NullProgressMonitor() ).getID()); //$NON-NLS-1$ //$NON-NLS-2$
  }*/


  /**tests the method {@link PEMCertificateLoader#getCertificateList
   *   (URI, org.eclipse.core.runtime.IProgressMonitor)}
   * @throws URISyntaxException 
   * @throws GridException 
   * 
   */
  
  //commented due to automatic test
  /*@Test
  public void testGetCertificateList() throws URISyntaxException, GridException {
    URI uri = new URI( 
    "file:///c:/Dokumente und Einstellungen/
    Tao-j/workspaces/runtime-EclipseApplication/.metadata/.plugins/eu.geclipse.core/.certs"); //$NON-NLS-1$                
    String[] list = loader.getCertificateList( uri, null );
    Assert.assertNotNull( list );
    Assert.assertEquals( "ca_AEGIS-1.16.tar.gz", list[0].toString() ); //$NON-NLS-1$
  }*/
  
  /**tests the method {@link PEMCertificateLoader#
   *   getPredefinedRemoteLocations()}
   * 
   */
  @Test
  public void testGetPredefinedRemoteLocations() {
     Assert.assertNull( loader.getPredefinedRemoteLocations() );
  }
}
