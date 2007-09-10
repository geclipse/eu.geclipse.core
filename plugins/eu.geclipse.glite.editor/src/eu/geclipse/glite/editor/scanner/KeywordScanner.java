/*******************************************************************************
 * Copyright (c) 2006 g-Eclipse Consortium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Harald Kornmayer - initial implementation
 *******************************************************************************/

package eu.geclipse.glite.editor.scanner;

import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * <p>
 * Der KeywordScanner erkennt die definierten Schluesselwoerter und weist ihnen
 * spezielle Textattribute zu.
 * </p>
 * 
 * @author Leif Frenzel
 */
public class KeywordScanner extends RuleBasedScanner {

  private static final String[] KEYWORDS = {
    "Arguments", //$NON-NLS-1$
    "Environment", //$NON-NLS-1$
    "StdOutput", //$NON-NLS-1$
    "StdError", //$NON-NLS-1$
    "InputSandbox", //$NON-NLS-1$
    "OutputSandbox", //$NON-NLS-1$
    "RetryCount" //$NON-NLS-1$
  };

  public KeywordScanner() {
    createRules();
  }

  // Hilfsmethoden
  // ////////////////
  private void createRules() {
    IToken keywordToken = createKeywordToken();
    IToken defaultToken = createDefaultToken();
    WordRule wordRule = new WordRule( new WordDetector(), defaultToken );
    for( int i = 0; i < KEYWORDS.length; i++ ) {
      wordRule.addWord( KEYWORDS[ i ], keywordToken );
    }
    IRule[] rules = new IRule[]{
      wordRule,
      // generic whitespace rule.
      new WhitespaceRule( new WhitespaceDetector() )
    };
    setRules( rules );
  }

  private IToken createKeywordToken() {
    Color color = Display.getDefault().getSystemColor( SWT.COLOR_RED );
    TextAttribute ta = new TextAttribute( color, null, SWT.BOLD );
    return new Token( ta );
  }

  private IToken createDefaultToken() {
    Color color = Display.getDefault().getSystemColor( SWT.COLOR_BLACK );
    TextAttribute ta = new TextAttribute( color );
    return new Token( ta );
  }
  
  // inner class
  //////////////
  private class WordDetector implements IWordDetector {

    public boolean isWordPart( final char character ) {
      return Character.isJavaIdentifierPart( character );
    }

    public boolean isWordStart( final char character ) {
      return Character.isJavaIdentifierStart( character );
    }
  }
}
