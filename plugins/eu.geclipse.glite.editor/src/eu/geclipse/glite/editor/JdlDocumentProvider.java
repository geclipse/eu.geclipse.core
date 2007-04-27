package eu.geclipse.glite.editor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.DefaultPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

public class JdlDocumentProvider extends FileDocumentProvider {

  /*
   * provide the Document to the Editor here we use the Partion
   */
  @Override
  protected IDocument createDocument( final Object element ) throws CoreException
  {
    IDocument document = super.createDocument( element );
    // if the document is not empty, then connect it with the
    // PartitionScanner
    if( document != null ) {
      JdlPartitionScanner partScanner = new JdlPartitionScanner();
      IDocumentPartitioner partitioner = new DefaultPartitioner( partScanner,
                                                                 JdlPartitionScanner.PARTITION_TYPES );
      document.setDocumentPartitioner( partitioner );
      partitioner.connect( document );
    }
    return document;
  }
}
