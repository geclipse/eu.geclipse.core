package eu.geclipse.glite.editor.scanner;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class JdlCommentScanner extends RuleBasedScanner {

  public JdlCommentScanner() {
    IToken green = new Token( new TextAttribute( Display.getCurrent()
      .getSystemColor( SWT.COLOR_GREEN ) ) );
    IRule singleLineRule = new EndOfLineRule( "#", green ); //$NON-NLS-1$
    IRule[] rules = new IRule[]{
      singleLineRule
    };
    setRules( rules );
  }
}
