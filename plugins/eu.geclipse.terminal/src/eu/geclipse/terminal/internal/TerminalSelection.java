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
import org.eclipse.swt.widgets.Display;

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
  
  void clearSelection() {
    Display.getDefault().syncExec( new Runnable() {
      public void run() {
        clearSelection( 0, 0 );
      }
    } );
  }

  void clearSelection( final int row, final int col ) {
    boolean redraw = !isEmpty();
    this.row1 = row;
    this.col1 = col;
    this.row2 = row;
    this.col2 = col;
    this.startRow = row;
    this.startCol = col;
    this.endRow = row;
    this.endCol = col;
    if ( redraw ) this.terminal.redraw();
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
    int col = xPos / this.terminal.getFontWidth();
    if ( col > this.terminal.getNumCols() ) {
      col = this.terminal.getNumCols();
    }
    return col;
  }

  private int getRowForYPos( final int yPos ) {
    int scrollbarLine = this.terminal.getScrollbarPosLine();
    int row = yPos / this.terminal.getFontHeigth() + scrollbarLine;
    if ( row >= this.terminal.getNumLines() + this.terminal.getHistorySize() ) {
      row = this.terminal.getNumLines() + this.terminal.getHistorySize() - 1;
    }
    return row;
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
    Char[][] screenbuffer = this.terminal.getScreenBuffer();
    for( int row = this.startRow; row <= this.endRow; row++ ) {
      for( int col = ( ( row == this.startRow ) ? this.startCol : 0 );
           col < ( ( row == this.endRow ) ? this.endCol : this.terminal.getNumCols() );
           col++ ) {
        char ch = screenbuffer[ row ][ col ].ch;
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
    int row = getRowForYPos( event.y );
    int col = getColForXPos( event.x );
    int selStartCol = col;
    int selEndCol = col;
    char ch;
    do {
      ch = this.terminal.getScreenBuffer()[ row ][ selStartCol ].ch;
      selStartCol--;
    } while( selStartCol >= 0 && ch !=' ' && ch != 0 );
    selStartCol++;
    if ( ch ==' ' || ch == 0 ) selStartCol++;
    do {
      ch = this.terminal.getScreenBuffer()[ row ][ selEndCol ].ch;
      selEndCol++;
    } while( selEndCol < this.terminal.getNumCols() && ch !=' ' && ch != 0 );
    if ( ch ==' ' || ch == 0 ) selEndCol--;
    clearSelection( row, selStartCol );
    updateSelection( row, selEndCol );
    triggerRedraw( row, selStartCol, row, selEndCol );
    this.terminal.fireSelectionChanged();    
  }

  public void mouseDown( final MouseEvent event ) {
    if ( event.button == 1 ) {
      clearSelection( getRowForYPos( event.y ), getColForXPos( event.x ) );
    }
  }

  public void mouseUp( final MouseEvent event ) {
//    if ( event.button == 1 ) {
//      updateSelection( getRowForYPos( event.y ), getColForXPos( event.x ) );
//      this.terminal.fireSelectionChanged();
//    }
  }

  public void mouseMove( final MouseEvent event ) {
    if ( ( event.stateMask & SWT.BUTTON1 ) != 0 ) {
      int oldStartRow = this.startRow;
      int oldStartCol = this.startCol;
      int oldEndRow = this.endRow;
      int oldEndCol = this.endCol;
      updateSelection( getRowForYPos( event.y ), getColForXPos( event.x ) );
      triggerRedraw( oldStartRow, oldStartCol, this.startRow, this.startCol );
      triggerRedraw( oldEndRow, oldEndCol, this.endRow, this.endCol );
      this.terminal.fireSelectionChanged();
    }
  }
  
  private void triggerRedraw( final int oldRow, final int oldCol,
                              final int newRow, final int newCol ) {
    int topRow = Math.min( oldRow, newRow );
    int bottomRow = Math.max( oldRow, newRow );
    int leftCol = Math.min( oldCol, newCol );
    int rightCol = Math.max( oldCol, newCol );
    if ( topRow != bottomRow ) {
      this.terminal.triggerRedraw( 0,
                                   topRow - this.terminal.getHistorySize(),
                                   this.terminal.getNumCols(),
                                   bottomRow - topRow + 1 );
    } else if ( leftCol != rightCol ) {
      this.terminal.triggerRedraw( leftCol,
                                   topRow - this.terminal.getHistorySize(),
                                   rightCol - leftCol,
                                   1 );
    }
  }
  
  boolean isSelected( final int row, final int col ) {
    return ( row > this.startRow && row < this.endRow)
           || ( row == this.startRow && row != this.endRow && col >= this.startCol )
           || ( row != this.startRow && row == this.endRow && col < this.endCol )
           || ( row == this.startRow && row == this.endRow && col >= this.startCol && col < this.endCol );
  }
}
