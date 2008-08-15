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

package eu.geclipse.traceview;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewSite;

/**
 * Provides a Trace Visualization via an Extension Point
 */
public interface ITraceVisProvider {

  /**
   * Returns the Visualization for the trace
   * 
   * @param parent
   * @param style
   * @param viewSite
   * @param trace
   * @return a TraceVisualization
   */
  public TraceVisualization getTraceVis( Composite parent,
                                         int style,
                                         IViewSite viewSite,
                                         ITrace trace );

  /**
   * Returns whether this trace format is supported by this visualization
   * 
   * @param trace
   * @return supported
   */
  public boolean supports( ITrace trace );

  /**
   * Returns an image for the provider
   * 
   * @return Image
   */
  public Image getImage();
}
