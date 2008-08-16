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

package eu.geclipse.traceview.debug.listeners;

import org.eclipse.cdt.debug.core.model.ICDebugTarget;
import org.eclipse.cdt.debug.core.model.ICLineBreakpoint;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.traceview.ITraceView;
import eu.geclipse.traceview.debug.Activator;
import eu.geclipse.traceview.debug.EventBreakpoint;

/**
 * Removes wrong targets from Breakpoint
 */
public class BreakpointListener implements IBreakpointListener {

  private void redraw( final IBreakpoint breakpoint ) {
    if( breakpoint instanceof ICLineBreakpoint ) {
      Display.getDefault().asyncExec( new Runnable() {

        public void run() {
          try {
            ITraceView traceView = ( ITraceView )PlatformUI.getWorkbench()
              .getActiveWorkbenchWindow()
              .getActivePage()
              .showView( "eu.geclipse.traceview.views.TraceView" ); //$NON-NLS-1$
            traceView.redraw();
          } catch( PartInitException partInitException ) {
            Activator.logException( partInitException );
          }
        }
      } );
    }
  }

  public void breakpointAdded( final IBreakpoint breakpoint ) {
    redraw( breakpoint );
  }

  public void breakpointChanged( final IBreakpoint breakpoint,
                                 final IMarkerDelta delta )
  {
    if( breakpoint instanceof EventBreakpoint ) {
      EventBreakpoint eventBreakPoint = ( EventBreakpoint )breakpoint;
      try {
        for( ICDebugTarget target : eventBreakPoint.getTargetFilters() ) {
          try {
            while( !target.isSuspended() ) {
              // wait here;
              wait( 100 );
            }
          } catch( InterruptedException interruptedException ) {
            Activator.logException( interruptedException );
          }
          // TODO add my own ProcessTarget with getProcess method
          int a = Integer.parseInt( target.toString()
            .substring( target.toString().lastIndexOf( "s " ) + 2, //$NON-NLS-1$
                        target.toString().indexOf( "(" ) - 1 ) ); //$NON-NLS-1$
          if( eventBreakPoint.getProcess() != a ) {
            eventBreakPoint.removeTargetFilter( target );
          }
        }
      } catch( CoreException exception ) {
        Activator.logException( exception );
      }
    }
    redraw( breakpoint );
  }

  public void breakpointRemoved( final IBreakpoint breakpoint,
                                 final IMarkerDelta delta )
  {
    redraw( breakpoint );
  }
}
