/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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

package eu.geclipse.aws.internal;

import java.util.Properties;

import org.eclipse.core.net.proxy.IProxyChangeEvent;
import org.eclipse.core.net.proxy.IProxyChangeListener;
import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.jets3t.service.Jets3tProperties;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends Plugin implements IProxyChangeListener {

  /** The plug-in ID */
  public static final String PLUGIN_ID = "eu.geclipse.aws"; //$NON-NLS-1$

  private static final String HTTP_RETRY_COUNT_KEY = "httpclient.retry-max"; //$NON-NLS-1$

  private static final String HTTP_PROXY_AUTO_DETECT_KEY = "httpclient.proxy-autodetect"; //$NON-NLS-1$

  private static final String HTTP_PROXY_HOST_KEY = "httpclient.proxy-host"; //$NON-NLS-1$

  private static final String HTTP_PROXY_PORT_KEY = "httpclient.proxy-port"; //$NON-NLS-1$

  private static final String JETS3T_PROPERTIES = "jets3t.properties"; //$NON-NLS-1$

  // The shared instance
  private static Activator plugin;

  private ServiceTracker tracker;

  /**
   * The constructor
   */
  public Activator() {
    Jets3tProperties props = getJets3tProperties();
    props.setProperty(HTTP_RETRY_COUNT_KEY, "2"); //$NON-NLS-1$
    props.setProperty(HTTP_PROXY_AUTO_DETECT_KEY, "false"); //$NON-NLS-1$
  }

  /**
   * Get the properties for the jets3t plug-in.
   * 
   * @return The jets3t properties.
   */
  public Jets3tProperties getJets3tProperties() {
    return Jets3tProperties.getInstance(JETS3T_PROPERTIES);
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(final BundleContext context) throws Exception {
    super.start(context);
    plugin = this;
    this.tracker = new ServiceTracker(getBundle().getBundleContext(),
        IProxyService.class.getName(), null);
    this.tracker.open();
    getProxyService().addProxyChangeListener(this);
    updateProxySettings();
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(final BundleContext context) throws Exception {
    plugin = null;
    super.stop(context);
  }

  /**
   * Returns the shared instance.
   *
   * @return the shared instance.
   */
  public static Activator getDefault() {
    return plugin;
  }

  /**
   * Logs an exception to the eclipse logger.
   * 
   * @param exc The exception to be logged.
   */
  public static void logException(final Throwable exc) {
    String message = exc.getLocalizedMessage();
    if (message == null)
      message = exc.getClass().getName();
    IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, message,
        exc);
    logStatus(status);
  }

  /**
   * Logs a status object to the eclipse logger.
   * 
   * @param status The status to be logged.
   */
  public static void logStatus(final IStatus status) {
    getDefault().getLog().log(status);
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

  /* (non-Javadoc)
   * @see org.eclipse.core.net.proxy.IProxyChangeListener#proxyInfoChanged(org.eclipse.core.net.proxy.IProxyChangeEvent)
   */
  public void proxyInfoChanged( final IProxyChangeEvent event ) {
    updateProxySettings();
  }

  /**
   * Update the proxy settings of the jets3t library.
   */
  private void updateProxySettings() {

    IProxyService proxyService = getProxyService();
    boolean enabled = proxyService.isProxiesEnabled();
    Jets3tProperties jets3tProperties = getJets3tProperties();

    if (enabled) {
      IProxyData proxyData = proxyService
          .getProxyData(IProxyData.HTTP_PROXY_TYPE);
      String host = proxyData.getHost();
      String port = String.valueOf(proxyData.getPort());
      jets3tProperties.setProperty(HTTP_PROXY_HOST_KEY, host);
      jets3tProperties.setProperty(HTTP_PROXY_PORT_KEY, port);
    } else {
      Properties properties = jets3tProperties.getProperties();
      properties.remove(HTTP_PROXY_HOST_KEY);
      properties.remove(HTTP_PROXY_PORT_KEY);
    }
    
    ServiceRegistry.getRegistry().clear();

  }

}
