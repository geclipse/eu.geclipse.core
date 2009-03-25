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
 *    Andreas Roesch - initial implementation based on eu.geclipse.traceview.logicalgraph 
 *    Christof Klausecker - source code clean-up, adaptions
 *    
 *****************************************************************************/

package eu.geclipse.traceview.physicalgraph;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.ui.PartInitException;

import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.IPhysicalEvent;
import eu.geclipse.traceview.IPhysicalProcess;
import eu.geclipse.traceview.IProcess;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.internal.Activator;

/**
 * A MouseAdapter which handles the mouse events on the PhysicalGraph and sets
 * the according selection in the SelectionProvider.
 */
public class PhysicalGraphMouseAdapter extends MouseAdapter {

  private PhysicalGraph physicalGraph;

  /**
   * Creates a new EventGraphMouseAdapter
   * 
   * @param eventGraph
   */
  PhysicalGraphMouseAdapter( final PhysicalGraph physicalGraph ) {
    this.physicalGraph = physicalGraph;
  }

  private int getLineNumber( final MouseEvent e ) {
    int yOffset = this.physicalGraph.getEventGraphPaintListener().getYOffset();
    int eventSize = this.physicalGraph.getEventGraphPaintListener()
      .getEventSize();
    int vSpace = this.physicalGraph.getEventGraphPaintListener().getVSpace();
    int numProc = this.physicalGraph.getEventGraphPaintListener().getNumProc();
    int process = -1;
    int tmp = e.y + yOffset - eventSize / 2;
    if( tmp % vSpace <= eventSize / 2 ) {
      process = tmp / vSpace;
    }
    if( vSpace - ( tmp % vSpace ) <= eventSize / 2 ) {
      process = tmp / vSpace + 1;
    }
    if( process > numProc - 1 ) {
      process = -1;
    } else {
      process += this.physicalGraph.getEventGraphPaintListener()
        .getFromProcess();
    }
    return process;
  }

  private Object getObject( final MouseEvent e ) {
    Object object = null;
    float hzoomfactor = this.physicalGraph.getHZoomFactor();
    int hSelection = ( int )( this.physicalGraph.getHorizontalBar()
      .getSelection()
                              / hzoomfactor * 10 );
    float x = -1;
    int y = getLineNumber( e );
    IPhysicalProcess process = ( IPhysicalProcess )this.physicalGraph.getTrace()
      .getProcess( y );
    x = hSelection + ( ( ( e.x - 30 ) / hzoomfactor ) - ( 20 / hzoomfactor ) );
    int clock = Math.round( x );
    IPhysicalEvent[] events = process.getEventsByPhysicalClock( clock, clock );
    if( events.length > 0 ) {
      object = events[ 0 ];
    } else {
      object = this.physicalGraph.getTrace().getProcess( y );
    }
    return object;
  }

  @Override
  public void mouseDown( final MouseEvent e ) {
    Object obj = null;
    int graphWidth = this.physicalGraph.getClientArea().width;
    int graphHeight = this.physicalGraph.getClientArea().height - 30;
    int y = -1;
    y = getLineNumber( e );
    if( e.x > 30 && e.y > 0 && e.x < graphWidth && e.y < graphHeight ) {
      if( y != -1 ) {
        obj = getObject( e );
      } else {
        obj = this.physicalGraph.getTrace();
      }
    }
    if( e.button == 1 || e.button == 3 ) {
      if( obj instanceof IEvent
          || obj instanceof IProcess
          || obj instanceof ITrace )
      {
        ISelection selection = new StructuredSelection( obj );
        try {
          Activator.getDefault()
            .getWorkbench()
            .getActiveWorkbenchWindow()
            .getActivePage()
            .showView( "eu.geclipse.traceview.views.TraceView" )
            .getSite()
            .getSelectionProvider()
            .setSelection( selection );
          this.physicalGraph.redraw();
        } catch( PartInitException exception ) {
          Activator.logException( exception );
        }
      }
    }
  }
}
