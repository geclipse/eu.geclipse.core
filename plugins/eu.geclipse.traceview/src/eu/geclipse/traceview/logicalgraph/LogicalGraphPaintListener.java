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
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.IEventMarker;
import eu.geclipse.traceview.ILamportEvent;
import eu.geclipse.traceview.ILamportProcess;
import eu.geclipse.traceview.ILamportTrace;
import eu.geclipse.traceview.IProcess;
import eu.geclipse.traceview.internal.Activator;
import eu.geclipse.traceview.internal.LineType;
import eu.geclipse.traceview.preferences.PreferenceConstants;

class LogicalGraphPaintListener implements PaintListener {

  // constants
  private final static int leftRulerWidth = 25;
  private final static int bottomRulerWidth = 25;
  private final static int leftMargin = leftRulerWidth + 5;
  private final static int bottomMargin = bottomRulerWidth + 5;
  private final static int arc = 6;
  protected IPropertyChangeListener listener;
  // size
  private int zoomfactor;
  private int hSpace = 6;
  private int vSpace = 6;
  private int eventSize = 2;
  private int fontsize = 8;
  // size
  private int width;
  private int height;
  private int xOffset;
  private int yOffset;
  // scrollbar
  private ScrollBar horizontalScrollBar;
  private ScrollBar verticalScrollBar;
  // visible
  private int fromClock = 0;
  private int fromProcess = 0;
  private int toClock = 0;
  private int toProcess = 0;
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
  private Color messageColor;
  private Color selectionColor;
  // lines
  private Color line1;
  private Color line5;
  private Color line10;
  private boolean antialiasing;
  private int numProc;
  private int maxLamport;
  private ILamportTrace trace;
  private LogicalGraph eventGraph = null;
  private Font smallFont;
  private GC gc;
  private boolean scrollBarsInitialized = false;

  LogicalGraphPaintListener( final LogicalGraph eventGraph ) {
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    this.listener = new IPropertyChangeListener() {

      public void propertyChange( final PropertyChangeEvent event ) {
        handleProperyChanged( event );
      }
    };
    store.addPropertyChangeListener( this.listener );
    this.zoomfactor = 1;
    this.eventGraph = eventGraph;
    updatePropertiesFromPreferences();
    setHorizontal( 0 );
    setVertical( 0 );
    this.line1 = new Color( Display.getDefault(), new RGB( 196, 196, 196 ) );
    this.line5 = new Color( Display.getDefault(), new RGB( 128, 128, 128 ) );
    this.line10 = new Color( Display.getDefault(), new RGB( 64, 64, 64 ) );
    // this.selectionColor = Display.getDefault().getSystemColor(
    // SWT.COLOR_GREEN );
  }

  protected int getZoomfactor() {
    return this.zoomfactor;
  }

  protected void handleResize() {
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
    // selection color
    this.selectionColor = new Color( this.eventGraph.getDisplay(),
                                     PreferenceConverter.getColor( store,
                                                                   PreferenceConstants.P_SELECTION_COLOR ) );
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
    // Selection Color
    else if( event.getProperty().equals( PreferenceConstants.P_SELECTION_COLOR ) )
    {
      this.selectionColor.dispose();
      this.selectionColor = new Color( Display.getDefault(),
                                       PreferenceConverter.getColor( store,
                                                                     event.getProperty() ) );
      this.eventGraph.redraw();
    }
  }

  void setFont( final Font font ) {
    FontData fontData = font.getFontData()[ 0 ];
    fontData.setHeight( this.fontsize );
    fontData.setStyle( SWT.NORMAL );
    this.smallFont = new Font( font.getDevice(), fontData );
  }

  /**
   * Draw a connection with an arrowhead from (x1,y1) to (x2,y2).
   *
   * @param x1
   * @param y1
   * @param x2
   * @param y2
   */
  private void connection( final int x1,
                           final int y1,
                           final int x2,
                           final int y2 )
  {
    int xv = x2 - x1;
    int yv = y2 - y1;
    float c = ( float )Math.sqrt( xv * xv + yv * yv );
    int ex = Math.round( ( xv / c * this.eventSize / 2 ) );
    int ey = Math.round( ( yv / c * this.eventSize / 2 ) );
    this.gc.drawLine( x2 - ex, y2 - ey, x1 + ex, y1 + ey );
    int[] arrowhead = {
      x2 - ex,
      y2 - ey,
      x2 - ex - ex - ey,
      y2 - ey - ey + ex,
      x2 - ex - ex + ey,
      y2 - ey - ey - ex
    };
    this.gc.fillPolygon( arrowhead );
  }

  /**
   * Draw the controls.
   */
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
                          / 2 );
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
                          / 2 );
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
                          / 2 );
        }
      }
    }
    // TODO right - because of broadcasts
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
                                / 2 );
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
                            y + this.eventSize / 2 );
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

  private void drawRulers() {
    this.gc.setForeground( this.gc.getDevice().getSystemColor( SWT.COLOR_BLACK ) );
    this.gc.setFont( this.smallFont );
    // vertical
    this.gc.setClipping( 1, 1, 24, this.height - 31 );
    for( int i = this.fromProcess, y = 0 - this.yOffset; i < this.toProcess; i++, y += this.vSpace )
    {
      if (this.vSpace > 8 || i % 2 == 0)  this.gc.drawText( Integer.toString( i ), 3, y - 7 + this.eventSize / 2 );
      this.gc.drawLine( 20, y + this.eventSize / 2, 22, y + this.eventSize / 2 );
    }
    // horizontal
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
      drawRulers();
      // set the clipping to the graph area
      this.gc.setClipping( 31, 1, this.width - 31, this.height - 31 );
      // this.gc.setClipping(0, 0, this.width, this.height);
      // draw the grid
      drawGrid();
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
  public void setVertical( final int selection ) {
    this.fromProcess = selection / this.vSpace;
    this.yOffset = selection % this.vSpace - this.hSpace / 2;
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

  protected void setTrace( final ILamportTrace trace ) {
    this.trace = trace;
    if( this.trace != null ) {
      this.maxLamport = this.trace.getMaximumLamportClock();
      this.numProc = this.trace.getNumberOfProcesses();
      this.setHorizontal( 0 );
      this.setVertical( 0 );
      this.scrollBarsInitialized = false;
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

  protected void setHorizontalScrollBar( final ScrollBar bar ) {
    this.horizontalScrollBar = bar;
    this.horizontalScrollBar.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event e ) {
        handleHorizontalScrollBar();
      }
    } );
  }

  protected void setVerticalScrollBar( final ScrollBar bar ) {
    this.verticalScrollBar = bar;
    this.verticalScrollBar.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event e ) {
        handleVerticalScrollBar();
      }
    } );
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

  protected int getEventSize() {
    return this.eventSize;
  }

  protected int getHSpace() {
    return this.hSpace;
  }

  protected int getVSpace() {
    return this.vSpace;
  }
}
