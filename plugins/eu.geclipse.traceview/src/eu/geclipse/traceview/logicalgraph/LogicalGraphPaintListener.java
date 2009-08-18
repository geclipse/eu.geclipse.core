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

  public void handleResize() {
    setScrollBarSizes();
  }

  protected void setScrollBarSizes() {
    int clockDiff = Math.min( ( int )( ( this.maxLamport + 1.5 - this.fromClock ) * this.hSpace ),
                              this.width - 31 + this.eventSize );
    int processDiff = Math.min( ( int )( ( this.numProc - this.fromProcess + 0.5 ) * this.hSpace ),
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
    // set the message color
    this.gc.setBackground( this.messageColor );
    this.gc.setForeground( this.messageColor );
    // draw only last couple of events
    int from = Math.max( this.fromClock - 100, 0 );
    // left
    // for all processes
    for( int i = 0, y = 0 - this.yOffset - this.fromProcess * this.vSpace; i < this.numProc; i++, y += this.vSpace )
    {
      ILamportEvent[] events = ( ( ILamportProcess )this.eventGraph.getTrace()
        .getProcess( i ) ).getEventsByLamportClock( from, this.fromClock - 1 );
      for( int j = 0; j < events.length; j++ ) {
        int x = 30
                - this.xOffset
                + ( events[ j ].getLamportClock() - this.fromClock )
                * this.hSpace;
        ILamportEvent event = events[ j ];
        // if( event != null ) {
        // only draw send messages and only the ones which could visible
        if( event.getType() == EventType.SEND
            && event.getPartnerLamportClock() >= this.fromClock )
        {
          // draw connections
          connection( x + this.eventSize / 2,
                      y + this.eventSize / 2,
                      ( event.getPartnerLamportClock() - this.fromClock )
                          * this.hSpace
                          + 30
                          - this.xOffset
                          + this.eventSize
                          / 2,
                      ( event.getPartnerProcessId() - this.fromProcess )
                          * this.vSpace
                          - this.yOffset
                          + this.eventSize
                          / 2, true );
        }
        // }
      }
    }
    // top
    // for all processes till the first visible one
    for( int i = 0, y = 0 - this.yOffset - this.fromProcess * this.vSpace; i < this.fromProcess; i++, y += this.vSpace )
    {
      ILamportEvent[] events = ( ( ILamportProcess )this.eventGraph.getTrace()
        .getProcess( i ) ).getEventsByLamportClock( this.fromClock,
                                                    this.toClock );
      for( int j = 0; j < events.length; j++ ) {
        ILamportEvent event = events[ j ];
        int x = 30
                - this.xOffset
                + ( event.getLamportClock() - this.fromClock )
                * this.hSpace;
        // only draw send messages and only the ones which could visible TODO
        // problems with broadcast
        if( event.getType() == EventType.SEND
            && event.getPartnerProcessId() >= this.fromProcess )
        {
          connection( x + this.eventSize / 2,
                      y + this.eventSize / 2,
                      ( event.getPartnerLamportClock() - this.fromClock )
                          * this.hSpace
                          + 30
                          - this.xOffset
                          + this.eventSize
                          / 2,
                      ( event.getPartnerProcessId() - this.fromProcess )
                          * this.vSpace
                          - this.yOffset
                          + this.eventSize
                          / 2, true );
        }
      }
    }
    // bottom
    // for all processes from the last visible one
    for( int i = this.toProcess, y = 0
                                     - this.yOffset
                                     - ( this.fromProcess - this.toProcess )
                                     * this.vSpace; i < this.numProc; i++, y += this.vSpace )
    {
      ILamportEvent[] events = ( ( ILamportProcess )this.eventGraph.getTrace()
        .getProcess( i ) ).getEventsByLamportClock( this.fromClock,
                                                    this.toClock );
      for( int j = 0; j < events.length; j++ ) {
        ILamportEvent event = events[ j ];
        int x = 30
                - this.xOffset
                + ( event.getLamportClock() - this.fromClock )
                * this.hSpace;
        // only messages which could be visible
        if( event.getType() == EventType.SEND
            && event.getPartnerProcessId() < this.toProcess )
        {
          connection( x + this.eventSize / 2,
                      y + this.eventSize / 2,
                      ( event.getPartnerLamportClock() - this.fromClock )
                          * this.hSpace
                          + 30
                          - this.xOffset
                          + this.eventSize
                          / 2,
                      ( event.getPartnerProcessId() - this.fromProcess )
                          * this.vSpace
                          - this.yOffset
                          + this.eventSize
                          / 2, true );
        }
      }
    }
    // TODO right - because of broadcasts
  }

  private void drawGraphBackground() {
    for( int i = this.fromProcess, y = 0 - this.yOffset - this.vSpace/2 + this.eventSize/2; i < this.toProcess; i++, y += this.vSpace ) {
      ILamportEvent[] events = ( ( ILamportProcess )this.eventGraph.getTrace()
        .getProcess( i ) ).getEventsByLamportClock( this.fromClock, this.toClock );
      for( ILamportEvent event : events ) {
        int x = 30 - this.hSpace/2 + this.eventSize/2
                - this.xOffset
                + ( event.getLamportClock() - this.fromClock )
                * this.hSpace;
        for( IEventMarker eventmarker : this.eventGraph.getEventMarkers() ) {
          int mark = eventmarker.mark( event );
          if (mark != IEventMarker.No_Mark) {
            Color color = eventmarker.getCanvasBackgroundColor();
            if( color != null ) {
              int bgWidth = this.hSpace;
              ILamportEvent nextEvent = ( ILamportEvent )event.getNextEvent();
              if ( nextEvent != null ) {
                if ( eventmarker.mark( nextEvent ) != IEventMarker.No_Mark &&
                     color.equals( eventmarker.getCanvasBackgroundColor() )) {
                  bgWidth *= (nextEvent.getLamportClock() - event.getLamportClock()); 
                }
              }
              this.gc.setBackground( color );
              this.gc.fillRectangle( x, y, bgWidth, this.vSpace );
            }
          }
        }
      }
    }
  }

  /**
   * Draws all the visible events and their send & receive connections
   */
  private void drawGraph() {
    Color color = null;
    for( int i = this.fromProcess, y = 0 - this.yOffset; i < this.toProcess; i++, y += this.vSpace )
    {
      // get events
      try {
        ILamportEvent[] events = ( ( ILamportProcess )this.eventGraph.getTrace()
          .getProcess( i ) ).getEventsByLamportClock( this.fromClock,
                                                      this.toClock );
        for( ILamportEvent event : events ) {
          int x = 30
                  - this.xOffset
                  + ( event.getLamportClock() - this.fromClock )
                  * this.hSpace;
          // draw event different?
          boolean standardEvents = true;
          for( IEventMarker eventmarker : this.eventGraph.getEventMarkers() ) {
            int mark = eventmarker.mark( event );
            if( ( mark & 63 ) != 0 ) {
              standardEvents = false;
              // Diamond
              if( ( mark & IEventMarker.Diamond_Event ) == IEventMarker.Diamond_Event )
              {
                this.gc.setLineStyle( eventmarker.getLineStyle( IEventMarker.Diamond_Event ) );
                this.gc.setLineWidth( eventmarker.getLineWidth( IEventMarker.Diamond_Event ) );
                int[] diamond = {
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
                color = eventmarker.getBackgroundColor( IEventMarker.Diamond_Event );
                if( color != null ) {
                  this.gc.setBackground( color );
                  this.gc.fillPolygon( diamond );
                }
                color = eventmarker.getForegroundColor( IEventMarker.Diamond_Event );
                if( color != null ) {
                  this.gc.setForeground( color );
                  this.gc.drawPolygon( diamond );
                }
              }
              // Triangle
              if( ( mark & IEventMarker.Triangle_Event ) == IEventMarker.Triangle_Event )
              {
                this.gc.setLineStyle( eventmarker.getLineStyle( IEventMarker.Triangle_Event ) );
                this.gc.setLineWidth( eventmarker.getLineWidth( IEventMarker.Triangle_Event ) );
                int[] triangle = {
                  x + this.eventSize / 2,
                  y,
                  x,
                  y + this.eventSize,
                  x + this.eventSize,
                  y + this.eventSize,
                  x + this.eventSize / 2,
                  y
                };
                color = eventmarker.getBackgroundColor( IEventMarker.Triangle_Event );
                if( color != null ) {
                  this.gc.setBackground( color );
                  this.gc.fillPolygon( triangle );
                }
                color = eventmarker.getForegroundColor( IEventMarker.Triangle_Event );
                if( color != null ) {
                  this.gc.setForeground( color );
                  this.gc.drawPolygon( triangle );
                }
              }
              // Ellipse
              if( ( mark & IEventMarker.Ellipse_Event ) == IEventMarker.Ellipse_Event )
              {
                this.gc.setLineStyle( eventmarker.getLineStyle( IEventMarker.Ellipse_Event ) );
                this.gc.setLineWidth( eventmarker.getLineWidth( IEventMarker.Ellipse_Event ) );
                color = eventmarker.getBackgroundColor( IEventMarker.Ellipse_Event );
                if( color != null ) {
                  this.gc.setBackground( color );
                  this.gc.fillOval( x, y, this.eventSize, this.eventSize );
                }
                color = eventmarker.getForegroundColor( IEventMarker.Ellipse_Event );
                if( color != null ) {
                  this.gc.setForeground( color );
                  this.gc.drawOval( x, y, this.eventSize, this.eventSize );
                }
              }
              // Rectangle
              if( ( mark & IEventMarker.Rectangle_Event ) == IEventMarker.Rectangle_Event )
              {
                this.gc.setLineStyle( eventmarker.getLineStyle( IEventMarker.Rectangle_Event ) );
                this.gc.setLineWidth( eventmarker.getLineWidth( IEventMarker.Rectangle_Event ) );
                color = eventmarker.getBackgroundColor( IEventMarker.Rectangle_Event );
                if( color != null ) {
                  this.gc.setBackground( color );
                  this.gc.fillRectangle( x, y, this.eventSize, this.eventSize );
                }
                color = eventmarker.getForegroundColor( IEventMarker.Rectangle_Event );
                if( color != null ) {
                  this.gc.setForeground( color );
                  this.gc.drawRectangle( x, y, this.eventSize, this.eventSize );
                }
              }
              // Cross
              if( ( mark & IEventMarker.Cross_Event ) == IEventMarker.Cross_Event )
              {
                this.gc.setLineStyle( eventmarker.getLineStyle( IEventMarker.Cross_Event ) );
                this.gc.setLineWidth( eventmarker.getLineWidth( IEventMarker.Cross_Event ) );
                color = eventmarker.getForegroundColor( IEventMarker.Cross_Event );
                if( color != null ) {
                  this.gc.setForeground( color );
                  this.gc.setBackground( color );
                  this.gc.drawLine( x, y, x + this.eventSize, y
                                                              + this.eventSize );
                  this.gc.drawLine( x,
                                    y + this.eventSize,
                                    x + this.eventSize,
                                    y );
                }
              }
            }
          }
          // draw standard types
          if( standardEvents ) {
            this.gc.setLineStyle( SWT.LINE_SOLID );
            this.gc.setLineWidth( 0 );
            // draw standard send
            if( event.getType() == EventType.SEND ) {
              if( this.sendEventFill ) {
                this.gc.setBackground( this.sendEventFillColor );
                this.gc.fillOval( x, y, this.eventSize, this.eventSize );
              }
              if( this.sendEventDraw ) {
                this.gc.setForeground( this.sendEventColor );
                this.gc.drawOval( x, y, this.eventSize, this.eventSize );
              }
            }
            // draw standard receive
            else if( event.getType() == EventType.RECV ) {
              if( this.recvEventFill ) {
                this.gc.setBackground( this.recvEventFillColor );
                this.gc.fillOval( x, y, this.eventSize, this.eventSize );
              }
              if( this.recvEventDraw ) {
                this.gc.setForeground( this.recvEventColor );
                this.gc.drawOval( x, y, this.eventSize, this.eventSize );
              }
            }
            // draw standard test
            else if( event.getType() == EventType.TEST ) {
              if( this.testEventFill ) {
                this.gc.setBackground( this.testEventFillColor );
                this.gc.fillRectangle( x, y, this.eventSize, this.eventSize );
              }
              if( this.testEventDraw ) {
                this.gc.setForeground( this.testEventColor );
                this.gc.drawRectangle( x, y, this.eventSize, this.eventSize );
              }
            }
            // draw standard other
            else if( event.getType() == EventType.OTHER ) {
              int[] diamond = {
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
              if( this.otherEventFill ) {
                this.gc.setBackground( this.otherEventFillColor );
                this.gc.fillPolygon( diamond );
              }
              if( this.otherEventDraw ) {
                this.gc.setForeground( this.otherEventColor );
                this.gc.drawPolygon( diamond );
              }
            }
          }
          if( event.getType() != EventType.OTHER
              && event.getType() != EventType.TEST )
          {
            this.gc.setLineWidth( 0 );
            this.gc.setLineStyle( SWT.LINE_SOLID );
            // set connections color
            this.gc.setBackground( this.messageColor );
            this.gc.setForeground( this.messageColor );
            for( IEventMarker eventmarker : this.eventGraph.getEventMarkers() )
            {
              if( ( eventmarker.mark( event ) & IEventMarker.Connection ) == IEventMarker.Connection )
              {
                this.gc.setBackground( eventmarker.getBackgroundColor( IEventMarker.Connection ) );
                this.gc.setForeground( eventmarker.getForegroundColor( IEventMarker.Connection ) );
              }
            }
            if( event.getPartnerProcessId() != -1 ) {
              // draw send messages
              if( event.getType() == EventType.SEND ) {
                connection( x + this.eventSize / 2,
                            y + this.eventSize / 2,
                            ( event.getPartnerLamportClock() - this.fromClock )
                                * this.hSpace
                                + 30
                                - this.xOffset
                                + this.eventSize
                                / 2,
                            ( event.getPartnerProcessId() - this.fromProcess )
                                * this.vSpace
                                - this.yOffset
                                + this.eventSize
                                / 2, true );
                // draw receive messages
              } else { // needed for broadcast ... better idea ??? perhaps add
                // more information in reader
                connection( ( event.getPartnerLamportClock() - this.fromClock )
                                * this.hSpace
                                + 30
                                - this.xOffset
                                + this.eventSize
                                / 2,
                            ( event.getPartnerProcessId() - this.fromProcess )
                                * this.vSpace
                                - this.yOffset
                                + this.eventSize
                                / 2,
                            x + this.eventSize / 2,
                            y + this.eventSize / 2, true );
              }
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

  private void drawHRuler() {
    this.gc.setForeground( this.gc.getDevice().getSystemColor( SWT.COLOR_BLACK ) );
    this.gc.setFont( this.smallFont );
    int y = this.height - 22;
    this.gc.setClipping( 31, this.height - 26, this.width - 31, 26 );
    for( int i = this.fromClock, x = 30 - this.xOffset + this.eventSize / 2; i <= this.toClock; i++, x += this.hSpace )
    {
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
      for( int i = this.fromClock, x = 30 - this.xOffset + this.eventSize / 2; i <= this.toClock; i++, x += this.hSpace )
      {
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
      this.toProcess = Math.min( this.numProc,
                                 this.fromProcess
                                     + ( this.height - 31 + this.eventSize )
                                     / this.vSpace
                                     + 2 );
      // draw the rulers to the control
      drawVRuler();
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
          && structuredSelection.getFirstElement() instanceof ILamportEvent )
      {
        ILamportEvent event = ( ILamportEvent )structuredSelection.getFirstElement();
        if( this.fromProcess <= event.getProcessId()
            && event.getProcessId() < this.toProcess )
        {
          if( this.fromClock <= event.getLamportClock()
              && event.getLamportClock() <= this.toClock )
          {
            int x = ( event.getLamportClock() - this.fromClock )
                    * this.hSpace
                    + 30
                    - this.xOffset;
            int y = ( ( event.getProcessId() - this.fromProcess ) * this.vSpace )
                    - this.yOffset;
            this.gc.setForeground( this.selectionColor );
            this.gc.setBackground( this.selectionColor );
            this.gc.fillOval( x - this.eventSize / 4,
                              y - this.eventSize / 4,
                              this.eventSize + this.eventSize / 2,
                              this.eventSize + this.eventSize / 2 );
          }
        }
      } else if( structuredSelection.getFirstElement() instanceof IProcess ) {
        IProcess process = ( IProcess )structuredSelection.getFirstElement();
        if( this.fromProcess <= process.getProcessId()
            && process.getProcessId() < this.toProcess )
        {
          int x = 0;
          int y = ( ( process.getProcessId() - this.fromProcess ) * this.vSpace )
                  - this.yOffset;
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

  /**
   * @param selection
   */
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

  public void print( final GC gc2 ) {
    this.gc = gc2;
    gc.setLineAttributes( new LineAttributes(1) );
    drawVRuler();
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
}
