package eu.geclipse.eventgraph.tracereader.otf;


class OTFUtils {
  String line;
  int lineIdx = 0;
    
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
  
  int read() {
    int result;
    if ( this.lineIdx == this.line.length() ) result = 0;
    else result = this.line.charAt( this.lineIdx++ );
//System.out.println( ((char)result) );
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
