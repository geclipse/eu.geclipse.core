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
import java.util.ArrayList;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.LineAttributes;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.IEventMarker;
import eu.geclipse.traceview.IPhysicalEvent;
import eu.geclipse.traceview.IPhysicalProcess;
import eu.geclipse.traceview.IPhysicalTrace;
import eu.geclipse.traceview.IProcess;
import eu.geclipse.traceview.internal.Activator;
import eu.geclipse.traceview.internal.LineType;
import eu.geclipse.traceview.preferences.PreferenceConstants;

class PhysicalGraphPaintListener implements PaintListener {

  // constants
  private final static int leftRulerWidth = 25;
  private final static int bottomRulerWidth = 25;
  private final static int leftMargin = leftRulerWidth + 5;
  private final static int bottomMargin = bottomRulerWidth + 5;
  private final static int arc = 6;
  protected IPropertyChangeListener listener;
  private float hzoomfactor;
  private int vzoomfactor;
  private ScrollBar horizontalScrollBar;
  private ScrollBar verticalScrollBar;
  private int numProc;
  private ArrayList<ArrayList<Integer>> procs;
  private int maxTimeStop;
  private IPhysicalTrace trace;
  private PhysicalGraph eventGraph = null;
  private Font smallFont;
  private int hSpace = 6; // 32
  private int vSpace = 12; // 32
  private int eventSize = 2; // 8
  private int fontsize = 8; // 6
  private int fromTime = 0;
  private int fromProcess = 0;
  private int toTime = 0;
  private int toProcess = 0;
  private int width;
  private int height;
  private int yOffset;
  private int xStep;
  // presentation
  // event draw
  private boolean sendEventDraw;
  private boolean recvEventDraw;
  private boolean testEventDraw;
  private boolean otherEventDraw;
  // event fill
  private boolean sendEventFill;
  private boolean recvEventFill;
  private boolean testEventFill;
  private boolean otherEventFill;
  // event color
  private Color sendEventColor;
  private Color recvEventColor;
  private Color testEventColor;
  private Color otherEventColor;
  // event fill color
  private Color sendEventFillColor;
  private Color recvEventFillColor;
  private Color testEventFillColor;
  private Color otherEventFillColor;
  // message
  private boolean drawMessages = true;
  private Color messageColor;
  private Color selectionColor;
  // lines
  private Color line1;
  private Color line5;
  private Color line10;
  private boolean antialiasing;
  private GC gc;
  // private ArrayList<ArrayList<IPhysicalEvent>> displayedEvents;
  private IEvent selectedEvent = null;
  private int arrowsize;

  PhysicalGraphPaintListener( final PhysicalGraph eventGraph ) {
    this.listener = new IPropertyChangeListener() {

      public void propertyChange( final PropertyChangeEvent event ) {
        handleProperyChanged( event );
      }
    };
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    store.addPropertyChangeListener( this.listener );
    // this.zoomfactor = 1;
    this.hzoomfactor = 1;
    this.vzoomfactor = 1;
    this.eventGraph = eventGraph;
    updatePropertiesFromPreferences();
    setHorizontal( 0 );
    setVertical( 0 );
    this.line1 = new Color( Display.getDefault(), new RGB( 196, 196, 196 ) );
    this.line5 = new Color( Display.getDefault(), new RGB( 128, 128, 128 ) );
    this.line10 = new Color( Display.getDefault(), new RGB( 64, 64, 64 ) );
  }

  protected IEvent getSelectedEvent() {
    return this.selectedEvent;
  }

  protected void setSelectedEvent( final IEvent selectedEvent ) {
    this.selectedEvent = selectedEvent;
  }

  protected int getYOffset() {
    return this.yOffset;
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

  protected int getFromProcess() {
    return this.fromProcess;
  }

  protected void handleResize() {
    correctScrollbar();
  }

  protected void handleProperyChanged( final PropertyChangeEvent event ) {
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    // settings
    if( event.getProperty().equals( PreferenceConstants.P_ANTI_ALIASING ) ) {
      if( event.getNewValue() instanceof String ) {
        this.antialiasing = Boolean.getBoolean( ( String )event.getNewValue() );
      } else {
        this.antialiasing = ( ( Boolean )event.getNewValue() ).booleanValue();
      }
      this.eventGraph.redraw();
    }
    // draw
    else if( event.getProperty().equals( PreferenceConstants.P_SEND_EVENT
                                         + PreferenceConstants.P_DRAW ) )
    {
      this.sendEventDraw = store.getBoolean( event.getProperty() );
      this.eventGraph.redraw();
    } else if( event.getProperty().equals( PreferenceConstants.P_RECV_EVENT
                                           + PreferenceConstants.P_DRAW ) )
    {
      this.recvEventDraw = store.getBoolean( event.getProperty() );
      this.eventGraph.redraw();
    } else if( event.getProperty().equals( PreferenceConstants.P_TEST_EVENT
                                           + PreferenceConstants.P_DRAW ) )
    {
      this.testEventDraw = store.getBoolean( event.getProperty() );
      this.eventGraph.redraw();
    } else if( event.getProperty().equals( PreferenceConstants.P_OTHER_EVENT
                                           + PreferenceConstants.P_DRAW ) )
    {
      this.otherEventDraw = store.getBoolean( event.getProperty() );
      this.eventGraph.redraw();
    }
    // Fill
    else if( event.getProperty().equals( PreferenceConstants.P_SEND_EVENT
                                         + PreferenceConstants.P_FILL ) )
    {
      this.sendEventFill = store.getBoolean( event.getProperty() );
      this.eventGraph.redraw();
    } else if( event.getProperty().equals( PreferenceConstants.P_RECV_EVENT
                                           + PreferenceConstants.P_FILL ) )
    {
      this.recvEventFill = store.getBoolean( event.getProperty() );
      this.eventGraph.redraw();
    } else if( event.getProperty().equals( PreferenceConstants.P_TEST_EVENT
                                           + PreferenceConstants.P_FILL ) )
    {
      this.testEventFill = store.getBoolean( event.getProperty() );
      this.eventGraph.redraw();
    } else if( event.getProperty().equals( PreferenceConstants.P_OTHER_EVENT
                                           + PreferenceConstants.P_FILL ) )
    {
      this.otherEventFill = store.getBoolean( event.getProperty() );
      this.eventGraph.redraw();
    }
    // Message color
    else if( event.getProperty().equals( PreferenceConstants.P_MESSAGE
                                         + PreferenceConstants.P_COLOR ) )
    {
      this.messageColor.dispose();
      this.messageColor = new Color( Display.getDefault(),
                                     PreferenceConverter.getColor( store,
                                                                   event.getProperty() ) );
      this.eventGraph.redraw();
    }
    // Event Color
    else if( event.getProperty().equals( PreferenceConstants.P_SEND_EVENT
                                         + PreferenceConstants.P_COLOR ) )
    {
      this.sendEventColor.dispose();
      this.sendEventColor = new Color( Display.getDefault(),
                                       PreferenceConverter.getColor( store,
                                                                     event.getProperty() ) );
      this.eventGraph.redraw();
    } else if( event.getProperty().equals( PreferenceConstants.P_RECV_EVENT
                                           + PreferenceConstants.P_COLOR ) )
    {
      this.recvEventColor.dispose();
      this.recvEventColor = new Color( Display.getDefault(),
                                       PreferenceConverter.getColor( store,
                                                                     event.getProperty() ) );
      this.eventGraph.redraw();
    } else if( event.getProperty().equals( PreferenceConstants.P_TEST_EVENT
                                           + PreferenceConstants.P_COLOR ) )
    {
      this.testEventColor.dispose();
      this.testEventColor = new Color( Display.getDefault(),
                                       PreferenceConverter.getColor( store,
                                                                     event.getProperty() ) );
      this.eventGraph.redraw();
    } else if( event.getProperty().equals( PreferenceConstants.P_OTHER_EVENT
                                           + PreferenceConstants.P_COLOR ) )
    {
      this.otherEventColor.dispose();
      this.otherEventColor = new Color( Display.getDefault(),
                                        PreferenceConverter.getColor( store,
                                                                      event.getProperty() ) );
      this.eventGraph.redraw();
    }
    // Event Fill Color
    else if( event.getProperty().equals( PreferenceConstants.P_SEND_EVENT
                                         + PreferenceConstants.P_FILL_COLOR ) )
    {
      this.sendEventFillColor.dispose();
      this.sendEventFillColor = new Color( Display.getDefault(),
                                           PreferenceConverter.getColor( store,
                                                                         event.getProperty() ) );
      this.eventGraph.redraw();
    } else if( event.getProperty().equals( PreferenceConstants.P_RECV_EVENT
                                           + PreferenceConstants.P_FILL_COLOR ) )
    {
      this.recvEventFillColor.dispose();
      this.recvEventFillColor = new Color( Display.getDefault(),
                                           PreferenceConverter.getColor( store,
                                                                         event.getProperty() ) );
      this.eventGraph.redraw();
    } else if( event.getProperty().equals( PreferenceConstants.P_TEST_EVENT
                                           + PreferenceConstants.P_FILL_COLOR ) )
    {
      this.testEventFillColor.dispose();
      this.testEventFillColor = new Color( Display.getDefault(),
                                           PreferenceConverter.getColor( store,
                                                                         event.getProperty() ) );
      this.eventGraph.redraw();
    } else if( event.getProperty().equals( PreferenceConstants.P_OTHER_EVENT
                                           + PreferenceConstants.P_FILL_COLOR ) )
    {
      this.otherEventFillColor.dispose();
      this.otherEventFillColor = new Color( Display.getDefault(),
                                            PreferenceConverter.getColor( store,
                                                                          event.getProperty() ) );
      this.eventGraph.redraw();
    }
  }

  void updatePropertiesFromPreferences() {
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    // settings
    this.antialiasing = store.getBoolean( PreferenceConstants.P_ANTI_ALIASING );
    // event draw
    this.sendEventDraw = store.getBoolean( PreferenceConstants.P_SEND_EVENT
                                           + PreferenceConstants.P_DRAW );
    this.recvEventDraw = store.getBoolean( PreferenceConstants.P_RECV_EVENT
                                           + PreferenceConstants.P_DRAW );
    this.testEventDraw = store.getBoolean( PreferenceConstants.P_TEST_EVENT
                                           + PreferenceConstants.P_DRAW );
    this.otherEventDraw = store.getBoolean( PreferenceConstants.P_RECV_EVENT
                                            + PreferenceConstants.P_DRAW );
    // event fill
    this.sendEventFill = store.getBoolean( PreferenceConstants.P_SEND_EVENT
                                           + PreferenceConstants.P_FILL );
    this.recvEventFill = store.getBoolean( PreferenceConstants.P_RECV_EVENT
                                           + PreferenceConstants.P_FILL );
    this.testEventFill = store.getBoolean( PreferenceConstants.P_TEST_EVENT
                                           + PreferenceConstants.P_FILL );
    this.otherEventFill = store.getBoolean( PreferenceConstants.P_RECV_EVENT
                                            + PreferenceConstants.P_FILL );
    // message color
    this.messageColor = new Color( this.eventGraph.getDisplay(),
                                   PreferenceConverter.getColor( store,
                                                                 PreferenceConstants.P_MESSAGE
                                                                     + PreferenceConstants.P_COLOR ) );
    // event color
    this.sendEventColor = new Color( this.eventGraph.getDisplay(),
                                     PreferenceConverter.getColor( store,
                                                                   PreferenceConstants.P_SEND_EVENT
                                                                       + PreferenceConstants.P_COLOR ) );
    this.recvEventColor = new Color( this.eventGraph.getDisplay(),
                                     PreferenceConverter.getColor( store,
                                                                   PreferenceConstants.P_RECV_EVENT
                                                                       + PreferenceConstants.P_COLOR ) );
    this.testEventColor = new Color( this.eventGraph.getDisplay(),
                                     PreferenceConverter.getColor( store,
                                                                   PreferenceConstants.P_TEST_EVENT
                                                                       + PreferenceConstants.P_COLOR ) );
    this.otherEventColor = new Color( this.eventGraph.getDisplay(),
                                      PreferenceConverter.getColor( store,
                                                                    PreferenceConstants.P_OTHER_EVENT
                                                                        + PreferenceConstants.P_COLOR ) );
    // event fill color
    this.sendEventFillColor = new Color( this.eventGraph.getDisplay(),
                                         PreferenceConverter.getColor( store,
                                                                       PreferenceConstants.P_SEND_EVENT
                                                                           + PreferenceConstants.P_FILL_COLOR ) );
    this.recvEventFillColor = new Color( this.eventGraph.getDisplay(),
                                         PreferenceConverter.getColor( store,
                                                                       PreferenceConstants.P_RECV_EVENT
                                                                           + PreferenceConstants.P_FILL_COLOR ) );
    this.testEventFillColor = new Color( this.eventGraph.getDisplay(),
                                         PreferenceConverter.getColor( store,
                                                                       PreferenceConstants.P_TEST_EVENT
                                                                           + PreferenceConstants.P_FILL_COLOR ) );
    this.otherEventFillColor = new Color( this.eventGraph.getDisplay(),
                                          PreferenceConverter.getColor( store,
                                                                        PreferenceConstants.P_OTHER_EVENT
                                                                            + PreferenceConstants.P_FILL_COLOR ) );
    this.selectionColor = new Color( this.eventGraph.getDisplay(),
                                     PreferenceConverter.getColor( store,
                                                                   PreferenceConstants.P_SELECTION_COLOR ) );
  }

  void setFont( final Font font ) {
    FontData fontData = font.getFontData()[ 0 ];
    fontData.setHeight( this.fontsize );
    fontData.setStyle( SWT.NORMAL );
    this.smallFont = new Font( font.getDevice(), fontData );
  }

  // draw the controls
  private void drawBackground() {
    // color
    this.gc.setForeground( this.gc.getDevice()
      .getSystemColor( SWT.COLOR_WIDGET_NORMAL_SHADOW ) );
    this.gc.setBackground( this.gc.getDevice().getSystemColor( SWT.COLOR_WHITE ) );
    // main
    this.gc.fillRectangle( leftMargin,
                           0,
                           this.width - leftMargin,
                           this.height - bottomMargin );
    this.gc.drawRectangle( leftMargin,
                           0,
                           this.width - leftMargin,
                           this.height - bottomMargin );
    // bottom
    this.gc.fillRoundRectangle( leftMargin,
                                this.height - bottomRulerWidth,
                                this.width - leftMargin,
                                bottomRulerWidth,
                                arc,
                                arc );
    this.gc.drawRoundRectangle( leftMargin,
                                this.height - bottomRulerWidth,
                                this.width - leftMargin,
                                bottomRulerWidth,
                                arc,
                                arc );
    // left
    this.gc.fillRoundRectangle( 0, 0, leftRulerWidth, this.height
                                                      - bottomMargin, arc, arc );
    this.gc.drawRoundRectangle( 0, 0, leftRulerWidth, this.height
                                                      - bottomMargin, arc, arc );
  }

  /**
   * Draws all the visible events and their send & receive connections
   * 
   * @throws IOException
   */
  private void drawGraph() {
    // draw events from visible processes
    for( int i = this.fromProcess; i < this.toProcess; i++ ) {
      // calc vertical position
      int y = ( 0 - this.yOffset ) + ( i - this.fromProcess ) * this.vSpace;
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
        int x = ( int )( 50 + ( event.getPhysicalStartClock() - this.fromTime )
                              * this.hzoomfactor );
        int rectangleWidth = ( int )( ( event.getPhysicalStopClock() - event.getPhysicalStartClock() ) * this.hzoomfactor );
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

  private void connection( final int x1,
                           final int y1,
                           final int x2,
                           final int y2 )
  {
    long xv = x2 - x1;
    long yv = y2 - y1;
    float c = ( float )Math.sqrt( xv * xv + yv * yv );
    int ex = Math.round( ( xv / c * this.arrowsize ) );
    int ey = Math.round( ( yv / c * this.arrowsize ) );
    this.gc.drawLine( x2, y2, x1, y1 );
    int[] arrowhead = {
      x2, y2, x2 - ex - ey, y2 - ey + ex, x2 - ex + ey, y2 - ey - ex
    };
    this.gc.fillPolygon( arrowhead );
  }

  private void drawConnections() {
    this.arrowsize = this.vzoomfactor;
    if( this.arrowsize > 6 ) {
      this.arrowsize = 6;
    }
    this.gc.setBackground( this.messageColor );
    this.gc.setForeground( this.messageColor );
    for( int i = 0, y = -this.fromProcess * this.vSpace - this.yOffset; i < this.procs.size(); i++, y += this.vSpace )
    {
      IPhysicalEvent[] events = ( ( IPhysicalProcess )this.eventGraph.getTrace()
        .getProcess( i ) ).getEventsByPhysicalClock( this.fromTime, this.toTime );
      for( IPhysicalEvent event : events ) {
        if( event.getType() == EventType.SEND ) {
          IPhysicalEvent partner = ( IPhysicalEvent )event.getPartnerEvent();
          if( partner != null ) {
            int fromX = ( int )( 50 + ( event.getPhysicalStopClock() - this.fromTime )
                                      * this.hzoomfactor );
            int toX = ( int )( 50 + ( partner.getPhysicalStartClock() - this.fromTime )
                                    * this.hzoomfactor );
            connection( fromX,
                        y + this.eventSize / 2,
                        toX,
                        y
                            + ( partner.getProcessId() - i )
                            * this.vSpace
                            + this.eventSize
                            / 2 );
          }
        }
        if( event.getType() == EventType.RECV ) {
          IPhysicalEvent partner = ( IPhysicalEvent )event.getPartnerEvent();
          if( partner != null ) {
            int fromX = ( int )( 50 + ( partner.getPhysicalStopClock() - this.fromTime )
                                      * this.hzoomfactor );
            int toX = ( int )( 50 + ( event.getPhysicalStartClock() - this.fromTime )
                                    * this.hzoomfactor );
            connection( fromX, y
                               + ( partner.getProcessId() - i )
                               * this.vSpace
                               + this.eventSize
                               / 2, toX, y + this.eventSize / 2 );
          }
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

  private void drawRulers() {
    this.gc.setForeground( this.gc.getDevice().getSystemColor( SWT.COLOR_BLACK ) );
    this.gc.setFont( this.smallFont );
    // vertical
    this.gc.setClipping( 1, 1, 24, this.height - 31 );
    for( int i = this.fromProcess, y = 0 - this.yOffset; i < this.toProcess; i++, y += this.vSpace )
    {
      StringBuilder sb = new StringBuilder();
      ArrayList<Integer> processes = this.procs.get( i );
      for( Integer ii : processes ) {
        sb.append( ii );
        sb.append( '\n' );
      }
      String text = sb.substring( 0, sb.length() - 1 );
      this.gc.drawText( text, 3, y );
      this.gc.drawLine( 20, y + this.eventSize / 2, 22, y + this.eventSize / 2 );
    }
    // horizontal
    int y = this.height - 22;
    this.gc.setClipping( 31, this.height - 26, this.width - 31, 26 );
    int from = this.fromTime / this.xStep * this.xStep;
    int to = this.toTime
             / this.xStep
             * this.xStep
             + ( int )( this.width / this.hzoomfactor );
    for( int i = from; i <= to; i += this.xStep ) {
      int x = ( int )( 50 + ( i - this.fromTime ) * this.hzoomfactor - this.gc.textExtent( Integer.toString( i ) ).x / 2 );
      this.gc.drawText( Integer.toString( i ), x, y );
    }
  }

  private void drawGrid() {
    this.gc.setForeground( this.line1 );
    LineType hLines = this.eventGraph.getHLines();
    // horizontal
    if( hLines != LineType.Lines_None ) {
      for( int i = this.fromProcess, y = 0 - this.yOffset + this.eventSize / 2; i < this.toProcess; i++, y += this.vSpace )
      {
        if( i % 10 == 0 ) {
          this.gc.setForeground( this.line10 );
          this.gc.drawLine( 31, y, this.width - 1, y );
          this.gc.setForeground( this.line1 );
        } else if( ( hLines == LineType.Lines_5 || hLines == LineType.Lines_1 )
                   && i % 5 == 0 )
        {
          this.gc.setForeground( this.line5 );
          this.gc.drawLine( 31, y, this.width - 1, y );
          this.gc.setForeground( this.line5 );
        } else if( hLines == LineType.Lines_1 ) {
          this.gc.drawLine( 31, y, this.width - 1, y );
        }
      }
    }
    // vertical
    int from = this.fromTime / this.xStep * this.xStep;
    int to = this.toTime
             / this.xStep
             * this.xStep
             + ( int )( this.width / this.hzoomfactor );
    for( int i = from; i <= to; i += this.xStep ) {
      int x = ( int )( 50 + ( i - this.fromTime ) * this.hzoomfactor );
      this.gc.drawLine( x, 1, x, this.height - 31 );
      // this.gc.drawText( Integer.toString(i), x, y );
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
      this.toProcess = Math.min( this.procs.size(),
                                 this.fromProcess
                                     + ( this.height - 31 + this.eventSize )
                                     / this.vSpace
                                     + 2 );
      calculateXStep();
      drawRulers();
      // set the clipping to the graph area
      this.gc.setClipping( 31, 1, this.width - 31, this.height - 31 );
      drawGrid();
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
        if( this.fromProcess <= event.getProcessId()
            && event.getProcessId() < this.toProcess )
        {
          if( this.fromTime <= event.getPhysicalStopClock()
              || event.getPartnerPhysicalStartClock() <= this.toTime )
          {
            int y = ( ( event.getProcessId() - this.fromProcess ) * this.vSpace )
                    - this.yOffset;
            this.gc.setForeground( this.selectionColor );
            this.gc.setBackground( this.selectionColor );
            this.gc.fillRectangle( ( int )( 50 + ( event.getPhysicalStartClock() - this.fromTime )
                                                 * this.hzoomfactor ) - 2,
                                   y - 2,
                                   ( int )( ( event.getPhysicalStopClock() - event.getPhysicalStartClock() ) * this.hzoomfactor ) + 5,
                                   2 * this.vzoomfactor + 5 );
          }
        }
      } else if( structuredSelection.getFirstElement() instanceof IProcess ) {
        // TODO lookup position in case the processes got reordered
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
  public void setVertical( final int selection ) {
    this.fromProcess = selection / this.vSpace;
    this.yOffset = selection % this.vSpace - this.vSpace / 2;
  }

  /**
   * @param selection
   */
  public void setHorizontal( final int selection ) {
    this.fromTime = ( int )( selection / this.hzoomfactor ) * 10;
  }

  /**
   * @return clockdiff
   */
  public int getDiff() {
    return this.toTime - this.fromTime;
  }

  protected void setTrace( final IPhysicalTrace trace ) {
    this.trace = trace;
    if( this.trace != null ) {
      this.maxTimeStop = this.trace.getMaximumPhysicalClock();
      this.numProc = this.trace.getNumberOfProcesses();
      resetOrdering();
    }
  }

  protected void resetOrdering() {
    this.numProc = this.trace.getNumberOfProcesses();
    this.procs = new ArrayList<ArrayList<Integer>>();
    for( int i = 0; i < this.numProc; i++ ) {
      ArrayList<Integer> tmp = new ArrayList<Integer>();
      tmp.add( Integer.valueOf( i ) );
      this.procs.add( tmp );
    }
  }

  protected void handleHorizontalScrollBar() {
    int selection = this.horizontalScrollBar.getSelection();
    setHorizontal( selection );
    this.eventGraph.redraw();
  }

  protected void handleVerticalScrollBar() {
    int selection = this.verticalScrollBar.getSelection();
    setVertical( selection );
    this.eventGraph.redraw();
  }

  protected int getNumProc() {
    return this.numProc;
  }

  protected int getMaxTimeStop() {
    return this.maxTimeStop;
  }

  protected void setHorizontalScrollBar( final ScrollBar bar ) {
    this.horizontalScrollBar = bar;
    this.horizontalScrollBar.setPageIncrement( this.hSpace );
    this.horizontalScrollBar.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event e ) {
        handleHorizontalScrollBar();
      }
    } );
  }

  protected void setVerticalScrollBar( final ScrollBar bar ) {
    this.verticalScrollBar = bar;
    this.verticalScrollBar.setPageIncrement( this.vSpace );
    this.verticalScrollBar.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event e ) {
        handleVerticalScrollBar();
      }
    } );
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

  protected int getEventSize() {
    return this.eventSize;
  }

  protected int getHSpace() {
    return this.hSpace;
  }

  protected int getVSpace() {
    return this.vSpace;
  }

  protected ArrayList<ArrayList<Integer>> getProcs() {
    return this.procs;
  }

  protected void setNumProc( final int numProc ) {
    this.numProc = numProc;
  }

  public void print( final GC gc2 ) {
    this.gc = gc2;
    gc.setLineAttributes( new LineAttributes(1) );
    calculateXStep();
    drawRulers();
    // set the clipping to the graph area
    this.gc.setClipping( 31, 1, this.width - 31, this.height - 31 );
    drawGrid();
    drawSelection();
    drawGraph();
    if( this.drawMessages )
      drawConnections();
  }
}
