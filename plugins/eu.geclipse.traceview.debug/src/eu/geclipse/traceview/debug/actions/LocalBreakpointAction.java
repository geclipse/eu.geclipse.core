/*******************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Christof Klausecker GUP, JKU - initial API and implementation
 ******************************************************************************/
package eu.geclipse.traceview.debug.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.ITraceView;
import eu.geclipse.traceview.debug.Activator;
import eu.geclipse.traceview.debug.EventBreakpoint;

/**
 * Event Breakpoint Action
 */
public class LocalBreakpointAction extends AbstractEventBreakpointAction {

  public void run( final IAction action ) {
    if( this.selectedObject instanceof IEvent ) {
      IEvent event = ( IEvent )this.selectedObject;
      try {
        EventBreakpoint eventBreakpoint = createEventBreakpoint( event );
        DebugPlugin.getDefault()
          .getBreakpointManager()
          .addBreakpoint( eventBreakpoint );
      } catch( CoreException e ) {
        e.printStackTrace();
      }
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
}
