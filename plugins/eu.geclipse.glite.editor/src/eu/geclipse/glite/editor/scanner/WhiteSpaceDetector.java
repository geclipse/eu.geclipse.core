package eu.geclipse.glite.editor.scanner;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

/** <p>Der WhitespaceDetector entscheidet, ob es sich bei einem Zeichen 
 * um ein zu ignorierendes Treenzeichen handelt.</p>
 * 
 * @author Leif Frenzel 
 */
class WhitespaceDetector implements IWhitespaceDetector {

  public boolean isWhitespace( final char c ) {
    return ( c == ' ' || c == '\t' || c == '\n' || c == '\r' );
  }
}