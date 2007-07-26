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

class Char {
  boolean bold;
  boolean underscore;
  boolean blink;
  boolean negative;
  boolean italics;
  boolean strikethrough;
  Color fgColor;
  Color bgColor;
  CharSet charSet;
  char ch;

  Char( final Color defaultFgColor, final Color defaultBgColor ) {
    erase( defaultFgColor, defaultBgColor );
  }

  void setToCursorFormat( final Cursor cur ) {
    this.bold = cur.bold;
    this.underscore = cur.underscore;
    this.blink = cur.blink;
    this.negative = cur.negative;
    this.italics = cur.italics;
    this.strikethrough = cur.strikethrough;
    this.fgColor = cur.fgColor;
    this.bgColor = cur.bgColor;
    this.charSet = cur.charSet;
  }

  boolean hasSameFormat( final Char otherChar ) {
    return this.bold == otherChar.bold
           && this.underscore == otherChar.underscore
           && this.blink == otherChar.blink
           && this.negative == otherChar.negative
           && this.italics == otherChar.italics
           && this.strikethrough == otherChar.strikethrough
           && this.fgColor == otherChar.fgColor
           && this.bgColor == otherChar.bgColor
           && this.fgColor.equals( otherChar.fgColor )
           && this.bgColor.equals( otherChar.bgColor );
  }

  void erase( final Color defaultFgColor, final Color defaultBgColor ) {
    this.ch = 0;
    this.fgColor = defaultFgColor;
    this.bgColor = defaultBgColor;
    this.bold = false;
    this.underscore = false;
    this.blink = false;
    this.negative = false;
    this.italics = false;
    this.strikethrough = false;
    this.charSet = CharSet.USASCII;
  }
}
