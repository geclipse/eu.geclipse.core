/*****************************************************************************
 * Copyright (c) 2009, 2010 g-Eclipse Consortium 
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
 *    Thomas Koeckerbauer MNM-Team, LMU Munich - initial API and implementation
 *****************************************************************************/

package eu.geclipse.eventgraph.tracereader.otf;

class OTFUtils {
  String line;
  int lineIdx = 0;
  int starttime = -1;

  int readNumber() {
    int value = 0;
    int ch = read();
    while( ( ch >= '0' && ch <= '9' ) || ( ch >= 'a' && ch <= 'f' ) ) {
      value *= 16;
      if ( ch >= '0' && ch <= '9' ) {
        value += ch - '0';
      } else {
        value += ch - 'a' + 10;
      }
      ch = read();
    }
    this.lineIdx--;
    return value;
  }
  
  int readTime() {
    long value = 0;
    int ch = read();
    while( ( ch >= '0' && ch <= '9' ) || ( ch >= 'a' && ch <= 'f' ) ) {
      value *= 16;
      if ( ch >= '0' && ch <= '9' ) {
        value += ch - '0';
      } else {
        value += ch - 'a' + 10;
      }
      ch = read();
    }
    this.lineIdx--;
    return (int) (value / 1000); // TODO add support for larger time stamps in trace viewer
  }
  
  int read() {
    int result;
    if ( this.lineIdx == this.line.length() ) result = 0;
    else result = this.line.charAt( this.lineIdx++ ); 
    return result;
  }
  
  boolean checkChar( final int chExpected ) {
    int chRead = read();
    boolean rightChar = chRead == chExpected;
    if ( !rightChar ) System.out.println( "Parse error: expeced "
                                          + ((char) chExpected) + " found "
                                          + ((char) chRead) );
    return rightChar;
  }
}
