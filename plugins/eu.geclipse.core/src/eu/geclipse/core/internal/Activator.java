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

package eu.geclipse.core.internal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.eclipse.core.net.proxy.IProxyService;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import eu.geclipse.core.internal.security.CertificateManager;
import eu.geclipse.core.security.Security;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends Plugin {

  /** The plug-in ID */
  public static final String PLUGIN_ID = "eu.geclipse.core"; //$NON-NLS-1$
  
  /** The shared instance */
  private static Activator plugin;
  
  private ServiceTracker tracker;

  /**
   * The constructor
   */
  public Activator() {
    plugin = this;
  }
  
  /**
   * Return the {@link IProxyService} or <code>null</code> if the service is
   * not available.
   * 
   * @return the {@link IProxyService} or <code>null</code>
   */
  public IProxyService getProxyService() {
    return (IProxyService) this.tracker.getService();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start( final BundleContext context ) throws Exception {
    
    super.start( context );
    
    this.tracker = new ServiceTracker( getBundle().getBundleContext(),
                                       IProxyService.class.getName(),
                                       null );
    this.tracker.open();
    
    try {
      SSLSocketFactory socketFactory = Security.getSocketFactory();
      HttpsURLConnection.setDefaultSSLSocketFactory( socketFactory );
    } catch ( Exception exc ) {
      logException( exc );
    }
    
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop( final BundleContext context ) throws Exception {
    plugin = null;
    super.stop( context );
    this.tracker.close();
  }

  /**
   * Returns the shared instance
   * 
   * @return the shared instance
   */
  public static Activator getDefault() {
    return plugin;
  }

  /**
   * Logs an exception to the eclipse logger.
   * 
   * @param exc The exception to be logged.
   */
  public static void logException( final Throwable exc ) {
    String message = exc.getLocalizedMessage();
    if( message == null )
      message = exc.getClass().getName();
    IStatus status = new Status( IStatus.ERROR,
                                 PLUGIN_ID,
                                 IStatus.OK,
                                 message,
                                 exc );
    logStatus( status );
  }

  /**
   * Logs a status object to the eclipse logger.
   * 
   * @param status The status to be logged.
   */
  public static void logStatus( final IStatus status ) {
    getDefault().getLog().log( status );
  }
  
}
