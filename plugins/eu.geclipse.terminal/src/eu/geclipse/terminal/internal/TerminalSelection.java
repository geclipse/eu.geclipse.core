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

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;

class TerminalSelection implements ITextSelection, MouseMoveListener, MouseListener {
  private int startRow;
  private int startCol;
  private int endRow;
  private int endCol;
  private int row1;
  private int col1;
  private int row2;
  private int col2;
  private Terminal terminal;
  
  TerminalSelection( final Terminal terminal ) {
    this.terminal = terminal;
  }
  
  private void clearSelection( final int row, final int col ) {
    this.row1 = row;
    this.col1 = col;
    this.row2 = row;
    this.col2 = col;
    this.startRow = row;
    this.startCol = col;
    this.endRow = row;
    this.endCol = col;
  }
  
  private void updateSelection( final int row, final int col ) {
    this.row2 = row;
    this.col2 = col;
    if ( this.row1 < this.row2 || ( this.row1 == this.row2 && this.col1 < this.col2 ) ) {
      this.startRow = this.row1;
      this.startCol = this.col1;
      this.endRow = row;
      this.endCol = col;
    } else {
      this.startRow = row;
      this.startCol = col;
      this.endRow = this.row1;
      this.endCol = this.col1;
    }
  }

  private int getColForXPos( final int xPos ) {
    return xPos / this.terminal.getFontWidth();
  }

  private int getRowForYPos( final int yPos ) {
    return yPos / this.terminal.getFontHeigth();
  }

  public int getEndLine() {
    return this.endRow;
  }

  public int getLength() {
    return getText().length();
  }

  public int getOffset() {
    // TODO Auto-generated method stub
    return 0;
  }

  public int getStartLine() {
    return this.startRow;
  }

  public String getText() {
    StringBuilder str = new StringBuilder();
    int scrollbarLine = this.terminal.getScrollbarPosLine();
    Char[][] screenbuffer = this.terminal.getScreenBuffer();
    for( int row = this.startRow; row <= this.endRow; row++ ) {
      for( int col = ( ( row == this.startRow ) ? this.startCol : 0 );
           col < ( ( row == this.endRow ) ? this.endCol : this.terminal.getNumCols() );
           col++ ) {
        char ch = screenbuffer[ row + scrollbarLine ][ col ].ch;
        if ( ch != 0 ) str.append( ch );
      }
      if ( row != this.endRow ) str.append( '\n' );
    }
    return str.toString();
  }

  public boolean isEmpty() {
    return this.startCol == this.endCol && this.startRow == this.endRow;
  }

  public void mouseDoubleClick( final MouseEvent event ) {
    // not used
  }

  public void mouseDown( final MouseEvent event ) {
    if ( event.button == 1 ) {
      clearSelection( getRowForYPos( event.y ), getColForXPos( event.x ) );
    }
  }

  public void mouseUp( final MouseEvent event ) {
    if ( event.button == 1 ) {
      // TODO add bounds check for selection
      updateSelection( getRowForYPos( event.y ), getColForXPos( event.x ) );
      this.terminal.fireSelectionChanged();
    }
  }

  public void mouseMove( final MouseEvent event ) {
    if ( ( event.stateMask & SWT.BUTTON1 ) != 0 ) {
      updateSelection( getRowForYPos( event.x ), getRowForYPos( event.y ) );
    }
  }
}
