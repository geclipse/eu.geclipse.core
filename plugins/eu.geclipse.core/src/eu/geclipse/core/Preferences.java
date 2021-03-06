/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
 *    Szymon Mueller
 *****************************************************************************/

package eu.geclipse.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.internal.PreferenceConstants;
import eu.geclipse.core.security.Base64;

/**
 * This class gives easy access to the core preferences of g-Eclipse.
 * 
 * @author stuempert-m
 */

public class Preferences {
  
  private static final String HTTP_SCHEME = "http"; //$NON-NLS-1$
  
  private static final String HTTPS_SCHEME = "https"; //$NON-NLS-1$
  
  private static final String SOCKS_SCHEME = "socks"; //$NON-NLS-1$
  
  /**
   * Get an {@link URLConnection} that is initialised with the current proxy and
   * timeout settings.
   * 
   * @param url The {@link URL} from which to create the connection.
   * @return The connection created from the specified URL and initialised
   * with the current proxy and timeout settings.
   * @throws IOException If an IO-error occurs while this connection is
   * created. 
   */
  static public URLConnection getURLConnection( final URL url )
      throws IOException {
    
    URLConnection connection = null;
    IProxyService proxyService = Activator.getDefault().getProxyService();
    
    if ( ( proxyService != null ) && proxyService.isProxiesEnabled() ) {
      
      String host = url.getHost();
      String type = url.getProtocol();
      if ( type.equalsIgnoreCase( HTTP_SCHEME ) ) {
        type = IProxyData.HTTP_PROXY_TYPE;
      } else if ( type.equalsIgnoreCase( HTTPS_SCHEME ) ) {
        type = IProxyData.HTTPS_PROXY_TYPE;
      } else if ( type.equalsIgnoreCase( SOCKS_SCHEME ) ) {
        type = IProxyData.SOCKS_PROXY_TYPE;
      }
      
      IProxyData proxyData = proxyService.getProxyDataForHost( host, type );
      
      if ( proxyData != null ) {
      
        String proxyHost = proxyData.getHost();
        int proxyPort = proxyData.getPort();
        InetSocketAddress addr = new InetSocketAddress( proxyHost, proxyPort );
        Proxy proxy = new Proxy( Proxy.Type.HTTP, addr );
        connection = url.openConnection( proxy );
        
        if ( proxyData.isRequiresAuthentication() ) {
          String proxyAuthLogin = proxyData.getUserId();
          String proxyAuthPw = proxyData.getPassword();
          String encoded = new Base64().encode( proxyAuthLogin + ":" + proxyAuthPw ); //$NON-NLS-1$
          connection.setRequestProperty( "Proxy-Authorization", "Basic " + encoded ); //$NON-NLS-1$ //$NON-NLS-2$
        }
        
      }
      
    }
    
    if ( connection == null ) {
      connection = url.openConnection( Proxy.NO_PROXY );
    }
    
    return connection;
    
  }
  
  /**
   * Set the name of the current default VO.
   * 
   * @param defaultVoName The name of the default VO.
   */
  static public void setDefaultVoName( final String defaultVoName ) {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    preferenceStore.setValue( PreferenceConstants.DEFAULT_VO_ID, defaultVoName );
  }
  
  /**
   * Get the name of the current default VO.
   * 
   * @return The name of the default VO.
   */
  static public String getDefaultVoName() {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    String defaultVoName = preferenceStore.getString( PreferenceConstants.DEFAULT_VO_ID );
    return defaultVoName;
  }
  
  /**
   * Sets the status of the job updates
   * @param status Status of updates to be set
   */
  static public void setUpdateJobsStatus( final boolean status ) {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    preferenceStore.setValue( PreferenceConstants.JOBS_UPDATE_JOBS_STATUS, status );
  }
  
  /**
   * Gets the status of the job updates
   * @return Status of job updates
   */
  static public boolean getUpdateJobsStatus() {
    boolean valueUpdateJobsStatus = false;
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    if ( preferenceStore != null ) {
       valueUpdateJobsStatus = preferenceStore.getBoolean( PreferenceConstants.JOBS_UPDATE_JOBS_STATUS );
    }
    return valueUpdateJobsStatus;
  }
  
  static public void setJobUpdaterCancelBehaviour( final boolean cancel ) {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    preferenceStore.setValue( PreferenceConstants.JOBS_UPDATE_JOBS_CANCEL_BAHAVIOUR, cancel );
  }
  
  /**
   * Gets the behaviour of the global job status updating when user cancels token
   * request of the job status updater.
   * @return <code>true</code> if cancel should turn off global updating. <br>
   *         <code>false</code> if cancel shouldn't change the status of the global updating.
   */
  static public boolean getJobUpdaterCancelBehaviour() {
    boolean valueUpdateCancelBehaviour = false;
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    if( preferenceStore != null ) {
      valueUpdateCancelBehaviour = preferenceStore.getBoolean( PreferenceConstants.JOBS_UPDATE_JOBS_CANCEL_BAHAVIOUR );
    }
    return valueUpdateCancelBehaviour;
  }
  
  /**
   * Sets the time period (in miliseconds) between job updates
   * @param period Period between job status' updates
   */
  static public void setUpdateJobsPeriod( final int period ) {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    preferenceStore.setValue( PreferenceConstants.JOBS_UPDATE_JOBS_PERIOD, period );
  }
  
  /**
   * Gets the time period (in seconds)
   * @return Time in seconds between job status' udpates
   */
  static public int getUpdateJobsPeriod() {
    int valueUpdateJobsPeriod = 30;
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    if ( preferenceStore != null ) {
      valueUpdateJobsPeriod = preferenceStore.getInt( PreferenceConstants.JOBS_UPDATE_JOBS_PERIOD );
    }
    return valueUpdateJobsPeriod * 1000;
  }
  
  /**
   * Sets the limit of the simultaneously running job status updaters
   * @param limit Maximum number of the running updaters.
   */
  static public void setUpdatersLimit( final int limit ) {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    preferenceStore.setValue( PreferenceConstants.JOBS_UPDATE_UPDATERS_LIMTI, limit );
  }
  
  /**
   * Gets the limit of the simultaneously running job status updaters
   * @return Maximum number of the running updaters.
   */
  static public int getUpdatersLimit() {
    org.eclipse.core.runtime.Preferences preferenceStore = getPreferenceStore();
    int valueUpdatersLimit = preferenceStore.getInt( PreferenceConstants.JOBS_UPDATE_UPDATERS_LIMTI );
    return valueUpdatersLimit;
  }
  
  /**
   * Save these preferences to the preference store. This method just calls
   * <code>Activator.getDefault().savePluginPreferences();</code>.
   */
  static public void save() {
    Activator.getDefault().savePluginPreferences();
  }
  
  /**
   * Get the preference store of the core preferences.
   * 
   * @return The preference store of the g-Eclipse core.
   */
  static protected org.eclipse.core.runtime.Preferences getPreferenceStore() {
    org.eclipse.core.runtime.Preferences preferenceStore = null;
    Activator activator = Activator.getDefault();
    if ( activator != null ) {
      preferenceStore = activator.getPluginPreferences();
    }
    return preferenceStore;
  }
  
}
