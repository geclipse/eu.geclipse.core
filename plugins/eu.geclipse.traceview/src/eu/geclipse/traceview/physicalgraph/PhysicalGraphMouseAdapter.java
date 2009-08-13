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

import eu.geclipse.traceview.IPhysicalEvent;
import eu.geclipse.traceview.IPhysicalProcess;
import eu.geclipse.traceview.internal.AbstractGraphMouseAdapter;

/**
 * A MouseAdapter which handles the mouse events on the PhysicalGraph and sets
 * the according selection in the SelectionProvider.
 */
public class PhysicalGraphMouseAdapter extends AbstractGraphMouseAdapter {
  /**
   * Creates a new EventGraphMouseAdapter
   * 
   * @param eventGraph
   */
  PhysicalGraphMouseAdapter( final PhysicalGraph physicalGraph ) {
    super(physicalGraph);
  }

  private int getLineNumber( final int yPos ) {
    int yOffset = ((PhysicalGraphPaintListener)this.graph.getEventGraphPaintListener()).getYOffset();
    int eventSize = this.graph.getEventGraphPaintListener()
      .getEventSize();
    int vSpace = this.graph.getEventGraphPaintListener().getVSpace();
    int numProc = ((PhysicalGraphPaintListener)this.graph.getEventGraphPaintListener()).getNumProc();
    int process = -1;
    int tmp = yPos + yOffset - eventSize / 2;
    if( tmp % vSpace <= eventSize / 2 ) {
      process = tmp / vSpace;
    }
    if( vSpace - ( tmp % vSpace ) <= eventSize / 2 ) {
      process = tmp / vSpace + 1;
    }
    if( process > numProc - 1 ) {
      process = -1;
    } else {
      process += ((PhysicalGraphPaintListener)this.graph.getEventGraphPaintListener())
        .getFromProcess();
    }
    return process;
  }

  private Object getObject( final int xPos, final int yPos ) {
    Object object = null;
    float hzoomfactor = ((PhysicalGraph)this.graph).getHZoomFactor();
    int hSelection = ( int )( this.graph.getHorizontalBar()
      .getSelection()
                              / hzoomfactor * 10 );
    float x = -1;
    int y = getLineNumber( yPos );
    IPhysicalProcess process = ( IPhysicalProcess )this.graph.getTrace()
      .getProcess( y );
    x = hSelection + ( ( ( xPos - 30 ) / hzoomfactor ) - ( 20 / hzoomfactor ) );
    int clock = Math.round( x );
    IPhysicalEvent[] events = process.getEventsByPhysicalClock( clock, clock );
    if( events.length > 0 ) {
      object = events[ 0 ];
    } else {
      object = this.graph.getTrace().getProcess( y );
    }
    return object;
  }

  @Override
  public Object getObjectForPosition( int xPos, int yPos ) {
    Object obj = null;
    int graphWidth = this.graph.getClientArea().width;
    int graphHeight = this.graph.getClientArea().height - 30;
    int y = -1;
    y = getLineNumber( yPos );
    if( xPos > 30 && yPos > 0 && xPos < graphWidth && yPos < graphHeight ) {
      if( y != -1 ) {
        obj = getObject( xPos, yPos );
      } else {
        obj = this.graph.getTrace();
      }
    }
    return obj;
  }
}
