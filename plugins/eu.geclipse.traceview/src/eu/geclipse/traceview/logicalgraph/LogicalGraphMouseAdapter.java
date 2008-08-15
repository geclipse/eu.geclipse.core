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

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.ILamportProcess;
import eu.geclipse.traceview.IProcess;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.internal.Activator;

/**
 * A MouseAdapter which handles the mouse events on the LogicalGraph and sets
 * the according selection in the SelectionProvider.
 */
public class LogicalGraphMouseAdapter extends MouseAdapter {

  private LogicalGraph logicalGraph;

  /**
   * Creates a new EventGraphMouseAdapter
   *
   * @param eventGraph
   */
  LogicalGraphMouseAdapter( final LogicalGraph logicalGraph ) {
    this.logicalGraph = logicalGraph;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.events.MouseEvent)
   */
  @Override
  public void mouseDown( final MouseEvent e ) {
    Object obj = getObjectForPosition( e.x, e.y );
    if( e.button == 1 || e.button == 3 ) {
      if( obj instanceof IEvent || obj instanceof IProcess
          || obj instanceof ITrace ) {
        ISelection selection = new StructuredSelection( obj );
        Activator.getDefault()
          .getWorkbench()
          .getActiveWorkbenchWindow()
          .getActivePage()
          .getActivePart()
          .getSite()
          .getSelectionProvider()
          .setSelection( selection );
        this.logicalGraph.redraw();
      }
    }
  }

  Object getObjectForPosition( final int xPos, final int yPos ) {
    Object obj = null;
    // horizontal and vertical space between events
    int hSpace = this.logicalGraph.getEventGraphPaintListener().getHSpace();
    int vSpace = this.logicalGraph.getEventGraphPaintListener().getVSpace();
    // scrollbar values
    int hSelection = this.logicalGraph.getHorizontalBar().getSelection();
    int vSelection = this.logicalGraph.getVerticalBar().getSelection();
    // first displayed clock and process
    int firstClock = hSelection / hSpace;
    int firstProc = vSelection / vSpace;
    // x and y offset
    int xOffset = hSelection % hSpace - hSpace / 2;
    int yOffset = vSelection % vSpace - vSpace / 2;
    // area get mouse events from
    int graphWidth = this.logicalGraph.getClientArea().width;
    int graphHeight = this.logicalGraph.getClientArea().height - 30;
    int x = -1;
    int y = -1;
    if( xPos > 30 && yPos > 0 && xPos < graphWidth && yPos < graphHeight ) {
      x = ( xPos
            + xOffset
            - 30
            - this.logicalGraph.getEventGraphPaintListener().getEventSize()
            / 2 + hSpace / 2 )
          / hSpace
          + firstClock;
      y = ( yPos
            + yOffset
            - this.logicalGraph.getEventGraphPaintListener().getEventSize()
            / 2 + vSpace / 2 )
          / vSpace
          + firstProc;
      if( Math.abs( xPos
                    - ( ( x - firstClock ) * hSpace + 30 - xOffset + this.logicalGraph.getEventGraphPaintListener()
                      .getEventSize() / 2 ) ) > this.logicalGraph.getEventGraphPaintListener()
        .getEventSize() / 2 )
      {
        x = -1;
      }
      if( Math.abs( yPos
                    - ( ( y - firstProc ) * vSpace - yOffset + this.logicalGraph.getEventGraphPaintListener()
                      .getEventSize() / 2 ) ) > this.logicalGraph.getEventGraphPaintListener()
        .getEventSize() / 2 )
      {
        y = -1;
      }
    }
    if( y < this.logicalGraph.getTrace().getNumberOfProcesses() ) {
      if( x != -1 && y != -1 ) {
        obj = ( ( ILamportProcess )this.logicalGraph.getTrace().getProcess( y ) ).getEventByLamportClock( x );
      }
      if( obj == null && y != -1 ) {
        obj = this.logicalGraph.getTrace().getProcess( y );
      }
    }
    if( obj == null )
      obj = this.logicalGraph.getTrace();
    return obj;
  }
}
