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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.terminal.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

  /** The plug-in ID */
  public static final String PLUGIN_ID = "eu.geclipse.terminal"; //$NON-NLS-1$

  // The shared instance
  private static Activator plugin;

  /**
   * The constructor
   */
  public Activator() {
    plugin = this;
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start( final BundleContext context ) throws Exception {
    super.start(context);
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop( final BundleContext context ) throws Exception {
    plugin = null;
    super.stop(context);
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
   * Returns an image descriptor for the image file at the given
   * plug-in relative path
   *
   * @param path the path
   * @return the image descriptor
   */
  public static ImageDescriptor getImageDescriptor( final String path ) {
    return imageDescriptorFromPlugin(PLUGIN_ID, path);
  }
  
  /**
   * Logs an exception.
   * 
   * @param exception the exception.
   */
  public static void logException( final Exception exception ) {
    String message = exception.getLocalizedMessage();
    if ( message == null ) message = exception.getClass().getName();
    IStatus status = new Status( IStatus.ERROR,
                                 PLUGIN_ID,
                                 IStatus.OK,
                                 message,
                                 exception );
    getDefault().getLog().log( status );
  }
  
  /**
   * Logs a message.
   * 
   * @param severity severity constant (defined in IStatus). 
   * @param message the message to log.
   */
  public static void logMessage( final int severity, final String message ) {
    IStatus status = new Status( severity,
                                 PLUGIN_ID,
                                 IStatus.OK,
                                 message,
                                 null );
    getDefault().getLog().log( status );
  }
}
