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
package eu.geclipse.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the functionality of each method in the class Preferences.
 * In most cases, the test is associated with both Set and Get methods
 */

public class Preferences_Test {
  private Preferences preferences;

  @Before
  public void setUp() throws Exception
  {
    this.preferences = new Preferences ();
  }
  
  /**
   * Test for the <code>eu.geclipse.core.Preferences#setConnectionTimeout/code>
   * and <code>eu.geclipse.core.Preferences#getConnectionTimeout/code>. 
   * The got timeout value must be the set value!
   * @author jie
   */
  
  @Test
  public void testSet_GetConnectionTimeout()
  {
    int timeout_in = 30;
    int timeout_out;
    Assert.assertNotNull( this.preferences );
    Preferences.setConnectionTimeout( timeout_in );
    timeout_out = Preferences.getConnectionTimeout();
    Assert.assertEquals( new Integer( timeout_out ), new Integer( timeout_in ));
  }

  /**
   * Test for the <code>eu.geclipse.core.Preferences#getDefaultConnectionTimeout/code>
   * class. The default timeout value can not be 0!
   * @author jie
   */
  
  @Test
  public void testGetDefaultConnectionTimeout()
  {
    int timeout;
    timeout = Preferences.getDefaultConnectionTimeout();
    Assert.assertFalse( timeout == 0 );
    Assert.assertTrue( timeout == 20 );
  }

  /**
   * Test for the <code>eu.geclipse.core.Preferences#setProxyEnabled/code>
   * and <code>eu.geclipse.core.Preferences#getProxyEnabled/code>.
   * The set and got values must be identical!
   * @author jie
   */
  
  @Test
  public void testSet_GetProxyEnabled()
  {
    boolean enabled_set = true;
    boolean enabled_get;
    Preferences.setProxyEnabled( enabled_set );
    enabled_get = Preferences.getProxyEnabled();
    Assert.assertEquals( new Boolean( enabled_get ),new Boolean( enabled_set ));
    enabled_set = false;
    Preferences.setProxyEnabled( enabled_set );
    enabled_get = Preferences.getProxyEnabled();
    Assert.assertFalse( enabled_get == true );
  }

  /**
   * Test for the <code>eu.geclipse.core.Preferences#getDefaultProxyEnabled/code>
   * method. The default value shall be false!
   * @author jie
   */
  
  @Test
  public void testGetDefaultProxyEnabled()
  {
    boolean enabled;
    enabled = Preferences.getProxyEnabled();
    Assert.assertFalse( enabled == true );
  }

  /**
   * Test for the <code>eu.geclipse.core.Preferences#setProxyHost/code>
   * and <code>eu.geclipse.core.Preferences#getProxyHost/code>.
   * The set and got values must be identical!
   * @author jie
   */
  
  @Test
  public void testSet_GetProxyHost()
  {
    String host_set = "proxy.uni-karlsruhe.de"; //$NON-NLS-1$
    String host_get;
    Preferences.setProxyHost( host_set );
    host_get = Preferences.getProxyHost();
    Assert.assertEquals( host_get,host_set );  
  }

  /**
   * Test for the <code>eu.geclipse.core.Preferences#getDefaultProxyHost/code>
   * method. The result must be "" (see PerformanceInitializer.java)!
   * @author jie
   */
  
  @Test
  public void testGetDefaultProxyHost()
  {
    String host;
    host = Preferences.getDefaultProxyHost();
    Assert.assertEquals( host,"" ); //$NON-NLS-1$
  }

  /**
   * Test for the <code>eu.geclipse.core.Preferences#setProxyPort/code>
   * and <code>eu.geclipse.core.Preferences#getProxyPort/code>.
   * The set and got values must be identical!
   * @author jie
   */
  
  @Test
  public void testSet_GetProxyPort()
  {
    int port_set = 8000;
    int port_get;
    Preferences.setProxyPort( port_set );
    port_get = Preferences.getProxyPort();
    Assert.assertEquals( new Integer( port_get ),new Integer( port_set ));
  }

  /**
   * Test for the <code>eu.geclipse.core.Preferences#getDefaultProxyPort/code>
   * method. The result must be 0!
   * @author jie
   */

  @Test
  public void testGetDefaultProxyPort()
  {
    int port;
    port = Preferences.getDefaultProxyPort();
    Assert.assertTrue( port == 0 );
  }

  /**
   * Test for the <code>eu.geclipse.core.Preferences#setProxyAuthenticationRequired/code>
   * and <code>eu.geclipse.core.Preferences#getProxyPAuthenticationRequired/code>.
   * The set and got values must be identical!
   * @author jie
   */
  
  @Test
  public void testSet_GetProxyAuthenticationRequired()
  {
    boolean required_set = true;
    boolean required_get;
    Preferences.setProxyAuthenticationRequired( required_set);
    required_get = Preferences.getProxyAuthenticationRequired();
    Assert.assertEquals( new Boolean( required_get ),new Boolean( required_set ));
  }

  /**
   * Test for the <code>eu.geclipse.core.Preferences#getDefaultProxyAuthenticationRequired/code>
   * method. The result must be false!
   * @author jie
   */
  
  @Test
  public void testGetDefaultProxyAuthenticationRequired()
  {
    boolean required;
    required = Preferences.getDefaultProxyAuthenticationRequired();
    Assert.assertFalse( required == true );
  }

  /**
   * Test for the <code>eu.geclipse.core.Preferences#setProxyAuthenticationLogin/code>
   * and <code>eu.geclipse.core.Preferences#getProxyPAuthenticationLogin/code>.
   * The set and got values must be identical!
   * @author jie
   */
  
  @Test
  public void testSet_SetProxyAuthenticationLogin()
  {
    String login_set = "username"; //$NON-NLS-1$
    String login_get;
    Preferences.setProxyAuthenticationLogin( login_set );
    login_get = Preferences.getProxyAuthenticationLogin();
    Assert.assertEquals( login_get,login_set );  
  }

  /**
   * Test for the <code>eu.geclipse.core.Preferences#getDefaultProxyLogin/code>
   * method. The result must be "" (see PerformanceInitializer.java)!
   * @author jie
   */
 
  @Test
  public void testGetDefaultProxyAuthenticationLogin()
  {
    String login;
    login = Preferences.getDefaultProxyAuthenticationLogin();
    Assert.assertEquals( login,"" ); //$NON-NLS-1$
  }

  /**
   * Test for the <code>eu.geclipse.core.Preferences#setProxyAuthenticationPassword/code>
   * and <code>eu.geclipse.core.Preferences#getProxyPAuthenticationPassword/code>.
   * The set and got values must be identical!
   * @author jie
   */
  
  @Test
  public void testSet_GetProxyAuthenticationPassword()
  {
    String passwd_set = "xxxxx"; //$NON-NLS-1$
    String passwd_get;
    Preferences.setProxyAuthenticationPassword( passwd_set );
    passwd_get = Preferences.getProxyAuthenticationPassword();
    Assert.assertEquals( passwd_get,passwd_set );  
  }

  /**
   * Test for the <code>eu.geclipse.core.Preferences#getDefaultProxyPassword/code>
   * method. The result must be "" (see PerformanceInitializer.java)!
   * @author jie
   */

  @Test
  public void testGetDefaultProxyAuthenticationPassword()
  {
    String passwd;
    passwd = Preferences.getDefaultProxyAuthenticationPassword();
    Assert.assertEquals( passwd,"" ); //$NON-NLS-1$
  }

  /**
   * Test for the <code>eu.geclipse.core.Preferences#getProxy/code>
   * method.
   * @author jie
   */
  @Test
  public void testGetProxy()
  {
    Proxy proxy;
    String ptype = "HTTP"; //$NON-NLS-1$
    SocketAddress address;
    String proxyhost = "proxy.fzk.de"; //$NON-NLS-1$
    int proxyport = 8000;
    int port;
    String host;
    boolean enabled = true;
    proxy = Preferences.getProxy();
    Assert.assertEquals( proxy,Proxy.NO_PROXY );
    Assert.assertNull( proxy.address() ); // a DIRECT connection
    Preferences.setProxyHost( proxyhost );
    Preferences.setProxyPort( proxyport ); 
    Preferences.setProxyEnabled( enabled );  
    proxy = Preferences.getProxy();
    address = proxy.address();
    Assert.assertNotNull( proxy );
    Assert.assertNotNull( address ); // a proxy connection
    Assert.assertEquals( proxy.type(),Proxy.Type.valueOf( ptype ));
    host = (( InetSocketAddress )address ).getHostName();
    Assert.assertEquals( host,proxyhost );
    port = (( InetSocketAddress )address ).getPort();
    Assert.assertEquals( new Integer( port ),new Integer( proxyport ));
  }

  /**
   * Test for the <code>eu.geclipse.core.Preferences#getURLConnection/code>
   * method.
   * @throws MalformedURLException
   * @throws IOException
   * @author jie
   */
  @Test
  public void testGetURLConnection() throws MalformedURLException, IOException
  {
    URL url = new URL( "http://www.geclipse.eu/" ); //$NON-NLS-1$
    URLConnection urlconnect;
    String proxyhost = "proxy.fzk.de"; //$NON-NLS-1$
    int proxyport = 8000;
    boolean enabled = true;
    Preferences.setProxyHost( proxyhost );
    Preferences.setProxyPort( proxyport ); 
    Preferences.setProxyEnabled( enabled );
    urlconnect = Preferences.getURLConnection( url );
    Assert.assertNotNull( urlconnect );
    Assert.assertEquals( urlconnect.getURL(), url );
  }

  /**
   * Test for the <code>eu.geclipse.core.Preferences#setDefaultVoName/code>
   * and <code>eu.geclipse.core.Preferences#getDefaultVoName/code>.
   * The set and got values must be identical!
   * @author jie
   */
  
  @Test
  public void testSet_GetDefaultVoName()
  {
    String vo_set = "geclipse"; //$NON-NLS-1$  
    String vo_get;
    Preferences.setDefaultVoName( vo_set );
    vo_get = Preferences.getDefaultVoName();
    Assert.assertNotNull( vo_get );
    Assert.assertEquals( vo_get,vo_set );
  }
}
