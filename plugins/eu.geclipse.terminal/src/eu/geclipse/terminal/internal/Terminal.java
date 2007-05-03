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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import eu.geclipse.terminal.ITerminalListener;


/**
 * VT100 terminal emulator widget.
 */
public class Terminal extends Canvas {
  private static final int BEL = 7;
  private static final int FF = 12;
  private static final int SO = 14;
  private static final int SI = 15;
  private static final int TAB_WIDTH = 8;
  OutputStream output;
  KeyPadMode keyPadMode;
  CursorKeyMode cursorKeyMode;
  ScrollMode scrollMode;
  boolean newLineMode;
  TerminalPainter terminalPainter;
  int topMargin;
  int bottomMargin;
  private PushbackInputStream input;
  private Char[][] screenBuffer;
  private LineHeightMode[] lineHeightMode;
  private LineWidthMode[] lineWidthMode;
  private int fontHeight;
  private int fontWidth;
  private int numCols;
  private int numLines;
  private final boolean[] leds = new boolean[4];
  private Cursor cursor;
  private Cursor savedCursor;
  private Color[] systemColors;
  private Color defaultBgColor;
  private Color defaultFgColor;
  private OriginMode originMode;
  private final CharSet[] charSetG = new CharSet[4];
  private boolean reverseScreenMode;
  private String windowTitle;
  private final SortedSet<Integer> tabulatorPositons = new TreeSet<Integer>();
  private ITerminalListener listener;
  private boolean wraparound;
  private boolean reverseWraparound;
  private boolean running;
  private Clipboard clipboard;

  /**
   * Creates a new terminal widget.
   * 
   * @param parent parent widget.
   * @param style widget style.
   * @param initFgColor default foreground color.
   * @param initBgColor default background color.
   */
  public Terminal(final Composite parent, final int style, final Color initFgColor, final Color initBgColor) {
    super(parent, style | SWT.NO_BACKGROUND );
    this.clipboard = new Clipboard( getDisplay() );
    initMenu();
    initSystemColorTable();    
    if ( initBgColor != null ) this.defaultBgColor = initBgColor;
    if ( initFgColor != null ) this.defaultFgColor = initFgColor;
    this.cursor = new Cursor( this.defaultFgColor, this.defaultBgColor );
    this.savedCursor = new Cursor( this.defaultFgColor, this.defaultBgColor );
    this.screenBuffer = new Char[0][];
    changeScreenSize();
    this.terminalPainter = new TerminalPainter( this );
    addPaintListener( this.terminalPainter );
    addListener(SWT.Resize, new Listener() {
      public void handleEvent( final Event event ) {
        changeScreenSize();
      }
    });

    addListener(SWT.KeyDown, new Listener() { 
      public void handleEvent( final Event event ) {
        // index 0 = keypad numeric mode, index 1 = keypad application mode
        // vt100
        final byte[][] arrowUp      = { { SWT.ESC, '[', 'A' }, { SWT.ESC, 'O', 'A' } };
        final byte[][] arrowDown    = { { SWT.ESC, '[', 'B' }, { SWT.ESC, 'O', 'B' } };
        final byte[][] arrowRight   = { { SWT.ESC, '[', 'C' }, { SWT.ESC, 'O', 'C' } };
        final byte[][] arrowLeft    = { { SWT.ESC, '[', 'D' }, { SWT.ESC, 'O', 'D' } };
        // xterm
        final byte[] backspace    = { SWT.DEL };
        final byte[] home         = { SWT.ESC, '[', 'H' };
        final byte[] end          = { SWT.ESC, '[', 'F' };
        final byte[] insert       = { SWT.ESC, '[', '2', '~' };
        final byte[] delete       = { SWT.ESC, '[', '3', '~' };
        final byte[] pageUp       = { SWT.ESC, '[', '5', '~' };
        final byte[] pageDown     = { SWT.ESC, '[', '6', '~' };
        final byte[] F1           = { SWT.ESC, 'O', 'P' };
        final byte[] F2           = { SWT.ESC, 'O', 'Q' };
        final byte[] F3           = { SWT.ESC, 'O', 'R' };
        final byte[] F4           = { SWT.ESC, 'O', 'S' };
        final byte[] F5           = { SWT.ESC, '[', '1', '5', '~' };
        final byte[] F6           = { SWT.ESC, '[', '1', '7', '~' };
        final byte[] F7           = { SWT.ESC, '[', '1', '8', '~' };
        final byte[] F8           = { SWT.ESC, '[', '1', '9', '~' };
        final byte[] F9           = { SWT.ESC, '[', '2', '0', '~' };
        final byte[] F10          = { SWT.ESC, '[', '2', '1', '~' };
        final byte[] F11          = { SWT.ESC, '[', '2', '3', '~' };
        final byte[] F12          = { SWT.ESC, '[', '2', '4', '~' };
        final byte[] F13          = { SWT.ESC, '[', '2', '5', '~' };
        final byte[] F14          = { SWT.ESC, '[', '2', '6', '~' };
        final byte[] F15          = { SWT.ESC, '[', '2', '8', '~' };

        final int[] unhandledKeycodes = { // sorted by keycode
          SWT.ALT, SWT.SHIFT, SWT.CTRL, SWT.CAPS_LOCK,
          SWT.NUM_LOCK, SWT.SCROLL_LOCK, SWT.PAUSE
        };

        try {
          OutputStream out = Terminal.this.output;
          if ( out != null ) {
            if ( event.keyCode == SWT.ARROW_UP) {
              out.write( arrowUp[ Terminal.this.keyPadMode.getIndex() ] );
            } else if ( event.keyCode == SWT.ARROW_DOWN ) {
              out.write( arrowDown[ Terminal.this.keyPadMode.getIndex() ] );
            } else if ( event.keyCode == SWT.ARROW_RIGHT ) {
              out.write( arrowRight[ Terminal.this.keyPadMode.getIndex() ] );
            } else if ( event.keyCode == SWT.ARROW_LEFT ) {
              out.write( arrowLeft[ Terminal.this.keyPadMode.getIndex() ] );
            } else if ( event.keyCode == SWT.HOME ) out.write( home );
            else if ( event.keyCode == SWT.END ) out.write( end );
            else if ( event.keyCode == SWT.INSERT ) out.write( insert );
            else if ( event.character == SWT.DEL ) out.write( delete );
            else if ( event.character == SWT.BS ) out.write( backspace );
            else if ( event.keyCode == SWT.PAGE_UP ) out.write( pageUp );
            else if ( event.keyCode == SWT.PAGE_DOWN ) out.write( pageDown );
            else if ( event.keyCode == SWT.F1 ) out.write( F1 );
            else if ( event.keyCode == SWT.F2 ) out.write( F2 );
            else if ( event.keyCode == SWT.F3 ) out.write( F3 );
            else if ( event.keyCode == SWT.F4 ) out.write( F4 );
            else if ( event.keyCode == SWT.F5 ) out.write( F5 );
            else if ( event.keyCode == SWT.F6 ) out.write( F6 );
            else if ( event.keyCode == SWT.F7 ) out.write( F7 );
            else if ( event.keyCode == SWT.F8 ) out.write( F8 );
            else if ( event.keyCode == SWT.F9 ) out.write( F9 );
            else if ( event.keyCode == SWT.F10 ) out.write( F10 );
            else if ( event.keyCode == SWT.F11 ) out.write( F11 );
            else if ( event.keyCode == SWT.F12 ) out.write( F12 );
            else if ( event.keyCode == SWT.F13 ) out.write( F13 );
            else if ( event.keyCode == SWT.F14 ) out.write( F14 );
            else if ( event.keyCode == SWT.F15 ) out.write( F15 );
            else if ( event.character == SWT.CR ) {
              out.write( SWT.CR );
              if ( Terminal.this.newLineMode ) out.write( SWT.LF );
            }
            else if ( event.character != 0 ) out.write( event.character );
            else if ( Arrays.binarySearch( unhandledKeycodes, event.keyCode ) < 0 ) {
              Activator.logMessage( IStatus.WARNING,
                                    Messages.formatMessage( "Terminal.unhandledKeycode", //$NON-NLS-1$
                                                            new Integer( event.keyCode ),
                                                            new Integer( event.character) ) ); 
            }
            out.flush();
          }
        } catch( IOException ioException ) {
          Activator.logException( ioException );
        }
      }
    } );
    reset();
  }

  private void initMenu() {
    Menu popUpMenu = new Menu( getShell(), SWT.POP_UP );
    MenuItem pasteItem = new MenuItem( popUpMenu, SWT.PUSH );
    pasteItem.setText( Messages.getString( "Terminal.paste" ) ); //$NON-NLS-1$
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    ImageDescriptor pasteImage 
        = sharedImages.getImageDescriptor( ISharedImages.IMG_TOOL_PASTE );
    pasteItem.setImage( pasteImage.createImage() );
    pasteItem.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent event ) {
        paste();
      }
    } );
    setMenu( popUpMenu );    
  }

  private Object getClipboardContent( final  int clipboardType ) {
    TextTransfer plainTextTransfer = TextTransfer.getInstance();
    return this.clipboard.getContents(plainTextTransfer, clipboardType);
  }
  
  /**
   * Triggers paste from the clipboard into the terminal.
   */
  public void paste() {
    checkWidget();  
    String text = (String) getClipboardContent( DND.CLIPBOARD );
    if ( text != null && text.length() > 0 ) {
      for ( int i = 0; i < text.length(); i++ ) {
        Event event = new Event();
        event.character = text.charAt( i );
        notifyListeners( SWT.KeyDown, event );
      }
    }
  }

  /**
   * Registers a terminal listner for tracking terminal size and title changes.
   * @param termListener the listener.
   */
  public void setTerminalListener( final ITerminalListener termListener ) {
    this.listener = termListener;
  }

  private void bell() {
    Display.getDefault().syncExec(new Runnable() {
      public void run () {
        Display.getDefault().beep();
      }
    });
    // TODO visible bell?
  }

  private void carriageReturn() {
    if ( this.cursor.col != 0) {
      int oldCol = this.cursor.col;
      this.cursor.col = 0;
      triggerRedraw( oldCol, this.cursor.line, 1, 1 );
      triggerRedraw( 0, this.cursor.line, 1, 1 );
    }
  }

  private void backspace() {
    if ( this.cursor.col > 0 ) {
      this.cursor.col--;
      triggerRedraw( this.cursor.col + 1, this.cursor.line, 1, 1 );
      triggerRedraw( this.cursor.col, this.cursor.line, 1, 1 );
    }
  }

  private void startReaderThread() {
    Thread thread = new Thread( new Runnable(){
      public void run() {
        readerMainLoop();
      }
    }, "Terminal" ); //$NON-NLS-1$
    thread.start();
  }
  
  void readerMainLoop() {
    try {
      this.running = true;
      while( this.running ) {
        int ch = read();
        if ( ch == BEL ) bell(); 
        else if ( ch == SWT.CR ) carriageReturn();
        else if ( ch == SWT.LF ) nextLine();
        else if ( ch == SI ) this.cursor.charSet = this.charSetG[0];
        else if ( ch == SO ) this.cursor.charSet = this.charSetG[1];
        else if ( ch == SWT.TAB ) tabulator();
        else if ( ch == FF ) eraseScreen();
        else if ( ch == SWT.BS ) backspace(); 
        else if ( ch == SWT.ESC ) parseEscSequence();
        else if ( ch < ' ' ) {
          Activator.logMessage( IStatus.WARNING,
                                Messages.formatMessage( "Terminal.unhandledCtrlChar", //$NON-NLS-1$
                                                        new Character( (char) ch ) ) );
        } else {
          this.input.unread( ch );
          readText(); 
        }
      }
    } catch( IOException ioException ) {
      Activator.logException( ioException );
    }
  }

  private void tabulator() {
    int oldCol = this.cursor.col;
    Integer cursorCol = new Integer( this.cursor.col + 1 );
    Integer[] tabArray = this.tabulatorPositons.toArray( new Integer[0] );
    int pos = Arrays.binarySearch( tabArray, cursorCol );
    if ( pos < 0 ) {
      pos = (-pos) - 1;  // get insertion point
    }
    if (tabArray.length > pos) {
      int nextTabStop = tabArray[pos].intValue();
      if ( nextTabStop < numColsInLine( this.cursor.line ) ) {
        this.cursor.col = nextTabStop;
      } else {
        Activator.logMessage( IStatus.WARNING, 
                              Messages.getString( "Terminal.tabStopOutsideDisplay" ) ); //$NON-NLS-1$
      }
    } else {
      // next tabstop (tab width 8)
      this.cursor.col = ( ( this.cursor.col + TAB_WIDTH + 1 ) / TAB_WIDTH ) * TAB_WIDTH;
      if (this.cursor.col >= numColsInLine( this.cursor.line )) {
        this.cursor.col = numColsInLine( this.cursor.line ) - 1;
      }
    }
    triggerRedraw(oldCol, this.cursor.line, 1, 1);
    triggerRedraw(this.cursor.col, this.cursor.line, 1, 1);
  }

  private void readText() throws IOException {
    int ch = read();
    int line = this.cursor.line;
    int startCol = this.cursor.col;
    int colsToUpdate = 0;

    while ( ch >= ' ' ) {
/*try {
  Thread.sleep( 10 );
} catch( InterruptedException e ) {
}
triggerRedraw();*/
//System.out.println( cursor.line + " " + cursor.col + " " + numLines + " " + numCols );

      Char scrCh = this.screenBuffer[ this.cursor.line ][ this.cursor.col ];
      scrCh.ch = (char)ch;
      scrCh.setToCursorFormat( this.cursor );
      this.cursor.col++;
      colsToUpdate++;
      if ( this.cursor.col >= numColsInLine( this.cursor.line ) ) {
        if ( this.wraparound ) {
          this.cursor.col = 0;
          if ( this.cursor.line < this.numLines - 1 ) {
            this.cursor.line++;
            triggerRedraw( startCol, line, colsToUpdate, 1 );
            startCol = 0;
            line = this.cursor.line;
            colsToUpdate = 0;
          }
          else this.cursor.col = numColsInLine( this.cursor.line ) - 1;
        } else {
          this.cursor.col = numColsInLine( this.cursor.line ) - 1;
        }
      }
      if ( this.input.available() == 0 ) {
        if (colsToUpdate != 0) {
          triggerRedraw( startCol, line, colsToUpdate, 1 );
          triggerRedraw( this.cursor.col, this.cursor.line, 1, 1);
          startCol += colsToUpdate;
          colsToUpdate = 0;
        }
      }
      ch = read();
    }
    triggerRedraw( startCol, line, colsToUpdate, 1 );
    triggerRedraw( this.cursor.col, this.cursor.line, 1, 1);
    this.input.unread( ch );
  }

  private int numColsInLine( final int line ) {
    int numColsInLine = this.numCols;
    if ( this.lineWidthMode[line] == LineWidthMode.DOUBLE ) {
      numColsInLine /= 2;
    }
    return numColsInLine;
  }
  
  private void triggerRedraw( final int col, final int line, final int cols, final int lines ) {
    int fontWidthMult = 1;
    for ( int i = line; i < line + lines
                        && i < this.lineWidthMode.length ; i++ ) {
      if ( this.lineWidthMode[i] == LineWidthMode.DOUBLE ) {
        fontWidthMult = 2;
        break;
      }
    }
    final int finalFontWidthMult = fontWidthMult;
    Display.getDefault().syncExec( new Runnable() {
      public void run () {
        if ( !isDisposed() ) {
          redraw( col * getFontWidth() * finalFontWidthMult,
                  line * getFontHeigth(),
                  cols * getFontWidth() * finalFontWidthMult,
                  lines * getFontHeigth(),
                  false );
        }
      }
    } );
  }

  private void triggerRedraw() {
    Display.getDefault().syncExec( new Runnable() {
      public void run () {
        redraw();
      }
    } );
  }

  private void parseEscSequence() throws IOException {
    int ch = read();
    List<Integer> params = new LinkedList<Integer>();
    switch(ch) {
      case '[':
        do {
          params.add( new Integer(readNumber()) );
          ch = read();
        }while(ch == ';');
        executeControlSequence( listToArray(params), ch );
        break;
      case ']':
        executeOperatingSystemCommand();
        break;
      case '#':
        executeDecCommand();
        break;
      case '(': // SCS - Select Character Set (G0)
        selectCharacterSet(0);
        break;
      case ')': // SCS - Select Character Set (G1)
        selectCharacterSet(1);
        break;
      case '*': // SCS - Select Character Set (G2)
        selectCharacterSet(2);
        break;
      case '+': // SCS - Select Character Set (G3)
        selectCharacterSet(3);
        break;
      case 'Z': // DECID - Identify Terminal
        deviceAttributes( new int[0] );
        break;
      case '=': // DECKPAM - Keypad Application Mode
        this.keyPadMode = KeyPadMode.APPLICATION;
        break;
      case '>': // DECKPNM - Keypad Numeric Mode
        this.keyPadMode = KeyPadMode.NUMERIC;
        break;
      case '7': // DECSC - Save Cursor
        saveCursor();
        break;
      case '8': // DECRC - Restore Cursor
        restoreCursor();
        break;
      case 'H': // HTS - Horizontal Tabulation Set
        horizontalTabulationSet();
        break;
      case 'D': // IND - Index
        index();
        break;
      case 'E': // NEL - Next Line
        nextLine();
        break;
      case 'M': // RI - Reverse Index
        reverseIndex();
        break;
      case 'c': // RIS - Reset To Initial State;
        reset();
        break;
      // ------------------ VT52 commands ------------------
      // TODO implement a VT52 mode
/*      case 'A': // Cursor Up
        System.out.println( "Cursor Up not implemented" );
        break;
      case 'B': // Cursor Down
        System.out.println( "Cursor Down not implemented" );
        break;
      case 'C': // Cursor Right
        System.out.println( "Cursor Right not implemented" );
        break;
      //case 'D': // Cursor Left
        //System.out.println( "Cursor Left not implemented" );
        //break;
      case 'F': // Enter Graphics Mode
        System.out.println( "Enter Graphics Mode not implemented" );
        break;
      case 'G': // Exit Graphics Mode
        System.out.println( "Exit Graphics Mode not implemented" );
        break;
      //case 'H': // Cursor to Home
        //System.out.println( "Cursor to Home not implemented" );
        //break;
      case 'I': // Reverse Line Feed
        System.out.println( "Reverse Line Feed not implemented" );
        break;
      case 'J': // Erase to End of Screen
        System.out.println( "Erase to End of Screen not implemented" );
        break;
      case 'K': // Erase to End of Line
        System.out.println( "Erase to End of Line not implemented" );
        break;
      case 'Y': // Direct Cursor Address
        System.out.println( "Direct Cursor Address not implemented" );
        break;
      case '<': // Enter ANSI Mode
        System.out.println( "Enter ANSI Mode not implemented" );
        break;*/
        // ---------------- end VT52 commands ----------------
      default:
        Activator.logMessage( IStatus.WARNING,
                              Messages.formatMessage( "Terminal.unknownEscSeq", //$NON-NLS-1$
                                                      new Character( (char) ch ) ) );
        break;
    }
  }

  private void horizontalTabulationSet() {
    Integer cursorCol = new Integer( this.cursor.col );
    if ( !this.tabulatorPositons.contains( cursorCol ) ) {
      this.tabulatorPositons.add( cursorCol );
    }
  }

  private void selectCharacterSet( final int charSetNr ) throws IOException {
    int ch = read();
    CharSet charSet = CharSet.USASCII;
    switch (ch) {
      case 'A':
        Activator.logMessage( IStatus.WARNING,
                              Messages.getString( "Terminal.ukCharSetNotSupported" ) ); //$NON-NLS-1$
        break;
      case 'B':
        charSet = CharSet.USASCII;
        break;
      case '0':
        charSet = CharSet.SPECIAL;
        break;
      case '1':
        Activator.logMessage( IStatus.WARNING,
                              Messages.getString( "Terminal.alternateStdCharSetNotSupported" ) ); //$NON-NLS-1$
        break;
      case '2':
        Activator.logMessage( IStatus.WARNING,
                              Messages.getString( "Terminal.alternateSpecialCharSetNotSupported" ) ); //$NON-NLS-1$
        break;
      default:
        Activator.logMessage( IStatus.WARNING,
                              Messages.getString( "Terminal.unknownCharSet" ) ); //$NON-NLS-1$
        break;
    }
    this.charSetG[charSetNr] = charSet;
  }

  private void nextLine() {
    int oldCol = this.cursor.col; 
    this.cursor.col = 0;
    triggerRedraw( oldCol, this.cursor.line, 1, 1 );
    index();
  }

  private void index() {
    if ( this.cursor.line < this.bottomMargin ) {
      int oldLine = this.cursor.line;
      this.cursor.line++;
      triggerRedraw( this.cursor.col, oldLine, 1, 1 );
      triggerRedraw( this.cursor.col, this.cursor.line, 1, 1 );
    }
    else scrollUp();
  }

  private void reverseIndex() {
    if ( this.cursor.line > this.topMargin ) {
      int oldLine = this.cursor.line;
      this.cursor.line--;
      triggerRedraw( this.cursor.col, oldLine, 1, 1 );
      triggerRedraw( this.cursor.col, this.cursor.line, 1, 1 );
    }
    else scrollDown();
  }

  private void saveCursor() {
    this.savedCursor = new Cursor( this.cursor );
  }

  private void restoreCursor() {
    int oldCol = this.cursor.col;
    int oldLine = this.cursor.line;
    this.cursor = this.savedCursor;
    triggerRedraw( oldCol, oldLine, 1, 1 );
    triggerRedraw( this.cursor.col, this.cursor.line, 1, 1 );
  }

  private static int[] listToArray( final List<Integer> list ) {
    int[] array = new int[list.size()];
    for( int i = 0; i < list.size(); i++ ) {
      array[i] = list.get( i ).intValue();
    }
    return array;
  }

  private void executeDecCommand() throws IOException {
    int ch = read();
    switch (ch) {
      case '3': // DECDHL - Double Height Line (Top Half)
        doubleHeightLine( LineHeightMode.DOUBLE_TOP );
        break;
      case '4': // DECDHL - Double Height Line (Bottom Half)
        doubleHeightLine( LineHeightMode.DOUBLE_BOTTOM );
        break;
      case '5': // DECSWL - Single-width Line
        doubleWidthLine( LineWidthMode.NORMAL );
        break;
      case '6': // DECDWL - Double-Width Line
        doubleWidthLine( LineWidthMode.DOUBLE );
        break;
      case '8': // DECALN - Screen Alignment Display
        screenAlignmentDisplay();
        break;
      default:
        Activator.logMessage( IStatus.WARNING, 
                              Messages.formatMessage( "Terminal.unknownDecCommand", //$NON-NLS-1$
                                                       new Character( (char) ch ) ) );
    }
  }

  private void doubleWidthLine( final LineWidthMode mode ) {
    this.lineHeightMode[ this.cursor.line ] = LineHeightMode.NORMAL;
    this.lineWidthMode[ this.cursor.line ] = mode;
    if (mode == LineWidthMode.DOUBLE && this.cursor.col > this.numCols / 2) {
      this.cursor.col = this.numCols / 2;
    }
    triggerRedraw( 0, this.cursor.line, this.numCols, 1 );
  }

  private void doubleHeightLine( final LineHeightMode mode ) {
    if ( mode == LineHeightMode.DOUBLE_TOP || mode == LineHeightMode.DOUBLE_BOTTOM ) {
      this.lineWidthMode[ this.cursor.line ] = LineWidthMode.DOUBLE;
    }
    this.lineHeightMode[ this.cursor.line ] = mode;
    triggerRedraw( 0, this.cursor.line, this.numCols, 1 );
  }

  private void screenAlignmentDisplay() {
    for( int i = 0; i < this.lineHeightMode.length; i++ ) {
      this.lineHeightMode[ i ] = LineHeightMode.NORMAL;
      this.lineWidthMode[ i ] = LineWidthMode.NORMAL;
    }
    for( Char[] line : this.screenBuffer ) {
      for( Char character : line ) {
        character.ch = 'E';
      }
    }
    triggerRedraw();
  }

  private void executeOperatingSystemCommand() throws IOException {
    int commandNr = readNumber();
    int colorNr;
    Color color;

    if (read() != ';') {
      Activator.logMessage( IStatus.WARNING, 
                            Messages.getString( "Terminal.invalidOsCommand" ) ); //$NON-NLS-1$
    } else switch (commandNr) {
      case 0: // Set Window Title
        this.windowTitle = readString();
        if ( this.listener != null ) this.listener.windowTitleChanged( this.windowTitle ); 
        break;
      case 4:
        colorNr = readNumber();
        if (read() != ';') {
          Activator.logMessage( IStatus.WARNING,
                                Messages.getString( "Terminal.invalidOsCommand" ) ); //$NON-NLS-1$
        } else {
          color = parseColorString( readString() );
          this.systemColors[ colorNr ] = color;
        }
        break;
      default:
        Activator.logMessage( IStatus.WARNING,
                              Messages.formatMessage( "Terminal.unknownOsCommand", //$NON-NLS-1$
                                                      new Integer( commandNr ) ) );
        break;
    }
  }

  private Color parseColorString( final String colorString ) {
    Color color = null;
    if (colorString.startsWith( "rgb:" )) { //$NON-NLS-1$
      StringTokenizer tokenizer = new StringTokenizer(colorString.substring( 4 ),"/"); //$NON-NLS-1$
      if (tokenizer.countTokens() == 3) {
        int r = Integer.parseInt( tokenizer.nextToken(), 16 );
        int g = Integer.parseInt( tokenizer.nextToken(), 16 );
        int b = Integer.parseInt( tokenizer.nextToken(), 16 );
        color = new Color( Display.getDefault(), r, g, b );
      }
    } 
    if (color == null) {
      color = this.defaultFgColor;
      Activator.logMessage( IStatus.WARNING,
                            Messages.getString( "Terminal.unknownColorString" ) ); //$NON-NLS-1$
    }
    return color;
  }

  private String readString( /*final char[] terminatingChars*/ ) throws IOException {
    final char[] terminatingChars = new char[] { BEL, SWT.ESC }; // sorted array
    StringBuilder buffer = new StringBuilder();
    char ch = (char) read();
    while( Arrays.binarySearch( terminatingChars, ch ) < 0  ) {
      buffer.append( ch );
      ch = (char) read();
    }
    if ( ch == SWT.ESC ) {
      if ( read() != '\\' ) {
        Activator.logMessage( IStatus.WARNING,
                              Messages.getString( "Terminal.stringTerminatorExpected" ) ); //$NON-NLS-1$
      }
    }
    return buffer.toString();
  }

  private void executeControlSequence(final int[] params, final int command) throws IOException {
    switch (command) {
      case 'D': // CUB - Cursor Backward
        cursorBackward( params );
        break;
      case 'B': // CUD - Cursor Down
        cursorDown( params );
        break;
      case 'C': // CUF - Cursor Forward
        cursorForward( params );
        break;
      case 'H': // CUP - Cursor Position
        cursorPosition( params );
        break;
      case 'A': // CUU - Cursor Up
        cursorUp( params );
        break;
      case 'c': // DA - Device Attributes
        deviceAttributes( params );
        break;
      case 'q': // DECLL - Load LEDS
        loadLeds( params );
        break;
      case 'x': // DECREPTPARM - Report Terminal Parameters / DECREQTPARM - Request Terminal Parameters
        requestTerminalParameters( params );
        break;
      case 'r': // DECSTBM - Set Top and Bottom Margins
        setTopAndBottomMargins( params );
        break;
      case 'y': // DECTST - Invoke Confidence Test
        Activator.logMessage( IStatus.WARNING,
                              Messages.getString( "Terminal.invokeConfidenceTestNotImplemented" ) ); //$NON-NLS-1$
        break;
      case 'n': // DSR - Device Status Report
        deviceStatusReport( params );
        break;
      case 'J': // ED - Erase In Display
        eraseInDisplay( params );
        break;
      case 'K': // EL - Erase In Line
        eraseInLine( params );
        break;
      case 'f': // HVP - Horizontal and Vertical Position
        cursorPosition( params );  // same as CUP - Cursor Position
        break;
      case 'l': // RM - Reset Mode
        resetMode( params );
        break;
      case 'm': // SGR - Select Graphic Rendition
        selectGraphicRendition( params );
        break;
      case 'h': // SM - Set Mode
        setMode( params );
        break;
      case 'g': // TBC - Tabulation Clear
        tabulationClear( params );
        break;
      // Xterm (and some VTs >100)
      case 'd': // VPA - Vertical Line Position Absolute
        verticalLinePositionAbsolute( params );
        break;
      case 'X': // ECH - Erase Character
        eraseCharacter( params );
        break;
      case 'P': // DCH - Delete Character
        deleteCharacter( params );
        break;
      case 'G': // CHA - Cursor Horizontal Absolute
        cursorHorizontalAbsolute( params );
        break;
      default:
        Activator.logMessage( IStatus.WARNING,
                              Messages.formatMessage("Terminal.unknownCtrlSeq", //$NON-NLS-1$
                                                     new Character( (char) command ) ) );
        break;
    }
  }

  private void deviceStatusReport( final int[] params ) throws IOException {
    final byte[] response = { SWT.ESC, '[', '0', 'n' }; // terminal ok
    
    int val = 0;
    if (params.length != 0) val = params[0];
    switch ( val ) {
      case 5:
        this.output.write( response );
        this.output.flush();
        break;
      case 6:
        reportCursorPosition();
        break;
      default:
        Activator.logMessage( IStatus.WARNING,
                              Messages.getString( "Terminal.invalidDeviceStatusReportParam" ) ); //$NON-NLS-1$
        break;
    }
  }
  
  private void reportCursorPosition() throws IOException {
    this.output.write( SWT.ESC );
    this.output.write( '[' );
    this.output.write( Integer.toString( this.cursor.line + 1 ).getBytes( "ASCII" ) ); //$NON-NLS-1$
    this.output.write( ';' );
    this.output.write( Integer.toString( this.cursor.col + 1 ).getBytes( "ASCII" ) ); //$NON-NLS-1$
    this.output.write( 'R' );
    this.output.flush();
  }

  private void requestTerminalParameters( final int[] params ) throws IOException {
    // Parity none, 8 bits, xmitspeed 38400, recvspeed 38400,
    // clock multiplier 1, STP option flags 0
    byte[] response = {
      SWT.ESC, '[', '2', ';', '1', ';', '1', ';', '1', '2', '8',
      ';', '1', '2', '8', ';', '1', ';', '0', 'x'
    };

    if (params.length > 0 && params[0] != 0 && params[0] != 1 ) {
      Activator.logMessage( IStatus.WARNING,
                            Messages.getString( "Terminal.unknownTermParamReq" ) ); //$NON-NLS-1$
    } else {
      if ( params[0] == 1 ) response[2] = '3';
      this.output.write( response );
      this.output.flush();
    }
  }

  private void eraseCharacter( final int[] params ) {
    int val = 1;
    int colsInLine = numColsInLine( this.cursor.line );
    if (params.length != 0) val = params[0];
    if (val == 0) val = 1;
    for ( int i = this.cursor.col; ( i < ( this.cursor.col + val ) ) && ( i < colsInLine ); i++ ) {
      this.screenBuffer[ this.cursor.line ][ i ].erase( this.defaultFgColor, this.defaultBgColor );
      this.screenBuffer[ this.cursor.line ][ i ].bgColor = this.cursor.bgColor;
    }
    triggerRedraw( this.cursor.col, this.cursor.line, val, 1 );
  }

  private void deleteCharacter( final int[] params ) {
    int val = 1;
    int colsInLine = numColsInLine( this.cursor.line );
    if ( params.length != 0 ) val = params[ 0 ];
    if ( val == 0 ) val = 1;
    if ( val > ( colsInLine - this.cursor.col ) ) val = colsInLine - this.cursor.col;
    int colsToMove = colsInLine - this.cursor.col - val;
    for ( int i = this.cursor.col; i < ( this.cursor.col + colsToMove ); i++ ) {
      this.screenBuffer[ this.cursor.line ][ i ] = this.screenBuffer[ this.cursor.line ][ i + val ];
    }
    for ( int i = ( this.cursor.col + colsToMove ); i < colsInLine; i++ ) {
      this.screenBuffer[ this.cursor.line ][ i ] = new Char( this.defaultFgColor, this.defaultBgColor );
    }
    triggerRedraw( this.cursor.col, this.cursor.line, colsInLine - this.cursor.col, 1 );
  }

  private void setMode( final int[] params ) {
    for( int param : params ) {
      switch(param) {
        case 1: // Application Cursor Key Mode
          this.cursorKeyMode = CursorKeyMode.APPLICATION;
          break;
        case 3: // 132 Column Mode
          this.cursor.reset( this.defaultFgColor, this.defaultBgColor );
          this.tabulatorPositons.clear();
          eraseScreen(); 
          Display.getDefault().syncExec(new Runnable() {
            public void run () {
              setSize( 132 * getFontWidth(), 24 * getFontHeigth() );
            }
          });
          break;
        case 4: // Smooth Scrolling Mode
          // TODO implement smooth scrolling
          this.scrollMode = ScrollMode.SMOOTH;
          break;
        case 5: // Reverse Screen Mode
          this.reverseScreenMode = true;
          triggerRedraw();
          break;
        case 6: // Relative Origin Mode
          setOriginMode( OriginMode.RELATIVE );
          break;
        case 7: // Wraparound On
          this.wraparound = true;
          break;
        case 8: // Auto repeat On
          // XXX check if auto repeat keyboard events in SWT are possible?
          Activator.logMessage( IStatus.WARNING,
                                Messages.getString("Terminal.autoRepeatOnNotSupported") ); //$NON-NLS-1$
          break;
        case 9: // Interlace On
          Activator.logMessage( IStatus.WARNING,
                                Messages.getString( "Terminal.interlaceOnNotSupported" ) ); //$NON-NLS-1$
          break;
        case 20: // New Line Mode
          this.newLineMode = true;
          break;
        case 45: // Reverse Wraparound On
          this.reverseWraparound = true;
          break;
        default:
          Activator.logMessage( IStatus.WARNING,
                                Messages.formatMessage("Terminal.unknownSetModeParam", //$NON-NLS-1$
                                                       new Integer( param ) ) );
          break;
      }
    }
  }

  private void resetMode( final int[] params ) {
    for ( int param :  params ) {
      switch(param) {
        case 1: // Cursor Cursor Key Mode
          this.cursorKeyMode = CursorKeyMode.CURSOR;
          break;
        case 2:
          // XXX VT52 Mode not supported
          Activator.logMessage( IStatus.WARNING,
                                Messages.getString("Terminal.vt52ModeNotSupported") ); //$NON-NLS-1$
          break;
        case 3: // 80 Column Mode
          Display.getDefault().syncExec(new Runnable() {
            public void run () {
              setSize( 80 * getFontWidth(), 24 * getFontHeigth() );
            }
          });
          this.cursor.reset( this.defaultFgColor, this.defaultBgColor );
          this.tabulatorPositons.clear();
          eraseScreen(); 
          break;
        case 4:
          this.scrollMode = ScrollMode.JUMP;
          break;
        case 5: // Normal Screen Mode
          this.reverseScreenMode = false;
          triggerRedraw();
          break;
        case 6: // Absolute Origin Mode
          setOriginMode( OriginMode.ABSOLUTE );
          break;
        case 7: // Wraparound Off
          this.wraparound = false;
          break;
        case 8: // Auto repeat Off
          // XXX check of auto repeat keyboard events in SWT possible?
          Activator.logMessage( IStatus.WARNING,
                                Messages.getString("Terminal.autoRepeatOffNotSupported") ); //$NON-NLS-1$
          break;
        case 9: // Interlace Off
          Activator.logMessage( IStatus.WARNING,
                                Messages.getString( "Terminal.interlaceOffNotSupported" ) ); //$NON-NLS-1$
          break;
        case 20: // Line Feed Mode
          this.newLineMode = false;
          break;
        case 45: // Reverse Wraparound Off
          this.reverseWraparound = false;
          break;
        default:
          Activator.logMessage( IStatus.WARNING,
                                Messages.formatMessage( "Terminal.unknownResetModeParam", //$NON-NLS-1$
                                                        new Integer( param ) ) );
          break;
      }
    }
  }

  private void tabulationClear( final int[] params ) {
    Integer cursorCol = new Integer( this.cursor.col );
    int val = 0;
    if ( params.length > 0 ) val = params[ 0 ];
    switch (val) {
      case 0:
        this.tabulatorPositons.remove( cursorCol );
        break;
      case 3:
        this.tabulatorPositons.clear();
        break;
      default:
        Activator.logMessage( IStatus.WARNING,
                              Messages.formatMessage( "Terminal.unknownTabClearParam", //$NON-NLS-1$
                                                      new Integer( val ) ) );
        break;
    }
  }

  private void reset() {
    this.cursor.reset( this.defaultFgColor, this.defaultBgColor );
    this.tabulatorPositons.clear();
    this.wraparound = false;
    this.reverseWraparound = false;
    this.reverseScreenMode = false;
    this.newLineMode = false;
    this.keyPadMode = KeyPadMode.NUMERIC;
    for( int i = 0; i < this.charSetG.length; i++ ) this.charSetG[i] = CharSet.USASCII;
    this.topMargin = 0;
    this.bottomMargin = this.numLines - 1;
    for ( int i = 0; i < this.leds.length; i++ ) this.leds[i] = false;
    setOriginMode( OriginMode.ABSOLUTE );
    eraseScreen(); 
  }

  private void setOriginMode( final OriginMode mode ) {
    int oldLine = this.cursor.line;
    int oldCol = this.cursor.col;
    if ( mode == OriginMode.ABSOLUTE ) {
      this.cursor.col = 0;
      this.cursor.line = 0;
    } else {
      this.cursor.col = 0;
      this.cursor.line = this.topMargin;      
    }
    this.originMode = mode;
    triggerRedraw( oldCol, oldLine, 1, 1 );
    triggerRedraw( this.cursor.col, this.cursor.line, 1, 1 );
  }

  private void scrollUp() {
    Char[] tmp = this.screenBuffer[ this.topMargin ];
    for( int i = this.topMargin; i < this.bottomMargin; i++ ) {
      this.lineHeightMode[ i ] = this.lineHeightMode[ i + 1 ];
      this.lineWidthMode[ i ] = this.lineWidthMode[ i + 1 ];
      this.screenBuffer[ i ] = this.screenBuffer[ i + 1 ];
    }
    this.screenBuffer[ this.bottomMargin ] = tmp;
    eraseLine( this.bottomMargin );
    this.lineWidthMode[ this.bottomMargin ] = LineWidthMode.NORMAL;
    this.lineHeightMode[ this.bottomMargin ] = LineHeightMode.NORMAL;
    Display.getDefault().syncExec(new Runnable() {
      public void run () {
        Terminal.this.terminalPainter.scrollUp( Terminal.this.topMargin,
                                                Terminal.this.bottomMargin );
        Display.getDefault().update();
      }
    });
    triggerRedraw( 0, this.bottomMargin, this.numCols, 1 );
    // repaint old cursor position
    triggerRedraw( this.cursor.col, this.cursor.line - 1, 1, 1 );
  }

  private void scrollDown() {
    Char[] tmp = this.screenBuffer[ this.bottomMargin ];
    for( int i = this.bottomMargin; i > this.topMargin; i-- ) {
      this.lineHeightMode[ i ] = this.lineHeightMode[ i - 1 ];
      this.lineWidthMode[ i ] = this.lineWidthMode[ i - 1 ];
      this.screenBuffer[ i ] = this.screenBuffer[ i - 1 ];
    }
    this.screenBuffer[ this.topMargin ] = tmp;
    eraseLine( this.topMargin );
    this.lineWidthMode[ this.topMargin ] = LineWidthMode.NORMAL;
    this.lineHeightMode[ this.topMargin ] = LineHeightMode.NORMAL;
    Display.getDefault().syncExec(new Runnable() {
      public void run () {
        Terminal.this.terminalPainter.scrollDown( Terminal.this.topMargin,
                                                  Terminal.this.bottomMargin );
        Display.getDefault().update();
      }
    });
    triggerRedraw( 0, this.topMargin, this.numCols, 1 );
    // repaint old cursor position
    triggerRedraw( this.cursor.col, this.cursor.line + 1, 1, 1 );
  }

  private void setTopAndBottomMargins( final int[] params ) {
    int top = 0;
    int bottom = this.numLines;
    
    if ( params.length > 0 ) top = params[ 0 ];
    if ( params.length > 1 ) bottom = params[ 1 ];
    if ( top > 0 ) top--;
    if ( bottom > 0 ) bottom--;
    if ( top >= bottom ) {
      Activator.logMessage( IStatus.WARNING,
                            Messages.getString( "Terminal.topLargerEqualBottom" ) ); //$NON-NLS-1$
    } else if ( bottom >= this.numLines ) {
      Activator.logMessage( IStatus.WARNING,
                            Messages.formatMessage( "Terminal.bottomOutOfScreen", //$NON-NLS-1$
                                                    new Integer( bottom ),
                                                    new Integer( this.numLines ) ) );
    } else {
      this.topMargin = top;
      this.bottomMargin = bottom;
      cursorPosition( new int[0] );
    }
  }

  private void selectGraphicRendition( final int[] params ) {
    int val = 0;
    int idx = 0;
    do {
      if (params.length > idx) val = params[idx];
      if (val >= 30 && val <= 37) { // foreground colors // TODO change color when bold is changed
        if ( this.cursor.bold ) this.cursor.fgColor = this.systemColors[ val - 30 + 8 ];
        else this.cursor.fgColor = this.systemColors[ val - 30 ];
      } else if ( val >= 40 && val <= 47 ) { // background colors
        this.cursor.bgColor = this.systemColors[ val - 40 ];
      } else switch( val ) {
        // VT100
        case 0:
          this.cursor.bold = false;
          this.cursor.underscore = false;
          this.cursor.blink = false;
          this.cursor.negative = false;
          this.cursor.italics = false;
          this.cursor.strikethrough = false;
          this.cursor.fgColor = this.defaultFgColor;
          this.cursor.bgColor = this.defaultBgColor;
          break;
        case 1:
          this.cursor.bold = true;
          break;
        case 4:
          this.cursor.underscore = true;
          break;
        case 5:
          this.cursor.blink = true;
          break;
        case 7:
          this.cursor.negative = true;
          break;
        // ANSI
        case 3:
          this.cursor.italics = true;
          break;
        case 9:
          this.cursor.strikethrough = true;
          break;
        case 22:
          this.cursor.bold = false;
          break;
        case 23:
          this.cursor.italics = false;
          break;
        case 24:
          this.cursor.underscore = false;
          break;
        case 27:
          this.cursor.negative = false;
          break;
        case 29:
          this.cursor.strikethrough = false;
          break;
        case 38:
          if ( params.length >= idx + 3 && params[ idx + 1 ] == 5 && params[ idx + 2 ] < 256 ) {
            this.cursor.fgColor = this.systemColors[ params[ idx + 2 ] ];
            idx += 2;
          } else {
            Activator.logMessage( IStatus.WARNING,
                                  Messages.getString( "Terminal.invalidColorParams" ) ); //$NON-NLS-1$
          }
          break;
        case 39:
          this.cursor.fgColor = this.defaultFgColor;
          break;
        case 48:
          if (params.length >= idx + 3 && params[idx + 1] == 5 && params[idx + 2] < 256 ) {
            this.cursor.bgColor = this.systemColors[ params[ idx + 2 ] ];
            idx += 2;
          } else {
            Activator.logMessage( IStatus.WARNING,
                                  Messages.getString( "Terminal.invalidColorParams" ) ); //$NON-NLS-1$
          }
          break;
        case 49:
          this.cursor.bgColor = this.defaultBgColor;
          break;
        default:
          Activator.logMessage( IStatus.WARNING,
                                Messages.formatMessage( "Terminal.unknownGraphRendParam", //$NON-NLS-1$
                                                        new Integer( val ) ));
          break;
      }
      idx++;
    } while ( idx < params.length ); 
  }

  private void eraseScreen() {
    final int[] eraseScreen = { 2 };
    eraseInDisplay( eraseScreen );
    resetLineModes();
  }

  private void resetLineModes() {
    for( int i = 0; i < this.lineHeightMode.length; i++ ) {
      this.lineHeightMode[ i ] = LineHeightMode.NORMAL;
      this.lineWidthMode[ i ] = LineWidthMode.NORMAL;
    }
  }

  private void eraseInDisplay( final int[] params ) {
    int val = 0;
    if ( params.length > 0 ) val = params[ 0 ];
    switch ( val ) {
      case 0: // From cursor to end of screen
        eraseInLine( params );
        for( int i = this.cursor.line+1; i < this.screenBuffer.length; i++ ) {
          eraseLine( i );
        }    
        triggerRedraw( 0, this.cursor.line+1, this.numCols,
                       this.numLines - this.cursor.line-1 );
        break;
      case 1: // From beginning of screen to cursor
        eraseInLine( params );
        for( int i = 0; i < this.cursor.line; i++) {
          eraseLine( i );
        }    
        triggerRedraw( 0, 0, this.numCols, this.cursor.line );
        break;
      case 2: // Entire screen
        for( int i = 0; i < this.screenBuffer.length; i++ ) {
          eraseLine( i );
        }
        triggerRedraw();
        break;
      default:
        Activator.logMessage( IStatus.WARNING,
                              Messages.formatMessage("Terminal.unknownEraseInDispParam", //$NON-NLS-1$
                                                     new Integer( val ) ) );
        break;
    }
  }

  private void eraseInLine( final int[] params ) {
    int val = 0;
    if (params.length > 0) val = params[0];
    switch (val) {
      case 0: // From cursor to end of line
        for( int i = this.cursor.col; i < this.screenBuffer[ this.cursor.line ].length; i++ ) {
         this.screenBuffer[ this.cursor.line ][ i ].erase( this.defaultFgColor, this.defaultBgColor );
        }
        triggerRedraw( this.cursor.col, this.cursor.line, this.numCols-this.cursor.col, 1 );
        break;
      case 1: // From beginning of line to cursor
        for( int i = 0; i <= this.cursor.col; i++ ) {
          this.screenBuffer[ this.cursor.line ][ i ].erase( this.defaultFgColor, this.defaultBgColor );
        }
        triggerRedraw( 0, this.cursor.line, this.cursor.col+1, 1 );
        break;
      case 2: // Entire line containing cursor
        eraseLine( this.cursor.line );
        triggerRedraw( 0, this.cursor.line, this.numCols, 1 );
        break;
      default:
        Activator.logMessage( IStatus.WARNING,
                              Messages.formatMessage( "Terminal.unknownEraseInLineParam", //$NON-NLS-1$
                                                      new Integer( val ) ) );
        break;
    }
  }

  private void eraseLine( final int lineNr ) {
    for( Char character : this.screenBuffer[ lineNr ] ) {
      character.erase( this.defaultFgColor, this.defaultBgColor );
    }
  }

  private void loadLeds( final int[] params ) {
    int val = 0;
    if ( params.length > 0 ) val = params[ 0 ];
    if ( val == 0 ) {
      for ( int i = 0; i < this.leds.length; i++ ) this.leds[i] = false;
    } else if ( val < this.leds.length ) {
      this.leds[ val-1 ] = true;
    } else {
      Activator.logMessage( IStatus.WARNING,
                            Messages.formatMessage("Terminal.invalidLoadLedsParam", //$NON-NLS-1$
                                                   new Integer( val ) ) );
    }
  }

  private void cursorBackward(final int[] params) {
    int val = 1;
    int oldCol = this.cursor.col;
    int oldLine = this.cursor.line;
    if ( params.length != 0 ) val = params[ 0 ];
    if ( val == 0 ) val = 1;
    if ( this.reverseWraparound /*|| cursor.col == 0*/ ) { // XXX I'am not so sure if this is correct behavior
      while ( this.cursor.col - val < 0 ) {
        if (this.cursor.line != 0 ) {
          val -= this.cursor.col + 1;
          this.cursor.line--;
          this.cursor.col = numColsInLine( this.cursor.line ) - 1;
        } else { 
          this.cursor.col = 0;
          val = 0;
          break;
        }
      }
      this.cursor.col -= val;
    } else {
      if ( this.cursor.col - val < 0 ) this.cursor.col = 0;
      else this.cursor.col -= val;
    }
    triggerRedraw( oldCol, oldLine, 1, 1 );
    triggerRedraw( this.cursor.col, this.cursor.line, 1, 1 );
  }

  private void cursorDown(final int[] params) {
    int val = 1;
    int oldLine = this.cursor.line;
    if ( params.length != 0 ) val = params[ 0 ];
    if ( val == 0 ) val = 1;
    if ( this.cursor.line + val >= this.bottomMargin ) this.cursor.line = this.bottomMargin - 1;
    else this.cursor.line += val;
    triggerRedraw( this.cursor.col, oldLine, 1, 1 );
    triggerRedraw( this.cursor.col, this.cursor.line, 1, 1 );
  }

  private void cursorForward(final int[] params) {
    int val = 1;
    int oldCol = this.cursor.col;
    if ( params.length != 0 ) val = params[ 0 ];
    if ( val == 0 ) val = 1;
    if ( this.cursor.col + val >= this.numCols ) this.cursor.col = this.numCols - 1;
    else this.cursor.col += val;
    triggerRedraw( oldCol, this.cursor.line, 1, 1 );
    triggerRedraw( this.cursor.col, this.cursor.line, 1, 1 );
  }

  private void cursorUp(final int[] params) {
    int val = 1;
    int oldLine = this.cursor.line;
    if ( params.length != 0 ) val = params[ 0 ];
    if ( val == 0 ) val = 1;
    if ( this.cursor.line - val < this.topMargin ) this.cursor.line = this.topMargin;
    else this.cursor.line -= val;
    triggerRedraw( this.cursor.col, oldLine, 1, 1 );
    triggerRedraw( this.cursor.col, this.cursor.line, 1, 1 );
  }

  private void verticalLinePositionAbsolute(final int[] params) {
    int val = 1;
    int oldLine = this.cursor.line;
    if ( params.length != 0 ) val = params[ 0 ];
    if ( val == 0 ) val = 1;
    if ( val >= this.numLines ) val = this.numLines - 1;
    this.cursor.line = val;
    triggerRedraw( this.cursor.col, oldLine, 1, 1 );
    triggerRedraw( this.cursor.col, this.cursor.line, 1, 1 );
  }

  private void cursorHorizontalAbsolute( final int[] params ) {
    int val = 1;
    int oldCol = this.cursor.col;
    if ( params.length != 0 ) val = params[ 0 ];
    if ( val == 0 ) val = 1;
    if ( val >= this.numCols ) val = this.numCols - 1;
    this.cursor.col = val;
    triggerRedraw( oldCol, this.cursor.col, 1, 1 );
    triggerRedraw( this.cursor.col, this.cursor.line, 1, 1 );
  }

  private void cursorPosition(final int[] params) {
    int col = 0;
    int line = 0;
    int lines = this.numLines;
    if ( params.length > 0 ) line = params[ 0 ];
    if ( params.length > 1 ) col = params[ 1 ];
    if ( line > 0 ) line--;
    if ( col > 0 ) col--;
    if ( this.originMode == OriginMode.RELATIVE ) {
      line += this.topMargin;
      lines = this.bottomMargin;
    }
    if ( line >= lines ) {
      line = lines - 1;
      Activator.logMessage( IStatus.WARNING,
                            Messages.getString( "Terminal.cursorPosOutOfRangeLine" ) ); //$NON-NLS-1$
    }
    if ( col >= this.numCols ) {
      col = this.numCols - 1;
      Activator.logMessage( IStatus.WARNING, 
                            Messages.getString( "Terminal.cursorPosOutOfRangeCol" ) ); //$NON-NLS-1$
    }
    int oldLine = this.cursor.line;
    int oldCol = this.cursor.col;
    this.cursor.line = line;
    this.cursor.col = col;
    triggerRedraw( oldCol, oldLine, 1, 1 );
    triggerRedraw( this.cursor.col, this.cursor.line, 1, 1 );
  }

  private void deviceAttributes( final int[] params) throws IOException {
    final byte[] response = { SWT.ESC, '[', '?', '1', ';', '2', 'c' };  // 2 = VT100 with AVO
    
    if ( params.length > 0 && params[ 0 ] != 0 ) {
      Activator.logMessage( IStatus.WARNING,
                            Messages.getString( "Terminal.unknownDevAttribReq" ) ); //$NON-NLS-1$
    } else {
      this.output.write( response );
      this.output.flush();
    }
  }

  private int readNumber() throws IOException {
    int ch = read();
    int val = 0;
    while ( ( ch >= '0' && ch <= '9' ) || ch =='?' ) {
      if ( ch != '?' ) {  // XXX meaning of '?' character ?!?
        val *= 10;
        val += ch - '0';
      }
      ch = read();
    }
    this.input.unread( ch );
    return val;
  }

  private int read() throws IOException {
     int val = this.input.read();
     if ( val == -1 ) this.running = false;
     return val;
  }

  private void initSystemColorTable() {
    Display dpy = Display.getDefault();
    this.defaultBgColor = Display.getDefault().getSystemColor( SWT.COLOR_BLACK );
    this.defaultFgColor = Display.getDefault().getSystemColor( SWT.COLOR_GRAY );
    this.systemColors = new Color[256];
    this.systemColors[ 0 ] = dpy.getSystemColor( SWT.COLOR_BLACK );
    this.systemColors[ 1 ] = dpy.getSystemColor( SWT.COLOR_DARK_RED );
    this.systemColors[ 2 ] = dpy.getSystemColor( SWT.COLOR_DARK_GREEN );
    this.systemColors[ 3 ] = dpy.getSystemColor( SWT.COLOR_DARK_YELLOW );
    this.systemColors[ 4 ] = dpy.getSystemColor( SWT.COLOR_DARK_BLUE );
    this.systemColors[ 5 ] = dpy.getSystemColor( SWT.COLOR_DARK_MAGENTA );
    this.systemColors[ 6 ] = dpy.getSystemColor( SWT.COLOR_DARK_CYAN );
    this.systemColors[ 7 ] = dpy.getSystemColor( SWT.COLOR_GRAY );
    this.systemColors[ 8 ] = dpy.getSystemColor( SWT.COLOR_DARK_GRAY );
    this.systemColors[ 9 ] = dpy.getSystemColor( SWT.COLOR_RED );
    this.systemColors[ 10 ] = dpy.getSystemColor( SWT.COLOR_GREEN );
    this.systemColors[ 11 ] = dpy.getSystemColor( SWT.COLOR_YELLOW );
    this.systemColors[ 12 ] = dpy.getSystemColor( SWT.COLOR_BLUE );
    this.systemColors[ 13 ] = dpy.getSystemColor( SWT.COLOR_MAGENTA );
    this.systemColors[ 14 ] = dpy.getSystemColor( SWT.COLOR_CYAN );
    this.systemColors[ 15 ] = dpy.getSystemColor( SWT.COLOR_WHITE );
    for( int i = 16; i < 256; i++ ) {
      this.systemColors[ i ] = this.defaultBgColor;
    }
  }

  void changeScreenSize() {
    Char[][] newScreenBuffer;
    Font font = getFont();
    Point widgetSize = getSize();
    GC gc = new GC(this);
    gc.setFont(font);
    this.fontWidth = gc.getFontMetrics().getAverageCharWidth();
    this.fontHeight = gc.getFontMetrics().getHeight();
    gc.dispose();
    this.numCols = widgetSize.x / this.fontWidth;
    this.numLines = widgetSize.y / this.fontHeight;
    if ( this.numCols < 2 ) this.numCols = 80;
    if ( this.numLines < 2 ) this.numLines = 24;
    newScreenBuffer = new Char[ this.numLines ][];
    for( int i = 0; i < newScreenBuffer.length; i++ ) {
      newScreenBuffer[ i ] = new Char[ this.numCols ];
      // TODO copy old linemodes;
      // TODO copy contents of old screenbuffer to right location after resize (to smaller window)
      // copy contents of old screenbuffer
      if ( i < this.screenBuffer.length ) {
        int len = newScreenBuffer[ i ].length;
        if ( this.screenBuffer[ i ].length < len ) len = this.screenBuffer[ i ].length;
        for( int j = 0; j < len; j++ ) newScreenBuffer[ i ][ j ] = this.screenBuffer[ i ][ j ];
      }
      for( int j = 0; j < newScreenBuffer[ i ].length; j++ ) {
        if ( newScreenBuffer[ i ][ j ] == null ) {
          newScreenBuffer[ i ][ j ] = new Char( this.defaultFgColor, this.defaultBgColor );
        }
      }
    }
    this.screenBuffer = newScreenBuffer;
    this.lineHeightMode = new LineHeightMode[ this.numLines ];
    this.lineWidthMode = new LineWidthMode[ this.numLines ];
    resetLineModes();
    setTopAndBottomMargins( new int[0] );
    if ( this.cursor.line >= this.numLines ) this.cursor.line = this.numLines - 1;
    if ( this.cursor.col >= this.numCols ) this.cursor.col = this.numCols - 1;
    if ( this.numCols != 0 && this.numLines !=0 && this.listener != null) {
      this.listener.windowSizeChanged( this.numCols, this.numLines,
                                       this.numCols * this.fontWidth,
                                       this.numLines * this.fontHeight );
    }
  }

  /**
   * Sets the InputStream from which the data to display should be read.
   * @param in stream to display.
   */
  public void setInputStream( final InputStream in ) {
    this.input = new PushbackInputStream( in );
    startReaderThread();
  }

  /**
   * Sets the OuputStream to which the terminal input sould be sent to.
   * @param out stream to send input to.
   */
  public void setOutputStream( final OutputStream out ) {
    this.output = out;
  }

  @Override
  public void setFont( final Font font ) {
    this.terminalPainter.setFont( font );
    super.setFont( font );
  }
  
  @Override
  public Color getForeground() {
    return this.defaultFgColor;
  }
  
  @Override
  public Color getBackground() {
    return this.defaultBgColor;
  }
  
  boolean isInReverseScreenMode() {
    return this.reverseScreenMode;
  }
  
  int getFontHeigth() {
    return this.fontHeight;
  }
  
  int getFontWidth() {
    return this.fontWidth;
  }
  
  Char[][] getScreenBuffer() {
    return this.screenBuffer;
  }
  
  LineHeightMode[] getLineHeightMode() {
    return this.lineHeightMode;
  }
  
  
  LineWidthMode[] getLineWidthMode() {
    return this.lineWidthMode;
  }
  
  int getCursorLine() {
    return this.cursor.line;
  }
  
  int getCursorCol() {
    return this.cursor.col;
  }
}
