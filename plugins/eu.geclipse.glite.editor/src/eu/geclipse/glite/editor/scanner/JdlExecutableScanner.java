package eu.geclipse.glite.editor.scanner;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class JdlExecutableScanner extends RuleBasedScanner {

  public JdlExecutableScanner() {
    IToken green = new Token( new TextAttribute( Display.getCurrent()
      .getSystemColor( SWT.COLOR_DARK_RED ) ) );
    IRule singleLineRule = new SingleLineRule( "Executable", //$NON-NLS-1$
                                               ";", //$NON-NLS-1$
                                               green,
                                               '\\',
                                               false,
                                               true );
    IRule[] rules = new IRule[]{
      singleLineRule
    };
    setRules( rules );
  }
}