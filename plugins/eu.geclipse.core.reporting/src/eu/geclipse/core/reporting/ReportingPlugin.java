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

package eu.geclipse.core.reporting;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import eu.geclipse.core.reporting.internal.ReportingService;

/**
 * The activator class controls the plug-in life cycle
 */
public class ReportingPlugin extends Plugin {

  // The shared instance
  private static ReportingPlugin plugin;
  
  private IReportingService reportingService;

  /**
   * The constructor
   */
  public ReportingPlugin() {
    this.reportingService = ReportingService.getService();
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context) throws Exception {
    super.start(context);
    plugin = this;
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    plugin = null;
    super.stop(context);
  }

  /**
   * Returns the shared instance.
   *
   * @return the shared instance.
   */
  public static ReportingPlugin getPlugin() {
    return plugin;
  }
  
  /**
   * Get the reporting service.
   * 
   * @return The {@link IReportingService} that is provided
   * by the reporting plug-in.
   */
  public static IReportingService getReportingService() {
    return getPlugin().reportingService;
  }

}
