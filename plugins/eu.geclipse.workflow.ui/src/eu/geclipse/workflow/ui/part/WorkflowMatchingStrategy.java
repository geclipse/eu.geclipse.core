/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.part;

import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PartInitException;

/**
 * @generated
 */
public class WorkflowMatchingStrategy implements IEditorMatchingStrategy {

  /**
   * @generated
   */
  public boolean matches( IEditorReference editorRef, IEditorInput input ) {
    IEditorInput editorInput;
    try {
      editorInput = editorRef.getEditorInput();
    } catch( PartInitException e ) {
      return false;
    }
    if( editorInput.equals( input ) ) {
      return true;
    }
    if( editorInput instanceof URIEditorInput
        && input instanceof URIEditorInput )
    {
      return ( ( URIEditorInput )editorInput ).getURI()
        .equals( ( ( URIEditorInput )input ).getURI() );
    }
    return false;
  }
}
