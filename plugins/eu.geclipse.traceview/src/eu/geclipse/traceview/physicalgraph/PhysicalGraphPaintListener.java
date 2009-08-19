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
 *    Christof Klausecker - source code clean-up, adaptions, speed improvments, fixed drawing errors
 *    
 *****************************************************************************/

package eu.geclipse.traceview.physicalgraph;

import java.io.IOException;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.LineAttributes;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.IEventMarker;
import eu.geclipse.traceview.IPhysicalEvent;
import eu.geclipse.traceview.IPhysicalProcess;
import eu.geclipse.traceview.IPhysicalTrace;
import eu.geclipse.traceview.IProcess;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.internal.AbstractGraphPaintListener;
import eu.geclipse.traceview.internal.Activator;

class PhysicalGraphPaintListener extends AbstractGraphPaintListener {

  // constants
  private float hzoomfactor;
  private int vzoomfactor;
  private int maxTimeStop;
  private IPhysicalTrace trace;
  private int fromTime = 0;
  private int toTime = 0;
  private int xStep;
  private IEvent selectedEvent = null;
  private int arrowsize;
  private boolean drawMessages = true;

  PhysicalGraphPaintListener( final PhysicalGraph eventGraph ) {
    super(eventGraph);
    this.hzoomfactor = 1;
    this.vzoomfactor = 1;
  }

  protected IEvent getSelectedEvent() {
    return this.selectedEvent;
  }

  protected void setSelectedEvent( final IEvent selectedEvent ) {
    this.selectedEvent = selectedEvent;
  }

  protected float getHZoomfactor() {
    return this.hzoomfactor;
  }

  protected int getVZoomfactor() {
    return this.vzoomfactor;
  }

  protected boolean isDrawMessages() {
    return this.drawMessages;
  }

  protected void setDrawMessages( final boolean drawMessages ) {
    this.drawMessages = drawMessages;
    this.eventGraph.redraw();
  }

  @Override
  public void handleResize() {
    correctScrollbar();
  }

  /**
   * Draws all the visible events and their send & receive connections
   * 
   * @throws IOException
   */
  private void drawGraph() {
    // draw events from visible processes
    for( int i = 0; i < this.numProc; i++ ) {
      if (!procDrawingEnabled( i )) continue;
      // calc vertical position
      int y = getYPosForProcId( i ) - this.eventSize/2;
      // get all visible events for the process
      IPhysicalProcess process = ( IPhysicalProcess )( this.eventGraph.getTrace().getProcess( i ) );
      IPhysicalEvent[] events = process.getEventsByPhysicalClock( this.fromTime - 20,
                                                                  this.toTime );
      Color drawColor = null;
      Color fillColor = null;
      boolean draw = false;
      boolean fill = false;
      for( IPhysicalEvent event : events ) {
        // if( ( event.getPhysicalStopClock() - event.getPhysicalStartClock() )
        // * this.hzoomfactor > 1 )
        // {
        // Color markerDrawColor = null;
        // Color markerFillColor = null;
        if( event.getType() == EventType.SEND ) {
          // this.gc.setForeground( this.sendEventColor );
          draw = this.sendEventDraw;
          fill = this.sendEventFill;
          drawColor = this.sendEventColor;
          fillColor = this.sendEventFillColor;
        }
        if( event.getType() == EventType.RECV ) {
          // this.gc.setForeground( this.sendEventColor );
          draw = this.recvEventDraw;
          fill = this.recvEventFill;
          drawColor = this.recvEventColor;
          fillColor = this.recvEventFillColor;
        }
        if( event.getType() == EventType.TEST ) {
          // this.gc.setForeground( this.sendEventColor );
          draw = this.testEventDraw;
          fill = this.testEventFill;
          drawColor = this.testEventColor;
          fillColor = this.testEventFillColor;
        }
        if( event.getType() == EventType.OTHER ) {
          // this.gc.setForeground( this.sendEventColor );
          draw = this.otherEventDraw;
          fill = this.otherEventFill;
          drawColor = this.otherEventColor;
          fillColor = this.otherEventFillColor;
        }
        // this.gc.setLineStyle( SWT.LINE_SOLID );
        // if( event == this.getSelectedEvent() )
        // this.gc.setLineWidth( 4 );
        // else
        // this.gc.setLineWidth( 0 );
        for( IEventMarker eventmarker : this.eventGraph.getEventMarkers() ) {
          int mark = eventmarker.mark( event );
          if( mark != 0 ) {
            fillColor = eventmarker.getBackgroundColor( mark );
            drawColor = eventmarker.getForegroundColor( mark );
            if( drawColor == null ) {
              draw = false;
            }
            if( fillColor == null ) {
              fill = false;
            }
          }
        }
        if( drawColor != null ) {
          this.gc.setForeground( drawColor );
        }
        if( fillColor != null )
          this.gc.setBackground( fillColor );
        int x = getXPosForClock( event.getPhysicalStartClock() );
        int rectangleWidth = getXPosForClock( event.getPhysicalStopClock() ) - x;
        int rectangleHeight = 2 * this.vzoomfactor;
        if( fill ) {
          this.gc.fillRectangle( x, y, rectangleWidth, rectangleHeight );
        }
        if( rectangleHeight > this.fontsize + 2 ) {
          String name = event.getName();
          if( name != null ) {
            int textWidth = this.gc.textExtent( name ).x;
            int textHeight = this.gc.textExtent( name ).y;
            if( textWidth < rectangleWidth ) {
              this.gc.drawText( name,
                                x + rectangleWidth / 2 - textWidth / 2,
                                y + rectangleHeight / 2 - textHeight / 2 );
            }
          }
        }
        if( draw ) {
          this.gc.drawRectangle( x, y, rectangleWidth, rectangleHeight );
        }
      }
    }
  }

  private void drawConnections() {
    this.arrowsize = this.vzoomfactor;
    if( this.arrowsize > 6 ) {
      this.arrowsize = 6;
    }
    this.gc.setBackground( this.messageColor );
    this.gc.setForeground( this.messageColor );
    for( int i = 0; i < this.numProc; i++ ) {
      IPhysicalEvent[] events = ( ( IPhysicalProcess )this.eventGraph.getTrace()
        .getProcess( i ) ).getEventsByPhysicalClock( this.fromTime, this.toTime );
      for( IPhysicalEvent event : events ) {
        drawConnection( event );
      }
    }
  }

  private void calculateXStep() {
    int to = this.fromTime + ( int )( this.width / this.hzoomfactor );
    int mid = ( to + this.fromTime ) / 2;
    int midSize = this.gc.textExtent( Integer.toString( mid ) ).x + 20;
    this.xStep = ( int )( midSize / this.hzoomfactor );
  }

  private void drawHRuler() {
    this.gc.setForeground( this.gc.getDevice().getSystemColor( SWT.COLOR_BLACK ) );
    this.gc.setFont( this.smallFont );
    int y = this.height - 22;
    this.gc.setClipping( 31, this.height - 26, this.width - 31, 26 );
    int from = this.fromTime / this.xStep * this.xStep;
    int to = this.toTime
             / this.xStep
             * this.xStep
             + ( int )( this.width / this.hzoomfactor );
    for( int i = from; i <= to; i += this.xStep ) {
      int x = getXPosForClock( i ) - this.gc.textExtent( Integer.toString( i ) ).x / 2;
      this.gc.drawText( Integer.toString( i ), x, y );
    }
  }

  private void drawGridVLines() {
    this.gc.setForeground( this.line1 );
    int from = this.fromTime / this.xStep * this.xStep;
    int to = this.toTime
             / this.xStep
             * this.xStep
             + ( int )( this.width / this.hzoomfactor );
    for( int i = from; i <= to; i += this.xStep ) {
      int x = getXPosForClock( i );
      this.gc.drawLine( x, 1, x, this.height - 31 );
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.swt.events.PaintListener#paintControl(org.eclipse.swt.events.PaintEvent)
   */
  public void paintControl( final PaintEvent e ) {
    this.gc = e.gc;
    if( this.antialiasing ) {
      this.gc.setAntialias( SWT.ON );
    }
    this.height = this.eventGraph.getClientArea().height - 1;
    this.width = this.eventGraph.getClientArea().width - 1;
    correctScrollbar();
    // draw controls
    drawBackground();
    // draw rest only if trace is available
    if( this.eventGraph.getTrace() != null ) {
      // calculate visible events
      this.toTime = Math.min( this.maxTimeStop,
                              ( int )( this.fromTime + this.width
                                                       / this.hzoomfactor ) );
      this.toProcessLine = Math.min( this.eventGraph.getLineToProcessMapping().size(),
                                     this.fromProcessLine
                                     + ( this.height - 31 + this.eventSize )
                                     / this.vSpace
                                     + 2 );
      calculateXStep();
      drawVRuler();
      drawHRuler();
      // set the clipping to the graph area
      this.gc.setClipping( 31, 1, this.width - 31, this.height - 31 );
      drawGridHLines();
      drawGridVLines();
      drawSelection();
      drawGraph();
      if( this.drawMessages )
        drawConnections();
    }
  }

  void drawSelection() {
    ISelection selection = null;
    try {
      selection = Activator.getDefault()
        .getWorkbench()
        .getActiveWorkbenchWindow()
        .getActivePage()
        .getActivePart()
        .getSite()
        .getSelectionProvider()
        .getSelection();
    } catch( Exception e ) {
      // ignore
    }
    if( selection != null && selection instanceof StructuredSelection ) {
      StructuredSelection structuredSelection = ( StructuredSelection )selection;
      if( structuredSelection.getFirstElement() != null
          && structuredSelection.getFirstElement() instanceof IPhysicalEvent )
      {
        IPhysicalEvent event = ( IPhysicalEvent )structuredSelection.getFirstElement();
        if (procDrawingEnabled( event.getProcessId() )) {
          if( this.fromTime <= event.getPhysicalStopClock()
              || event.getPartnerPhysicalStartClock() <= this.toTime ) {
            int y = getYPosForProcId( event.getProcessId() ) - this.eventSize/2;
            this.gc.setForeground( this.selectionColor );
            this.gc.setBackground( this.selectionColor );
            int x = getXPosForClock( event.getPhysicalStartClock() );
            int rectWidth = getXPosForClock( event.getPhysicalStopClock() ) - x;
            this.gc.fillRectangle( x - 2,
                                   y - 2,
                                   rectWidth + 5,
                                   2 * this.vzoomfactor + 5 );
          }
        }
      } else if( structuredSelection.getFirstElement() instanceof IProcess ) {
        IProcess process = ( IProcess )structuredSelection.getFirstElement();
        if (procDrawingEnabled( process.getProcessId() )) {
          int x = 0;
          int y = getYPosForProcId( process.getProcessId() ) - this.eventSize/2;
          this.gc.setForeground( this.selectionColor );
          this.gc.setBackground( this.selectionColor );
          this.gc.fillRectangle( x,
                                 y + this.eventSize / 4,
                                 this.width,
                                 this.eventSize / 2 );
        }
      }
    }
  }

  private void correctScrollbar() {
    int max = ( int )( this.maxTimeStop * this.hzoomfactor - this.width + 250 ) / 10;
    if( max < 1 ) {
      this.horizontalScrollBar.setMaximum( 1 );
      this.fromTime = 0;
    } else {
      this.horizontalScrollBar.setMaximum( max );
    }
    this.verticalScrollBar.setMaximum( this.vSpace * this.numProc );
  }

  /**
   * @param selection
   */
  @Override
  public void setHorizontal( final int selection ) {
    this.fromTime = ( int )( selection / this.hzoomfactor ) * 10;
  }

  /**
   * @return clockdiff
   */
  public int getDiff() {
    return this.toTime - this.fromTime;
  }

  @Override
  public void setTrace( final ITrace trace ) {
    this.trace = ( IPhysicalTrace )trace;
    if( this.trace != null ) {
      this.maxTimeStop = this.trace.getMaximumPhysicalClock();
      this.numProc = this.trace.getNumberOfProcesses();
    }
  }

  void updateMaxTimeStop() {
    this.maxTimeStop = this.trace.getMaximumPhysicalClock();
    correctScrollbar();
  }

  protected int getNumProc() {
    return this.numProc;
  }

  protected int getMaxTimeStop() {
    return this.maxTimeStop;
  }

  protected void setZoomfactor( final int zoomfactor ) {
    this.hSpace = 6 * zoomfactor;
    this.vSpace = 6 * zoomfactor;
    this.eventSize = 2 * zoomfactor;
    // this.zoomfactor = zoomfactor;
    // adapt ScrollBar
    correctScrollbar();
    this.horizontalScrollBar.setPageIncrement( this.hSpace );
    this.verticalScrollBar.setPageIncrement( this.vSpace );
  }

  protected void setHZoomfactor( final float zoomfactor ) {
    this.hSpace = ( int )( 6 * zoomfactor );
    if( this.hSpace < 1 )
      this.hSpace = 1;
    this.hzoomfactor = zoomfactor;
  }

  protected void setVZoomfactor( final int zoomfactor ) {
    this.vSpace = 6 * zoomfactor;
    if( this.vSpace < 12 )
      this.vSpace = 12;
    this.eventSize = 2 * zoomfactor;
    this.vzoomfactor = zoomfactor;
    correctScrollbar();
    this.verticalScrollBar.setPageIncrement( this.vSpace );
  }

  protected void setNumProc( final int numProc ) {
    this.numProc = numProc;
  }

  @Override
  public void print( final GC gc2 ) {
    this.gc = gc2;
    this.gc.setLineAttributes( new LineAttributes(1) );
    calculateXStep();
    drawVRuler();
    drawHRuler();
    // set the clipping to the graph area
    this.gc.setClipping( 31, 1, this.width - 31, this.height - 31 );
    drawGridHLines();
    drawGridVLines();
    drawSelection();
    drawGraph();
    if( this.drawMessages )
      drawConnections();
  }

  @Override
  public int getArrowSize() {
    return this.arrowsize;
  }

  void drawConnection( IPhysicalEvent event ) {
    if (event.getPartnerProcessId() != -1 &&
        procDrawingEnabled( event.getProcessId() ) &&
        procDrawingEnabled( event.getPartnerProcessId() )) {
      int x1 = getXPosForClock( event.getPhysicalStopClock() );
      int y1 = getYPosForProcId( event.getProcessId() );
      int x2 = getXPosForClock( event.getPartnerPhysicalStopClock() );
      int y2 = getYPosForProcId( event.getPartnerProcessId() );
      if (y1 == y2) return;
      if( event.getType() == EventType.SEND ) {
        connection( x1, y1, x2, y2, false );
      } else {
        connection( x2, y2, x1, y1, false );
      }
    }
  }

  private int getXPosForClock( int physicalClock ) {
    return ( int )( 50 + ( physicalClock - this.fromTime ) * this.hzoomfactor );
  }
}
