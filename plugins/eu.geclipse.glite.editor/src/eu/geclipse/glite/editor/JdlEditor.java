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

import java.util.ResourceBundle;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.TextOperationAction;

public class JdlEditor extends TextEditor {

  public JdlEditor() {
    super();
    // now we define the Viewer of the Editor
    // there the "highlight" will be done based on the partitions
    setSourceViewerConfiguration( new JdlSourceViewerConfiguration() );
    // set the document provider
    setDocumentProvider( new JdlDocumentProvider() );
  }

  /*
   * Overwrites the method to enable CodeCompletion (non-Javadoc)
   * 
   * @see org.eclipse.ui.editors.text.TextEditor#createActions@Override ()
   */
  protected void createActions() {
    super.createActions();
    // wir konfigurieren eine Action fuer Content Assist,
    // die ausgefuehrt wird, wenn der Benutzer CTRL-C eingibt
    IAction action = createAction();
    String actionId = ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS;
    action.setActionDefinitionId( actionId );
    setAction( "ContentAssistProposal", action );
  }

  // Hilfsmethoden
  // //////////////
  private IAction createAction() {
    ResourceBundle bundle = Activator.getDefault().getResourceBundle();
    // TextOperationAction gehoert zu den Actions in der Eclipse-Workbench,
    // die sich selbst konfigurieren; die benaetigte Information wird aus
    // dem uebergebenen ResourceBundle ausgelesen, verwendet werden hier
    // diejenigen Properties, deren Schluessel mit "ContentAssistProposal."
    // beginnt
    return new TextOperationAction( bundle,
                                    "ContentAssistProposal.",
                                    this,
                                    ISourceViewer.CONTENTASSIST_PROPOSALS );
  }
}
