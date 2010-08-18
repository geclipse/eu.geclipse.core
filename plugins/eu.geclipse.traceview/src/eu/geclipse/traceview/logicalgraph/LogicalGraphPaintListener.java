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
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.LineAttributes;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.IEventMarker;
import eu.geclipse.traceview.ILamportEvent;
import eu.geclipse.traceview.ILamportProcess;
import eu.geclipse.traceview.ILamportTrace;
import eu.geclipse.traceview.IProcess;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.internal.AbstractGraphPaintListener;
import eu.geclipse.traceview.internal.Activator;
import eu.geclipse.traceview.internal.LineType;

class LogicalGraphPaintListener extends AbstractGraphPaintListener {

  // size
  private int zoomfactor;
  private int xOffset;
  // visible
  private int fromClock = 0;
  private int toClock = 0;
  private int maxLamport;
  private ILamportTrace trace;
  private boolean scrollBarsInitialized = false;

  LogicalGraphPaintListener( final LogicalGraph eventGraph ) {
    super(eventGraph);
    this.zoomfactor = 1;
  }

  protected int getZoomfactor() {
    return this.zoomfactor;
  }

  @Override
  public void handleResize() {
    setScrollBarSizes();
  }

  protected void setScrollBarSizes() {
    int clockDiff = Math.min( ( int )( ( this.maxLamport + 1.5 - this.fromClock ) * this.hSpace ),
                              this.width - 31 + this.eventSize );
    int processDiff = Math.min( ( int )( ( this.numProc - this.fromProcessLine + 0.5 ) * this.hSpace ),
                                this.height - 31 + this.eventSize );
    if( this.horizontalScrollBar != null ) {
      int selection = this.horizontalScrollBar.getSelection();
      this.horizontalScrollBar.setValues( selection,
                                          0,
                                          ( int )( this.hSpace * ( this.maxLamport + 1.5 ) ),
                                          clockDiff,
                                          1,
                                          this.hSpace );
    }
    if( this.verticalScrollBar != null ) {
      int selection = this.verticalScrollBar.getSelection();
      this.verticalScrollBar.setValues( selection,
                                        0,
                                        ( int )( this.vSpace * ( this.numProc + 0.5 ) ),
                                        processDiff,
                                        1,
                                        this.vSpace );
    }
  }

  /**
   * Draws the messages of the currently invisible events (left, right, top,
   * bottom)
   */
  private void drawLeftRightTopBottom() {
    // draw only last couple of events
    int from = Math.max( this.fromClock - 100, 0 );
    // left
    // for all processes
    for( int i = 0; i < this.numProc; i++ )
    {
      ILamportEvent[] events = ( ( ILamportProcess )this.eventGraph.getTrace()
        .getProcess( i ) ).getEventsByLamportClock( from, this.fromClock - 1 );
      for( ILamportEvent event : events ) {
        // only draw send messages and only the ones which could visible
        if( event.getType() == EventType.SEND
            && event.getPartnerLamportClock() >= this.fromClock ) {
          Color messageColor = null;
          for( IEventMarker eventmarker : this.eventGraph.getEventMarkers() ) {
            if( eventmarker.mark( event ) != 0 ) {
              Color newColor = eventmarker.getMessageColor();
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
    // top
    // for all processes till the first visible one
/*    for( int i = 0; i < this.fromProcessLine; i++ )
    {
      ILamportEvent[] events = ( ( ILamportProcess )this.eventGraph.getTrace()
        .getProcess( i ) ).getEventsByLamportClock( this.fromClock, this.toClock );
      for( ILamportEvent event : events ) {
        // only draw send messages and only the ones which could visible
        // TODO problems with broadcast
        if( event.getType() == EventType.SEND
            && event.getPartnerProcessId() >= this.fromProcessLine ) {
          drawConnection( event );
        }
      }
    }
    // bottom
    // for all processes from the last visible one
    for( int i = this.toProcessLine; i < this.numProc; i++ )
    {
      ILamportEvent[] events = ( ( ILamportProcess )this.eventGraph.getTrace()
        .getProcess( i ) ).getEventsByLamportClock( this.fromClock, this.toClock );
      for( ILamportEvent event : events ) {
        // only messages which could be visible
        if( event.getType() == EventType.SEND
            && event.getPartnerProcessId() < this.toProcessLine ) {
          drawConnection( event );
        }
      }
    }*/
    // TODO right - because of broadcasts
  }

  private void drawGraphBackground() {
    for( int i = 0; i < this.numProc; i++ ) {
      ILamportProcess process = ( ILamportProcess )this.eventGraph.getTrace().getProcess( i );
      if (!procDrawingEnabled( process.getProcessId() )) continue;
      ILamportEvent[] events = process.getEventsByLamportClock( this.fromClock, this.toClock );
      for( ILamportEvent event : events ) {
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
          int bgWidth = this.hSpace;
          ILamportEvent nextEvent = ( ILamportEvent )event.getNextEvent();
          if ( nextEvent != null ) {
            if ( lastMarker != null && lastMarker.mark( nextEvent ) != IEventMarker.No_Mark
                && color.equals( lastMarker.getCanvasBackgroundColor() )) {
              bgWidth *= (nextEvent.getLamportClock() - event.getLamportClock()); 
            }
          }
          this.gc.setBackground( color );
          int x = getXPosForClock( event.getLamportClock() ) - this.hSpace/2;
          int y = getYPosForProcId( event.getProcessId() ) - this.vSpace/2;
          this.gc.fillRectangle( x, y, bgWidth, this.vSpace );
        }
      }
    }
  }

  /**
   * Draws all the visible events and their send & receive connections
   */
  private void drawGraph() {
    for( int i = 0; i < this.numProc; i++ ) {
      // get events
      try {
        ILamportProcess process =  ( ILamportProcess )this.eventGraph.getTrace().getProcess( i );
        if (!procDrawingEnabled( process.getProcessId() )) continue;
        ILamportEvent[] events = process.getEventsByLamportClock( this.fromClock, this.toClock );
        for( ILamportEvent event : events ) {
          int x = getXPosForClock( event.getLamportClock() ) - this.eventSize/2;
          int y = getYPosForProcId( event.getProcessId() ) - this.eventSize/2;
          // draw event
          drawEvent( event, x, y );
          if( event.getType() == EventType.SEND
              || event.getType() == EventType.RECV ) {
            this.gc.setLineWidth( 0 );
            this.gc.setLineStyle( SWT.LINE_SOLID );
            // set connections color
            Color messageColor = null;
            for( IEventMarker eventmarker : this.eventGraph.getEventMarkers() ) {
              if( eventmarker.mark( event ) != 0 ) {
                Color newColor = eventmarker.getMessageColor();
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
      } catch( Exception exception ) {
        Activator.logException( exception );
      }
      // loop through
    }
    this.gc.setLineStyle( SWT.LINE_SOLID );
    this.gc.setLineWidth( 0 );
  }

  void drawEvent( final ILamportEvent event, final int x, final int y ) {
    final int[] markTypes = { IEventMarker.Diamond_Event, IEventMarker.Triangle_Event,
                              IEventMarker.Ellipse_Event, IEventMarker.Rectangle_Event,
                              IEventMarker.Cross_Event };
    for( IEventMarker eventmarker : this.eventGraph.getEventMarkers() ) {
      int mark = eventmarker.mark( event );
      if( ( mark & 63 ) != 0 ) {
        int markType = 0;
        for (int i = 0; i < markTypes.length; i++) {
          markType = markTypes[i];
          if (( mark & markType ) != 0) break;
        }
        int[] poly = null;
        this.gc.setLineStyle( eventmarker.getLineStyle( markType ) );
        this.gc.setLineWidth( eventmarker.getLineWidth( markType ) );
        if ( markType == IEventMarker.Diamond_Event ) {
          poly = new int[] {
            x + this.eventSize / 2,
            y,
            x,
            y + this.eventSize / 2,
            x + this.eventSize / 2,
            y + this.eventSize,
            x + this.eventSize,
            y + this.eventSize / 2,
            x + this.eventSize / 2,
            y
          };
        } else if ( markType == IEventMarker.Triangle_Event ) {
          poly = new int[] {
            x + this.eventSize / 2,
            y,
            x,
            y + this.eventSize,
            x + this.eventSize,
            y + this.eventSize,
            x + this.eventSize / 2,
            y
          };
        }
        Color color = eventmarker.getBackgroundColor( markType );
        if( color != null ) {
          this.gc.setBackground( color );
          if ( poly != null ) this.gc.fillPolygon( poly );
          else if ( markType == IEventMarker.Ellipse_Event) this.gc.fillOval( x, y, this.eventSize, this.eventSize );
          else if ( markType == IEventMarker.Rectangle_Event) this.gc.fillRectangle( x, y, this.eventSize, this.eventSize );
        }
        color = eventmarker.getForegroundColor( markType );
        if( color != null ) {
          this.gc.setForeground( color );
          if ( poly != null ) this.gc.drawPolygon( poly );
          else if ( markType == IEventMarker.Ellipse_Event) this.gc.drawOval( x, y, this.eventSize, this.eventSize );
          else if ( markType == IEventMarker.Rectangle_Event) 
            this.gc.drawRectangle( x, y, this.eventSize, this.eventSize );
          else if ( markType == IEventMarker.Cross_Event) {
            this.gc.drawLine( x, y, x + this.eventSize, y + this.eventSize );
            this.gc.drawLine( x, y + this.eventSize, x + this.eventSize, y );
          }
        }
      }
    }
  }

  private void drawHRuler() {
    this.gc.setForeground( this.gc.getDevice().getSystemColor( SWT.COLOR_BLACK ) );
    this.gc.setFont( this.smallFont );
    int y = this.height - 22;
    this.gc.setClipping( 31, this.height - 26, this.width - 31, 26 );
    for( int i = this.fromClock; i <= this.toClock; i++ ) {
      int x = getXPosForClock( i );
      if( i % 10 == 0 ) {
        this.gc.drawLine( x, y, x, y + 7 );
      } else if( i % 5 == 0 ) {
        this.gc.drawLine( x, y, x, y + 5 );
      } else {
        this.gc.drawLine( x, y, x, y + 3 );
      }
      if( this.zoomfactor > 6 ) {
        this.gc.drawText( Integer.toString( i ),
                          x - this.gc.textExtent( Integer.toString( i ) ).x / 2,
                          y + 9 );
      } else if( this.zoomfactor > 3 && i % 2 == 0 ) {
        this.gc.drawText( Integer.toString( i ),
                          x - this.gc.textExtent( Integer.toString( i ) ).x / 2,
                          y + 9 );
      } else if( i % 5 == 0 && this.zoomfactor < 4 && this.zoomfactor > 1 ) {
        this.gc.drawText( Integer.toString( i ),
                          x - this.gc.textExtent( Integer.toString( i ) ).x / 2,
                          y + 9 );
      } else if( i % 10 == 0 && this.zoomfactor == 1 ) {
        this.gc.drawText( Integer.toString( i ),
                          x - this.gc.textExtent( Integer.toString( i ) ).x / 2,
                          y + 9 );
      }
    }
  }

  private void drawGridVLines() {
    this.gc.setForeground( this.line1 );
    LineType vLines = this.eventGraph.getVLines();
    if( vLines != LineType.Lines_None ) {
      for( int i = this.fromClock; i <= this.toClock; i++ ) {
        int x = getXPosForClock( i );
        if( i % 10 == 0 ) {
          this.gc.setForeground( this.line10 );
          this.gc.drawLine( x, 1, x, this.height - 31 );
          this.gc.setForeground( this.line1 );
        } else if( ( vLines == LineType.Lines_5 || vLines == LineType.Lines_1 )
                   && i % 5 == 0 )
        {
          this.gc.setForeground( this.line5 );
          this.gc.drawLine( x, 1, x, this.height - 31 );
          this.gc.setForeground( this.line1 );
        } else if( vLines == LineType.Lines_1 ) {
          this.gc.drawLine( x, 1, x, this.height - 31 );
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
    // draw controls
    drawBackground();
    // draw rest only if trace is available
    if( this.eventGraph.getTrace() != null ) {
      // calculate visible events
      this.toClock = Math.min( this.maxLamport,
                               this.fromClock
                                   + ( this.width - 31 + this.eventSize )
                                   / this.hSpace
                                   + 2 );
      this.toProcessLine = Math.min( this.eventGraph.getLineToProcessMapping().size(),
                                     this.fromProcessLine
                                     + ( this.height - 31 + this.eventSize )
                                     / this.vSpace
                                     + 2 );
      // draw the rulers to the control
      drawVRuler(this.gc, -1);
      drawHRuler();
      // set the clipping to the graph area
      this.gc.setClipping( 31, 1, this.width - 31, this.height - 31 );
      // this.gc.setClipping(0, 0, this.width, this.height);
      // Draw background markers of events
      drawGraphBackground();
      // draw the grid
      drawGridHLines();
      drawGridVLines();
      // draw selection
      drawSelection();
      // draw the graph
      drawGraph();
      // draw the additional messages
      drawLeftRightTopBottom();
      if( !this.scrollBarsInitialized ) {
        setScrollBarSizes();
        this.scrollBarsInitialized = true;
      }
    }
  }

  void drawSelection() {
    ISelection selection = null;
    try {
      ISelectionProvider provider = Activator.getDefault()
      .getWorkbench()
      .getActiveWorkbenchWindow()
      .getActivePage()
      .getActivePart()
      .getSite()
      .getSelectionProvider();
      if(provider != null){
      selection = provider.getSelection();
      }
    } catch( Exception e ) {
      // ignore
    }
    if( selection != null && selection instanceof StructuredSelection ) {
      StructuredSelection structuredSelection = ( StructuredSelection )selection;
      for (Object obj : structuredSelection.toList()) {
        if( obj instanceof ILamportEvent ) {
          ILamportEvent event = ( ILamportEvent )obj;
          if (event.getProcess().getTrace() != this.trace) continue;
          if( this.fromClock <= event.getLamportClock()
              && event.getLamportClock() <= this.toClock ) {
            if ( procDrawingEnabled( event.getProcessId(), true ) ) {
              int x = getXPosForClock( event.getLamportClock() ) - this.eventSize/2;
              int y = getYPosForProcId( event.getProcessId() ) - this.eventSize/2;
              this.gc.setForeground( this.selectionColor );
              this.gc.setBackground( this.selectionColor );
              this.gc.fillOval( x - this.eventSize / 4,
                                y - this.eventSize / 4,
                                this.eventSize + this.eventSize / 2,
                                this.eventSize + this.eventSize / 2 );
            }
          }
        } else if( obj instanceof IProcess ) {
          IProcess process = ( IProcess )obj;
          if ( process.getTrace() != this.trace ) continue;
          if ( procDrawingEnabled( process.getProcessId(), true ) ) {
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

  /**
   * @param selection
   */
  @Override
  public void setHorizontal( final int selection ) {
    this.fromClock = selection / this.hSpace;
    this.xOffset = selection % this.hSpace - this.hSpace / 2;
  }

  /**
   * @return clockdiff
   */
  public int getDiff() {
    return this.toClock - this.fromClock;
  }

  @Override
  public void setTrace( final ITrace trace ) {
    this.trace = ( ILamportTrace )trace;
    if( this.trace != null ) {
      this.maxLamport = this.trace.getMaximumLamportClock();
      this.numProc = this.trace.getNumberOfProcesses();
      this.setHorizontal( 0 );
      this.setVertical( 0 );
      this.scrollBarsInitialized = false;
    }
  }

  protected void setZoomfactor( final int zoomfactor ) {
    int hsel = this.horizontalScrollBar.getSelection() / this.zoomfactor;
    int vsel = this.verticalScrollBar.getSelection() / this.zoomfactor;
    int hValue = this.horizontalScrollBar.getSelection() / this.hSpace;
    int vValue = this.verticalScrollBar.getSelection() / this.vSpace;
    this.hSpace = 6 * zoomfactor;
    this.vSpace = 6 * zoomfactor;
    this.eventSize = 2 * zoomfactor;
    this.zoomfactor = zoomfactor;
    hsel = hsel * this.zoomfactor;
    vsel = vsel * this.zoomfactor;
    this.horizontalScrollBar.setSelection( hValue * this.hSpace );
    this.verticalScrollBar.setSelection( vValue * this.vSpace );
    setScrollBarSizes();
    setHorizontal( hsel );
    setVertical( vsel );
  }

  @Override
  public void print( final GC gc2 ) {
    this.gc = gc2;
    this.gc.setLineAttributes( new LineAttributes(1) );
    drawVRuler(this.gc, -1);
    drawHRuler();
    this.gc.setClipping( 31, 1, this.width - 31, this.height - 31 );
    drawGridHLines();
    drawGridVLines();
    drawGraph();
  }

  @Override
  public int getArrowSize() {
    return this.eventSize / 2;
  }
  
  private int getXPosForClock( final int lamportClock ) {
    return ( lamportClock - this.fromClock ) * this.hSpace
           + 30 - this.xOffset + this.eventSize / 2;
  }

  void drawConnection( final ILamportEvent event ) {
    if (event.getPartnerProcessId() != -1
        && procDrawingEnabled( event.getProcessId() )
        && procDrawingEnabled( event.getPartnerProcessId() )) {
      int x1 = getXPosForClock( event.getLamportClock() );
      int y1 = getYPosForProcId( event.getProcessId() );
      int x2 = getXPosForClock( event.getPartnerLamportClock() );
      int y2 = getYPosForProcId( event.getPartnerProcessId() );
      if (y1 == y2) return;
      if( event.getType() == EventType.SEND ) {
        connection( x1, y1, x2, y2, true );
      } else {
        connection( x2, y2, x1, y1, true );
      }
    }
  }
}
