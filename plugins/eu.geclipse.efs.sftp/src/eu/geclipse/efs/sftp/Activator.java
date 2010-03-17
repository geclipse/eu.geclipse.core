/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
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
 *    Christof Klausecker GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.efs.sftp;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.jsch.core.IJSchService;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

  /** The plug-in ID */
  public static final String PLUGIN_ID = "eu.geclipse.efs.sftp"; //$NON-NLS-1$
  // The shared instance
  private static Activator plugin;
  private ServiceTracker tracker;

  /**
   * The constructor
   */
  public Activator() {
    // empty
  }

  /*
   * (non-Javadoc)
   * @see
   * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
   * )
   */
  @Override
  public void start( final BundleContext context ) throws Exception {
    super.start( context );
    this.tracker = new ServiceTracker( getBundle().getBundleContext(),
                                       IJSchService.class.getName(),
                                       null );
    this.tracker.open();
    plugin = this;
  }

  /*
   * (non-Javadoc)
   * @see
   * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
   * )
   */
  @Override
  public void stop( final BundleContext context ) throws Exception {
    plugin = null;
    this.tracker.close();
    super.stop( context );
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
   * Logs an exception.
   * 
   * @param exception the exception.
   */
  public static void logException( final Exception exception ) {
    String message = exception.getLocalizedMessage();
    if( message == null ) {
      message = exception.getClass().getName();
    }
    IStatus status = new Status( IStatus.ERROR,
                                 Activator.PLUGIN_ID,
                                 IStatus.OK,
                                 message,
                                 exception );
    getDefault().getLog().log( status );
  }

  /**
   * Returns the jschService
   * 
   * @return jschService
   */
  public IJSchService getJSchService() {
    return ( IJSchService )this.tracker.getService();
  }
}
