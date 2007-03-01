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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.internal.PreferenceConstants;
import eu.geclipse.core.security.Base64Codec;

/**
 * This class gives easy access to the core preferences of g-Eclipse.
 * 
 * @author stuempert-m
 */

public class Preferences {
  
  /**
   * Set the timeout value for URL connections in seconds.
   * 
   * @param timeout The new timeout that is used when creating
   * {@link URLConnection}s with {@link #getURLConnection(URL)}.
   * @see #getURLConnection(URL)
   */
  static public void setConnectionTimeout( final int timeout ) {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    preferenceStore.setValue( PreferenceConstants.CONNECTION_TIMEOUT, timeout );
  }
  
  /**
   * Get the current value of the connection timeout in seconds.
   * 
   * @return The value that is used when creating
   * {@link URLConnection}s with {@link #getURLConnection(URL)}.
   * @see #getURLConnection(URL)
   */
  static public int getConnectionTimeout() {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    int timeout = preferenceStore.getInt( PreferenceConstants.CONNECTION_TIMEOUT );
    return timeout;
  }
  
  /**
   * Get the default value for the connection timeout in seconds.
   * 
   * @return The default value for the connection timeout.
   */
  static public int getDefaultConnectionTimeout() {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    int timeout = preferenceStore.getDefaultInt( PreferenceConstants.CONNECTION_TIMEOUT );
    return timeout;
  }
  
  /**
   * Set the proxy enabled preference to the specified value.
   * 
   * @param enabled The new value of the proxy enabled preference.
   */
  static public void setProxyEnabled( final boolean enabled ) {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    preferenceStore.setValue( PreferenceConstants.HTTP_PROXY_ENABLED, enabled );
  }
  
  /**
   * Get the current value of the proxy enabled preference.
   * 
   * @return The value of the proxy enabled preference.
   */
  static public boolean getProxyEnabled() {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    boolean proxyEnabled = preferenceStore.getBoolean( PreferenceConstants.HTTP_PROXY_ENABLED );
    return proxyEnabled;
  }
  
  /**
   * Get the default value of the proxy enabled preference.
   * 
   * @return The default value of the proxy enabled preference.
   */
  static public boolean getDefaultProxyEnabled() {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    boolean proxyEnabled = preferenceStore.getDefaultBoolean( PreferenceConstants.HTTP_PROXY_ENABLED );
    return proxyEnabled;
  }
  
  /**
   * Set the proxy host preference to the specified value.
   * 
   * @param host The new value of the proxy host preference.
   */
  static public void setProxyHost( final String host ) {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    preferenceStore.setValue( PreferenceConstants.HTTP_PROXY_HOST, host );
  }
  
  /**
   * Get the current value of the proxy host preference.
   * 
   * @return The value of the proxy host preference.
   */
  static public String getProxyHost() {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    String proxyHost = preferenceStore.getString( PreferenceConstants.HTTP_PROXY_HOST );
    return proxyHost;
  }
  
  /**
   * Get the default value of the proxy host preference.
   * 
   * @return The default value of the proxy host preference.
   */
  static public String getDefaultProxyHost() {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    String proxyHost = preferenceStore.getDefaultString( PreferenceConstants.HTTP_PROXY_HOST );
    return proxyHost;
  }

  /**
   * Set the proxy port preference to the specified value.
   * 
   * @param port The new value of the proxy port preference.
   */
  static public void setProxyPort( final int port ) {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    preferenceStore.setValue( PreferenceConstants.HTTP_PROXY_PORT, port );
  }
  
  /**
   * Get the current value of the proxy port preference.
   * 
   * @return The value of the proxy port preference.
   */
  static public int getProxyPort() {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    int proxyPort = preferenceStore.getInt( PreferenceConstants.HTTP_PROXY_PORT );
    return proxyPort;
  }
  
  /**
   * Get the default value of the proxy port preference.
   * 
   * @return The default value of the proxy port preference.
   */
  static public int getDefaultProxyPort() {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    int proxyPort = preferenceStore.getDefaultInt( PreferenceConstants.HTTP_PROXY_PORT );
    return proxyPort;
  }
  
  /**
   * Set the proxy authentication required preference to the specified value.
   * 
   * @param required The new value of the proxy authentication required preference.
   */
  static public void setProxyAuthenticationRequired( final boolean required ) {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    preferenceStore.setValue( PreferenceConstants.HTTP_PROXY_AUTH_REQUIRED, required );
  }
  
  /**
   * Get the current value of the proxy authentication required preference.
   * 
   * @return The value of the proxy authentication required preference.
   */
  static public boolean getProxyAuthenticationRequired() {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    boolean authRequired = preferenceStore.getBoolean( PreferenceConstants.HTTP_PROXY_AUTH_REQUIRED );
    return authRequired;
  }
  
  /**
   * Get the default value of the proxy authentication required preference.
   * 
   * @return The default value of the proxy authentication required preference.
   */
  static public boolean getDefaultProxyAuthenticationRequired() {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    boolean authRequired = preferenceStore.getDefaultBoolean( PreferenceConstants.HTTP_PROXY_AUTH_REQUIRED );
    return authRequired;
  }
  
  /**
   * Set the proxy authentication login preference to the specified value.
   * 
   * @param login The new value of the proxy authentication login preference.
   */
  static public void setProxyAuthenticationLogin( final String login ) {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    preferenceStore.setValue( PreferenceConstants.HTTP_PROXY_AUTH_LOGIN, login );
  }
  
  /**
   * Get the current value of the proxy authentication login preference.
   * 
   * @return The value of the proxy authentication login preference.
   */
  static public String getProxyAuthenticationLogin() {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    String authLogin = preferenceStore.getString( PreferenceConstants.HTTP_PROXY_AUTH_LOGIN );
    return authLogin;
  }
  
  /**
   * Get the default value of the proxy authentication login preference.
   * 
   * @return The default value of the proxy authentication login preference.
   */
  static public String getDefaultProxyAuthenticationLogin() {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    String authLogin = preferenceStore.getDefaultString( PreferenceConstants.HTTP_PROXY_AUTH_LOGIN );
    return authLogin;
  }
  
  /**
   * Set the proxy authentication password preference to the specified value.
   * 
   * @param password The new value of the proxy authentication password preference.
   */
  static public void setProxyAuthenticationPassword( final String password ) {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    preferenceStore.setValue( PreferenceConstants.HTTP_PROXY_AUTH_PW, password );
  }
  
  /**
   * Get the current value of the proxy authentication password preference.
   * 
   * @return The value of the proxy authentication password preference.
   */
  static public String getProxyAuthenticationPassword() {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    String authPassword = preferenceStore.getString( PreferenceConstants.HTTP_PROXY_AUTH_PW );
    return authPassword;
  }
  
  /**
   * Get the default value of the proxy authentication password preference.
   * 
   * @return The default value of the proxy authentication password preference.
   */
  static public String getDefaultProxyAuthenticationPassword() {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    String authPassword = preferenceStore.getDefaultString( PreferenceConstants.HTTP_PROXY_AUTH_PW );
    return authPassword;
  }
  
  /**
   * Get a <code>Proxy</code> object from the current proxy related preferences.
   * If the proxy enabled preference is not set a <code>Proxy.NO_PROXY</code> object
   * is returned. Otherwise a <code>Proxy</code> is generated from the host and
   * port preferences.
   * 
   * @return A <code>Proxy</code> object from the related preferences.
   */
  static public Proxy getProxy() {
    boolean enabled = getProxyEnabled();
    Proxy proxy = Proxy.NO_PROXY;
    if ( enabled ) {
      String proxyHost = getProxyHost();
      int proxyPort = getProxyPort();
      InetSocketAddress addr = new InetSocketAddress( proxyHost, proxyPort );
      proxy = new Proxy( Proxy.Type.HTTP, addr );
    }
    return proxy;
  }
  
  static public URLConnection getURLConnection( final URL url )
      throws IOException {
    
    Proxy proxy = getProxy();
    int timeout = getConnectionTimeout() * 1000;
    URLConnection connection = url.openConnection( proxy );
    connection.setConnectTimeout( timeout );
    boolean proxyAuthRequired = getProxyAuthenticationRequired();
    
    if ( proxyAuthRequired ) {
      String proxyAuthLogin = getProxyAuthenticationLogin();
      String proxyAuthPw = getProxyAuthenticationPassword();
      String encoded = Base64Codec.encode( proxyAuthLogin + ":" + proxyAuthPw ); //$NON-NLS-1$
      connection.setRequestProperty( "Proxy-Authorization", "Basic " + encoded ); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    return connection;
    
  }
  
  static public void setDefaultVoName( final String defaultVoName ) {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    preferenceStore.setValue( PreferenceConstants.DEFAULT_VO_ID, defaultVoName );
  }
  
  static public String getDefaultVoName() {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    String defaultVoName = preferenceStore.getString( PreferenceConstants.DEFAULT_VO_ID );
    return defaultVoName;
  }
  
  static public void save() {
    Activator.getDefault().savePluginPreferences();
  }
  
  /**
   * Get the preference store of the core preferences.
   * 
   * @return The preference store of the g-Eclipse core.
   */
  static protected org.eclipse.core.runtime.Preferences getPreferenceStore() {
    Activator activator = Activator.getDefault();
    org.eclipse.core.runtime.Preferences preferenceStore = activator.getPluginPreferences();
    return preferenceStore;
  }
  
}
