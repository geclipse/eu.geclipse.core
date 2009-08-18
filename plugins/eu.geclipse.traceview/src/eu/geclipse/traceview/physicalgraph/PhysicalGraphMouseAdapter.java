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

  @Override
  public Object getObjectOnProcess( final int xPos, final int procNr ) {
    Object object = null;
    float hzoomfactor = ((PhysicalGraph)this.graph).getHZoomFactor();
    int hSelection = ( int )( this.graph.getHorizontalBar().getSelection()
                              / hzoomfactor * 10 );
    float x = -1;
    IPhysicalProcess process = ( IPhysicalProcess )this.graph.getTrace()
      .getProcess( procNr );
    x = hSelection + ( ( ( xPos - 30 ) / hzoomfactor ) - ( 20 / hzoomfactor ) );
    int clock = Math.round( x );
    IPhysicalEvent[] events = process.getEventsByPhysicalClock( clock, clock );
    if( events.length > 0 ) {
      object = events[ 0 ];
    } else {
      object = process;
    }
    return object;
  }


}
