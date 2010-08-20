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
      for( IPhysicalEvent event : events ) {
        for( IEventMarker eventmarker : this.eventGraph.getEventMarkers() ) {
          int mark = eventmarker.mark( event );
          if( mark != 0 ) {
            Color newFillColor = eventmarker.getBackgroundColor( mark );
            Color newDrawColor = eventmarker.getForegroundColor( mark );
            if (newFillColor != null) fillColor = newFillColor;
            if (newDrawColor != null) drawColor = newDrawColor;
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
        if( fillColor != null ) {
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
        if( drawColor != null ) {
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
    for( int i = 0; i < this.numProc; i++ ) {
      IPhysicalEvent[] events = ( ( IPhysicalProcess )this.eventGraph.getTrace()
        .getProcess( i ) ).getEventsByPhysicalClock( this.fromTime, this.toTime );
      for( IPhysicalEvent event : events ) {
        if ( !event.getType().equals( EventType.SEND ) && !event.getType().equals( EventType.RECV ) ) {
          continue;
        }
        Color messageColor = null;
        for( IEventMarker marker : this.eventGraph.getEventMarkers() ) {
          if (marker.mark( event ) != 0) {
            Color newColor = marker.getMessageColor();
            if (newColor != null) messageColor = newColor;
          }
        }
        if (messageColor != null) {
          this.gc.setBackground( messageColor );
          this.gc.setForeground( messageColor );
          drawConnection( event );
        }
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

  private void drawGraphBackground() {
    for( int i = 0; i < this.numProc; i++ ) {
      IPhysicalProcess process = ( IPhysicalProcess )this.eventGraph.getTrace().getProcess( i );
      if (!procDrawingEnabled( process.getProcessId() )) continue;
      IPhysicalEvent[] events = process.getEventsByPhysicalClock( this.fromTime - 20,
                                                                  this.toTime );      
      for( IPhysicalEvent event : events ) {
        Color color = null;
        IEventMarker lastMarker = null;
        for( IEventMarker eventmarker : this.eventGraph.getEventMarkers() ) {
          int mark = eventmarker.mark( event );
          if (mark != IEventMarker.No_Mark) {
            Color newColor = eventmarker.getCanvasBackgroundColor();
            if (newColor != null) {
              color = newColor;
              lastMarker = eventmarker;
            }
          }
        }
        if( color != null ) {
          int x = getXPosForClock( event.getPhysicalStartClock() );
          int bgWidth = getXPosForClock( event.getPhysicalStopClock() ) - x;
          IPhysicalEvent nextEvent = ( IPhysicalEvent )event.getNextEvent();
          if ( nextEvent != null ) {
            if ( lastMarker.mark( nextEvent ) != IEventMarker.No_Mark &&
                 color.equals( lastMarker.getCanvasBackgroundColor() )) {
              bgWidth += getXPosForClock( nextEvent.getPhysicalStartClock() )
                         - getXPosForClock( event.getPhysicalStopClock() );
            }
          }
          this.gc.setBackground( color );
          int y = getYPosForProcId( event.getProcessId() ) - this.vSpace/2;
          this.gc.fillRectangle( x, y, bgWidth, this.vSpace );
        }
      }
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
    for( IEventMarker eventmarker : this.eventGraph.getEventMarkers() ) {
      eventmarker.startMarking();
    }
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
      drawVRuler(this.gc, -1);
      drawHRuler();
      // set the clipping to the graph area
      this.gc.setClipping( 31, 1, this.width - 31, this.height - 31 );
      // Draw background markers of events
      drawGraphBackground();
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
      for ( Object obj : structuredSelection.toList() ) {
        if( obj instanceof IPhysicalEvent ) {
          IPhysicalEvent event = ( IPhysicalEvent )obj;
          if (event.getProcess().getTrace() != this.trace) continue;
          if (procDrawingEnabled( event.getProcessId(), true )) {
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
        } else if( obj instanceof IProcess ) {
          IProcess process = ( IProcess )obj;
          if (process.getTrace() != this.trace) continue;
          if (procDrawingEnabled( process.getProcessId(), true )) {
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
    drawVRuler(this.gc, -1);
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
