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

package eu.geclipse.traceview.logicalgraph;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewSite;

import eu.geclipse.traceview.ILamportTrace;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.ITraceVisProvider;
import eu.geclipse.traceview.TraceVisualization;
import eu.geclipse.traceview.internal.Activator;

/**
 * Provides a LogicalGraph
 */
public class LogicalGraphProvider implements ITraceVisProvider {

  public TraceVisualization getTraceVis( final Composite parent,
                                         final int style,
                                         final IViewSite viewSite,
                                         final ITrace trace )
  {
    return new LogicalGraph( parent, style, viewSite, ( ILamportTrace )trace );
  }

  public boolean supports( final ITrace trace ) {
    return trace instanceof ILamportTrace;
  }

  public Image getImage() {
    // TODO dispose somewhere!!!
    return Activator.getImageDescriptor( "icons/logical.gif" ).createImage(); //$NON-NLS-1$
  }
}
