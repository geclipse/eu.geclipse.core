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

package eu.geclipse.traceview.statistics;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewSite;

import eu.geclipse.traceview.IPhysicalTrace;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.ITraceVisProvider;
import eu.geclipse.traceview.TraceVisualization;

/**
 * Provides a Statistics Visualization
 */
public class StatisticsVisualisationProvider implements ITraceVisProvider {

  public TraceVisualization getTraceVis( final Composite parent,
                                         final int style,
                                         final IViewSite viewSite,
                                         final ITrace trace )
  {
    return new StatisticsVisualisation( parent,
                                        style,
                                        viewSite,
                                        ( IPhysicalTrace )trace );
  }

  public boolean supports( final ITrace trace ) {
    return trace instanceof IPhysicalTrace;
  }
  
  public Image getImage() {   
    // TODO dispose somewhere!!!
    return Activator.getImageDescriptor( "icons/obj16/statistics.gif" ).createImage(); //$NON-NLS-1$
  }
}
