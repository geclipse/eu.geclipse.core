/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.terminal.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

class TerminalPainter implements PaintListener {
  private Terminal terminal;
  private Char startChar = null;
  private int startCharLine = 0;
  private int startCharCol = 0;
  private StringBuilder buffer = new StringBuilder();
  private int numChars = 0;
  private LineHeightMode lineHeight;
  private LineWidthMode lineWidth;
  private LineHeightMode[] lineHeightMode;
  private LineWidthMode[] lineWidthMode;
  private Font normalFont;
  private Font boldFont;
  private Font normalDoubleFont;
  private Font boldDoubleFont;

  // Character a - z of the VT100 "special" character set
  private final char[] specialChars = {   // TODO find missing characters in unicode table
    '\u2591', // a
    '\u2409', // b
    '\u240c', // c
    '\u240d', // d
    '\u240a', // e
    '\u00b0', // f
    '\u00b1', // g 
    '?', // h
    '?', // i
    '\u2518', // j
    '\u2510', // k
    '\u250c', // l 
    '\u2514', // m
    '\u253c', // n
    '?', // o
    '?', // p
    '\u2500', // q
    '?', // r
    '?', // s
    '\u251c', // t
    '\u2524', // u
    '\u2534', // v
    '\u252c', // w
    '\u2502', // x
    '\u2264', // y
    '\u2265' // z
  };
  

  TerminalPainter( final Terminal term ) {
    this.terminal = term;
  }

  private void addToStringBuffer( final StringBuilder buf, final Char ch ) {
    if ( ch.charSet == CharSet.SPECIAL && ch.ch >= 'a' && ch.ch <= 'z' ) {
      buf.append( this.specialChars[ ch.ch - 'a' ] );
    } else {
      buf.append( ch.ch );
    }
  }

  private void paintBufferedArea( final GC gc ) {
    int fontHeight = this.terminal.getFontHeigth();
    int fontWidth = this.terminal.getFontWidth();
    if ( this.lineWidth == LineWidthMode.DOUBLE ) fontWidth *= 2;
    
    if ( this.startChar.negative ^ this.terminal.isInReverseScreenMode() ) {
      gc.setBackground( this.startChar.fgColor );
      gc.setForeground( this.startChar.bgColor );
    } else {
      gc.setBackground( this.startChar.bgColor );
      gc.setForeground( this.startChar.fgColor );
    }
    if ( this.startChar.ch == 0 ) {
      gc.fillRectangle( fontWidth * this.startCharCol,
                        fontHeight * this.startCharLine,
                        fontWidth * this.numChars, fontHeight );
    } else {
      // XXX fillRectangle is a hack against ugly fonts
      gc.fillRectangle( fontWidth * this.startCharCol,
                        fontHeight * this.startCharLine,
                        fontWidth * this.numChars, fontHeight );

      if ( this.lineHeight != LineHeightMode.NORMAL 
           && this.lineWidth == LineWidthMode.DOUBLE ) {
        Rectangle rect = gc.getClipping();
        gc.setClipping( 0, fontHeight * this.startCharLine,
                        rect.width, fontHeight );
        if ( this.startChar.bold ) gc.setFont( this.boldDoubleFont );
        else gc.setFont( this.normalDoubleFont );
      } else {
        gc.setClipping( (Rectangle) null );
        if ( this.startChar.bold ) gc.setFont( this.boldFont );
        else gc.setFont( this.normalFont );
      }
      if ( this.lineWidth == LineWidthMode.NORMAL ) {
        gc.drawText( this.buffer.toString(), fontWidth * this.startCharCol,
                     fontHeight * this.startCharLine );
      } else {
        int heightOffset = 0;
        if ( this.lineHeight == LineHeightMode.DOUBLE_BOTTOM ) {
          heightOffset = -1;
        }
        String string = this.buffer.toString();
        for ( int i = 0; i < string.length(); i++ ) {
          gc.drawText( string.substring( i, i + 1 ),
                       fontWidth * ( this.startCharCol + i ),
                       fontHeight * ( this.startCharLine + heightOffset ) );
        }
      }
      if ( this.startChar.underscore ) {
        for ( int col = this.startCharCol; col < this.startCharCol + this.buffer.length(); col++ ) {
          gc.drawText( "_", fontWidth * col, //$NON-NLS-1$
                       fontHeight * this.startCharLine, true );
        }
      }
    }
  }

  private void resetStartChar( final Char ch, final int line, final int col ) {
    if ( ch.ch != 0 ) {
      this.buffer.delete( 0, this.buffer.length() );
      addToStringBuffer( this.buffer, ch );
    }
    this.startChar = ch;
    this.startCharLine = line;
    this.startCharCol = col;
    this.numChars = 1;
    this.lineHeight = this.lineHeightMode[ line + this.terminal.getScrollbarPosLine() ];
    this.lineWidth = this.lineWidthMode[ line + this.terminal.getScrollbarPosLine() ];
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.swt.events.PaintListener#paintControl(org.eclipse.swt.events.PaintEvent)
   */
  public void paintControl( final PaintEvent paintEvent ) {
    Char[][] screenBuffer = this.terminal.getScreenBuffer();
    this.lineHeightMode = this.terminal.getLineHeightMode();
    this.lineWidthMode = this.terminal.getLineWidthMode();
    int numLines = this.terminal.getNumLines();
    int numCols = screenBuffer[0].length;
    int fontHeight = this.terminal.getFontHeigth();
    int fontWidth = this.terminal.getFontWidth();
    int startLine = paintEvent.y / fontHeight;
    int endLine = ( paintEvent.y + paintEvent.height + fontHeight ) / fontHeight;
    boolean fillBottomGap = screenBuffer.length < endLine + this.terminal.getScrollbarPosLine();
    if ( endLine > numLines && fillBottomGap) endLine = numLines;
    for ( int line = startLine; line < endLine; line++ ) {
      int startCol = paintEvent.x / fontWidth;
      int endCol = ( paintEvent.x + paintEvent.width + fontWidth ) / fontWidth;
      if ( endCol > numCols ) endCol = numCols;
      if ( this.lineWidthMode[ line + this.terminal.getScrollbarPosLine() ] == LineWidthMode.DOUBLE ) {
        startCol /= 2;
        endCol = ( endCol + 1 ) / 2;
      }
      for ( int col = startCol; col < endCol; col ++) {
        Char ch = screenBuffer[ line + this.terminal.getScrollbarPosLine() ][ col ];
        if ( this.startChar == null ) {
          resetStartChar( ch, line, col );
        } else if ( this.startChar.ch == 0 && ch.ch == 0
                    && ch.hasSameFormat( this.startChar ) ) {
          this.numChars++;
        } else if ( this.startChar.ch != 0 && ch.ch != 0
                    && ch.hasSameFormat( this.startChar ) ) {
          addToStringBuffer( this.buffer, ch );
          this.numChars++;
        } else {
          paintBufferedArea( paintEvent.gc );
          resetStartChar( ch, line, col );
        }
      }
      paintBufferedArea( paintEvent.gc );
      this.startChar = null;
      if ( line + this.terminal.getScrollbarPosLine() == this.terminal.getCursorLine() + this.terminal.getHistorySize() ) {
        int widthMult = 1;
        if ( this.lineWidthMode[ line + this.terminal.getScrollbarPosLine() ] == LineWidthMode.DOUBLE ) widthMult = 2; 
        // draw cursor
        paintEvent.gc.drawText( "_", //$NON-NLS-1$
                                fontWidth * this.terminal.getCursorCol() * widthMult,
                                fontHeight * line , true );
      }
    }
    // fill the gap at the widget border
    paintEvent.gc.setBackground( this.terminal.getBackground() );
    if ( fillBottomGap ) {
      if ( ( paintEvent.y + paintEvent.height ) >= ( fontHeight * numLines ) ) {
        int height = ( paintEvent.y + paintEvent.height )
                     - ( fontHeight * numLines );
        paintEvent.gc.fillRectangle( paintEvent.x, fontHeight * numLines,
                                     paintEvent.width, height );
      }
    }
    if ( ( paintEvent.x + paintEvent.width ) >= ( fontWidth * numCols ) ) {
      int width = ( paintEvent.x + paintEvent.width )
                  - ( fontWidth * numCols );
      paintEvent.gc.fillRectangle( fontWidth * numCols, paintEvent.y,
                                   width, paintEvent.height );
    }
  }

  void setFont( final Font font ) {
   FontData fontData = font.getFontData()[0];
   fontData.setStyle( SWT.NORMAL );
   this.normalFont = new Font( font.getDevice(), fontData );
   fontData.setStyle( SWT.BOLD );
   this.boldFont = new Font( font.getDevice(), fontData );
   fontData.setStyle( SWT.NORMAL );
   fontData.setHeight( fontData.getHeight() * 2 );
   this.normalDoubleFont = new Font( font.getDevice(), fontData );
   fontData.setStyle( SWT.BOLD );
   this.boldDoubleFont = new Font( font.getDevice(), fontData );
  }
  
  void scrollUp( final int topMargin, final int bottomMargin ) {
    Char[][] screenBuffer = this.terminal.getScreenBuffer();
    int numCols = screenBuffer[0].length;
    int fontHeight = this.terminal.getFontHeigth();
    int fontWidth = this.terminal.getFontWidth();
    GC gc = new GC( this.terminal );
    gc.copyArea( 0,
                 fontHeight * ( topMargin + this.terminal.getHistorySize() - this.terminal.getScrollbarPosLine() + 1 ),
                 fontWidth * numCols,
                 fontHeight * ( bottomMargin - topMargin ),
                 0,
                 fontHeight * ( topMargin + this.terminal.getHistorySize() - this.terminal.getScrollbarPosLine() ),
                 true );
    gc.dispose();
  }

  void scrollDown( final int topMargin, final int bottomMargin ) {
    Char[][] screenBuffer = this.terminal.getScreenBuffer();
    int numCols = screenBuffer[0].length;
    int fontHeight = this.terminal.getFontHeigth();
    int fontWidth = this.terminal.getFontWidth();
    GC gc = new GC( this.terminal );
    gc.copyArea( 0,
                 fontHeight * ( topMargin + this.terminal.getHistorySize() - this.terminal.getScrollbarPosLine() ),
                 fontWidth * numCols,
                 fontHeight * ( bottomMargin - topMargin ),
                 0,
                 fontHeight * ( topMargin + this.terminal.getHistorySize() - this.terminal.getScrollbarPosLine() + 1 ),
                 true );
    gc.dispose();
  }
}
