/*****************************************************************************
 * Copyright (c) 2009 g-Eclipse Consortium 
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
 *    Thomas Koeckerbauer - MNM-Team, LMU Munich, code cleanup of logical and physical trace viewers
 *****************************************************************************/

package eu.geclipse.traceview.internal;

import java.util.Iterator;
import java.util.SortedSet;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.preferences.PreferenceConstants;


public abstract class AbstractGraphPaintListener implements PaintListener {
  // constants
  protected final static int leftRulerWidth = 25;
  protected final static int bottomRulerWidth = 25;
  protected final static int leftMargin = leftRulerWidth + 5;
  protected final static int bottomMargin = bottomRulerWidth + 5;
  protected final static int arc = 6;
  public IPropertyChangeListener listener;
  // scrollbar
  protected ScrollBar horizontalScrollBar;
  protected ScrollBar verticalScrollBar;
  // visible
  protected int fromProcessLine = 0;
  protected int toProcessLine = 0;
  // size
  protected int hSpace = 6;
  protected int vSpace = 6;
  protected int yOffset;
  protected int eventSize = 2;
  protected int fontsize = 8;
  protected int width;
  protected int height;
  // presentation
  protected Color selectionColor;
  // black
  protected Color black;
  // lines
  protected Color line1;
  protected Color line5;
  protected Color line10;
  protected boolean antialiasing;
  protected GC gc;
  protected AbstractGraphVisualization eventGraph;
  protected Font smallFont;
  protected int numProc;
  protected boolean fastRedraw;
  protected long fullRedrawDuration;

  protected AbstractGraphPaintListener( final AbstractGraphVisualization eventGraph ) {
    this.listener = new IPropertyChangeListener() {

      public void propertyChange( final PropertyChangeEvent event ) {
        handleProperyChanged( event );
      }
    };
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    store.addPropertyChangeListener( this.listener );
    this.eventGraph = eventGraph;
    updatePropertiesFromPreferences();
    setHorizontal( 0 );
    setVertical( 0 );
    this.black = Display.getDefault().getSystemColor( SWT.COLOR_BLACK);
    this.line1 = new Color( Display.getDefault(), new RGB( 196, 196, 196 ) );
    this.line5 = new Color( Display.getDefault(), new RGB( 128, 128, 128 ) );
    this.line10 = new Color( Display.getDefault(), new RGB( 64, 64, 64 ) );
  }

  /**
   * @param selection
   */
  public void setVertical( final int selection ) {
    this.fromProcessLine = selection / this.vSpace;
    this.yOffset = selection % this.vSpace - this.vSpace / 2;
  }

  public abstract void setHorizontal( int i );
  

  void updatePropertiesFromPreferences() {
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    // settings
    this.antialiasing = store.getBoolean( PreferenceConstants.P_ANTI_ALIASING );
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
    // Selection Color
    else if( event.getProperty().equals( PreferenceConstants.P_SELECTION_COLOR ) ) {
      this.selectionColor.dispose();
      this.selectionColor = new Color( Display.getDefault(),
                                       PreferenceConverter.getColor( store,
                                                                     event.getProperty() ) );
      this.eventGraph.redraw();
    }
  }

  public void setFont( final Font font ) {
    FontData fontData = font.getFontData()[ 0 ];
    fontData.setHeight( this.fontsize );
    fontData.setStyle( SWT.NORMAL );
    this.smallFont = new Font( font.getDevice(), fontData );
  }

  protected void drawVRulerWithMovingLine( final GC rulerGC, final int line, final int y ) {
    rulerGC.setForeground( rulerGC.getDevice().getSystemColor( SWT.COLOR_BLACK ) );
    rulerGC.setBackground( rulerGC.getDevice().getSystemColor( SWT.COLOR_WHITE ) );
    rulerGC.fillRoundRectangle( 0, 0, leftRulerWidth, this.height
                                - bottomMargin, arc, arc );
    rulerGC.drawRoundRectangle( 0, 0, leftRulerWidth, this.height
                                - bottomMargin, arc, arc );
    drawVRuler( rulerGC, line );
    rulerGC.drawLine( 20, y , 22, y );
    String text = getLabelForLine( line );
    rulerGC.drawText( text, 3, y - 7 );
  }

  protected void drawVRuler( final GC rulerGC, final int lineToSkip ) {
    rulerGC.setForeground( rulerGC.getDevice().getSystemColor( SWT.COLOR_BLACK ) );
    rulerGC.setFont( this.smallFont );
    rulerGC.setClipping( 1, 1, 24, this.height - 31 );
    for( int i = this.fromProcessLine, y = 0 - this.yOffset; i < this.toProcessLine; i++, y += this.vSpace ) {
      if (i == lineToSkip) continue;
      if (this.vSpace > 8 || i % 2 == 0) {
        String text = getLabelForLine( i );
        rulerGC.drawText( text, 3, y - 7 + this.eventSize / 2 );
      }
      rulerGC.drawLine( 20, y + this.eventSize / 2, 22, y + this.eventSize / 2 );
    }
  }
  
  protected String getLabelForLine( final int procLine ) {
    SortedSet<Integer> procSet = this.eventGraph.getLineToProcessMapping().get( procLine );
    StringBuilder sb = new StringBuilder();
    Iterator<Integer> it = procSet.iterator();
    if (it.hasNext()) {
      int procNr = it.next().intValue();
      boolean hidden = this.eventGraph.hideProcess[procNr];
      if (hidden) sb.append( '(' );
      sb.append( procNr );
      if (hidden) sb.append( ')' );
      while (it.hasNext()) {
        procNr = it.next().intValue();
        hidden = this.eventGraph.hideProcess[procNr];
        sb.append( ',' );
        if (hidden) sb.append( '(' );
        sb.append( procNr );
        if (hidden) sb.append( ')' );
      }
    } else {
      sb.append( 'X' );
    }
    return sb.toString();
  }

  protected void drawGridHLines() {
    this.gc.setForeground( this.line1 );
    LineType hLines = this.eventGraph.getHLines();
    if( hLines != LineType.Lines_None ) {
      if (this.fastRedraw) hLines = LineType.Lines_10;
      for( int i = this.fromProcessLine, y = 0 - this.yOffset + this.eventSize / 2; i < this.toProcessLine; i++, y += this.vSpace )
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
  }

  /**
   * Draw the controls.
   */
  protected void drawBackground() {
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
   * Draw a connection with an arrowhead from (x1,y1) to (x2,y2).
   *
   * @param x1
   * @param y1
   * @param x2
   * @param y2
   */
  protected void connection( final int x1,
                             final int y1,
                             final int x2,
                             final int y2,
                             final boolean spacing)
  {
    if( this.fastRedraw ) {
      this.gc.drawLine( x2, y2, x1, y1 );
    } else {
      long xv = x2 - x1;
      long yv = y2 - y1;
      float c = ( float )Math.sqrt( xv * xv + yv * yv );
      int ex = (int)( ( xv / c * getArrowSize() ) + 0.5);
      int ey = (int)( ( yv / c * getArrowSize() ) + 0.5);
      int ox = spacing ? ex : 0;
      int oy = spacing ? ey : 0;
      this.gc.drawLine( x2 - ox, y2 - oy, x1 + ox, y1 + oy );
      int[] arrowhead = {
        x2 - ox,
        y2 - oy,
        x2 - ox - ex - ey,
        y2 - oy - ey + ex,
        x2 - ox - ex + ey,
        y2 - oy - ey - ex
      };
      this.gc.fillPolygon( arrowhead );
    }
  }

  public abstract int getArrowSize();

  public void setHorizontalScrollBar( final ScrollBar bar ) {
    this.horizontalScrollBar = bar;
    this.horizontalScrollBar.setPageIncrement( this.hSpace );
    this.horizontalScrollBar.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event e ) {
        // might need fixed bug 323092 to work reliable
        AbstractGraphPaintListener.this.fastRedraw = e.detail == SWT.DRAG;
        handleHorizontalScrollBar();
      }
    } );
  }

  public void setVerticalScrollBar( final ScrollBar bar ) {
    this.verticalScrollBar = bar;
    this.verticalScrollBar.setPageIncrement( this.vSpace );
    this.verticalScrollBar.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event e ) {
        // might need fixed bug 323092 to work reliable
        AbstractGraphPaintListener.this.fastRedraw = e.detail == SWT.DRAG;
        handleVerticalScrollBar();
      }
    } );
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

  public int getEventSize() {
    return this.eventSize;
  }

  public int getHSpace() {
    return this.hSpace;
  }

  public int getVSpace() {
    return this.vSpace;
  }

  public int getYOffset() {
    return this.yOffset;
  }

  public int getFromProcessLine() {
    return this.fromProcessLine;
  }

  protected boolean procDrawingEnabled( final int procId, final boolean ignoreHide ) {
    return (!this.eventGraph.hideProcess[procId] || ignoreHide) &&
           this.eventGraph.getProcessToLineMapping()[procId] != -1;
  }

  protected boolean procDrawingEnabled( final int procId ) {
    return procDrawingEnabled( procId, false );
  }

  protected int getYPosForProcId( final int procId ) {
    int line = this.eventGraph.getProcessToLineMapping()[procId];
    return ( line - this.fromProcessLine ) * this.vSpace
           - this.yOffset + this.eventSize / 2;
  }

  public boolean isInGraphArea(int x, int y) {
    return x >= 31 && y >= 1 && x <= this.width - 31 && y <= this.height - 31;
  }

  public void updateSelectionRectangle(Rectangle selectionRect, MouseEvent e) {
    GC selectionGC = new GC(this.eventGraph);
    selectionGC.setClipping( 31, 1, this.width - 31, this.height - 31 );
    selectionGC.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
    selectionGC.setXORMode(true);
    if (selectionRect.width != 0 || selectionRect.height != 0) {
      selectionGC.drawRectangle(selectionRect);
    }
    if (e != null) {
      selectionRect.width = e.x - selectionRect.x;
      selectionRect.height = e.y - selectionRect.y;
      if (selectionRect.width != 0 || selectionRect.height != 0) {
        selectionGC.drawRectangle(selectionRect);
      }
    }
    selectionGC.dispose();
  }

  public abstract void handleResize();

  public abstract void print( final GC gc2 );

  public abstract void setTrace( final ITrace trace );
}
