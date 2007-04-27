package eu.geclipse.glite.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import eu.geclipse.glite.editor.codeassist.KeywordCompletionProcessor;
import eu.geclipse.glite.editor.scanner.JdlCommentScanner;
import eu.geclipse.glite.editor.scanner.JdlExecutableScanner;
import eu.geclipse.glite.editor.scanner.KeywordScanner;

/**
 * This class defines the text high lightning of a JDL source file.
 */
public class JdlSourceViewerConfiguration extends SourceViewerConfiguration {

  /*
   * Here we implement the codeAssistant functionality. We define the Assistant
   * only for the default partition, as we don't need one for the comment
   * partition. (non-Javadoc)
   * 
   * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getContentAssistant(org.eclipse.jface.text.source.ISourceViewer)
   */
  @Override
  public IContentAssistant getContentAssistant( final ISourceViewer sv ) {
    ContentAssistant result = new ContentAssistant();
    // connect the CodeAssistantprocessor with the partition
    result.setContentAssistProcessor( new KeywordCompletionProcessor(),
                                      IDocument.DEFAULT_CONTENT_TYPE );
    result.setProposalPopupOrientation( IContentAssistant.PROPOSAL_OVERLAY );
    return result;
  }

  /*
   * This function needs to be overwritten to let Editor know what he has to do
   * for the highlighting of text. The Reconciler defines two methods: Damage
   * and Repair (non-Javadoc)
   * 
   * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getPresentationReconciler(org.eclipse.jface.text.source.ISourceViewer)
   */
  @Override
  public IPresentationReconciler getPresentationReconciler( final ISourceViewer sourceViewer )
  {
    PresentationReconciler reconciler = new PresentationReconciler();
    DefaultDamagerRepairer dr = new DefaultDamagerRepairer( new JdlCommentScanner() );
    reconciler.setDamager( dr, JdlPartitionScanner.COMMENT );
    reconciler.setRepairer( dr, JdlPartitionScanner.COMMENT );
    dr = new DefaultDamagerRepairer( new JdlExecutableScanner() );
    reconciler.setDamager( dr, JdlPartitionScanner.EXECUTABLE );
    reconciler.setRepairer( dr, JdlPartitionScanner.EXECUTABLE );
    dr = new DefaultDamagerRepairer( new KeywordScanner() );
    reconciler.setDamager( dr, IDocument.DEFAULT_CONTENT_TYPE );
    reconciler.setRepairer( dr, IDocument.DEFAULT_CONTENT_TYPE );
    return reconciler;
  }
}
