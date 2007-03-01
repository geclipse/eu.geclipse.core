package eu.geclipse.glite.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

/*
 * Here we define the logical parts of the Jdl document. There are 3 sections
 * forseen - default (inherited from IDocument --> see PARTITION_TYPES ) -
 * Comment - Executable
 */
public class JdlPartitionScanner extends RuleBasedPartitionScanner {

  public static final String COMMENT = "Comment";
  public static final String EXECUTABLE = "Executable";
  public static final String[] PARTITION_TYPES = {
    IDocument.DEFAULT_CONTENT_TYPE, COMMENT, EXECUTABLE
  };

  public JdlPartitionScanner() {
    super();
    // define tokens to be used with the rules
    IToken CommentToken = new Token( JdlPartitionScanner.COMMENT );
    IToken ExecutableToken = new Token( JdlPartitionScanner.EXECUTABLE );
    // define the rules for the different partitions
    IPredicateRule[] rules = new IPredicateRule[ 2 ];
    rules[ 0 ] = new EndOfLineRule( "#", CommentToken ); //$NON-NLS-1$
    rules[ 1 ] = new SingleLineRule( "Executable", //$NON-NLS-1$
                                     ";", //$NON-NLS-1$
                                     ExecutableToken,
                                     '\\',
                                     false,
                                     true );
    setPredicateRules( rules );
  }
}
