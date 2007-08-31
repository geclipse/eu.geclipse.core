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

import org.eclipse.swt.graphics.Color;

class Cursor {
  int col;
  int line;
  boolean bold;
  boolean underscore;
  boolean blink;
  boolean negative;
  boolean italics;
  boolean strikethrough;
  CharSet charSet;
  Color fgColor;
  Color bgColor;

  Cursor( final Color defaultFgColor, final Color defaultBgColor ) {
    reset( defaultFgColor, defaultBgColor );
  }

  Cursor( final Cursor cursor ) {      
    this.col = cursor.col;
    this.line = cursor.line;
    this.bgColor = cursor.bgColor;
    this.fgColor = cursor.fgColor;
    this.bold = cursor.bold;
    this.underscore = cursor.underscore;
    this.blink = cursor.blink;
    this.negative = cursor.negative;
    this.italics = cursor.italics;
    this.strikethrough = cursor.strikethrough;
    this.charSet = cursor.charSet;
  }

  void reset( final Color defaultFgColor, final Color defaultBgColor ) {
    this.col = 0;
    this.line = 0;
    this.bgColor = defaultBgColor;
    this.fgColor = defaultFgColor;
    this.bold = false;
    this.underscore = false;
    this.blink = false;
    this.negative = false;
    this.italics = false;
    this.strikethrough = false;
    this.charSet = CharSet.USASCII;
  }
}
