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

package eu.geclipse.traceview.internal;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.ITraceView;
import eu.geclipse.traceview.IVectorEvent;

/**
 * Action to activate the cause effect marker
 */
public class CauseEffectAction extends Action implements IActionDelegate {

  private Object selectedObj;

  public void run( final IAction action ) {
    boolean equal = true;
    IVectorEvent event = ( IVectorEvent )this.selectedObj;
    ITrace trace = event.getProcess().getTrace();
    IVectorEvent causeEvent = ( IVectorEvent )trace.getUserData( CauseEffectMarker.MARKER_ID );
    for( int i = 0; i < event.getVectorClock().length; i++ ) {
      if( causeEvent == null
          || event.getVectorClock()[ i ] != causeEvent.getVectorClock()[ i ] )
      {
        equal = false;
        continue;
      }
    }
    IVectorEvent newCauseEvent;
    if( !equal )
      newCauseEvent = ( IVectorEvent )this.selectedObj;
    else
      newCauseEvent = null;
    trace.setUserData( CauseEffectMarker.MARKER_ID, newCauseEvent );
    Display.getDefault().asyncExec( new Runnable() {

      public void run() {
        try {
          ITraceView traceView = ( ITraceView )PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow()
            .getActivePage()
            .showView( "eu.geclipse.traceview.views.TraceView" ); //$NON-NLS-1$
          traceView.redraw();
        } catch( PartInitException exception ) {
          Activator.logException( exception );
        }
      }
    } );
  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    if( selection instanceof StructuredSelection ) {
      StructuredSelection structuredSelection = ( StructuredSelection )selection;
      this.selectedObj = structuredSelection.getFirstElement();
    }
  }
}
