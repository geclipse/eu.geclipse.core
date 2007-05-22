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
