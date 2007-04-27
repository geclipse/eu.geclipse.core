package eu.geclipse.glite.editor.codeassist;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

/**
 * <p>
 * Der KeywordCompletionProcessor implementiert fuer den Content Assistant die
 * automatische Code-Vervollstaendigung (content assist proposals), die den
 * Benutzer bei Betaetigen von CTRL-C angezeigt werden.
 * </p>
 * 
 * @author Leif Frenzel
 */
public class KeywordCompletionProcessor implements IContentAssistProcessor {

  private final static String[] proposalDisplays = new String[]{
    "Execuatable - declare the grid application to run ",
    "Arguments - declare the applications arguements",
    "Environment - declare specific environment variables ",
    "StdOutput - direct the StdOut to a file",
    "StdError - direct the StdError to a file",
    "InputSandbox - define the files send from local computer to the Grid sandbox",
    "OutputSandbox - define the files send from the Grid sandbox to your local computer"
  };
  private final static String[] proposalReplacements = new String[]{
    "Executable = {  }; ",
    "Arguments = {\"\"}; ",
    "Environment = {\"\"}; ",
    "StdOutput = {\"\"}; ",
    "StdError = {\"\"}; ",
    "InputSandBox = {\"\"}; ",
    "OutputSandbox = {\"\"}; "
  };

  public ICompletionProposal[] computeCompletionProposals( final ITextViewer viewer,
                                                           final int documentOffset )
  {
    // we return just the list of proposals
    int length = proposalDisplays.length;
    ICompletionProposal[] result = new ICompletionProposal[ length ];
    for( int i = 0; i < length; i++ ) {
      String toDisplay = proposalDisplays[ i ];
      IContextInformation info = new ContextInformation( toDisplay, toDisplay );
      result[ i ] = new CompletionProposal( proposalReplacements[ i ],
                                            documentOffset,
                                            0,
                                            proposalReplacements[ i ].length(),
                                            null,
                                            proposalDisplays[ i ],
                                            info,
                                            "Test2" );
    }
    return result;
  }

  public IContextInformation[] computeContextInformation( final ITextViewer viewer,
                                                          final int documentOffset )
  {
    // no context infopops
    return null;
  }

  public char[] getCompletionProposalAutoActivationCharacters() {
    // no auto activation characters, we trigger only on user request
    return new char[ 0 ];
  }

  public char[] getContextInformationAutoActivationCharacters() {
    // no context infopops
    return new char[ 0 ];
  }

  public String getErrorMessage() {
    return null;
  }

  public IContextInformationValidator getContextInformationValidator() {
    // no context infopops
    return null;
  }
}