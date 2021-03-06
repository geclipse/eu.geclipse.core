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

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Interface to the Trace View that allows other plugins to access it. Use
 * following code to get the trace view instance: <code>
 * ITraceView traceView = ( ITraceView ) PlatformUI.getWorkbench()
 *           .getActiveWorkbenchWindow()
 *           .getActivePage()
 *           .showView( "eu.geclipse.traceview.views.TraceView" );
 * </code>
 */
public interface ITraceView {

  /**
   * Adds a trace to the trace view (in a new tab).
   * 
   * @param trace trace to add.
   */
  void addTrace( ITrace trace );

  /**
   * Tries to load the specified trace file using the registered
   * trace readers.
   *
   * @param tracePath path of the trace file to open.
   * @param monitor   progress monitor used to display load progress
   * @throws IOException in case of error during loading the trace
   */
  ITrace openTrace( String tracePath, IProgressMonitor monitor ) throws IOException;

  /**
   * Redraws the contents of the trace view. Useful for marker that change.
   */
  void redraw();

  TraceVisualization getVisualisationForTrace( ITrace trace );
}
