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

import eu.geclipse.traceview.ILamportProcess;
import eu.geclipse.traceview.internal.AbstractGraphMouseAdapter;

/**
 * A MouseAdapter which handles the mouse events on the LogicalGraph and sets
 * the according selection in the SelectionProvider.
 */
public class LogicalGraphMouseAdapter extends AbstractGraphMouseAdapter {
  /**
   * Creates a new EventGraphMouseAdapter
   *
   * @param eventGraph
   */
  LogicalGraphMouseAdapter( final LogicalGraph logicalGraph ) {
    super(logicalGraph);
  }

  @Override
  public Object getObjectOnProcess( final int xPos, final int procNr ) {
    Object obj = null;
    int hSpace = this.graph.getEventGraphPaintListener().getHSpace();
    int hSelection = this.graph.getHorizontalBar().getSelection();
    int firstClock = hSelection / hSpace;
    int eventSize = this.graph.getEventGraphPaintListener().getEventSize();
    int xOffset = hSelection % hSpace - hSpace / 2;
    ILamportProcess process = ( ILamportProcess )this.graph.getTrace().getProcess( procNr );
    int x = -1;
    x = ( xPos + xOffset - 30 - eventSize / 2 + hSpace / 2 ) / hSpace + firstClock;
    if ( Math.abs( xPos - ( ( x - firstClock ) * hSpace + 30 - xOffset + eventSize / 2 ) ) > eventSize / 2 ) {
      x = -1;
    }
    if( x != -1 ) {
      obj = process.getEventByLamportClock( x );
    } else {
      obj = process;
    }
    return obj;
  }
}
