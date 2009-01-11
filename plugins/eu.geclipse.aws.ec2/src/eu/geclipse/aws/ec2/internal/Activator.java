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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.ec2.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

  /** The plug-in ID. */
  public static final String PLUGIN_ID = "eu.geclipse.aws.ec2"; //$NON-NLS-1$

  /** The shared instance. */
  private static Activator plugin;

  /**
   * The constructor
   */
  public Activator() {
    // nothing to do here
  }

  /**
   * Start the bundle. Store a reference to self.
   */
  @Override
  public void start( final BundleContext context ) throws Exception {
    super.start( context );
    plugin = this;
  }

  /**
   * Stop the bundle. remove reference to self.
   */
  @Override
  public void stop( final BundleContext context ) throws Exception {
    plugin = null;
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
   * Log the exception via an {@link IStatus}.
   * 
   * @param e the exception to log
   */
  public static void log( final Exception e ) {
    log( e.getLocalizedMessage(), e );
  }

  /**
   * Create a log entry with the given description and exception.
   * 
   * @param description a more descriptive text of the exception
   * @param e the exception to log
   */
  public static void log( String description, final Exception e ) {
    if( description == null ) {
      description = e.getClass().getName();
    }
    IStatus status = new Status( IStatus.ERROR,
                                 PLUGIN_ID,
                                 IStatus.OK,
                                 description,
                                 e );
    getDefault().getLog().log( status );
  }
}
